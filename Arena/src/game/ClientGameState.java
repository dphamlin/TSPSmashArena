package game;

/**
 * Client implementation of the game state
 * 
 * @author Jacob Charles
 */
import java.awt.Color;
import java.awt.Graphics;

public class ClientGameState extends GameState {

	//non-synched player name set
	private String[] pn = {"Player 1", "Player 2", "Player 3", "Player 4",};

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
		playSounds(); //not a draw TECHNICALLY but...
		//TODO: start music on first frame
		/*if (getFrameNumber() == 1) {
			MusicBank.stop();
			MusicBank.play(Warehouse.getMaps()[getStage()].getBgm());
		}*/
		
	}

	/**
	 * Draw a background
	 * 
	 * @param g
	 * 		graphics object to draw through
	 */
	private void drawBackground(Graphics g) {
		Wardrobe.drawBackground(g, Warehouse.getMaps()[getStage()].getBg());
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
		//draw spots for the number of players expected
		for (int i = 0; i < getMaxPlayers() || i < getNumberOfPlayers(); i++) {
			g.setColor(Color.BLACK);
			g.fillRoundRect((1+i)*WIDTH/5-27, 15, 55, 36, 10, 10);
		}
		//draw actual characters and stats
		for (int i = 0; i < getNumberOfPlayers(); i++) {
			if (getPlayer(i).isSuspend()) continue; //skip over suspended players

			g.setColor(Color.WHITE);
			if (getMode() == STOCK) {
				g.drawString("x"+getPlayer(i).getLives(), 3+(1+i)*WIDTH/5, 40);
			}
			if (getMode() == TIME) {
				g.drawString(" "+getPlayer(i).getScore(), 3+(1+i)*WIDTH/5, 40);
			}
			//draw the player's name just above
			if (i < pn.length) {
				g.setColor(Color.BLACK);
				g.fillRoundRect((1+i)*WIDTH/5-33, 0, 66, 14, 10, 10);
				g.setColor(Color.WHITE);
				g.drawString(pn[i], (1+i)*WIDTH/5-28, 11);
			}
			//draw the character's current state in box
			draw(getPlayer(i), (1+i)*WIDTH/5-11, 31, 1.0, g);
			//draw the reload bar
			if (getPlayer(i).getReload() > 0) {
				g.setColor(Color.RED);
				g.fillRect((1+i)*WIDTH/5-19, 43, (16*getPlayer(i).getReload())/getPlayer(i).getShotDelay(), 2);
			}
			//indicate the player's powerup
			if (getPlayer(i).getPowerup() > 0) {
				g.setColor(Color.BLACK);
				String powername = "";
				if (getPlayer(i).getPowerup() == Item.BIG) powername = "Gigantism";
				if (getPlayer(i).getPowerup() == Item.MINI) powername = "Dwarfism";
				if (getPlayer(i).getPowerup() == Item.DJUMP) powername = "Air Jump";
				if (getPlayer(i).getPowerup() == Item.SPEED) powername = "Fast-Forward";
				if (getPlayer(i).getPowerup() == Item.SSHOT) powername = "Hyper Shot";
				if (getPlayer(i).getPowerup() == Item.CHANGE) powername = "Transformation";
				if (getPlayer(i).getPowerup() == Item.HYPER) powername = "Invincibility";
				g.drawString(powername, (1+i)*WIDTH/5-27, 61);
			}
		}
		//crown winners
		for (Integer i : getWinners()) {
			if ((getFrameNumber()/6)%9 < 6)
				Wardrobe.drawEffect(g, 9+(1+i)*WIDTH/5, 26, 3, (getFrameNumber()/6)%9);
			else
				Wardrobe.drawEffect(g, 9+(1+i)*WIDTH/5, 26, 3, 0);
		}
		//draw the timer
		if (getMode() == TIME) {
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

		//fall through to position drawing method
		if (a.getPowerup() == Item.BIG) draw(a, (int)a.getHCenter(), (int)a.getVCenter(), 2.0, g);
		else if (a.getPowerup() == Item.MINI) draw(a, (int)a.getHCenter(), (int)a.getVCenter(), 0.5, g);
		else draw(a, (int)a.getHCenter(), (int)a.getVCenter(), 1.0, g);

		//crown a winner
		for (Integer n : getWinners()) {
			if (n == a.getId()) {
				if ((getFrameNumber()/6)%9 < 6)
					Wardrobe.drawEffect(g, (int)a.getHCenter(), (int)a.getTopEdge()-6, 3, (getFrameNumber()/6)%9);
				else
					Wardrobe.drawEffect(g, (int)a.getHCenter(), (int)a.getTopEdge()-6, 3, 0);
			}
		}
	}

	/**
	 * Draw an actor to the designated graphics object
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
	private void draw(Actor a, int x, int y, double scale, Graphics g) {

		//respawn blink
		if (!a.isDead() && a.isArmored() && a.getDeadTime() % 10 < 5) return;

		//respawn timer
		if (a.isDead()) {
			if ((a.getLives() > 0 || getMode() != STOCK) && (a.getDeadTime() <= a.getSpawnTime()-16))
			{
				g.setColor(Color.GRAY);
				g.fillArc(x-10, y-10, 20, 20, 90, 360-360*a.getDeadTime()/(a.getSpawnTime()-16));
			}
			else if (a.getLives() > 0 || getMode() != STOCK) {
				Wardrobe.drawChar(g, x, y, a.getSkin(), 5, scale);
			}
			else Wardrobe.drawChar(g, x, y, a.getSkin(), 9, scale);
			return;
		}

		//draw the regular sprite
		if (a.getDir() > 0)
			Wardrobe.drawChar(g, x, y, a.getSkin(), a.getFrame(), scale);
		else
			Wardrobe.drawCharFlip(g, x, y, a.getSkin(), a.getFrame(), scale);
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
		//abandon drawing
		if (l.isHatch() && !isControl()) { //disabled toggles
			return;
		}
		else if (l.isNHatch() && isControl()) { //disabled negative toggles
			return;
		}

		//draw the tile map
		if (l.isImage()) {
			Wardrobe.drawMisc(g, (int)l.getHCenter(), (int)l.getVCenter(), l.getSkin());
		}
		else if (l.isAnimate()) {
			Wardrobe.drawLand(g, (int)l.getLeftEdge(), (int)l.getTopEdge(),
					(int)l.getRightEdge(), (int)l.getBottomEdge(),
					l.getSkin()+(getFrameNumber()/6)%4);
		}
		else if (l.isRAnimate()){
			Wardrobe.drawLand(g, (int)l.getLeftEdge(), (int)l.getTopEdge(),
					(int)l.getRightEdge(), (int)l.getBottomEdge(),
					l.getSkin()+3-(getFrameNumber()/6)%4);
		}
		else {
			Wardrobe.drawLand(g, (int)l.getLeftEdge(), (int)l.getTopEdge(),
					(int)l.getRightEdge(), (int)l.getBottomEdge(), l.getSkin());
		}

		//text on option blocks
		g.setColor(Color.BLACK);
		if (l.isOption() && l.getVar() == 0) {//mode change
			if (getNextMode() == STOCK) g.drawString("Stock", (int)l.getHCenter()-15, (int)l.getVCenter()+4);
			if (getNextMode() == TIME) g.drawString("Time", (int)l.getHCenter()-14, (int)l.getVCenter()+4);
		}
		else if (l.isOption() && l.getVar() == 1) { //value change
			String s = "";
			if (getNextMode() == STOCK) s = "x"+getStock();
			if (getNextMode() == TIME) s = ""+getTime()/(60*50)+":"+(getTime()/500)%6+""+(getTime()/50)%10;
			g.drawString(s, (int)l.getHCenter()-14, (int)l.getVCenter()+4);
		}
	}
	/*private void draw(Land l, Graphics g) {

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
	}*/

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
			//scaling things
			double sc = 1;
			if (s.isBig()) sc = 2;
			if (s.isMini()) sc = 0.5;

			if (s.getVx() > 0)
				Wardrobe.drawShot(g, (int)s.getHCenter(), (int)s.getVCenter(), 
						s.getSkin(), 3-(s.getLifeTime()/10)%4, (float)sc);
			else
				Wardrobe.drawShotFlip(g, (int)s.getHCenter(), (int)s.getVCenter(),
						s.getSkin(), 3-(s.getLifeTime()/10)%4, (float)sc);
		}
		else if (s.getSkin() == Warehouse.EXPLOSION) {
			g.setColor(Color.RED);
			g.fillOval((int)s.getLeftEdge()-s.getW()/4, (int)s.getTopEdge()-s.getH()/4, s.getW()*3/2, s.getH()*3/2);
			g.setColor(Color.ORANGE);
			g.fillOval((int)s.getLeftEdge(), (int)s.getTopEdge(), s.getW(), s.getH());
		}
		else if (s.getSkin() == Warehouse.METEOR) {
			Wardrobe.drawMisc(g, (int)s.getHCenter(), (int)s.getVCenter(), Warehouse.ROBOT+4);
		}
		else if (s.getSkin() == Warehouse.LAVAWAVE) {
			Wardrobe.drawLand(g, (int)s.getLeftEdge(), (int)s.getTopEdge(),
					(int)s.getRightEdge(), (int)s.getBottomEdge(), Warehouse.LAVA+3-(s.getLifeTime()/6)%4);
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
		if ((p.getLifeTime()/9) % 7 > 3)
			Wardrobe.drawPowerup(g, (int)p.getHCenter(), (int)p.getVCenter(), p.getSkin(), 7-(p.getLifeTime()/9)%7);
		else
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
		if (e.getType() >= 0)
			Wardrobe.drawEffect(g, (int)e.getHCenter(), (int)e.getVCenter(), e.getSkin(), 5-e.getLife()/5);
		else
			Wardrobe.drawChar(g, (int)e.getHCenter(), (int)e.getVCenter(), e.getSkin(), 9, 1.0);
	}

	/**
	 * Play all queued sounds
	 */
	private void playSounds() {
		for (int i = -1; i < SoundBank.N_SAMPLES; i++) {
			if (readSound(i)) {
				SoundBank.play(i);
			}
		}
	}

	/**
	 * @return the array of player names
	 */
	public String[] getPlayerNames() {
		return pn;
	}

	/**
	 * Set the names of the players
	 * 
	 * @param pn
	 * 		new array of player names
	 */
	public void setPlayerNames(String[] pn) {
		this.pn = pn;
	}
}
