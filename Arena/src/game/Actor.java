package game;

/**
 * Class for the player's characters
 * 
 * @author Jacob Charles
 *
 */
public class Actor extends GameObject {
	
	private Player owner;
	private int airTime;
	private int deadTime;
	private int reload;
	private int dir;
	private int animation;
	private int skin;
	private int powerup;
	private Land onLand;
	
	private int runSpeed;
	private int jumpPower;
	private int shotTime;
	private int shotSpeed, shotLife;
	private int shotWid, shotHei;
	
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
	public int getAnimation() {
		return animation;
	}
	public void setAnimation(int animation) {
		this.animation = animation;
	}
	public int getSkin() {
		return skin;
	}
	public void setSkin(int skin) {
		this.skin = skin;
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
	public int getshotTime() {
		return shotTime;
	}
	public void setshotTime(int shotTime) {
		this.shotTime = shotTime;
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
