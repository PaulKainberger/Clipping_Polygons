package Polygon;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestClippingAlgorithm {
	private Polygon triangle;
	private Polygon rectangle;
	
	@Before
	public void setUp() throws Exception {
		triangle = new Polygon();
		triangle.addVertex(0, 0);
		triangle.addVertex(0, 50);
		triangle.addVertex(50, 0);
	
		rectangle = new Polygon();
		rectangle.addVertex(10, -10);
		rectangle.addVertex(30, -10);
		rectangle.addVertex(30, 70);
		rectangle.addVertex(10, 70);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSutherlandHodgmanTriangle() {
		ClippingAlgorithm clipSH = new ClippingAlgorithm();
		clipSH.setClippingPolygon(triangle);
		clipSH.addCandidatePolygon(rectangle);
		System.out.println(triangle.toString());
		System.out.println(rectangle.toString());
		boolean shResult = clipSH.SutherlandHodgman();
		assertTrue(shResult);
		System.out.println(clipSH.getResult().toString());
	}

}
