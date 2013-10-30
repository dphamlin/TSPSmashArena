package game;

/**
 * Server implementation of the GameState, with updating methods
 * 
 * @author Jacob Charles
 */

public class ServerGameState extends GameState {

	//a bunch of positional and directional constants
	private static final int LEFT = -1;
	private static final int STOP = 0;
	private static final int RIGHT = 1;
	private static final int TOP = -1;
	private static final int BOTTOM = 1;
	private static final int NONE = 0;

	//non-synchronized game results
	GameResults res = new GameResults();

	/**
	 * Generic constructor
	 */
	public ServerGameState(){
		super();
	}

	/**
	 * Clone constructor from generic GameState
	 * 
	 * @param g
	 * 		GameState to clone from
	 */
	public ServerGameState(GameState g){
		super(g);
	}

	/**
	 * Convert ServerGameState to a ClientGameState
	 * 
	 * @return ClientGameState version of this object
	 */
	public ClientGameState convert() {
		return new ClientGameState(this);
	}

	/**
	 * Get the results of a game
	 * 
	 * @return the game results, in a nice package
	 */
	public GameResults getResults() {
		res.setMode(getNextMode()); //nextMode is preserved in case of Sudden Death Match
		res.setStock(getStock());
		res.setTime(getTime());
		res.setWinners(getWinners());
		return res;
	}

	/**
	 * Update the entire game state
	 * 
	 * Apply user input first!
	 */
	public void update() {
		//player logic
		for (Actor a : getFighters()) {
			update(a);
		}
		//bullet logic (with removal)
		for(int i = 0; i < getBullets().size(); i++) {
			update(getBullets().get(i));
			//remove dead bullets
			if (getBullets().get(i).isDead()) {
				getBullets().remove(i);
				i--;
			}
		}
		//power-up/item logic (with removal)
		for(int i = 0; i < getPowerups().size(); i++) {
			update(getPowerups().get(i));
			//remove dead items
			if (getPowerups().get(i).isDead()) {
				getPowerups().remove(i);
				i--;
			}
		}
		//TODO: Special effects update here

		//check for the end of the game
		checkEnd();

		//TODO: Flow control?

		//track the number of frames passed
		incrementFrames();
	}

	/**
	 * Apply a participant's controls to their character
	 * 
	 * @param p
	 * 		the participant to update from
	 */
	public void readControls(Participant p) {
		Actor a = p.getPlayer();
		Controller c = p.getController();
		readControls(a, c);
	}

	/**
	 * Apply a player's controls to their character
	 * 
	 * @param a
	 * 		the actor associated with the input
	 * @param c
	 * 		the controller data
	 */
	public void readControls(Actor a, Controller c) {
		if (a.isDead()) return;

		//running
		if (c.getLeft() > 0) {
			run(a, LEFT);
		}
		else if (c.getRight() > 0) {
			run(a, RIGHT);
		}
		else {
			run(a, STOP);
		}
		
		//crouching (through tiles)
		a.setCrouch(c.getDown() > 0);

		//jumping
		if ((c.getJump() == 1 || c.getUp() == 1) && a.getOnLand() != null) {
			jump(a);
		}
		else if (c.getJump() > 0 || c.getUp() > 0) {
			holdJump(a);
		}

		//shooting
		if (c.getFire() == 1) {
			shoot(a);
		}
	}

	@Override
	public Actor addPlayer(int character) {
		res.addPlayer();
		return super.addPlayer(character);
	}

	/**
	 * Update whether the game has ended or not
	 */
	private void checkEnd() {
		//menu has no end
		if (getMode() == MENU) {
			setEnd(false);
		}
		//there is only one (survivor)
		if (getMode() == STOCK && getNumberOfPlayers() > 1) {
			setEnd(getLivingPlayers() < 2);
		}
		//single player match (for testing mostly)
		else if (getMode() == STOCK) {
			setEnd(getLivingPlayers() <= 0);
		}
		//time out
		else if (getMode() == TIME) {
			setEnd(getFrameNumber() > getTime());
		}
	}

	/**
	 * @return the number of players still alive
	 */
	private int getLivingPlayers() {
		int n = 0;
		for (Actor a : getFighters()) {
			if (a.getLives() > 0) n++;
		}
		return n;
	}
	
	/**
	 * 
	 * @return the number of players without a chosen character
	 */
	private int getNoPs() {
		int n = 0;
		for (Actor a : getFighters()) {
			if (a.getModel() == Warehouse.NOP) n++;
		}
		return n;
	}

	/**
	 * Make an actor jump
	 * 
	 * @param a
	 * 		the actor to make jump
	 */
	private void jump (Actor a) {
		a.setAirTime(1);
		a.setOnLand(null);
		a.setVy(-a.getJumpPower());
	}

	/**
	 * Extend the height of an actor's jump
	 * 
	 * @param a
	 * 		the actor to extend the jump of
	 */
	private void holdJump (Actor a) {
		if (a.getAirTime() > 0 && a.getAirTime() <= a.getJumpHold()
				&& a.getVy() <= 2*a.getGrav()-a.getJumpPower()) {
			a.setVy(-a.getJumpPower());
		}
	}

	/**
	 * Make an actor fall
	 * 
	 * @param a
	 * 		the actor to make fall
	 */
	private void fall (Actor a) {
		a.setAirTime(1);
		a.setOnLand(null);
		a.setVy(STOP);
	}

	/**
	 * Make an actor land on a piece of land
	 * 
	 * @param a
	 * 		the actor to land
	 * @param l
	 * 		the land to land on
	 */
	private void land (Actor a, Land l) {
		a.setVy(STOP);
		a.setAirTime(-1);
		a.setOnLand(l);
	}

	/**
	 * Make an actor run in the specified direction
	 * 
	 * @param a
	 * 		the actor to make run
	 * @param dir
	 * 		LEFT, RIGHT, or STOP, the direction to run
	 */
	private void run (Actor a, int dir) {
		//limit and set direction
		if (dir > RIGHT) dir = RIGHT;
		if (dir < LEFT) dir = LEFT;
		if (dir != 0) {
			a.setDir(dir);
		}

		//in the air
		if (a.getAirTime() > 1) {
			a.setVx(a.getVx()+a.getAirSpeed()*dir);
		}
		//on the ground
		else {
			a.setVx(a.getVx()+a.getRunSpeed()*dir);
		}
	}

	/**
	 * Make an actor fire their shot
	 * 
	 * @param a
	 * 		the actor doing the shooting
	 */
	private void shoot (Actor a) {
		//can't fire if you haven't reloaded
		if (a.getReload() > 0) {
			return;
		}

		//delay until next shot
		a.setReload(a.getShotDelay());

		//build a new shot, according to the Actor's specifications
		Shot s = new Shot(a.getShot(), a);

		//add the new bullet to the list of bullets
		getBullets().add(s);
	}

	/**
	 * The specified actor dies
	 * 
	 * @param a
	 * 		the actor who dies
	 */
	private void die (Actor a) {
		a.setDeadTime(0);
		a.setDead(true);
		if (!isGameOver() && getMode() != MENU) {
			a.loseLife();
		}
	}

	/**
	 * An actor kills another
	 * 
	 * @param a
	 * 		the killer
	 * @param b
	 * 		the kill-ee
	 */
	private void kill (Actor a, Actor b) {
		//target dies
		die(b);

		//killed by a target
		if (a != null) {
			//score a point
			if (!isGameOver() && getMode() != MENU) {
				a.gainPoint();
			}
			//a gets a kill, b gets a death
			res.addKill(a.getId(), b.getId());
			res.addDeath(b.getId(), a.getId());
		}
		else {
			//lose a point
			if (!isGameOver() && getMode() != MENU) {
				b.losePoint();
			}
			//death by level
			res.addDeath(b.getId(), -1);
		}
	}

	/**
	 * Respawn specified actor
	 * 
	 * @param a
	 * 		actor to respawn
	 */
	private void respawn (Actor a) {
		//don't respawn if you're out of lives in a stock match
		if (a.getLives() <= 0 && getMode() == STOCK) {
			return;
		}
		a.setDead(false);
		fall(a);
		a.setHCenter(getSpawnX(a.getId()));
		a.setVCenter(getSpawnX(a.getId()));
	}

	/**
	 * update the actor's status
	 * 
	 * @param a
	 * 		the actor to update
	 */
	private void update (Actor a) {
		if (a.getAirTime() > 0) a.setAirTime(a.getAirTime()+1); //time in mid-air
		if (a.getAirTime() < 0) a.setAirTime(a.getAirTime()-1); //time on the ground

		a.setDeadTime(a.getDeadTime()+1); //respawn timer and spawn invincibility
		if (a.getDeadTime() == a.getSpawnTime() && a.isDead()) respawn(a);

		if (a.getReload() > 0)a.setReload(a.getReload()-1); //timer between shots

		if (!a.isDead()) {
			move(a); //updates positions and speeds
		}
	}

	/**
	 * update a shot's status
	 * 
	 * @param s
	 * 		the shot to update
	 */
	private void update (Shot s) {
		if (s.getLifeTime() <= 0) {
			s.setDead(true);
			return;
		}
		s.setLifeTime(s.getLifeTime()-1);
		move(s);
	}

	/**
	 * update an item's status
	 * 
	 * @param p
	 * 		the item to update
	 */
	private void update (Item p) {
		//TODO: item updates
		move(p);
	}

	/**
	 * Check if two objects are overlapping
	 * 
	 * @param a
	 * 		the first object
	 * @param b
	 * 		the second object
	 * @return
	 * 		true if they are overlapped
	 */
	private boolean overlap (GameObject a, GameObject b) {
		//check that the edges are pushed through
		if (a.getBottomEdge()+a.getVy() >= b.getTopEdge() && a.getTopEdge() <= b.getBottomEdge()) {
			if (a.getRightEdge() >= b.getLeftEdge() && a.getLeftEdge() <= b.getRightEdge()) {
				return true;
			}
		}
		//no overlap
		return false;
	}

	/**
	 * Check collisions between two objects horizontally
	 * 
	 * @param a
	 * 		the object to collide
	 * @param b
	 * 		the object it collides with
	 * @return
	 * 		LEFT if A is to the left of B
	 * 		RIGHT if A is to the right of B
	 * 		NONE if there is no collision
	 */
	private int hCollide (GameObject a, GameObject b) {
		//lined up for horizontal collisions
		if (a.getBottomEdge()+a.getVy() >= b.getTopEdge()+b.getVy() 
				&& a.getTopEdge()+a.getVy()  <= b.getBottomEdge()+b.getVy()) {
			//moving right
			if (a.getRightEdge() <= b.getLeftEdge() && a.getRightEdge()+a.getVx() > b.getLeftEdge()+b.getVx()) {
				return LEFT;
			}
			//moving left
			else if (a.getLeftEdge() >= b.getRightEdge() && a.getLeftEdge()+a.getVx() < b.getRightEdge()+b.getVx()) {
				return RIGHT;
			}
		}

		//no collisions
		return 0;
	}

	/**
	 * Check collisions between two objects vertically
	 * 
	 * @param a
	 * 		the object to collide
	 * @param b
	 * 		the object it collides with
	 * @return
	 * 		TOP if A is on top of B
	 * 		BOTTOM if A is under B
	 * 		NONE if there is no collision
	 */
	private int vCollide (GameObject a, GameObject b) {
		//lined up for vertical collisions
		if (a.getRightEdge()+a.getVx() >= b.getLeftEdge()+b.getVx()
				&& a.getLeftEdge()+a.getVx() <= b.getRightEdge()+b.getVx()) {
			//falling
			if (a.getBottomEdge() <= b.getTopEdge() && a.getBottomEdge()+a.getVy() > b.getTopEdge()+b.getVy()) {
				return TOP;
			}
			//rising
			else if (a.getTopEdge() >= b.getBottomEdge() && a.getTopEdge()+a.getVy() < b.getBottomEdge()+b.getVy()) {
				return BOTTOM;
			}
		}

		//no collisions
		return 0;
	}

	/**
	 * Check for and handle collision between an actor and the stage 
	 * 
	 * @param a
	 * 		the actor to collide
	 * @param l
	 * 		the land to check for collisions with
	 */
	private void collide (Actor a, Land l) {

		//dead men don't interact
		if (a.isDead()) return;
		if (l.isHatch() && !isControl()) return;
		if (l.isNHatch() && isControl()) return;
		
		//collision values
		int v = vCollide(a, l);
		int h = hCollide(a, l);
		boolean ov = overlap(a, l);

		//activate warp blocks
		if (l.isWarp() && getNoPs() == 0 && (v != NONE || h != NONE || ov)) {
			/*setLevel(l.getVar()); //warp to chosen level
			setMode(getNextMode()); //change to appropriate game mode*/
			//TODO: Take a vote- enough people and you can warp
		}

		//character change blocks
		if (l.isChar() && (v != NONE || h != NONE || ov)) {
			a.setModel(l.getVar());
			respawn(a);
		}

		//option changing blocks
		if (l.isOption() && (v != NONE || h != NONE || ov)) {
			//mode changing
			if (l.getVar() == 0) {
				//cycle up
				if (getNextMode() == STOCK) {
					setNextMode(TIME);
				}
				//loop back
				else {
					setNextMode(STOCK);
				}
			}
			//TODO: add variable changing blocks, somehow...?
		}

		//TODO: Setting (lives/time) changing block

		//TODO: "Exit server" blocks?

		//die on touching danger
		if (l.isDanger() && (v != NONE || h != NONE || ov)) {
			kill(null, a); //killed by no one
		}

		//switch blocks
		if (l.isSwitch() && (v != NONE || h != NONE || ov)) {
			setControl(!isControl());
		}
		
		//bouncy blocks
		if (v == TOP && l.isBounce()) {
			a.setBottomEdge(l.getTopEdge()-1);
			a.setVy(-a.getVy()*(10+l.getVar())/10);
		}
		//bouncy platforms don't have the other sides
		if (v == BOTTOM && l.isBounce() && !l.isPlatform()) {
			a.setTopEdge(l.getBottomEdge()+1);
			a.setVy(-a.getVy()*(10+l.getVar())/10);
		}
		if (h == LEFT && l.isBounce() && !l.isPlatform()) {
			a.setRightEdge(l.getLeftEdge()-1);
			a.setVx(-a.getVx()*(10+l.getVar())/10);
		}
		if (h == RIGHT && l.isBounce() && !l.isPlatform()) {
			a.setLeftEdge(l.getRightEdge()+1);
			a.setVx(-a.getVx()*(10+l.getVar())/10);
		}
		if (l.isBounce()) return; //bounced off, no other action

		//solid or platform floors
		if (v == TOP && (l.isSolid() || (l.isPlatform() && !a.isCrouch())) ) {
			a.setBottomEdge(l.getTopEdge()-1);
			land(a, l);
		}

		//solid walls and ceilings
		if (v == BOTTOM && l.isSolid()) {
			a.setTopEdge(l.getBottomEdge()+1);
			a.setVy(STOP);
		}
		if (h == LEFT && l.isSolid()) {
			a.setRightEdge(l.getLeftEdge()-1);
			a.setVx(STOP);
		}
		if (h == RIGHT && l.isSolid()) {
			a.setLeftEdge(l.getRightEdge()+1);
			a.setVx(STOP);
		}
	}

	/**
	 * Check for actor/actor collisions and head bouncing
	 * 
	 * @param a
	 * 		actor to collide
	 * @param b
	 * 		actor to check collisions with
	 */
	private void collide(Actor a, Actor b) {
		//can't hit yourself, a dead guy, or an invincible guy
		if (a == b || a.isDead() || b.isDead() || b.isArmored()) {
			return;
		}

		//land on enemy heads
		if (vCollide(a, b) == TOP) {
			a.setBottomEdge(b.getTopEdge());
			jump(a);
			kill(a, b);
		}

		//bounce each other back
		int h = hCollide(a, b);
		if (h == LEFT) {
			float cVx = a.getVx();
			a.setVx(b.getVx()-5);
			b.setVx(cVx+5);
		}
		if (h == RIGHT) {
			float cVx = a.getVx();
			a.setVx(b.getVx()+5);
			b.setVx(cVx-5);
		}
	}

	/**
	 * Check for collisions between a shot and the terrain
	 * 
	 * @param s
	 * 		shot to collide
	 * @param l
	 * 		land to check for collisions with
	 */
	private void collide(Shot s, Land l) {
		//non-solid platforms simply return for now
		if (!l.isSolid() || s.isPhase()) {
			return;
		}
		
		//out-of-phase platforms ignored
		if (l.isHatch() && !isControl()) return;
		if (l.isNHatch() && isControl()) return;

		//bounce off non-spikes
		if (s.isBounce() && !l.isDanger()) {
			//top and bottom
			if (vCollide(s,l) != NONE) {
				s.setVy(-s.getVy());
			}
			//sides
			else if (hCollide(s,l) != NONE) {
				s.setVx(-s.getVx());
				if (s.isAccel()) s.setVar(-s.getVar());
			}
			//stuck inside
			else if (overlap(s,l)) {
				s.setDead(true); 
			}
		}
		//check for any collision
		else if (overlap(s, l) || vCollide(s, l) != NONE || hCollide(s, l) != NONE) {
			s.setDead(true);
		}
	}

	/**
	 * Check for shot-actor collisions
	 * 
	 * @param s
	 * 		shot to collide
	 * @param a
	 * 		actor to check collisions with
	 */
	private void collide(Shot s, Actor a) {
		//don't hit your own source
		if (s.getSource() == a.getId() || a.isDead() || a.isArmored()) {
			return;
		}

		//check for any collision
		if (overlap(s, a) || vCollide(s, a) != NONE || hCollide(s, a) != NONE) {
			if (!s.isPierce()) s.setDead(true);
			kill(getPlayer(s.getSource()), a);
		}
	}

	/**
	 * Move the actor according to their current speeds (with gravity)
	 * 
	 * @param a
	 * 		the actor to move
	 */
	private void move(Actor a) {
		//check collisions with the level
		for (Land l : getLevel()) {
			collide(a, l);
		}

		//check for collisions with other actors
		for (Actor b : getFighters()) {
			collide(a, b);
		}

		//move along the ground
		a.setX(a.getX()+a.getVx());
		//apply ground friction (moving ground)
		if (a.getOnLand() != null && a.getOnLand().isMove()) {
			Land l = a.getOnLand();
			a.setVx((a.getVx()-l.getVar()*.1)*a.getRunSlip()+l.getVar()*.1); //slip + movement
		}
		//apply ground friction (normal ground)
		else if (a.getOnLand() != null) {
			a.setVx(a.getVx()*a.getRunSlip()); //slide
		}

		//move through the air
		if (a.getAirTime() > 0) {

			//apply air friction and momentum
			a.setVx(a.getVx()*a.getAirSlip()); //slide

			//move vertically
			a.setY(a.getY()+a.getVy());

			//gravity
			a.setVy(a.getVy()+a.getGrav());

			//apply terminal velocity
			if (a.getVy() > a.getTermVel()) a.setVy(a.getTermVel());
		}
		//on the ground, stop vertical motion
		else {
			a.setVy(0);
		}

		//falling off edges and ducking through platforms
		Land l = a.getOnLand();
		if (l != null) {
			if (a.getRightEdge() < l.getLeftEdge() || a.getLeftEdge() > l.getRightEdge()) {
				fall(a);
			}
			if (a.isCrouch() && l.isPlatform() && !l.isSolid()) {
				fall(a);
			}
		}

		//out-of-bounds wraparound (temp)
		if (a.getBottomEdge() < 0) a.setTopEdge(GameState.HEIGHT); //off top
		if (a.getTopEdge() > GameState.HEIGHT) a.setBottomEdge(0); //off bottom
		if (a.getRightEdge() < 0) a.setLeftEdge(GameState.WIDTH); //off left
		if (a.getLeftEdge() > GameState.WIDTH) a.setRightEdge(0); //off right
	}

	/**
	 * Move the shot according to its speeds
	 * 
	 * @param s
	 * 		the shot to be moved
	 */
	private void move(Shot s) {
		//terrain collisions
		for (Land l : getLevel()) {
			collide(s, l);
		}
		//target collisions
		for (Actor a : getFighters()) {
			collide(s, a);
		}

		//apply velocity
		s.setX(s.getX()+s.getVx());
		s.setY(s.getY()+s.getVy());
		
		//apply gravity (uses vx as terminal velocity- yes it's hackish)
		if (s.isGravity()) {
			s.setVy(s.getVy()+s.getVar()/100.0);
			if (s.getVy() > Math.abs(s.getVx())) {
				s.setVy(Math.abs(s.getVx()));
			}
		}

		//apply acceleration/deceleration, no maximum for now
		if (s.isAccel()) {
			s.setVx(s.getVx()+s.getVar()/1000.0);
		}

		//out of bounds removal
		if (s.getBottomEdge() < -s.getH()*2 || s.getTopEdge() > GameState.HEIGHT+s.getH()*2
				|| s.getRightEdge() < -s.getW()*2 || s.getLeftEdge() > GameState.WIDTH+s.getW()*2) {
			s.setDead(true);
		}
	}

	/**
	 * Move the item around the level
	 * 
	 * @param p
	 * 		the item to move
	 */
	private void move(Item p) {
		//TODO: Moving items
	}
}
