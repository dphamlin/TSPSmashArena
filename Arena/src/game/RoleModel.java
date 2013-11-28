package game;

/**
 * Archetype for Actor abilities and appearances
 * 
 * @author Jacob Charles
 */
public class RoleModel {
	private int skin; //sprite set
	private int w = 12, h = 15; //allow for variable dimensions (but mostly use the default
	private float runSpeed, airSpeed; //horizontal 'thrust's
	private float runSlip, airSlip; //friction values
	private float jumpPower; //jump power
	private int jumpHold; //jump control 
	private float grav, termVel; //gravity
	private float wallTermVel; //alternate terminal velocity for wallslide
	private float sink; //ability to forcibly sink
	private int spawnTime, spawnInv; //respawn values
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
	
	//special setMaxSpeed methods
	public float getMaxSpeed() {
		return runSpeed/getRunFrict(); //run chosen arbitrarily
	}
	public void setMaxSpeed(double maxSpeed) {
		this.runSpeed = (float) maxSpeed*getRunFrict();
		this.airSpeed = (float) maxSpeed*getAirFrict();
	}
	//end special methods
	
	public float getRunSlip() {
		return runSlip;
	}
	public void setRunSlip(double runSlip) {
		this.runSlip = (float) runSlip;
	}
	public float getAirSlip() {
		return airSlip;
	}
	public void setAirSlip(double airSlip) {
		this.airSlip = (float) airSlip;
	}
	
	//inverted forms for slip: friction
	public float getRunFrict() {
		return (float)1.0-runSlip;
	}
	public void setRunFrict(double runFrict) {
		this.runSlip = (float) (1.0-runFrict);
	}
	public float getAirFrict() {
		return (float)1.0-airSlip;
	}
	public void setAirFrict(double airFrict) {
		this.airSlip = (float) (1.0-airFrict);
	}
	//end inverted forms
	
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
	public float getWallTermVel() {
		return wallTermVel;
	}
	public void setWallTermVel(double wallTermVel) {
		this.wallTermVel = (float) wallTermVel;
	}
	public float getGrav() {
		return grav;
	}
	public void setGrav(double grav) {
		this.grav = (float) grav;
	}
	public float getSink() {
		return sink;
	}
	public void setSink(double sink) {
		this.sink = (float) sink;
	}
	public int getSpawnTime() {
		return spawnTime;
	}
	public void setSpawnTime(int spawnTime) {
		this.spawnTime = spawnTime;
	}
	public int getSpawnInv() {
		return spawnInv;
	}
	public void setSpawnInv(int spawnInv) {
		this.spawnInv = spawnInv;
	}
	public int getShotDelay() {
		return shotType.getReload();
	}
	public ShotModel getShotType() {
		return shotType;
	}
	public void setShotType(ShotModel shotType) {
		this.shotType = shotType;
	}
}
