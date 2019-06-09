/**
 * 
 */
package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;

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

	private static final long serialVersionUID = 1L;
	private boolean drawClippingPolygon = false;
	private boolean drawCandidatePolygons = false;
	private boolean drawClippedPolygons = false;
	boolean draw = true;
	
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
		super.paintComponent(g);
		this.setBackground(Color.white);
		if (draw)
			test((Graphics2D)g);
	}
	
	private void test(Graphics2D g) {
		((Graphics2D)g).setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON));
		drawnPolygon.drawIncomplete(g, Color.ORANGE);
	}
	
	
}
