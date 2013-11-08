package game;

import com.google.gson.Gson;

/**
 * Local test driver
 * 
 * @author Jacob Charles
 */
public class TestDriver {

	/**
	 * Local game testing
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		//initialize
		Controller c = new Controller();
		View v = new View();
		ServerGameState gs = new ServerGameState();
		StopWatch t = new StopWatch(20);
		v.attachController(c);

		//test players
		gs.addPlayer();
		//gs.addPlayer();
		//gs.addPlayer();
		//gs.addPlayer();

		//main loop
		while (v.isVisible()) {
			t.loopStart();
			c.update();
			for (int i = 0; i < gs.getNumberOfPlayers(); i++){
				gs.readControls(gs.getPlayer(i), c); //using a hackish version to not create a Participant
			}
			gs.update();
			v.reDraw(gs.convert());
			t.loopRest();
		}
		System.exit(0);
	}
}
