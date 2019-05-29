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
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import java.awt.Color;

/**
 * @author Paul
 *
 */
public class GUI {

	private JFrame frmClippingPolygons;
	private Polygon clippingPol;
	//private ArrayList<Polygon> candidatePols = new ArrayList<Polygon>();
	private Polygon drawnPol = new Polygon();
	private Vector<Polygon> candidatePols = new Vector<Polygon>();
	private JTextField txtDrawAPolygon;
	private JTextField txtClippingPolygons;
	private JTextField txtCandidatePolygons;
	private JTextField txtClippedPolygons;
	
	private JButton btnCancelDrawing = new JButton("Cancel drawing");
	private JButton btnFinishDrawing = new JButton("Finish drawing");
	private JButton btnStartDrawing = new JButton("Start drawing");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frmClippingPolygons.setVisible(true);
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
		frmClippingPolygons = new JFrame();
		frmClippingPolygons.setTitle("Clipping polygons");
		frmClippingPolygons.setName("frame");
		frmClippingPolygons.setBounds(100, 100, 1060, 630);
		frmClippingPolygons.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {600, 360};
		gridBagLayout.rowHeights = new int[] {450};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0};
		frmClippingPolygons.getContentPane().setLayout(gridBagLayout);
		
		JScrollPane scrollPane_display = new JScrollPane();
		GridBagConstraints gbc_scrollPane_display = new GridBagConstraints();
		gbc_scrollPane_display.insets = new Insets(5, 5, 5, 0);
		gbc_scrollPane_display.weighty = 0.5;
		gbc_scrollPane_display.weightx = 0.5;
		gbc_scrollPane_display.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_display.gridx = 0;
		gbc_scrollPane_display.gridy = 0;
		frmClippingPolygons.getContentPane().add(scrollPane_display, gbc_scrollPane_display);
		
		final PolygonGraphic display = new PolygonGraphic();
		JPanel panel_display = new JPanel();
		scrollPane_display.setViewportView(panel_display);
		GridBagLayout gbl_panel_display = new GridBagLayout();
		gbl_panel_display.columnWidths = new int[] {600};
		gbl_panel_display.rowHeights = new int[] {500};
		gbl_panel_display.columnWeights = new double[]{1.0};
		gbl_panel_display.rowWeights = new double[]{1.0};
		panel_display.setLayout(gbl_panel_display);
		
		
		GridBagConstraints gbc_display = new GridBagConstraints();
		gbc_display.gridheight = 0;
		gbc_display.gridwidth = 0;
		gbc_display.fill = GridBagConstraints.BOTH;
		gbc_display.gridx = 0;
		gbc_display.gridy = 0;
		panel_display.add(display, gbc_display);
		
		panel_display.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent m) {
				//if(! btnStartDrawing.isEnabled()) {
				//	drawnPol.addVertex(new Point2D.Double((double) m.getX(), (double) m.getY()));
				//}
				
			}
		});
		
		JScrollPane scrollPane_settings = new JScrollPane();
		GridBagConstraints gbc_scrollPane_settings = new GridBagConstraints();
		gbc_scrollPane_settings.insets = new Insets(5, 5, 5, 5);
		gbc_scrollPane_settings.weighty = 1.0;
		gbc_scrollPane_settings.weightx = 1.0;
		gbc_scrollPane_settings.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_settings.gridx = 1;
		gbc_scrollPane_settings.gridy = 0;
		frmClippingPolygons.getContentPane().add(scrollPane_settings, gbc_scrollPane_settings);
		
		JPanel panel = new JPanel();
		scrollPane_settings.setViewportView(panel);
		
		
		
		panel.setBorder(null);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {350};
		gbl_panel.rowHeights = new int[] {120, 270, 130};
		gbl_panel.columnWeights = new double[]{1.0};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 1.0};
		panel.setLayout(gbl_panel);
		
		
		JPanel panel_1 = new JPanel();
		JPanel panel_2 = new JPanel();
		JPanel panel_3 = new JPanel();
		
		
		
		
		
		
		
		
		
		
		panel_1.setBorder(null);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		panel.add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] {180, 180};
		gbl_panel_1.rowHeights = new int[] {30, 30, 30};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0};
		panel_1.setLayout(gbl_panel_1);
		
		
		btnFinishDrawing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//candidatePols.add(new Polygon(drawnPol));
				//list_candidates.setListData(candidatePols);
				drawnPol = new Polygon();
				btnStartDrawing.setEnabled(true);
				btnCancelDrawing.setEnabled(false);
				btnFinishDrawing.setEnabled(false);
			}
		});
		
		txtDrawAPolygon = new JTextField();
		txtDrawAPolygon.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtDrawAPolygon.setBorder(null);
		txtDrawAPolygon.setEditable(false);
		txtDrawAPolygon.setText("Draw a polygon");
		GridBagConstraints gbc_txtDrawAPolygon = new GridBagConstraints();
		gbc_txtDrawAPolygon.fill = GridBagConstraints.BOTH;
		gbc_txtDrawAPolygon.insets = new Insets(0, 0, 5, 5);
		gbc_txtDrawAPolygon.gridx = 0;
		gbc_txtDrawAPolygon.gridy = 0;
		panel_1.add(txtDrawAPolygon, gbc_txtDrawAPolygon);
		txtDrawAPolygon.setColumns(10);
		
		GridBagConstraints gbc_btnStartDrawing = new GridBagConstraints();
		gbc_btnStartDrawing.anchor = GridBagConstraints.NORTH;
		gbc_btnStartDrawing.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnStartDrawing.insets = new Insets(0, 0, 5, 0);
		gbc_btnStartDrawing.gridx = 1;
		gbc_btnStartDrawing.gridy = 0;
		panel_1.add(btnStartDrawing, gbc_btnStartDrawing);
		btnStartDrawing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnFinishDrawing.setEnabled(true);
				btnCancelDrawing.setEnabled(true);
				btnStartDrawing.setEnabled(false);
			}
		});
		
		
		btnCancelDrawing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawnPol = new Polygon();
				btnStartDrawing.setEnabled(true);
				btnFinishDrawing.setEnabled(false);
				btnCancelDrawing.setEnabled(false);
			}
		});
		
		JRadioButton rdbtnClippingPolygon = new JRadioButton("Clipping polygon");
		GridBagConstraints gbc_rdbtnClippingPolygon = new GridBagConstraints();
		gbc_rdbtnClippingPolygon.anchor = GridBagConstraints.NORTH;
		gbc_rdbtnClippingPolygon.fill = GridBagConstraints.HORIZONTAL;
		gbc_rdbtnClippingPolygon.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnClippingPolygon.gridx = 0;
		gbc_rdbtnClippingPolygon.gridy = 1;
		panel_1.add(rdbtnClippingPolygon, gbc_rdbtnClippingPolygon);
		
		/*JButton btnNewButton = new JButton("New button");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.SOUTHEAST;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 1;
		panel_1.add(btnNewButton, gbc_btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				display.draw = true;
				display.repaint();
			}
		});*/
		
		btnCancelDrawing.setEnabled(false);
		GridBagConstraints gbc_btnCancelDrawing = new GridBagConstraints();
		gbc_btnCancelDrawing.anchor = GridBagConstraints.SOUTH;
		gbc_btnCancelDrawing.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCancelDrawing.insets = new Insets(0, 0, 5, 0);
		gbc_btnCancelDrawing.gridx = 1;
		gbc_btnCancelDrawing.gridy = 1;
		panel_1.add(btnCancelDrawing, gbc_btnCancelDrawing);
		
		JRadioButton rdbtnCandidatePolygon = new JRadioButton("Candidate polygon");
		GridBagConstraints gbc_rdbtnCandidatePolygon = new GridBagConstraints();
		gbc_rdbtnCandidatePolygon.anchor = GridBagConstraints.NORTH;
		gbc_rdbtnCandidatePolygon.fill = GridBagConstraints.HORIZONTAL;
		gbc_rdbtnCandidatePolygon.insets = new Insets(0, 0, 0, 5);
		gbc_rdbtnCandidatePolygon.gridx = 0;
		gbc_rdbtnCandidatePolygon.gridy = 2;
		panel_1.add(rdbtnCandidatePolygon, gbc_rdbtnCandidatePolygon);
		btnFinishDrawing.setEnabled(false);
		GridBagConstraints gbc_btnFinishDrawing = new GridBagConstraints();
		gbc_btnFinishDrawing.anchor = GridBagConstraints.SOUTH;
		gbc_btnFinishDrawing.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnFinishDrawing.gridx = 1;
		gbc_btnFinishDrawing.gridy = 2;
		panel_1.add(btnFinishDrawing, gbc_btnFinishDrawing);
		
		
		
		panel_2.setBorder(new MatteBorder(1, 0, 1, 0, (Color) new Color(0, 0, 0)));
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 1;
		panel.add(panel_2, gbc_panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] {120, 120, 120};
		gbl_panel_2.rowHeights = new int[] {35, 200, 35};
		gbl_panel_2.columnWeights = new double[]{0.0, 0.0, 0.0};
		gbl_panel_2.rowWeights = new double[]{1.0, 1.0, 0.0};
		panel_2.setLayout(gbl_panel_2);
		
		txtClippingPolygons = new JTextField();
		txtClippingPolygons.setEditable(false);
		txtClippingPolygons.setHorizontalAlignment(SwingConstants.CENTER);
		txtClippingPolygons.setBorder(null);
		txtClippingPolygons.setText("Clipping polygons");
		GridBagConstraints gbc_txtClippingPolygons = new GridBagConstraints();
		gbc_txtClippingPolygons.anchor = GridBagConstraints.SOUTH;
		gbc_txtClippingPolygons.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtClippingPolygons.insets = new Insets(0, 0, 5, 5);
		gbc_txtClippingPolygons.gridx = 0;
		gbc_txtClippingPolygons.gridy = 0;
		panel_2.add(txtClippingPolygons, gbc_txtClippingPolygons);
		txtClippingPolygons.setColumns(10);
		
		txtCandidatePolygons = new JTextField();
		txtCandidatePolygons.setEditable(false);
		txtCandidatePolygons.setHorizontalAlignment(SwingConstants.CENTER);
		txtCandidatePolygons.setBorder(null);
		txtCandidatePolygons.setText("Candidate polygons");
		GridBagConstraints gbc_txtCandidatePolygons = new GridBagConstraints();
		gbc_txtCandidatePolygons.anchor = GridBagConstraints.SOUTH;
		gbc_txtCandidatePolygons.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCandidatePolygons.insets = new Insets(0, 0, 5, 5);
		gbc_txtCandidatePolygons.gridx = 1;
		gbc_txtCandidatePolygons.gridy = 0;
		panel_2.add(txtCandidatePolygons, gbc_txtCandidatePolygons);
		txtCandidatePolygons.setColumns(10);
		
		txtClippedPolygons = new JTextField();
		txtClippedPolygons.setEditable(false);
		txtClippedPolygons.setHorizontalAlignment(SwingConstants.CENTER);
		txtClippedPolygons.setBorder(null);
		txtClippedPolygons.setText("Clipped polygons");
		GridBagConstraints gbc_txtClippedPolygons = new GridBagConstraints();
		gbc_txtClippedPolygons.anchor = GridBagConstraints.SOUTH;
		gbc_txtClippedPolygons.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtClippedPolygons.insets = new Insets(0, 0, 5, 5);
		gbc_txtClippedPolygons.gridx = 2;
		gbc_txtClippedPolygons.gridy = 0;
		panel_2.add(txtClippedPolygons, gbc_txtClippedPolygons);
		txtClippedPolygons.setColumns(10);
		
		final JList list_clipping = new JList();
		GridBagConstraints gbc_list_clipping = new GridBagConstraints();
		gbc_list_clipping.fill = GridBagConstraints.BOTH;
		gbc_list_clipping.insets = new Insets(0, 0, 5, 5);
		gbc_list_clipping.gridx = 0;
		gbc_list_clipping.gridy = 1;
		panel_2.add(list_clipping, gbc_list_clipping);
		final JList list_candidates = new JList();
		GridBagConstraints gbc_list_candidates = new GridBagConstraints();
		gbc_list_candidates.fill = GridBagConstraints.BOTH;
		gbc_list_candidates.insets = new Insets(0, 0, 5, 5);
		gbc_list_candidates.gridx = 1;
		gbc_list_candidates.gridy = 1;
		panel_2.add(list_candidates, gbc_list_candidates);
		final JList list_clipped = new JList();
		list_clipped.setModel(new AbstractListModel() {
			String[] values = new String[] {"bla", "blub"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		GridBagConstraints gbc_list_clipped = new GridBagConstraints();
		gbc_list_clipped.fill = GridBagConstraints.BOTH;
		gbc_list_clipped.insets = new Insets(0, 0, 5, 5);
		gbc_list_clipped.gridx = 2;
		gbc_list_clipped.gridy = 1;
		panel_2.add(list_clipped, gbc_list_clipped);
		
		JButton btnDelete_1 = new JButton("Delete selected");
		GridBagConstraints gbc_btnDelete_1 = new GridBagConstraints();
		gbc_btnDelete_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnDelete_1.gridx = 0;
		gbc_btnDelete_1.gridy = 2;
		panel_2.add(btnDelete_1, gbc_btnDelete_1);
		
		JButton btnDelete = new JButton("Delete selected");
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.insets = new Insets(0, 0, 5, 5);
		gbc_btnDelete.gridx = 1;
		gbc_btnDelete.gridy = 2;
		panel_2.add(btnDelete, gbc_btnDelete);
		final JButton btnDeletePol = new JButton("Delete selected");
		
		
		btnDeletePol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//list_clipped.remove(list_clipped.getSelectedIndex());
				//txtReport.setText("" + list_clipped.getSelectedIndex() + "\n" + list_clipped.getSelectedValue()); 
			}
		});
		GridBagConstraints gbc_btnDeletePol = new GridBagConstraints();
		gbc_btnDeletePol.insets = new Insets(0, 0, 5, 0);
		gbc_btnDeletePol.gridx = 2;
		gbc_btnDeletePol.gridy = 2;
		panel_2.add(btnDeletePol, gbc_btnDeletePol);
		
		panel_3.setBorder(null);
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.anchor = GridBagConstraints.SOUTH;
		gbc_panel_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 2;
		panel.add(panel_3, gbc_panel_3);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[] {180, 180};
		gbl_panel_3.rowHeights = new int[] {50, 120};
		gbl_panel_3.columnWeights = new double[]{0.0, 0.0};
		gbl_panel_3.rowWeights = new double[]{0.0, 1.0};
		panel_3.setLayout(gbl_panel_3);
		
		
		
		JComboBox comboBox = new JComboBox();
		
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Automatic", "Sutherland-Hodgman", "Weiler-Atherton", "Greiner-Hormann"}));
		comboBox.setToolTipText("");
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 0;
		panel_3.add(comboBox, gbc_comboBox);
		
		JButton btnClipPolygons = new JButton("Clip polygons");
		GridBagConstraints gbc_btnClipPolygons = new GridBagConstraints();
		gbc_btnClipPolygons.insets = new Insets(0, 0, 5, 0);
		gbc_btnClipPolygons.gridx = 1;
		gbc_btnClipPolygons.gridy = 0;
		panel_3.add(btnClipPolygons, gbc_btnClipPolygons);
		
		final JTextArea txtReport = new JTextArea();
		
		txtReport.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagConstraints gbc_txtReport = new GridBagConstraints();
		gbc_txtReport.insets = new Insets(0, 0, 10, 0);
		gbc_txtReport.gridwidth = 2;
		gbc_txtReport.fill = GridBagConstraints.BOTH;
		gbc_txtReport.gridx = 0;
		gbc_txtReport.gridy = 1;
		panel_3.add(txtReport, gbc_txtReport);
		txtReport.setText("Report:");
		
		
		JMenuBar menuBar = new JMenuBar();
		frmClippingPolygons.setJMenuBar(menuBar);
		
	}
}
