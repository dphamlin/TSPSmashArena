package game;

/**
 * Class for terrain objects
 * 
 * @author Jacob Charles
 *
 */
public class Land extends GameObject {
	//mask values
	private static final int solid = 1;
	private static final int platform = 2;
	private static final int danger = 4;
	private static final int water = 8;
	private static final int slip = 16;
	private static final int max = 31; //sum of all previous
	
	//current state is a bitmask
	private int state;
	
	/*land detail getter and setters*/
	public boolean isSolid() {
		return (state&solid) > 0;
	}
	public void setSolid(boolean b) {
		if (b) state |= solid;
		else state &= max-solid;
	}
	public boolean isPlatform() {
		return (state&platform) > 0;
	}
	public void setPlatform(boolean b) {
		if (b) state |= platform;
		else state &= max-platform;
	}
	public boolean isDanger() {
		return (state&danger) > 0;
	}
	public void setDanger(boolean b) {
		if (b) state |= danger;
		else state &= max-danger;
	}
	public boolean isWater() {
		return (state&water) > 0;
	}
	public void setWater(boolean b) {
		if (b) state |= water;
		else state &= max-water;
	}
	public boolean isSlip() {
		return (state&slip) > 0;
	}
	public void setSlip(boolean b) {
		if (b) state |= slip;
		else state &= max-slip;
	}
}
