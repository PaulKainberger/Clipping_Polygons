package Polygon;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestClippingAlgorithm {
	private Polygon triangle;
	private Polygon triangleInv;
	private Polygon rectangle;
	private Polygon horseshoe;
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
}
