package Polygon;

import java.util.HashSet;
import java.util.Set;

public class ClippingAlgorithm {
	
	private Polygon clippingPolygon;
	private Set<Polygon> candidatePolygons;
	private Set<Polygon> resultPolygons;

	public ClippingAlgorithm() {
		candidatePolygons = new HashSet<Polygon>();
		resultPolygons = new HashSet<Polygon>();
	}
	
	public void setClippingPolygon(Polygon p) {
		clippingPolygon = p;
	}
	
	public void addCandidatePolygon(Polygon p) {
		candidatePolygons.add(p);
	}
	
	public void addCandidatePolygon(Set<Polygon> s) {
		candidatePolygons.addAll(s);
	}
	
	public Set<Polygon> getResult(){
		return resultPolygons;
	}
	
	public void SutherlandHodgman() {
		//TODO: implement Sutherland-Hodgman algorithm
	}
	
	public void WeilerAtherton() {
		//TODO: implement Weiler-Atherton algorithm
	}
	
	public void GreinerHorman() {
		//TODO: implement Greiner-Horman algorithm
	}
}
