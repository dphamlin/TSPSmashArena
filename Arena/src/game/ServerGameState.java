package game;

public class ServerGameState extends GameState {
	
	public final int LEFT = -1;
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
	 * 		LEFT or RIGHT, the direction to run
	 */
	public void run (Actor a, int dir) {
		//TODO: Air/land checks?
		if (dir > 1) dir = 1;
		if (dir < -1) dir = -1;
		a.setVx(dir*a.getRunSpeed());
		if (dir != 0) {
			a.setDir(dir);
		}
	}
	
	/**
	 * update all of an actor's time fields
	 * 
	 * @param a
	 * 		the actor to update
	 */
	public void update (Actor a) {
		if (a.getAirTime() > 0) a.setAirTime(a.getAirTime()+1);
		if (a.getAirTime() < 0) a.setAirTime(a.getAirTime()-1);
		
		if (a.getDeadTime() > 0) a.setDeadTime(a.getDeadTime()+1);
		
		if (a.getReload() > 0) a.setReload(a.getReload()-1);
		//TODO: add more as other fields need updating
	}
}
