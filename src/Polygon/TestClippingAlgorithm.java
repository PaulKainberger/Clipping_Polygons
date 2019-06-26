package Polygon;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Junit tests for Polygon clipping class.
 * @author Philipp
 * @version 0.1
 *
 */
public class TestClippingAlgorithm {
	private Polygon triangle;
	private Polygon triangleInv;
	private Polygon rectangle;
	private Polygon horseshoe;
	private Polygon star;
	private Polygon bowl;
	private Polygon onePoint;
	private Polygon onePoint2;
	private Polygon empty;
	
	@Before
	public void setUp() throws Exception {
		triangle = new Polygon();
		triangle.addVertex(0, 0);
		triangle.addVertex(0, 50);
		triangle.addVertex(50, 0);
		
		triangleInv = new Polygon();
		triangleInv.addVertex(0, 0);
		triangleInv.addVertex(50, 0);
		triangleInv.addVertex(0, 50);
		
		rectangle = new Polygon();
		rectangle.addVertex(10, -10);
		rectangle.addVertex(30, -10);
		rectangle.addVertex(30, 70);
		rectangle.addVertex(10, 70);
		
		horseshoe = new Polygon();
		horseshoe.addVertex(-20, 20);
		horseshoe.addVertex(-20, 30);
		horseshoe.addVertex(10, 30);
		horseshoe.addVertex(10, 27);
		horseshoe.addVertex(-10, 27);
		horseshoe.addVertex(-10, 23);
		horseshoe.addVertex(10, 23);
		horseshoe.addVertex(10, 20);
		
		star = new Polygon();
		star.addVertex(0, 10);
		star.addVertex(2.5, 5);
		star.addVertex(10, 5);
		star.addVertex(5, 0);
		star.addVertex(7.5, -10);
		star.addVertex(0, -5);
		star.addVertex(-7.5, -10);
		star.addVertex(-5, 0);
		star.addVertex(-10, 5);
		star.addVertex(-2.5, 5);
		
		bowl = new Polygon();
		bowl.addVertex(-7.5, 10);
		bowl.addVertex(-10, -5);
		bowl.addVertex(0, -10);
		bowl.addVertex(10, -5);
		bowl.addVertex(7.5, 10);
		bowl.addVertex(6.5, 10);
		bowl.addVertex(9, -5);
		bowl.addVertex(0, -9);
		bowl.addVertex(-9, -5);
		bowl.addVertex(-6.5, 10);
		
		onePoint = new Polygon();
		onePoint.addVertex(5, 5);
		
		onePoint2 = new Polygon();
		onePoint2.addVertex(0, 5);
		
		empty = new Polygon();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSutherlandHodgmanTriangle() {
		// https://www.wolframalpha.com/input/?i=Polygon((10.0,-10.0),(30.0,-10.0),(30.0,70.0),(10.0,70.0)),+Polygon((0.0,0.0),(0.0,50.0),(50.0,0.0))
		ClippingAlgorithm clipSH = new ClippingAlgorithm();
		clipSH.setClippingPolygon(triangle);
		clipSH.addCandidatePolygon(rectangle);
		boolean shResult = clipSH.SutherlandHodgman();
		assertTrue(shResult);
		Set<Polygon> shResultPolys = clipSH.getResult();
		assertEquals(1, shResultPolys.size());
		Polygon clipped = new Polygon();
		clipped.addVertex(10, 40);
		clipped.addVertex(10, 0);
		clipped.addVertex(30, 0);
		clipped.addVertex(30, 20);
		assertTrue(shResultPolys.contains(clipped));
	}
	
	@Test
	public void testSutherlandHodgmanTriangleInv() {
		// https://www.wolframalpha.com/input/?i=Polygon((10.0,-10.0),(30.0,-10.0),(30.0,70.0),(10.0,70.0)),+Polygon((0.0,0.0),(0.0,50.0),(50.0,0.0))
		ClippingAlgorithm clipSH = new ClippingAlgorithm();
		clipSH.setClippingPolygon(triangleInv);
		clipSH.addCandidatePolygon(rectangle);
		boolean shResult = clipSH.SutherlandHodgman();
		assertTrue(shResult);
		Set<Polygon> shResultPolys = clipSH.getResult();
		assertEquals(1, shResultPolys.size());
		Polygon clipped = new Polygon();
		clipped.addVertex(10, 40);
		clipped.addVertex(10, 0);
		clipped.addVertex(30, 0);
		clipped.addVertex(30, 20);
		assertTrue(shResultPolys.contains(clipped));
	}

	@Test
	public void testSutherlandHodgmanHorseshoe() {
		ClippingAlgorithm clipSH = new ClippingAlgorithm();
		clipSH.setClippingPolygon(triangle);
		clipSH.addCandidatePolygon(horseshoe);
		boolean shResult = clipSH.SutherlandHodgman();
		assertTrue(shResult);
		Set<Polygon> shResultPolys = clipSH.getResult();
		assertEquals(1, shResultPolys.size());
		Polygon clipped = new Polygon();
		clipped.addVertex(0, 20);
		clipped.addVertex(0, 30);
		clipped.addVertex(10, 30);
		clipped.addVertex(10, 27);
		clipped.addVertex(0, 27);
		clipped.addVertex(0, 23);
		clipped.addVertex(10, 23);
		clipped.addVertex(10, 20);
		assertTrue(shResultPolys.contains(clipped));
	}
	
	@Test
	public void testSutherlandHodgmanOnePointIn() {
		ClippingAlgorithm clipSH = new ClippingAlgorithm();
		clipSH.setClippingPolygon(triangle);
		clipSH.addCandidatePolygon(onePoint);
		boolean shResult = clipSH.SutherlandHodgman();
		assertTrue(shResult);
		Set<Polygon> shResultPolys = clipSH.getResult();
		assertEquals(1, shResultPolys.size());
		Polygon clipped = new Polygon();
		clipped.addVertex(5, 5);
		assertTrue(shResultPolys.contains(clipped));
	}
	
	@Test
	public void testSutherlandHodgmanOnePointOut() {
		ClippingAlgorithm clipSH = new ClippingAlgorithm();
		clipSH.setClippingPolygon(rectangle);
		clipSH.addCandidatePolygon(onePoint);
		boolean shResult = clipSH.SutherlandHodgman();
		assertTrue(shResult);
		Set<Polygon> shResultPolys = clipSH.getResult();
		assertEquals(0, shResultPolys.size());
	}
	
	@Test
	public void testSutherlandHodgmanOnePointBorder() {
		ClippingAlgorithm clipSH = new ClippingAlgorithm();
		clipSH.setClippingPolygon(triangle);
		clipSH.addCandidatePolygon(onePoint2);
		boolean shResult = clipSH.SutherlandHodgman();
		assertTrue(shResult);
		Set<Polygon> shResultPolys = clipSH.getResult();
		assertEquals(1, shResultPolys.size());
		Polygon clipped = new Polygon();
		clipped.addVertex(0, 5);
		assertTrue(shResultPolys.contains(clipped));
	}
	
	@Test
	public void testSutherlandHodgmanEmpty() {
		ClippingAlgorithm clipSH = new ClippingAlgorithm();
		clipSH.setClippingPolygon(triangle);
		clipSH.addCandidatePolygon(empty);
		boolean shResult = clipSH.SutherlandHodgman();
		assertTrue(shResult);
		Set<Polygon> shResultPolys = clipSH.getResult();
		assertTrue(shResultPolys.isEmpty());
	}
	
	/**
	 * Tests for the Weiler-Atherton algorithm.
	 */
	
	@Test // copied from Philipp
	public void testWeilerAthertonTriangle() {
		// https://www.wolframalpha.com/input/?i=Polygon((10.0,-10.0),(30.0,-10.0),(30.0,70.0),(10.0,70.0)),+Polygon((0.0,0.0),(0.0,50.0),(50.0,0.0))
		ClippingAlgorithm clipSH = new ClippingAlgorithm();
		clipSH.setClippingPolygon(triangle);
		clipSH.addCandidatePolygon(rectangle);
		boolean shResult = clipSH.WeilerAtherton();
		assertTrue(shResult);
		Set<Polygon> shResultPolys = clipSH.getResult();
		assertEquals(1, shResultPolys.size());
		Polygon clipped = new Polygon();
		clipped.addVertex(10, 40);
		clipped.addVertex(30, 20);
		clipped.addVertex(30, 0);
		clipped.addVertex(10, 0);
		assertTrue(shResultPolys.contains(clipped));
	}
	
	@Test // copied from Philipp
	public void testWeilerAthertonTriangleInv() {
		// https://www.wolframalpha.com/input/?i=Polygon((10.0,-10.0),(30.0,-10.0),(30.0,70.0),(10.0,70.0)),+Polygon((0.0,0.0),(0.0,50.0),(50.0,0.0))
		ClippingAlgorithm clipSH = new ClippingAlgorithm();
		clipSH.setClippingPolygon(triangleInv);
		clipSH.addCandidatePolygon(rectangle);
		boolean shResult = clipSH.WeilerAtherton();
		assertTrue(shResult);
		Set<Polygon> shResultPolys = clipSH.getResult();
		assertEquals(1, shResultPolys.size());
		Polygon clipped = new Polygon();
		clipped.addVertex(10, 40);
		clipped.addVertex(30, 20);
		clipped.addVertex(30, 0);
		clipped.addVertex(10, 0);
		assertTrue(shResultPolys.contains(clipped));
	}
	
	@Test // copied from Philipp
	public void testWeilerAthertonHorseshoe() {
		ClippingAlgorithm clipSH = new ClippingAlgorithm();
		clipSH.setClippingPolygon(triangle);
		clipSH.addCandidatePolygon(horseshoe);
		boolean shResult = clipSH.WeilerAtherton();
		assertTrue(shResult);
		Set<Polygon> shResultPolys = clipSH.getResult();
		assertEquals(2, shResultPolys.size());
		Polygon clipped = new Polygon();
		clipped.addVertex(0, 20);
		clipped.addVertex(0, 23);
		clipped.addVertex(10, 23);
		clipped.addVertex(10, 20);
		assertTrue(shResultPolys.contains(clipped));
		clipped = new Polygon();
		clipped.addVertex(0, 27);
		clipped.addVertex(0, 30);
		clipped.addVertex(10, 30);
		clipped.addVertex(10, 27);
		assertTrue(shResultPolys.contains(clipped));
	}
	
	@Test
	public void testWeilerAthertonStarBowl() {
		ClippingAlgorithm clipSH = new ClippingAlgorithm();
		clipSH.setClippingPolygon(star);
		clipSH.addCandidatePolygon(bowl);
		boolean shResult = clipSH.WeilerAtherton();
		assertTrue(shResult);
		Set<Polygon> shResultPolys = clipSH.getResult();
		assertEquals(4, shResultPolys.size());
		Polygon clipped = new Polygon();
		clipped.addVertex(-3.6, -7.4);
		clipped.addVertex(-4.2857142857142865, -7.857142857142857);
		clipped.addVertex(-6.666666666666667, -6.666666666666666);
		clipped.addVertex(-6.5249999999999995, -6.1);
		assertTrue(shResultPolys.contains(clipped));
		clipped = new Polygon();
		clipped.addVertex(6.525, -6.1);
		clipped.addVertex(6.666666666666666, -6.666666666666667);
		clipped.addVertex(4.285714285714286, -7.857142857142858);
		clipped.addVertex(3.6000000000000005, -7.4);
		assertTrue(shResultPolys.contains(clipped));
		clipped = new Polygon();
		clipped.addVertex(7.333333333333333, 5.0);
		clipped.addVertex(8.333333333333334, 5.0);
		clipped.addVertex(8.571428571428571, 3.571428571428571);
		clipped.addVertex(7.714285714285714, 2.7142857142857144);
		assertTrue(shResultPolys.contains(clipped));
		clipped = new Polygon();
		clipped.addVertex(-7.714285714285714, 2.7142857142857135);
		clipped.addVertex(-8.571428571428571, 3.571428571428572);
		clipped.addVertex(-8.333333333333334, 5.0);
		clipped.addVertex(-7.333333333333334, 5.0);
		assertTrue(shResultPolys.contains(clipped));
	}
	
	@Test
	public void testWeilerAthertonBowlStar() {
		ClippingAlgorithm clipSH = new ClippingAlgorithm();
		clipSH.setClippingPolygon(bowl);
		clipSH.addCandidatePolygon(star);
		boolean shResult = clipSH.WeilerAtherton();
		assertTrue(shResult);
		Set<Polygon> shResultPolys = clipSH.getResult();
		assertEquals(4, shResultPolys.size());
		Polygon clipped = new Polygon();
		clipped.addVertex(3.5999999999999996, -7.4);
		clipped.addVertex(6.525, -6.1);
		clipped.addVertex(6.666666666666666, -6.666666666666666);
		clipped.addVertex(4.2857142857142865, -7.857142857142858);
		assertTrue(shResultPolys.contains(clipped));
		clipped = new Polygon();
		clipped.addVertex(-7.333333333333333, 5.0);
		clipped.addVertex(-7.7142857142857135, 2.714285714285714);
		clipped.addVertex(-8.571428571428571, 3.5714285714285716);
		clipped.addVertex(-8.333333333333334, 5.0);
		assertTrue(shResultPolys.contains(clipped));
		clipped = new Polygon();
		clipped.addVertex(-6.525, -6.1);
		clipped.addVertex(-3.5999999999999996, -7.4);
		clipped.addVertex(-4.285714285714286, -7.857142857142857);
		clipped.addVertex(-6.666666666666667, -6.666666666666667);
		assertTrue(shResultPolys.contains(clipped));
		clipped = new Polygon();
		clipped.addVertex(7.714285714285714, 2.7142857142857144);
		clipped.addVertex(7.333333333333334, 5.0);
		clipped.addVertex(8.333333333333332, 5.0);
		clipped.addVertex(8.571428571428571, 3.5714285714285716);
		assertTrue(shResultPolys.contains(clipped));
	}
}
