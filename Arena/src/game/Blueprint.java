package game;

/**
 * Storage of an entire map
 * 
 * @author Jacob Charles
 */
import java.util.ArrayList;

public class Blueprint {
	private String name; //name, may or may not get used
	private ArrayList<Land> pieces = new ArrayList<Land>(); //the level terrain
	private ArrayList<Land> pspawns = new ArrayList<Land>(); //tiles that support powerups
	private int spawnX[] = {0,0,0,0}; //spawn point array (max 4 players)
	private int spawnY[] = {0,0,0,0};
	private int bg, bgm; //image, music, etc?
	private int id; //map number for internal use

	/**
	 * Add a land to the blueprint
	 * 
	 * @param l
	 * 		the land to be added
	 */
	public void add(Land l) {
		l.setId(pieces.size());
		l.setMap(id);
		pieces.add(l);
		if (l.isPowerSpawn()) pspawns.add(l);
	}

	/**
	 * Add a new land to the blueprint
	 * @param x
	 * 		left edge
	 * @param y
	 * 		top edge
	 * @param h
	 * 		height
	 * @param w
	 * 		width
	 * @param t
	 * 		land type bitmask
	 */
	public void add(int x, int y, int h, int w, int t) {
		Land l = new Land(x, y, h, w, t, 0);
		add(l);
	}
	
	/**
	 * Add a new land to the blueprint
	 * @param x
	 * 		left edge
	 * @param y
	 * 		top edge
	 * @param h
	 * 		height
	 * @param w
	 * 		width
	 * @param t
	 * 		land type bitmask
	 * @param v
	 * 		extra data for certain types
	 */
	public void add(int x, int y, int h, int w, int t, int v) {
		Land l = new Land(x, y, h, w, t, v);
		add(l);
	}

	//getters and setters
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Land> getPieces() {
		return pieces;
	}
	public ArrayList<Land> getPowerupSpawns() {
		return pspawns;
	}
	public int getBg() {
		return bg;
	}
	public void setBg(int bg) {
		this.bg = bg;
	}
	public int getBgm() {
		return bgm;
	}
	public void setBgm(int bgm) {
		this.bgm = bgm;
	}
	public int getSpawnX(int i) {
		return spawnX[i];
	}
	public int getSpawnY(int i) {
		return spawnY[i];
	}
	public void setSpawn(int i, int x, int y) {
		spawnX[i] = x;
		spawnY[i] = y;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
