package game;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Class for the GUI
 * 
 * @author Jacob Charles, Dean Hamlin
 *
 */

public class View extends JFrame {
	
	
	/**
	 * Connects a controller to the Applet
	 * 
	 * @param c
	 * 		the controller object to be connected
	 */
	public void attachController(Controller c) {
		this.addKeyListener(new ControlListener(c));
	}
	
	public void reDraw(ClientGameState state){
		state.draw(this.getGraphics());
	}
}

