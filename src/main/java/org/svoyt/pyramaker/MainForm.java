/*
 * Copyright 2012 Sergey Voyteshonok info@svoyt.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.svoyt.pyramaker;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.Timer;

import org.svoyt.pyramaker.engine.ImageDownloader;
import org.svoyt.pyramaker.engine.ImageDownloaderAsync;
import org.svoyt.pyramaker.engine.ImageDownloaderParams;
import org.svoyt.pyramaker.engine.Rectangle;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.text.NumberFormat;
import javax.swing.JCheckBox;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;


/**
 * Main form of application.
 * 
 */
public class MainForm {

	private JFrame frmPyramakerVer;
	private JTextField txtUrlPattern;
	private JTextField txtBoundsLeft;
	private JTextField txtBoundsTop;
	private JTextField txtBoundsBottom;
	private JTextField txtBoundsRight;
	private JTextField txtLevelHigher;
	private JTextField txtLevelLower;
	private JTextField txtPath;
	private JTextField txtRegionLeft;
	private JTextField txtRegionTop;
	private JTextField txtRegionBottom;
	private JTextField txtRegionRight;
	private JTextField txtTileSize;
	private JTextPane txtSRSWKT;
	private JTextField txtQuality;
	private JProgressBar progressBar;
	private JLabel lblAll;
	private JLabel lblDone;
	private JButton btnShowLog;
	private JCheckBox chckbxUseJpegCompression;
	private ImageDownloaderAsync imageDownloaderAsync;
	private Timer activityMonitor;
	
	
	private  final static String WKT_EPSG_3857 = "PROJCS[\"WGS 84 / Pseudo-Mercator\",  GEOGCS[\"WGS 84\",    DATUM[\"World Geodetic System 1984\",      SPHEROID[\"WGS 84\", 6378137.0, 298.257223563, AUTHORITY[\"EPSG\", \"7030\"]],      AUTHORITY[\"EPSG\", \"6326\"]],    PRIMEM[\"Greenwich\", 0.0, AUTHORITY[\"EPSG\", \"8901\"]],    UNIT[\"degree\", 0.017453292519943295],    AXIS[\"Geodetic longitude\", EAST],    AXIS[\"Geodetic latitude\", NORTH],    AUTHORITY[\"EPSG\", \"4326\"]],  PROJECTION[\"Popular Visualisation Pseudo Mercator\", AUTHORITY[\"EPSG\", \"1024\"]],  PARAMETER[\"semi_minor\", 6378137.0],  PARAMETER[\"latitude_of_origin\", 0.0],  PARAMETER[\"central_meridian\", 0.0],  PARAMETER[\"scale_factor\", 1.0],  PARAMETER[\"false_easting\", 0.0],  PARAMETER[\"false_northing\", 0.0],  UNIT[\"m\", 1.0],  AXIS[\"Easting\", EAST],  AXIS[\"Northing\", NORTH],AUTHORITY[\"EPSG\", \"3857\"]]";

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm window = new MainForm();
					window.frmPyramakerVer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmPyramakerVer = new JFrame();
		frmPyramakerVer.setTitle("Pyramaker ver. 1.0.0");
		frmPyramakerVer.setResizable(false);
		frmPyramakerVer.setBounds(100, 100, 756, 625);
		frmPyramakerVer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPyramakerVer.getContentPane().setLayout(null);
		
		txtUrlPattern = new JTextField();
		txtUrlPattern.setText("");
		txtUrlPattern.setBounds(6, 57, 744, 28);
		frmPyramakerVer.getContentPane().add(txtUrlPattern);
		txtUrlPattern.setColumns(10);
		
		JLabel lblSergeyVoyteshonok = new JLabel("\u00A9 Sergey Voyteshonok info@svoyt.com");
		lblSergeyVoyteshonok.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		lblSergeyVoyteshonok.setBounds(7, 581, 507, 16);
		frmPyramakerVer.getContentPane().add(lblSergeyVoyteshonok);
		
		JLabel lblTileUrlPattern = new JLabel("Tile url pattern. Definitions: x (column) - {0}, y (row) - {1}, z (layer number) - {2}");
		lblTileUrlPattern.setBounds(6, 19, 507, 16);
		frmPyramakerVer.getContentPane().add(lblTileUrlPattern);
		
		JLabel lblExampleHttpyourdomaincomtile = new JLabel("Example: http://yourdomain.com/tile/{2}/{0}/{1}");
		lblExampleHttpyourdomaincomtile.setFont(new Font("Lucida Grande", Font.ITALIC, 10));
		lblExampleHttpyourdomaincomtile.setBounds(6, 40, 323, 16);
		frmPyramakerVer.getContentPane().add(lblExampleHttpyourdomaincomtile);
		
		JLabel lblLayerBounds = new JLabel("Max layer bounds");
		lblLayerBounds.setBounds(6, 97, 133, 16);
		frmPyramakerVer.getContentPane().add(lblLayerBounds);
		
		txtBoundsLeft = new JTextField();
		txtBoundsLeft.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		txtBoundsLeft.setBounds(47, 125, 79, 28);
		frmPyramakerVer.getContentPane().add(txtBoundsLeft);
		txtBoundsLeft.setColumns(10);
		
		txtBoundsTop = new JTextField();
		txtBoundsTop.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		txtBoundsTop.setBounds(47, 152, 79, 28);
		frmPyramakerVer.getContentPane().add(txtBoundsTop);
		txtBoundsTop.setColumns(10);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(MainForm.class.getResource("/org/svoyt/pyramaker/res/images/map.png")));
		label.setBounds(138, 152, 88, 88);
		frmPyramakerVer.getContentPane().add(label);
		
		txtBoundsBottom = new JTextField();
		txtBoundsBottom.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		txtBoundsBottom.setColumns(10);
		txtBoundsBottom.setBounds(238, 240, 79, 28);
		frmPyramakerVer.getContentPane().add(txtBoundsBottom);
		
		txtBoundsRight = new JTextField();
		txtBoundsRight.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		txtBoundsRight.setColumns(10);
		txtBoundsRight.setBounds(238, 213, 79, 28);
		frmPyramakerVer.getContentPane().add(txtBoundsRight);
		
		JLabel lblLeft = new JLabel("left");
		lblLeft.setLabelFor(txtBoundsLeft);
		lblLeft.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		lblLeft.setBounds(16, 131, 21, 16);
		frmPyramakerVer.getContentPane().add(lblLeft);
		
		JLabel lblTop = new JLabel("top");
		lblTop.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		lblTop.setBounds(16, 158, 21, 16);
		frmPyramakerVer.getContentPane().add(lblTop);
		
		JLabel lblRight = new JLabel("right");
		lblRight.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		lblRight.setBounds(329, 219, 61, 16);
		frmPyramakerVer.getContentPane().add(lblRight);
		
		JLabel lblBottom = new JLabel("bottom");
		lblBottom.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		lblBottom.setBounds(329, 246, 61, 16);
		frmPyramakerVer.getContentPane().add(lblBottom);
		 
		JLabel lblLevel = new JLabel("Levels");
		lblLevel.setBounds(6, 276, 61, 16);
		frmPyramakerVer.getContentPane().add(lblLevel);
		
		JLabel lblStarthigher = new JLabel("Start(higher)");
		lblStarthigher.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		lblStarthigher.setBounds(6, 300, 79, 16);
		frmPyramakerVer.getContentPane().add(lblStarthigher);
		
		txtLevelHigher = new JTextField();
		lblStarthigher.setLabelFor(txtLevelHigher);
		txtLevelHigher.setBounds(86, 294, 41, 28);
		frmPyramakerVer.getContentPane().add(txtLevelHigher);
		txtLevelHigher.setColumns(10);
		
		JLabel lblEndlower = new JLabel("End(lower)");
		lblEndlower.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		lblEndlower.setBounds(139, 300, 61, 16);
		frmPyramakerVer.getContentPane().add(lblEndlower);
		
		txtLevelLower = new JTextField();
		txtLevelLower.setBounds(208, 294, 41, 28);
		frmPyramakerVer.getContentPane().add(txtLevelLower);
		txtLevelLower.setColumns(10);
		
		JLabel lblReferenceSystemWkt = new JLabel("Reference System WKT");
		lblReferenceSystemWkt.setBounds(6, 334, 179, 16);
		frmPyramakerVer.getContentPane().add(lblReferenceSystemWkt);
		
	    txtSRSWKT = new JTextPane();
		final JScrollPane conversationScrollPane = new JScrollPane(txtSRSWKT);
		conversationScrollPane.setBounds(6, 360, 744, 59);
        frmPyramakerVer.getContentPane().add(conversationScrollPane);
		
		JButton btnRun = new JButton("Run");
		btnRun.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start();
			}
		});
		btnRun.setIcon(new ImageIcon(MainForm.class.getResource("/org/svoyt/pyramaker/res/images/run.png")));
		btnRun.setBounds(6, 502, 88, 32);
		frmPyramakerVer.getContentPane().add(btnRun);
		
		JButton btnPause = new JButton("Pause");
		btnPause.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pause();
			}
		});
		btnPause.setIcon(new ImageIcon(MainForm.class.getResource("/org/svoyt/pyramaker/res/images/pause.png")));
		btnPause.setBounds(106, 502, 94, 32);
		frmPyramakerVer.getContentPane().add(btnPause);
		
		JButton btnStop = new JButton("Stop");
		btnStop.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stop();
			}
		});
		btnStop.setIcon(new ImageIcon(MainForm.class.getResource("/org/svoyt/pyramaker/res/images/stop.png")));
		btnStop.setBounds(216, 502, 88, 32);
		frmPyramakerVer.getContentPane().add(btnStop);
		
	    progressBar = new JProgressBar();
		progressBar.setBounds(6, 546, 744, 20);
		frmPyramakerVer.getContentPane().add(progressBar);
		
		JLabel lblProcess = new JLabel("Process");
		lblProcess.setBounds(6, 483, 61, 16);
		frmPyramakerVer.getContentPane().add(lblProcess);
		
		JLabel lblall = new JLabel("All:");
		lblall.setBounds(316, 510, 21, 16);
		frmPyramakerVer.getContentPane().add(lblall);
		
		JLabel lbldone = new JLabel("Done:");
		lbldone.setBounds(428, 510, 41, 16);
		frmPyramakerVer.getContentPane().add(lbldone);
		
	    lblAll = new JLabel("0");
		lblAll.setFont(new Font("Lucida Grande", Font.ITALIC, 12));
		lblAll.setBounds(340, 510, 79, 16);
		frmPyramakerVer.getContentPane().add(lblAll);
		
		lblDone = new JLabel("0");
		lblDone.setFont(new Font("Lucida Grande", Font.ITALIC, 12));
		lblDone.setBounds(470, 510, 79, 16);
		frmPyramakerVer.getContentPane().add(lblDone);
		
		JLabel lblPath = new JLabel("Path");
		lblPath.setBounds(6, 431, 61, 16);
		frmPyramakerVer.getContentPane().add(lblPath);
		
		txtPath = new JTextField();
		txtPath.setBounds(6, 449, 636, 28);
		frmPyramakerVer.getContentPane().add(txtPath);
		txtPath.setColumns(10);
		
		JButton btnBrowse = new JButton("browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fc.showOpenDialog(frmPyramakerVer);

		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fc.getSelectedFile();
		            txtPath.setText(file.getPath());
		        }
			}
		});
		btnBrowse.setBounds(645, 450, 94, 29);
		frmPyramakerVer.getContentPane().add(btnBrowse);
		
		JLabel lblRegionLayerBounds = new JLabel("Region layer bounds");
		lblRegionLayerBounds.setBounds(366, 97, 140, 16);
		frmPyramakerVer.getContentPane().add(lblRegionLayerBounds);
		
		txtRegionLeft = new JTextField();
		txtRegionLeft.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		txtRegionLeft.setColumns(10);
		txtRegionLeft.setBounds(407, 125, 79, 28);
		frmPyramakerVer.getContentPane().add(txtRegionLeft);
		
		txtRegionTop = new JTextField();
		txtRegionTop.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		txtRegionTop.setColumns(10);
		txtRegionTop.setBounds(407, 152, 79, 28);
		frmPyramakerVer.getContentPane().add(txtRegionTop);
		
		JLabel label_4 = new JLabel("");
		label_4.setIcon(new ImageIcon(MainForm.class.getResource("/org/svoyt/pyramaker/res/images/mapWithRegion.png")));
		label_4.setBounds(498, 152, 88, 88);
		frmPyramakerVer.getContentPane().add(label_4);
		
		txtRegionBottom = new JTextField();
		txtRegionBottom.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		txtRegionBottom.setColumns(10);
		txtRegionBottom.setBounds(598, 240, 79, 28);
		frmPyramakerVer.getContentPane().add(txtRegionBottom);
		
		txtRegionRight = new JTextField();
		txtRegionRight.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		txtRegionRight.setColumns(10);
		txtRegionRight.setBounds(598, 213, 79, 28);
		frmPyramakerVer.getContentPane().add(txtRegionRight);
		
		JLabel label_5 = new JLabel("left");
		label_5.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		label_5.setBounds(376, 131, 21, 16);
		frmPyramakerVer.getContentPane().add(label_5);
		
		JLabel label_6 = new JLabel("top");
		label_6.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		label_6.setBounds(376, 158, 21, 16);
		frmPyramakerVer.getContentPane().add(label_6);
		
		JLabel label_7 = new JLabel("right");
		label_7.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		label_7.setBounds(689, 219, 61, 16);
		frmPyramakerVer.getContentPane().add(label_7);
		
		JLabel label_8 = new JLabel("bottom");
		label_8.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		label_8.setBounds(689, 246, 61, 16);
		frmPyramakerVer.getContentPane().add(label_8);
		
		JLabel lblTileSize = new JLabel("Tile size");
		lblTileSize.setBounds(385, 274, 61, 16);
		frmPyramakerVer.getContentPane().add(lblTileSize);
		
		txtTileSize = new JTextField();
		txtTileSize.setBounds(387, 297, 70, 22);
		frmPyramakerVer.getContentPane().add(txtTileSize);
		txtTileSize.setColumns(10);
		
	    btnShowLog = new JButton("Show log");
	    btnShowLog.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	    		if (imageDownloaderAsync!=null){
	    			new LogForm(imageDownloaderAsync.getLog());
	    		}
	    	}
	    });
		btnShowLog.setEnabled(false);
		btnShowLog.setBounds(622, 504, 117, 29);
		frmPyramakerVer.getContentPane().add(btnShowLog);
		
		JButton btnOsmExample = new JButton("OSM example");
		btnOsmExample.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    final ImageDownloaderParams params = new ImageDownloaderParams(
			    		"http://c.tile.openstreetmap.org/{2}/{0}/{1}.png",
			    		new Rectangle(-19918247,19918247,19918247,-19918247),
			    		new Rectangle(-19918246,19918246,19918246,-19918246),
			    		256,
			    		2,
			    		0,
			    		WKT_EPSG_3857,
			    		"",
			    		true,
			    		100
			    		); 
			    setParamsToForm(params);
			}
		});
		btnOsmExample.setBounds(622, 14, 117, 29);
		frmPyramakerVer.getContentPane().add(btnOsmExample);
		
	    chckbxUseJpegCompression = new JCheckBox("Use JPEG compression");
	    chckbxUseJpegCompression.addChangeListener(new ChangeListener() {
	    	public void stateChanged(ChangeEvent arg0) {
	    		txtQuality.setEnabled(chckbxUseJpegCompression.isSelected());
	    	}
	    });
		chckbxUseJpegCompression.setBounds(579, 272, 171, 23);
		frmPyramakerVer.getContentPane().add(chckbxUseJpegCompression);
		
		txtQuality = new JTextField();
		txtQuality.setText("100");
		txtQuality.setBounds(646, 294, 50, 28);
		frmPyramakerVer.getContentPane().add(txtQuality);
		txtQuality.setColumns(10);
		
		JLabel lblQuality = new JLabel("Quality");
		lblQuality.setBounds(589, 300, 50, 16);
		frmPyramakerVer.getContentPane().add(lblQuality);
		
		JLabel label_1 = new JLabel("%");
		label_1.setBounds(697, 300, 36, 16);
		frmPyramakerVer.getContentPane().add(label_1);
	}
		
	/**
	 * Start main process
	 */
	private void start(){
		if (!validate())
			return;
		ImageDownloaderParams params = getParamsFromForm();
		imageDownloaderAsync = new ImageDownloaderAsync(new ImageDownloader(params));
		btnShowLog.setEnabled(true);
	    int all = params.getTilesCount();
		progressBar.setMaximum(all);
		lblAll.setText(String.valueOf(all));
		lblDone.setText("0");
		progressBar.setValue(0);
		activityMonitor = new Timer(100,
				new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						int process = imageDownloaderAsync.getProcess();
						lblDone.setText(String.valueOf(process));
						progressBar.setValue(imageDownloaderAsync.getProcess());
						if (imageDownloaderAsync.isDone()){
							activityMonitor.stop();
							progressBar.setValue(0);
						}
					}		
		        }
		);
		imageDownloaderAsync.start();
		activityMonitor.start();
	}
	
	/**
	 * Get data from form elements
	 * @return ImageDownloaderParams for image downloader
	 */
	private ImageDownloaderParams getParamsFromForm(){
	    return new ImageDownloaderParams(
	    		txtUrlPattern.getText(),
	    		new Rectangle(
	    				Double.valueOf(txtBoundsLeft.getText()),
	    				Double.valueOf(txtBoundsTop.getText()),
	    				Double.valueOf(txtBoundsRight.getText()),
	    				Double.valueOf(txtBoundsBottom.getText())),
	    		new Rectangle(
	    				Double.valueOf(txtRegionLeft.getText()),
	    				Double.valueOf(txtRegionTop.getText()),
	    				Double.valueOf(txtRegionRight.getText()),
	    				Double.valueOf(txtRegionBottom.getText())),
	    				Integer.valueOf(txtTileSize.getText()),
	    				Integer.valueOf(txtLevelHigher.getText()),
	    				Integer.valueOf(txtLevelLower.getText()), 
	    				txtSRSWKT.getText(),
	    				txtPath.getText(),
	    				chckbxUseJpegCompression.isSelected(),
	    				Float.valueOf(txtQuality.getText()));
	}
	
	/**
	 * Write data to form
	 * @param params image downloader parameters for form
	 */
	private void setParamsToForm(ImageDownloaderParams params){
		NumberFormat f = NumberFormat.getInstance();
		f.setGroupingUsed(false);
		txtUrlPattern.setText(params.getUrlPattern());
		txtBoundsLeft.setText(f.format(params.getMaxLayerBounds().left));
		txtBoundsTop.setText(f.format(params.getMaxLayerBounds().top));
		txtBoundsRight.setText(f.format(params.getMaxLayerBounds().right));
		txtBoundsBottom.setText(f.format(params.getMaxLayerBounds().bottom));
		txtRegionLeft.setText(f.format(params.getRegionLayerBounds().left));
		txtRegionTop.setText(f.format(params.getRegionLayerBounds().top));
		txtRegionRight.setText(f.format(params.getRegionLayerBounds().right));
		txtRegionBottom.setText(f.format(params.getRegionLayerBounds().bottom));
		txtTileSize.setText(String.valueOf(params.getTileSize()));
		txtLevelHigher.setText(String.valueOf(params.getStartLevel()));
		txtLevelLower.setText(String.valueOf(params.getEndLevel()));
		txtSRSWKT.setText(params.getWkt());
		txtPath.setText(params.getPath());
		chckbxUseJpegCompression.setSelected(params.isJpegCompressionUsed());
		txtQuality.setText(f.format(params.getJpegQuality()));
	}
	

	/**
	 * Validate form
	 * @return true if form is valid
	 */
	private boolean validate(){
		if (txtPath.getText().isEmpty()){
			JOptionPane.showMessageDialog(frmPyramakerVer, "Please, specify output path");
			return false;
		}
		return true;
		// add additional validators to your taste =)
	}
	
	/**
	 * pause main process
	 */
	private void pause(){
		imageDownloaderAsync.pauseWork();
	}
	
	/**
	 * stop main process
	 */
	private void stop(){
		imageDownloaderAsync.stopWork();
	}
}


