package game;

/**
 * Store the game results for a single actor
 * 
 * @author Jacob Charles
 */
import java.util.ArrayList;

public class ActorResults {
	private ArrayList<Integer> d = new ArrayList<Integer>(); //deaths
	private ArrayList<Integer> k = new ArrayList<Integer>(); //kills
	private int l; //remaining lives

	//getters and setters
	public ArrayList<Integer> getDeaths() {
		return d;
	}
	public void addDeath(int id) {
		d.add(id);
	}
	public void resetDeaths() {
		d.clear();
	}
	public ArrayList<Integer> getKills() {
		return k;
	}
	public void addKill(int id) {
		k.add(id);
	}
	public void resetKills() {
		d.clear();
	}
	public int getScore() {
		return k.size();
	}
	public int getLives() {
		return l;
	}
	public void setLives(int lives) {
		this.l = lives;
	}
}
