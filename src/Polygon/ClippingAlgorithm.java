package Polygon;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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
	public Set<Polygon> getResult() {
		// remove empty polgon
		Polygon emptyPolygon = new Polygon();
		resultPolygons.remove(emptyPolygon);
		return resultPolygons;
	}
	
	/**
	 * Runs the Sutherland-Hodgman algorithm.
	 * The algorithm can only handle a convex and therefore non intersecting clipping polygon.
	 * If the clipping polygon is not convex false will be returned and nothing will happen. 
	 * More details on the algorithm can be found e.g. on Wikipedia, see:
	 * https://en.wikipedia.org/wiki/Sutherland%E2%80%93Hodgman_algorithm
	 * If the candidate polygons are not convex there might be double edges in the resulting
	 * polygons.
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
		if(candidate.isPoint()) {
			if(clippingPolygon.contains(candidate.getVertex(0))) {
				return new Polygon(candidate);
			} else {
				return new Polygon();
			}
		}
		
		Polygon result = new Polygon(candidate);
		
		boolean clockwiseOriented = clippingPolygon.orientedClockwise();
		
		int numVertClip = clippingPolygon.getNumberVertices();
		for(int i = 0; i < numVertClip; i++) {
			// two end points of current edge
			Point2D.Double eBegin, eEnd;
			if(clockwiseOriented) {
				eBegin = clippingPolygon.getVertex(i);
				eEnd = clippingPolygon.getVertex((i + numVertClip - 1) % numVertClip);
			} else {
				eBegin = clippingPolygon.getVertex(numVertClip - i - 1);
				eEnd = clippingPolygon.getVertex((numVertClip - i) % numVertClip);
			}
			
			Polygon resultTmp = new Polygon(result);
			result.clear();
			
			int numberVertRes = resultTmp.getNumberVertices();
			for(int  j = 0; j < numberVertRes; j++) {
				Point2D.Double currentPoint = resultTmp.getVertex(j);
				Point2D.Double prevPoint = resultTmp.getVertex((j + numberVertRes - 1) % numberVertRes);
				
				Point2D.Double intersection;
				try {
					intersection = Polygon.intersectLines(prevPoint, currentPoint, eBegin, eEnd, true);
				} catch(IntersectionException e) {
					return null;
				}
				if(Polygon.inside(currentPoint, eBegin, eEnd)) {
					if(!Polygon.inside(prevPoint, eBegin, eEnd)) {
						result.addVertex(intersection);
					}
					result.addVertex(currentPoint);
				} else if(Polygon.inside(prevPoint, eBegin, eEnd)) {
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
		if(clippingPolygon == null || clippingPolygon.isSelfIntersecting()) {
			return false;
		}
		
		for(Polygon candidate : candidatePolygons) {
			List<Polygon> result = clipWeilerAtherton(candidate);
			if(result == null) {
				return false;
			} else {
				resultPolygons.addAll(result);
			}
		}
		return true;
	}
	
	private List<Polygon> clipWeilerAtherton(Polygon candidate) {
		// TODO method contains must be implemented (?)
		/*if (clippingPolygon.contains(candidate)) {
			return new Polygon(candidate);
		}
		if (candidate.contains(clippingPolygon)) {
			return new Polygon();
		}*/
		
		Set<Point2D.Double> intersectionPointsSet = null;
		try {
			intersectionPointsSet = candidate.intersect(clippingPolygon);
		} catch (IntersectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Point2D.Double> intersectionPoints = new ArrayList<Point2D.Double>(intersectionPointsSet);
		
		Polygon candidateWithIntersections = new Polygon(candidate);
		Polygon clippingWithIntersections = new Polygon(clippingPolygon);
		List<Boolean> candidateIsIntersectionPoint = new ArrayList<Boolean>(Collections.nCopies(candidate.getNumberVertices(), false));
		List<Boolean> clippingIsIntersectionPoint = new ArrayList<Boolean>(Collections.nCopies(clippingPolygon.getNumberVertices(), false));
		
		// Add intersection points to the polygons. Polygons do not change visually.
		for (Point2D.Double intersectingPoint : intersectionPoints) {
			// Add intersection points to candidate polygon.
			boolean flag = true; // Indicates whether the intersection point lies between last and first vertex.
			for (int i = 0; i < candidateWithIntersections.getNumberVertices() - 1; i++) {
				if(Polygon.isBetween(candidateWithIntersections.getVertex(i), candidateWithIntersections.getVertex(i+1), intersectingPoint)) {
					candidateWithIntersections.addVertex(intersectingPoint, i+1);
					candidateIsIntersectionPoint.add(i+1, true);
					flag = false;
					break;
				}
			}
			if(flag) {
				candidateWithIntersections.addVertex(intersectingPoint);
				candidateIsIntersectionPoint.add(true);
			}
			
			// Add intersection points to clipping polygon.
			flag = true; // Indicates whether the intersection point lies between last and first vertex.
			for (int i = 0; i < clippingWithIntersections.getNumberVertices() - 1; i++) {
				if(Polygon.isBetween(clippingWithIntersections.getVertex(i), clippingWithIntersections.getVertex(i+1), intersectingPoint)) {
					clippingWithIntersections.addVertex(intersectingPoint, i+1);
					clippingIsIntersectionPoint.add(i+1, true);
					flag = false;
					break;
				}
			}
			if(flag) {
				clippingWithIntersections.addVertex(intersectingPoint);
				clippingIsIntersectionPoint.add(true);
			}
		}
		
		// Construct clipped polygons.
		List<Polygon> clippedPols = new ArrayList<Polygon>();
		while (! intersectionPoints.isEmpty()) {
			// Construct new clipped polygon.
			Polygon clipped = new Polygon();
			Point2D.Double vertex = new Point2D.Double();
			int i = candidateWithIntersections.vertices.indexOf(intersectionPoints.get(0)); // index for candidate polygon
			// Start clipping.
			do {
				vertex = candidateWithIntersections.getVertex(i);
				// vertex already in clipped??
				// ...
				clipped.addVertex(candidateWithIntersections.getVertex(i));
				intersectionPoints.remove(candidateWithIntersections.getVertex(i));
				i++;
			}
			while (candidateIsIntersectionPoint.get(i));
			if(intersectionPoints.isEmpty()) {
				break;
			}
			
			// ...
			
			clippedPols.add(clipped);
		}
		
		
		
		
		
		/*/int j = clippingWithIntersections.vertices.indexOf(intersectionPoints.get(0)); // index for clipping polygon
		if(! clippingWithIntersections.contains(candidateWithIntersections.getVertex(i+1))) {
			i++;
			while (! candidateIsIntersectionPoint.get(i)) {
				clipped.addVertex(clippingWithIntersections.getVertex(i));
				i++;
			}
			intersectionPoints.remove(0);
		}
		clipped.addVertex(intersectionPoints.get(0));*/
		
		
		return clippedPols;
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
