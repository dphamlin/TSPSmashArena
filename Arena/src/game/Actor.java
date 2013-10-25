package game;

/**
 * Class for the player's characters
 * 
 * @author Jacob Charles
 *
 */
public class Actor extends GameObject {

	//current data
	private int at;
	private int dt;
	private int r;
	private int dir = 1;
	private int p;
	private Land ol;
	private int s;
	private int l;

	private int m; //selected RoleModel from the Warehouse
	//eventually replace this with a reference to a "character archetype"
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
	 */
	public Actor (int x, int y) {
		super(x, y, 16, 16);
		dir = 1;
		at = 1;
		ol = null;
		p = 0;
		
		//default stats
		runSpeed = 3;
		jumpPower = 10;
		shotDelay = 16;
		shotSpeed = 16;
		shotLife = 20;
		shotWid = 8;
		shotHei = 8;
	}
	
	/**
	 * Spawn a player with a given archetype and location
	 * 
	 * @param x
	 *		start x
	 * @param y
	 * 		start y
	 * @param model
	 * 		which player the character is using
	 */
	/*public Actor (int x, int y, int model) {
		super(x, y);
		RoleModel rm = Warehouse.getCharacters()[model];
		setModel(model);
		setSkin(rm.getSkin());
		setW(rm.getW());
		setH(rm.getH());
	}*/
	
	/*getters and setters for attributes*/
	public int getAirTime() {
		return at;
	}
	public void setAirTime(int airTime) {
		this.at = airTime;
	}
	public int getDeadTime() {
		return dt;
	}
	public void setDeadTime(int deadTime) {
		this.dt = deadTime;
	}
	public int getReload() {
		return r;
	}
	public void setReload(int reload) {
		this.r = reload;
	}
	public int getDir() {
		return dir;
	}
	public void setDir(int dir) {
		this.dir = dir;
	}
	public int getPowerup() {
		return p;
	}
	public void setEquip(int powerup) {
		this.p = powerup;
	}
	public Land getOnLand() {
		return ol;
	}
	public void setOnLand(Land onLand) {
		this.ol = onLand;
	}
	public int getScore() {
		return s;
	}

	public void setScore(int score) {
		this.s = score;
	}

	public int getLives() {
		return l;
	}

	public void setLives(int lives) {
		this.l = lives;
	}

	//TODO: Make these reference a RoleModel's values instead
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
	//TODO: add and adjust the getters and setters here, to use the RoleModel's other features

	//getter and setter for basic player type
	public int getModel() {
		return m;
	}
	public void setModel(int model) {
		this.m = model;
	}
}
