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
	
	/**
	 * @return the leftmost edge of the object
	 */
	public int getLeftEdge() {
		return getX();
	}
	/**
	 * @return the topmost edge of the object
	 */
	public int getTopEdge() {
		return getY();
	}
	/**
	 * @return the rightmost edge of the object
	 */
	public int getRightEdge() {
		return getX()+getW();
	}
	/**
	 * @return the bottom-most edge of the object
	 */
	public int getBottomEdge() {
		return getY()+getH();
	}
	/**
	 * Set the position of the left edge without resizing
	 * 
	 * @param x the new left edge of the object
	 */
	public void setLeftEdge(int x) {
		setX(x);
	}
	/**
	 * Set the position of the top edge without resizing
	 * 
	 * @param y the new top edge of the object
	 */
	public void setTopEdge(int y) {
		setY(y);
	}
	/**
	 * Set the position of the right edge without resizing
	 * 
	 * @param x the new right edge of the object
	 */
	public void setRightEdge(int x) {
		setX(x-getW());
	}
	/**
	 * Set the position of the bottom edge without resizing
	 * 
	 * @param y the new bottom edge of the object
	 */
	public void setBottomEdge(int y) {
		setY(y-getH());
	}
	
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
