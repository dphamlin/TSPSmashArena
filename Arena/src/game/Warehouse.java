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

	//levels
	public static final int HOLODECK = 0;
	public static final int PLANET = 1;
	public static final int FACTORY = 2;
	public static final int DEMO = 3; //will be eventually removed

	//dimensions for easy level building
	private static final int WIDTH = 640, HEIGHT = 480;

	//actual lists
	private static RoleModel characters[] = 
		{noP(), lizardman(), slime(), captain(), spaceMarine(), robot(), madScientist()};
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
		r.setW(16);
		r.setH(16);

		//meh on air and ground
		r.setRunFrict(.5);
		r.setAirFrict(.5);
		r.setMaxSpeed(3);

		//pretty eh jump
		r.setJumpPower(8);
		r.setJumpHold(5);

		//standard gravity
		r.setGrav(.9);
		r.setTermVel(8);

		//standard respawn
		r.setSpawnTime(50);
		r.setSpawnInv(50);

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

		//slow-shooting
		r.setShotDelay(48);
		r.setShotType(lizardShot());
		return r;
	}
	private static ShotModel lizardShot() {
		ShotModel s = new ShotModel();
		s.setSkin(LIZARD);

		//wide lasers
		s.setH(8);
		s.setW(40);

		//medium range
		s.setLife(30);
		s.setSpeed(8);

		//standard type
		s.setType(0);
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
		r.setShotDelay(34);
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
		s.setLife(300);
		s.setSpeed(.9);

		//standard type
		s.setType(0);
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
		r.setShotDelay(25);
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

	//space marine
	private static RoleModel spaceMarine() {
		RoleModel r = new RoleModel();
		r.setSkin(MARINE);
		//TODO: fill out stats
		r.setShotType(marineShot());
		return r;
	}
	private static ShotModel marineShot() {
		ShotModel s = new ShotModel();
		s.setSkin(MARINE);
		//TODO: fill out bullet stats
		return s;
	}

	//sentai robot
	private static RoleModel robot() {
		RoleModel r = new RoleModel();
		r.setSkin(ROBOT);
		//TODO: fill out stats
		r.setShotType(robotShot());
		return r;
	}
	private static ShotModel robotShot() {
		ShotModel s = new ShotModel();
		s.setSkin(ROBOT);
		//TODO: fill out bullet stats
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
		//TODO: fill out bullet stats
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
	 */
	
	public static final int SOLID = 1; //solid all around (includes top)
	public static final int PLATFORM = 2; //solid top
	public static final int DANGER = 4; //kill on contact
	public static final int BOUNCE = 8; //bouncy (var = multiplier for bounciness * 10)
	public static final int SLIP = 16; //slippery
	public static final int MOVE = 32; //(var = vx * 10. 10 is 1 px/s right, -20 is 2 px/s left, etc.) 
	public static final int WARP = 64; //go to a new level (var = level to change to)
	public static final int CHAR = 128; //change characters (var = character to become)
	public static final int OPTION = 256; //edit in game options  (var = various special things) (WIP)
	public static final int LEAVE = 512; //disconnect from server? (UNIMPLEMENTED)
	
	//level select / menu world
	private static Blueprint holodeck() {
		Blueprint b = new Blueprint();
		b.setId(HOLODECK);
		b.setName("Level select");

		//TODO: build actual map

		//TODO: add spawn points

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
		b.add(WIDTH/4, HEIGHT*3/4-40, WIDTH/8, 4, PLATFORM|MOVE, 18);
		b.add(WIDTH*7/16, HEIGHT*3/4-90, WIDTH/8, 24, BOUNCE|SOLID, 12);
		b.add(WIDTH*5/8, HEIGHT*3/4-40, WIDTH/8, 4, PLATFORM|SLIP);
		b.add(20, HEIGHT/2-50, 50, 50, SOLID|DANGER);
		b.add(WIDTH-70, HEIGHT/2-50, 50, 50, SOLID|DANGER);

		//add spawn points
		b.setSpawn(0, 150, 60);
		b.setSpawn(1, 200, 50);
		b.setSpawn(2, 250, 50);
		b.setSpawn(3, 300, 60);

		return b;
	}
}
