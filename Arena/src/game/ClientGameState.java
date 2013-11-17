package game;

/**
 * Client implementation of the game state
 * 
 * @author Jacob Charles
 */
import java.awt.Color;
import java.awt.Graphics;

public class ClientGameState extends GameState {

	/**
	 * Generic constructor
	 */
	public ClientGameState(){
		super();
	}

	/**
	 * Clone constructor from generic GameState
	 * 
	 * @param g
	 * 		GameState to clone from
	 */
	public ClientGameState(GameState g){
		super(g);
	}

	/**
	 * Draw everything
	 * 
	 * @param g
	 */
	public void draw(Graphics g) {
		drawBackground(g);
		drawFighters(g);
		drawPowerups(g);
		drawLevel(g);
		drawBullets(g);
		drawEffects(g);
		drawStatus(g);
	}

	/**
	 * Draw a background
	 * 
	 * @param g
	 * 		graphics object to draw through
	 */
	private void drawBackground(Graphics g) {
		//TODO: Clear with a background image instead
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 640, 480);
	}

	/**
	 * Draw the fighters
	 * 
	 * @param g
	 * 		graphics object to draw through
	 */
	private void drawFighters(Graphics g) {
		for (Actor a : getFighters()) {
			draw(a, g);
		}
	}

	/**
	 * Draw the level objects
	 * 
	 * @param g
	 * 		graphics object to draw through
	 */
	private void drawLevel(Graphics g) {
		for (Land l : getLevel()) {
			draw(l, g);
		}
	}

	/**
	 * Draw the projectiles
	 * 
	 * @param g
	 * 		graphics object to draw through
	 */
	private void drawBullets(Graphics g) {
		for (Shot s : getBullets()) {
			draw(s, g);
		}
	}

	/**
	 * Draw the powerups
	 * 
	 * @param g
	 * 		graphics object to draw through
	 */
	private void drawPowerups(Graphics g) {
		for (Item i : getPowerups()) {
			draw(i, g);
		}
	}

	/**
	 * Draw the effects
	 * 
	 * @param g
	 * 		graphics object to draw through
	 */
	private void drawEffects(Graphics g) {
		for (Effect e : getEffects()) {
			draw(e, g);
		}
	}

	/**
	 * Draw character statuses
	 * 
	 * @param g
	 * 		graphics object to draw through
	 */
	private void drawStatus(Graphics g) {
		//if (getMode() == MENU) return; //temporarily blind status until gametime
		//draw spots for the number of players expected
		for (int i = 0; i < getMaxPlayers() || i < getNumberOfPlayers(); i++) {
			g.setColor(Color.BLACK);
			g.fillRoundRect((1+i)*WIDTH/5-27, 10, 55, 36, 10, 10);
		}
		//draw actual characters and stats
		for (int i = 0; i < getNumberOfPlayers(); i++) {
			if (getPlayer(i).isSuspend()) continue; //skip over suspended players

			g.setColor(Color.WHITE);
			if (getMode() == STOCK) {
				g.drawString("x"+getPlayer(i).getLives(), 3+(1+i)*WIDTH/5, 35);
			}
			if (getMode() == TIME) {
				g.drawString(" "+getPlayer(i).getScore(), 3+(1+i)*WIDTH/5, 35);
			}
			//tag winners
			if (isGameOver()) {
				for (Integer n : getWinners()) {
					if (n == i) {
						g.drawString("♛", 3+(1+i)*WIDTH/5, 24); //poorly-rendered crown
					}
				}
			}
			//draw the character's current state in box
			draw(getPlayer(i), (1+i)*WIDTH/5-19, 18, g);
			//draw the reload bar
			if (getPlayer(i).getReload() > 0) {
				g.setColor(Color.RED);
				g.fillRect((1+i)*WIDTH/5-19, 38, (16*getPlayer(i).getReload())/getPlayer(i).getShotDelay(), 2);
			}
			if (getPlayer(i).getPowerup() > 0) {
				g.setColor(Color.BLACK);
				String powername = "";
				if (getPlayer(i).getPowerup() == Item.BIG) powername = "Gigantism";
				if (getPlayer(i).getPowerup() == Item.MINI) powername = "Dwarfism";
				if (getPlayer(i).getPowerup() == Item.DJUMP) powername = "Air jump";
				if (getPlayer(i).getPowerup() == Item.SPEED) powername = "Fast-Forward";
				if (getPlayer(i).getPowerup() == Item.SSHOT) powername = "Bullet fever";
				if (getPlayer(i).getPowerup() == Item.CHANGE) {
					if (getPlayer(i).getPowerupVar() == Warehouse.LIZARD) powername = "Reptilia";
					if (getPlayer(i).getPowerupVar() == Warehouse.SLIME) powername = "Fluidity";
					if (getPlayer(i).getPowerupVar() == Warehouse.CAPTAIN) powername = "Disposability";
					if (getPlayer(i).getPowerupVar() == Warehouse.MARINE) powername = "Cybernetics";
					if (getPlayer(i).getPowerupVar() == Warehouse.ROBOT) powername = "Ninjitsu";
					if (getPlayer(i).getPowerupVar() == Warehouse.SCIENTIST) powername = "Engineering";
				}
				if (getPlayer(i).getPowerup() == Item.HYPER) powername = "Overcharge";
				g.drawString(powername, (1+i)*WIDTH/5-27, 56);
			}
		}
		if (getMode() == TIME) {
			//TODO: Adjust font and centering
			g.setColor(Color.BLACK);
			g.fillRoundRect(WIDTH/2-25, 10, 50, 35, 10, 10);
			g.setColor(Color.WHITE);
			int sec = getTimeLeft()/50; //TODO: Keep this accurate to the frame rate
			int min = sec/60;
			sec %= 60;
			g.drawString(min+":"+sec, WIDTH/2-15, 35);
		}
	}

	/**
	 * Draw an actor to the designated graphics object
	 * 
	 * @param a
	 * 		Actor to be drawn
	 * @param g
	 * 		graphics object to draw through
	 */
	private void draw(Actor a, Graphics g) {
		//don't draw dead guys
		if (a.isDead()) return;
		//if (a.isNoP()) return;

		//fall through to position drawing method
		draw(a, (int)a.getLeftEdge(), (int)a.getTopEdge(), g);
	}

	/**
	 * Draw an actor to the designated graphics object
	 * TODO: Have a better alternative than this
	 * 
	 * @param a
	 * 		Actor to be drawn
	 * @param x
	 * 		x position of the top left corner
	 * @param y
	 * 		y position of the top left corner
	 * @param g
	 * 		graphics object to draw through
	 */
	private void draw(Actor a, int x, int y, Graphics g) {
		//TODO: Make this draw an image with transparency instead

		//respawn blink
		if (a.isArmored() && a.getDeadTime() % 8 < 4) return;

		//respawn timer
		if (a.isDead()) {
			g.setColor(Color.GRAY);
			g.fillArc(x-1, y-1, a.getW()+2, a.getH()+2, 90, (-360*a.getDeadTime())/a.getSpawnTime()-90);
			return;
		}

		//temporary shape
		if (a.getDir() > 0)
			Wardrobe.drawChar(g, x+8, y+8, a.getSkin(), a.getFrame());
		else
			Wardrobe.drawCharFlip(g, x+8, y+8, a.getSkin(), a.getFrame());
	}

	/**
	 * Draw a land to the designated graphics object
	 * 
	 * @param l
	 * 		Land to be drawn
	 * @param g
	 * 		graphics object to draw through
	 */
	private void draw(Land l, Graphics g) {
		//TODO: Draw composite images instead

		//abandon drawing
		if (l.isHatch() && !isControl()) { //disabled toggles
			return;
		}
		else if (l.isNHatch() && isControl()) { //disabled negative toggles
			return;
		}

		//pick colors
		if (l.isColor()) { //painted colors
			Color c[] = {Color.ORANGE, Color.GREEN, Color.BLUE, Color.DARK_GRAY, Color.MAGENTA, Color.BLACK};
			g.setColor(c[l.getVar()]);
		}
		else if (l.isWarp() && isReady()) { //yellow warps
			g.setColor(Color.YELLOW);
		}
		else if (l.isChar()) { //color-coded player blocks
			Color c[] = {Color.ORANGE, Color.GREEN, Color.BLUE, Color.DARK_GRAY, Color.MAGENTA, Color.BLACK};
			g.setColor(c[l.getVar()]);
		}
		else if (l.isDanger()) { //red danger
			g.setColor(Color.RED);
		}
		else if (l.isSlip()) { //blue ice
			g.setColor(Color.BLUE);
		}
		else if (l.isBounce()) { //green springs
			g.setColor(Color.GREEN);
		}
		else if (l.isPlatform() || l.isSolid()) { //normal black
			g.setColor(Color.BLACK);
		}
		else { //useless gray
			g.setColor(Color.LIGHT_GRAY);
		}

		//pick style
		if (l.isBounce() && l.isPlatform() && !l.isSolid()) {
			g.drawRoundRect((int)l.getLeftEdge(), (int)l.getTopEdge(), l.getW(), l.getH(), 12, 12);
		}
		else if (l.isBounce()) {
			g.fillRoundRect((int)l.getLeftEdge(), (int)l.getTopEdge(), l.getW(), l.getH(), 12, 12);
		}
		else if (l.isSolid() || l.isColor()) {
			g.fillRect((int)l.getLeftEdge(), (int)l.getTopEdge(), l.getW(), l.getH());
		}
		else {
			g.drawRect((int)l.getLeftEdge(), (int)l.getTopEdge(), l.getW(), l.getH());
		}
		//VERY hackish temporary "move" indicators
		if (l.isMove()) {
			int y = (int)l.getTopEdge()-2;
			int sx = (int)l.getLeftEdge();
			int ex = (int)l.getLeftEdge()+l.getW()*3/4;
			for (int x = sx; x <= ex; x += l.getW()/4) {
				int x2 = x+l.getW()/8;
				g.drawLine(x+l.getW()/16, y, x2+l.getW()/16, y);				
			}
		}
		//text for mode changers
		if (l.isOption() && l.getVar() == 0) {
			g.setColor(Color.BLACK);
			if (getNextMode() == STOCK) g.drawString("Stock", (int)l.getHCenter()-15, (int)l.getVCenter()+4);
			if (getNextMode() == TIME) g.drawString("Time", (int)l.getHCenter()-14, (int)l.getVCenter()+4);
		}
		//text for value changers
		else if (l.isOption() && l.getVar() == 1) {
			g.setColor(Color.BLACK);
			String s = "";
			if (getNextMode() == STOCK) s = "x"+getStock();
			if (getNextMode() == TIME) s = ""+getTime()/(60*50)+":"+(getTime()/500)%6+""+(getTime()/50)%10;
			g.drawString(s, (int)l.getHCenter()-12, (int)l.getVCenter()+4);
		}
	}

	/**
	 * Draw a shot to the designated graphics object
	 * 
	 * @param s
	 * 		Shot to be drawn
	 * @param g
	 * 		graphics object to draw through
	 */
	private void draw(Shot s, Graphics g) {
		if (s.getSkin() < Warehouse.CHAR_NUM) {
			if (s.getVx() > 0)
				Wardrobe.drawShot(g, (int)s.getHCenter(), (int)s.getVCenter(), s.getSkin(), (getFrameNumber()/10)%4);
			else
				Wardrobe.drawShotFlip(g, (int)s.getHCenter(), (int)s.getVCenter(), s.getSkin(), (getFrameNumber()/10)%4);
		}
		else {
			g.setColor(Color.RED);
			g.fillRect((int)s.getLeftEdge(), (int)s.getTopEdge(), s.getW(), s.getH());
		}
	}

	/**
	 * Draw a powerup to the designated graphics object
	 * 
	 * @param p
	 * 		Powerup to be drawn
	 * @param g
	 * 		graphics object to draw through
	 */
	private void draw(Item p, Graphics g) {
		/*g.setColor(Color.YELLOW);
		g.fillRect((int)p.getLeftEdge(), (int)p.getTopEdge(), p.getW(), p.getH());
		g.setColor(Color.BLACK);
		String name = "";
		if (p.getType() == Item.BIG) name = "G";
		if (p.getType() == Item.MINI) name = "D";
		if (p.getType() == Item.DJUMP) name = "J";
		if (p.getType() == Item.SPEED) name = "F";
		if (p.getType() == Item.SSHOT) name = "B";
		if (p.getType() == Item.CHANGE) name = "C";
		if (p.getType() == Item.HYPER) name = "O";
		if (p.getType() == Item.LIFE) name = "+";
		g.drawString(name, (int)p.getLeftEdge()+3, (int)p.getBottomEdge()-3);*/
		Wardrobe.drawPowerup(g, (int)p.getHCenter(), (int)p.getVCenter(), p.getSkin(), 0);
	}

	/**
	 * Draw an effect to the designated graphics object
	 * 
	 * @param e
	 * 		Effect to be drawn
	 * @param g
	 * 		graphics object to draw through
	 */
	private void draw(Effect e, Graphics g) {
		//TODO: Fill out
	}
}
