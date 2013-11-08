package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

/**
 * Update the buffers in a Controller object using actual key input
 * 
 * @author Jacob Charles
 */
public class ControlListener implements KeyListener,ActionListener {
	//controls (may eventually be reconfigurable)
	private static final int KEY_UP = KeyEvent.VK_UP;
	private static final int KEY_DOWN = KeyEvent.VK_DOWN;
	private static final int KEY_LEFT = KeyEvent.VK_LEFT;
	private static final int KEY_RIGHT = KeyEvent.VK_RIGHT;
	private static final int KEY_JUMP = KeyEvent.VK_Z;
	private static final int KEY_FIRE = KeyEvent.VK_X;
	private static final int KEY_START = KeyEvent.VK_ESCAPE;

	private Controller c;
	private View v;

	/**
	 * Initialize a ControlListener to a Controller
	 * 
	 * @param c
	 * 		controller object to update
	 */
	public ControlListener(View v) {
		this.v = v;
	}

	public void setContoller(Controller c){
		this.c = c;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//Don't care, not using this method
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//System.out.println("Hi!");
		int k = e.getKeyCode();
		//Set relevant buffers to true
		if (k == KEY_UP) c.setUp(true);
		if (k == KEY_DOWN) c.setDown(true);
		if (k == KEY_LEFT) c.setLeft(true);
		if (k == KEY_RIGHT) c.setRight(true);
		if (k == KEY_JUMP) c.setJump(true);
		if (k == KEY_FIRE) c.setFire(true);
		if (k == KEY_START) c.setStart(true);
		 
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		//System.out.println("Hey!");
		int k = e.getKeyCode();
		//Set relevant buffers to false
		if (k == KEY_UP) c.setUp(false);
		if (k == KEY_DOWN) c.setDown(false);
		if (k == KEY_LEFT) c.setLeft(false);
		if (k == KEY_RIGHT) c.setRight(false);
		if (k == KEY_JUMP) c.setJump(false);
		if (k == KEY_FIRE) c.setFire(false);
		if (k == KEY_START) c.setStart(false);

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == v.getjGo()){
			String ip = v.getIpField().getText();
			if(ip != null){
				v.getCl().show(v.getCardPane(), "draw");
				v.getArena().join(ip, 5379);
			}
		}

		else if(e.getSource() == v.gethGo()){
			int numPlayers =  Integer.parseInt(v.getNumPlayersField().getText());
			if(numPlayers < 5 && numPlayers > 0){
				v.getCl().show(v.getCardPane(), "draw");
				v.getArena().host(numPlayers, 5379);
			}
		}
	}

}
