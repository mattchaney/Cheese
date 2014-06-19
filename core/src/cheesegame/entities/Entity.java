package cheesegame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
	
	public Vector2 position;
	public Rectangle hitbox;
	public Texture texture;
	
	public Entity(final Vector2 position, final Rectangle hitbox, final String texFilename) {
		this.position = position;
		this.hitbox = hitbox;
		texture = new Texture(Gdx.files.internal(texFilename));
	}
	
	public abstract void update(float delta);
}
