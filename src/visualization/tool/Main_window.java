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

public class Main_window {

	private JFrame frame;
	private Scroll_image scroll_image;
	private JPanel panel;
	private JScrollPane scrollPane;
	private JComboBox<String> comboBox;
	private Model_reader model_reader;
	private Scheduler scheduler;
	private Visualizer visualizer;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new java.lang.Runnable() {
			@Override
			public void run() {
				try {
					Main_window window = new Main_window();
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
	public Main_window() {
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setSize(800, 700);
		
		// set frame location to center of the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		/*JScrollPane scroll_image_pane = new JScrollPane(scroll_image);
		scroll_image_pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll_image_pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll_image_pane.setBounds(10, 10, 300, 200);
		frame.getContentPane().add(scroll_image_pane);*/
		
		JButton btnNewButton = new JButton("Load");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				model_reader = new Model_reader();
				scheduler = new Scheduler();
				visualizer = new Visualizer();
				
				model_reader.read_model(textField.getText());
				
				int max_time = 30;
				
				try {
					max_time = Integer.parseInt(textField_1.getText());
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
				
				scheduler.build_schedule(model_reader.get_tasks(), max_time);
				scheduler.build_event_chains(model_reader.get_event_chains(), max_time);
				scheduler.sort_event_queue();
				
				visualizer.initialize(scheduler.get_event_queue(), model_reader.get_tasks(), model_reader.get_event_chains(), max_time);
				
				comboBox.removeAllItems();
				for (Event_chain event_chain : model_reader.get_event_chains()) {
					comboBox.addItem(event_chain.get_name());
				}
				
			}
		});
		btnNewButton.setBounds(10, 637, 120, 23);
		frame.getContentPane().add(btnNewButton);
		
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 11, 774, 577);
		frame.getContentPane().add(scrollPane);
		
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(500, 500));
		panel.setBounds(new Rectangle(100, 100, 500, 500));
		scrollPane.setViewportView(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		scroll_image = new Scroll_image();
		panel.add(scroll_image);
		scroll_image.setPreferredSize(new Dimension(100, 100));
		scroll_image.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		scroll_image.setAutoscrolls(true);
		
		comboBox = new JComboBox<String>();
		comboBox.setBounds(419, 637, 200, 20);
		frame.getContentPane().add(comboBox);
		
		JButton btnShow = new JButton("Show");
		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Event_chain selected_event_chain = model_reader.get_event_chains().get(0);
				for (Event_chain event_chain : model_reader.get_event_chains()) {
					if (event_chain.get_name().equalsIgnoreCase((String)comboBox.getSelectedItem())) {
						selected_event_chain = event_chain;
					}
				}
				BufferedImage image = visualizer.draw_event_queue(selected_event_chain);
				
				scroll_image.load_image(image);
				panel.setPreferredSize(scroll_image.getSize());
				panel.validate();
				scrollPane.validate();
				
			}
		});
		btnShow.setBounds(173, 637, 120, 23);
		frame.getContentPane().add(btnShow);
		
		textField = new JTextField();
		textField.setBounds(10, 606, 774, 20);
		textField.setText("model-input/model_democar_AMALTHEA_Democar_Corrected.amxmi");
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setText("30");
		textField_1.setBounds(734, 637, 50, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblChain = new JLabel("Chain:");
		lblChain.setBounds(373, 640, 36, 14);
		frame.getContentPane().add(lblChain);
		
		JLabel lblTimems = new JLabel("Time (ms):");
		lblTimems.setBounds(660, 640, 64, 14);
		frame.getContentPane().add(lblTimems);
	}
}
