package game;

public class Effect extends GameObject {
	private int t;
	private int l;
	
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
	public void decLif() {
		this.l--;
	}
}
