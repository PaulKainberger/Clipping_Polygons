package Polygon;
/**
 * Exception which is thrown if the intersection of two shapes cannot be written as
 * finite set of points.
 * 
 * @author Philipp
 *
 */
@SuppressWarnings("serial")
public class IntersectionException extends Exception {
	/**
	 * Constructor of Intersection Exception.
	 */
	IntersectionException()
    {
		super("Infinitely many intersection points.");
    }
}
