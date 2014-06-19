package cheesegame.handlers;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class CollisionDetector {
	private static Set<Polygon> polys = new HashSet<Polygon>();
	private static final float SLOPE_HAX = 1f;
	
	public static void setLevelPolys(final Set<Polygon> polygons) {
		polys = polygons;
	}
	
	public static Set<Polygon> getCollisions(final Rectangle test) {
		if(polys.isEmpty())
			return new HashSet<Polygon>();
		Set<Polygon> res = new HashSet<Polygon>();
		
		Vector2[] points = new Vector2[] {new Vector2(test.x, test.y), new Vector2(test.x + test.width, test.y), 
										  new Vector2(test.x, test.y + test.height), new Vector2(test.x + test.width, test.y + test.height)};
		for(Vector2 point : points)
			for(Polygon poly : polys)
				if(poly.contains(point.x, point.y))
					res.add(poly);
		return res;
	}
	
	public static void handleCollision(final Vector2 velocity, final Vector2 position, final Rectangle hitbox) {
		if(velocity.x > 0) {
			// Handle slope collision
			if((collidesWithLevel(hitbox.x + hitbox.width + velocity.x, hitbox.y)) && 
			   (!collidesWithLevel(hitbox.x + hitbox.width + velocity.x, hitbox.y + SLOPE_HAX))) { 
					position.x += SLOPE_HAX;
					position.y += SLOPE_HAX;
			}
			if((collidesWithLevel(hitbox.x + hitbox.width + velocity.x, hitbox.y)) || 
			   (collidesWithLevel(hitbox.x + hitbox.width + velocity.x, hitbox.y + hitbox.height)))
				velocity.x = 0;
		}
		if((velocity.x < 0) && 
				(collidesWithLevel(hitbox.x + velocity.x, hitbox.y)) || 
				(collidesWithLevel(hitbox.x + velocity.x, hitbox.y + hitbox.height))) {
			if(!collidesWithLevel(hitbox.x + velocity.x, hitbox.y + SLOPE_HAX)) {
				position.x -= SLOPE_HAX;
				position.y += SLOPE_HAX;
			}
			if((collidesWithLevel(hitbox.x + velocity.x, hitbox.y)) || 
			   (collidesWithLevel(hitbox.x + velocity.x, hitbox.y + hitbox.height)))
				velocity.x = 0;
		}
		if((velocity.y > 0) && 
				(collidesWithLevel(hitbox.x, hitbox.y + hitbox.height + velocity.y)) || 
				(collidesWithLevel(hitbox.x + hitbox.width, hitbox.y + hitbox.height + velocity.y))) {
			velocity.y = 0;
		}
		if((velocity.y < 0) &&
				(collidesWithLevel(hitbox.x, hitbox.y + velocity.y)) || 
				(collidesWithLevel(hitbox.x + hitbox.width, hitbox.y + velocity.y))) {
			velocity.y = 0;
		}
	}
	
	public static boolean collidesWithLevel(final float x, final float y) {
		for(Polygon poly : polys)
			if(poly.contains(x, y))
				return true;
		return false;
	}
}
