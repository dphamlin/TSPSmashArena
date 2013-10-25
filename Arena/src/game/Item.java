package game;

/**
 * Class for general items and special effects
 * 
 * @author Jacob Charles, Dean Hamlin
 *
 */

public class Item extends GameObject {

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
