package game;

/**
 * 
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
		gs.initTestLevel();
		gs.addPlayer(); //single player

		//main loop
		while (v.isVisible() && c.getStart() < 1) {
			t.loopStart();
			c.update();
			gs.readControls(gs.getPlayer(0), c);
			gs.update();
			v.reDraw(gs.convert());
			t.loopRest();
		}
		System.exit(0);
	}
}
