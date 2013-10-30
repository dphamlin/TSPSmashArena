package game;

/**
 * Class for general items and special effects
 * 
 * @author Jacob Charles, Dean Hamlin
 *
 */

public class Item extends GameObject {

	//powerrup types
	public static final int BIG = 1; //get huge and beefy
	public static final int DJUMP = 2; //get an air jump
	public static final int SPEED = 3; //move fast and slip around
	public static final int SSHOT = 4; //faster, piercing shots
	public static final int CHANGE = 5; //temporary transformation
	public static final int HYPER = 6; //hypermode, ending with a bang
	public static final int LIFE = 7; //ultra-rare one-up

	private int t; //type
	private int st; //subtype
	private int l; //life time

	/*getters and setters for attributes*/
	public int getType() {
		return t;
	}
	public void setType(int type) {
		this.t = type;
	}
	public int getSubType() {
		return st;
	}
	public void setSubType(int subType) {
		this.st = subType;
	}
	public int getLifeTime() {
		return l;
	}
	public void setLifeTime(int lifeTime) {
		this.l = lifeTime;
	}
}
