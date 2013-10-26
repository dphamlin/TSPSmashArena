package game;

/**
 * Class for the bullets and projectiles being fired
 * 
 * @author Jacob Charles
 *
 */
public class Shot extends GameObject {
	private int t; //type
	private int l; //life time
	private int  s; //source, null means no source, can hurt anyone
	//TODO: Remove the Actor reference here, to reduce gson packet size

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
	public Shot(int x, int y, int source) {
		super(x, y);
		this.s = source;
	}
	
	/**
	 * Create a bullet from a generic type
	 * @param base
	 * 		bullet template
	 * @param source
	 * 		actor source
	 */
	public Shot(ShotModel base, Actor a) {
		this.s = a.getId();
		setSkin(base.getSkin());
		this.t = base.getType();
		this.l = base.getLife();
		setW(base.getW());
		setH(base.getH());
		setVCenter(a.getVCenter());
		setVy(0);
		if (a.getDir() > 0) {
			setLeftEdge(a.getRightEdge());
			setVx(base.getSpeed());
		}
		else {
			setRightEdge(a.getLeftEdge());
			setVx(-base.getSpeed());
		}
	}

	/*getters and setters for attributes*/
	public int getType() {
		return t;
	}
	public void setType(int type) {
		this.t = type;
	}
	public int getLifeTime() {
		return l;
	}
	public void setLifeTime(int lifeTime) {
		this.l = lifeTime;
	}
	public int getSource() {
		return s;
	}
	public void setSource(int source) {
		this.s = source;
	}
}
