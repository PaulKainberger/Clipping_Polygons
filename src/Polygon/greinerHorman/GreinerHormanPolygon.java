package Polygon.greinerHorman;

import java.awt.geom.Point2D.Double;
import java.util.LinkedList;
import java.util.ListIterator;

import Polygon.Polygon;

/**
 * Represents a polygon in the data structure used by the Greiner-Horman algorithm.
 * Additionally to the coordinates of the points further information used by the algorithm is stored.
 * 
 * @author Jakob
 *
 */
public class GreinerHormanPolygon extends Polygon {
	
	/**
	 * Constructor using a Linked List for the vertices.
	 */
	public GreinerHormanPolygon() {
		vertices = new LinkedList<Double>();
	}

	/**
	 * Add a vertex at a position given by a list iterator.
	 * @param v The vertex that is added.
	 * @param i The iterator specifying the position of the vertex.
	 * @return Whether the vertex was added.
	 */
	public boolean addVertex(GreinerHormanVertex v, ListIterator<Double> i) {
		if(Polygon.pointsEqualEps(v, i.next()) || Polygon.pointsEqualEps(v, i.previous()))
			return false;
		i.add(v);
		return true;
	}

	/**
	 * Constructor for a Greiner Horman polygon from a normal polygon.
	 * Converts the Arraylist of vertices in a Linked List of Greiner-Horman vertices. 
	 * @param p The polygon to be stored as Greiner-Horman polygon.
	 */
	public GreinerHormanPolygon(Polygon p) {
		vertices = new LinkedList<Double>();
		for(int i=0; i<p.getNumberVertices(); i++) {
			vertices.add(new GreinerHormanVertex(p.getVertex(i)));
		}
	}
}
