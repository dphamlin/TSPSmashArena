package game;

public class Warehouse {
	//characters
	public static final int CAPTAIN = 0;
	public static final int LIZARD = 1;
	public static final int SLIME = 2;
	public static final int MARINE = 3;
	public static final int ROBOT = 4;
	public static final int CHAR_NUM = 5; //number of implemented players
	public static final int SCIENTIST = 5;

	//bullets
	public static final int RAYGUN = 0;
	public static final int FIREBALL = 1;
	public static final int BUBBLE = 2;
	public static final int MORTAR = 3;
	public static final int BEAMSWORD = 4;
	public static final int BOOMERANG = 5;
	public static final int MISSILE = 6;
	public static final int EXPLOSION = 7;
	public static final int LAVABALL = 8;
	public static final int METEOR = 9;
	public static final int LAVAWAVE = 10;
	public static final int LAVAWARN = 11;

	//levels
	public static final int HOLODECK = 0;
	public static final int PLANET = 1;
	public static final int FACTORY = 2;
	public static final int SNOW = 3;
	public static final int DEMO = -1; //dummied out but data still exists for now

	//dimensions for easy level building
	private static final int WIDTH = 640, HEIGHT = 480;

	//actual lists
	private static RoleModel characters[] = 
		{captain(), lizardman(), slime(), spaceMarine(), japaneseRobot(), madScientist()};
	private static ShotModel shots[] =
		{raygun(), fireball(), bubble(), mortar(), beamSword(), boomerang(), missile(), explosion(),
		lavaball(), meteor(), lavaWave(), lavaWarning()};
	private static Blueprint maps[] = 
		{holodeck(), alienPlanet(), factory()};

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
	//lizard man
	private static RoleModel lizardman() {
		RoleModel r = new RoleModel();
		r.setSkin(LIZARD);

		//agile on ground, sluggish in air
		r.setRunFrict(.3);
		r.setAirFrict(.045);
		r.setMaxSpeed(3.9);

		//powerful jump
		r.setJumpPower(8.6);
		r.setJumpHold(4);

		//standard gravity
		r.setGrav(.65);
		r.setTermVel(7);

		//great wall slide
		r.setWallTermVel(1);
		r.setSink(1.3);

		//slightly quicker respawn
		r.setSpawnTime(45);
		r.setSpawnInv(50);

		//bouncing fireballs
		r.setShotType(fireball());
		return r;
	}
	//slime alien
	private static RoleModel slime() {
		RoleModel r = new RoleModel();
		r.setSkin(SLIME);

		//always slippery, equal air and ground
		r.setRunFrict(.1);
		r.setAirFrict(.1);
		r.setMaxSpeed(3);

		//floaty jump
		r.setJumpPower(3.8);
		r.setJumpHold(25);

		//slow fall
		r.setGrav(0.25);
		r.setTermVel(3.5);

		//good fall control
		r.setWallTermVel(1.5);
		r.setSink(1.6);

		//slightly faster respawn
		r.setSpawnTime(45);
		r.setSpawnInv(50);

		//slow bubbles
		r.setShotType(bubble());
		return r;
	}
	//dashing captain
	private static RoleModel captain() {
		RoleModel r = new RoleModel();
		r.setSkin(CAPTAIN);

		//average land speed, a bit weaker in the air
		r.setRunFrict(.2);
		r.setAirFrict(.05);
		r.setMaxSpeed(3);

		//middling jump
		r.setJumpPower(8.0);
		r.setJumpHold(6);

		//average fall
		r.setGrav(.62);
		r.setTermVel(7);

		//weak fall control
		r.setWallTermVel(5.8);
		r.setSink(1.15);

		//quick respawn, long spawn armor
		r.setSpawnTime(45);
		r.setSpawnInv(100);

		//basic raygun
		r.setShotType(raygun());
		return r;
	}
	//Big chufty marine
	private static RoleModel spaceMarine() {
		RoleModel r = new RoleModel();
		r.setSkin(MARINE);

		//average land speed, a bit weaker in the air
		r.setRunFrict(.2);
		r.setAirFrict(.15);
		r.setMaxSpeed(2.15);

		//jetpack jump
		r.setJumpPower(2.2);
		r.setJumpHold(70);

		//hard fall
		r.setGrav(0.6);
		r.setTermVel(7.5);

		//not bad fall control
		r.setWallTermVel(7);
		r.setSink(1.4);

		//slow respawn, average armor
		r.setSpawnTime(65);
		r.setSpawnInv(50);

		//mortar shots
		r.setShotType(mortar());
		return r;
	}
	//Samurai robot
	private static RoleModel japaneseRobot() {
		RoleModel r = new RoleModel();
		r.setSkin(ROBOT);

		//high speed very slippery
		r.setRunFrict(.04);
		r.setAirFrict(.03);
		r.setMaxSpeed(5.4);

		//short hops with potential for higher
		r.setJumpPower(6.1);
		r.setJumpHold(10);

		//slightly brisk fall
		r.setGrav(.85);
		r.setTermVel(7);

		//wallrun
		r.setWallTermVel(-3);
		r.setSink(1.3);

		//average respawn, slightly short armor
		r.setSpawnTime(50);
		r.setSpawnInv(40);

		//quick beam slashes
		r.setShotType(beamSword());
		return r;
	}
	//mad scientist
	private static RoleModel madScientist() {
		RoleModel r = new RoleModel();
		r.setSkin(SCIENTIST);

		//movement...?
		r.setRunFrict(.04);
		r.setAirFrict(.025);
		r.setMaxSpeed(4.75);

		//average jump?
		r.setJumpPower(6.8);
		r.setJumpHold(4);

		//slow fall?
		r.setGrav(0.55);
		r.setTermVel(3.8);

		//ok fall control?
		r.setWallTermVel(3.8);
		r.setSink(1.3);

		//slightly longer spawn armor
		r.setSpawnTime(50);
		r.setSpawnInv(55);

		//cartoony missiles
		r.setShotType(missile());
		return r;
	}

	/*private methods to init each shot type*/
	//bouncing, mario-type fireballs
	private static ShotModel fireball() {
		ShotModel s = new ShotModel();
		s.setSkin(FIREBALL);

		//quick firing
		s.setReload(52);

		//small shots
		s.setH(5);
		s.setW(5);

		//long range, mid speed
		s.setLife(360);
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

		//average firing
		s.setReload(55);

		//large bubbles
		s.setH(15);
		s.setW(15);

		//slow-moving, long lasting
		s.setLife(393);
		s.setSpeed(1);

		//bouncy bubbles slow down
		s.setType(Shot.BOUNCE+Shot.ACCEL+Shot.WEAK);
		s.setVar(-2);
		return s;
	}
	//straight, simple lasers
	private static ShotModel raygun() {
		ShotModel s = new ShotModel();
		s.setSkin(RAYGUN);

		//fast fire
		s.setReload(43);

		//small lasers
		s.setH(2);
		s.setW(20);

		//mid speed long range
		s.setLife(75);
		s.setSpeed(4.2);

		//standard type
		s.setType(0);
		return s;
	}
	//currently just a cannon
	private static ShotModel mortar() {
		ShotModel s = new ShotModel();
		s.setSkin(MORTAR);

		//slow speed
		s.setReload(65);

		//mid-size shots
		s.setH(10);
		s.setW(10);

		//shoot forward
		s.setLife(100);
		s.setSpeed(6.5);
		s.setVSpeed(-3);

		//standard type
		s.setType(Shot.GRAV|Shot.BOMB);
		s.setVar(35);
		return s;
	}
	//mid-range blade
	private static ShotModel beamSword() {
		ShotModel s = new ShotModel();
		s.setSkin(BEAMSWORD);

		//super quick
		s.setReload(30);

		//long blades
		s.setH(6);
		s.setW(20);

		//short range
		s.setLife(12);
		s.setSpeed(9.5);

		//stop quickly
		s.setType(Shot.ACCEL+Shot.MOMENT+Shot.SHIELD);
		s.setVar(-850);
		return s;
	}
	//mid-range blade
	private static ShotModel boomerang() {
		ShotModel s = new ShotModel();
		s.setSkin(BOOMERANG);

		//quick fire
		s.setReload(40);

		//long blades
		s.setH(4);
		s.setW(16);

		//short range
		s.setLife(52);
		s.setSpeed(9);

		//go out then return
		s.setType(Shot.ACCEL);
		s.setVar(-400);
		return s;
	}
	//acceleration missiles
	private static ShotModel missile() {
		ShotModel s = new ShotModel();
		s.setSkin(MISSILE);

		//mid speed
		s.setReload(65);

		//mid-size shots
		s.setH(12);
		s.setW(12);

		//shoot slightly backward
		s.setLife(150);
		s.setSpeed(-4);

		//accelerate quickly forward and explode on impact
		s.setType(Shot.ACCEL|Shot.BOMB);
		s.setVar(210);
		return s;
	}
	//explosions for various uses
	private static ShotModel explosion() {
		ShotModel s = new ShotModel();
		s.setSkin(EXPLOSION);

		//slow speed
		s.setReload(75);

		//start small
		s.setH(4);
		s.setW(4);

		//unmoving
		s.setLife(7);
		s.setSpeed(0);

		//ignore walls and players and get bigger
		s.setType(Shot.GROW|Shot.PHASE|Shot.PIERCE);
		s.setVar(4);
		return s;
	}
	//jumping lava fireballs
	private static ShotModel lavaball() {
		ShotModel s = new ShotModel();
		s.setSkin(FIREBALL);

		//mid-speed firing
		s.setReload(80);

		//small shots
		s.setH(8);
		s.setW(8);

		//short hops up
		s.setLife(50);
		s.setSpeed(0);
		s.setVSpeed(-5.8);

		//bounce across the ground
		s.setType(Shot.GRAV|Shot.PHASE);
		s.setVar(32);
		return s;
	}
	private static ShotModel meteor() {
		ShotModel s = new ShotModel();
		s.setSkin(METEOR);

		//slow firing
		s.setReload(245);

		//huge shots
		s.setH(32);
		s.setW(32);

		//drops straight
		s.setLife(200);
		s.setSpeed(0);
		s.setVSpeed(4.8);

		//fall straight blow up
		s.setType(Shot.BOMB+Shot.BIG);
		s.setVar(0);
		return s;
	}
	private static ShotModel lavaWave() {
		ShotModel s = new ShotModel();
		s.setSkin(LAVAWAVE);

		//very infrequent
		s.setReload(25*50);

		//screen-covering
		s.setH(240);
		s.setW(640);

		//drops straight
		s.setLife(500);
		s.setSpeed(0);
		//s.setVSpeed(-10);
		s.setVSpeed(-12);

		//slowly rise, then fall
		s.setType(Shot.GRAV+Shot.PIERCE+Shot.PHASE);
		s.setVar(6);
		return s;
	}
	private static ShotModel lavaWarning() {
		ShotModel s = new ShotModel();
		s.setSkin(LAVAWAVE);

		//very infrequent
		s.setReload(25*50);

		//screen-covering
		s.setH(240);
		s.setW(640);

		//drops straight
		s.setLife(300);
		s.setSpeed(0);
		s.setVSpeed(-10);

		//slowly rise, then fall
		s.setType(Shot.GRAV+Shot.PIERCE+Shot.PHASE);
		s.setVar(12);
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
	 * The level is 640 x 480, so extend around ~16 pixels beyond that to make sure.
	 */

	//shortcuts to flags
	public static final int SOLID = Land.SOLID; //solid all around (includes top)
	public static final int PLATFORM = Land.PLATFORM; //solid top
	public static final int DANGER = Land.DANGER; //kill on contact
	public static final int BOUNCE = Land.BOUNCE; //bouncy (var = bounciness mod, 0 = 100%, 5 = 150%, -2 = 80%, etc.)
	public static final int SLIP = Land.SLIP; //slippery
	public static final int MOVE = Land.MOVE; //moves you (var = speed*10)
	public static final int HATCH = Land.HATCH; //appears when control is on
	public static final int NHATCH = Land.NHATCH; //appears when controls is off
	public static final int SWITCH = Land.SWITCH; //toggles control when hit
	public static final int PIPE = Land.PIPE; //slide in, slide out
	public static final int WARP = Land.WARP; //for in-game level select (var = target level)
	public static final int CHAR = Land.CHAR; //change characters (var = target character)
	public static final int OPTION = Land.OPTION; //for in game options (var = various things)
	public static final int GUN = Land.GUN; //emits shots (var = bullet to emit)
	public static final int MUTE = Land.MUTE; //makes no sound
	public static final int PSPAWN = Land.PSPAWN; //capable of spawning powerups
	public static final int SOUND = Land.SOUND; //makes a sound when touched (var = sound made)
	public static final int ANIMATE = Land.ANIMATE; //animated tile
	public static final int R_ANIMATE = Land.R_ANIMATE; //backwards animated tile
	public static final int IMAGE = Land.IMAGE; //draw a special image instead of a tilemap

	//tile skins
	public static final int NONE = -1;
	public static final int METAL = 0;
	public static final int GRATE = 1;
	public static final int TUBE = 2;
	public static final int TV = 3;
	public static final int SPRING = 4;
	public static final int BELT = 5; //animated
	public static final int B_BELT = 9; //animated
	public static final int LAVA = 13; //animated

	//level select / menu world
	private static Blueprint holodeck() {
		Blueprint b = new Blueprint();
		b.setId(HOLODECK);
		b.setBg(HOLODECK);
		b.setBgm(HOLODECK);
		b.setName("Level select");

		//character change chambers
		b.add(METAL, -1*16, -1*16, 3*16, 6*16, SOLID);
		b.add(NONE, 2*16, -1*16, 3*16, 3*16, SOUND, SoundBank.SPAWN);
		b.add(NONE, 2*16, -1*16, 3*16, 3*16, BOUNCE|CHAR|MUTE, LIZARD);
		b.add(TUBE, 2*16, -1*16, 3*16, 5*16, PIPE|SOLID, LIZARD);
		b.add(METAL, 5*16, -1*16, 4*16, 6*16, SOLID);
		b.add(NONE, 9*16, -1*16, 3*16, 3*16, SOUND, SoundBank.SPAWN);
		b.add(NONE, 9*16, -1*16, 3*16, 3*16, BOUNCE|CHAR|MUTE, SLIME);
		b.add(TUBE, 9*16, -1*16, 3*16, 5*16, PIPE|SOLID, SLIME);
		b.add(METAL, 12*16, -1*16, 4*16, 6*16, SOLID);
		b.add(NONE, 16*16, -1*16, 3*16, 3*16, SOUND, SoundBank.SPAWN);
		b.add(NONE, 16*16, -1*16, 3*16, 3*16, BOUNCE|CHAR|MUTE, MARINE);
		b.add(TUBE, 16*16, -1*16, 3*16, 5*16, PIPE|SOLID, MARINE);
		b.add(METAL, 19*16, -1*16, 4*16, 6*16, SOLID);
		b.add(NONE, 23*16, -1*16, 3*16, 3*16, SOUND, SoundBank.SPAWN);
		b.add(NONE, 23*16, -1*16, 3*16, 3*16, BOUNCE|CHAR|MUTE, ROBOT);
		b.add(TUBE, 23*16, -1*16, 3*16, 5*16, PIPE|SOLID, ROBOT);
		b.add(METAL, 26*16, -1*16, 8*16, 6*16, SOLID);
		b.add(NONE, 34*16, -1*16, 3*16, 3*16, SOUND, SoundBank.DIE);
		b.add(NONE, 34*16, -1*16, 3*16, 3*16, BOUNCE|CHAR|MUTE, CAPTAIN);
		b.add(TUBE, 34*16, -1*16, 3*16, 5*16, PIPE|SOLID, CAPTAIN);
		b.add(METAL, 37*16, -1*16, 4*16, 6*16, SOLID);

		//character tags
		b.add(LIZARD, 6*16, 3*16+4, 1*16, 1*16, IMAGE);
		b.add(SLIME, 13*16, 3*16+4, 1*16, 1*16, IMAGE);
		b.add(MARINE, 20*16, 3*16+4, 1*16, 1*16, IMAGE);
		b.add(ROBOT, 27*16, 3*16+4, 1*16, 1*16, IMAGE);
		b.add(CAPTAIN, 32*16, 3*16+4, 1*16, 1*16, IMAGE);

		//top/'attic' ladders
		b.add(GRATE, 2*16, 8*16, 3*16, 16, PLATFORM);
		b.add(GRATE, 9*16, 8*16, 3*16, 16, PLATFORM);
		b.add(GRATE, 16*16, 8*16, 3*16, 16, PLATFORM);
		b.add(GRATE, 23*16, 8*16, 3*16, 16, PLATFORM);
		b.add(GRATE, 34*16, 8*16, 3*16, 16, PLATFORM);

		//top floor
		b.add(METAL, -1*16, 11*16, 18*16, 2*16, SOLID);
		b.add(GRATE, 17*16, 11*16, 6*16, 16, PLATFORM);
		b.add(METAL, 23*16, 11*16, 18*16, 2*16, SOLID);

		//middle/top ladder
		b.add(GRATE, 18*16, 15*16, 4*16, 16, PLATFORM);

		//option change blocks
		b.add(TV, 11*16, 14*16, 3*16, 2*16, SOUND, SoundBank.PICKUP);
		b.add(TV, 11*16, 14*16, 3*16, 2*16, SOLID|BOUNCE|OPTION|MUTE, 0); //mode
		b.add(TV, 26*16, 14*16, 3*16, 2*16, SOUND, SoundBank.PICKUP);
		b.add(TV, 26*16, 14*16, 3*16, 2*16, SOLID|BOUNCE|OPTION|MUTE, 1); //parameter

		//middle floor
		b.add(GRATE, -1*16, 19*16, 7*16, 16, PLATFORM);
		b.add(METAL, 6*16, 19*16, 28*16, 2*16, SOLID);
		b.add(GRATE, 34*16, 19*16, 7*16, 16, PLATFORM);

		//bottom/middle ladders
		b.add(GRATE, 1*16, 23*16, 4*16, 16, PLATFORM);
		b.add(GRATE, 35*16, 23*16, 4*16, 16, PLATFORM);

		//base floor + warps
		b.add(METAL, -1*16, 27*16, 13*16, 4*16, SOLID);
		b.add(NONE, 12*16, 27*16, 4*16, 1*16, PLATFORM);
		b.add(METAL, 12*16, 30*16, 4*16, 1*16, BOUNCE|WARP|MUTE, PLANET);
		b.add(TUBE, 12*16, 27*16+1, 4*16, 4*16, PIPE); //planet portal
		b.add(METAL, 16*16, 27*16, 8*16, 4*16, SOLID);
		b.add(NONE, 24*16, 27*16, 4*16, 1*16, PLATFORM);
		b.add(METAL, 24*16, 30*16, 4*16, 1*16, BOUNCE|WARP|MUTE, FACTORY);
		b.add(TUBE, 24*16, 27*16+1, 4*16, 4*16, PIPE); //factory portal
		b.add(METAL, 28*16, 27*16, 13*16, 4*16, SOLID);

		//level signs
		b.add(ROBOT+PLANET, 17*16, 28*16-8, 1*16, 1*16, IMAGE);
		b.add(ROBOT+FACTORY, 29*16, 28*16-8, 1*16, 1*16, IMAGE);

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
		b.setBg(PLANET);
		b.setBgm(PLANET);
		b.setName("Alien Planet Surface");

		//meteor shooter
		b.add(NONE, 1*16, -9*16, 38*16, 8*16, GUN|PSPAWN, METEOR);

		//cave
		b.add(METAL, -1*16, 16*16, 3*16, 15*16, SOLID);
		b.add(METAL, 38*16, 16*16, 3*16, 15*16, SOLID);
		b.add(METAL, 1*16, 27*16, 17*16, 4*16, SOLID);
		b.add(METAL, 22*16, 27*16, 17*16, 4*16, SOLID);

		//cannon escape
		b.add(NONE, 18*16, 27*16, 4*16, 1*16, PLATFORM);
		b.add(NONE, 18*16, 29*16, 4*16, 1*16, SOUND, SoundBank.BOOM);
		b.add(NONE, 18*16, 29*16, 4*16, 1*16, BOUNCE|MUTE, 175);
		b.add(TUBE, 18*16, 27*16+1, 4*16, 4*16, PIPE);

		//surface
		b.add(B_BELT, -1*16, 15*16, 15*16, 1*16, SOLID|MOVE|ANIMATE, 8);
		b.add(BELT, 14*16, 15*16, 5*16, 1*16, PLATFORM|MOVE|ANIMATE, 22);
		b.add(BELT, 21*16, 15*16, 5*16, 1*16, PLATFORM|MOVE|R_ANIMATE, -22);
		b.add(B_BELT, 26*16, 15*16, 15*16, 1*16, SOLID|MOVE|R_ANIMATE, -8);

		//internal platforms
		b.add(BELT, 18*16, 21*16, 2*16, 1*16, PLATFORM|MOVE|R_ANIMATE, -8);
		b.add(BELT, 20*16, 21*16, 2*16, 1*16, PLATFORM|MOVE|ANIMATE, 8);

		b.add(GRATE, 36*16, 24*16, 2*16, 1*16, PLATFORM);
		b.add(GRATE, 8*16, 22*16, 4*16, 1*16, PLATFORM);

		b.add(GRATE, 24*16, 18*16, 1*16, 1*16, PLATFORM);

		//lava waves!
		b.add(NONE, WIDTH/2, HEIGHT*7/2-64, 1, 1, GUN, LAVAWAVE);
		b.add(NONE, WIDTH/2, HEIGHT*2+32, 1, 1, GUN, LAVAWARN);

		//spawn points
		b.setSpawn(0, 5*16, 14*16);
		b.setSpawn(1, 10*16, 14*16);
		b.setSpawn(2, 30*16, 14*16);
		b.setSpawn(3, 35*16, 14*16);

		return b;
	}
	//springs and conveyers machine world
	private static Blueprint factory() {
		Blueprint b = new Blueprint();
		b.setId(FACTORY);
		b.setBg(FACTORY);
		b.setBgm(FACTORY);
		b.setName("Factory");

		//floor+lower walls
		b.add(METAL, 5*16, 29*16, 10*16, 2*16, SOLID);
		b.add(METAL, 25*16, 29*16, 10*16, 2*16, SOLID);
		b.add(METAL, 2*16, 29*16, 3*16, 2*16, PLATFORM);
		b.add(TUBE, 2*16, 29*16+1, 3*16, 3*16, PIPE);
		b.add(METAL, 35*16, 29*16, 3*16, 2*16, PLATFORM);
		b.add(TUBE, 35*16, 29*16+1, 3*16, 3*16, PIPE);
		b.add(METAL, -1*16, 16*16, 3*16, 14*16, SOLID);
		b.add(METAL, 38*16, 16*16, 3*16, 14*16, SOLID);

		//melter
		b.add(METAL, 15*16, 28*16, 1*16, 3*16, SOLID);
		b.add(LAVA, 16*16, 29*16, 8*16, 2*16, DANGER|GUN|ANIMATE, LAVABALL);
		b.add(NONE, 16*16, 29*16+4, 8*16, 1*16, BOUNCE, 5);
		b.add(METAL, 24*16, 28*16, 1*16, 3*16, SOLID);

		//upper walls+ceiling
		b.add(METAL, -1*16, -1*16, 3*16, 14*16, SOLID);
		b.add(METAL, 38*16, -1*16, 3*16, 14*16, SOLID);
		b.add(TUBE, 2*16, -1*16, 3*16, 2*16, PIPE);
		b.add(TUBE, 35*16, -1*16, 3*16, 2*16, PIPE);
		b.add(METAL, 5*16, -1*16, 30*16, 2*16, SOLID);

		//middle wraparound
		b.add(METAL, 2*16, 16*16, 3*16, 2*16, SOLID);
		b.add(METAL, 5*16, 12*16, 3*16, 6*16, SOLID);
		b.add(METAL, 30*16, 16*16, 8*16, 2*16, SOLID);

		//top production
		b.add(B_BELT, 0*16, 5*16, 10*16, 2*16, SOLID|MOVE|ANIMATE, 19);
		b.add(NONE, 1*16, 4*16, 1, 1, PSPAWN);
		b.add(METAL, 0*16, 3*16, 2*16, 4*16, 0); //visual producer

		//middle production
		b.add(B_BELT, 32*16, 20*16, 8*16, 2*16, SOLID|MOVE|R_ANIMATE, -19);
		b.add(NONE, 39*16, 19*16, 1, 1, PSPAWN);
		b.add(METAL, 38*16, 18*16, 2*16, 4*16, SOLID); //visual producer

		//middle carry
		b.add(BELT, 9*16, 13*16, 18*16, 1*16, PLATFORM|MOVE|ANIMATE, 19);
		b.add(BELT, 22*16, 21*16, 10*16, 1*16, PLATFORM|MOVE|R_ANIMATE, -19);

		//spring lift
		b.add(SPRING, 10*16, 25*16, 3*16, 1*16, PLATFORM|BOUNCE, 15);
		b.add(SPRING, 27*16, 25*16, 3*16, 1*16, PLATFORM|BOUNCE, 10);

		//spawn points
		b.setSpawn(0, 2*16, 15*16);
		b.setSpawn(1, 38*16, 15*16);
		b.setSpawn(2, 10*16, 35*16);
		b.setSpawn(3, 20*16, 15*16);

		return b;
	}
	//test environment
	private static Blueprint demo() {
		Blueprint b = new Blueprint();
		b.setId(DEMO);
		b.setName("Test level");

		//build various test platforms
		b.add(METAL, WIDTH/4, HEIGHT*3/4, WIDTH/2, 48, SOLID|PSPAWN);
		b.add(METAL, WIDTH/4, HEIGHT*3/4-40, WIDTH/8, 4, PLATFORM|MOVE|HATCH, 18);
		b.add(METAL, WIDTH*7/16, HEIGHT*3/4-90, WIDTH/8, 24, BOUNCE|SOLID, 2);
		b.add(METAL, WIDTH*5/8, HEIGHT*3/4-40, WIDTH/8, 4, PLATFORM|SLIP|NHATCH);
		b.add(METAL, 20, HEIGHT/2-50, 50, 50, SOLID|DANGER);
		b.add(METAL, WIDTH-70, HEIGHT/2-50, 50, 50, SOLID|DANGER);

		b.add(METAL, WIDTH/4+10, HEIGHT*3/4-160, 25, 25, SOLID|HATCH|SWITCH|BOUNCE);
		b.add(METAL, WIDTH*3/4-35, HEIGHT*3/4-160, 25, 25, SOLID|NHATCH|SWITCH|BOUNCE);

		b.add(METAL, WIDTH/2-10, HEIGHT/4, 20, 20, GUN, EXPLOSION);

		//add spawn points
		b.setSpawn(0, 150, 60);
		b.setSpawn(1, 200, 50);
		b.setSpawn(2, 250, 50);
		b.setSpawn(3, 300, 60);

		return b;
	}
}
