package visualization.tool;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Scroll_image extends JComponent {
	private static final long serialVersionUID = 1L;
    private BufferedImage image = null;

    public Scroll_image() {
    	/*try {
            this.image = ImageIO.read(new File("C:\\Users\\alyha\\Desktop\\test.png"));
            setBounds(100, 100, 500, 500);
            //canvas.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        }catch(IOException ex) {
            //Logger.getLogger(ScrollImageTest.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    	 /*this.canvas = new JPanel() {
             private static final long serialVersionUID = 1L;
             @Override
             
         };*/
         //canvas.add(new JButton("Currently I do nothing"));
         //canvas.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
         //JScrollPane sp = new JScrollPane(canvas);
         //setLayout(new BorderLayout());
         //add(sp, BorderLayout.CENTER);
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (image != null) g.drawImage(image, 0, 0, null);
    }
    
    public void load_image(BufferedImage image) {
        //try {
            //image = ImageIO.read(new File("C:\\Users\\alyha\\Desktop\\test.png"));
        	//Visualizer vis = new Visualizer();
        	//image = vis.draw_event_queue(new ArrayList<Event>());
    	this.image = image;
        setBounds(0, 0, image.getWidth(), image.getHeight());
            //setSize(new Dimension(image.getWidth(), image.getHeight()));
            //setLocation(500, 600);
        repaint();
        //}catch(IOException ex) {
            //Logger.getLogger(ScrollImageTest.class.getName()).log(Level.SEVERE, null, ex);
        //}
    }
}
