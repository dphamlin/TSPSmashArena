package game;

/**
 * Contains all objects and actors in the current game session
 * Different versions are implemented between the client and server
 * 
 * @author Jacob Charles
 *
 */
public abstract class GameState {
	private ArrayList<GameObject> list;
	
	public ArrayList<GameObject> getList() {
		return list;
	}
}
