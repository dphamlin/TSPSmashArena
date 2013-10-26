package game;

public class Warehouse {
	public static final int LIZARDMAN = 0;
	public static final int SLIME = 1;
	private static final int WIDTH = 640, HEIGHT = 480;
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
		r.setAirFrict(.1);
		r.setMaxSpeed(5);

		//fairly standard jump and fall
		r.setJumpPower(10);
		r.setJumpHold(5);
		r.setTermVel(8);

		//standard gravity
		r.setGrav(1);

		//slow-shooting
		r.setShotDelay(50);
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
		s.setLife(25);
		s.setSpeed(12);

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

		//floaty jump, slow fall
		r.setJumpPower(4);
		r.setJumpHold(20);
		r.setTermVel(3);

		//low gravity
		r.setGrav(0.25);

		//slow bullets
		r.setShotDelay(35);
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
		s.setSpeed(.8);

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
		b.getPieces().add(new Land(WIDTH/4, HEIGHT*3/4, WIDTH/2, 48, Land.SOLID));
		b.getPieces().add(new Land(WIDTH/4, HEIGHT*3/4-40, WIDTH/8, 4, Land.PLATFORM));
		b.getPieces().add(new Land(WIDTH*7/16, HEIGHT*3/4-90, WIDTH/8, 24, Land.SOLID));
		b.getPieces().add(new Land(WIDTH*5/8, HEIGHT*3/4-40, WIDTH/8, 4, Land.PLATFORM));
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
