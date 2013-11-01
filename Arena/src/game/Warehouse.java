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
	public static final int CHAR_NUM = 5; //number of implemented players

	//bullets
	public static final int FIREBALL = 1;
	public static final int BUBBLE = 2;
	public static final int RAYGUN = 3;
	public static final int BEAMSWORD = 4;
	public static final int BOOMERANG = 5;
	public static final int MORTAR = 6;
	public static final int MISSILE = 7;
	public static final int EXPLOSION = 8;

	//levels
	public static final int HOLODECK = 0;
	public static final int PLANET = 1;
	public static final int FACTORY = 2;
	public static final int DEMO = 3; //will be eventually removed, probably

	//dimensions for easy level building
	private static final int WIDTH = 640, HEIGHT = 480;

	//actual lists
	private static RoleModel characters[] = 
		{noP(), lizardman(), slime(), captain(), spaceMarine(), japaneseRobot(), madScientist()};
	private static ShotModel shots[] =
		{noShot(), fireball(), bubble(), raygun(), mortar(), beamSword(), boomerang(), missile(), explosion()};
	private static Blueprint maps[] = 
		{holodeck(), alienPlanet(), factory(), demo()};

	/**
	 * @return Array of selectable characters
	 */
	public static RoleModel[] getCharacters() {
		return characters;
	}

	/**
	 * @return Array of shot types
	 */
	public static ShotModel[] getShots() {
		return shots;
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

		//meh on air and ground
		r.setRunFrict(.5);
		r.setAirFrict(.25);
		r.setMaxSpeed(3);

		//pretty eh jump
		r.setJumpPower(9);
		r.setJumpHold(5);

		//standard gravity
		r.setGrav(1);
		r.setTermVel(7.5);

		//fall control
		r.setWallTermVel(7);
		r.setSink(1.1);

		//standard respawn
		r.setSpawnTime(1);
		r.setSpawnInv(0);

		//doesn't have a bullet
		r.setShotDelay(0);
		r.setShotType(noShot());
		return r;
	}
	//lizard man
	private static RoleModel lizardman() {
		RoleModel r = new RoleModel();
		r.setSkin(LIZARD);

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

		//fall control
		r.setWallTermVel(5.5);
		r.setSink(1.2);

		//standard respawn
		r.setSpawnTime(50);
		r.setSpawnInv(50);

		//average fire rate
		r.setShotDelay(50);
		r.setShotType(fireball());
		return r;
	}
	//slime alien
	private static RoleModel slime() {
		RoleModel r = new RoleModel();
		r.setSkin(SLIME);

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

		//fall control
		r.setWallTermVel(0.5);
		r.setSink(1.5);

		//slightly faster respawn
		r.setSpawnTime(45);
		r.setSpawnInv(50);

		//slow bullets
		r.setShotDelay(55);
		r.setShotType(bubble());
		return r;
	}
	//dashing captain
	private static RoleModel captain() {
		RoleModel r = new RoleModel();
		r.setSkin(CAPTAIN);

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
		
		//fall control
		r.setWallTermVel(7);
		r.setSink(1.1);

		//average respawn, long spawn armor
		r.setSpawnTime(50);
		r.setSpawnInv(85);

		//quick fire rate
		r.setShotDelay(45);
		r.setShotType(raygun());
		return r;
	}
	//Big chufty marine
	private static RoleModel spaceMarine() {
		RoleModel r = new RoleModel();
		r.setSkin(MARINE);

		//average land speed, a bit weaker in the air
		r.setRunFrict(.35);
		r.setAirFrict(.25);
		r.setMaxSpeed(3);

		//low but very long jump (rockets)
		r.setJumpPower(3);
		r.setJumpHold(45);

		//hard fall
		r.setGrav(0.65);
		r.setTermVel(8.5);

		//fall control
		r.setWallTermVel(8);
		r.setSink(1.1);

		//slow respawn, average armor
		r.setSpawnTime(65);
		r.setSpawnInv(50);

		//slow fire rate
		r.setShotDelay(65);
		r.setShotType(mortar());
		return r;
	}
	//Samurai robot
	private static RoleModel japaneseRobot() {
		RoleModel r = new RoleModel();
		r.setSkin(ROBOT);

		//good speeds, but slippery
		r.setRunFrict(.18);
		r.setAirFrict(.08);
		r.setMaxSpeed(4.6);

		//strong jump low control
		r.setJumpPower(11);
		r.setJumpHold(4);

		//slightly brisk fall
		r.setGrav(.8);
		r.setTermVel(9.5);

		//fall control
		r.setWallTermVel(-2.5);
		r.setSink(1.35);

		//average respawn, slightly short armor
		r.setSpawnTime(50);
		r.setSpawnInv(40);

		//quick fire rate
		r.setShotDelay(40);
		//r.setShotType(beamSword());
		r.setShotType(boomerang());
		return r;
	}
	//mad scientist
	private static RoleModel madScientist() {
		RoleModel r = new RoleModel();
		r.setSkin(SCIENTIST);
		//TODO: fill out stats
		r.setShotType(missile());
		return r;
	}

	/*private methods to init each shot type*/
	//nonexistant shot
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
	//bouncing, mario-type fireballs
	private static ShotModel fireball() {
		ShotModel s = new ShotModel();
		s.setSkin(FIREBALL);

		//small shots
		s.setH(8);
		s.setW(8);

		//decent range, a bit slow
		s.setLife(80);
		s.setSpeed(2.8);
		s.setVSpeed(2.8);

		//bounce across the ground
		s.setType(Shot.GRAV+Shot.BOUNCE);
		s.setVar(30);
		return s;
	}
	//slow, lingering bubbles
	private static ShotModel bubble() {
		ShotModel s = new ShotModel();
		s.setSkin(BUBBLE);

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
	//straight, simple lasers
	private static ShotModel raygun() {
		ShotModel s = new ShotModel();
		s.setSkin(RAYGUN);

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
	//currently just a cannon
	private static ShotModel mortar() {
		ShotModel s = new ShotModel();
		s.setSkin(MORTAR);

		//mid-size shots
		s.setH(10);
		s.setW(10);

		//shoot forward
		s.setLife(100);
		s.setSpeed(5.5);
		s.setVSpeed(-3.5);

		//standard type
		s.setType(Shot.GRAV|Shot.BOMB);
		s.setVar(38);
		return s;
	}
	//mid-range blade
	private static ShotModel beamSword() {
		ShotModel s = new ShotModel();
		s.setSkin(BEAMSWORD);

		//long blades
		s.setH(4);
		s.setW(16);

		//very short range
		s.setLife(8);
		s.setSpeed(8);

		//ignore obstacles and stop quickly
		s.setType(Shot.PHASE+Shot.PIERCE+Shot.ACCEL);
		s.setVar(-1200);
		return s;
	}
	//mid-range blade
	private static ShotModel boomerang() {
		ShotModel s = new ShotModel();
		s.setSkin(BOOMERANG);

		//long blades
		s.setH(4);
		s.setW(16);

		//very short range
		s.setLife(55);
		s.setSpeed(9);

		//go out then reterun
		s.setType(Shot.ACCEL);
		s.setVar(-400);
		return s;
	}
	//acceleration missiles
	private static ShotModel missile() {
		ShotModel s = new ShotModel();
		s.setSkin(MISSILE);

		//mid-size shots
		s.setH(12);
		s.setW(12);

		//shoot slightly backward
		s.setLife(65);
		s.setSpeed(-1.4);

		//accelerate quickly forward and explode on impact
		s.setType(Shot.ACCEL|Shot.BOMB);
		s.setVar(210);
		return s;
	}
	//explosions for various uses
	private static ShotModel explosion() {
		ShotModel s = new ShotModel();
		s.setSkin(EXPLOSION);

		//start small
		s.setH(4);
		s.setW(4);

		//unmoving
		s.setLife(6);
		s.setSpeed(0);

		//ignore walls and players and get bigger
		s.setType(Shot.GROW|Shot.PHASE|Shot.PIERCE);
		s.setVar(4);
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

		//character change chambers
		b.add(-1*16, -1*16, 2*16, 6*16, SOLID);
		b.add(1*16, -1*16, 3*16, 5*16, DANGER|SOLID|CHAR, LIZARD);
		b.add(4*16, -1*16, 4*16, 6*16, SOLID);
		b.add(8*16, -1*16, 3*16, 5*16, DANGER|SOLID|CHAR, SLIME);
		b.add(11*16, -1*16, 4*16, 6*16, SOLID);
		b.add(15*16, -1*16, 3*16, 5*16, DANGER|SOLID|CHAR, CAPTAIN);
		b.add(18*16, -1*16, 4*16, 6*16, SOLID);
		b.add(22*16, -1*16, 3*16, 5*16, DANGER|SOLID|CHAR, MARINE);
		b.add(25*16, -1*16, 4*16, 6*16, SOLID);
		b.add(29*16, -1*16, 3*16, 5*16, DANGER|SOLID|CHAR, ROBOT);
		b.add(32*16, -1*16, 4*16, 6*16, SOLID);
		//b.add(36*16, -1*16, 3*16, 5*16, DANGER|SOLID|CHAR, SCIENTIST); //TODO: Implement the mad scientist
		b.add(39*16, -1*16, 2*16, 6*16, SOLID);

		//top/'attic' ladders
		b.add(1*16, 8*16, 3*16, 16, PLATFORM);
		b.add(8*16, 8*16, 3*16, 16, PLATFORM);
		b.add(15*16, 8*16, 3*16, 16, PLATFORM);
		b.add(22*16, 8*16, 3*16, 16, PLATFORM);
		b.add(29*16, 8*16, 3*16, 16, PLATFORM);
		b.add(36*16, 8*16, 3*16, 16, PLATFORM);

		//top floor
		b.add(-1*16, 11*16, 18*16, 2*16, SOLID);
		b.add(17*16, 11*16, 6*16, 16, PLATFORM);
		b.add(23*16, 11*16, 18*16, 2*16, SOLID);

		//middle/top ladder
		b.add(18*16, 15*16, 4*16, 16, PLATFORM);

		//option change blocks
		b.add(12*16, 14*16, 2*16, 2*16, SOLID|BOUNCE|OPTION|HATCH|SWITCH, 0); //stock
		b.add(9*16, 14*16, 2*16, 2*16, SOLID|BOUNCE|OPTION|HATCH, 1); //stock adjust
		b.add(26*16, 14*16, 2*16, 2*16, SOLID|BOUNCE|OPTION|NHATCH|SWITCH, 0); //time
		b.add(29*16, 14*16, 2*16, 2*16, SOLID|BOUNCE|OPTION|NHATCH, 1); //time adjust

		//middle floor
		b.add(-1*16, 19*16, 7*16, 16, PLATFORM);
		b.add(6*16, 19*16, 28*16, 2*16, SOLID);
		b.add(34*16, 19*16, 7*16, 16, PLATFORM);

		//bottom/middle ladders
		b.add(1*16, 23*16, 4*16, 16, PLATFORM);
		b.add(35*16, 23*16, 4*16, 16, PLATFORM);

		//base floor + warps
		b.add(-1*16, 27*16, 13*16, 4*16, SOLID);
		b.add(12*16, 27*16, 4*16, 1*16, PLATFORM);
		b.add(12*16, 29*16, 4*16, 2*16, SOLID|WARP|DANGER, DEMO); //TODO: Warp to PLANET when it's built
		//b.add(12*16, 29*16, 4*16, 2*16, SOLID|WARP|DANGER, PLANET);
		b.add(16*16, 27*16, 8*16, 4*16, SOLID);
		b.add(24*16, 27*16, 4*16, 1*16, PLATFORM|SOLID); //TODO: Unblock when FACTORY is built
		b.add(24*16, 29*16, 4*16, 2*16, SOLID|WARP|DANGER, FACTORY); 
		b.add(28*16, 27*16, 13*16, 4*16, SOLID);
		//TODO: Add more warps to more levels

		//add spawn points
		b.setSpawn(0, 68+0*WIDTH/4, 9*16+8);
		b.setSpawn(1, 68+1*WIDTH/4, 9*16+8);
		b.setSpawn(2, 68+2*WIDTH/4, 9*16+8);
		b.setSpawn(3, 68+3*WIDTH/4, 9*16+8);

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

		//build various test platforms
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
