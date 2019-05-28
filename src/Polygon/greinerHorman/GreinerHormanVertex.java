package Polygon.greinerHorman;

import java.awt.geom.Point2D.Double;
import java.util.ListIterator;

class GreinerHormanVertex extends Double {
	
	boolean intersect;
	boolean entryExit;
	ListIterator<Double> neighbor;
	double alpha;
	

	public GreinerHormanVertex() {
		super(0,0);
	}

	public GreinerHormanVertex(double x, double y) {
		super(x, y);
	}

	public GreinerHormanVertex(Double v) {
		super(v.getX(),v.getY());
	}
	
	public GreinerHormanVertex(double x, double y, boolean intersect, 
			boolean entryExit, ListIterator<Double> neighbor, double alpha) {
		super(x,y);
		this.intersect = intersect;
		this.entryExit = entryExit;
		this.neighbor = neighbor;
		this.alpha = alpha;
	}

	public boolean isIntersect() {
		return intersect;
	}

	public boolean isEntryExit() {
		return entryExit;
	}

	public ListIterator<Double> getNeighbor() {
		return neighbor;
	}

	public double getAlpha() {
		return alpha;
	}
}
