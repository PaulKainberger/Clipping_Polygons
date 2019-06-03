import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUIGraphicsExample extends JPanel
{
	public GUIGraphicsExample()
	{
	}
	private static final long serialVersionUID = 1L;
	public void paintComponent(Graphics g)
	{
	super.paintComponent(g);
	Polygon p = new Polygon();
	for (int i = 0; i < 5; i++) p.addPoint((int) (
	100 + 50 * Math.cos(i * 2 * Math.PI / 5)), (int) (
	100 + 50 * Math.sin(i * 2 * Math.PI / 5)));
	Polygon q = new Polygon();
	for (int i = 0; i < 5; i++) q.addPoint((int) (
	150 + 50 * Math.cos(i * 2 * Math.PI / 5)), (int) (
	100 + 50 * Math.sin(i * 2 * Math.PI / 5)));
      
      g.fillPolygon(p);
      g.setColor(Color.blue);
      //g.drawPolygon(q);
      g.fillPolygon(q);
      g.setColor(Color.red);
      g.drawPolygon(p);
   } 
   public static void main(String[] args)
   {
      JFrame frame = new JFrame();
      frame.setSize(350, 250);
      frame.addWindowListener(new WindowAdapter()
      {
         public void windowClosing(WindowEvent e)
         {
            System.exit(0);
         } 
      }); 
      Container contentPane = frame.getContentPane();
      contentPane.add(new GUIGraphicsExample());
      frame.show();
   }
}
