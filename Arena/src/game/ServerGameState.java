package game;

/**
 * Server implementation of the GameState, with updating methods
 * 
 * @author Jacob Charles
 */
import java.util.*;

public class ServerGameState extends GameState {

	//a bunch of positional and directional constants
	private static final int LEFT = -1;
	private static final int STOP = 0;
	private static final int RIGHT = 1;
	private static final int TOP = -1;
	private static final int BOTTOM = 1;
	private static final int NONE = 0;

	//TODO: add results- ranking, winner, order, game time, etc.
	
	/**
	 * Generic constructor
	 */
	public ServerGameState(){
		super();
	}

	/**
	 * Clone constructor from generic GameState
	 * 
	 * @param g
	 * 		GameState to clone from
	 */
	public ServerGameState(GameState g){
		super(g);
	}

	/**
	 * Convert ServerGameState to a ClientGameState
	 * 
	 * @return ClientGameState version of this object
	 */
	public ClientGameState convert() {
		return new ClientGameState(this);
	}

	/**
	 * Update the entire game state
	 * 
	 * Apply user input first!
	 */
	public void update() {
		//player logic
		for (Actor a : getFighters()) {
			update(a);
		}
		//bullet logic (with removal)
		for(int i = 0; i < getBullets().size(); i++) {
			update(getBullets().get(i));
			//remove dead bullets
			if (getBullets().get(i).isDead()) {
				getBullets().remove(i);
				i--;
			}
		}
		//TODO: Other kinds of update logic here.

		//check for the end of the game
		checkEnd();
		
		//track the number of frames passed
		incrementFrames();
	}

	/**
	 * Apply a participant's controls to their character
	 * 
	 * @param p
	 * 		the participant to update from
	 */
	public void readControls(Participant p) {
		Actor a = p.getPlayer();
		Controller c = p.getController();
		readControls(a, c);
	}

	/**
	 * Apply a player's controls to their character (make private eventually)
	 * 
	 * @param a
	 * 		the actor associated with the input
	 * @param c
	 * 		the controller data
	 */
	public void readControls(Actor a, Controller c) {
		if (a.isDead()) return;

		//running
		if (c.getLeft() > 0) {
			run(a, LEFT);
		}
		else if (c.getRight() > 0) {
			run(a, RIGHT);
		}
		else {
			run(a, STOP);
		}

		//jumping
		if ((c.getJump() == 1 || c.getUp() == 1) && a.getOnLand() != null) {
			jump(a);
		}
		else if (c.getJump() > 0 || c.getUp() > 0) {
			holdJump(a);
		}

		//shooting
		if (c.getFire() == 1) {
			shoot(a);
		}
	}
	
	/**
	 * Update whether the game has ended or not
	 */
	private void checkEnd() {
		//menu has no end
		if (getMode() == MENU) {
			setEnd(false);
		}
		//there is only one (survivor)
		if (getMode() == STOCK) {
			setEnd(getLivingPlayers() < 2);
		}
		//time out
		else if (getMode() == TIME) {
			setEnd(getFrameNumber() > getTime());
		}
	}
	
	/**
	 * @return the number of players still alive
	 */
	private int getLivingPlayers() {
		int n = 0;
		for (Actor a : getFighters()) {
			if (a.getLives() > 0) n++;
		}
		return n;
	}

	/**
	 * Make an actor jump
	 * 
	 * @param a
	 * 		the actor to make jump
	 */
	private void jump (Actor a) {
		a.setAirTime(1);
		a.setOnLand(null);
		a.setVy(-a.getJumpPower());
	}

	/**
	 * Extend the height of an actor's jump
	 * 
	 * @param a
	 * 		the actor to extend the jump of
	 */
	private void holdJump (Actor a) {
		if (a.getAirTime() > 0 && a.getAirTime() <= a.getJumpHold()
				&& a.getVy() <= a.getGrav()-a.getJumpPower()+0.1) {
			a.setVy(-a.getJumpPower());
		}
	}

	/**
	 * Make an actor fall
	 * 
	 * @param a
	 * 		the actor to make fall
	 */
	private void fall (Actor a) {
		a.setAirTime(1);
		a.setOnLand(null);
		a.setVy(STOP);
	}

	/**
	 * Make an actor land on a piece of land
	 * 
	 * @param a
	 * 		the actor to land
	 * @param l
	 * 		the land to land on
	 */
	private void land (Actor a, Land l) {
		a.setVy(STOP);
		a.setAirTime(-1);
		a.setOnLand(l);
	}

	/**
	 * Make an actor run in the specified direction
	 * 
	 * @param a
	 * 		the actor to make run
	 * @param dir
	 * 		LEFT, RIGHT, or STOP, the direction to run
	 */
	private void run (Actor a, int dir) {
		//limit and set direction
		if (dir > RIGHT) dir = RIGHT;
		if (dir < LEFT) dir = LEFT;
		if (dir != 0) {
			a.setDir(dir);
		}

		//in the air
		if (a.getAirTime() > 1) {
			a.setVx(a.getVx()+a.getAirSpeed()*dir);
		}
		//on the ground
		else {
			a.setVx(a.getVx()+a.getRunSpeed()*dir);
		}
	}

	/**
	 * Make an actor fire their shot
	 * 
	 * @param a
	 * 		the actor doing the shooting
	 */
	private void shoot (Actor a) {
		//can't fire if you haven't reloaded
		if (a.getReload() > 0) {
			return;
		}

		//delay until next shot
		a.setReload(a.getShotDelay());

		//build a new shot, according to the Actor's specifications
		Shot s = new Shot(a.getShot(), a);

		//add the new bullet to the list of bullets
		getBullets().add(s);
	}

	/**
	 * The specified actor dies
	 * 
	 * @param a
	 * 		the actor who dies
	 */
	private void die (Actor a) {
		a.setDeadTime(0);
		a.setDead(true);
		if (!isGameOver() && getMode() != MENU) {
			a.loseLife();
		}
	}
	
	/**
	 * An actor kills another
	 * 
	 * @param a
	 * 		the killer
	 * @param b
	 * 		the kill-ee
	 */
	private void kill (Actor a, Actor b) {
		//target dies
		die(b);
		//score a point
		if (!isGameOver() && getMode() != MENU) {
			a.getPoint();
		}
	}

	/**
	 * Respawn specified actor
	 * 
	 * @param a
	 * 		actor to respawn
	 */
	private void respawn (Actor a) {
		//don't respawn if you're out of lives in a stock match
		if (a.getLives() <= 0 && getMode() == STOCK) {
			return;
		}
		a.setDead(false);
		fall(a);
		a.setHCenter(WIDTH/2); //TODO: Make proper spawn points in the level
		a.setTopEdge(50);
	}

	/**
	 * update the actor's status
	 * 
	 * @param a
	 * 		the actor to update
	 */
	private void update (Actor a) {
		if (a.getAirTime() > 0) a.setAirTime(a.getAirTime()+1); //time in mid-air
		if (a.getAirTime() < 0) a.setAirTime(a.getAirTime()-1); //time on the ground

		a.setDeadTime(a.getDeadTime()+1); //respawn timer and spawn invincibility
		if (a.getDeadTime() == a.getSpawnTime() && a.isDead()) respawn(a);

		a.setReload(a.getReload()-1); //timer between shots

		if (!a.isDead()) {
			move(a); //updates positions and speeds
		}
		//TODO: add more as other fields need updating
	}

	/**
	 * update a shot's status
	 * 
	 * @param s
	 * 		the shot to update
	 */
	private void update (Shot s) {
		if (s.getLifeTime() <= 0) {
			s.setDead(true);
			return;
		}
		s.setLifeTime(s.getLifeTime()-1);
		move(s);
	}

	/**
	 * Check if two objects are overlapping
	 * 
	 * @param a
	 * 		the first object
	 * @param b
	 * 		the second object
	 * @return
	 * 		true if they are overlapped
	 */
	private boolean overlap (GameObject a, GameObject b) {
		//check that the edges are pushed through
		if (a.getBottomEdge()+a.getVy() >= b.getTopEdge() && a.getTopEdge() <= b.getBottomEdge()) {
			if (a.getRightEdge() >= b.getLeftEdge() && a.getLeftEdge() <= b.getRightEdge()) {
				return true;
			}
		}
		//no overlap
		return false;
	}

	/**
	 * Check collisions between two objects horizontally
	 * 
	 * @param a
	 * 		the object to collide
	 * @param b
	 * 		the object it collides with
	 * @return
	 * 		LEFT if A is to the left of B
	 * 		RIGHT if A is to the right of B
	 * 		NONE if there is no collision
	 */
	private int hCollide (GameObject a, GameObject b) {
		//lined up for horizontal collisions
		if (a.getBottomEdge()+a.getVy() >= b.getTopEdge()+b.getVy() 
				&& a.getTopEdge()+a.getVy()  <= b.getBottomEdge()+b.getVy()) {
			//moving right
			if (a.getRightEdge() < b.getLeftEdge() && a.getRightEdge()+a.getVx() >= b.getLeftEdge()+b.getVx()) {
				return LEFT;
			}
			//moving left
			else if (a.getLeftEdge() > b.getRightEdge() && a.getLeftEdge()+a.getVx() <= b.getRightEdge()+b.getVx()) {
				return RIGHT;
			}
		}

		//no collisions
		return 0;
	}

	/**
	 * Check collisions between two objects vertically
	 * 
	 * @param a
	 * 		the object to collide
	 * @param b
	 * 		the object it collides with
	 * @return
	 * 		TOP if A is on top of B
	 * 		BOTTOM if A is under B
	 * 		NONE if there is no collision
	 */
	private int vCollide (GameObject a, GameObject b) {
		//lined up for vertical collisions
		if (a.getRightEdge()+a.getVx() >= b.getLeftEdge()+b.getVx()
				&& a.getLeftEdge()+a.getVx() <= b.getRightEdge()+b.getVx()) {
			//falling
			if (a.getBottomEdge() < b.getTopEdge() && a.getBottomEdge()+a.getVy() >= b.getTopEdge()+b.getVy()) {
				return TOP;
			}
			//rising
			else if (a.getTopEdge() > b.getBottomEdge() && a.getTopEdge()+a.getVy() <= b.getBottomEdge()+b.getVy()) {
				return BOTTOM;
			}
		}

		//no collisions
		return 0;
	}

	/**
	 * Check for and handle collision between an actor and the stage 
	 * 
	 * @param a
	 * 		the actor to collide
	 * @param l
	 * 		the land to check for collisions with
	 */
	private void collide (Actor a, Land l) {

		//dead men don't interact
		if (a.isDead()) return;

		//collision values
		int v = vCollide(a, l);
		int h = hCollide(a, l);
		boolean ov = overlap(a, l);

		//die on touching danger
		if (l.isDanger() && (v != NONE || h != NONE || ov)) {
			die(a);
			//TODO: Determine if you should lose points for level deaths
			/*if (getMode() == TIME) {
				a.setScore(a.getScore()-1);
			}*/
		}

		//bouncy blocks
		if (v == TOP && l.isBounce()) {
			a.setBottomEdge(l.getTopEdge()-1);
			a.setVy(-a.getVy());
		}
		//bouncy platforms only have a top
		if (v == BOTTOM && l.isBounce() && !l.isPlatform()) {
			a.setTopEdge(l.getBottomEdge()+1);
			a.setVy(-a.getVy());
		}
		if (h == LEFT && l.isBounce() && !l.isPlatform()) {
			a.setRightEdge(l.getLeftEdge()-1);
			a.setVx(-a.getVx());
		}
		if (h == RIGHT && l.isBounce() && !l.isPlatform()) {
			a.setLeftEdge(l.getRightEdge()+1);
			a.setVx(-a.getVx());
		}
		if (l.isBounce()) return; //bounced off, no other action

		//solid or platform floors
		if (v == TOP && (l.isPlatform() || l.isSolid())) {
			a.setBottomEdge(l.getTopEdge()-1);
			land(a, l);
		}

		//solid walls and ceilings
		if (v == BOTTOM && l.isSolid()) {
			a.setTopEdge(l.getBottomEdge()+1);
			a.setVy(STOP);
		}
		if (h == LEFT && l.isSolid()) {
			a.setRightEdge(l.getLeftEdge()-1);
			a.setVx(STOP);
		}
		if (h == RIGHT && l.isSolid()) {
			a.setLeftEdge(l.getRightEdge()+1);
			a.setVx(STOP);
		}
	}

	/**
	 * Check for actor/actor collisions and head bouncing
	 * 
	 * @param a
	 * 		actor to collide
	 * @param b
	 * 		actor to check collisions with
	 */
	private void collide(Actor a, Actor b) {
		//can't hit yourself, a dead guy, or an invincible guy
		if (a == b || a.isDead() || b.isDead() || b.isArmored()) {
			return;
		}

		//land on enemy heads
		if (vCollide(a, b) == TOP) {
			a.setBottomEdge(b.getTopEdge());
			jump(a);
			kill(a, b);
		}

		//bounce each other back
		if (hCollide(a, b) != NONE) {
			a.setVx(-a.getVx());
			b.setVx(-b.getVx());
		}
	}

	/**
	 * Check for collisions between a shot and the terrain
	 * 
	 * @param s
	 * 		shot to collide
	 * @param l
	 * 		land to check for collisions with
	 */
	private void collide(Shot s, Land l) {
		//non-solid platforms simply return for now
		if (!l.isSolid()) {
			return;
		}

		//check for any collision
		if (overlap(s, l) || vCollide(s, l) != NONE || hCollide(s, l) != NONE) {
			s.setDead(true);
		}
	}

	/**
	 * Check for shot-actor collisions
	 * 
	 * @param s
	 * 		shot to collide
	 * @param a
	 * 		actor to check collisions with
	 */
	private void collide(Shot s, Actor a) {
		//don't hit your own source
		if (s.getSource() == a.getId() || a.isDead() || a.isArmored()) {
			return;
		}

		//check for any collision
		if (overlap(s, a) || vCollide(s, a) != NONE || hCollide(s, a) != NONE) {
			s.setDead(true);
			kill(getPlayer(s.getSource()), a);
		}
	}

	/**
	 * Move the actor according to their current speeds (with gravity)
	 * 
	 * @param a
	 * 		the actor to move
	 */
	private void move(Actor a) {
		//check collisions with the level
		for (Land l : getLevel()) {
			collide(a, l);
		}

		//check for collisions with other actors
		for (Actor b : getFighters()) {
			collide(a, b);
		}

		//move along the ground
		a.setX(a.getX()+a.getVx());
		//apply ground friction (moving ground)
		if (a.getOnLand() != null && a.getOnLand().isMove()) {
			Land l = a.getOnLand();
			a.setVx((a.getVx()-l.getVar())*a.getRunSlip()+l.getVar()); //slip + movement
		}
		//apply ground friction (normal ground)
		else if (a.getOnLand() != null) {
			a.setVx(a.getVx()*a.getRunSlip()); //slide
		}

		//move through the air
		if (a.getAirTime() > 0) {

			//apply air friction and momentum
			a.setVx(a.getVx()*a.getAirSlip()); //slide

			//move vertically
			a.setY(a.getY()+a.getVy());

			//gravity
			a.setVy(a.getVy()+a.getGrav());

			//apply terminal velocity
			if (a.getVy() > a.getTermVel()) a.setVy(a.getTermVel());
		}
		//on the ground, stop vertical motion
		else {
			a.setVy(0);
		}

		//falling off edges
		Land l = a.getOnLand();
		if (l != null) {
			if (a.getRightEdge() < l.getLeftEdge() || a.getLeftEdge() > l.getRightEdge()) {
				fall(a);
			}
		}

		//out-of-bounds wraparound (temp)
		if (a.getBottomEdge() < 0) a.setTopEdge(GameState.HEIGHT); //off top
		if (a.getTopEdge() > GameState.HEIGHT) a.setBottomEdge(0); //off bottom
		if (a.getRightEdge() < 0) a.setLeftEdge(GameState.WIDTH); //off left
		if (a.getLeftEdge() > GameState.WIDTH) a.setRightEdge(0); //off right
	}

	/**
	 * Move the shot according to its speeds
	 * 
	 * @param s
	 * 		the shot to be moved
	 */
	private void move(Shot s) {
		//terrain collisions
		for (Land l : getLevel()) {
			collide(s, l);
		}
		//target collisions
		for (Actor a : getFighters()) {
			collide(s, a);
		}

		//apply velocity (straight-only)
		s.setX(s.getX()+s.getVx());
		s.setY(s.getY()+s.getVy());

		//out of bounds removal
		if (s.getBottomEdge() < -s.getH()*2 || s.getTopEdge() > GameState.HEIGHT+s.getH()*2
				|| s.getRightEdge() < -s.getW()*2 || s.getLeftEdge() > GameState.WIDTH+s.getW()*2) {
			s.setDead(true);
		}
	}
}
