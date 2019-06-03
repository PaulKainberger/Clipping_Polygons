package Polygon;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;
import java.util.Set;


/**
 * Implements different polygon clipping algorithms.
 * The class stores the clipping polygon, the candidate polygons and the result polygons.
 * There are methods for each clipping algorithm which return whether the clipping was successful.
 * 
 * @author Jakob
 * 
 */
public class ClippingAlgorithm {
	
	/**
	 * The clipping polygon.
	 */
	private Polygon clippingPolygon;

	/**
	 * The candidate polygons.
	 */
	private Set<Polygon> candidatePolygons;
	
	/**
	 * The result polygons.
	 */
	private Set<Polygon> resultPolygons;

	/**
	 * Constructor for Clipping algorithm with empty fields.
	 */
	public ClippingAlgorithm() {
		candidatePolygons = new HashSet<Polygon>();
		resultPolygons = new HashSet<Polygon>();
	}
	
	/**
	 * Sets a polygon as clipping polygon.
	 * @param p The clipping polygon.
	 */
	public void setClippingPolygon(Polygon p) {
		clippingPolygon = p;
	}
	
	/**
	 * Adds a polygon to the set of candidate polygons.
	 * @param p The candidate polygon.
	 */
	public void addCandidatePolygon(Polygon p) {
		candidatePolygons.add(p);
	}
	
	/**
	 * Adds a set of polygon to the set of candidate polygons.
	 * @param s The set of clipping polygons.
	 */
	public void addCandidatePolygon(Set<Polygon> s) {
		candidatePolygons.addAll(s);
	}
	
	/**
	 * Returns the result of the clipping algorithm.
	 * @return The set of result polygons.
	 */
	public Set<Polygon> getResult(){
		return resultPolygons;
	}
	
	/**
	 * Runs the Sutherland-Hodgman algorithm.
	 * The algorithm can only handle a convex and therefore non intersecting clipping polygon.
	 * If the clipping polygon is not convex false will be returned and nothing will happen. 
	 * More details on the algorithm can be found e.g. on Wikipedia, see:
	 * https://en.wikipedia.org/wiki/Sutherland%E2%80%93Hodgman_algorithm
	 * 
	 * @return Whether the clipping was successful.
	 */
	public boolean SutherlandHodgman() {
		if(clippingPolygon == null || clippingPolygon.isSelfIntersecting() || !clippingPolygon.isConvex()) {
			return false;
		}
		
		for(Polygon candidate : candidatePolygons) {
			Polygon result = clipSutherlandHodgman(candidate);
			if(result == null) {
				return false;
			} else {
				resultPolygons.add(result);
			}
		}
		return true;
	}
	
	/**
	 * Clips a candidate polygon against the clipping polygon using
	 * the Sutherland-Hodgman algorithm.
	 * 
	 * @param candidate Polygon which should be clipped against the clipping polygon.
	 * @return The clipped polygon.
	 */
	private Polygon clipSutherlandHodgman(Polygon candidate) {
		// add exception for single point in clipping poly?!
		
		Polygon result = new Polygon(candidate);
		
		int numVertClip = clippingPolygon.getNumberVertices();
		for(int i = 0; i < numVertClip; i++) {
			result = new Polygon();
			// two end points of current edge
			Point2D.Double eBegin = clippingPolygon.getVertex(i);
			Point2D.Double eEnd = clippingPolygon.getVertex((i + 1) % numVertClip);
			
			int numberVertRes = result.getNumberVertices();
			for(int  j = 0; j < numberVertRes; j++) {
				Point2D.Double currentPoint = result.getVertex(j);
				Point2D.Double prevPoint = result.getVertex((j+numberVertRes-1) % numberVertRes);
				
				Point2D.Double intersection;
				try {
					intersection = Polygon.intersectLines(prevPoint, currentPoint, eBegin, eEnd, true);
				} catch(IntersectionException e) {
					return null;
				}
				if(clippingPolygon.contains(currentPoint)) {
					if(!clippingPolygon.contains(prevPoint)) {
						result.addVertex(intersection);
					}
					result.addVertex(currentPoint);
				} else if(clippingPolygon.contains(prevPoint)) {
					result.addVertex(intersection);
				}
			}
		}
		
		return result;
	}
	
	/**
	 * Runs the Weiler-Atherton algorithm.
	 * @return Whether the clipping was successful.
	 */
	public boolean WeilerAtherton() {
		//TODO: implement Weiler-Atherton algorithm
		return false;
	}
	
	/**
	 * Runs the Greiner-Horman algorithm.
	 * @return Whether the clipping was successful.
	 */
	public boolean GreinerHorman() {
		//TODO: implement Greiner-Horman algorithm
		return false;
	}
}
