package game;

public class Warehouse {
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
		//TODO: fill out stats
		r.setShotType(lizardShot());
		return r;
	}
	private static ShotModel lizardShot() {
		ShotModel s = new ShotModel();
		//TODO: fill out bullet stats
		return s;
	}
	
	//liquid alien
	private static RoleModel slime() {
		RoleModel r = new RoleModel();
		//TODO: fill out stats
		r.setShotType(slimeShot());
		return r;
	}
	private static ShotModel slimeShot() {
		ShotModel s = new ShotModel();
		//TODO: fill out bullet stats
		return s;
	}
	
	//dashing captain
	private static RoleModel captain() {
		RoleModel r = new RoleModel();
		//TODO: fill out stats
		r.setShotType(captainShot());
		return r;
	}
	private static ShotModel captainShot() {
		ShotModel s = new ShotModel();
		//TODO: fill out bullet stats
		return s;
	}
	
	//space marine
	private static RoleModel spaceMarine() {
		RoleModel r = new RoleModel();
		//TODO: fill out stats
		r.setShotType(marineShot());
		return r;
	}
	private static ShotModel marineShot() {
		ShotModel s = new ShotModel();
		//TODO: fill out bullet stats
		return s;
	}
	
	//sentai robot
	private static RoleModel robot() {
		RoleModel r = new RoleModel();
		//TODO: fill out stats
		r.setShotType(robotShot());
		return r;
	}
	private static ShotModel robotShot() {
		ShotModel s = new ShotModel();
		//TODO: fill out bullet stats
		return s;
	}
	
	//mad scientist
	private static RoleModel madScientist() {
		RoleModel r = new RoleModel();
		//TODO: fill out stats
		r.setShotType(scienceShot());
		return r;
	}
	private static ShotModel scienceShot() {
		ShotModel s = new ShotModel();
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
