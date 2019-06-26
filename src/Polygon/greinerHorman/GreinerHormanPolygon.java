package Polygon.greinerHorman;

import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.ListIterator;

import Polygon.Polygon;

/**
 * Represents a polygon in the data structure used by the Greiner-Horman algorithm.
 * Additionally to the coordinates of the points further information used by the algorithm is stored.
 * 
 * @author Jakob
 *
 */
public class GreinerHormanPolygon{
	
	private GreinerHormanVertex firstVertex;

	/**
	 * Add a vertex at a position given by the previous vertex.
	 * @param v The vertex that is added.
	 * @param i The iterator specifying the position of the vertex.
	 * @return Whether the vertex was added.
	 */
	public void addVertex(GreinerHormanVertex v, GreinerHormanVertex position) {
		if(firstVertex == null) {
			firstVertex = v;
			v.next = v;
			v.previous = v;
			return;
		}
		v.previous = position;
		v.next = position.next;
		position.next = v;
		v.next.previous = v;
	}
	
	public void addIntersectionVertex(GreinerHormanVertex v, GreinerHormanVertex position) {
		if (!v.isIntersect())
			return;
		GreinerHormanVertex newPosition = position;
		while(newPosition.next.intersect&&newPosition.next.alpha<v.alpha)
			newPosition = newPosition.next;
		addVertex(v,newPosition);
	}

	/**
	 * Constructor for a Greiner Horman polygon from a normal polygon.
	 * Converts the Arraylist of vertices in a Linked List of Greiner-Horman vertices. 
	 * @param p The polygon to be stored as Greiner-Horman polygon.
	 */
	public GreinerHormanPolygon(Polygon p) {
		addVertex(new GreinerHormanVertex(p.getVertex(0)),null);
		for(int i=1; i<p.getNumberVertices(); i++) {
			addVertex(new GreinerHormanVertex(p.getVertex(i)),firstVertex.previous);
		}
	}
	
	public GreinerHormanVertex getFirstVertex() {
		return firstVertex;
	}

	
	public Polygon toPolygon() {
		Polygon p = new Polygon();
		for(GreinerHormanVertex current = firstVertex; current.next !=firstVertex; current=current.next) {
			p.addVertex(current);
		}
		p.addVertex(firstVertex.previous);
		return p;
	}
	
	public boolean hasIntersection() {
		GreinerHormanVertex v = firstVertex.next;
		while(v!=firstVertex) {
			if(v.intersect)
				return true;
			v= v.next;
		}
		return false;
	}
}
