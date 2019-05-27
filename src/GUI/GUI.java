package GUI;

import Polygon.*;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextArea;
import java.awt.Dimension;
import javax.swing.border.EtchedBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import javax.swing.JTextPane;
import javax.swing.JList;
import javax.swing.AbstractListModel;

/**
 * @author Paul
 *
 */
public class GUI {

	private JFrame frame;
	private Polygon clippingPol;
	//private ArrayList<Polygon> candidatePols = new ArrayList<Polygon>();
	private ArrayList<Point2D.Double> drawnPol = new ArrayList<Point2D.Double>();
	private Vector<Polygon> candidatePols = new Vector<Polygon>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setMinimumSize(new Dimension(1000, 500));
		frame.setName("frame");
		frame.setBounds(100, 100, 1085, 557);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {600, 400};
		gridBagLayout.rowHeights = new int[] {500};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0};
		frame.getContentPane().setLayout(gridBagLayout);
		
		final PolygonGraphic display = new PolygonGraphic();
		
		JPanel panel = new JPanel();
		JPanel panel_1 = new JPanel();
		JPanel panel_2 = new JPanel();
		JPanel panel_3 = new JPanel();
		JPanel panel_4 = new JPanel();
		
		final JTextPane textPane = new JTextPane();
		final JButton btnStartDrawing = new JButton("Start drawing");
		final JButton btnCancelDrawing = new JButton("Cancel drawing");
		final JButton btnFinishDrawing = new JButton("Finish drawing");
		final JButton btnDeletePol = new JButton("Delete");
		JButton btnClipPolygons = new JButton("Clip polygons");
		
		final JList list_clipping = new JList();
		final JList list_candidates = new JList();
		final JList list_clipped = new JList();
		
		final JTextArea txtReport = new JTextArea();
		
		JComboBox comboBox = new JComboBox();
		
		panel_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent m) {
				if(! btnStartDrawing.isEnabled()) {
					drawnPol.add(new Point2D.Double((double) m.getX(), (double) m.getY()));
					textPane.setText(drawnPol.toString());
				}
				
			}
		});
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.insets = new Insets(0, 0, 0, 5);
		gbc_panel_4.gridx = 0;
		gbc_panel_4.gridy = 0;
		frame.getContentPane().add(panel_4, gbc_panel_4);
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[] {650};
		gbl_panel_4.rowHeights = new int[] {500};
		gbl_panel_4.columnWeights = new double[]{0.0};
		gbl_panel_4.rowWeights = new double[]{0.0};
		panel_4.setLayout(gbl_panel_4);
		
		
		GridBagConstraints gbc_display = new GridBagConstraints();
		gbc_display.fill = GridBagConstraints.BOTH;
		gbc_display.gridx = 0;
		gbc_display.gridy = 0;
		panel_4.add(display, gbc_display);
		
		
		
		panel.setBorder(null);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 0;
		frame.getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {30};
		gbl_panel.rowHeights = new int[] {50, 30, 30};
		gbl_panel.columnWeights = new double[]{1.0};
		gbl_panel.rowWeights = new double[]{1.0, 1.0, 1.0};
		panel.setLayout(gbl_panel);
		
		
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setLayout(null);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		panel.add(panel_1, gbc_panel_1);
		
		
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(152, 140, 89, 23);
		panel_1.add(btnNewButton);
		
		btnStartDrawing.setBounds(10, 11, 126, 23);
		panel_1.add(btnStartDrawing);
		btnStartDrawing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnFinishDrawing.setEnabled(true);
				btnCancelDrawing.setEnabled(true);
				btnStartDrawing.setEnabled(false);
			}
		});
		
		btnFinishDrawing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//candidatePols.add(new Polygon(drawnPol));
				list_candidates.setListData(candidatePols);
				drawnPol.clear();
				btnStartDrawing.setEnabled(true);
				btnCancelDrawing.setEnabled(false);
				btnFinishDrawing.setEnabled(false);
			}
		});
		btnFinishDrawing.setEnabled(false);
		btnFinishDrawing.setBounds(146, 11, 101, 23);
		panel_1.add(btnFinishDrawing);
		
		btnCancelDrawing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawnPol.clear();
				btnStartDrawing.setEnabled(true);
				btnFinishDrawing.setEnabled(false);
				btnCancelDrawing.setEnabled(false);
			}
		});
		btnCancelDrawing.setEnabled(false);
		btnCancelDrawing.setBounds(257, 11, 105, 23);
		panel_1.add(btnCancelDrawing);
		
		
		textPane.setBounds(10, 45, 135, 118);
		panel_1.add(textPane);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				display.draw = true;
				display.repaint();
			}
		});
		
		
		panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_2.setLayout(null);
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 5, 5);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 1;
		panel.add(panel_2, gbc_panel_2);
		
		
		list_clipping.setBounds(10, 11, 116, 132);
		panel_2.add(list_clipping);
		
		
		list_candidates.setBounds(136, 11, 129, 81);
		panel_2.add(list_candidates);

		
		list_clipped.setBounds(275, 11, 110, 81);
		list_clipped.setModel(new AbstractListModel() {
			String[] values = new String[] {"bla", "blub"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		panel_2.add(list_clipped);
		
		
		btnDeletePol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//list_clipped.remove(list_clipped.getSelectedIndex());
				txtReport.setText("" + list_clipped.getSelectedIndex() + "\n" + list_clipped.getSelectedValue()); 
			}
		});
		btnDeletePol.setBounds(296, 120, 89, 23);
		panel_2.add(btnDeletePol);
		
		panel_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_3.setLayout(null);
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 5, 5);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 2;
		panel.add(panel_3, gbc_panel_3);
		
		txtReport.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtReport.setBounds(205, 11, 176, 130);
		panel_3.add(txtReport);
		txtReport.setText("Report:");
		
		btnClipPolygons.setBounds(10, 118, 185, 23);
		panel_3.add(btnClipPolygons);
		
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Automatic", "Sutherland-Hodgman", "Weiler-Atherton", "Greiner-Hormann"}));
		comboBox.setToolTipText("");
		comboBox.setBounds(10, 87, 185, 20);
		panel_3.add(comboBox);
		
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
	}
}
