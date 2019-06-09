package Polygon;

import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;

/**
 * Used to bundle information on the intersection of two line(segments).
 * @author Philipp
 *
 */
public class LineIntersection {
	/**
	 * Intersection types of the intersection of two lines:
	 * - No intersection at all.
	 * - Intersection is one point.
	 * - Intersection is a line segment.
	 */
	public enum IntersectionType {
		NO,
		POINT,
		SEGMENT
	}

	/**
	 * True if line segments are parallel (i.e. have the same
	 * slope) and false otherwise.
	 */
	private boolean linesParallel;
	/**
	 * Intersection type.
	 */
	private IntersectionType type;
	/**
	 * The point of intersection if type is POINT.
	 */
	private Point2D.Double intersection;
	/**
	 * If type is SEGMENT this is one end point of the line segment.
	 */
	private Point2D.Double lineSegmentStart;
	/**
	 * If type is SEGMENT this is the second end point of the line segment.
	 */
	private Point2D.Double lineSegmentEnd;
	/**
	 * If type is POINT this is the ratio in [0,1] where the intersection point is located 
	 * w.r.t. the first line.
	 */
	private double lineRatio1;
	/**
	 * If type is POINT this is the ratio in [0,1] where the intersection point is located 
	 * w.r.t. the second line.
	 */
	private double lineRatio2;
	
	/**
	 * Constructor if intersection is one point.
	 * 
	 * @param intersection Point of intersection.
	 * @param parallel True if lines are parallel and false otherwise.
	 * @param lineRatio1 Ratio point for first line.
	 * @param lineRatio2 Ratio point for second line.
	 */
	public LineIntersection(Point2D.Double intersection, boolean parallel, double lineRatio1,
			                double lineRatio2) {
		this.intersection = intersection;
		this.type = IntersectionType.POINT;
		this.linesParallel = parallel;
		this.lineRatio1 = lineRatio1;
		this.lineRatio2 = lineRatio2;
	}
	
	/**
	 * Constructor if no intersection.
	 * 
	 * @param parallel True if lines are parallel and false otherwise.
	 */
	public LineIntersection(boolean parallel) {
		this.type = IntersectionType.NO;
		this.linesParallel = parallel;
	}
	
	/**
	 * Constructor if intersection is line segment.
	 * 
	 * @param lineSegmentStart Start point of line segment.
	 * @param lineSegmentEnd End point of line segment.
	 */
	public LineIntersection(Point2D.Double lineSegmentStart, Point2D.Double lineSegmentEnd) {
		this.type = IntersectionType.SEGMENT;
		this.linesParallel = true;
		this.lineSegmentStart = lineSegmentStart;
		this.lineSegmentEnd = lineSegmentEnd;
	}
	
	/**
	 * Getter for parallel lines
	 *
	 * @return True if lines are parallel and false otherwise.
	 */
	public boolean isParallel() {
		return linesParallel;
	}
	
	/**
	 * Setter for parallel lines.
	 * 
	 * @param linesParallel True if lines are parallel and false otherwise.
	 */
	
	public void setParallel(boolean parallel) {
		this.linesParallel = parallel;
	}
	
	/**
	 * Getter for type
	 *
	 * @return the type
	 */
	public IntersectionType getType() {
		return type;
	}
	
	/**
	 * Setter for type.
	 * 
	 * @param type the type to set
	 */
	public void setType(IntersectionType type) {
		this.type = type;
	}
	
	/**
	 * Getter for intersection
	 *
	 * @return the intersection
	 */
	public Point2D.Double getIntersection() {
		return intersection;
	}
	
	/**
	 * Setter for intersection.
	 * 
	 * @param intersection the intersection to set
	 */
	public void setIntersection(Point2D.Double intersection) {
		this.intersection = intersection;
	}
	
	/**
	 * Getter for lineSegmentStart
	 *
	 * @return the lineSegmentStart
	 */
	public Point2D.Double getLineSegmentStart() {
		return lineSegmentStart;
	}
	
	/**
	 * Setter for lineSegmentStart.
	 * 
	 * @param lineSegmentStart the lineSegmentStart to set
	 */
	public void setLineSegmentStart(Point2D.Double lineSegmentStart) {
		this.lineSegmentStart = lineSegmentStart;
	}
	
	/**
	 * Getter for lineSegmentEnd
	 *
	 * @return the lineSegmentEnd
	 */
	public Point2D.Double getLineSegmentEnd() {
		return lineSegmentEnd;
	}
	
	/**
	 * Setter for lineSegmentEnd.
	 * 
	 * @param lineSegmentEnd the lineSegmentEnd to set
	 */
	public void setLineSegmentEnd(Point2D.Double lineSegmentEnd) {
		this.lineSegmentEnd = lineSegmentEnd;
	}
	
	/**
	 * Getter for lineRatio1
	 *
	 * @return the lineRatio1
	 */
	public double getLineRatio1() {
		return lineRatio1;
	}
	
	/**
	 * Setter for lineRatio1.
	 * 
	 * @param lineRatio1 the lineRatio1 to set
	 */
	public void setLineRatio1(double lineRatio1) {
		this.lineRatio1 = lineRatio1;
	}
	
	/**
	 * Getter for lineRatio2
	 *
	 * @return the lineRatio2
	 */
	public double getLineRatio2() {
		return lineRatio2;
	}
	
	/**
	 * Setter for lineRatio2.
	 * 
	 * @param lineRatio2 the lineRatio2 to set
	 */
	public void setLineRatio2(double lineRatio2) {
		this.lineRatio2 = lineRatio2;
	}
	
	/**
	 * Get all the intersection points, i.e. the set is empty
	 * if there is no, contains one point if we have one intersection points and
	 * contains two points if the intersection is a line, containing the start- and
	 * the endpoint of the intersection.
	 * 
	 * @return The intersection points.
	 */
	public Set<Point2D.Double> getPoints() {
		Set<Point2D.Double> set = new HashSet<Point2D.Double>();
		if(getType() == LineIntersection.IntersectionType.POINT) {
			set.add(getIntersection());
		} else if(getType() == LineIntersection.IntersectionType.SEGMENT) {
			set.add(getLineSegmentStart());
			set.add(getLineSegmentEnd());
		}
		return set;
	}

	/** 
	 * Creates hash code of line intersection.
	 * 
	 * @return Hash code of line intersection.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((intersection == null) ? 0 : intersection.hashCode());
		long temp;
		temp = Double.doubleToLongBits(lineRatio1);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(lineRatio2);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((lineSegmentEnd == null) ? 0 : lineSegmentEnd.hashCode());
		result = prime * result + ((lineSegmentStart == null) ? 0 : lineSegmentStart.hashCode());
		result = prime * result + (linesParallel ? 1231 : 1237);
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	/**
	 * Checks if line intersections are equal.
	 * 
	 * @return True if they describe the same intersection and false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LineIntersection other = (LineIntersection) obj;
		if (intersection == null) {
			if (other.intersection != null)
				return false;
		} else if (!intersection.equals(other.intersection))
			return false;
		if (Double.doubleToLongBits(lineRatio1) != Double.doubleToLongBits(other.lineRatio1))
			return false;
		if (Double.doubleToLongBits(lineRatio2) != Double.doubleToLongBits(other.lineRatio2))
			return false;
		if (lineSegmentEnd == null) {
			if (other.lineSegmentEnd != null)
				return false;
		} else if (!lineSegmentEnd.equals(other.lineSegmentEnd))
			return false;
		if (lineSegmentStart == null) {
			if (other.lineSegmentStart != null)
				return false;
		} else if (!lineSegmentStart.equals(other.lineSegmentStart))
			return false;
		if (linesParallel != other.linesParallel)
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	
	/**
	 * Returns a string representation of the intersection.
	 * 
	 * @return String representation.
	 */
	@Override
	public String toString() {
		String rep;
		if(isParallel()) {
			rep = "Lines parallel.";
		} else {
			rep = "Lines not parallel.";
		}
		if(getType() == IntersectionType.NO) {
			rep += "No intersection.";
		} else if(getType() == IntersectionType.POINT) {
			rep += "Intersection in point " + getIntersection().toString() + ".";
		} else {
			rep += "Intersection is line segment from " + getLineSegmentStart().toString()
					+ " to " + getLineSegmentEnd().toString() + ".";
		}
		return rep;
	}
}
