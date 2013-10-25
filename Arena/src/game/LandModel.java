package game;

/**
 * Storage of a single terrain object
 * 
 * @author Jacob Charles
 */
public class LandModel {
	private int x, y;
	private int w, h;
	private int skin;
	private int type;

	//getters and setters
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getW() {
		return w;
	}
	public void setW(int w) {
		this.w = w;
	}
	public int getH() {
		return h;
	}
	public void setH(int h) {
		this.h = h;
	}
	public int getSkin() {
		return skin;
	}
	public void setSkin(int skin) {
		this.skin = skin;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}
