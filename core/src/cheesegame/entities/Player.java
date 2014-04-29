package cheesegame.entities;

import cheesegame.handlers.CollisionHandler;
import cheesegame.screens.PlayScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player extends Entity {
	public static final float MAX_VELOCITY = 12f;
	public static final float JUMP_VELOCITY = 260f;
	public static final float DAMPING = 0.87f;
	public static final float WIDTH = 24;
	public static final float HEIGHT = 36;
	
	public final Vector2 velocity = new Vector2();
	public State state = State.Walking;
	public float stateTime = 0;
	public boolean facesRight = true;
	public boolean grounded = false;
	
	public Texture texture;
	
	private static final float SLOPE_HAX = 1f;
	private static final float HB_HAX = 0.5f;
	
	public enum State {
		Standing, Walking, Jumping, Dead
	}
	
	public Player(final float x, final float y) {
		super(x, y, new Rectangle(x, y, WIDTH * 0.45f, HEIGHT * 0.9f));
		texture = new Texture(Gdx.files.internal("cheese.png"));
		
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void update(float delta) {
		velocity.scl(delta);
		
		velocity.add(0f, PlayScreen.GRAVITY * delta);
		if (Math.abs(velocity.x) > MAX_VELOCITY) {
			velocity.x = Math.signum(velocity.x) * Player.MAX_VELOCITY;
		}
		if(velocity.x > 0)
			facesRight = true;
		else
			facesRight = false;
		
		// Perform collision detection and handling
		if(velocity.x > 0) {
			// Will leading foot collide with level?
			if((CollisionHandler.collidesWithLevel(hitbox.x + hitbox.width + velocity.x, hitbox.y)) && 
			   (!CollisionHandler.collidesWithLevel(hitbox.x + hitbox.width, hitbox.y + SLOPE_HAX))) { 
					x += SLOPE_HAX;
					y += SLOPE_HAX;
			}
			if((CollisionHandler.collidesWithLevel(hitbox.x + hitbox.width + velocity.x, hitbox.y)) || 
			   (CollisionHandler.collidesWithLevel(hitbox.x + hitbox.width + velocity.x, hitbox.y + hitbox.height)))
				velocity.x = 0;
		}
		if((velocity.x < 0) && 
				(CollisionHandler.collidesWithLevel(hitbox.x + velocity.x, hitbox.y)) || 
				(CollisionHandler.collidesWithLevel(hitbox.x + velocity.x, hitbox.y + hitbox.height))) {
			if(!CollisionHandler.collidesWithLevel(hitbox.x, hitbox.y + SLOPE_HAX)) {
				x -= SLOPE_HAX;
				y += SLOPE_HAX;
			}
			if((CollisionHandler.collidesWithLevel(hitbox.x + velocity.x, hitbox.y)) || 
			   (CollisionHandler.collidesWithLevel(hitbox.x + velocity.x, hitbox.y + hitbox.height)))
				velocity.x = 0;
		}
		if((velocity.y > 0) && 
				(CollisionHandler.collidesWithLevel(hitbox.x, hitbox.y + hitbox.height + velocity.y)) || 
				(CollisionHandler.collidesWithLevel(hitbox.x + hitbox.width, hitbox.y + hitbox.height + velocity.y))) {
			velocity.y = 0;
		}
		if((velocity.y < 0) && 
				(CollisionHandler.collidesWithLevel(hitbox.x, hitbox.y + velocity.y)) || 
				(CollisionHandler.collidesWithLevel(hitbox.x + hitbox.width, hitbox.y + velocity.y))) {
			velocity.y = 0;
			grounded = true;
		}
		
		// update player position
		x = x + velocity.x;
		y = y + velocity.y;
		
		// update hitbox position
		hitbox.x = x + WIDTH * 0.275f;
		hitbox.y = y + HEIGHT * 0.05f;
		
		if(velocity.y == 0)
			grounded = true;
		else
			grounded = false;
		
		velocity.scl(1 / delta);
		
		velocity.x *= Player.DAMPING;
		
		if(hitbox.y + hitbox.height < 0)
			state = State.Dead;
	}
}
