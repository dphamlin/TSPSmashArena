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

	private ArrayList<Actor> fighters = new ArrayList<Actor>();
	private ArrayList<Land> level = new ArrayList<Land>();
	private ArrayList<Shot> bullets = new ArrayList<Shot>();
	private ArrayList<Item> powerups = new ArrayList<Item>();
	//private ArrayList<Player> players = new ArrayList<Player>();
	private int stage;
	private int frameNumber = 0;
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
		fighters = g.getFighters();
		level = g.getLevel();
		bullets = g.getBullets();
		powerups = g.getPowerups();
		stage = g.getStage();
		frameNumber = g.getFrameNumber();
	}

	/**
	 * Add another player to the game (test)
	 */
	public Actor addPlayer() {
		Actor a = new Actor(200+fighters.size()*100, 100);
		fighters.add(a);
		return a;
	}

	/**
	 * Construct a basic level (test)
	 */
	public void initTestLevel() {
		fighters.clear();
		level.clear();
		bullets.clear();
		powerups.clear();
		//players.clear();
		stage = -1;
		//a solid base and three platforms
		level.add(new Land(WIDTH/4, HEIGHT*3/4, WIDTH/2, 48, Land.SOLID));
		level.add(new Land(WIDTH/4, HEIGHT*3/4-40, WIDTH/8, 4, Land.PLATFORM));
		level.add(new Land(WIDTH*7/16, HEIGHT*3/4-90, WIDTH/8, 24, Land.SOLID));
		level.add(new Land(WIDTH*5/8, HEIGHT*3/4-40, WIDTH/8, 4, Land.PLATFORM));
	}
	
	/**
	 * Initialize the level off of a Blueprint from the static list
	 * @param i
	 */
	//TODO: The classes needed to use this
	/*public void setLevel(int i) {
		//clean up potential leftovers
		level.clear();
		bullets.clear();
		powerups.clear();
		//grab the level blueprint
		Blueprint nMap = Warehouse.getMaps()[i];
		
		//construct level
		stage = i;
		//TODO: Store BG and BGM in GameState
		for (LandModel lm : nMap.getPieces()) {
			level.add(new Land(lm));
		}
		//TODO: Set spawn points?
	}*/
	

	/**
	 * Get a list of the current fighters
	 * 
	 * @return an ArrayList of Actors
	 */
	public ArrayList<Actor> getFighters() {
		return fighters;
	}

	/**
	 * Get a list of the level objects
	 * 
	 * @return an ArrayList of Land objects
	 */
	public ArrayList<Land> getLevel() {
		return level;
	}

	/**
	 * Get a list of the bullets and projectiles
	 * 
	 * @return an ArrayList of Shots
	 */
	public ArrayList<Shot> getBullets() {
		return bullets;
	}

	/**
	 * Get the list of powerups in the stage
	 * 
	 * @return an ArrayList of Items
	 */
	public ArrayList<Item> getPowerups() {
		return powerups;
	}

	/**
	 * Check the number of players in the current game
	 * 
	 * @return
	 * 		number of players in the game
	 */
	public int getNumberOfPlayers() {
		return fighters.size();
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
		return fighters.get(n);
	}

	/**
	 * 
	 * @return
	 * 		the current stage number
	 */
	public int getStage() {
		return stage;
	}

	/**
	 * @return the frame number
	 */
	public int getFrameNumber() {
		return frameNumber;
	}

	/**
	 * reset the loop number
	 */
	public void resetFrames() {
		this.frameNumber = 0;
	}

	/**
	 * increment the loop number
	 */
	public void incrementFrames() {
		this.frameNumber++;
	}
}
