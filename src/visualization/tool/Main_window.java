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

public class Main_window {

	private JFrame frame;

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
		frame.setSize(480, 480);
		// set frame location to center of the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(30, 51, 46, 14);
		
		final File inputFile = new File("model-input/ChallengeModel_withCommImplementationTypev082.amxmi");
		Amalthea model = AmaltheaLoader.loadFromFile(inputFile);
		EList<Task> tasks = model.getSwModel().getTasks();
		String s = "";
		for (Task task : tasks) {
			//s += task.getName() + ", ";
		}
		
		
		s = SoftwareUtil.getRunnableList(tasks.get(0), null).get(0).getName();
		
		//model.getConstraintsModel().getEventChains().get(0).getSegments().get(0).getEventChain().getName();
		//s = model.getConstraintsModel().getEventChains().get(0).getSegments().get(0).getEventChain().getName();
		//System.out.println(model.getConstraintsModel().getEventChains().size());
		
		lblNewLabel.setText(s);
		lblNewLabel.setSize(lblNewLabel.getPreferredSize());
		
		frame.getContentPane().add(lblNewLabel);
	}
}
