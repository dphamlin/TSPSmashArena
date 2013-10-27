package game;

public class Warehouse {
	//characters
	public static final int LIZARD = 0;
	public static final int SLIME = 1;
	public static final int CAPTAIN = 2;
	public static final int MARINE = 3;
	public static final int ROBOT = 4;
	public static final int SCIENTIST = 5;

	//dimensions for easy level building
	private static final int WIDTH = 640, HEIGHT = 480;

	//actual lists
	private static RoleModel characters[] = 
		{lizardman(), slime(), captain(), spaceMarine(), robot(), madScientist()};
	private static Blueprint maps[] = 
		{holodeck(), alienPlanet(), factory()};

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
	//lizard man
	private static RoleModel lizardman() {
		RoleModel r = new RoleModel();
		r.setSkin(0);

		//standard size
		r.setW(16);
		r.setH(16);

		//agile on ground, sluggish in air
		r.setRunFrict(1);
		r.setAirFrict(.15);
		r.setMaxSpeed(4.8);

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
		s.setSkin(0);

		//wide lasers
		s.setH(8);
		s.setW(40);

		//medium range
		s.setLife(30);
		s.setSpeed(8);

		//standard
		s.setType(0);
		return s;
	}

	//slime alien
	private static RoleModel slime() {
		RoleModel r = new RoleModel();
		r.setSkin(1);

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
		
		//slightly respawn
		r.setSpawnTime(45);
		r.setSpawnInv(50);

		//slow bullets
		r.setShotDelay(34);
		r.setShotType(slimeShot());
		return r;
	}
	private static ShotModel slimeShot() {
		ShotModel s = new ShotModel();
		s.setSkin(1);

		//medium sized bubbles
		s.setH(16);
		s.setW(16);

		//slow-moving, long lasting
		s.setLife(300);
		s.setSpeed(.9);

		//standard
		s.setType(0);
		return s;
	}

	//dashing captain
	private static RoleModel captain() {
		RoleModel r = new RoleModel();
		r.setSkin(2);
		//TODO: fill out stats
		r.setShotType(captainShot());
		return r;
	}
	private static ShotModel captainShot() {
		ShotModel s = new ShotModel();
		s.setSkin(2);
		//TODO: fill out bullet stats
		return s;
	}

	//space marine
	private static RoleModel spaceMarine() {
		RoleModel r = new RoleModel();
		r.setSkin(3);
		//TODO: fill out stats
		r.setShotType(marineShot());
		return r;
	}
	private static ShotModel marineShot() {
		ShotModel s = new ShotModel();
		s.setSkin(3);
		//TODO: fill out bullet stats
		return s;
	}

	//sentai robot
	private static RoleModel robot() {
		RoleModel r = new RoleModel();
		r.setSkin(4);
		//TODO: fill out stats
		r.setShotType(robotShot());
		return r;
	}
	private static ShotModel robotShot() {
		ShotModel s = new ShotModel();
		s.setSkin(4);
		//TODO: fill out bullet stats
		return s;
	}

	//mad scientist
	private static RoleModel madScientist() {
		RoleModel r = new RoleModel();
		r.setSkin(5);
		//TODO: fill out stats
		r.setShotType(scienceShot());
		return r;
	}
	private static ShotModel scienceShot() {
		ShotModel s = new ShotModel();
		s.setSkin(5);
		//TODO: fill out bullet stats
		return s;
	}

	/*private methods to init each map*/

	//level select environment
	private static Blueprint holodeck() {
		Blueprint b = new Blueprint();
		//TODO: build actual map details
		b.add(new Land(WIDTH/4, HEIGHT*3/4, WIDTH/2, 48, Land.SOLID));
		b.add(new Land(WIDTH/4, HEIGHT*3/4-40, WIDTH/8, 4, Land.PLATFORM));
		b.add(new Land(WIDTH*7/16, HEIGHT*3/4-90, WIDTH/8, 24, Land.BOUNCE+Land.SOLID));
		b.add(new Land(WIDTH*5/8, HEIGHT*3/4-40, WIDTH/8, 4, Land.PLATFORM));
		b.add(new Land(20, HEIGHT/2-50, 50, 50, Land.DANGER));
		b.add(new Land(WIDTH-70, HEIGHT/2-50, 50, 50, Land.DANGER));
		return b;
	}
	//lava-and-meteors surface world
	private static Blueprint alienPlanet() {
		Blueprint b = new Blueprint();
		//TODO: build actual map details
		return b;
	}
	//springs and conveyers factory
	private static Blueprint factory() {
		Blueprint b = new Blueprint();
		//TODO: build actual map details
		return b;
	}
}
