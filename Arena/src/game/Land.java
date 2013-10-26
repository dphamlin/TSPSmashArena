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
	public static final int WARP = 32; //for in-game level select
	private static final int MAX = 63; //sum of all previous
	
	//current type is a bitmask
	private int t; //type
	
	/**
	 * Construct a rectangular land at the set space
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param t
	 * 		bitmask of the platform t
	 */
	public Land(int x, int y, int w, int h, int t) {
		super(x, y, w, h);
		this.t = t;
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
	public boolean isWarp() {
		return (t&WARP) > 0;
	}
	public void setWarp(boolean b) {
		if (b) t |= WARP;
		else t &= MAX-WARP;
	}
}
