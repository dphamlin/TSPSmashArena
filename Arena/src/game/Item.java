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
	public static final int MINI = 2; //get teensy
	public static final int DJUMP = 3; //get an air jump
	public static final int SPEED = 4; //move fast and slip around
	public static final int SSHOT = 5; //faster, piercing shots
	public static final int CHANGE = 6; //temporary transformation
	public static final int HYPER = 7; //hypermode, ending with a bang
	public static final int LIFE = 8; //rare one-up
	public static final int ITEM_NUM = 7; //total number of items (generally)

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
