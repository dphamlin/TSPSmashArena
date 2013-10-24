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

	/**
	 * Do-nothing constructor
	 */
	public Shot() {
		super();
	}
	
	/**
	 * Spawn a shot at a location
	 * @param x
	 * 		starting x
	 * @param y
	 * 		starting y
	 */
	public Shot(int x, int y, Actor source) {
		super(x, y);
		this.source = source;
	}
	
	/**
	 * Create a bullet from a generic type
	 * @param base
	 * 		bullet template
	 * @param source
	 * 		actor source
	 */
	//TODO: Add required classes for this to work
	/*public Shot(ShotModel base, Actor source) {
		this.source = source;
		this.type = base.getType();
		this.lifeTime = base.getLife();
		setW(base.getW());
		setH(base.getH());
		setVCenter(source.getVCenter());
		setVy(0);
		if (source.getDir() > 0) {
			setLeftEdge(source.getRightEdge());
			setVx(base.getSpeed());
		}
		else {
			setRightEdge(source.getLeftEdge());
			setVx(-base.getSpeed());
		}
	}*/
	
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
