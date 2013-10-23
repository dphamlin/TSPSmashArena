package game;

/**
 * Class for the player's characters
 * 
 * @author Jacob Charles
 *
 */
public class Actor extends GameObject {
	
	//current data
	private Player owner;
	private int airTime;
	private int deadTime;
	private int reload;
	private int dir = 1;
	private int powerup;
	private Land onLand;
	
	//eventually replace this with a reference to a "character archetype?"
	private int skin;
	private int runSpeed;
	private int jumpPower;
	private int shotDelay;
	private int shotSpeed, shotLife;
	private int shotWid, shotHei;
	
	/**
	 * Spawn a player character a location
	 * 
	 * @param x
	 * 		starting x
	 * @param y
	 * 		starting y
	 * @param owner
	 * 		associated player object
	 * TODO: Associate this with a "base" player
	 */
	public Actor (int x, int y, Player owner) {
		super(x, y, 16, 16);
		this.owner = owner;
		dir = 1;
		airTime = 1;
		onLand = null;
		powerup = 0;
	}
	
	/**
	 * @return the leftmost edge of the object
	 */
	public int getLeftEdge() {
		return getX()-getW()/2;
	}
	/**
	 * @return the topmost edge of the object
	 */
	public int getTopEdge() {
		return getY()-getH();
	}
	/**
	 * @return the rightmost edge of the object
	 */
	public int getRightEdge() {
		return getX()+getW()/2;
	}
	/**
	 * @return the bottom-most edge of the object
	 */
	public int getBottomEdge() {
		return getY();
	}
	/**
	 * Set the position of the left edge without resizing
	 * 
	 * @param x the new left edge of the object
	 */
	public void setLeftEdge(int x) {
		setX(x+getW()/2);
	}
	/**
	 * Set the position of the top edge without resizing
	 * 
	 * @param y the new top edge of the object
	 */
	public void setTopEdge(int y) {
		setY(y+getH());
	}
	/**
	 * Set the position of the right edge without resizing
	 * 
	 * @param x the new right edge of the object
	 */
	public void setRightEdge(int x) {
		setX(x-getW()/2);
	}
	/**
	 * Set the position of the bottom edge without resizing
	 * 
	 * @param y the new bottom edge of the object
	 */
	public void setBottomEdge(int y) {
		setY(y);
	}
	
	/*getters and setters for attributes*/
	public Player getOwner() {
		return owner;
	}
	public void setOwner(Player owner) {
		this.owner = owner;
	}
	public int getAirTime() {
		return airTime;
	}
	public void setAirTime(int airTime) {
		this.airTime = airTime;
	}
	public int getDeadTime() {
		return deadTime;
	}
	public void setDeadTime(int deadTime) {
		this.deadTime = deadTime;
	}
	public int getReload() {
		return reload;
	}
	public void setReload(int reload) {
		this.reload = reload;
	}
	public int getDir() {
		return dir;
	}
	public void setDir(int dir) {
		this.dir = dir;
	}
	public int getPowerup() {
		return powerup;
	}
	public void setEquip(int powerup) {
		this.powerup = powerup;
	}
	public Land getOnLand() {
		return onLand;
	}
	public void setOnLand(Land onLand) {
		this.onLand = onLand;
	}
	public int getSkin() {
		return skin;
	}
	public void setSkin(int skin) {
		this.skin = skin;
	}
	public int getRunSpeed() {
		return runSpeed;
	}
	public void setRunSpeed(int runSpeed) {
		this.runSpeed = runSpeed;
	}
	public int getJumpPower() {
		return jumpPower;
	}
	public void setJumpPower(int jumpPower) {
		this.jumpPower = jumpPower;
	}
	public int getShotDelay() {
		return shotDelay;
	}
	public void setShotDelay(int shotDelay) {
		this.shotDelay = shotDelay;
	}
	public int getShotSpeed() {
		return shotSpeed;
	}
	public void setShotSpeed(int shotSpeed) {
		this.shotSpeed = shotSpeed;
	}
	public int getShotLife() {
		return shotLife;
	}
	public void setShotLife(int shotLife) {
		this.shotLife = shotLife;
	}
	public int getShotWid() {
		return shotWid;
	}
	public void setShotWid(int shotWid) {
		this.shotWid = shotWid;
	}
	public int getShotHei() {
		return shotHei;
	}
	public void setShotHei(int shotHei) {
		this.shotHei = shotHei;
	}
}
