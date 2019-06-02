/**
 * 
 */
package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import Polygon.Polygon;

/**
 * @author Paul
 *
 */
public class PolygonGraphic extends JPanel {


	Polygon drawnPolygon;

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
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.white);
		if (draw)
			test((Graphics2D)g);
	}
	
	private void test(Graphics2D g) {
		((Graphics2D)g).setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON));
		g.setColor(Color.red);
		g.fillRect(10, 10, 100, 30);
		g.drawLine(20, 20, 500, 20);
		g.drawLine(20, 20, 500, 30);
		g.drawLine(20, 20, 500, 300);

		Polygon p = new Polygon();
		p.addVertex(20, 20);
		p.addVertex(100, 50);
		p.addVertex(80,100);
		p.draw(g, Color.GREEN);
		drawnPolygon.drawIncomplete(g, Color.ORANGE);
	}
	
	
}
