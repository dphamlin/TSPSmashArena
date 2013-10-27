package game;

/**
 * Storage of an entire map
 * 
 * @author Jacob Charles
 */
import java.util.ArrayList;

public class Blueprint {
	//private ArrayList<LandModel> pieces; //objects in the level
	private ArrayList<Land> pieces = new ArrayList<Land>();
	private int spawnX[] = {0,0,0,0};
	private int spawnY[] = {0,0,0,0};
	private int bg, bgm; //image, music, etc?
	private int tileset; //id for a tile set from a big storage set of them

	//TODO: Add a list of spawn points somehow 

	/**
	 * Add a new land to the blueprint
	 * 
	 * @param l
	 * 		the land to be added
	 */
	public void add(Land l) {
		pieces.add(l);
	}
	
	//getters and setters
	public ArrayList<Land> getPieces() {
		return pieces;
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
	public int getTileset() {
		return tileset;
	}
	public void setTileset(int tileset) {
		this.tileset = tileset;
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
	
}
