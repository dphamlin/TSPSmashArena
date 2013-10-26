package game;

/**
 * Archetype for Actor abilities and appearances
 * 
 * @author Jacob Charles
 */
public class RoleModel {
	private int skin; //sprite set
	private int w, h; //allow for variable dimensions
	private float runSpeed, airSpeed; //horizontal 'thrust's
	private float runMomentum, airMomentum; //friction values (percent out of 100)
	private float jumpPower, termVel; //jump and fall speeds
	private int jumpHold; //jump control
	//private int gravNum = 1, gravDen = 1; //gravity strength (N per D frames)
	private float grav;
	private int shotDelay; //shot frequency
	private ShotModel shotType; //what kind of bullet you shoot

	//getters and setters for attributes
	public int getSkin() {
		return skin;
	}
	public void setSkin(int skin) {
		this.skin = skin;
	}
	public int getW() {
		return w;
	}
	public void setW(int w) {
		this.w = w;
	}
	public int getH() {
		return h;
	}
	public void setH(int h) {
		this.h = h;
	}
	public float getRunSpeed() {
		return runSpeed;
	}
	public void setRunSpeed(double runSpeed) {
		this.runSpeed = (float) runSpeed;
	}
	public float getAirSpeed() {
		return airSpeed;
	}
	public void setAirSpeed(double airSpeed) {
		this.airSpeed = (float) airSpeed;
	}
	public float getRunMomentum() {
		return runMomentum;
	}
	public void setRunMomentum(double runMomentum) {
		this.runMomentum = (float) runMomentum;
	}
	public float getAirMomentum() {
		return airMomentum;
	}
	public void setAirMomentum(double airMomentum) {
		this.airMomentum = (float) airMomentum;
	}
	public float getJumpPower() {
		return jumpPower;
	}
	public void setJumpPower(double jumpPower) {
		this.jumpPower = (float) jumpPower;
	}
	public int getJumpHold() {
		return jumpHold;
	}
	public void setJumpHold(int jumpHold) {
		this.jumpHold = jumpHold;
	}
	public float getTermVel() {
		return termVel;
	}
	public void setTermVel(double termVel) {
		this.termVel = (float) termVel;
	}
	public float getGrav() {
		return grav;
	}
	public void setGrav(double grav) {
		this.grav = (float) grav;
	}
	/*public int getGravNum() {
		return gravNum;
	}
	public void setGravNum(int gravNum) {
		this.gravNum = gravNum;
	}
	public int getGravDen() {
		return gravDen;
	}
	public void setGravDen(int gravDen) {
		this.gravDen = gravDen;
	}*/
	public int getShotDelay() {
		return shotDelay;
	}
	public void setShotDelay(int shotDelay) {
		this.shotDelay = shotDelay;
	}
	public ShotModel getShotType() {
		return shotType;
	}
	public void setShotType(ShotModel shotType) {
		this.shotType = shotType;
	}
}
