package cheesegame.entities;

import com.badlogic.gdx.math.Rectangle;

public abstract class Entity {
	
	public float x;
	public float y;
	public Rectangle hitbox;
	
	Entity(final float x, final float y, final Rectangle hitbox) {
		this.x = x;
		this.y = y;
		this.hitbox = hitbox;
	}
	
	public abstract void update(float delta);
}
