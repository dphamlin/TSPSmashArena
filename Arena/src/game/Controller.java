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
	
	//button hold times
	private int u = 0;
	private int d = 0;
	private int l = 0;
	private int r = 0;
	private int j = 0;
	private int f = 0;
	private int s = 0;

	/*private boolean B_UP;
	private boolean B_DOWN;
	private boolean B_LEFT;
	private boolean B_RIGHT;
	private boolean B_JUMP;
	private boolean B_FIRE;
	private boolean B_START;*/

	/**
	 * Internal update method, track hold times for buttons
	 * TODO: Find an appropriate location for this method
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

	//getters and setters for button durations
	public int getUp() {
		return u;
	}
	public void setUp(int up) {
		this.u = up;
	}
	public int getDown() {
		return d;
	}
	public void setDown(int down) {
		this.d = down;
	}
	public int getLeft() {
		return l;
	}
	public void setLeft(int left) {
		this.l = left;
	}
	public int getRight() {
		return r;
	}
	public void setRight(int right) {
		this.r = right;
	}
	public int getJump() {
		return j;
	}
	public void setJump(int jump) {
		this.j = jump;
	}
	public int getFire() {
		return f;
	}
	public void setFire(int fire) {
		this.f = fire;
	}
	public int getStart() {
		return s;
	}
	public void setStart(int start) {
		this.s = start;
	}

	//getters and setters for buffer fields
	public boolean isUp() {
		return (buf&B_UP) > 0;
	}
	public void setUp(boolean b) {
		if (b) buf |= B_UP;
		else buf &= B_MAX-B_UP;
	}
	public boolean isDown() {
		return (buf&B_DOWN) > 0;
	}
	public void setDown(boolean b) {
		if (b) buf |= B_DOWN;
		else buf &= B_MAX-B_DOWN;
	}
	public boolean isLeft() {
		return (buf&B_LEFT) > 0;
	}
	public void setLeft(boolean b) {
		if (b) buf |= B_LEFT;
		else buf &= B_MAX-B_LEFT;
	}
	public boolean isRight() {
		return (buf&B_RIGHT) > 0;
	}
	public void setRight(boolean b) {
		if (b) buf |= B_RIGHT;
		else buf &= B_MAX-B_RIGHT;
	}
	public boolean isJump() {
		return (buf&B_JUMP) > 0;
	}
	public void setJump(boolean b) {
		if (b) buf |= B_JUMP;
		else buf &= B_MAX-B_JUMP;
	}
	public boolean isFire() {
		return (buf&B_FIRE) > 0;
	}
	public void setFire(boolean b) {
		if (b) buf |= B_FIRE;
		else buf &= B_MAX-B_FIRE;
	}
	public boolean isStart() {
		return (buf&B_START) > 0;
	}
	public void setStart(boolean b) {
		if (b) buf |= B_START;
		else buf &= B_MAX-B_START;
	}
}
