package game;

/**
 * Abstract class for the game objects
 * 
 * @author Jacob Charles
 *
 */
public abstract class GameObject {
	
	protected int x, y;
	protected int vx, vy;
	protected int w, h;
	protected int frame;
	
	/*
	 * Getters for basic properties 
	 */
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getVx() {
		return vx;
	}
	public int getVy() {
		return vy;
	}
	public int getW() {
		return w;
	}
	public int getH() {
		return h;
	}
	public int getFrame() {
		return frame;
	}

	/*
	 * Setters for basic properties
	 */
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void setVx(int vx) {
		this.vx = vx;
	}
	public void setVy(int vy) {
		this.vy = vy;
	}
	public void setW(int w) {
		this.w = w;
	}
	public void setH(int h) {
		this.h = h;
	}
	public void setFrame(int frame) {
		this.frame = frame;
	}
}
