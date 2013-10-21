package game;

/**
 * Server implementation of the GameState, with updating methods
 * 
 * @author Jacob Charles
 */
import java.util.*;

public class ServerGameState extends GameState {

	//a bunch of positional and directional constants
	public static final int LEFT = -1;
	public static final int STOP = 0;
	public static final int RIGHT = 1;
	public static final int TOP = -1;
	public static final int BOTTOM = 1;
	public static final int NONE = 0;

	/**
	 * Apply a player's controls to their character
	 * 
	 * @param p
	 * 		the player associated with the input
	 * @param c
	 * 		the controller data object
	 */
	public void useControls(Player p, Controller c) {
		Actor a = p.getAvatar();

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
		if (c.getJump() == 1 && a.getOnLand() == null) {
			jump(a);
		}
		if (c.getJump() > 1 && a.getAirTime() > 0 && a.getAirTime() <= 5) {
			holdJump(a);
		}

		//shooting
		if (c.getFire() == 1) {
			shoot(a);
		}
	}

	/**
	 * Make an actor jump
	 * 
	 * @param a
	 * 		the actor to make jump
	 */
	public void jump (Actor a) {
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
	public void holdJump (Actor a) {
		if (a.getVy() == 1-a.getJumpPower()) {
			a.setVy(a.getJumpPower());
		}
	}

	/**
	 * Make an actor fall
	 * 
	 * @param a
	 * 		the actor to make fall
	 */
	public void fall (Actor a) {
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
	public void land (Actor a, Land l) {
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
	public void run (Actor a, int dir) {
		//TODO: Air/land differences?
		if (dir > RIGHT) dir = RIGHT;
		if (dir < LEFT) dir = LEFT;

		if (dir == STOP) {
			a.setVx(STOP);
			return; //don't update anything else
		}

		a.setVx(dir*a.getRunSpeed());
		if (dir != 0) {
			a.setDir(dir);
		}
	}

	/**
	 * Make an actor fire their shot
	 * 
	 * @param a
	 * 		the actor doing the shooting
	 */
	public void shoot (Actor a) {
		//can't fire if you haven't reloaded
		if (a.getReload() > 0) {
			return;
		}

		a.setReload(a.getShotDelay());

		//build a new shot, according to the Actor's specifications
		Shot s = new Shot();
		s.setSource(a);
		s.setDead(false);
		s.setX(a.getX()+a.getW()*a.getDir());
		s.setY(a.getY()-a.getH()/2);
		s.setVx(a.getShotSpeed()*a.getDir());
		s.setVy(0);
		s.setH(a.getShotHei());
		s.setW(a.getShotWid());
		s.setLifeTime(a.getShotLife());

		//add the new bullet to the list of bullets
		bullets.add(s);
	}

	/**
	 * Kill the specified actor
	 * 
	 * @param a
	 * 		the actor who dies
	 */
	public void die (Actor a) {
		a.setDeadTime(0);
	}
	
	/**
	 * update the actor's status
	 * 
	 * @param a
	 * 		the actor to update
	 */
	public void update (Actor a) {
		if (a.getAirTime() > 0) a.setAirTime(a.getAirTime()+1); //time in mid-air
		if (a.getAirTime() < 0) a.setAirTime(a.getAirTime()-1); //time on the ground

		if (a.getDeadTime() > 0) a.setDeadTime(a.getDeadTime()+1); //respawn timer, potentially spawn armor

		if (a.getReload() > 0) a.setReload(a.getReload()-1); //timer between shots

		if (a.getOnLand() != null) a.setVy(a.getOnLand().getVy()); //match platform's vertical speed
		
		move(a); //updates positions and speeds
		//TODO: add more as other fields need updating
	}

	/**
	 * update a shot's status
	 * 
	 * @param s
	 * 		the shot to update
	 */
	public void update (Shot s) {
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
	public boolean overlap (GameObject a, GameObject b) {
		if (a.getBottomEdge() >= b.getTopEdge() && a.getTopEdge() <= b.getBottomEdge()) {
			if (a.getRightEdge() >= b.getLeftEdge() && a.getLeftEdge() <= b.getRightEdge()) {
				return true;
			}
		}
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
	public int hCollide (GameObject a, GameObject b) {
		//lined up for horizontal collisions
		if (a.getBottomEdge() >= b.getTopEdge() && a.getTopEdge() <= b.getBottomEdge()) {
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
	public int vCollide (GameObject a, GameObject b) {
		//lined up for vertical collisions
		if (a.getRightEdge() >= b.getLeftEdge() && a.getLeftEdge() <= b.getRightEdge()) {
			//falling
			if (a.getBottomEdge() < b.getTopEdge() && a.getBottomEdge()+a.getVy() >= b.getTopEdge()+b.getVy()) {
				return TOP;
			}
			//rising
			else if (a.getTopEdge() > b.getBottomEdge() && a.getBottomEdge()+a.getVy() >= b.getTopEdge()+b.getVy()) {
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
	public void collide (Actor a, Land l) {
		//non-solid platforms simply return for now
		if (!l.isSolid()) {
			return;
		}
		
		//vertical collisions
		int v = vCollide(a, l);
		if (v == TOP) {
			a.setBottomEdge(l.getTopEdge()-1);
			land(a, l);
		}
		if (v == BOTTOM) {
			a.setTopEdge(l.getBottomEdge()+1);
			a.setVy(STOP);
		}

		//horizontal collisions
		int h = hCollide(a, l);
		if (h == LEFT) {
			a.setRightEdge(l.getLeftEdge()-1);
			a.setVx(STOP);
		}
		if (h == RIGHT) {
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
	public void collide(Actor a, Actor b) {
		//can't hit yourself
		if (a == b) {
			return;
		}

		//land on enemy heads
		if (vCollide(a, b) == TOP) {
			a.setBottomEdge(b.getTopEdge());
			b.setDeadTime(0);
			a.setVy(b.getVy()+a.getJumpPower()/2);
		}

		//TODO: Make interesting side to side collisions
	}

	/**
	 * Check for collisions between a shot and the terrain
	 * 
	 * @param s
	 * 		shot to collide
	 * @param l
	 * 		land to check for collisions with
	 */
	public void collide(Shot s, Land l) {
		//non-solid platforms simply return for now
		if (!l.isSolid()) {
			return;
		}

		//check for any collision
		if (vCollide(s, l) != NONE || hCollide(s, l) != NONE) {
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
	public void collide(Shot s, Actor a) {
		//don't hit your own source
		if (s.getSource() == a) {
			return;
		}

		//check for any collision
		if (vCollide(s, a) != NONE || hCollide(s, a) != NONE) {
			s.setDead(true);
			a.setDeadTime(0);
		}
	}

	/**
	 * Move the actor according to their current speeds (with gravity)
	 * 
	 * @param a
	 * 		the actor to move
	 */
	public void move(Actor a) {
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

		//airborne
		if (a.getAirTime() > 0) {
			a.setY(a.getY()+a.getVy());
			a.setVy(a.getVy()+1); //TODO: determine what exact gravity to use
			if (a.getVy() < -8) a.setVy(-8); //TODO: Make a specific termian velocity
		}

		//falling off edges
		Land l = a.getOnLand();
		if (l != null) {
			if (a.getRightEdge() < l.getLeftEdge() || a.getLeftEdge() > l.getRightEdge()) {
				fall(a);
			}
		}
	}

	/**
	 * Move the shot according to its speeds
	 * 
	 * @param s
	 * 		the shot to be moved
	 */
	public void move(Shot s) {
		for (Land l : getLevel()) {
			collide(s, l);
		}
		for (Actor a : getFighters()) {
			collide(s, a);
		}
		s.setX(s.getX()+s.getVx());
		s.setY(s.getY()+s.getVy());
	}
}
