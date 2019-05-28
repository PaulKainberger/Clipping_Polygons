package Polygon.greinerHorman;

import java.awt.geom.Point2D.Double;
import java.util.LinkedList;
import java.util.ListIterator;

import Polygon.Polygon;

public class GreinerHormanPolygon extends Polygon {

	public GreinerHormanPolygon() {
		vertices = new LinkedList<Double>();
	}
	
	public boolean addVertex(GreinerHormanVertex v, ListIterator<Double> i) {
		if(Polygon.pointsEqualEps(v, i.next()) || Polygon.pointsEqualEps(v, i.previous()))
			return false;
		i.add(v);
		return true;
	}
	
	public GreinerHormanPolygon(Polygon p) {
		vertices = new LinkedList<Double>();
		for(int i=0; i<p.getNumberVertices(); i++) {
			vertices.add(new GreinerHormanVertex(p.getVertex(i)));
		}
	}
}
