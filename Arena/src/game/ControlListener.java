package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 * Update the buffers in a Controller object using actual key input
 * 
 * @author Jacob Charles, James Hannah
 */
public class ControlListener implements ActionListener{
	//controls (may eventually be reconfigurable)
	private static final int KEY_UP = KeyEvent.VK_UP;
	private static final int KEY_DOWN = KeyEvent.VK_DOWN;
	private static final int KEY_LEFT = KeyEvent.VK_LEFT;
	private static final int KEY_RIGHT = KeyEvent.VK_RIGHT;
	private static final int KEY_JUMP = KeyEvent.VK_Z;
	private static final int KEY_FIRE = KeyEvent.VK_X;
	private static final int KEY_START = KeyEvent.VK_ESCAPE;

	private static Controller c;
	private static View v;

	/**
	 * Initialize a ControlListener to a View
	 * 
	 * @param v
	 * 		the view
	 */
	public ControlListener(View v) {
		ControlListener.v = v;
		JPanel draw = v.getDraw();

		//Set up key binding actions.
		leftPressed lp = new leftPressed();
		leftReleased lr = new leftReleased();
		rightPressed rp = new rightPressed();
		rightReleased rr = new rightReleased();
		upPressed up = new upPressed();
		upReleased ur = new upReleased();
		downPressed dp = new downPressed();
		downReleased dr = new downReleased();
		jumpPressed jp = new jumpPressed();
		jumpReleased jr = new jumpReleased();
		firePressed fp = new firePressed();
		fireReleased fr = new fireReleased();
		startPressed sp = new startPressed();
		startReleased sr = new startReleased();

		//Put key binding actions into the draw panel input and action maps.
		draw.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).
		put( KeyStroke.getKeyStroke(KEY_LEFT, 0, false), "doLeftPressed");//false for pressed
		draw.getActionMap().put( "doLeftPressed", lp);
		draw.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).
		put( KeyStroke.getKeyStroke(KEY_LEFT, 0, true), "doLeftReleased");//true for released
		draw.getActionMap().put( "doLeftReleased", lr);

		draw.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
		.put( KeyStroke.getKeyStroke(KEY_RIGHT, 0, false), "doRightPressed");
		draw.getActionMap().put( "doRightPressed", rp);
		draw.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
		.put( KeyStroke.getKeyStroke(KEY_RIGHT, 0, true), "doRightReleased");
		draw.getActionMap().put( "doRightReleased", rr);

		draw.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).
		put( KeyStroke.getKeyStroke(KEY_UP, 0, false), "doUpPressed");
		draw.getActionMap().put( "doUpPressed", up);
		draw.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).
		put( KeyStroke.getKeyStroke(KEY_UP, 0, true), "doUpReleased");
		draw.getActionMap().put( "doUpReleased", ur);

		draw.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).
		put( KeyStroke.getKeyStroke(KEY_DOWN, 0, false), "doDownPressed");
		draw.getActionMap().put( "doDownPressed", dp);
		draw.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).
		put( KeyStroke.getKeyStroke(KEY_DOWN, 0, true), "doDownReleased");
		draw.getActionMap().put( "doDownReleased", dr);

		draw.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).
		put( KeyStroke.getKeyStroke(KEY_JUMP, 0, false), "doJumpPressed");
		draw.getActionMap().put( "doJumpPressed", jp);
		draw.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).
		put( KeyStroke.getKeyStroke(KEY_JUMP, 0, true), "doJumpReleased");
		draw.getActionMap().put( "doJumpReleased", jr);

		draw.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).
		put( KeyStroke.getKeyStroke(KEY_FIRE, 0, false), "doFirePressed");
		draw.getActionMap().put( "doFirePressed", fp);
		draw.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).
		put( KeyStroke.getKeyStroke(KEY_FIRE, 0, true), "doFireReleased");
		draw.getActionMap().put( "doFireReleased", fr);

		draw.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).
		put( KeyStroke.getKeyStroke(KEY_START, 0, false), "doStartPressed");
		draw.getActionMap().put( "doStartPressed", sp);
		draw.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).
		put( KeyStroke.getKeyStroke(KEY_START, 0, true), "doStartReleased");
		draw.getActionMap().put( "doStartReleased", sr);
	}

	/**
	 * Initialize a ControlListener to a Controller
	 * 
	 * @param c
	 * 		controller object to update
	 */
	public void setContoller(Controller c){
		ControlListener.c = c;
	}

	/**
	 * Action Preformed on GUI.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		int port;
		if(e.getSource() == v.getjGo()){//if the user pressed the join button
			String ip = v.getIpField().getText();
			try{
				port = Integer.parseInt(v.getJoinPortField().getText());
			} catch (NumberFormatException ne) {
				port = 0;
			}
			if(ip != null && port > 1024){//check ip and port
				v.getCl().show(v.getCardPane(), "draw");
				v.getArena().setHost(false);
				v.getArena().setIp(ip);
				v.getArena().setPort(port);//Set up arena and start worker.
				ArenaWorker worker = new ArenaWorker(v.getArena());
				worker.execute();
			}
		}
		else if(e.getSource() == v.gethGo()){//if the user pressed the host button
			int numPlayers;
			try{
				numPlayers = Integer.parseInt(v.getNumPlayersField().getText());
			} catch (NumberFormatException ne) {
				numPlayers = 0;
			}
			try{
				port = Integer.parseInt(v.getJoinPortField().getText());
			} catch (NumberFormatException ne) {
				port = 0;
			}
			if(port > 1024 && (numPlayers < 5 && numPlayers > 0)){//check the port and number of players
				v.getCl().show(v.getCardPane(), "draw");
				v.getArena().setHost(true);
				v.getArena().setNumPlayers(numPlayers);
				v.getArena().setPort(port);//set up arena and start worker
				ArenaWorker worker = new ArenaWorker(v.getArena());
				worker.execute();
			}
		}
	}

	//Abstract action classes for each key event.
	static class leftPressed extends AbstractAction{
		/**
		 * generated serial to suppress warnings
		 */
		private static final long serialVersionUID = -4886804893231055972L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			c.setLeft(true);
		}
	}

	static class leftReleased extends AbstractAction{
		/**
		 * generated serial to suppress warnings
		 */
		private static final long serialVersionUID = 7835730970619702608L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			c.setLeft(false);
		}
	}

	static class rightPressed extends AbstractAction{
		/**
		 * generated serial to suppress warnings
		 */
		private static final long serialVersionUID = -3591946708471060346L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			c.setRight(true);
		}
	}

	static class rightReleased extends AbstractAction{
		/**
		 * generated serial to suppress warnings
		 */
		private static final long serialVersionUID = -5478435890018151109L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			c.setRight(false);
		}
	}

	static class upPressed extends AbstractAction{
		/**
		 * generated serial to suppress warnings
		 */
		private static final long serialVersionUID = -4115521643458613030L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			c.setUp(true);
		}
	}

	static class upReleased extends AbstractAction{
		/**
		 * generated serial to suppress warnings
		 */
		private static final long serialVersionUID = 9134607484250654925L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			c.setUp(false);
		}
	}

	static class downPressed extends AbstractAction{
		/**
		 * generated serial to suppress warnings
		 */
		private static final long serialVersionUID = -2353656107396910815L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			c.setDown(true);
		}
	}

	static class downReleased extends AbstractAction{
		/**
		 * generated serial to suppress warnings
		 */
		private static final long serialVersionUID = -6010055355016031548L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			c.setDown(false);
		}
	}

	static class jumpPressed extends AbstractAction{
		/**
		 * generated serial to suppress warnings
		 */
		private static final long serialVersionUID = -8108677304761704349L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			c.setJump(true);
		}
	}

	static class jumpReleased extends AbstractAction{
		/**
		 * generated serial to suppress warnings
		 */
		private static final long serialVersionUID = 651023788291983995L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			c.setJump(false);
		}
	}

	static class firePressed extends AbstractAction{
		/**
		 * generated serial to suppress warnings
		 */
		private static final long serialVersionUID = 2603106652557807488L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			c.setFire(true);
		}
	}

	static class fireReleased extends AbstractAction{
		/**
		 * generated serial to suppress warnings
		 */
		private static final long serialVersionUID = -4127790348238725822L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			c.setFire(false);
		}
	}

	static class startPressed extends AbstractAction{
		/**
		 * generated serial to suppress warnings
		 */
		private static final long serialVersionUID = 1297957199382345574L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			c.setStart(true);
		}
	}

	static class startReleased extends AbstractAction{
		/**
		 * generated serial to suppress warnings
		 */
		private static final long serialVersionUID = -5326323446301818867L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			c.setStart(false);
		}
	}
}
