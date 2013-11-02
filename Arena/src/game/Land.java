package game;

/**
 * Class for terrain objects
 * 
 * @author Jacob Charles
 *
 */
public class Land extends GameObject {
	//mask values
	public static final int SOLID = 1; //solid all around (includes top)
	public static final int PLATFORM = 2; //solid top
	public static final int DANGER = 4; //kill on contact
	public static final int BOUNCE = 8; //bouncy (var = bounciness mod, 0 = 100%, 5 = 150%, -2 = 80%, etc.)
	public static final int SLIP = 16; //slippery
	public static final int MOVE = 32; //moves you (var = speed*10)
	public static final int HATCH = 64; //appears when control is on
	public static final int NHATCH = 128; //appears when controls is off
	public static final int SWITCH = 256; //toggles control when hit
	public static final int PIPE = 512; //slide in, slide out
	public static final int WARP = 1024; //for in-game level select (var = target level)
	public static final int CHAR = 2048; //change characters (var = target character)
	public static final int OPTION = 4096; //for in game options (var = various things)
	public static final int COLOR = 8192; //painted a different color (temp)
	private static final int MAX = 16384-1; //sum of all previous

	//current type is a bitmask
	private int t; //type
	private int v; //extra data
	private int id, m; //id and map number
	
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
	public boolean isHatch() {
		return (t&HATCH) > 0;
	}
	public void setHatch(boolean b) {
		if (b) t |= HATCH;
		else t &= MAX-HATCH;
	}
	public boolean isNHatch() {
		return (t&NHATCH) > 0;
	}
	public void setNHatch(boolean b) {
		if (b) t |= NHATCH;
		else t &= MAX-NHATCH;
	}
	public boolean isSwitch() {
		return (t&SWITCH) > 0;
	}
	public void setSwitch(boolean b) {
		if (b) t |= SWITCH;
		else t &= MAX-SWITCH;
	}
	public boolean isPipe() {
		return (t&PIPE) > 0;
	}
	public void setPipe(boolean b) {
		if (b) t |= PIPE;
		else t &= MAX-PIPE;
	}
	public boolean isWarp() {
		return (t&WARP) > 0;
	}
	public void setWarp(boolean b) {
		if (b) t |= WARP;
		else t &= MAX-WARP;
	}
	public boolean isChar() {
		return (t&CHAR) > 0;
	}
	public void setChar(boolean b) {
		if (b) t |= CHAR;
		else t &= MAX-CHAR;
	}
	public boolean isOption() {
		return (t&OPTION) > 0;
	}
	public void setOption(boolean b) {
		if (b) t |= OPTION;
		else t &= MAX-OPTION;
	}
	public boolean isColor() {
		return (t&COLOR) > 0;
	}
	public void setColor(boolean b) {
		if (b) t |= COLOR;
		else t &= MAX-COLOR;
	}
	
	public int getVar() {
		return v;
	}
	public void setVar(int var) {
		this.v = var;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMap() {
		return m;
	}
	public void setMap(int map) {
		this.m = map;
	}
}
