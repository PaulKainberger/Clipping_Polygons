import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Represents a polygon defined by vertices which are two dimensional double points.
 * The first and the last point is connected by an edge.
 * Two adjacent vertices are different.
 * 
 * @author Philipp
 *
 */
public class Polygon {
	/**
	 * Internal representation of list of vertices.
	 */
	private ArrayList<Point2D.Double> vertices;
	
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
		if(!isEmpty() && ( vertex.equals(getVertex(  Math.floorMod(index, getNumberVertices()) )) || 
						   vertex.equals(getVertex(  Math.floorMod(index-1, getNumberVertices()) )))) {
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
	 * Getter for vertex at given index.
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
	 * Used to get a canonical vertex of the polygon, namely the vertex with the smallest x coordinate,
	 * and if that is not unique, the one with the smallest y coordinate among them.
	 * 
	 * @return The index of this least vertex or -1 if the polygon is empty.
	 */
	private int getLeastVertex() {
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
	 * Checks if two objects are the same.
	 * Two polygons are considered the same if they contain
	 * the same vertices in the same order.
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
					if(!secPolygon.getVertex( (leastIndexSec + i) % numVertices).equals(getVertex((leastIndexThis + i) % numVertices))) {
						return false;
					}
				}
				return true;
			}
		}
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
}
