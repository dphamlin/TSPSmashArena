package game;

/**
 * Store results from a game
 * 
 * @author Jacob Charles
 */
import java.util.ArrayList;

public class GameResults {
	private ArrayList<ActorResults> r = new ArrayList<ActorResults>(); //individual player results
	private ArrayList<Integer> w; //winners (get from gamestate)
	private int t; //match time
	private int ml; //maximum lives
	
	//getters and setters
	public ArrayList<ActorResults> getResults() {
		return r;
	}
	public ArrayList<Integer> getWinners() {
		return w;
	}
	public void setWinners(ArrayList<Integer> winners) {
		this.w = winners;
	}
	public int getTime() {
		return t;
	}
	public void setTime(int time) {
		this.t = time;
	}
	public int getStock() {
		return ml;
	}
	public void setStock(int stock) {
		this.ml = stock;
	}

	//easy access to individual Actor results
	public ArrayList<Integer> getDeaths(int i) {
		return r.get(i).getDeaths();
	}
	public void addDeath(int i, int id) {
		r.get(i).addDeath(id);
	}
	public void resetDeaths(int i) {
		r.get(i).resetDeaths();
	}
	public ArrayList<Integer> getKills(int i) {
		return r.get(i).getKills();
	}
	public void addKill(int i, int id) {
		r.get(i).addKill(id);
	}
	public void resetKills(int i) {
		r.get(i).resetKills();
	}
	public int getScore(int i) {
		return r.get(i).getScore();
	}
	public int getLives(int i) {
		return r.get(i).getLives();
	}
	public void setLives(int i, int lives) {
		r.get(i).setLives(lives);
	}
}
