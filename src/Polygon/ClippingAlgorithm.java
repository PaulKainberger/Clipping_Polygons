package Polygon;

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
	 * @return Whether the clipping was successful.
	 */
	public boolean SutherlandHodgman() {
		//TODO: implement Sutherland-Hodgman algorithm		if(Polygon.pointsEqualEps(v, i.next()) || Polygon.pointsEqualEps(v, i.previous()))
		return false;
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
