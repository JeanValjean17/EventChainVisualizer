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
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;
import java.awt.Rectangle;
import java.awt.ComponentOrientation;

public class Main_window {

	private JFrame frame;
	//private Scroll_image scroll_image;

	/**
	 * Launch the application.
	 */
	/*
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
	/*public Main_window() {
		initialize();
		
	}*/

	/**
	 * Initialize the contents of the frame.
	 */
	/*private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setSize(800, 600);
		
		// set frame location to center of the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		scroll_image = new Scroll_image();
		scroll_image.setPreferredSize(new Dimension(100, 100));
		scroll_image.setSize(new Dimension(100, 100));
		scroll_image.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		scroll_image.setAutoscrolls(true);
		scroll_image.setBounds(new Rectangle(0, 0, 100, 100));
		scroll_image.setSize(300, 300);
		scroll_image.setLocation(10, 10);
		//frame.getContentPane().add(scroll_image);
		
		JScrollPane scroll_image_pane = new JScrollPane(scroll_image);
		scroll_image_pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll_image_pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll_image_pane.setBounds(10, 10, 300, 200);
		frame.getContentPane().add(scroll_image_pane);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				scroll_image.load_image();
				scroll_image.setBounds(100, 100, 500, 500);
			}
		});
		btnNewButton.setBounds(10, 537, 89, 23);
		frame.getContentPane().add(btnNewButton);
	}*/
}
