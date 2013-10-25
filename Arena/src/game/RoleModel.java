package game;

/**
 * Archetype for Actor abilities and appearances
 * 
 * @author Jacob Charles
 */
public class RoleModel {
	private int skin; //sprite set
	private int w, h; //allow for variable dimensions
	private int runSpeed, airSpeed; //horizontal 'thrust's
	private int runMomentum, airMomentum; //friction values (percent out of 100)
	private int jumpPower, termVel; //rise and fall cap speeds
	private int gravNum = 1, gravDen = 1; //gravity strength (N per D frames)
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
	public int getRunSpeed() {
		return runSpeed;
	}
	public void setRunSpeed(int runSpeed) {
		this.runSpeed = runSpeed;
	}
	public int getAirSpeed() {
		return airSpeed;
	}
	public void setAirSpeed(int airSpeed) {
		this.airSpeed = airSpeed;
	}
	public int getRunMomentum() {
		return runMomentum;
	}
	public void setRunMomentum(int runMomentum) {
		this.runMomentum = runMomentum;
	}
	public int getAirMomentum() {
		return airMomentum;
	}
	public void setAirMomentum(int airMomentum) {
		this.airMomentum = airMomentum;
	}
	public int getJumpPower() {
		return jumpPower;
	}
	public void setJumpPower(int jumpPower) {
		this.jumpPower = jumpPower;
	}
	public int getTermVel() {
		return termVel;
	}
	public void setTermVel(int termVel) {
		this.termVel = termVel;
	}
	public int getGravNum() {
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
	}
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
