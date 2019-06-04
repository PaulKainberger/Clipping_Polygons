package Polygon;
import static org.junit.Assert.*;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Junit tests for Polygon class.
 * @author Philipp
 * @version 0.1
 *
 */
public class TestPolygon {
	Polygon poly;
	Polygon poly2;
	Point2D.Double p1;
	Point2D.Double p2;
	Point2D.Double p3;
	
	@Before
	public void setUp() throws Exception {
		poly = new Polygon();
		p1 = new Point2D.Double(1.3,4.5);
		p2 = new Point2D.Double(8.9,4.2);
		p3 = new Point2D.Double(3.1,2);
		poly2 = new Polygon();
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
	public void testAddVertexDupl() {
		poly.addVertex(p1);
		poly.addVertex(p1);
		assertEquals(1, poly.getNumberVertices());
		poly.addVertex(p2);
		poly.addVertex(p3);
		poly.addVertex(p2, 1);
		assertEquals(3, poly.getNumberVertices());
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
	public void testOrientedClockwiseTrivial() {
		assertFalse(poly.orientedClockwise());
		poly.addVertex(p1);
		assertFalse(poly.orientedClockwise());
	}

	@Test
	public void testContains() {
		poly.addVertex(p1);
		poly.addVertex(p2);
		poly.addVertex(p3);
				
		assertTrue(poly.contains(new Point2D.Double(3, 4)));
		assertFalse(poly.contains(new Point2D.Double(0, 0)));
	}
	
	@Test
	public void testContainsStar() {
		// https://www.wolframalpha.com/input/?i=Polygon((-1,-1),(0,1),(1,-1),(-1,0.5),(1,0.5))
		poly.addVertex(1, -1);
		poly.addVertex(0, 1);
		poly.addVertex(1, -1);
		poly.addVertex(-1, 0.5);
		poly.addVertex(1, 0.5);
		
		assertFalse(poly.contains(new Point2D.Double(0, 0)));
	}
	
	@Test
	public void testContainsBorder() {
		poly.addVertex(0, 0);
		poly.addVertex(0, 1);
		poly.addVertex(1, 0);
		
		assertTrue(poly.contains(new Point2D.Double(0, 0)));
		assertTrue(poly.contains(new Point2D.Double(0, 0.5)));
		assertFalse(poly.contains(new Point2D.Double(1, 1)));
	}

	@Test
	public void testEqualsObject() {
		assertEquals(poly, poly2);
		poly.addVertex(p1);
		poly.addVertex(p2);
		assertNotEquals(poly, poly2);
		poly2.addVertex(p2);
		assertNotEquals(poly, poly2);
		poly2.addVertex(p1);
		assertEquals(poly, poly2);
	}
	
	@Test
	public void testIntersectNormal() {
		// cf. https://www.wolframalpha.com/input/?i=Polygon((1.3,4.5),(8.9,4.2),(3.1,2.0)),+Polygon((2.0,2.0),(2.0,6.0),(6.0,2.0))
		poly.addVertex(p1);
		poly.addVertex(p2);
		poly.addVertex(p3);
		poly2.addVertex(2, 2);
		poly2.addVertex(2, 6);
		poly2.addVertex(6, 2);
		try {
			Set<Point2D.Double> points = poly.intersect(poly2);
			assertEquals(5, points.size());
			assertTrue(Polygon.containsPoint(points, new Point2D.Double(3.1, 2.0)));
			assertTrue(Polygon.containsPoint(points, new Point2D.Double(2.0, 3.5277777777777777)));
			assertTrue(Polygon.containsPoint(points, new Point2D.Double(2.0, 4.472368421052631)));
			assertTrue(Polygon.containsPoint(points, new Point2D.Double(3.5904109589041093, 4.409589041095891)));
			assertTrue(Polygon.containsPoint(points, new Point2D.Double(5.2025, 2.7975)));
		} catch (IntersectionException e) {
			fail("Infinitely many intersection points.");
		}
	}

	@Test
	public void testIntersectEmpty() {
		// cf. https://www.wolframalpha.com/input/?i=Polygon((1.3,4.5),(8.9,4.2),(3.1,2.0)),+Polygon((0.0,0.0),(0.0,10.0),(10.0,10.0),(10.0,0.0))
		poly.addVertex(p1);
		poly.addVertex(p2);
		poly.addVertex(p3);
		poly2.addVertex(0, 0);
		poly2.addVertex(0, 10);
		poly2.addVertex(10, 10);
		poly2.addVertex(10, 0);
		try {
			Set<Point2D.Double> points = poly.intersect(poly2);
			assertTrue(points.isEmpty());
		} catch (IntersectionException e) {
			fail("Infinitely many intersection points.");
		}
	}
	
	@Test 
	public void testIntersectLines() {
		poly.addVertex(0, 0);
		poly.addVertex(5, 5);
		
		poly2.addVertex(5, 5);
		poly2.addVertex(10, 10);
		
		try {
			Set<Point2D.Double> points = poly.intersect(poly2);
			assertEquals(1, points.size());
			assertTrue(Polygon.containsPoint(points, new Point2D.Double(5, 5)));
		} catch (IntersectionException e) {
			fail("Infinitely many intersection points.");
		}
	}
	
	@Test
	public void testIntersectParallelLines() {
		poly.addVertex(0, 0);
		poly.addVertex(5, 5);
		
		poly2.addVertex(0, 1);
		poly2.addVertex(5, 6);
		
		try {
			Set<Point2D.Double> points = poly.intersect(poly2);
			assertTrue(points.isEmpty());
		} catch (IntersectionException e) {
			fail("Infinitely many intersection points.");
		}
	}
	
	@Test(expected=IntersectionException.class)
	public void testIntersectInfPoints() throws IntersectionException {
		poly.addVertex(0, 0);
		poly.addVertex(5, 5);
		
		poly2.addVertex(4, 4);
		poly2.addVertex(6, 6);
		
		poly.intersect(poly2);
	}
	
	@Test(expected=IntersectionException.class)
	public void testIntersectInfPoints2() throws IntersectionException {
		poly.addVertex(0, 0);
		poly.addVertex(0, 1);
		poly.addVertex(1, 0);
		
		poly2.addVertex(0, -0.5);
		poly2.addVertex(0, 0.5);
		poly2.addVertex(1, -0.5);
		
		poly.intersect(poly2);
	}
	
	@Test
	public void testIsSelfIntersectingNot() {
		poly.addVertex(p1);
		poly.addVertex(p2);
		poly.addVertex(p3);
		assertFalse(poly.isSelfIntersecting());
	}
	
	@Test
	public void testIsSelfIntersectingTrivial() {
		assertFalse(poly.isSelfIntersecting());
		poly.addVertex(p1);
		assertFalse(poly.isSelfIntersecting());
		poly.addVertex(p2);
		assertTrue(poly.isSelfIntersecting());
	}
	
	@Test
	public void testIsSelfIntersecting() {
		// https://www.wolframalpha.com/input/?i=Polygon((0.0,0.0),(1.0,2.0),(-1.0,3.0),(1.0,4.0))
		poly.addVertex(0, 0);
		poly.addVertex(1, 2);
		poly.addVertex(-1, 3);
		poly.addVertex(1, 4);
		assertTrue(poly.isSelfIntersecting());
	}
	
	@Test
	public void testIsConvexTriangle() {
		poly.addVertex(p1);
		poly.addVertex(p2);
		poly.addVertex(p3);
		assertTrue(poly.isConvex());
	}
	
	@Test
	public void testIsConvexNot() {
		// https://www.wolframalpha.com/input/?i=Polygon((0.0,0.0),(0.0,2.0),(0.5,0.5),(2.0,0.0))
		poly.addVertex(0, 0);
		poly.addVertex(0, 2);
		poly.addVertex(0.5, 0.5);
		poly.addVertex(2, 0);
		assertFalse(poly.isConvex());
	}
	
	@Test
	public void testIsConvexTrivial() {
		assertFalse(poly.isConvex());
		poly.addVertex(p1);
		assertFalse(poly.isConvex());
		poly.addVertex(p2);
		assertFalse(poly.isConvex());
	}
	
	@Test
	public void testToString() {
		assertEquals("", poly.toString());
		poly.addVertex(0, 0);
		poly.addVertex(1, 2);
		assertEquals("(0.0,0.0),(1.0,2.0)", poly.toString());
	}
	
	@Test
	public void testGetBoundsNormal() {
		// https://www.wolframalpha.com/input/?i=Polygon((1.0,2.0),(2.0,3.0),(3.0,2.0),(2.0,1.0))
		poly.addVertex(1, 2);
		poly.addVertex(2, 3);
		poly.addVertex(3, 2);
		poly.addVertex(2, 1);
		System.out.println(poly.toString());
		Rectangle2D.Double bounds = poly.getBounds();
		assertEquals(new Rectangle2D.Double(1, 1, 2, 2), bounds);
	}
	
	@Test
	public void testGetBoundsTrivial() {
		assertNull(poly.getBounds());
	}
	
	@Test
	public void testGetBoundsOnePoint() {
		poly.addVertex(1.1, 1.2);
		Rectangle2D.Double bounds = poly.getBounds();
		assertEquals(new Rectangle2D.Double(1.1, 1.2, 0, 0), bounds);
	}
	
	@Test
	public void testInside() {
		assertTrue(Polygon.inside(p3, p2, p1));
		assertTrue(Polygon.inside(p1, p3, p2));
		assertFalse(Polygon.inside(p1, p2, p3));
	}
}
