package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Update the buffers in a Controller object using actual key input
 * 
 * @author Jacob Charles
 */
public class ControlListener implements KeyListener {
	//controls (may eventually be reconfigurable)
	private static final int KEY_UP = KeyEvent.VK_UP;
	private static final int KEY_DOWN = KeyEvent.VK_DOWN;
	private static final int KEY_LEFT = KeyEvent.VK_LEFT;
	private static final int KEY_RIGHT = KeyEvent.VK_RIGHT;
	private static final int KEY_JUMP = KeyEvent.VK_Z;
	private static final int KEY_FIRE = KeyEvent.VK_X;
	private static final int KEY_START = KeyEvent.VK_ESCAPE;
	
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
		if (k == KEY_UP) c.setbUp(true);
		if (k == KEY_DOWN) c.setbDown(true);
		if (k == KEY_LEFT) c.setbLeft(true);
		if (k == KEY_RIGHT) c.setbRight(true);
		if (k == KEY_JUMP) c.setbJump(true);
		if (k == KEY_FIRE) c.setbFire(true);
		if (k == KEY_START) c.setbStart(true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int k = e.getKeyCode();
		//Set relevant buffers to false
		if (k == KEY_UP) c.setbUp(false);
		if (k == KEY_DOWN) c.setbDown(false);
		if (k == KEY_LEFT) c.setbLeft(false);
		if (k == KEY_RIGHT) c.setbRight(false);
		if (k == KEY_JUMP) c.setbJump(false);
		if (k == KEY_FIRE) c.setbFire(false);
		if (k == KEY_START) c.setbStart(false);
	}

}
