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
	private ArrayList<Item> p = new ArrayList<Item>(); //power ups
	private int c = 1; //control signal
	
	private int s = 0; //stage (default: holodeck)
	private int fn = 0; //frame number
	private int t = 50*60; //overall game time (default 1 min)
	private int ml = 5; //starting lives (default 5)
	private int m = MENU, nm = STOCK; //mode and impending mode (default starts on MENU)
	private int end = 0; //is the game over?
	private int np = 0; //expected number of players (0 is no limit)

	public static final int WIDTH = 640, HEIGHT = 480; //field dimensions
	public static final int MENU = 0, STOCK = 1, TIME = 2; //game modes

	/**
	 * default constructor
	 */
	public GameState() {}
	
	/**
	 * Start game state with specific number of players
	 * 
	 * @param players
	 * 		number of players expected in the game
	 */
	public GameState(int players) {
		np = players;
	}

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
		setControl(g.isControl());
		s = g.getStage();
		fn = g.getFrameNumber();
		t = g.getTime();
		ml = g.getStock();
		m = g.getMode();
		nm = g.getNextMode();
		np = g.getMaxPlayers();
		setEnd(g.isGameOver());
	}

	/**
	 * Add another player to the game (test)
	 */
	public Actor addPlayer() {
		return addPlayer(Warehouse.CAPTAIN); //start as a redshirt
	}

	/**
	 * Add another player to the game (test)
	 * 
	 * @param model
	 * 		the character they selected to spawn as
	 */
	public Actor addPlayer(int character) {
		//don't go over the maximum (4 default)
		if (getMaxPlayers() > 0 && getNumberOfPlayers() == getMaxPlayers()) return null;
		else if (getNumberOfPlayers() == 4) return null;
		
		Actor a = new Actor(0, 0, character); //start somewheres
		//move to spawn point, assign starting values
		a.setLives(getStock());
		a.setId(f.size());
		a.setLives(getStock());
		a.setHCenter(getSpawnX(a.getId()));
		a.setVCenter(getSpawnY(a.getId()));
		f.add(a);
		return a;
	}
	
	/**
	 * Suspend the participant's actor
	 * 
	 * @param p
	 * 		the participant being suspended
	 */
	public void suspendPlayer(Participant p) {
		//TODO: Make a resumable version
	}

	/**
	 * Resume the participant's actor
	 * 
	 * @param p
	 * 		the participant being resumed
	 */
	public void resumePlayer(Participant p) {
		
	}
	
	/**
	 * Load up a test level
	 */
	public void initTestLevel() {
		setLevel(Warehouse.DEMO);
		setMode(STOCK);
	}
	
	/**
	 * Initialize the level off of a Blueprint from the static list
	 * (USE warpTo FOR ACTUAL IN-GAME FUNCTIONS!)
	 * 
	 * @param i
	 * 		the level number to load
	 */
	public void setLevel(int i) {
		//clean up potential leftovers
		b.clear();
		p.clear();
		c = 1;

		//identify level
		s = i;
	}
	
	/**
	 * Initialize the level off of a Blueprint from the static list
	 * (USE warpTo FOR ACTUAL IN-GAME FUNCTIONS!)
	 * 
	 * @param i
	 * 		the level number to load
	 */
	public void reSetLevel(int i) {
		setLevel(i);

		//move all players to spawn points and remove their powerups
		for (Actor a : f) {
			a.setDead(true);
			a.setDeadTime(0);
			a.setPowerup(0);
		}

		//reset the timer and end state
		resetFrameNumber();
		setEnd(false);
	}
	
	/**
	 * Initiates sudden death mode
	 * 
	 * @param players
	 * 		the participating player's IDs
	 */
	public void startSuddenDeath(ArrayList<Integer> players) {
		setMode(STOCK); //always a stock match
		//assume dead
		for (Actor a : f) {
			a.setLives(0);
		}
		//revive participants with 1 life
		for (Integer i : players) {
			getPlayer(i).setLives(1);
		}
		setLevel(s); //restart the stage
	}
	
	/**
	 * Get a list of the winners
	 * 
	 * @return a list of the winners
	 */
	public ArrayList<Integer> getWinners() {
		ArrayList<Integer> winners = new ArrayList<Integer>();
		//find winners for time mode
		if (getMode() == TIME && isGameOver()) {
			int hiscore = 0;
			for (Actor a : getFighters()) {
				if (a.getScore() > hiscore) { //score is higher, lose old winners
					hiscore = a.getScore();
					winners.clear();
				}
				if (a.getScore() == hiscore) { //log more than one winner
					winners.add(new Integer(a.getId()));
				}
			}
		}
		//find winners for stock mode
		if (getMode() == STOCK && isGameOver()) {
			for (Actor a : getFighters()) {
				if (a.getLives() > 0) { //only up to one will qualify
					winners.add(new Integer(a.getId()));
				}
			}
		}
		return winners;
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
	 * Get a list of the lands that can spawn powerups
	 * 
	 * @return an ArrayList of Land objects
	 */
	public ArrayList<Land> getPowerSpawn() {
		return Warehouse.getMaps()[s].getPowerupSpawns();
	}
	
	/**
	 * Find the x position of player's spawn point
	 * 
	 * @param i
	 * 		the player's spawn to get
	 * @return
	 * 		the x position of a spawn point
	 */
	public int getSpawnX (int i) {
		return Warehouse.getMaps()[s].getSpawnX(i);
	}

	/**
	 * Find the y position of player's spawn point
	 * 
	 * @param i
	 * 		the player's spawn to get
	 * @return
	 * 		the y position of a spawn point
	 */
	public int getSpawnY (int i) {
		return Warehouse.getMaps()[s].getSpawnY(i);
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
		if (n < 0) return null;
		return f.get(n);
	}

	/**
	 * @return true if the control signal is on
	 */
	public boolean isControl() {
		return (c != 0);
	}
	
	/**
	 * Change the control signal
	 * 
	 * @param b
	 * 		new value of the control signal
	 */
	public void setControl(boolean b) {
		if (b) c = 1;
		else c = 0;
	}
	
	/**
	 * 
	 * @return the current stage number
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
	public void resetFrameNumber() {
		this.fn = 0;
	}

	/**
	 * increment the loop number
	 */
	public void incrementFrames() {
		this.fn++;
	}

	/**
	 * @return the end timer
	 */
	public int getTime() {
		return t;
	}

	/**
	 * @return the time remaining
	 */
	public int getTimeLeft() {
		if (t-fn < 0) return 0;
		return t-fn;
	}
	
	/**
	 * @param time the new end time
	 */
	public void setTime(int time) {
		this.t = time;
	}

	/**
	 * @return the number of lives for stock mode
	 */
	public int getStock() {
		return ml;
	}

	/**
	 * @param stock new maximum lives for stock mode
	 */
	public void setStock(int stock) {
		this.ml = stock;
		for (Actor a : getFighters()) {
			a.setLives(stock);
		}
	}

	/**
	 * @return the game mode
	 */
	public int getMode() {
		return m;
	}

	/**
	 * @param m the new game mode
	 */
	public void setMode(int mode) {
		this.m = mode;
	}
	
	/**
	 * @return the next game mode
	 */
	public int getNextMode() {
		return nm;
	}

	/**
	 * @param m the new next game mode
	 */
	public void setNextMode(int nextMode) {
		this.nm = nextMode;
	}
	
	/**
	 * Check if the game is over
	 * 
	 * @return true if the game is done
	 */
	public boolean isGameOver() {
		return (end != 0);
	}
	
	/**
	 * Set the end state
	 * 
	 * @param end
	 * 		0 keeps playing, 1 ends the game
	 */
	public void setEnd(boolean end) {
		if (end) this.end = 1;
		else this.end = 0;
	}
	
	/**
	 * @return the expected total number of players
	 */
	public int getMaxPlayers() {
		return np;
	}

	/**
	 * @param players
	 * 			the new total number of players
	 */
	public void setMaxPlayers(int players) {
		this.np = players;
	}

	/**
	 * @return true if the game is ready to start
	 */
	public boolean isReady() {
		return (getNumberOfPlayers() == getMaxPlayers() || getMaxPlayers() < 1);
	}

	/**
	 * Get the name of the current stage
	 * 
	 * @return the name of the stage
	 */
	public String getMapName() {
		return Warehouse.getMaps()[s].getName();
	}
}
