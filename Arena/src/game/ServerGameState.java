package game;

/**
 * Server implementation of the GameState, with updating methods
 * 
 * @author Jacob Charles
 */
import java.util.*;

public class ServerGameState extends GameState {

	public static final int LEFT = -1;
	public static final int STOP = 0;
	public static final int RIGHT = 1;

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
		if (c.getJump() > 1 && c.getJump() <= 5) {
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

		//lined up for vertical collisions
		if (a.getRightEdge() >= l.getLeftEdge() && a.getLeftEdge() <= l.getRightEdge()) {
			//falling
			if (a.getBottomEdge() < l.getTopEdge() && a.getBottomEdge()+a.getVy() >= l.getTopEdge()+l.getVy()) {
				a.setBottomEdge(l.getTopEdge()-1);
				land(a, l);
			}
			//rising
			else if (a.getTopEdge() > l.getBottomEdge() && a.getBottomEdge()+a.getVy() >= l.getTopEdge()+l.getVy()) {
				a.setTopEdge(l.getBottomEdge()+1);
				a.setVy(STOP);
			}
		}

		//lined up for horizontal collisions
		if (a.getBottomEdge() >= l.getTopEdge() && a.getTopEdge() <= l.getBottomEdge()) {
			//moving right
			if (a.getRightEdge() < l.getLeftEdge() && a.getRightEdge()+a.getVx() >= l.getLeftEdge()+l.getVx()) {
				a.setRightEdge(l.getLeftEdge()-1);
				a.setVx(STOP);
			}
			//moving left
			else if (a.getLeftEdge() > l.getRightEdge() && a.getLeftEdge()+a.getVx() <= l.getRightEdge()+l.getVx()) {
				a.setLeftEdge(l.getRightEdge()+1);
				a.setVx(STOP);
			}
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

		//lined up for vertical collisions
		if (a.getRightEdge() >= b.getLeftEdge()&& a.getLeftEdge() <= b.getRightEdge()) {
			//land on the enemy's head
			if (a.getBottomEdge() < a.getTopEdge() && b.getBottomEdge()+b.getVy() >= a.getTopEdge()+a.getVy()) {
				a.setBottomEdge(b.getTopEdge());
				b.setDeadTime(0);
				a.setVy(b.getVy()+a.getJumpPower()/2);
			}
		}

		//TODO: Make these do MUCH more interesting things?
		/*//lined up for horizontal collisions
		if (a.getBottomEdge() >= b.getTopEdge() && a.getTopEdge() <= b.getBottomEdge()) {
			//moving right
			if (a.getRightEdge() < b.getLeftEdge() && a.getRightEdge()+a.getVx() >= b.getLeftEdge()+b.getVx()) {
				int t = a.getRightEdge();
				a.setRightEdge(b.getLeftEdge());
				b.setLeftEdge(t);
			}
			//moving left
			else if (a.getLeftEdge() > b.getRightEdge() && a.getLeftEdge()+a.getVx() <= b.getRightEdge()+b.getVx()) {
				int t = b.getRightEdge();
				b.setRightEdge(a.getLeftEdge());
				a.setLeftEdge(t);
			}
		}*/
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

		//lined up for vertical collisions
		if (s.getRightEdge() >= l.getLeftEdge()&& s.getLeftEdge() <= l.getRightEdge()) {
			//falling
			if (s.getBottomEdge() < l.getTopEdge() && s.getBottomEdge()+s.getVy() >= l.getTopEdge()+l.getVy()) {
				s.setDead(true);
			}
			//rising
			if (s.getTopEdge() > l.getBottomEdge() && s.getBottomEdge()+s.getVy() >= l.getTopEdge()+l.getVy()) {
				s.setDead(true);
			}
		}

		//lined up for horizontal collisions
		if (s.getBottomEdge() >= l.getTopEdge() && s.getTopEdge() <= l.getBottomEdge()) {
			//moving right
			if (s.getRightEdge() < l.getLeftEdge() && s.getRightEdge()+s.getVx() >= l.getLeftEdge()+l.getVx()) {
				s.setDead(true);
			}
			//moving left
			else if (s.getLeftEdge() > l.getRightEdge() && s.getLeftEdge()+s.getVx() <= l.getRightEdge()+l.getVx()) {
				s.setDead(true);
			}
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

		//lined up for vertical collisions
		if (s.getRightEdge() >= a.getLeftEdge()&& s.getLeftEdge() <= a.getRightEdge()) {
			//falling
			if (s.getBottomEdge() < a.getTopEdge() && s.getBottomEdge()+s.getVy() >= a.getTopEdge()+a.getVy()) {
				s.setDead(true);
				a.setDeadTime(0);
			}
			//rising
			if (s.getTopEdge() > a.getBottomEdge() && s.getBottomEdge()+s.getVy() >= a.getTopEdge()+a.getVy()) {
				s.setDead(true);
				a.setDeadTime(0);
			}
		}

		//lined up for horizontal collisions
		if (s.getBottomEdge() >= a.getTopEdge() && s.getTopEdge() <= a.getBottomEdge()) {
			//moving right
			if (s.getRightEdge() < a.getLeftEdge() && s.getRightEdge()+s.getVx() >= a.getLeftEdge()+a.getVx()) {
				s.setDead(true);
				a.setDeadTime(0);
			}
			//moving left
			else if (s.getLeftEdge() > a.getRightEdge() && s.getLeftEdge()+s.getVx() <= a.getRightEdge()+a.getVx()) {
				s.setDead(true);
				a.setDeadTime(0);
			}
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
