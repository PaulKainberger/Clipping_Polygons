/**
 * 
 */
package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

/**
 * @author Paul
 *
 */
public class PolygonGraphic extends JPanel {

	//private ClippingAlgorithm alg;

	//alg.getClippingPolygon.draw(g);
	

	private static final long serialVersionUID = 1L;
	private boolean drawClippingPolygon = false;
	private boolean drawCandidatePolygons = false;
	private boolean drawClippedPolygons = false;
	boolean draw = false;
	/**
	 * Create the panel.
	 */
	public PolygonGraphic() {

	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.white);
		if (draw)
			test(g);
	}
	
	private void test(Graphics g) {
		((Graphics2D)g).setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON));
		g.setColor(Color.red);
		g.fillRect(10, 10, 100, 30);
		g.drawLine(20, 20, 500, 20);
		g.drawLine(20, 20, 500, 30);
		g.drawLine(20, 20, 500, 300);

	}
	

}
