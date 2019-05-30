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
import java.awt.ScrollPane;
import java.awt.Toolkit;

import javax.swing.JTextArea;
import java.awt.Dimension;
import javax.swing.border.EtchedBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JComboBox;
import javax.swing.JComponent;
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
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.ScrollPaneConstants;

/**
 * @author Paul
 *
 */
public class GUI {

	private JFrame frmClippingPolygons;
	private Polygon clippingPol;
	private Polygon drawnPol = new Polygon();
	private Vector<Polygon> candidatePols = new Vector<Polygon>();
	
	private PolygonGraphic display = new PolygonGraphic();
	
	private JTextField txtDrawAPolygon;
	private JTextField txtClippingPolygons;
	private JTextField txtCandidatePolygons;
	private JTextField txtClippedPolygons;
	
	private JTextArea txtReport = new JTextArea();
	
	private JButton btnCancelDrawing = new JButton("Cancel drawing");
	private JButton btnFinishDrawing = new JButton("Finish drawing");
	private JButton btnStartDrawing = new JButton("Start drawing");
	private JButton btnDelete_clipped = new JButton("Delete selected");

	private JRadioButton rdbtnClippingPolygon = new JRadioButton("Clipping polygon");
	private JRadioButton rdbtnCandidatePolygon = new JRadioButton("Candidate polygon");
	
	private JList list_clipping = new JList();
	private JList list_candidates = new JList();
	private JList list_clipped = new JList();
	
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
		
	/**
	* Frame.
	*/
		
		frmClippingPolygons = new JFrame();
		frmClippingPolygons.setTitle("Clipping polygons");
		frmClippingPolygons.setName("frame");
		frmClippingPolygons.setBounds(100, 100, 1060, 630);
		frmClippingPolygons.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {600, 360};
		gridBagLayout.rowHeights = new int[] {450};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0};
		gridBagLayout.rowWeights = new double[]{1.0};
		frmClippingPolygons.getContentPane().setLayout(gridBagLayout);
		
	/**
	* Display.
	*/
		
		JPanel panel_display_out = new JPanel();
		panel_display_out.setBorder(null);
		GridBagConstraints gbc_panel_display_out = new GridBagConstraints();
		gbc_panel_display_out.fill = GridBagConstraints.BOTH;
		gbc_panel_display_out.insets = new Insets(0, 0, 0, 5);
		gbc_panel_display_out.gridx = 0;
		gbc_panel_display_out.gridy = 0;
		frmClippingPolygons.getContentPane().add(panel_display_out, gbc_panel_display_out);
		GridBagLayout gbl_panel_display_out = new GridBagLayout();
		gbl_panel_display_out.columnWidths = new int[]{600, 0};
		gbl_panel_display_out.rowHeights = new int[]{450, 0};
		gbl_panel_display_out.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_display_out.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel_display_out.setLayout(gbl_panel_display_out);
		
		JScrollPane scrollPane_display = new JScrollPane();
		scrollPane_display.setBorder(null);
		GridBagConstraints gbc_scrollPane_display = new GridBagConstraints();
		gbc_scrollPane_display.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_display.gridx = 0;
		gbc_scrollPane_display.gridy = 0;
		panel_display_out.add(scrollPane_display, gbc_scrollPane_display);
		
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
				if(! btnStartDrawing.isEnabled()) {
					drawnPol.addVertex(new Point2D.Double((double) m.getX(), (double) m.getY()));
				}
			}
		});
		
	/**
	* Settings.
	*/
		
		JPanel panel_settings_out = new JPanel();
		panel_settings_out.setBorder(null);
		GridBagConstraints gbc_panel_settings_out = new GridBagConstraints();
		gbc_panel_settings_out.anchor = GridBagConstraints.EAST;
		gbc_panel_settings_out.fill = GridBagConstraints.VERTICAL;
		gbc_panel_settings_out.gridx = 1;
		gbc_panel_settings_out.gridy = 0;
		frmClippingPolygons.getContentPane().add(panel_settings_out, gbc_panel_settings_out);
		GridBagLayout gbl_panel_settings_out = new GridBagLayout();
		gbl_panel_settings_out.columnWidths = new int[]{360, 0};
		gbl_panel_settings_out.rowHeights = new int[]{450, 0};
		gbl_panel_settings_out.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel_settings_out.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel_settings_out.setLayout(gbl_panel_settings_out);
		
		JScrollPane scrollPane_settings = new JScrollPane();
		scrollPane_settings.setMinimumSize(new Dimension(410, 29));
		GridBagConstraints gbc_scrollPane_settings = new GridBagConstraints();
		gbc_scrollPane_settings.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_settings.gridx = 0;
		gbc_scrollPane_settings.gridy = 0;
		panel_settings_out.add(scrollPane_settings, gbc_scrollPane_settings);
		scrollPane_settings.setBorder(new EmptyBorder(3, 3, 3, 3));
		
		JPanel panel_settings = new JPanel();
		scrollPane_settings.setViewportView(panel_settings);
		panel_settings.setBorder(null);
		GridBagLayout gbl_panel_settings = new GridBagLayout();
		gbl_panel_settings.columnWidths = new int[] {350};
		gbl_panel_settings.rowHeights = new int[] {120, 270, 130};
		gbl_panel_settings.columnWeights = new double[]{1.0};
		gbl_panel_settings.rowWeights = new double[]{0.0, 0.0, 1.0};
		panel_settings.setLayout(gbl_panel_settings);
		
	/**
	* Settings - Panel Draw.
	*/
		
		JPanel panel_draw = new JPanel();
		panel_draw.setBorder(null);
		GridBagConstraints gbc_panel_draw = new GridBagConstraints();
		gbc_panel_draw.fill = GridBagConstraints.BOTH;
		gbc_panel_draw.gridx = 0;
		gbc_panel_draw.gridy = 0;
		panel_settings.add(panel_draw, gbc_panel_draw);
		GridBagLayout gbl_panel_draw = new GridBagLayout();
		gbl_panel_draw.columnWidths = new int[] {180, 180};
		gbl_panel_draw.rowHeights = new int[] {30, 30, 30};
		gbl_panel_draw.columnWeights = new double[]{0.0, 0.0};
		gbl_panel_draw.rowWeights = new double[]{0.0, 0.0, 0.0};
		panel_draw.setLayout(gbl_panel_draw);
		
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
		panel_draw.add(txtDrawAPolygon, gbc_txtDrawAPolygon);
		txtDrawAPolygon.setColumns(10);
		
		rdbtnClippingPolygon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(! rdbtnClippingPolygon.isSelected())
					rdbtnClippingPolygon.setSelected(true);
				else
					rdbtnCandidatePolygon.setSelected(false);
			}
		});
		rdbtnClippingPolygon.setSelected(true);
		GridBagConstraints gbc_rdbtnClippingPolygon = new GridBagConstraints();
		gbc_rdbtnClippingPolygon.anchor = GridBagConstraints.NORTH;
		gbc_rdbtnClippingPolygon.fill = GridBagConstraints.HORIZONTAL;
		gbc_rdbtnClippingPolygon.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnClippingPolygon.gridx = 0;
		gbc_rdbtnClippingPolygon.gridy = 1;
		panel_draw.add(rdbtnClippingPolygon, gbc_rdbtnClippingPolygon);
		
		rdbtnCandidatePolygon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(! rdbtnCandidatePolygon.isSelected())
					rdbtnCandidatePolygon.setSelected(true);
				else
					rdbtnClippingPolygon.setSelected(false);
			}
		});
		GridBagConstraints gbc_rdbtnCandidatePolygon = new GridBagConstraints();
		gbc_rdbtnCandidatePolygon.anchor = GridBagConstraints.NORTH;
		gbc_rdbtnCandidatePolygon.fill = GridBagConstraints.HORIZONTAL;
		gbc_rdbtnCandidatePolygon.insets = new Insets(0, 0, 0, 5);
		gbc_rdbtnCandidatePolygon.gridx = 0;
		gbc_rdbtnCandidatePolygon.gridy = 2;
		panel_draw.add(rdbtnCandidatePolygon, gbc_rdbtnCandidatePolygon);
		
		GridBagConstraints gbc_btnStartDrawing = new GridBagConstraints();
		gbc_btnStartDrawing.anchor = GridBagConstraints.NORTH;
		gbc_btnStartDrawing.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnStartDrawing.insets = new Insets(0, 0, 5, 0);
		gbc_btnStartDrawing.gridx = 1;
		gbc_btnStartDrawing.gridy = 0;
		panel_draw.add(btnStartDrawing, gbc_btnStartDrawing);
		btnStartDrawing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnFinishDrawing.setEnabled(true);
				btnCancelDrawing.setEnabled(true);
				btnStartDrawing.setEnabled(false);
			}
		});
		
		btnCancelDrawing.setEnabled(false);
		GridBagConstraints gbc_btnCancelDrawing = new GridBagConstraints();
		gbc_btnCancelDrawing.anchor = GridBagConstraints.SOUTH;
		gbc_btnCancelDrawing.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCancelDrawing.insets = new Insets(0, 0, 5, 0);
		gbc_btnCancelDrawing.gridx = 1;
		gbc_btnCancelDrawing.gridy = 1;
		panel_draw.add(btnCancelDrawing, gbc_btnCancelDrawing);
		btnCancelDrawing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawnPol = new Polygon();
				btnStartDrawing.setEnabled(true);
				btnFinishDrawing.setEnabled(false);
				btnCancelDrawing.setEnabled(false);
			}
		});
		
		btnFinishDrawing.setEnabled(false);
		GridBagConstraints gbc_btnFinishDrawing = new GridBagConstraints();
		gbc_btnFinishDrawing.anchor = GridBagConstraints.SOUTH;
		gbc_btnFinishDrawing.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnFinishDrawing.gridx = 1;
		gbc_btnFinishDrawing.gridy = 2;
		panel_draw.add(btnFinishDrawing, gbc_btnFinishDrawing);
		btnFinishDrawing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				candidatePols.add(drawnPol);
				//list_candidates.setListData(candidatePols);
				drawnPol = new Polygon();
				btnStartDrawing.setEnabled(true);
				btnCancelDrawing.setEnabled(false);
				btnFinishDrawing.setEnabled(false);
			}
		});
		
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
		
	/**
	* Settings - Panel Manage.
	*/
		
		JPanel panel_manage = new JPanel();
		panel_manage.setBorder(new MatteBorder(1, 0, 1, 0, (Color) new Color(0, 0, 0)));
		GridBagConstraints gbc_panel_manage = new GridBagConstraints();
		gbc_panel_manage.fill = GridBagConstraints.BOTH;
		gbc_panel_manage.gridx = 0;
		gbc_panel_manage.gridy = 1;
		panel_settings.add(panel_manage, gbc_panel_manage);
		GridBagLayout gbl_panel_manage = new GridBagLayout();
		gbl_panel_manage.columnWidths = new int[] {120, 120, 120};
		gbl_panel_manage.rowHeights = new int[] {35, 200, 35};
		gbl_panel_manage.columnWeights = new double[]{0.0, 0.0, 0.0};
		gbl_panel_manage.rowWeights = new double[]{1.0, 1.0, 0.0};
		panel_manage.setLayout(gbl_panel_manage);
		
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
		panel_manage.add(txtClippingPolygons, gbc_txtClippingPolygons);
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
		panel_manage.add(txtCandidatePolygons, gbc_txtCandidatePolygons);
		txtCandidatePolygons.setColumns(10);
		
		txtClippedPolygons = new JTextField();
		txtClippedPolygons.setEditable(false);
		txtClippedPolygons.setHorizontalAlignment(SwingConstants.CENTER);
		txtClippedPolygons.setBorder(null);
		txtClippedPolygons.setText("Clipped polygons");
		GridBagConstraints gbc_txtClippedPolygons = new GridBagConstraints();
		gbc_txtClippedPolygons.anchor = GridBagConstraints.SOUTH;
		gbc_txtClippedPolygons.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtClippedPolygons.insets = new Insets(0, 0, 5, 0);
		gbc_txtClippedPolygons.gridx = 2;
		gbc_txtClippedPolygons.gridy = 0;
		panel_manage.add(txtClippedPolygons, gbc_txtClippedPolygons);
		txtClippedPolygons.setColumns(10);
		
		JScrollPane scrollPane_listClipping = new JScrollPane();
		GridBagConstraints gbc_scrollPane_listClipping = new GridBagConstraints();
		gbc_scrollPane_listClipping.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_listClipping.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_listClipping.gridx = 0;
		gbc_scrollPane_listClipping.gridy = 1;
		panel_manage.add(scrollPane_listClipping, gbc_scrollPane_listClipping);
		scrollPane_listClipping.setViewportView(list_clipping);
		
		list_clipping.setBorder(null);
		list_clipping.setModel(new AbstractListModel() {
			String[] values = new String[] {"Clipping Polygon 1", "Clipping Polygon 2"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list_clipping.setSelectedIndex(0);
		
		JScrollPane scrollPane_listCandidate = new JScrollPane();
		GridBagConstraints gbc_scrollPane_listCandidate = new GridBagConstraints();
		gbc_scrollPane_listCandidate.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_listCandidate.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_listCandidate.gridx = 1;
		gbc_scrollPane_listCandidate.gridy = 1;
		panel_manage.add(scrollPane_listCandidate, gbc_scrollPane_listCandidate);
		scrollPane_listCandidate.setViewportView(list_candidates);
		
		list_candidates.setBorder(null);
		list_candidates.setModel(new AbstractListModel() {
			String[] values = new String[] {"Candidate Polygon 1", "Candidate Polygon 2", "Candidate Polygon 3", "Candidate Polygon 4", "Candidate Polygon 5", "Candidate Polygon 6", "Candidate Polygon 7", "Candidate Polygon 8", "Candidate Polygon 9", "Candidate Polygon 10", "Candidate Polygon 11", "Candidate Polygon 12", "Candidate Polygon 13", "Candidate Polygon 14", "Candidate Polygon 15", "Candidate Polygon 16", "Candidate Polygon 17", "Candidate Polygon 18"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list_candidates.setSelectedIndex(0);
		
		JScrollPane scrollPane_listClipped = new JScrollPane();
		GridBagConstraints gbc_scrollPane_listClipped = new GridBagConstraints();
		gbc_scrollPane_listClipped.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_listClipped.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_listClipped.gridx = 2;
		gbc_scrollPane_listClipped.gridy = 1;
		panel_manage.add(scrollPane_listClipped, gbc_scrollPane_listClipped);
		scrollPane_listClipped.setViewportView(list_clipped);
		
		list_clipped.setBorder(null);
		list_clipped.setModel(new AbstractListModel() {
			String[] values = new String[] {"Clipped Polygon 1", "Clipped Polygon 2", "Clipped Polygon 3"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list_clipped.setSelectedIndex(0);
		
		JButton btnDelete_clipping = new JButton("Delete selected");
		GridBagConstraints gbc_btnDelete_clipping = new GridBagConstraints();
		gbc_btnDelete_clipping.insets = new Insets(0, 0, 0, 5);
		gbc_btnDelete_clipping.gridx = 0;
		gbc_btnDelete_clipping.gridy = 2;
		panel_manage.add(btnDelete_clipping, gbc_btnDelete_clipping);
		
		JButton btnDelete_candidate = new JButton("Delete selected");
		GridBagConstraints gbc_btnDelete_candidate = new GridBagConstraints();
		gbc_btnDelete_candidate.insets = new Insets(0, 0, 0, 5);
		gbc_btnDelete_candidate.gridx = 1;
		gbc_btnDelete_candidate.gridy = 2;
		panel_manage.add(btnDelete_candidate, gbc_btnDelete_candidate);
		
		btnDelete_clipped.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtReport.setText("" + list_clipped.getSelectedIndex() + "\n" + list_clipped.getSelectedValue());
				list_clipped.remove(list_clipped.getSelectedIndex());
				list_clipped.repaint();
				list_clipped.revalidate();
				list_clipped.updateUI();
				list_clipped.validate();
			}
		});
		GridBagConstraints gbc_btnDelete_clipped = new GridBagConstraints();
		gbc_btnDelete_clipped.gridx = 2;
		gbc_btnDelete_clipped.gridy = 2;
		panel_manage.add(btnDelete_clipped, gbc_btnDelete_clipped);
		
	/**
	* Settings - Panel Run.
	*/
		
		JPanel panel_run = new JPanel();
		panel_run.setBorder(null);
		GridBagConstraints gbc_panel_run = new GridBagConstraints();
		gbc_panel_run.fill = GridBagConstraints.BOTH;
		gbc_panel_run.gridx = 0;
		gbc_panel_run.gridy = 2;
		panel_settings.add(panel_run, gbc_panel_run);
		GridBagLayout gbl_panel_run = new GridBagLayout();
		gbl_panel_run.columnWidths = new int[] {180, 180};
		gbl_panel_run.rowHeights = new int[] {50, 120};
		gbl_panel_run.columnWeights = new double[]{0.0, 0.0};
		gbl_panel_run.rowWeights = new double[]{0.0, 1.0};
		panel_run.setLayout(gbl_panel_run);
		
		JComboBox comboBox_algorithms = new JComboBox();
		comboBox_algorithms.setModel(new DefaultComboBoxModel(new String[] {"Automatic", "Sutherland-Hodgman", "Weiler-Atherton", "Greiner-Hormann"}));
		comboBox_algorithms.setToolTipText("");
		GridBagConstraints gbc_comboBox_algorithms = new GridBagConstraints();
		gbc_comboBox_algorithms.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_algorithms.gridx = 0;
		gbc_comboBox_algorithms.gridy = 0;
		panel_run.add(comboBox_algorithms, gbc_comboBox_algorithms);
		
		JButton btnClipPolygons = new JButton("Clip polygons");
		GridBagConstraints gbc_btnClipPolygons = new GridBagConstraints();
		gbc_btnClipPolygons.insets = new Insets(0, 0, 5, 0);
		gbc_btnClipPolygons.gridx = 1;
		gbc_btnClipPolygons.gridy = 0;
		panel_run.add(btnClipPolygons, gbc_btnClipPolygons);
		
		txtReport.setEditable(false);
		txtReport.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "Report", TitledBorder.CENTER, TitledBorder.BELOW_TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_txtReport = new GridBagConstraints();
		gbc_txtReport.fill = GridBagConstraints.BOTH;
		gbc_txtReport.insets = new Insets(0, 0, 10, 0);
		gbc_txtReport.gridwidth = 2;
		gbc_txtReport.gridx = 0;
		gbc_txtReport.gridy = 1;
		panel_run.add(txtReport, gbc_txtReport);
		txtReport.setText("Success.\r\n\r\nAlgorithm Greiner-Hormann was used.");
		
	/**
	* Menu bar.
	*/
		
		JMenuBar menuBar = new JMenuBar();
		frmClippingPolygons.setJMenuBar(menuBar);
		
	}
}
