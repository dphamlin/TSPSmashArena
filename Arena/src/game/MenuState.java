package game;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Class to manage pre-game menus
 * 
 * @author Jacob Charles
 */

public class MenuState {

	public static final int QUIT = -1;
	public static final int MAIN = 0;
	public static final int HOST = 1;
	public static final int JOIN = 2;

	private static final int WIDTH = 640;
	private static final int HEIGHT = 480;

	private long loopNum = 0;
	private int state = MAIN; //which screen
	private int cursor = 0; //cursor position
	private boolean typing = false; //for text entry cases
	private int menuSize = 3; //number of options in the menu
	private String name = "A four-player battle platformer in space."; //name of the current menu
	private int players = 2; //number of players for hosting
	private String host = ""; //host's IP as a string


	/**
	 * Take controls and apply them
	 * 
	 * @param c
	 * 		controller for input
	 * @return next action
	 */
	public int update(Controller c) {
		//increment counter
		loopNum++;
		
		//movements
		if (!typing) {
			if (c.getUp() % 45 == 1) cursor--;
			if (c.getDown() % 45 == 1) cursor++;
			cursor = (cursor+menuSize)%menuSize;
		}
		boolean select = (c.getJump() == 1);
		boolean esc = (c.getStart() == 1);

		//selections
		if (typing) { //full keyboard input
			char ch = c.getTyped();
			if (ch == 8 || ch == 127) //backspace and delete
				host = host.substring(0, host.length()-1);
			else if (ch > 32 && ch < 127) //printable characters
				host += ch;
			else if (ch == 27 || ch == 10 || ch == 13) //escape and enter
				typing = false;
			
			c.setTyped((char)0); //data has been read
		}
		else if (state == MAIN) { //main menu options
			//host a game
			if (cursor == 0 && select) setState(HOST);
			//join a game
			if (cursor == 1 && select) setState(JOIN);
			//quit the game
			if (cursor == 2 && select) {
				return QUIT;
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
				return HOST;
			}
			//go back
			if (esc || cursor == 2 && select) setState(MAIN);
		}
		else if (state == JOIN) { //join menu options
			//fill out an IP address
			if (cursor == 0 && select && !typing) {
				c.setTyped((char)0);
				typing = true;
			}
			//join the game
			if (cursor == 1 && select) {
				return JOIN;
			}
			//go back
			if (esc || cursor == 2 && select) setState(MAIN);
		}

		return 0; //no decision made
	}

	/**
	 * Draw the menu to a screen
	 * 
	 * @param g
	 * 		graphics object to draw with
	 */
	public void draw(Graphics g) {
		//TODO: background fill
		//clear background
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 640, 480);
		g.setColor(Color.BLACK);
		
		//main menu
		if (state == MAIN) {
			g.drawString("TSPArena", WIDTH/2-25, HEIGHT/4);
			
			g.drawString("Host", WIDTH/2-10, HEIGHT/2-20);
			g.drawString("Join", WIDTH/2-10, HEIGHT/2);
			g.drawString("Quit", WIDTH/2-10, HEIGHT/2+20);
		}
		//host menu
		else if (state == HOST) {
			g.drawString("Host game", WIDTH/2-25, HEIGHT/4);
			
			g.drawString("< "+players+" players >", WIDTH/2-10, HEIGHT/2-20);
			g.drawString("Start", WIDTH/2-10, HEIGHT/2);
			g.drawString("Back", WIDTH/2-10, HEIGHT/2+20);
		}
		//join menu
		else if (state == JOIN) {
			g.drawString("Join game", WIDTH/2-25, HEIGHT/4);
			
			if (!typing || loopNum % 40 < 20)
				g.drawString("Host IP: "+host, WIDTH/2-10, HEIGHT/2-20);
			else
				g.drawString("Host IP: "+host+"|", WIDTH/2-10, HEIGHT/2-20);
			g.drawString("Connect", WIDTH/2-10, HEIGHT/2);
			g.drawString("Back", WIDTH/2-10, HEIGHT/2+20);
		}
		g.fillOval(WIDTH/2-20, HEIGHT/2+20*cursor-30, 8, 8);
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
