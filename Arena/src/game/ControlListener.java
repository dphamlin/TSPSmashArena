package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Update the buffers in a Controller object using actual key input
 * 
 * @author Jacob Charles
 */
public class ControlListener implements KeyListener {

	private Controller c;
	
	/**
	 * Initialize a ControlListener to a Controller
	 * 
	 * @param c
	 * 		controller object to update
	 */
	public ControlListener(Controller c) {
		this.c = c;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		//Don't care, not using this method
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int k = e.getKeyCode();
		//Set relevant buffers to true
		if (k == KeyEvent.VK_UP) c.setbUp(true);
		if (k == KeyEvent.VK_DOWN) c.setbDown(true);
		if (k == KeyEvent.VK_LEFT) c.setbLeft(true);
		if (k == KeyEvent.VK_RIGHT) c.setbRight(true);
		if (k == KeyEvent.VK_Z) c.setbJump(true);
		if (k == KeyEvent.VK_X) c.setbFire(true);
		if (k == KeyEvent.VK_ESCAPE) c.setbStart(true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int k = e.getKeyCode();
		//Set relevant buffers to false
		if (k == KeyEvent.VK_UP) c.setbUp(false);
		if (k == KeyEvent.VK_DOWN) c.setbDown(false);
		if (k == KeyEvent.VK_LEFT) c.setbLeft(false);
		if (k == KeyEvent.VK_RIGHT) c.setbRight(false);
		if (k == KeyEvent.VK_Z) c.setbJump(false);
		if (k == KeyEvent.VK_X) c.setbFire(false);
		if (k == KeyEvent.VK_ESCAPE) c.setbStart(false);
	}

}
