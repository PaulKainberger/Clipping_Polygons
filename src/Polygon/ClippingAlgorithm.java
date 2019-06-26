package Polygon;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import Polygon.greinerHorman.GreinerHormanIterator;
import Polygon.greinerHorman.GreinerHormanPolygon;
import Polygon.greinerHorman.GreinerHormanVertex;

/**
 * Implements different polygon clipping algorithms. The class stores the
 * clipping polygon, the candidate polygons and the result polygons. There are
 * methods for each clipping algorithm which return whether the clipping was
 * successful.
 * 
 * @author Jakob
 * 
 */
public class ClippingAlgorithm {

	/**
	 * The clipping polygon.
	 */
	private Polygon clippingPolygon;

	/**
	 * The candidate polygons.
	 */
	private Set<Polygon> candidatePolygons;

	/**
	 * The result polygons.
	 */
	private Set<Polygon> resultPolygons;

	/**
	 * Constructor for Clipping algorithm with empty fields.
	 */
	public ClippingAlgorithm() {
		candidatePolygons = new HashSet<Polygon>();
		resultPolygons = new HashSet<Polygon>();
	}

	/**
	 * Sets a polygon as clipping polygon.
	 * 
	 * @param p The clipping polygon.
	 */
	public void setClippingPolygon(Polygon p) {
		clippingPolygon = p;
	}

	/**
	 * Adds a polygon to the set of candidate polygons.
	 * 
	 * @param p The candidate polygon.
	 */
	public void addCandidatePolygon(Polygon p) {
		candidatePolygons.add(p);
	}

	/**
	 * Adds a set of polygon to the set of candidate polygons.
	 * 
	 * @param s The set of clipping polygons.
	 */
	public void addCandidatePolygon(Set<Polygon> s) {
		candidatePolygons.addAll(s);
	}

	/**
	 * Returns the result of the clipping algorithm.
	 * 
	 * @return The set of result polygons.
	 */
	public Set<Polygon> getResult() {
		// remove empty polgon
		Polygon emptyPolygon = new Polygon();
		resultPolygons.remove(emptyPolygon);
		return resultPolygons;
	}

	/**
	 * Runs the Sutherland-Hodgman algorithm. The algorithm can only handle a convex
	 * and therefore non intersecting clipping polygon. If the clipping polygon is
	 * not convex false will be returned and nothing will happen. More details on
	 * the algorithm can be found e.g. on Wikipedia, see:
	 * https://en.wikipedia.org/wiki/Sutherland%E2%80%93Hodgman_algorithm If the
	 * candidate polygons are not convex there might be double edges in the
	 * resulting polygons.
	 * 
	 * @return Whether the clipping was successful.
	 */
	public boolean SutherlandHodgman() {
		if (clippingPolygon == null || clippingPolygon.isSelfIntersecting() || !clippingPolygon.isConvex()) {
			return false;
		}

		for (Polygon candidate : candidatePolygons) {
			Polygon result = clipSutherlandHodgman(candidate);
			if (result == null) {
				return false;
			} else {
				resultPolygons.add(result);
			}
		}
		return true;
	}

	/**
	 * Clips a candidate polygon against the clipping polygon using the
	 * Sutherland-Hodgman algorithm.
	 * 
	 * @param candidate Polygon which should be clipped against the clipping
	 *                  polygon.
	 * @return The clipped polygon.
	 */
	private Polygon clipSutherlandHodgman(Polygon candidate) {
		if (candidate.isPoint()) {
			if (clippingPolygon.contains(candidate.getVertex(0))) {
				return new Polygon(candidate);
			} else {
				return new Polygon();
			}
		}

		Polygon result = new Polygon(candidate);

		boolean clockwiseOriented = clippingPolygon.orientedClockwise();

		int numVertClip = clippingPolygon.getNumberVertices();
		for (int i = 0; i < numVertClip; i++) {
			// two end points of current edge
			Point2D.Double eBegin, eEnd;
			if (clockwiseOriented) {
				eBegin = clippingPolygon.getVertex(i);
				eEnd = clippingPolygon.getVertex((i + numVertClip - 1) % numVertClip);
			} else {
				eBegin = clippingPolygon.getVertex(numVertClip - i - 1);
				eEnd = clippingPolygon.getVertex((numVertClip - i) % numVertClip);
			}

			Polygon resultTmp = new Polygon(result);
			result.clear();

			int numberVertRes = resultTmp.getNumberVertices();
			for (int j = 0; j < numberVertRes; j++) {
				Point2D.Double currentPoint = resultTmp.getVertex(j);
				Point2D.Double prevPoint = resultTmp.getVertex((j + numberVertRes - 1) % numberVertRes);

				LineIntersection intersectionLine = Polygon.intersectLines(prevPoint, currentPoint, eBegin, eEnd, true);
				if (intersectionLine.getType() == LineIntersection.IntersectionType.SEGMENT) {
					result.addVertex(intersectionLine.getLineSegmentStart());
					result.addVertex(intersectionLine.getLineSegmentEnd());
				} else {
					Point2D.Double intersection = intersectionLine.getIntersection();
					if (Polygon.inside(currentPoint, eBegin, eEnd)) {
						if (!Polygon.inside(prevPoint, eBegin, eEnd)) {
							result.addVertex(intersection);
						}
						result.addVertex(currentPoint);
					} else if (Polygon.inside(prevPoint, eBegin, eEnd)) {
						result.addVertex(intersection);
					}
				}
			}
		}

		return result;
	}

	/**
	 * Runs the Weiler-Atherton algorithm.
	 * 
	 * @return Whether the clipping was successful.
	 */
	public boolean WeilerAtherton() {
		if (clippingPolygon == null || clippingPolygon.isSelfIntersecting()) {
			return false;
		}

		for (Polygon candidate : candidatePolygons) {
			List<Polygon> result = clipWeilerAtherton(candidate);
			if (result == null) {
				return false;
			} else {
				resultPolygons.addAll(result);
			}
		}
		return true;
	}

	private List<Polygon> clipWeilerAtherton(Polygon candidate) {
		List<Polygon> clippedPols = new ArrayList<Polygon>();

		if (clippingPolygon.contains(candidate)) {
			clippedPols.add(candidate);
			return clippedPols;
		}
		if (candidate.contains(clippingPolygon)) {
			clippedPols.add(clippingPolygon);
			return clippedPols;
		}

		Set<Point2D.Double> intersectionPointsSet = null;
		intersectionPointsSet = candidate.intersect(clippingPolygon);

		List<Point2D.Double> intersectionPoints = new ArrayList<Point2D.Double>(intersectionPointsSet);

		Polygon candidateWithIntersections = new Polygon(candidate);
		Polygon clippingWithIntersections = new Polygon(clippingPolygon);
		List<Boolean> candidateIsIntersectionPoint = new ArrayList<Boolean>(
				Collections.nCopies(candidate.getNumberVertices(), false));
		List<Boolean> clippingIsIntersectionPoint = new ArrayList<Boolean>(
				Collections.nCopies(clippingPolygon.getNumberVertices(), false));

		// Add intersection points to the polygons. Polygons do not change visually.
		// Note that if a vertex already is an intersection point, this point will be
		// duplicated.
		for (Point2D.Double intersectingPoint : intersectionPoints) {
			// Add intersection points to candidate polygon.
			boolean flag = true; // Indicates whether the intersection point lies between last and first vertex.
			for (int i = 0; i < candidateWithIntersections.getNumberVertices() - 1; i++) {
				if (Polygon.isBetween(candidateWithIntersections.getVertex(i),
						candidateWithIntersections.getVertex(i + 1), intersectingPoint)) {
					candidateWithIntersections.addVertex(intersectingPoint, i + 1);
					candidateIsIntersectionPoint.add(i + 1, true);
					flag = false;
					break;
				}
			}
			if (flag) {
				candidateWithIntersections.addVertex(intersectingPoint);
				candidateIsIntersectionPoint.add(true);
			}

			// Add intersection points to clipping polygon.
			flag = true; // Indicates whether the intersection point lies between last and first vertex.
			for (int i = 0; i < clippingWithIntersections.getNumberVertices() - 1; i++) {
				if (Polygon.isBetween(clippingWithIntersections.getVertex(i),
						clippingWithIntersections.getVertex(i + 1), intersectingPoint)) {
					clippingWithIntersections.addVertex(intersectingPoint, i + 1);
					clippingIsIntersectionPoint.add(i + 1, true);
					flag = false;
					break;
				}
			}
			if (flag) {
				clippingWithIntersections.addVertex(intersectingPoint);
				clippingIsIntersectionPoint.add(true);
			}
		}

		// make the polygons clockwise oriented
		if (!clippingPolygon.orientedClockwise()) {
			Collections.reverse(clippingWithIntersections.vertices);
			Collections.reverse(clippingIsIntersectionPoint);
		}
		if (!candidate.orientedClockwise()) {
			Collections.reverse(candidateWithIntersections.vertices);
			Collections.reverse(candidateIsIntersectionPoint);
		}

		// Construct clipped polygons.
		while (clippingIsIntersectionPoint.contains(true)) {
			// Construct new clipped polygon.
			Polygon clipped = new Polygon();
			// Start on the clipping polygon outside of the candidate polygon on a
			// non-intersection point.
			// Such a point exists, because the polygons do not contain each other (see
			// first lines of this method).
			int initial = getIndexOfFirstNonIntersectionPointOutsideWA(clippingWithIntersections,
					clippingIsIntersectionPoint, candidate);
			// Start clipping at the first intersection point.
			int startingIndexClipping = getIndexOfNextIntersectionPointWA(clippingWithIntersections,
					clippingIsIntersectionPoint, initial);
			int endIndexClipping = getIndexOfNextIntersectionPointWA(clippingWithIntersections,
					clippingIsIntersectionPoint, startingIndexClipping);
			Point2D.Double v = clippingWithIntersections.getVertex(startingIndexClipping); // first vertex to be added
			clipped.addVertex(v);

			boolean breakflag = false;
			while (!breakflag) { // (! v.equals(clippingWithIntersections.getVertex(endIndexClipping))) {
				// add vertices of clipping polygon to clipped polygon
				if (startingIndexClipping > endIndexClipping) {
					for (int i = startingIndexClipping + 1; i < clippingWithIntersections.getNumberVertices(); i++) {
						v = clippingWithIntersections.getVertex(i);
						clipped.addVertex(v);
					}
					startingIndexClipping = 0;
				}
				for (int i = startingIndexClipping + 1; i <= endIndexClipping; i++) {
					v = clippingWithIntersections.getVertex(i);
					clipped.addVertex(v);
				}
				// "remove" these intersection points
				clippingIsIntersectionPoint.set(startingIndexClipping, false);
				clippingIsIntersectionPoint.set(endIndexClipping, false);

				// add vertices of candidate polygon to clipped polygon
				int startingIndexCandidate = candidateWithIntersections.vertices
						.indexOf(clippingWithIntersections.getVertex(endIndexClipping));
				int endIndexCandidate = getIndexOfNextIntersectionPointWA(candidateWithIntersections,
						candidateIsIntersectionPoint, startingIndexCandidate);
				if (candidateWithIntersections.getVertex(endIndexCandidate).equals(clipped.getVertex(0))) {
					candidateIsIntersectionPoint.set(startingIndexCandidate, false);
					candidateIsIntersectionPoint.set(endIndexCandidate, false);
					endIndexCandidate--;
					breakflag = true;
				}
				if (startingIndexCandidate > endIndexCandidate) {
					for (int i = startingIndexCandidate + 1; i < candidateWithIntersections.getNumberVertices(); i++) {
						v = candidateWithIntersections.getVertex(i);
						clipped.addVertex(v);
					}
					startingIndexCandidate = 0;
				}
				for (int i = startingIndexCandidate + 1; i <= endIndexCandidate; i++) {
					v = candidateWithIntersections.getVertex(i);
					clipped.addVertex(v);
				}
				// "remove" these intersection points
				candidateIsIntersectionPoint.set(startingIndexCandidate, false);
				candidateIsIntersectionPoint.set(endIndexCandidate, false);

				if (breakflag)
					break;

				// prepare for next iteration
				startingIndexClipping = clippingWithIntersections.vertices
						.indexOf(candidateWithIntersections.getVertex(endIndexCandidate));
				endIndexClipping = getIndexOfNextIntersectionPointWA(clippingWithIntersections,
						clippingIsIntersectionPoint, startingIndexClipping);
			}
			clippedPols.add(clipped);
		}
		return clippedPols;
	}

	/**
	 * Calculates the first vertex of the polygon p1 which is not an intersection
	 * point based on the list p1IsIntersectionPoint and which does not lie inside
	 * the polygon p2 and returns its position in p1.
	 * 
	 * @param p1                    A polygon containing intersection points
	 * @param p1IsIntersectionPoint A list of booleans which indicate whether a
	 *                              point in p1 is an intersection point.
	 * @param p2                    A polygon.
	 * @return The position of the first vertex of p1, which is located outside of
	 *         p2 and which is not an intersection point, or -1, if no such point
	 *         was found.
	 */
	private int getIndexOfFirstNonIntersectionPointOutsideWA(Polygon p1, List<Boolean> p1IsIntersectionPoint,
			Polygon p2) {
		for (int i = 0; i < p1.getNumberVertices(); i++)
			if (p1IsIntersectionPoint.get(i).equals(false) && (!p2.contains(p1.getVertex(i))))
				return i;
		return -1;
	}

	/**
	 * Calculates the next (starting from position startingIndex not-including)
	 * intersection point of the polygon p1 and returns its position in p1.
	 * 
	 * @param p1                    A polygon containing intersection points
	 * @param p1IsIntersectionPoint A list of booleans which indicate whether a
	 *                              point in p1 is an intersection point.
	 * @param startingIndex         The index from which p1 is searched through.
	 * @return The index of the next intersection point in p1.
	 */
	private int getIndexOfNextIntersectionPointWA(Polygon p1, List<Boolean> p1IsIntersectionPoint, int startingIndex) {
		// iterate from the starting index + 1 to the end.
		for (int i = startingIndex + 1; i < p1.getNumberVertices(); i++)
			if (p1IsIntersectionPoint.get(i).equals(true))
				return i;
		for (int i = 0; i < startingIndex; i++)
			if (p1IsIntersectionPoint.get(i).equals(true))
				return i;
		return -1;
	}

	/**
	 * Runs the Greiner-Horman algorithm.
	 * 
	 * @return Whether the clipping was successful.
	 */
	public boolean GreinerHorman() {
		for (Polygon candidate : candidatePolygons) {
			GreinerHormanPolygon currentClippingPolygon = new GreinerHormanPolygon(clippingPolygon);
			GreinerHormanPolygon currentCandidatePolygon = new GreinerHormanPolygon(candidate);
			GreinerHormanCreateIntersectionVertices(currentCandidatePolygon, currentClippingPolygon);
			GreinerHormanMarkEntryExit(currentClippingPolygon, currentCandidatePolygon);
			GreinerHormanMarkEntryExit(currentCandidatePolygon, currentClippingPolygon);
			GreinerHormanCreateClippedPolygons(currentCandidatePolygon, currentClippingPolygon);
		}
		return true;
	}

	/**
	 * Calculates all intersection of edges of the two given polygons and adds new
	 * points to them at each intersection.
	 * 
	 * @param candidatePolygon The candidate polygon.
	 * @param clippingPolygon  The clipping polygon.
	 */
	private void GreinerHormanCreateIntersectionVertices(GreinerHormanPolygon candidatePolygon,
			GreinerHormanPolygon clippingPolygon) {

		GreinerHormanVertex v = candidatePolygon.getFirstVertex();
		List<GreinerHormanVertex> listCandidate = new ArrayList<GreinerHormanVertex>();
		while (v.getNext() != candidatePolygon.getFirstVertex()) {
			listCandidate.add(v);
			v = v.getNext();
		}
		listCandidate.add(v);

		v = clippingPolygon.getFirstVertex();
		List<GreinerHormanVertex> listClipping = new ArrayList<GreinerHormanVertex>();
		while (v.getNext() != clippingPolygon.getFirstVertex()) {
			listClipping.add(v);
			v = v.getNext();
		}
		listClipping.add(v);

		for (int i = 0; i < listClipping.size(); i++) {
			for (int j = 0; j < listCandidate.size(); j++) {
				LineIntersection intersection = Polygon.intersectLines(listClipping.get(i), listClipping.get((i + 1)%listClipping.size()),
						listCandidate.get(j), listCandidate.get((j + 1)%listCandidate.size()));
				if (intersection.getType() == LineIntersection.IntersectionType.POINT) {
					if (intersection.getLineRatio1() == 0) // degenerate case
						;
					else if (intersection.getLineRatio2() == 0) // degenerate case
						;
					else { // general case
						GreinerHormanVertex newClippingVertex = new GreinerHormanVertex(intersection.getIntersection(),
								true, false, null, intersection.getLineRatio1());
						GreinerHormanVertex newCandidateVertex = new GreinerHormanVertex(intersection.getIntersection(),
								true, false, newClippingVertex, intersection.getLineRatio2());
						newClippingVertex.setNeighbor(newCandidateVertex);
						clippingPolygon.addIntersectionVertex(newClippingVertex, listClipping.get(i));
						candidatePolygon.addIntersectionVertex(newCandidateVertex, listCandidate.get(j));
					}
				}
				if (intersection.getType() == LineIntersection.IntersectionType.SEGMENT) {
					// degenerate case
				}
			}
		}
	}

	private void GreinerHormanMarkEntryExit(GreinerHormanPolygon polygon1, GreinerHormanPolygon polygon2) {
		boolean inside = polygon1.toPolygon().contains(polygon2.getFirstVertex());
		GreinerHormanVertex v = polygon2.getFirstVertex().getNext();
		while (v != polygon2.getFirstVertex()) {
			if (v.isIntersect()) {
				v.setEntry(!inside);
				inside = !inside;
			}
			v = v.getNext();
		}
	}

	private void GreinerHormanCreateClippedPolygons(GreinerHormanPolygon candidatePolygon,
			GreinerHormanPolygon clippingPolygon) {
		GreinerHormanVertex current = candidatePolygon.getFirstVertex().getNext();
		Polygon clippedPolygon;
	//	while (candidatePolygon.hasIntersection()) {
			while (!current.isIntersect())
				current = current.getNext();
			// we are at an unprocessed intersection vertex
			clippedPolygon = new Polygon();
			clippedPolygon.addVertex(current);
			GreinerHormanVertex firstVertex = current;
			do {
				if (current.isEntry()) {
					do {
						current = current.getNext();
						clippedPolygon.addVertex(current);
					} while (!current.isIntersect());
				} else {
					do {
						current = current.getPrevious();
						clippedPolygon.addVertex(current);
					} while (!current.isIntersect());
				}
				current.setIntersect(false);
				current = current.getNeighbor();
			} while (current != firstVertex);
			resultPolygons.add(clippedPolygon);
	//	}
	}

	/**
	 * Return string representation of Clipping algorithm showing the current
	 * polygons used.
	 * 
	 * @return String representation.
	 */
	@Override
	public String toString() {
		String rep = "Clipping Polygon: ";
		if (clippingPolygon != null) {
			rep += clippingPolygon.toString();
		} else {
			rep += "None";
		}
		rep += "\nCandidate polygons: ";
		if (candidatePolygons.isEmpty()) {
			rep += "None";
		} else {
			for (Polygon poly : candidatePolygons) {
				rep += "\n- " + poly.toString();
			}
		}
		rep += "\nResult polygons: ";
		if (resultPolygons.isEmpty()) {
			rep += "None";
		} else {
			for (Polygon poly : resultPolygons) {
				rep += "\n- " + poly.toString();
			}
		}
		return rep;
	}

}
