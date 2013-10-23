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
		v.attachController(c);
		gs.initTestLevel();
		gs.addPlayer(); //single player
		
		//main loop
		while (c.getStart() == 0) {
			c.update();
			gs.readControls(gs.getPlayer(0), c);
			gs.update();
			v.reDraw(gs.convert());
			//magical try-catch block for sleeping!
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
