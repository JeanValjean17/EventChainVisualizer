package visualization.tool;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.eclipse.app4mc.amalthea.model.Amalthea;
import org.eclipse.app4mc.amalthea.model.CallSequence;
import org.eclipse.app4mc.amalthea.model.Task;
import org.eclipse.app4mc.amalthea.model.io.AmaltheaLoader;
import org.eclipse.app4mc.amalthea.model.util.SoftwareUtil;
import org.eclipse.emf.common.util.EList;

import javafx.scene.control.ComboBox;

import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;
import java.awt.Rectangle;
import java.awt.ComponentOrientation;
import java.awt.BorderLayout;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JCheckBox;

public class Main_window {

	private JFrame frmEndtoendVisualizationTool;
	private Scroll_image scroll_image;
	private JPanel panelImage;
	private JScrollPane scrollPaneImage;
	private JComboBox<String> comboBoxChain;
	private Model_reader model_reader;
	private Scheduler scheduler;
	private Visualizer visualizer;
	private JTextField textFieldPath;
	private JTextField textFieldTime;
	private JCheckBox chckbxRunnableLevel;
	private JLabel lblModelFileName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new java.lang.Runnable() {
			@Override
			public void run() {
				try {
					Main_window window = new Main_window();
					window.frmEndtoendVisualizationTool.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main_window() {
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmEndtoendVisualizationTool = new JFrame();
		frmEndtoendVisualizationTool.setTitle("End-To-End Latency Visualization Tool");
		frmEndtoendVisualizationTool.setResizable(false);
		frmEndtoendVisualizationTool.setSize(800, 700);
		
		// set frame location to center of the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frmEndtoendVisualizationTool.setLocation(dim.width/2-frmEndtoendVisualizationTool.getSize().width/2, dim.height/2-frmEndtoendVisualizationTool.getSize().height/2);
		frmEndtoendVisualizationTool.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEndtoendVisualizationTool.getContentPane().setLayout(null);
		
		/*JScrollPane scroll_image_pane = new JScrollPane(scroll_image);
		scroll_image_pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll_image_pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll_image_pane.setBounds(10, 10, 300, 200);
		frame.getContentPane().add(scroll_image_pane);*/
		
		JButton btnLoad = new JButton("Load");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				model_reader = new Model_reader();
				scheduler = new Scheduler();
				visualizer = new Visualizer();
				
				model_reader.read_model(textFieldPath.getText());
				
				int max_time = 30;
				
				try {
					max_time = Integer.parseInt(textFieldTime.getText());
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
				
				scheduler.build_schedule(model_reader.get_tasks(), max_time);
				scheduler.build_event_chains(model_reader.get_event_chains(), max_time);
				scheduler.sort_event_queue();
				
				visualizer.initialize(scheduler.get_event_queue(), model_reader.get_tasks(), model_reader.get_event_chains(), max_time);
				
				comboBoxChain.removeAllItems();
				for (Event_chain event_chain : model_reader.get_event_chains()) {
					comboBoxChain.addItem(event_chain.get_name());
				}
				
			}
		});
		btnLoad.setBounds(10, 637, 120, 23);
		frmEndtoendVisualizationTool.getContentPane().add(btnLoad);
		
		scrollPaneImage = new JScrollPane();
		scrollPaneImage.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPaneImage.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneImage.setBounds(10, 11, 774, 577);
		frmEndtoendVisualizationTool.getContentPane().add(scrollPaneImage);
		
		panelImage = new JPanel();
		panelImage.setPreferredSize(new Dimension(500, 500));
		panelImage.setBounds(new Rectangle(100, 100, 500, 500));
		scrollPaneImage.setViewportView(panelImage);
		panelImage.setLayout(new BorderLayout(0, 0));
		
		scroll_image = new Scroll_image();
		panelImage.add(scroll_image);
		scroll_image.setPreferredSize(new Dimension(100, 100));
		scroll_image.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		scroll_image.setAutoscrolls(true);
		
		comboBoxChain = new JComboBox<String>();
		comboBoxChain.setBounds(326, 637, 160, 20);
		frmEndtoendVisualizationTool.getContentPane().add(comboBoxChain);
		
		JButton btnShow = new JButton("Show");
		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Event_chain selected_event_chain = model_reader.get_event_chains().get(0);
				for (Event_chain event_chain : model_reader.get_event_chains()) {
					if (event_chain.get_name().equalsIgnoreCase((String)comboBoxChain.getSelectedItem())) {
						selected_event_chain = event_chain;
					}
				}
				BufferedImage image = visualizer.draw_event_queue(selected_event_chain, chckbxRunnableLevel.isSelected());
				
				scroll_image.load_image(image);
				panelImage.setPreferredSize(scroll_image.getSize());
				panelImage.validate();
				scrollPaneImage.validate();
				
			}
		});
		btnShow.setBounds(140, 637, 120, 23);
		frmEndtoendVisualizationTool.getContentPane().add(btnShow);
		
		textFieldPath = new JTextField();
		textFieldPath.setBounds(102, 606, 682, 20);
		textFieldPath.setText("model-input/model_democar_AMALTHEA_Democar_Corrected.amxmi");
		frmEndtoendVisualizationTool.getContentPane().add(textFieldPath);
		textFieldPath.setColumns(10);
		
		textFieldTime = new JTextField();
		textFieldTime.setText("30");
		textFieldTime.setBounds(734, 637, 50, 20);
		frmEndtoendVisualizationTool.getContentPane().add(textFieldTime);
		textFieldTime.setColumns(10);
		
		JLabel lblChain = new JLabel("Chain:");
		lblChain.setBounds(280, 640, 36, 14);
		frmEndtoendVisualizationTool.getContentPane().add(lblChain);
		
		JLabel lblTime = new JLabel("Time (ms):");
		lblTime.setBounds(660, 640, 64, 14);
		frmEndtoendVisualizationTool.getContentPane().add(lblTime);
		
		chckbxRunnableLevel = new JCheckBox("Runnable_level");
		chckbxRunnableLevel.setBounds(518, 637, 125, 23);
		frmEndtoendVisualizationTool.getContentPane().add(chckbxRunnableLevel);
		
		lblModelFileName = new JLabel("Model File Path:");
		lblModelFileName.setBounds(10, 609, 98, 14);
		frmEndtoendVisualizationTool.getContentPane().add(lblModelFileName);
	}
}
