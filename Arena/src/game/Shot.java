package game;

/**
 * Class for the bullets and projectiles being fired
 * 
 * @author Jacob Charles
 *
 */
public class Shot extends GameObject {
	private int type;
	private int lifeTime;
	private Actor source; //null means no source, can hurt anyone
	
	/*getters and setters for attributes*/
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getLifeTime() {
		return lifeTime;
	}
	public void setLifeTime(int lifeTime) {
		this.lifeTime = lifeTime;
	}
	public Actor getSource() {
		return source;
	}
	public void setSource(Actor source) {
		this.source = source;
	}
}
