package game;

/**
 * Class for the player's characters
 * 
 * @author Jacob Charles, Dean Hamlin
 *
 */

public class Item extends GameObject {

	private int type;
	private int subType;
	private int lifeTime;

	/*getters and setters for attributes*/
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getSubType() {
		return subType;
	}
	public void setSubType(int subType) {
		this.subType = subType;
	}
	public int getLifeTime() {
		return lifeTime;
	}
	public void setLifeTime(int lifeTime) {
		this.lifeTime = lifeTime;
	}
}
