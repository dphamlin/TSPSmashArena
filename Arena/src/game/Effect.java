package game;

public class Effect extends GameObject {
	public static final int SPAWN = 0;
	public static final int FADE = 1;
	public static final int GRAB = 2;
	public static final int DEATH = -1;
	
	private int t;
	private int l;
	
	public Effect(int x, int y) {
		super(x, y);
	}
	
	/*getters and setter*/
	public int getType() {
		return t;
	}
	public void setType(int t) {
		this.t = t;
	}
	public int getLife() {
		return l;
	}
	public void setLife(int l) {
		this.l = l;
	}
	public void decLife() {
		this.l--;
	}
}
