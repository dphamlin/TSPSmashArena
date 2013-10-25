package game;

/**
 * Template to produce bullets
 * 
 * @author Jacob Charles
 */
public class ShotModel {
	private int skin; //shot appearance
	private int speed, life; //shot speed and duration
	private int w, h; //shot size
	private int type; //shot specifics
	
	//getters and setters
	public int getSkin() {
		return skin;
	}
	public void setSkin(int skin) {
		this.skin = skin;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
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
}
