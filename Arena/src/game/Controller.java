package game;

/**
 * Class to hold the buttons
 * 
 * @author Jacob Charles
 *
 */

public class Controller {
	//mask values
	private static final int B_UP = 1;
	private static final int B_DOWN = 2;
	private static final int B_LEFT = 4;
	private static final int B_RIGHT = 8;
	private static final int B_JUMP = 16;
	private static final int B_FIRE = 32;
	private static final int B_START = 64;
	private static final int B_MAX = 127; //sum of all previous
	
	//buffer (current state of buttons, a bitmask)
	private int buf;
	private char c;
	
	//button hold times
	private int u = 0; //up
	private int d = 0; //down
	private int l = 0; //left
	private int r = 0; //right
	private int j = 0; //jump
	private int f = 0; //fire
	private int s = 0; //start

	/*private boolean B_UP;
	private boolean B_DOWN;
	private boolean B_LEFT;
	private boolean B_RIGHT;
	private boolean B_JUMP;
	private boolean B_FIRE;
	private boolean B_START;*/

	/**
	 * Internal update method, track hold times for buttons
	 */
	public void update() {
		//up
		if (isUp()) setUp(getUp()+1);
		else setUp(0);
		//down
		if (isDown()) setDown(getDown()+1);
		else setDown(0);
		//left
		if (isLeft()) setLeft(getLeft()+1);
		else setLeft(0);
		//right
		if (isRight()) setRight(getRight()+1);
		else setRight(0);
		//jump
		if (isJump()) setJump(getJump()+1);
		else setJump(0);
		//fire
		if (isFire()) setFire(getFire()+1);
		else setFire(0);
		//start
		if (isStart()) setStart(getStart()+1);
		else setStart(0);
	}

	//getter and setter for typed letters
	public char getTyped() {
		return c;
	}
	public void setTyped(char c) {
		this.c = c;
	}
	
	//getters and setters for button durations
	public int getUp() {
		return u;
	}
	private void setUp(int up) {
		this.u = up;
	}
	public int getDown() {
		return d;
	}
	private void setDown(int down) {
		this.d = down;
	}
	public int getLeft() {
		return l;
	}
	private void setLeft(int left) {
		this.l = left;
	}
	public int getRight() {
		return r;
	}
	private void setRight(int right) {
		this.r = right;
	}
	public int getJump() {
		return j;
	}
	private void setJump(int jump) {
		this.j = jump;
	}
	public int getFire() {
		return f;
	}
	private void setFire(int fire) {
		this.f = fire;
	}
	public int getStart() {
		return s;
	}
	private void setStart(int start) {
		this.s = start;
	}

	//getters and setters for buffer fields
	private boolean isUp() {
		return (buf&B_UP) > 0;
	}
	public void setUp(boolean b) {
		if (b) buf |= B_UP;
		else buf &= B_MAX-B_UP;
	}
	private boolean isDown() {
		return (buf&B_DOWN) > 0;
	}
	public void setDown(boolean b) {
		if (b) buf |= B_DOWN;
		else buf &= B_MAX-B_DOWN;
	}
	private boolean isLeft() {
		return (buf&B_LEFT) > 0;
	}
	public void setLeft(boolean b) {
		if (b) buf |= B_LEFT;
		else buf &= B_MAX-B_LEFT;
	}
	private boolean isRight() {
		return (buf&B_RIGHT) > 0;
	}
	public void setRight(boolean b) {
		if (b) buf |= B_RIGHT;
		else buf &= B_MAX-B_RIGHT;
	}
	private boolean isJump() {
		return (buf&B_JUMP) > 0;
	}
	public void setJump(boolean b) {
		if (b) buf |= B_JUMP;
		else buf &= B_MAX-B_JUMP;
	}
	private boolean isFire() {
		return (buf&B_FIRE) > 0;
	}
	public void setFire(boolean b) {
		if (b) buf |= B_FIRE;
		else buf &= B_MAX-B_FIRE;
	}
	private boolean isStart() {
		return (buf&B_START) > 0;
	}
	public void setStart(boolean b) {
		if (b) buf |= B_START;
		else buf &= B_MAX-B_START;
	}
}
