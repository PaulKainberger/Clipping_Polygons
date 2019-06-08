package Polygon;

import java.awt.geom.Point2D;

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
}
