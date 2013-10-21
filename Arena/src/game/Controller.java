package game;

/**
 * Class to hold the buttons
 * 
 * @author Jacob Charles
 *
 */
public class Controller {
	//button hold times
	private int up = 0;
	private int down = 0;
	private int left = 0;
	private int right = 0;
	private int jump = 0;
	private int fire = 0;
	private int start = 0;

	//buffer (current state)
	private boolean bUp;
	private boolean bDown;
	private boolean bLeft;
	private boolean bRight;
	private boolean bJump;
	private boolean bFire;
	private boolean bStart;

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
		return bUp;
	}
	public void setbUp(boolean bUp) {
		this.bUp = bUp;
	}
	public boolean isbDown() {
		return bDown;
	}
	public void setbDown(boolean bDown) {
		this.bDown = bDown;
	}
	public boolean isbLeft() {
		return bLeft;
	}
	public void setbLeft(boolean bLeft) {
		this.bLeft = bLeft;
	}
	public boolean isbRight() {
		return bRight;
	}
	public void setbRight(boolean bRight) {
		this.bRight = bRight;
	}
	public boolean isbJump() {
		return bJump;
	}
	public void setbJump(boolean bJump) {
		this.bJump = bJump;
	}
	public boolean isbFire() {
		return bFire;
	}
	public void setbFire(boolean bFire) {
		this.bFire = bFire;
	}
	public boolean isbStart() {
		return bStart;
	}
	public void setbStart(boolean bStart) {
		this.bStart = bStart;
	}
}
