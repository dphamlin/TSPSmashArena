package game;

/**
 * Class for terrain objects
 * 
 * @author Jacob Charles
 *
 */
public class Land extends GameObject {
	//mask values
	public static final int SOLID = 1;
	public static final int PLATFORM = 2;
	public static final int DANGER = 4;
	public static final int BOUNCE = 8;
	public static final int SLIP = 16;
	public static final int MOVE = 32;
	public static final int WARP = 64; //for in-game level select
	public static final int CHAR = 128; //change characters
	public static final int OPTION = 256; //for in game options 
	private static final int MAX = 511; //sum of all previous

	//current type is a bitmask
	private int t; //type
	private int v; //extra data
	
	/**
	 * Construct a rectangular land at the set space
	 * @param x
	 * 		left edge
	 * @param y
	 * 		top edge
	 * @param w
	 * 		width
	 * @param h
	 * 		height
	 * @param t
	 * 		bitmask of the platform type
	 */
	public Land(int x, int y, int w, int h, int t) {
		super(x, y, w, h);
		this.t = t;
	}
	
	/**
	 * Construct a rectangular land at the set space
	 * 
	 * @param x
	 * 		left edge
	 * @param y
	 * 		top edge
	 * @param w
	 * 		width
	 * @param h
	 * 		height
	 * @param t
	 * 		bitmask of the platform type
	 * @param v
	 * 		extra data for certain types
	 */
	public Land(int x, int y, int w, int h, int t, int v) {
		super(x, y, w, h);
		this.t = t;
		this.v = v;
	}
	
	/*land detail getter and setters*/
	public boolean isSolid() {
		return (t&SOLID) > 0;
	}
	public void setSolid(boolean b) {
		if (b) t |= SOLID;
		else t &= MAX-SOLID;
	}
	public boolean isPlatform() {
		return (t&PLATFORM) > 0;
	}
	public void setPlatform(boolean b) {
		if (b) t |= PLATFORM;
		else t &= MAX-PLATFORM;
	}
	public boolean isDanger() {
		return (t&DANGER) > 0;
	}
	public void setDanger(boolean b) {
		if (b) t |= DANGER;
		else t &= MAX-DANGER;
	}
	public boolean isBounce() {
		return (t&BOUNCE) > 0;
	}
	public void setBounce(boolean b) {
		if (b) t |= BOUNCE;
		else t &= MAX-BOUNCE;
	}
	public boolean isSlip() {
		return (t&SLIP) > 0;
	}
	public void setSlip(boolean b) {
		if (b) t |= SLIP;
		else t &= MAX-SLIP;
	}
	public boolean isMove() {
		return (t&MOVE) > 0;
	}
	public void setMove(boolean b) {
		if (b) t |= MOVE;
		else t &= MAX-MOVE;
	}
	public boolean isWarp() {
		return (t&WARP) > 0;
	}
	public void setWarp(boolean b) {
		if (b) t |= WARP;
		else t &= MAX-WARP;
	}
	public boolean isOption() {
		return (t&OPTION) > 0;
	}
	public void setOption(boolean b) {
		if (b) t |= OPTION;
		else t &= MAX-OPTION;
	}
	
	public int getVar() {
		return v;
	}
	public void setVar(int var) {
		this.v = var;
	}
}
