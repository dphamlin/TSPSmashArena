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
				a.setAirTime(1);
				a.setOnLand(null);
			}
		}
	}
}
