package Polygon.greinerHorman;

import java.awt.geom.Point2D.Double;
import java.util.ListIterator;

/**
 * Represents a vertex of a polygon with additional information used by the Greiner-Horman algorithm.
 * 
 * @author Jakob
 *
 */

class GreinerHormanVertex extends Double {
	
	/**
	 * Default serial version ID.
	 * Just there because eclipse wants it.
	 */
	private static final long serialVersionUID = 1L;
	boolean intersect;
	boolean entryExit;
	ListIterator<Double> neighbor;
	double alpha;
	
	/**
	 * Constructor for a vertex without special information.
	 * @param x The x-coordinate.
	 * @param y The y-coordinate.
	 */
	public GreinerHormanVertex(double x, double y) {
		super(x, y);
	}

	/**
	 * Constructor from a Point2D.Double object.
	 * @param v The point2D.Double to take the coordinates from.
	 */
	public GreinerHormanVertex(Double v) {
		super(v.getX(),v.getY());
	}
	
	/**
	 * Constructor for all fields.
	 * @param x The x-coordinate.
	 * @param y The y-coordinate.
	 * @param intersect Is the vertex an intersection of the two polygons.
	 * @param entryExit Is the vertex an entry or an exit vertex 
	 * @param neighbor Pointer to the corresponding vertex of the other polygon considered.
	 * @param alpha The position of the vertex on the edge, if it is an intersecting vertex.
	 */
	public GreinerHormanVertex(double x, double y, boolean intersect, 
			boolean entryExit, ListIterator<Double> neighbor, double alpha) {
		super(x,y);
		this.intersect = intersect;
		this.entryExit = entryExit;
		this.neighbor = neighbor;
		this.alpha = alpha;
	}

	/**
	 * Is the vertex an intersection vertex.
	 * @return Whether the vertex is an intersection vertex.
	 */
	public boolean isIntersect() {
		return intersect;
	}

	/**
	 * @return True if the vertex is an entry to the other polygon, false otherwise.
	 */
	public boolean isEntryExit() {
		return entryExit;
	}

	/**
	 * Gets the corresponding vertex in the other polygon.
	 * @return An iterator with next pointing to the corresponding vertex in the other polygon.
	 */
	public ListIterator<Double> getNeighbor() {
		return neighbor;
	}

	/**
	 * Gets the relative position on the original edge.
	 * @return The relative position of an intersection vertex on the original edge.
	 */
	public double getAlpha() {
		return alpha;
	}
}
