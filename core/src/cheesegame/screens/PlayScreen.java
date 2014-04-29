package cheesegame.screens;

import java.util.HashSet;
import java.util.Set;

import cheesegame.CheeseGame;
import cheesegame.entities.Player;
import cheesegame.entities.Player.State;
import cheesegame.handlers.CollisionHandler;
import cheesegame.handlers.InputState;
import cheesegame.handlers.TiledMapManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PlayScreen extends AbstractScreen {
	
	public static final float GRAVITY = -12f;
	
	private static final String TAG = PlayScreen.class.getCanonicalName();
	private static int STARTX = 200;
	private static int STARTY = 15;
	
	private final CheeseGame game;
	private final SpriteBatch batch;
	private final Set<Polygon> polys = new HashSet<Polygon>();
	private final Set<Polygon> cPolys = new HashSet<Polygon>();
	private final Set<Polygon> uPolys = new HashSet<Polygon>();
	private final ShapeRenderer srenderer = new ShapeRenderer();
	private final String levelFilename;
	private TiledMap map;
	private OrthogonalTiledMapRenderer tileRenderer;
	private Player player;
	
	private boolean renderShapes = false;
	private Viewport viewport;
	
	public PlayScreen(final CheeseGame game, final String filename) {
		this.game = game;
		this.levelFilename = filename;
		
		viewport = game.getViewport();
		batch = game.getSpriteBatch();
		loadMap(filename);
	}
	
	private void loadMap(final String filename) {
		polys.clear();
		map = TiledMapManager.loadMap(filename, polys);
		CollisionHandler.setLevelPolys(polys);
		tileRenderer = new OrthogonalTiledMapRenderer(map, CheeseGame.SCALE);
		player = new Player(STARTX, STARTY);
	}
	
	@Override
	public void processInput() {
		if(InputState.isPressed(InputState.JUMP) && player.grounded) {
			Gdx.app.log(TAG, "Jump pressed");
			player.velocity.y += Player.JUMP_VELOCITY;
			player.grounded = false;
		}
		if(InputState.isPressed(InputState.ESC)) {
			Gdx.app.exit();
		}
		if(InputState.isDown(InputState.RIGHT)) {
			player.velocity.x += Player.MAX_VELOCITY;
		}
		if(InputState.isDown(InputState.LEFT)) {
			player.velocity.x -= Player.MAX_VELOCITY;
		}
		if(InputState.isPressed(InputState.F1)) {
			renderShapes ^= true;
			Gdx.app.log(TAG, "Debug rendering: " + renderShapes);
		}
		if(InputState.isPressed(InputState.F5)) {
			loadMap(levelFilename);
		}
	}
	
	@Override
	public void update(float delta) {
		player.update(delta);
		if(State.Dead == player.state)
			player = new Player(STARTX, STARTY);
	}
	
	@Override
	public void render(float dt) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		viewport.getCamera().position.x = Math.max(player.x, 40f);
		viewport.getCamera().update();
		
		tileRenderer.setView((OrthographicCamera) viewport.getCamera());
		tileRenderer.render();
		
		cPolys.clear();
		cPolys.addAll(CollisionHandler.getCollisions(player.hitbox));
		uPolys.clear();
		uPolys.addAll(polys);
		uPolys.removeAll(cPolys);
		
		if(renderShapes) {
			srenderer.setProjectionMatrix(viewport.getCamera().combined);
			
			// render stage polygons
			srenderer.begin(ShapeType.Line);
			srenderer.setColor(Color.WHITE);
			
			for(Polygon poly : uPolys)
				srenderer.polygon(poly.getVertices());
			srenderer.setColor(Color.RED);
			for(Polygon poly : cPolys)
				srenderer.polygon(poly.getVertices());
			srenderer.end();
			
			srenderer.setColor(Color.WHITE);
			// Render player aabb
			srenderer.begin(ShapeType.Filled);
			srenderer.rect(player.hitbox.x, player.hitbox.y, player.hitbox.width, player.hitbox.height);
			srenderer.end();
		}
		batch.begin();
		batch.setProjectionMatrix(game.getCamera().combined);
		
		
		if (player.facesRight) {
			batch.draw(player.texture, player.x + Player.WIDTH, player.y, -Player.WIDTH, Player.HEIGHT);
		} else {
			batch.draw(player.texture, player.x, player.y, Player.WIDTH, Player.HEIGHT);
		}
		
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void show() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
	}
}
