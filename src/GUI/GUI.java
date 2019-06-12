package GUI;

import Polygon.*;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JTextArea;
import java.awt.Dimension;
import javax.swing.border.BevelBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;

/**
 * @author Paul
 * @version 1.01 June 10th 2019
 */
public class GUI {

	private JFrame frmClippingPolygons;
	
	private ArrayList<Polygon> clippingPols = new ArrayList<Polygon>();
	private ArrayList<Polygon> candidatePols = new ArrayList<Polygon>();
	private ArrayList<Polygon> clippedPols = new ArrayList<Polygon>();
	private Polygon drawnPol = new Polygon();
	
	private PolygonGraphic display = new PolygonGraphic();
	
	private JLabel txtDrawAPolygon;
	private JLabel txtClippingPolygons;
	private JLabel txtCandidatePolygons;
	private JLabel txtClippedPolygons;
	
	private JTextArea txtReport = new JTextArea();
	
	private JButton btnCancelDrawing = new JButton("Cancel drawing");
	private JButton btnFinishDrawing = new JButton("Finish drawing");
	private JButton btnStartDrawing = new JButton("Start drawing");
	private JButton btnDelete_clipped = new JButton("Delete selected");

	private JRadioButton rdbtnClippingPolygon = new JRadioButton("Clipping polygon");
	private JRadioButton rdbtnCandidatePolygon = new JRadioButton("Candidate polygon");
	
	private JList<String> list_clipping = new JList<String>();
	private JList<String> list_candidates = new JList<String>();
	private JList<String> list_clipped = new JList<String>();
	
	private ArrayList<Integer> indicesClipping = new ArrayList<Integer>();
	private ArrayList<Integer> indicesCandidates = new ArrayList<Integer>();
	
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
		
		// set model for JLists, such that deleting elements is possible.
		list_clipping.setModel(new DefaultListModel<String>());
		DefaultListModel<String> model_clipping = (DefaultListModel<String>) list_clipping.getModel();
		list_candidates.setModel(new DefaultListModel<String>());
		DefaultListModel<String> model_candidates = (DefaultListModel<String>) list_candidates.getModel();
		list_clipped.setModel(new DefaultListModel<String>());
		DefaultListModel<String> model_clipped = (DefaultListModel<String>) list_clipped.getModel();
		
		list_clipping.setFixedCellWidth(50);
		list_candidates.setFixedCellWidth(50);
		list_clipped.setFixedCellWidth(50);
		
	/**
	* Frame.
	*/
		
		frmClippingPolygons = new JFrame();
		frmClippingPolygons.setTitle("Clipping polygons");
		frmClippingPolygons.setName("frame");
		frmClippingPolygons.setBounds(100, 100, 1123, 698);
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
	
		
		display.setDrawnPolygon(drawnPol);
		display.setCandidatePolygons(candidatePols);
		display.setClippingPolygons(clippingPols);
		display.setClippedPolygons(clippedPols);
		display.setListCandidatePolygons(list_candidates);
		display.setListClippingPolygons(list_clipping);
		display.setListClippedPolygons(list_clipped);
		
		panel_display.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent m) {
				if(! btnStartDrawing.isEnabled()) {
					drawnPol.addVertex(new Point2D.Double(m.getX()-display.getWidth()/2, m.getY()-display.getHeight()/2));
					display.repaint();
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
		
		txtDrawAPolygon = new JLabel();
		txtDrawAPolygon.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtDrawAPolygon.setBorder(null);
		txtDrawAPolygon.setText("Draw a polygon");
		GridBagConstraints gbc_txtDrawAPolygon = new GridBagConstraints();
		gbc_txtDrawAPolygon.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDrawAPolygon.insets = new Insets(0, 0, 5, 5);
		gbc_txtDrawAPolygon.gridx = 0;
		gbc_txtDrawAPolygon.gridy = 0;
		panel_draw.add(txtDrawAPolygon, gbc_txtDrawAPolygon);
		
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
		gbc_rdbtnCandidatePolygon.fill = GridBagConstraints.HORIZONTAL;
		gbc_rdbtnCandidatePolygon.insets = new Insets(0, 0, 0, 5);
		gbc_rdbtnCandidatePolygon.gridx = 0;
		gbc_rdbtnCandidatePolygon.gridy = 2;
		panel_draw.add(rdbtnCandidatePolygon, gbc_rdbtnCandidatePolygon);
		
		GridBagConstraints gbc_btnStartDrawing = new GridBagConstraints();
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
		gbc_btnCancelDrawing.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCancelDrawing.insets = new Insets(0, 0, 5, 0);
		gbc_btnCancelDrawing.gridx = 1;
		gbc_btnCancelDrawing.gridy = 1;
		panel_draw.add(btnCancelDrawing, gbc_btnCancelDrawing);
		btnCancelDrawing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawnPol = new Polygon();
				display.setDrawnPolygon(drawnPol);
				display.repaint();
				btnStartDrawing.setEnabled(true);
				btnFinishDrawing.setEnabled(false);
				btnCancelDrawing.setEnabled(false);
			}
		});
		
		btnFinishDrawing.setEnabled(false);
		GridBagConstraints gbc_btnFinishDrawing = new GridBagConstraints();
		gbc_btnFinishDrawing.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnFinishDrawing.gridx = 1;
		gbc_btnFinishDrawing.gridy = 2;
		panel_draw.add(btnFinishDrawing, gbc_btnFinishDrawing);
		btnFinishDrawing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnClippingPolygon.isSelected()) {
					int i = getFreeIndex(indicesClipping);
					indicesClipping.add(i, i);
					model_clipping.add(i, "Clipping P. " + (i + 1));
					clippingPols.add(i, drawnPol);
				}
				if(rdbtnCandidatePolygon.isSelected()) {
					int i = getFreeIndex(indicesCandidates);
					indicesCandidates.add(i, i);
					model_candidates.add(i, "Candidate P. " + (i + 1));
					candidatePols.add(i, drawnPol);
				}
				drawnPol = new Polygon();
				display.setDrawnPolygon(drawnPol);
				display.repaint();
				btnStartDrawing.setEnabled(true);
				btnCancelDrawing.setEnabled(false);
				btnFinishDrawing.setEnabled(false);
			}
		});
		
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
		
		txtClippingPolygons = new JLabel();
		txtClippingPolygons.setHorizontalAlignment(SwingConstants.CENTER);
		txtClippingPolygons.setBorder(null);
		txtClippingPolygons.setText("Clipping polygons");
		GridBagConstraints gbc_txtClippingPolygons = new GridBagConstraints();
		gbc_txtClippingPolygons.insets = new Insets(0, 5, 5, 0);
		gbc_txtClippingPolygons.anchor = GridBagConstraints.SOUTH;
		gbc_txtClippingPolygons.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtClippingPolygons.gridx = 0;
		gbc_txtClippingPolygons.gridy = 0;
		panel_manage.add(txtClippingPolygons, gbc_txtClippingPolygons);
		
		txtCandidatePolygons = new JLabel();
		txtCandidatePolygons.setHorizontalAlignment(SwingConstants.CENTER);
		txtCandidatePolygons.setBorder(null);
		txtCandidatePolygons.setText("Candidate polygons");
		GridBagConstraints gbc_txtCandidatePolygons = new GridBagConstraints();
		gbc_txtCandidatePolygons.anchor = GridBagConstraints.SOUTH;
		gbc_txtCandidatePolygons.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCandidatePolygons.insets = new Insets(0, 0, 5, 0);
		gbc_txtCandidatePolygons.gridx = 1;
		gbc_txtCandidatePolygons.gridy = 0;
		panel_manage.add(txtCandidatePolygons, gbc_txtCandidatePolygons);
		
		txtClippedPolygons = new JLabel();
		txtClippedPolygons.setHorizontalAlignment(SwingConstants.CENTER);
		txtClippedPolygons.setBorder(null);
		txtClippedPolygons.setText("Clipped polygons");
		GridBagConstraints gbc_txtClippedPolygons = new GridBagConstraints();
		gbc_txtClippedPolygons.anchor = GridBagConstraints.SOUTH;
		gbc_txtClippedPolygons.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtClippedPolygons.insets = new Insets(0, 0, 5, 5);
		gbc_txtClippedPolygons.gridx = 2;
		gbc_txtClippedPolygons.gridy = 0;
		panel_manage.add(txtClippedPolygons, gbc_txtClippedPolygons);
		
		JScrollPane scrollPane_listClipping = new JScrollPane();
		GridBagConstraints gbc_scrollPane_listClipping = new GridBagConstraints();
		gbc_scrollPane_listClipping.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_listClipping.insets = new Insets(0, 5, 5, 0);
		gbc_scrollPane_listClipping.gridx = 0;
		gbc_scrollPane_listClipping.gridy = 1;
		panel_manage.add(scrollPane_listClipping, gbc_scrollPane_listClipping);
		list_clipping.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_listClipping.setViewportView(list_clipping);
		
		list_clipping.setBorder(null);
		list_clipping.setSelectedIndex(0);
		list_clipping.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				display.repaint();
			}
			
		});
		
		JScrollPane scrollPane_listCandidate = new JScrollPane();
		GridBagConstraints gbc_scrollPane_listCandidate = new GridBagConstraints();
		gbc_scrollPane_listCandidate.insets = new Insets(0, 5, 5, 5);
		gbc_scrollPane_listCandidate.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_listCandidate.gridx = 1;
		gbc_scrollPane_listCandidate.gridy = 1;
		panel_manage.add(scrollPane_listCandidate, gbc_scrollPane_listCandidate);
		scrollPane_listCandidate.setViewportView(list_candidates);
		
		list_candidates.setBorder(null);
		list_candidates.setSelectedIndex(0);
		list_candidates.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				display.repaint();
			}
			
		});
		
		JScrollPane scrollPane_listClipped = new JScrollPane();
		GridBagConstraints gbc_scrollPane_listClipped = new GridBagConstraints();
		gbc_scrollPane_listClipped.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_listClipped.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_listClipped.gridx = 2;
		gbc_scrollPane_listClipped.gridy = 1;
		panel_manage.add(scrollPane_listClipped, gbc_scrollPane_listClipped);
		scrollPane_listClipped.setViewportView(list_clipped);
		
		list_clipped.setBorder(null);
		list_clipped.setSelectedIndex(0);
		list_clipped.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				display.repaint();
			}
			
		});
		
		JButton btnDelete_clipping = new JButton("Delete selected");
		btnDelete_clipping.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				while (!list_clipping.isSelectionEmpty()) {
					int selectedIndex = list_clipping.getSelectedIndex();
					indicesClipping.remove(selectedIndex);
					model_clipping.remove(selectedIndex);
					clippingPols.remove(selectedIndex);
				}
			}
		});
		GridBagConstraints gbc_btnDelete_clipping = new GridBagConstraints();
		gbc_btnDelete_clipping.insets = new Insets(0, 5, 5, 0);
		gbc_btnDelete_clipping.gridx = 0;
		gbc_btnDelete_clipping.gridy = 2;
		panel_manage.add(btnDelete_clipping, gbc_btnDelete_clipping);
		
		JButton btnDelete_candidate = new JButton("Delete selected");
		btnDelete_candidate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				while (!list_candidates.isSelectionEmpty()) {
					int selectedIndex = list_candidates.getSelectedIndex();
					indicesCandidates.remove(selectedIndex);
					model_candidates.remove(selectedIndex);
					candidatePols.remove(selectedIndex);
				}
			}
		});
		GridBagConstraints gbc_btnDelete_candidate = new GridBagConstraints();
		gbc_btnDelete_candidate.insets = new Insets(0, 5, 5, 5);
		gbc_btnDelete_candidate.gridx = 1;
		gbc_btnDelete_candidate.gridy = 2;
		panel_manage.add(btnDelete_candidate, gbc_btnDelete_candidate);
		
		btnDelete_clipped.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				while (!list_clipped.isSelectionEmpty()) {
					int selectedIndex = list_clipped.getSelectedIndex();
					model_clipped.remove(selectedIndex);
					clippedPols.remove(selectedIndex);
				}
			}
		});
		GridBagConstraints gbc_btnDelete_clipped = new GridBagConstraints();
		gbc_btnDelete_clipped.insets = new Insets(0, 0, 5, 5);
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
		
		JComboBox<String> comboBox_algorithms = new JComboBox<String>();
		comboBox_algorithms.setModel(new DefaultComboBoxModel<String>(new String[] {"Automatic", "Sutherland-Hodgman", "Weiler-Atherton", "Greiner-Hormann"}));
		comboBox_algorithms.setToolTipText("");
		GridBagConstraints gbc_comboBox_algorithms = new GridBagConstraints();
		gbc_comboBox_algorithms.gridx = 0;
		gbc_comboBox_algorithms.gridy = 0;
		panel_run.add(comboBox_algorithms, gbc_comboBox_algorithms);
		
		JButton btnClipPolygons = new JButton("Clip polygons");
		GridBagConstraints gbc_btnClipPolygons = new GridBagConstraints();
		gbc_btnClipPolygons.gridx = 1;
		gbc_btnClipPolygons.gridy = 0;
		panel_run.add(btnClipPolygons, gbc_btnClipPolygons);
		txtReport.setWrapStyleWord(true);
		txtReport.setLineWrap(true);
		
		btnClipPolygons.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(list_clipping.isSelectionEmpty()) {
					printReport("", 0, -1, -1, false, true);
					return;
				}
				if(list_candidates.isSelectionEmpty()) {
					printReport("", -1, 0, -1, false, true);
					return;
				}
				
				String algorithm = comboBox_algorithms.getSelectedItem().toString();
				boolean automaticChosen = algorithm.equals("Automatic");
				Polygon clippingPol = clippingPols.get(list_clipping.getSelectedIndex());
				Set<Polygon> candidatePolsSelected = new HashSet<Polygon>();
				for(int i = 0; i < candidatePols.size(); i++) {
					if(list_candidates.isSelectedIndex(i)) {
						candidatePolsSelected.add(candidatePols.get(i));
					}
				}
				
				if(automaticChosen) {
					algorithm = "Sutherland-Hodgman";
					if(! clippingPol.isConvex()) {
						algorithm = "Weiler-Atherton";
					}
					if(clippingPol.isSelfIntersecting()) {
						algorithm = "Greiner-Hormann";
					}
				}
				
				// Run algorithm
				boolean error = true;
				clippedPols.clear();
				model_clipped.removeAllElements();
				ClippingAlgorithm clippingAlgorithm = new ClippingAlgorithm();
				clippingAlgorithm.setClippingPolygon(clippingPol);
				clippingAlgorithm.addCandidatePolygon(candidatePolsSelected);
				if(algorithm.equals("Sutherland-Hodgman")) {
					if(clippingAlgorithm.SutherlandHodgman()) {
						for(Polygon p : clippingAlgorithm.getResult()) {
							clippedPols.add(p);
						}
						error = false;
					}
				}
				else if(algorithm.equals("Weiler-Atherton")) {
					if(clippingAlgorithm.WeilerAtherton()) {
						for(Polygon p : clippingAlgorithm.getResult()) {
							clippedPols.add(p);
						}
						error = false;
					}
				}
				else if(algorithm.equals("Greiner-Hormann")) {
					if(clippingAlgorithm.GreinerHorman()) {
						for(Polygon p : clippingAlgorithm.getResult()) {
							clippedPols.add(p);
						}
						error = false;
					}
				}
				for(int i = 0; i < clippedPols.size(); i++) {
					model_clipped.addElement("Clipped P. " + (i + 1));
				}
				
				printReport(algorithm, 1, candidatePolsSelected.size(), clippedPols.size(), automaticChosen, error);
				display.repaint();
			}
			
		});
		
		txtReport.setEditable(false);
		txtReport.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "Report", TitledBorder.CENTER, TitledBorder.BELOW_TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_txtReport = new GridBagConstraints();
		gbc_txtReport.fill = GridBagConstraints.BOTH;
		gbc_txtReport.insets = new Insets(0, 0, 10, 0);
		gbc_txtReport.gridwidth = 2;
		gbc_txtReport.gridx = 0;
		gbc_txtReport.gridy = 1;
		panel_run.add(txtReport, gbc_txtReport);
		txtReport.setText("No report yet.\n\nClip polygons to get a report.");
		
	/**
	* Menu bar.
	*/
		
		JMenuBar menuBar = new JMenuBar();
		frmClippingPolygons.setJMenuBar(menuBar);
		
		JMenu mnPolygon = new JMenu("Polygons");
		menuBar.add(mnPolygon);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmHelp = new JMenuItem("Help");
		mntmHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HelpPane helpPane = new HelpPane();
				helpPane.setVisible(true);
			}
		});
		mntmHelp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		mnHelp.add(mntmHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String aboutString = "<html>";
				aboutString += "<h1>Clipping polygons</h1>";
				aboutString += "<p>This program was developed in the course Software Technology";
				aboutString += " at JKU Linz by";
				aboutString += "<ul><li>Paul Kainberger</li><li>Jakob Moosbauer</li>";
				aboutString += "<li>Philipp Nuspl</li></ul>";
				aboutString += "in spring 2019.</p>";
				aboutString += "<p>Version 1.0, 26.7.2019</p>";
				aboutString += "</html>";
				JOptionPane.showMessageDialog(frmClippingPolygons,
						aboutString,
					    "About",
					    JOptionPane.PLAIN_MESSAGE);
			}
		});
		mnHelp.add(mntmAbout);
		
	}
	
	/**
	 * In the ordered ArrayList<Integer> list, the method finds the first index i, such that
	 * list.get(i) != i.
	 * @param list An ordered ArrayList containing nonnegative integers without duplicates.
	 * @return The first index i, such that list.get(i) != i.
	 */
	private int getFreeIndex(ArrayList<Integer> list) {
		if(list.isEmpty())
			return 0;
		int i = 0;
		while (i < list.size() && list.get(i) == i) {
			i++;
		}
		return i;
	}
	
	private void printReport(String algorithm, int numberSelectedClipping, int numberSelectedCandidate, int numberClipped, boolean automaticChosen, boolean error) {
		String s = new String();
		if(error) {
			s = "Error.\n\n";
			if(numberSelectedClipping != 1) {
				s += "Please select a clipping polygon from the list above which should be used.";
			}
			else if(numberSelectedCandidate < 1) {
				s += "Please select candidate polygons from the list above which should be used.";
			}
			else if(automaticChosen) {
				s += "None of the provided algorithms are applicable to the chosen polygons.";
				s += "Please open the 'Help' window from the menu to learn which polygons are suitable.";
			}
			else {
				s += "The algorithm '" + algorithm + "' ist not applicable to the chosen polygons.";
				s += "Please open the 'Help' window from the menu to learn which polygons are suitable or ";
				s += "select the algorithm 'Automatic' to let the program decide on a suitable algorithm.";
			}
		}
		else {
			s = "Success.\n\n";
			s += "Algorithm '" + algorithm + "' was used.\n\n";
			s += numberSelectedCandidate + " candidate polygon";
			if(numberSelectedCandidate > 1)
				s += "s were ";
			else
				s += " was ";
			s += "clipped against " + numberSelectedClipping + " clipping polygon.\n";
			s += "The result is " + numberClipped + " clipped polygon";
			if(numberClipped != 1)
				s += "s";
			s += ".";
		}
		txtReport.setText(s);
	}
}
