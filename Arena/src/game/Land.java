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
	public static final int LEVEL = 32; //for in-game level select
	private static final int MAX = 63; //sum of all previous
	
	//current state is a bitmask
	private int type;
	
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
		this.type = type;
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
		this.type = model.getType();
		setSkin(model.getSkin());
	}*/
	
	/*land detail getter and setters*/
	public boolean isSolid() {
		return (type&SOLID) > 0;
	}
	public void setSolid(boolean b) {
		if (b) type |= SOLID;
		else type &= MAX-SOLID;
	}
	public boolean isPlatform() {
		return (type&PLATFORM) > 0;
	}
	public void setPlatform(boolean b) {
		if (b) type |= PLATFORM;
		else type &= MAX-PLATFORM;
	}
	public boolean isDanger() {
		return (type&DANGER) > 0;
	}
	public void setDanger(boolean b) {
		if (b) type |= DANGER;
		else type &= MAX-DANGER;
	}
	public boolean isWater() {
		return (type&WATER) > 0;
	}
	public void setWater(boolean b) {
		if (b) type |= WATER;
		else type &= MAX-WATER;
	}
	public boolean isSlip() {
		return (type&SLIP) > 0;
	}
	public void setSlip(boolean b) {
		if (b) type |= SLIP;
		else type &= MAX-SLIP;
	}
}
