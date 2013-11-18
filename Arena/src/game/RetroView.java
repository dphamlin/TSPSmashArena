package game;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.JFrame;

/**
 * Class for local View testing
 * 
 * @author Jacob Charles, Dean Hamlin
 *
 */

public class RetroView extends JFrame {

	public RetroView() {
		super();
		setTitle("TSPArena");
		getContentPane().setPreferredSize(new Dimension(640, 480));
		setResizable(false);
		setVisible(true);
		pack(); //FORCE it to be 640 x 480, this has given me grief
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); // Closing the window closes the game
		getGraphics().drawString("Waiting for game to start...", 340-75, 240+5); //pre-join text
		toFront();
	}

	/**
	 * Connects a controller to the screen
	 * 
	 * @param c
	 *                 the controller object to be connected
	 */
	public void attachController(Controller c) {
		addKeyListener(new RetroControlListener(c));
	}

	/**
	 * Draw a game state (double buffered)
	 * 
	 * @param state
	 *                 game state to draw
	 */
	public void reDraw(ClientGameState state){
		if (!this.getTitle().equals("TSPArena: "+state.getMapName())) {
			this.setTitle("TSPArena: "+state.getMapName());
		}
		Image backBuffer = createImage(640, 480);
		state.draw(backBuffer.getGraphics());
		getContentPane().getGraphics().drawImage(backBuffer, 0, 0, null);
	}

	/**
	 * Draw a menu state (double buffered)
	 * 
	 * @param state
	 *                 menu state to draw
	 */
	public void reDraw(MenuState state){
		if (!this.getTitle().equals("TSPArena: "+state.getStateName())) {
			this.setTitle("TSPArena: "+state.getStateName());
		}
		Image backBuffer = createImage(640, 480);
		state.draw(backBuffer.getGraphics());
		getContentPane().getGraphics().drawImage(backBuffer, 0, 0, null);
	}
}