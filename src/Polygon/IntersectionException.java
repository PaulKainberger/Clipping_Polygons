package Polygon;
/**
 * Exception which is thrown if the intersection of two shapes cannot be written as
 * finite set of points.
 * 
 * @author Philipp
 * @version 0.1
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
