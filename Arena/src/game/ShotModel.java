package game;

/**
 * Template to produce bullets
 * 
 * @author Jacob Charles
 */
public class ShotModel {
	private int skin; //shot appearance
	private float speed; //shot speed
	private float vspeed = 0; //vertical speed
	private int life; //shot duration
	private int w, h; //shot size
	private int type; //shot specifics
	private int var; //extra variable
	
	//getters and setters
	public int getSkin() {
		return skin;
	}
	public void setSkin(int skin) {
		this.skin = skin;
	}
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = (float) speed;
	}
	public float getVSpeed() {
		return vspeed;
	}
	public void setVSpeed(double speed) {
		this.vspeed = (float) speed;
	}
	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getVar() {
		return var;
	}
	public void setVar(int var) {
		this.var = var;
	}
}
