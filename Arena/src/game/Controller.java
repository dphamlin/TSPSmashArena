package game;

/**
 * Class to hold the buttons
 * 
 * @author Jacob Charles
 *
 */
public class Controller {
	//mask values
	private static final int bUp = 1;
	private static final int bDown = 2;
	private static final int bLeft = 4;
	private static final int bRight = 8;
	private static final int bJump = 16;
	private static final int bFire = 32;
	private static final int bStart = 64;
	private static final int bMax = 127; //sum of all previous
	
	//buffer (current state of buttons, a bitmask)
	private int buf;
	
	//button hold times
	private int up = 0;
	private int down = 0;
	private int left = 0;
	private int right = 0;
	private int jump = 0;
	private int fire = 0;
	private int start = 0;

	/*private boolean bUp;
	private boolean bDown;
	private boolean bLeft;
	private boolean bRight;
	private boolean bJump;
	private boolean bFire;
	private boolean bStart;*/

	/**
	 * Internal update method, track hold times for buttons
	 * TODO: Find an appropriate location for this method
	 */
	public void update() {
		//up
		if (isbUp()) setUp(getUp()+1);
		else setUp(0);
		//down
		if (isbDown()) setDown(getDown()+1);
		else setDown(0);
		//left
		if (isbLeft()) setLeft(getLeft()+1);
		else setLeft(0);
		//right
		if (isbRight()) setRight(getRight()+1);
		else setRight(0);
		//jump
		if (isbJump()) setJump(getJump()+1);
		else setJump(0);
		//fire
		if (isbFire()) setFire(getFire()+1);
		else setFire(0);
		//start
		if (isbStart()) setStart(getStart()+1);
		else setStart(0);
	}

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

	//getters and setters for buffer fields
	public boolean isbUp() {
		return (buf&bUp) > 0;
	}
	public void setbUp(boolean b) {
		if (b) buf |= bUp;
		else buf &= bMax-bUp;
	}
	public boolean isbDown() {
		return (buf&bDown) > 0;
	}
	public void setbDown(boolean b) {
		if (b) buf |= bDown;
		else buf &= bMax-bDown;
	}
	public boolean isbLeft() {
		return (buf&bLeft) > 0;
	}
	public void setbLeft(boolean b) {
		if (b) buf |= bLeft;
		else buf &= bMax-bLeft;
	}
	public boolean isbRight() {
		return (buf&bRight) > 0;
	}
	public void setbRight(boolean b) {
		if (b) buf |= bRight;
		else buf &= bMax-bRight;
	}
	public boolean isbJump() {
		return (buf&bJump) > 0;
	}
	public void setbJump(boolean b) {
		if (b) buf |= bJump;
		else buf &= bMax-bJump;
	}
	public boolean isbFire() {
		return (buf&bFire) > 0;
	}
	public void setbFire(boolean b) {
		if (b) buf |= bFire;
		else buf &= bMax-bFire;
	}
	public boolean isbStart() {
		return (buf&bStart) > 0;
	}
	public void setbStart(boolean b) {
		if (b) buf |= bStart;
		else buf &= bMax-bStart;
	}
}
