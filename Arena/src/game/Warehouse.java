package game;

public class Warehouse {
	public static final int LIZARDMAN = 0;
	public static final int SLIME = 1;
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

		//fast on the ground
		r.setRunSpeed(6);
		r.setRunMomentum(0);

		//sluggish in the air
		r.setAirSpeed(1);
		r.setAirMomentum(80);

		//fairly standard jump and fall
		r.setJumpPower(10);
		r.setJumpHold(5);
		r.setTermVel(8);

		//standard gravity
		r.setGravNum(1);
		r.setGravDen(1);

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

		//fast on the ground
		r.setRunSpeed(2);
		r.setRunMomentum(60);
		r.setAirSpeed(2);
		r.setAirMomentum(60);

		//fairly standard jump and fall
		r.setJumpPower(6);
		r.setJumpHold(10);
		r.setTermVel(4);

		//standard gravity
		r.setGravNum(1);
		r.setGravDen(3);

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
		s.setSpeed(1);

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
