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
	public static final int WARP = 32; //for in-game level select
	private static final int MAX = 63; //sum of all previous
	
	//current type is a bitmask
	private int t;
	
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
	
	/**
	 * Build a Land from storage
	 * 
	 * @param model
	 * 		the stored form of the land
	 */
	//TODO: The classes required for this to work
	/*public Land(LandModel model) {
		super(model.getX(), model.getY(), model.getW(), model.getH());
		this.t = model.gett();
		setSkin(model.getSkin());
	}*/
	
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
	public boolean isWater() {
		return (t&WATER) > 0;
	}
	public void setWater(boolean b) {
		if (b) t |= WATER;
		else t &= MAX-WATER;
	}
	public boolean isSlip() {
		return (t&SLIP) > 0;
	}
	public void setSlip(boolean b) {
		if (b) t |= SLIP;
		else t &= MAX-SLIP;
	}
}
