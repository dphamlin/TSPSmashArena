package game;

/**
 * Abstract class for the game objects
 * 
 * @author Jacob Charles
 *
 */
public abstract class GameObject {
	
	private int x, y;
	private int vx = 0, vy = 0;
	private int w, h;
	private int frame;
	private int skin;
	private boolean dead = false;
	
	/**
	 * "Do nothing" generic constructor
	 */
	public GameObject() {
	}
	
	/**
	 * Create an object at a location
	 * 
	 * @param x
	 * 		starting x position
	 * @param y
	 * 		starting y position
	 */
	public GameObject(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Create an object at a location with dimensions
	 * 
	 * @param x
	 * 		starting x position
	 * @param y
	 * 		starting y position
	 * @param w
	 * 		starting width
	 * @param h
	 * 		starting height
	 */
	public GameObject(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.h = h;
		this.w = w;
	}
	
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
	 * @return the horizontal center of the object
	 */
	public int getHCenter() {
		return getX()+getW()/2;
	}
	/**
	 * @return the vertical center of the object
	 */
	public int getVCenter() {
		return getY()+getH()/2;
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
	/**
	 * Set the position of the horizontal center
	 * 
	 * @param x the new horizontal center
	 */
	public void setHCenter(int x) {
		setX(x-getW()/2);
	}
	/**
	 * Set the position of the vertical center
	 * 
	 * @param y the new vertical center
	 */
	public void setVCenter(int y) {
		setY(y-getH()/2);
	}
	/**
	 * Set the position of the object's center
	 * 
	 * @param x the new horizontal center
	 * @param y the new vertical center
	 */
	public void setCenter(int x, int y) {
		setHCenter(x);
		setVCenter(y);
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
	public int getSkin() {
		return skin;
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
	public void setSkin(int skin) {
		this.skin = skin;
	}

	public boolean isDead() {
		return dead;
	}
	public void setDead(boolean dead) {
		this.dead = dead;
	}
}
