package game;

public class Warehouse {
	//characters
	public static final int NOP = 0; //no player selected
	public static final int LIZARD = 1;
	public static final int SLIME = 2;
	public static final int CAPTAIN = 3;
	public static final int MARINE = 4;
	public static final int ROBOT = 5;
	public static final int SCIENTIST = 6;
	public static final int CHAR_NUM = 5;

	//levels
	public static final int HOLODECK = 0;
	public static final int PLANET = 1;
	public static final int FACTORY = 2;
	public static final int DEMO = 3; //will be eventually removed

	//dimensions for easy level building
	private static final int WIDTH = 640, HEIGHT = 480;

	//actual lists
	private static RoleModel characters[] = 
		{noP(), lizardman(), slime(), captain(), spaceMarine(), japaneseRobot(), madScientist()};
	private static Blueprint maps[] = 
		{holodeck(), alienPlanet(), factory(), demo()};

	/**
	 * @return Array of selectable characters
	 */
	public static RoleModel[] getCharacters() {
		return characters;
	}

	/**
	 * @return Array of selectable maps
	 */
	public static Blueprint[] getMaps() {
		return maps;
	}

	/*private method to init each character*/
	//pre-selection player
	private static RoleModel noP() {
		RoleModel r = new RoleModel();
		r.setSkin(NOP);

		//standard size
		r.setW(0);
		r.setH(0);

		//meh on air and ground
		r.setRunFrict(0);
		r.setAirFrict(0);
		r.setMaxSpeed(0);

		//pretty eh jump
		r.setJumpPower(0);
		r.setJumpHold(0);

		//standard gravity
		r.setGrav(0);
		r.setTermVel(0);

		//standard respawn
		r.setSpawnTime(0);
		r.setSpawnInv(0);

		//doesn't have a bullet
		r.setShotDelay(0);
		r.setShotType(noShot());
		return r;
	}
	private static ShotModel noShot() {
		ShotModel s = new ShotModel();
		s.setSkin(NOP);

		//invisible
		s.setH(0);
		s.setW(0);

		//no range
		s.setLife(0);
		s.setSpeed(0);

		//who cares what type?
		s.setType(0);
		return s;
	}
	//lizard man
	private static RoleModel lizardman() {
		RoleModel r = new RoleModel();
		r.setSkin(LIZARD);

		//standard size
		r.setW(16);
		r.setH(16);

		//agile on ground, sluggish in air
		r.setRunFrict(.9);
		r.setAirFrict(.045);
		r.setMaxSpeed(4.6);

		//fairly standard jump
		r.setJumpPower(9);
		r.setJumpHold(6);

		//standard gravity
		r.setGrav(.9);
		r.setTermVel(8);

		//standard respawn
		r.setSpawnTime(50);
		r.setSpawnInv(50);

		//average fire rate
		r.setShotDelay(50);
		r.setShotType(lizardShot());
		return r;
	}
	private static ShotModel lizardShot() {
		ShotModel s = new ShotModel();
		s.setSkin(LIZARD);

		//small shots
		s.setH(8);
		s.setW(8);

		//decent range, a bit slow
		s.setLife(80);
		s.setSpeed(2.8);

		//bounce across the ground
		s.setType(Shot.GRAV+Shot.BOUNCE);
		s.setVar(30);
		return s;
	}

	//slime alien
	private static RoleModel slime() {
		RoleModel r = new RoleModel();
		r.setSkin(SLIME);

		//standard size
		r.setW(16);
		r.setH(16);

		//always slippery, equal air and ground
		r.setRunFrict(.2);
		r.setAirFrict(.2);
		r.setMaxSpeed(3);

		//floaty jump
		r.setJumpPower(3.8);
		r.setJumpHold(25);

		//slow fall
		r.setGrav(0.25);
		r.setTermVel(3.5);

		//slightly faster respawn
		r.setSpawnTime(45);
		r.setSpawnInv(50);

		//slow bullets
		r.setShotDelay(55);
		r.setShotType(slimeShot());
		return r;
	}
	private static ShotModel slimeShot() {
		ShotModel s = new ShotModel();
		s.setSkin(SLIME);

		//medium sized bubbles
		s.setH(16);
		s.setW(16);

		//slow-moving, long lasting
		s.setLife(180);
		s.setSpeed(.9);

		//bouncy bubbles slow down
		s.setType(Shot.BOUNCE+Shot.ACCEL);
		s.setVar(-4);
		return s;
	}

	//dashing captain
	private static RoleModel captain() {
		RoleModel r = new RoleModel();
		r.setSkin(CAPTAIN);

		//standard size
		r.setW(16);
		r.setH(16);

		//average land speed, a bit weaker in the air
		r.setRunFrict(.5);
		r.setAirFrict(.25);
		r.setMaxSpeed(3);

		//middling jump
		r.setJumpPower(8);
		r.setJumpHold(5);

		//average fall
		r.setGrav(.9);
		r.setTermVel(7.5);

		//average respawn, long spawn armor
		r.setSpawnTime(50);
		r.setSpawnInv(85);

		//quick fire rate
		r.setShotDelay(45);
		r.setShotType(captainShot());
		return r;
	}
	private static ShotModel captainShot() {
		ShotModel s = new ShotModel();
		s.setSkin(CAPTAIN);

		//small lasers
		s.setH(2);
		s.setW(8);

		//mid speed long range
		s.setLife(100);
		s.setSpeed(10);

		//standard type
		s.setType(0);
		return s;
	}

	//Big chufty marine
	private static RoleModel spaceMarine() {
		RoleModel r = new RoleModel();
		r.setSkin(MARINE);

		//standard size
		r.setW(16);
		r.setH(16);

		//average land speed, a bit weaker in the air
		r.setRunFrict(.35);
		r.setAirFrict(.25);
		r.setMaxSpeed(3);

		//low but very long jump (rockets)
		r.setJumpPower(2.9);
		r.setJumpHold(45);

		//hard fall fall
		r.setGrav(0.8);
		r.setTermVel(9);

		//slow respawn, average armor
		r.setSpawnTime(65);
		r.setSpawnInv(50);

		//slow fire rate
		r.setShotDelay(65);
		r.setShotType(marineShot());
		return r;
	}
	private static ShotModel marineShot() {
		ShotModel s = new ShotModel();
		s.setSkin(MARINE);

		//mid-size shots
		s.setH(10);
		s.setW(10);

		//shoot forward
		s.setLife(80);
		s.setSpeed(4);

		//standard type
		s.setType(0);
		return s;
	}

	//Samurai robot
	private static RoleModel japaneseRobot() {
		RoleModel r = new RoleModel();
		r.setSkin(ROBOT);

		//standard size
		r.setW(16);
		r.setH(16);

		//good speeds, but slippery
		r.setRunFrict(.18);
		r.setAirFrict(.08);
		r.setMaxSpeed(4.6);

		//strong jump low control
		r.setJumpPower(11);
		r.setJumpHold(4);

		//average fall
		r.setGrav(.8);
		r.setTermVel(9.5);

		//average respawn, slightly short armor
		r.setSpawnTime(50);
		r.setSpawnInv(40);

		//quick fire rate
		r.setShotDelay(40);
		r.setShotType(robotShot());
		return r;
	}
	private static ShotModel robotShot() {
		ShotModel s = new ShotModel();
		s.setSkin(ROBOT);

		//long blades
		s.setH(4);
		s.setW(24);

		//very short range
		s.setLife(10);
		s.setSpeed(5);

		//ignore obstacles and stop quickly
		s.setType(Shot.PHASE+Shot.PIERCE+Shot.ACCEL);
		s.setVar(-500);
		return s;
	}

	//mad scientist
	private static RoleModel madScientist() {
		RoleModel r = new RoleModel();
		r.setSkin(SCIENTIST);
		//TODO: fill out stats
		r.setShotType(scienceShot());
		return r;
	}
	private static ShotModel scienceShot() {
		ShotModel s = new ShotModel();
		s.setSkin(SCIENTIST);

		//mid-size shots
		s.setH(12);
		s.setW(12);

		//shoot slightly backward
		s.setLife(65);
		s.setSpeed(-1.4);

		//accelerate quickly forward
		s.setType(Shot.ACCEL);
		s.setVar(210);
		return s;
	}

	/*private methods to init each map*/

	/**
	 * TODO: Make some levels, baby!
	 * 
	 * How to make a level:
	 * 
	 * b is the map itself. Call b.setId, and tell it its position in the array of maps.
	 * 
	 * Call b.setName and give it some name. These may be used internally.
	 * 
	 * Call b.add to add a terrain object. First specify the x and y of the upper left corner,
	 * then give it a width and height (positive values), then create a bitmask of traits it has.
	 * 
	 * To create a bitmask, simply sum(+) or OR(|) together some of the flags below as one argument.
	 * 
	 * To specify a Var (used by some flags), simply add one more argument afterward with the value for Var.
	 * 
	 * Lastly, use b.setSpawn(i, x, y) to specify 4 spawn points on the level (0-3). i tells which point you're setting.
	 * 
	 * Eventually, bg, bgm, and skins will matter, but not yet.
	 * Remember: The player wraps around the level, but only when he is completely offscreen.
	 * The level is 640 x 480, so extend around ~32 pixels beyond that to make sure.
	 */

	public static final int SOLID = 1; //solid all around (includes top)
	public static final int PLATFORM = 2; //solid top
	public static final int DANGER = 4; //kill on contact
	public static final int BOUNCE = 8; //bouncy (var = bounciness mod, 0 = 100%, 5 = 150%, -2 = 80%, etc.)
	public static final int SLIP = 16; //slippery
	public static final int MOVE = 32; //(var = vx * 10. 10 is 1 px/fr right, -20 is 2 px/fr left, etc.)
	public static final int HATCH = 64; //appears when control is on
	public static final int NHATCH = 128; //appears when controls is off
	public static final int SWITCH = 256; //toggles control when hit
	public static final int WARP = 512; //go to a new level (var = level to change to)
	public static final int CHAR = 1024; //change characters (var = character to become)
	public static final int OPTION = 2048; //edit in game options  (var = various special things) (WIP)
	public static final int LEAVE = 4096; //disconnect from server? (UNIMPLEMENTED)

	//level select / menu world
	private static Blueprint holodeck() {
		Blueprint b = new Blueprint();
		b.setId(HOLODECK);
		b.setName("Level select");

		//build actual map
		b.add(-32, HEIGHT-20, WIDTH+64, 30, SOLID); //ground floor
		//TODO: Add level, character, and game mode selects

		//TODO: add spawn points
		b.setSpawn(0, WIDTH/5, HEIGHT-30);
		b.setSpawn(1, WIDTH*2/5, HEIGHT-30);
		b.setSpawn(2, WIDTH*3/5, HEIGHT-30);
		b.setSpawn(3, WIDTH*4/5, HEIGHT-30);

		return b;
	}
	//lava and sand surface world
	private static Blueprint alienPlanet() {
		Blueprint b = new Blueprint();
		b.setId(PLANET);
		b.setName("Alien Planet: Surface");

		//TODO: build actual map

		//TODO: add spawn points

		return b;
	}
	//springs and conveyers machine world
	private static Blueprint factory() {
		Blueprint b = new Blueprint();
		b.setId(FACTORY);
		b.setName("Factory");

		//TODO: build actual map

		//TODO: add spawn points

		return b;
	}
	//test environment
	private static Blueprint demo() {
		Blueprint b = new Blueprint();
		b.setId(DEMO);
		b.setName("Test level");

		//build actual map
		b.add(WIDTH/4, HEIGHT*3/4, WIDTH/2, 48, SOLID);
		b.add(WIDTH/4, HEIGHT*3/4-40, WIDTH/8, 4, PLATFORM|MOVE|HATCH, 18);
		b.add(WIDTH*7/16, HEIGHT*3/4-90, WIDTH/8, 24, BOUNCE|SOLID, 2);
		b.add(WIDTH*5/8, HEIGHT*3/4-40, WIDTH/8, 4, PLATFORM|SLIP|NHATCH);
		b.add(20, HEIGHT/2-50, 50, 50, SOLID|DANGER);
		b.add(WIDTH-70, HEIGHT/2-50, 50, 50, SOLID|DANGER);

		b.add(WIDTH/4+10, HEIGHT*3/4-160, 25, 25, SOLID|HATCH|SWITCH|BOUNCE);
		b.add(WIDTH*3/4-35, HEIGHT*3/4-160, 25, 25, SOLID|NHATCH|SWITCH|BOUNCE);

		//add spawn points
		b.setSpawn(0, 150, 60);
		b.setSpawn(1, 200, 50);
		b.setSpawn(2, 250, 50);
		b.setSpawn(3, 300, 60);

		return b;
	}
}
