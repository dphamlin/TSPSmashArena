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
	public static final int WATER = 8;
	public static final int SLIP = 16;
	private static final int MAX = 31; //sum of all previous
	
	//current state is a bitmask
	private int state;
	
	/**
	 * Construct a rectangular land at the set space
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param type
	 * 		bitmask of the platform type
	 */
	public Land(int x, int y, int w, int h, int type) {
		super(x, y, w, h);
		state = type;
	}
	
	/*land detail getter and setters*/
	public boolean isSolid() {
		return (state&SOLID) > 0;
	}
	public void setSolid(boolean b) {
		if (b) state |= SOLID;
		else state &= MAX-SOLID;
	}
	public boolean isPlatform() {
		return (state&PLATFORM) > 0;
	}
	public void setPlatform(boolean b) {
		if (b) state |= PLATFORM;
		else state &= MAX-PLATFORM;
	}
	public boolean isDanger() {
		return (state&DANGER) > 0;
	}
	public void setDanger(boolean b) {
		if (b) state |= DANGER;
		else state &= MAX-DANGER;
	}
	public boolean isWater() {
		return (state&WATER) > 0;
	}
	public void setWater(boolean b) {
		if (b) state |= WATER;
		else state &= MAX-WATER;
	}
	public boolean isSlip() {
		return (state&SLIP) > 0;
	}
	public void setSlip(boolean b) {
		if (b) state |= SLIP;
		else state &= MAX-SLIP;
	}
}
