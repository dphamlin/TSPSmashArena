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
	private ArrayList<Player> players = new ArrayList<Player>();
	private int stage;
	
	/**
	 * @return
	 * 		a list of all entities in the current game
	 */
	/*public Iterable<GameObject> getPieces() {
		return pieces;
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
		return players.size();
	}
	
	/**
	 * Get a player object by index
	 * 
	 * @param n
	 * 		the id of the player to get
	 * @return
	 * 		a Player object
	 */
	public Player getPlayer(int n) {
		return players.get(n);
	}
	
	/**
	 * 
	 * @return
	 * 		the current stage number
	 */
	public int getStage() {
		return stage;
	}
}
