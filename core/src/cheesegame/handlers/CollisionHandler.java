package cheesegame.handlers;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class CollisionHandler {
	private static Set<Polygon> polys = new HashSet<Polygon>();
	
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
	
	public static boolean collidesWithLevel(final float x, final float y) {
		for(Polygon poly : polys)
			if(poly.contains(x, y))
				return true;
		return false;
	}
}
