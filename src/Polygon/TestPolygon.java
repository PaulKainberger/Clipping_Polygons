import static org.junit.Assert.*;

import java.awt.geom.Point2D;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestPolygon {
	Polygon poly;
	Point2D.Double p1;
	Point2D.Double p2;
	Point2D.Double p3;
	
	@Before
	public void setUp() throws Exception {
		poly = new Polygon();
		p1 = new Point2D.Double(1.3,4.5);
		p2 = new Point2D.Double(8.9,4.2);
		p3 = new Point2D.Double(3.1,2);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPolygon() {
		assertTrue(poly.isEmpty());
	}

	@Test
	public void testPolygonDoubleArray() {
		Point2D.Double[] vertices = {p1, p2, p3};
		Polygon poly2 = new Polygon(vertices);
		
		assertEquals(p2, poly2.getVertex(1));
		assertEquals(3, poly2.getNumberVertices());
	}

	@Test
	public void testAddVertexDouble() {
		poly.addVertex(p1);
		poly.addVertex(p3);
		assertEquals(p3, poly.getVertex(1));
	}

	@Test
	public void testAddVertexDoubleInt() {
		poly.addVertex(p1, 0);
		poly.addVertex(p3, 0);
		poly.addVertex(p2, 1);
		assertEquals(p3, poly.getVertex(0));
		assertEquals(p2, poly.getVertex(1));
	}

	@Test
	public void testGetNumberVertices() {
		assertEquals(0, poly.getNumberVertices());
		poly.addVertex(p1);
		poly.addVertex(p3);
		poly.addVertex(p2);
		assertEquals(3, poly.getNumberVertices());
	}

	@Test
	public void testOrientedClockwise() {
		poly.addVertex(5, 0);
		poly.addVertex(6, 4);
		poly.addVertex(4, 5);
		poly.addVertex(1, 5);
		poly.addVertex(1, 0);
		assertFalse(poly.orientedClockwise());
	}
	
	@Test
	public void testOrientedClockwise2() {
		poly.addVertex(1, 0);
		poly.addVertex(1, 5);
		poly.addVertex(4, 5);
		poly.addVertex(6, 4);
		poly.addVertex(5, 0);
		
		assertTrue(poly.orientedClockwise());
	}

	@Test
	public void testContains() {
		poly.addVertex(p1);
		poly.addVertex(p2);
		poly.addVertex(p3);
		
		System.out.print(poly.toString());
		
		assertTrue(poly.contains(new Point2D.Double(3, 4)));
		assertFalse(poly.contains(new Point2D.Double(0, 0)));
	}

	@Test
	public void testEqualsObject() {
		Polygon poly2 = new Polygon();
		assertEquals(poly, poly2);
		poly.addVertex(p1);
		poly.addVertex(p2);
		assertNotEquals(poly, poly2);
		poly2.addVertex(p2);
		assertNotEquals(poly, poly2);
		poly2.addVertex(p1);
		assertEquals(poly, poly2);
	}

}
