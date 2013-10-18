package game;

/**
 * Server implementation of the GameState, with updating methods
 * 
 * @author Jacob Charles
 */
import java.util.*;

public class ServerGameState extends GameState {

	public final int LEFT = -1;
	public final int STOP = 0;
	public final int RIGHT = 1;

	/**
	 * Make an actor jump
	 * 
	 * @param a
	 * 		the actor to make jump
	 */
	public void jump (Actor a) {
		//TODO: Add checks if the jump can be done
		a.setAirTime(1);
		a.setOnLand(null);
		a.setVy(-a.getJumpPower());
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
		//TODO: Air/land checks?
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
			if (a.getVy()-l.getVy() > 0 && a.getBottomEdge() >= l.getTopEdge()) {
				a.setBottomEdge(l.getTopEdge()-1);
				a.setVy(STOP);
				a.setAirTime(-1);
				a.setOnLand(l);
			}
			//moving up
			else if (a.getVy()-l.getVy() < 0 && a.getTopEdge() <= l.getBottomEdge()) {
				a.setTopEdge(l.getBottomEdge()+1);
				a.setVy(STOP);
			}
		}

		//horizontal cases
		if (a.getBottomEdge() >= l.getTopEdge() && a.getTopEdge() <= l.getBottomEdge()) {
			//moving right
			if (a.getVx()-l.getVx() > 0 && a.getRightEdge() >= l.getLeftEdge()) {
				a.setRightEdge(l.getLeftEdge()-1);
				a.setVx(STOP);
			}
			//moving left
			else if (a.getVx()-l.getVx() < 0 && a.getLeftEdge() <= l.getRightEdge()) {
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
		//TODO: loop through terrain and apply collisions
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
