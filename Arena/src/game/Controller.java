package game;

/**
 * Class to hold the buttons
 * 
 * @author Jacob Charles
 *
 */
public class Controller {
	private int up = 0;
	private int down = 0;
	private int left = 0;
	private int right = 0;
	private int jump = 0;
	private int fire = 0;
	private int start = 0;
	
	//getters and setters for button durations
	public int getUp() {
		return up;
	}
	public void setUp(int up) {
		this.up = up;
	}
	public int getDown() {
		return down;
	}
	public void setDown(int down) {
		this.down = down;
	}
	public int getLeft() {
		return left;
	}
	public void setLeft(int left) {
		this.left = left;
	}
	public int getRight() {
		return right;
	}
	public void setRight(int right) {
		this.right = right;
	}
	public int getJump() {
		return jump;
	}
	public void setJump(int jump) {
		this.jump = jump;
	}
	public int getFire() {
		return fire;
	}
	public void setFire(int fire) {
		this.fire = fire;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
}
