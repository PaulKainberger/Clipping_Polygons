/**
 * 
 */
package GUI;

import java.awt.Color;
import java.awt.Graphics;

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
		g.setColor(Color.red);
		g.fillRect(10, 10, 100, 30);
	}
	

}
