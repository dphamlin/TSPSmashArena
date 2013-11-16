package game;

import java.awt.Graphics;

/**
 * Class to manage pre-game menus
 * 
 * @author Jacob Charles
 */

public class MenuState {

	public static final int MAIN = 0;
	public static final int HOST = 1;
	public static final int JOIN = 2;

	private static final int WIDTH = 640;
	private static final int HEIGHT = 480;

	private int state = MAIN; //which screen
	private int cursor = 0; //cursor position
	private int menuSize = 3; //number of options in the menu
	private boolean typing = false; //for text entry cases
	private int players; //number of players for hosting
	private String host; //host's IP as a string
	private String name = "A four-player battle platformer in space."; //name of the current menu


	/**
	 * Take controls and apply them
	 * 
	 * @param c
	 * 		controller for input
	 */
	public void update(Controller c) {
		//movements
		if (!typing) {
			if (c.getUp() % 45 == 1) cursor--;
			if (c.getDown() % 45 == 1) cursor++;
			cursor = (cursor+menuSize)%menuSize;
		}
		boolean select = (c.getJump() == 1);
		boolean esc = (c.getStart() == 1);
		if (typing && esc) typing = false;

		//selections
		if (state == MAIN) { //main menu options
			//host a game
			if (cursor == 0 && select) setState(HOST);
			//join a game
			if (cursor == 1 && select) setState(JOIN);
			//quit the game
			if (cursor == 2 && select) {
				//TODO: Return code for quitting a game
			}
		}
		else if (state == HOST) { //host menu options
			//change number of players
			if (cursor == 0) {
				if (c.getLeft() % 45 == 1) players--;
				if (c.getRight() % 45 == 1 || select) players++;
				players = (((players-1)+4)%4) + 1; //1 to 4 range
			}
			//start the game
			if (cursor == 1 && select) {
				//TODO: Return code for starting a game
			}
			//go back
			if (esc || cursor == 2 && select) setState(MAIN);
		}
		else if (state == JOIN) { //join menu options
			//fill out an IP address
			if (cursor == 0 && !typing) {
				//TODO: Hook typing input to host
				typing = true;
			}
			//join the game
			if (cursor == 1 && select) {
				//TODO: Return code for joining a game
			}
			//go back
			if (esc || cursor == 2 && select) setState(MAIN);
		}
	}

	/**
	 * Draw the menu to a screen
	 * 
	 * @param g
	 * 		graphics object to draw with
	 */
	public void draw(Graphics g) {
		//TODO: Fill out
	}

	/**
	 * Jump to a new menu
	 * 
	 * @param state
	 * 		the new state to go to
	 */
	private void setState(int state) {
		this.state = state;
		cursor = 0;
		if (state == MAIN) {
			name = "A four-player battle platformer in space.";
			menuSize = 3;
		}
		if (state == HOST) {
			name = "Host game";
			menuSize = 3;
		}
		if (state == JOIN) {
			name = "Join game";
			menuSize = 3;
		}
	}

	/**
	 * @return the number of players
	 */
	public int getPlayers() {
		return players;
	}
	/**
	 * @return the host's IP
	 */
	public String getHost() {
		return host;
	}
	/**
	 * @return the current menu name
	 */
	public String getStateName() {
		return name;
	}
}
