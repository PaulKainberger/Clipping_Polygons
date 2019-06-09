/**
 * 
 */
package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Arrays;
import java.util.List;

import javax.swing.JList;
import javax.swing.JPanel;

import Polygon.Polygon;

/**
 * @author Paul
 *
 */
public class PolygonGraphic extends JPanel {


	private Polygon drawnPolygon;
	private List<Polygon> candidatePolygons;
	private List<Polygon> clippingPolygons;
	private List<Polygon> clippedPolygons;
	
	private JList<String> listCandidatePolygons;
	private JList<String> listClippingPolygons;
	private JList<String> listClippedPolygons;

	private static final long serialVersionUID = 1L;
	
	/**
	 * Create the panel.
	 */
	public PolygonGraphic() {
	}
	
	public void setDrawnPolygon(Polygon p) {
		drawnPolygon = p;
	}
		
	public void setCandidatePolygons(List<Polygon> candidatePolygons) {
		this.candidatePolygons = candidatePolygons;
	}

	public void setClippingPolygons(List<Polygon> clippingPolygons) {
		this.clippingPolygons = clippingPolygons;
	}

	public void setClippedPolygons(List<Polygon> clippedPolygons) {
		this.clippedPolygons = clippedPolygons;
	}

		
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		super.paintComponent(g);
		this.setBackground(Color.white);
		g2d.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON));
		
		g.setColor(Color.BLACK);
		g.drawLine(0,this.getHeight()/2,this.getWidth(),this.getHeight()/2);
		
		drawnPolygon.drawIncomplete(g2d, this.getWidth(), this.getHeight(), Color.YELLOW,1);
		
		for(int i = 0; i < candidatePolygons.size(); i++) {
			candidatePolygons.get(i).draw(g2d, this.getWidth(), this.getHeight(), Color.RED, 
					Arrays.binarySearch(listCandidatePolygons.getSelectedIndices(),i) >= 0 ? 0.5F : 0.2F, 1);
		}
		
		for(int i = 0; i < clippingPolygons.size(); i++) {
			clippingPolygons.get(i).draw(g2d, this.getWidth(), this.getHeight(), Color.BLUE, 
					Arrays.binarySearch(listClippingPolygons.getSelectedIndices(),i) >= 0 ? 0.5F : 0.2F, 1);
		}
		
		for(int i = 0; i < clippedPolygons.size(); i++) {
			clippedPolygons.get(i).draw(g2d, this.getWidth(), this.getHeight(), Color.GREEN, 
					Arrays.binarySearch(listClippedPolygons.getSelectedIndices(),i) >= 0 ? 0.5F : 0.2F, 1);
		}
	}

	/**
	 * @return the clippedPolygons
	 */
	public List<Polygon> getClippedPolygons() {
		return clippedPolygons;
	}

	/**
	 * @param listCandidatePolygons the listCandidatePolygons to set
	 */
	public void setListCandidatePolygons(JList<String> listCandidatePolygons) {
		this.listCandidatePolygons = listCandidatePolygons;
	}

	/**
	 * @param listClippingPolygons the listClippingPolygons to set
	 */
	public void setListClippingPolygons(JList<String> listClippingPolygons) {
		this.listClippingPolygons = listClippingPolygons;
	}

	/**
	 * @param listClippedPolygons the listClippedPolygons to set
	 */
	public void setListClippedPolygons(JList<String> listClippedPolygons) {
		this.listClippedPolygons = listClippedPolygons;
	}

	
	
}
