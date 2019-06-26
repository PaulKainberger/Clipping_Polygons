/**
 * 
 */
package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.LinkedList;
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
	
	private double scalingFactor = 1;
	
	public double getScalingFactor() {
		return scalingFactor;
	}

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

	/**
	 * Updates the scaling factor of the drawing panel, such that every polygon fits inside the panel.
	 */
	public void updateScalingFactor() {
		double maxWidth=this.getWidth()/2;
		double maxHeight=this.getHeight()/2;
		List<Polygon> polygons = new LinkedList<Polygon>(candidatePolygons);
		polygons.addAll(clippingPolygons);
		for(Polygon p : polygons) {
			Rectangle2D.Double bounds = p.getBounds();
			if(-bounds.x>maxWidth)
				maxWidth = -bounds.x;
			    System.out.println("j"+maxHeight + "," + maxWidth);
			if(bounds.x+bounds.width>maxWidth)
				maxWidth = bounds.x+bounds.width;
				System.out.println("j"+maxHeight + "," + maxWidth);
			if(-bounds.y>maxHeight)
				maxHeight = -bounds.y;
				System.out.println("k"+maxHeight + "," + maxWidth);
			if(bounds.y+bounds.height>maxHeight)
				maxHeight = bounds.y+bounds.height;
				System.out.println("j"+maxHeight + "," + maxWidth);
		}
		System.out.println(maxWidth + "," + maxHeight);
		scalingFactor = Math.min((this.getWidth()/2)/maxWidth, (this.getHeight()/2)/maxHeight);
		System.out.println(scalingFactor);
		System.out.println(this.getWidth() + "," + this.getHeight());
	}
		
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		super.paintComponent(g);
		this.setBackground(Color.white);
		g2d.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON));
		
		drawnPolygon.drawIncomplete(g2d, this.getWidth(), this.getHeight(), Color.YELLOW,scalingFactor);
		
		for(int i = 0; i < candidatePolygons.size(); i++) {
			candidatePolygons.get(i).draw(g2d, this.getWidth(), this.getHeight(), Color.RED, 
					Arrays.binarySearch(listCandidatePolygons.getSelectedIndices(),i) >= 0 ? 0.5F : 0.1F, scalingFactor);
		}
		
		for(int i = 0; i < clippingPolygons.size(); i++) {
			clippingPolygons.get(i).draw(g2d, this.getWidth(), this.getHeight(), Color.BLUE, 
					Arrays.binarySearch(listClippingPolygons.getSelectedIndices(),i) >= 0 ? 0.5F : 0.1F, scalingFactor);
		}
		
		for(int i = 0; i < clippedPolygons.size(); i++) {
			clippedPolygons.get(i).draw(g2d, this.getWidth(), this.getHeight(), Color.GREEN, 
					Arrays.binarySearch(listClippedPolygons.getSelectedIndices(),i) >= 0 ? 0.5F : 0.1F, scalingFactor);
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
