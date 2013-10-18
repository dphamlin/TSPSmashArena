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
	//Important consideration: faster way to access pieces of a specific type
	protected ArrayList<GameObject> pieces;
	protected ArrayList<Player> players;
	protected int stage;
	
	/**
	 * @return
	 * 		a list of all entities in the current game
	 */
	public Iterable<GameObject> getPieces() {
		return pieces;
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
