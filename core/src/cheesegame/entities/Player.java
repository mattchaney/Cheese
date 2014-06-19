package cheesegame.entities;

import cheesegame.handlers.CollisionDetector;
import cheesegame.screens.PlayScreen;

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
	
	public enum State {
		Standing, Walking, Jumping, Dead
	}
	
	public Player(final Vector2 position) {
		super(position, new Rectangle(position.x, position.y, WIDTH * 0.45f, HEIGHT * 0.9f), "cheese.png");
	}
	
	@Override
	public void update(float delta) {
		velocity.scl(delta);
		
		if(!grounded)
			velocity.add(0f, PlayScreen.GRAVITY * delta);
		
		if (Math.abs(velocity.x) > MAX_VELOCITY)
			velocity.x = Math.signum(velocity.x) * Player.MAX_VELOCITY;
		
		if(velocity.x > 0)
			facesRight = true;
		else
			facesRight = false;
		
		// Perform collision detection and handling
		CollisionDetector.handleCollision(velocity, position, hitbox);
		
		// update player position
		position.x = position.x + velocity.x;
		position.y = position.y + velocity.y;
		
		// update hitbox position
		hitbox.x = position.x + WIDTH * 0.275f;
		hitbox.y = position.y + HEIGHT * 0.05f;
		
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
