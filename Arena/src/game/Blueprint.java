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
	private int bg, bgm; //image, music, etc?
	private int tileset; //id for a tile set from a big storage set of them

	//TODO: Add a list of spawn points somehow 

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
}
