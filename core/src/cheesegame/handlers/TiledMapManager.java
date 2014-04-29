package cheesegame.handlers;

import java.util.Set;

import cheesegame.CheeseGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Polygon;

public class TiledMapManager {
	private static final String TAG = TiledMapManager.class.getCanonicalName();
	private static TmxMapLoader loader = new TmxMapLoader();
	
	/** Loads the TiledMap specified by <code>filename</code> and creates polygons from the 
	 *  object layer labeled "ground", adding each to the set of <code>polys</code>
	 */
	public static TiledMap loadMap(final String filename, final Set<Polygon> polygons) {
		TiledMap map = loader.load(filename);
		Gdx.app.log(TAG, "Loaded level: " + filename);
		MapLayer layer = (MapLayer) map.getLayers().get("ground");
		MapObjects objects = layer.getObjects();
		Gdx.app.log(TAG, "Creating " + objects.getCount() + " objects");
		for(MapObject object : objects) {
			if(object instanceof TextureMapObject)
				continue;
			if(object instanceof PolygonMapObject)
				polygons.add(buildPolygon((PolygonMapObject) object));
		}
		return map;
	}
	
	private static Polygon buildPolygon(PolygonMapObject polygonObject) {
		Polygon polygon = new Polygon();
        float[] vertices = polygonObject.getPolygon().getTransformedVertices();
        float[] worldVertices = new float[vertices.length];

        for (int i = 0; i < vertices.length; ++i) {
            worldVertices[i] = vertices[i] * CheeseGame.SCALE;
        }
        polygon.setVertices(worldVertices);
        return polygon;
    }
}

