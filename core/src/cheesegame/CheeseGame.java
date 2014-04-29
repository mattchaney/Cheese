package cheesegame;

import java.util.Stack;

import cheesegame.handlers.InputProcessor;
import cheesegame.handlers.InputState;
import cheesegame.screens.AbstractScreen;
import cheesegame.screens.PlayScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class CheeseGame extends Game {

	private Stack<AbstractScreen> screens = new Stack<AbstractScreen>();
	
	public static final int V_WIDTH = 320;
	public static final int V_HEIGHT = 240;
	public static final float SCALE = 0.2f;
	
	protected SpriteBatch batch;
	protected OrthographicCamera cam;
	protected OrthographicCamera hudcam;
	protected Viewport viewport;
	
	public SpriteBatch getSpriteBatch() {
		return batch;
	}
	
	public OrthographicCamera getCamera() {
		return cam;
	}
	
	public Viewport getViewport() {
		return viewport;
	}
	
	@Override
	public void create() {
		Gdx.input.setInputProcessor(new InputProcessor());
		cam = new OrthographicCamera();
		cam.setToOrtho(false, V_WIDTH, V_HEIGHT);
		cam.update();
		batch = new SpriteBatch();
		viewport = new FitViewport(V_WIDTH, V_HEIGHT, cam);
		screens.push(new PlayScreen(this, "level1.tmx"));
	}

	@Override
	public void render() {
		float delta = Gdx.graphics.getDeltaTime();
		screens.peek().processInput();
		screens.peek().update(delta);
		screens.peek().render(delta);
		InputState.update();
	}
	
	public void pushScreen(AbstractScreen screen){
		screens.push(screen);
		setScreen(screen);
	}
	
	public AbstractScreen popScreen() {
		AbstractScreen previous = screens.pop();
		if(!screens.isEmpty())
			setScreen(screens.peek());
		return previous;
	}
	
}
