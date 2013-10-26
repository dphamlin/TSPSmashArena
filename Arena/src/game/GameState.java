package game;

/**
 * Contains all objects and actors in the current game session
 * Different versions are implemented between the client and server
 * 
 * @author Jacob Charles
 *
 */

import java.util.*;

public abstract class GameState {

	private ArrayList<Actor> f = new ArrayList<Actor>(); //fighters
	private ArrayList<Shot> b = new ArrayList<Shot>(); //bullets
	private ArrayList<Item> p = new ArrayList<Item>(); //powerups
	private int s; //stage
	private int fn = 0; //frame number
	
	public static final int WIDTH = 640, HEIGHT = 480;

	/**
	 * default constructor
	 */
	public GameState() {}

	/**
	 * Clone constructor
	 * 
	 * @param g
	 * 		GameState to control
	 */
	public GameState(GameState g) {
		f = g.getFighters();
		b = g.getBullets();
		p = g.getPowerups();
		s = g.getStage();
		fn = g.getFrameNumber();
	}

	/**
	 * Add another player to the game (test)
	 */
	public Actor addPlayer() {
		return addPlayer(0);
	}
	
	/**
	 * Add another player to the game (test)
	 * 
	 * @param model
	 * 		the character they selected to spawn as
	 */
	public Actor addPlayer(int character) {
		Actor a = new Actor(200+f.size()*100, 100, character); //TODO: Proper spawn points
		a.setId(f.size());
		f.add(a);
		return a;
	}

	/**
	 * Construct a basic level (test)
	 */
	public void initTestLevel() {
		setLevel(0);
	}
	
	/**
	 * Initialize the level off of a Blueprint from the static list
	 * @param i
	 * 		the level number to load
	 */
	public void setLevel(int i) {
		//clean up potential leftovers
		b.clear();
		p.clear();
		
		//identify level
		s = i;
		
		//TODO: Set spawn points?
	}
	

	/**
	 * Get a list of the current fighters
	 * 
	 * @return an ArrayList of Actors
	 */
	public ArrayList<Actor> getFighters() {
		return f;
	}

	/**
	 * Get a list of the level objects
	 * 
	 * @return an ArrayList of Land objects
	 */
	public ArrayList<Land> getLevel() {
		return Warehouse.getMaps()[s].getPieces();
	}

	/**
	 * Get a list of the bullets and projectiles
	 * 
	 * @return an ArrayList of Shots
	 */
	public ArrayList<Shot> getBullets() {
		return b;
	}

	/**
	 * Get the list of powerups in the stage
	 * 
	 * @return an ArrayList of Items
	 */
	public ArrayList<Item> getPowerups() {
		return p;
	}

	/**
	 * Check the number of players in the current game
	 * 
	 * @return
	 * 		number of players in the game
	 */
	public int getNumberOfPlayers() {
		return f.size();
	}

	/**
	 * Get a player object by index
	 * 
	 * @param n
	 * 		the id of the player to get
	 * @return
	 * 		a Player object
	 */
	public Actor getPlayer(int n) {
		return f.get(n);
	}

	/**
	 * 
	 * @return
	 * 		the current stage number
	 */
	public int getStage() {
		return s;
	}

	/**
	 * @return the frame number
	 */
	public int getFrameNumber() {
		return fn;
	}

	/**
	 * reset the loop number
	 */
	public void resetFrames() {
		this.fn = 0;
	}

	/**
	 * increment the loop number
	 */
	public void incrementFrames() {
		this.fn++;
	}
}
