package game;

/**
 * Abstract class for the game objects
 * 
 * @author Jacob Charles
 *
 */
public abstract class GameObject {
	
	//Secretly, x, y, vx, and vy are fixed point values multiplied by scale, and shown outside as floats
	private int x, y;
	private int vx = 0, vy = 0;
	private int w, h;
	private int f;
	private int sk;
	private int d = 0;
	
	private static final float scale = 1000; //multiplier for fixed point storage
	
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
		//this.x = x;
		//this.y = y;
		this.x = (int)(x*scale);
		this.y = (int)(y*scale);
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
		//this.x = x;
		//this.y = y;
		this.x = (int)(x*scale);
		this.y = (int)(y*scale);
		this.h = h;
		this.w = w;
	}
	
	/**
	 * @return the leftmost edge of the object
	 */
	public float getLeftEdge() {
		return getX();
	}
	/**
	 * @return the topmost edge of the object
	 */
	public float getTopEdge() {
		return getY();
	}
	/**
	 * @return the rightmost edge of the object
	 */
	public float getRightEdge() {
		return (getX()+getW());
	}
	/**
	 * @return the bottom-most edge of the object
	 */
	public float getBottomEdge() {
		return (getY()+getH());
	}
	/**
	 * @return the horizontal center of the object
	 */
	public float getHCenter() {
		return (getX()+getW()/2);
	}
	/**
	 * @return the vertical center of the object
	 */
	public float getVCenter() {
		return (getY()+getH()/2);
	}
	/**
	 * Set the position of the left edge without resizing
	 * 
	 * @param x the new left edge of the object
	 */
	public void setLeftEdge(double x) {
		setX(x);
	}
	/**
	 * Set the position of the top edge without resizing
	 * 
	 * @param y the new top edge of the object
	 */
	public void setTopEdge(double y) {
		setY(y);
	}
	/**
	 * Set the position of the right edge without resizing
	 * 
	 * @param x the new right edge of the object
	 */
	public void setRightEdge(double x) {
		setX(x-getW());
	}
	/**
	 * Set the position of the bottom edge without resizing
	 * 
	 * @param y the new bottom edge of the object
	 */
	public void setBottomEdge(double y) {
		setY(y-getH());
	}
	/**
	 * Set the position of the horizontal center
	 * 
	 * @param x the new horizontal center
	 */
	public void setHCenter(double x) {
		setX(x-getW()/2);
	}
	/**
	 * Set the position of the vertical center
	 * 
	 * @param y the new vertical center
	 */
	public void setVCenter(double y) {
		setY(y-getH()/2);
	}
	/**
	 * Set the position of the object's center
	 * 
	 * @param x the new horizontal center
	 * @param y the new vertical center
	 */
	public void setCenter(double x, double y) {
		setHCenter(x);
		setVCenter(y);
	}
	/*
	 * Getters for basic properties 
	 */
	public float getX() {
		//return x;
		return (float)(x/scale);
	}
	public float getY() {
		//return y;
		return (float)(y/scale);
	}
	public float getVx() {
		//return vx;
		return (float)(vx/scale);
	}
	public float getVy() {
		//return vy;
		return (float)(vy/scale);
	}
	public int getW() {
		return w;
	}
	public int getH() {
		return h;
	}
	public int getFrame() {
		return f;
	}
	public int getSkin() {
		return sk;
	}

	/*
	 * Setters for basic properties
	 */
	public void setX(double x) {
		this.x = (int)(scale*x);
	}
	public void setY(double y) {
		this.y = (int)(scale*y);
	}
	public void setVx(double vx) {
		this.vx = (int)(scale*vx);
	}
	public void setVy(double vy) {
		this.vy = (int)(scale*vy);
	}
	public void setW(int w) {
		this.w = w;
	}
	public void setH(int h) {
		this.h = h;
	}
	public void setFrame(int frame) {
		this.f = frame;
	}
	public void setSkin(int skin) {
		this.sk = skin;
	}

	public boolean isDead() {
		return d != 0;
	}
	//TODO: Make sure nothing breaks here
	public void setDead(boolean dead) {
		if (dead) d |= 1;
		else d &= -2;
	}
	public boolean isSuspend() {
		return ((d >> 1)&1) != 0;
	}
	public void setSuspend(boolean suspend) {
		if (suspend) d |= 2;
		else d &= -3;
	}
}
