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
		gs.setLevel(Warehouse.DEMO); //temporary level
		gs.setMode(GameState.STOCK);
		//gs.setMode(GameState.MENU); //temporary game mode

		//test players
		//gs.addPlayer(Warehouse.LIZARD);
		//gs.addPlayer(Warehouse.SLIME);
		//gs.addPlayer(Warehouse.CAPTAIN);
		//gs.addPlayer(Warehouse.MARINE);
		//gs.addPlayer(Warehouse.ROBOT);
		gs.addPlayer(Warehouse.NOP);

		//state examination
		System.out.println("Start state:");
		System.out.println(new Gson().toJson(gs.convert()));

		//main loop
		while (v.isVisible()) {
			t.loopStart();
			c.update();
			/*//powerup testing
			if (!gs.isControl() && !gs.getPlayer(0).isDead()) {
				gs.getPlayer(0).setPowerup(Item.SPEED);
			}
			else if (!gs.getPlayer(0).isDead()) {
				gs.getPlayer(0).setPowerup(0);
			}
			//*/
			for (int i = 0; i < gs.getNumberOfPlayers(); i++){
				gs.readControls(gs.getPlayer(i), c); //using a hackish version to not create a Participant
			}
			gs.update();
			v.reDraw(gs.convert());
			t.loopRest();
		}
		
		//more state examination
		System.out.println("End state:");
		System.out.println(new Gson().toJson(gs.convert()));
		
		//close
		System.exit(0);
	}
}
