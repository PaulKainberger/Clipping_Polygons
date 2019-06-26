package Polygon.greinerHorman;

import java.awt.geom.Point2D.Double;

import Polygon.Polygon;

/**
 * Represents a vertex of a polygon with additional information used by the Greiner-Horman algorithm.
 * 
 * @author Jakob
 *
 */

public class GreinerHormanVertex extends Double {
	
	/**
	 * Default serial version ID.
	 * Just there because eclipse wants it.
	 */
	private static final long serialVersionUID = 1L;
	protected boolean intersect;
	protected boolean entry;
	protected GreinerHormanVertex neighbor;
	protected double alpha;
	protected GreinerHormanVertex next;
	protected GreinerHormanVertex previous;
	
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
			boolean entry, GreinerHormanVertex neighbor, double alpha) {
		super(x,y);
		this.intersect = intersect;
		this.entry = entry;
		this.neighbor = neighbor;
		this.alpha = alpha;
	}
	
	/**
	 * Constructor given a vertex and values for all other fields.
	 * @param vertex the given vertex
	 * @param intersect Is the vertex an intersection of the two polygons.
	 * @param entryExit Is the vertex an entry or an exit vertex 
	 * @param neighbor Pointer to the corresponding vertex of the other polygon considered.
	 * @param alpha The position of the vertex on the edge, if it is an intersecting vertex.
	 */
	public GreinerHormanVertex(Double vertex, boolean intersect, 
			boolean entry, GreinerHormanVertex neighbor, double alpha) {
		super(vertex.x,vertex.y);
		this.intersect = intersect;
		this.entry = entry;
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
	public boolean isEntry() {
		return entry;
	}

	/**
	 * Gets the corresponding vertex in the other polygon.
	 * @return An iterator with next pointing to the corresponding vertex in the other polygon.
	 */
	public GreinerHormanVertex getNeighbor() {
		return neighbor;
	}

	/**
	 * Gets the relative position on the original edge.
	 * @return The relative position of an intersection vertex on the original edge.
	 */
	public double getAlpha() {
		return alpha;
	}

	/**
	 * Sets the boolean whether the vertex is an entry or an exit to the other polynomial.
	 * @param entryExit true if the vertex is an entry vertex.
	 */
	public void setEntry(boolean entryExit) {
		this.entry = entryExit;
	}
	
	
	public GreinerHormanVertex getNext() {
		return next;
	}
	
	public GreinerHormanVertex getPrevious() {
		return previous;
	}

	public void setNeighbor(GreinerHormanVertex v) {
		this.neighbor = v;
	}

	public void setIntersect(boolean b) {
		intersect = b;
	}
}
