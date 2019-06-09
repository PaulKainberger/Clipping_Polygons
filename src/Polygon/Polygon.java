package Polygon;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

/**
 * Represents a polygon defined by vertices which are two dimensional double points.
 * The first and the last point is connected by an edge.
 * Two adjacent vertices are different. Two points are considered equal if they
 * only differ by eps=0.0000001. This avoids double points arising from
 * rounding during numerical calculations. However, it is nevertheless suggested to
 * make sure that vertices are not that close because methods like contains() might give
 * wrong results then.
 * 
 * @author Philipp
 * @version 0.1
 */
public class Polygon {
	/**
	 * Internal representation of list of vertices.
	 */
	protected List<Point2D.Double> vertices;
	/**
	 * Epsilon used for numeric computations.
	 */
	static final private double eps = 0.0000001; 

	/**
	 * Creates an empty polygon.
	 */
	public Polygon() {
		vertices = new ArrayList<Point2D.Double>();
	}

	/**
	 * Creates a polygon with the given vertices.
	 * 
	 * @param vertices The vertices of the new polygon.
	 */
	public Polygon(Point2D.Double[] vertices) {
		this();
		for(int i = 0; i < vertices.length; i++) {
			addVertex(vertices[i]);
		}
	}

	/**
	 * Copy-constructor of Polygon class.
	 * 
	 * @param polygon Polygon which should be copied.
	 */
	public Polygon(Polygon polygon) {
		vertices = new ArrayList<Point2D.Double>();
		for(Point2D.Double vertex : polygon.vertices){
			Point2D.Double copy = (Point2D.Double) vertex.clone();
			vertices.add(copy);
		}
	}

	public ListIterator<Point2D.Double> getIterator() {
		return vertices.listIterator();
	}

	/**
	 * Adds a vertex to the end of the polygon. The first vertex has the index 0.
	 * If the vertex violates the condition that
	 * two adjacent vertices are not the same then it is not 
	 * added.
	 * 
	 * @param vertex The vertex which is added to the polygon.
	 * @return True if the vertex has been added and false otherwise.
	 */
	public boolean addVertex(Point2D.Double vertex) {
		return addVertex(vertex, vertices.size());
	}

	/**
	 * Adds a vertex to the end of the polygon. The first vertex has the index 0.
	 * If the vertex violates the condition that
	 * two adjacent vertices are not the same then it is not 
	 * added.
	 * 
	 * @param x The x coordinate of the vertex.
	 * @param y The y coordinate of the vertex.
	 * @return True if the vertex has been added and false otherwise.
	 */
	public boolean addVertex(double x, double y) {
		return addVertex(new Point2D.Double(x,  y));
	}

	/**
	 * Adds a vertex at a given index of the polygon. The first vertex has the index 0.
	 * If the vertex violates the condition that
	 * two adjacent vertices are not the same then it is not 
	 * added.
	 * 
	 * @param vertex The vertex which is added to the polygon.
	 * @param index The index at which the vertex will be
	 * after the insertion, i.e. the vertex will be added before 
	 * the vertex with the given index.
	 * @return True if the vertex has been added and false otherwise.
	 * @throws If the given index is out of range (i.e. either 
	 * index < 0 or index > numberVertices an IndexOutOfBoundsException
	 * will be thrown.
	 */
	public boolean addVertex(Point2D.Double vertex, int index) {
		if(index < 0 || index > getNumberVertices()) {
			throw new IndexOutOfBoundsException();
		}

		// check if vertex does violate rule that
		// two adjacent vertices are not equal
		if(!isEmpty() && ( pointsEqualEps(getVertex( Math.floorMod(index, getNumberVertices()) ), vertex) || 
				pointsEqualEps(getVertex( Math.floorMod(index-1, getNumberVertices()) ), vertex))) {
			return false;
		} 

		vertices.add(index, vertex);
		return true;
	}

	/**
	 * Adds a vertex at a given index of the polygon. The first vertex has the index 0.
	 * If the vertex violates the condition that
	 * two adjacent vertices are not the same then it is not 
	 * added.
	 * 
	 * @param x The x coordinate of the vertex.
	 * @param y The y coordinate of the vertex.
	 * @param index The index at which the vertex will be
	 * after the insertion, i.e. the vertex will be added before 
	 * the vertex with the given index.
	 * @return True if the vertex has been added and false otherwise.
	 * @throws If the given index is out of range (i.e. either 
	 * index < 0 or index > numberVertices an IndexOutOfBoundsException
	 * will be thrown.
	 */
	public boolean addVertex(double x, double y, int index) {
		return addVertex(new Point2D.Double(x, y), index);
	}

	/**
	 * Getter for the number of vertices in the polygon. The first vertex has the index 0.
	 * 
	 * @return The number of vertices in the polygon.
	 */
	public int getNumberVertices() {
		return vertices.size();
	}

	/**
	 * Checks if the polygon is empty, i.e. there
	 * are no vertices in the polygon.
	 * 
	 * @return True if it is empty and false otherwise.
	 */
	public boolean isEmpty() {
		return getNumberVertices() == 0;
	}

	/**
	 * Checks if the polygon is only one point.
	 * 
	 * @return True if it is exactly one point and false otherwise.
	 */
	public boolean isPoint() {
		return getNumberVertices() == 1;
	}

	/**
	 * Getter for vertex at given index. Indices start with 0,
	 * so the last vertex is at index getNumberVertices() - 1
	 * 
	 * @param index Index of the vertex we want to get.
	 * @return The vertex at given index.
	 * @throws If the given index is out of range (i.e. either 
	 * index < 0 or index >= numberVertices an IndexOutOfBoundsException
	 * will be thrown.
	 */
	public Point2D.Double getVertex(int index) {
		return vertices.get(index);
	}

	/**
	 * Computes the bounds of the polygon. The top-left corner has the smallest x and y
	 * coordinates. If the polygon is empty null will be returned. If it only contains one 
	 * vertex then a rectangle with 0 size at that point will be returned. 
	 * 
	 * @return The bounding box of the polygon.
	 */
	public Rectangle2D.Double getBounds() {
		if(isEmpty()) {
			return null;
		}

		// indices of vertex with smallest/largest x/y coordinates
		int minX = 0;
		int minY = 0;
		int maxX = 0;
		int maxY = 0;

		for(int i = 0; i < getNumberVertices(); i++) {
			if(getVertex(i).getX() < getVertex(minX).getX()) {
				minX = i;
			}
			if(getVertex(i).getY() < getVertex(minY).getY()) {
				minY = i;
			}
			if(getVertex(maxX).getX() < getVertex(i).getX()) {
				maxX = i;
			}
			if(getVertex(maxY).getY() < getVertex(i).getY()) {
				maxY = i;
			}
		}
		double width = getVertex(maxX).getX() - getVertex(minX).getX();
		double height = getVertex(maxY).getY() - getVertex(minY).getY();

		return new Rectangle2D.Double(getVertex(minX).getX(), getVertex(minY).getY(), width, height);
	}

	/**
	 * Used to get a canonical vertex of the polygon, namely the vertex with the smallest x coordinate,
	 * and if that is not unique, the one with the smallest y coordinate among them.
	 * 
	 * @return The index of this least vertex or -1 if the polygon is empty.
	 */
	int getLeastVertex() {
		if(isEmpty()) {
			return -1;
		} else {
			int leastXIndex = 0;
			for(int i = 1; i < vertices.size(); i++) {
				if(vertices.get(i).getX() < vertices.get(leastXIndex).getX() ||
						(vertices.get(i).getX() == vertices.get(leastXIndex).getX() && 
						vertices.get(i).getY() < vertices.get(leastXIndex).getY())) {
					leastXIndex = i;
				}
			}
			return leastXIndex;
		}
	}

	/**
	 * Checks if the polygon is oriented clockwise.
	 * Implements the method suggested in: https://stackoverflow.com/a/1165943/7698457
	 * Edge cases (e.g. a single point or empty polygon) are considered as not clockwise.
	 * True if the vertex is an entry vertex, false if it is an exit vertex.
	 * 
	 * @return True if the polygon is oriented clockwise and false otherwise.
	 */
	public boolean orientedClockwise() {	 
		// degenerate case, only one point or empty
		if(getNumberVertices() < 2) {
			return false;
		}

		double area = 0;
		for(int i = 0; i < vertices.size() - 1; i++) {
			Point2D.Double vertex1 = vertices.get(i);
			Point2D.Double vertex2 = vertices.get(i+1);
			area += (vertex2.getX() - vertex1.getX())*(vertex2.getY() + vertex1.getY());
		}
		return area > 0;
	}

	/**
	 * Checks if given point is inside the polygon. We use the even-odd (winding number) rule
	 * for determining this for self-intersecting polygons, cf. 
	 * https://en.wikipedia.org/wiki/Even%E2%80%93odd_rule
	 * A point on the polygon (i.e. a vertex or on an edge) itself is considered inside.
	 * 
	 * @param point The point for which we check if it is inside.
	 * @return True if the point is inside the polygon and false otherwise.
	 */
	public boolean contains(Point2D.Double point) {
		int j = vertices.size() - 1;
		boolean pointInside = false;
		for(int i = 0; i < vertices.size(); i++) {
			Point2D.Double vi = vertices.get(i);
			Point2D.Double vj = vertices.get(j);
			if( (vi.getY() > point.getY()) != (vj.getY() > point.getY()) &&
					(point.getX() < vi.getX() + (vj.getX() - vi.getX())*(point.getY() - vi.getY()) / (vj.getY() - vi.getY()))) {
				pointInside = !pointInside;
			}
			j = i;
		}
		return pointInside;
	}

	/**
	 * Checks if a given point is in a line segment. Use the idea given in:
	 * https://stackoverflow.com/questions/328107/how-can-you-determine-a-point-is-between-two-other-points-on-a-line-segment
	 * 
	 * @param pointStart Start point of line segment.
	 * @param pointEnd End point of line segment.
	 * @param point Point for which we want to check if it is inside.
	 * @return True if the point is inside and false otherwise.
	 */
	static boolean isBetween(Point2D.Double pointStart, Point2D.Double pointEnd,
			Point2D.Double point) {
		double dx13 = point.getX() - pointStart.getX();
		double dy13 = point.getY() - pointStart.getY();
		double dx12 = pointEnd.getX() - pointStart.getX();
		double dy12 = pointEnd.getY() - pointStart.getY();

		double cross = dy13*dx12 - dx13*dy12;
		if(Math.abs(cross) > eps) {
			return false;
		}

		double dot = dx13*dx12 + dy13*dy12;
		if(dot < 0) {
			return false;
		}

		double sqLength = dx12*dx12 + dy12*dy12;
		if(dot > sqLength) {
			return false;
		}

		return true;
	}

	/**
	 * Computes the points of intersections of two lines given by their endpoints.
	 * We use the method mentioned in
	 * https://en.wikipedia.org/wiki/Line%E2%80%93line_intersection
	 * 
	 * @param point1Start Start point of first line.
	 * @param point1End End point of first line.
	 * @param point2Start Start point of second line.
	 * @param point2End	 End point of second line.
	 * @return An object representing the line intersection.
	 */
	static LineIntersection intersectLines(Point2D.Double point1Start, Point2D.Double point1End,
			Point2D.Double point2Start, Point2D.Double point2End) {
		return intersectLines(point1Start, point1End, point2Start, point2End, false);
	}

	/**
	 * Computes the points of intersections of two lines given by their endpoints.
	 * We use the method mentioned in
	 * https://en.wikipedia.org/wiki/Line%E2%80%93line_intersection
	 * 
	 * @param point1Start Start point of first line.
	 * @param point1End End point of first line.
	 * @param point2Start Start point of second line.
	 * @param point2End	 End point of second line.
	 * @param secondLineProper If true the second line will be assumed to be an entire line 
	 * (not only a segment) defined by the two given points, and if false it will be assumed
	 * to be really a line segment.
	 * @return An object representing the line intersection.
	 */
	static LineIntersection intersectLines(Point2D.Double point1Start, Point2D.Double point1End,
			Point2D.Double point2Start, Point2D.Double point2End,
			boolean secondLineProper) {
		double dx1 = point1Start.getX() - point1End.getX();
		double dx2 = point2Start.getX() - point2End.getX();
		double dy1 = point1Start.getY() - point1End.getY();
		double dy2 = point2Start.getY() - point2End.getY();
		double denom = dx1*dy2 - dy1*dx2;

		// segments are not parallel 
		if(Math.abs(denom) > eps) {
			double dx12 = point1Start.getX() - point2Start.getX();
			double dy12 = point1Start.getY() - point2Start.getY();
			double t = (dx12*dy2 - dy12*dx2)/denom;
			double u = -(dx1*dy12 - dy1*dx12)/denom;
			if(0 <= t && t <= 1) {
				if( (0 <= u && u <= 1) || secondLineProper) {
					Point2D.Double intersection =  new Point2D.Double(point1Start.getX() - t*dx1,
							point1Start.getY() - t*dy1);
					return new LineIntersection(intersection, false, t, u);
				} else {
					return new LineIntersection(false);
				}
			} else {
				return new LineIntersection(false);
			}
		} else {			
			// segments actually intersect in one point
			if((point1Start.equals(point2Start) && !isBetween(point2Start, point2End, point1End) && !isBetween(point1Start, point1End, point2End)) || 
					(point1Start.equals(point2End) && !isBetween(point2Start, point2End, point1End) && !isBetween(point1Start, point1End, point2Start))) {
				// segments "connect" at point1Start, but do not overlap otherwise
				Point2D.Double intersection = (Point2D.Double) point1Start.clone();
				return new LineIntersection(intersection, true, 0, 1);
			} else if((point1End.equals(point2Start) && !isBetween(point2Start, point2End, point1Start) && !isBetween(point1Start, point1End, point2End)) || 
					(point1End.equals(point2End) && !isBetween(point2Start, point2End, point1Start) && !isBetween(point1Start, point1End, point2Start))) {
				// segments "connect" at point1End, but do not overlap otherwise
				Point2D.Double intersection = (Point2D.Double) point1End.clone();
				return new LineIntersection(intersection, true, 1, 0);
			} 

			// segments overlap
			if(isBetween(point2Start, point2End, point1Start) || isBetween(point2Start, point2End, point1End)) {
				// TODO: Compute points of start and end of intersection!
				Point2D.Double lineSegmentStart = null;
				Point2D.Double lineSegmentEnd = null;
				return new LineIntersection(lineSegmentStart, lineSegmentEnd);
			}

			// segments do not overlap
			return new LineIntersection(false);
		}
	}

	/**
	 * Checks if a point is to the left of a line defined defined by two points, going through
	 * edge1 and edge2 (coming from the direction of edge1).
	 * 
	 * @param point Point for which we want to check the position.
	 * @param edge1 One point of the line.
	 * @param edge2 Second point of the line.
	 * @return True if point is to the right of the line. 
	 */
	public static boolean inside(Point2D.Double point, Point2D.Double edge1, Point2D.Double edge2) {
		return (point.getX() - edge2.getX()) * (edge1.getY() - edge2.getY()) >
		(point.getY() - edge2.getY()) * (edge1.getX() - edge2.getX());
	}

	/**
	 * Checks if two points are equal up to the epsilon of the class, i.e.
	 * their x and y coordinate only differ by the epsilon in both directions.
	 * 
	 * @param point1 First point.
	 * @param point2 Second point.
	 * @return True if they are equal up to an epsilon and false otherwise.
	 */
	protected static boolean pointsEqualEps(Point2D.Double point1, Point2D.Double point2) {
		if(point1.getX() - eps < point2.getX() && point2.getX() < point1.getX() + eps &&
				point1.getY() - eps < point2.getY() && point2.getY() < point1.getY() + eps) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks if set of points contains a point up to an epsilon.
	 * 
	 * @param points Set of points for which we want to check if it contains other point.
	 * @param point Point for which we want to check if it is in set.
	 * @return True if the point is in the set of points with coordinates considered up
	 * to an epsilon and false otherwise.
	 */
	static boolean containsPoint(Set<Point2D.Double> points, Point2D.Double point) {
		for(Point2D.Double p : points) {
			if(pointsEqualEps(p, point)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Removes points which are in the set twice, up to an epsilon. 
	 * The points of the set are not cloned, so if they are
	 * used after this method, the set points should be cloned before.
	 * 
	 * @param points The points for which we want to remove duplicates.
	 * @return The set of points with duplicates removed.
	 */
	static Set<Point2D.Double> removeDuplPoints(Set<Point2D.Double> points) {
		Set<Point2D.Double> newPoints = new HashSet<Point2D.Double>();
		for(Point2D.Double p : points) {
			if(!containsPoint(newPoints, p)) {
				newPoints.add(p);
			}
		}
		return newPoints;
	}

	/**
	 * Computes the points of intersections of this polygon with a second given polygon.
	 * If two edges agree on a line segment we add both end points to the intersection points.
	 * We use the naive approach of intersecting all edges of one polygon with all
	 * edges of the other polygon. 
	 * 
	 * @param polygon The second polygon we want to compute the intersection points with.
	 * @return A set of the intersection points.
	 */
	public Set<Point2D.Double> intersect(Polygon polygon) {
		Set<Point2D.Double> points = new HashSet<Point2D.Double>();
		// degenerate case, one is empty
		if(polygon.isEmpty() || isEmpty()) {
			return points;
		}

		// degenerate case, one does not have an edge
		if(getNumberVertices() == 1) {
			Point2D.Double singlePoint = getVertex(0);
			if(polygon.getNumberVertices() == 1) {
				if(singlePoint.equals(polygon.getVertex(0))) {
					points.add(singlePoint);
				} 
				return points;
			} else {
				int numVert = polygon.getNumberVertices();
				for(int i = 0; i < numVert; i++) {
					LineIntersection point = intersectLines(singlePoint, singlePoint, polygon.getVertex(i), polygon.getVertex((i+1) % numVert));
					points.addAll(point.getPoints());
				}
				return removeDuplPoints(points);
			}
		} else if(polygon.getNumberVertices() == 1) {
			return polygon.intersect(this);
		}

		// general case, both have at least an edge
		int numVertThis = getNumberVertices();
		int numVertPoly = polygon.getNumberVertices();
		for(int i = 0; i < numVertPoly; i++) {
			for(int j = 0; j < numVertThis ; j++) {
				LineIntersection point = intersectLines(getVertex(j), getVertex((j+1) % numVertThis), 
						polygon.getVertex(i), polygon.getVertex((i+1) % numVertPoly));
				points.addAll(point.getPoints());
			}
		}
		return removeDuplPoints(points);
	}

	/**
	 * Checks if the polygon is self-intersecting, i.e. if two edges
	 * intersect in different points than the vertices of the polygon.
	 * We use a slow naive approach and check all possible combinations.
	 * If two edges agree in some section we also call the polygon self-intersecting.
	 * This is e.g. the case for polygons with only two vertices.
	 * 
	 * @return True if the polygon is self-intersecting and false otherwise.
	 */
	public boolean isSelfIntersecting() {
		// degenerate cases
		int numVertices = getNumberVertices();
		if(numVertices < 2) {
			return false;
		} else if(numVertices == 2) {
			return true;
		}

		// intersect all possible edges (also adjacent because we can
		// have degenerate cases where e.g. three vertices are collinear)
		for(int i = 0; i < numVertices; i++) {
			for(int j = i + 1; j < numVertices ; j++) {
				LineIntersection point = intersectLines(getVertex(i), getVertex((i+1) % numVertices), 
						                                getVertex(j), getVertex((j+1) % numVertices));
				// there is an intersection point and the two edges are
				// not adjacent (otherwise there intersection point is just the vertex)
				if(point.getType() != LineIntersection.IntersectionType.POINT && i + 1 != j && Math.floorMod(i - 1, numVertices) != j ) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks if the polygon is convex.
	 * The algorithm assumes that the polygon is not self-intersecting,
	 * so one should use isSelfIntersecting() prior to using isConvex().
	 * Furthermore the algorithm assumes that three adjacent vertices are not collinear, 
	 * i.e. that an interior angle cannot be 2 pi.
	 * Degenerate cases with less than 3 vertices are considered not convex.
	 * We use the method suggested in: https://stackoverflow.com/a/45372025/7698457
	 * 
	 * @return True if the polygon is convex and false otherwise.
	 */
	public boolean isConvex() {
		int numVertices = getNumberVertices();
		if(numVertices < 3) {
			return false;
		}

		Point2D.Double oldVertex = getVertex(numVertices - 2);
		Point2D.Double newVertex = getVertex(numVertices - 1);

		double newDirection = Math.atan2(newVertex.getY() - oldVertex.getY(), newVertex.getX() - oldVertex.getX());
		double angleSum = 0;
		double oldDirection;
		double orientation = 1;

		for(int i = 0; i < numVertices; i++) {
			oldVertex = newVertex;
			oldDirection = newDirection;
			newVertex = getVertex(i);
			newDirection = Math.atan2(newVertex.getY() - oldVertex.getY(), newVertex.getX() - oldVertex.getX());
			double angle = newDirection - oldDirection;
			if(angle <= -Math.PI) {
				angle += 2*Math.PI;
			} else if(angle > Math.PI) {
				angle -= 2*Math.PI;
			}
			if(i == 0) {
				if(angle == 0) {
					return false;
				}
				if(angle > 0) {
					orientation = 1;
				} else {
					orientation = -1;
				}
			} else if(orientation*angle <= 0) {
				return false;
			}
			angleSum += angle;
		}
		return Math.abs(Math.round(angleSum/(2*Math.PI))) == 1;
	}

	/**
	 * Checks if two objects are the same.
	 * Two polygons are considered the same if they contain 
	 * the exact (not up to epsilon) same vertices in the same order.
	 * 
	 * @param polygon The second polygon which we want to check if it 
	 * is equal to this.
	 * @return True if they can be considered equal and false otherwise.
	 */
	@Override
	public boolean equals(Object polygon) {
		if(this == polygon) {
			return true;
		} else if(polygon == null || polygon.getClass() != this.getClass()) {
			return false;
		} else {
			Polygon secPolygon = (Polygon) polygon;
			if(secPolygon.getNumberVertices() != getNumberVertices()) {
				return false;
			} else {
				int numVertices = getNumberVertices();
				int leastIndexSec = secPolygon.getLeastVertex();
				int leastIndexThis = getLeastVertex();
				for(int i = 0; i < getNumberVertices(); i++) {
					if(!(secPolygon.getVertex( (leastIndexSec + i) % numVertices).equals(getVertex((leastIndexThis + i) % numVertices)))) {
						return false;
					}
				}
				return true;
			}
		}
	}

	/**
	 * Returns the hash code of the polygon. The code can differ if polygons are equal
	 * up to an epsilon, but if they are exactly equal it will be the same.
	 * 
	 * @return Hash code of polygon.
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		int numVertices = getNumberVertices();
		int least = getLeastVertex();
		for(int i = 0; i < numVertices; i++) {
			hash += getVertex((i + least) % numVertices).hashCode();
		}
		return hash;
	}

	/**
	 * Returns a string representation of this polygon as list of vertices.
	 * 
	 * @return String representation of the polygon.
	 */
	@Override
	public String toString() {
		String rep = "";
		for(int i = 0; i < vertices.size(); i++) {
			rep += "(" + String.valueOf(vertices.get(i).getX()) + "," +  String.valueOf(vertices.get(i).getY()) + ")";
			if(i != vertices.size() - 1) {
				rep += ",";
			}
		}
		return rep;
	}

	/**
	 * Removes all vertices from the polygon, i.e. sets the polygon to the empty polygon.
	 */
	public void clear() {
		vertices.clear();
	}

	private int[] getXintValues() {
		int[] x = new int[getNumberVertices()];
		for(int i=0; i<getNumberVertices(); i++) {
			x[i] = (int)getVertex(i).getX();
		}
		return x;
	}

	private int[] getYintValues() {
		int[] y = new int[getNumberVertices()];
		for(int i=0; i<getNumberVertices(); i++) {
			y[i] = (int)getVertex(i).getY();
		}
		return y;
	}

	/**
	 * Draws a polygon without the edge connecting the last point to the first.
	 * 
	 * @param g The graphics object on which the polygon is drawn.
	 * @param c The color of the polygon.
	 */
	public void drawIncomplete(Graphics2D g, Color c) {
		g.setColor(c);
		for(int i=0; i<getNumberVertices()-1; i++) {
			g.drawLine((int)getVertex(i).getX(), (int)getVertex(i).getY(), (int)getVertex(i+1).getX(), (int)getVertex(i+1).getY());
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.2F));
			g.fillPolygon(getXintValues(), getYintValues(), getNumberVertices());
		}
	}

	public void draw(Graphics2D g, Color c) {
		drawIncomplete(g,c);
		g.drawLine((int)getVertex(getNumberVertices()-1).getX(),(int)getVertex(getNumberVertices()-1).getY(),(int)getVertex(0).getX(),(int)getVertex(0).getY());
	}
}
