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
		drawLevel(g);
		drawFighters(g);
		drawBullets(g);
		//TODO: Draw items and special effects
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
	 * Draw character statuses
	 * 
	 * @param g
	 * 		graphics object to draw through
	 */
	private void drawStatus(Graphics g) {
		for (int i = 0; i < getNumberOfPlayers(); i++) {
			g.setColor(Color.BLACK);
			g.fillRoundRect(40+i*WIDTH/4, 10, 55, 35, 10, 10);
			//player is selected, all good
			if (!getPlayer(i).isNoP()) {
				g.setColor(Color.WHITE);
				if (getMode() == STOCK) {
					g.drawString("x"+getPlayer(i).getLives(), 70+i*WIDTH/4, 35);
				}
				if (getMode() == TIME) {
					g.drawString(" "+getPlayer(i).getScore(), 70+i*WIDTH/4, 35);
				}
				//tag winners
				if (isGameOver()) {
					for (Integer n : getWinners()) {
						if (n == i) {
							g.drawString("♛", 70+i*WIDTH/4, 24); //poorly-rendered crown
						}
					}
				}
				//draw the character's current state in box
				draw(getPlayer(i), 48+i*WIDTH/4, 18, g);
			}
			else drawSel(getPlayer(i), 60+i*WIDTH/4, 18, g);
			//draw the reload bar
			if (getPlayer(i).getReload() > 0) {
				g.setColor(Color.RED);
				g.fillRect(48+i*WIDTH/4, 38, (16*getPlayer(i).getReload())/getPlayer(i).getShotDelay(), 2);
			}
		}
		if (getMode() == TIME) {
			//TODO: Adjust font and centering
			g.setColor(Color.BLACK);
			g.fillRoundRect(WIDTH/2-20, 10, 50, 35, 10, 10);
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
		draw(a, (int)a.getLeftEdge(), (int)a.getTopEdge(), g);
	}

	/**
	 * Draw an actor to the designated graphics object
	 * 
	 * @param a
	 * 		Actor to be drawn
	 * @param g
	 * 		graphics object to draw through
	 */
	private void draw(Actor a, int x, int y, Graphics g) {
		//TODO: Make this draw an image with transparency instead

		//respawn blink
		if (a.isArmored() && a.getDeadTime() % 8 < 4) return;

		//temporary colors
		Color c[] = {Color.LIGHT_GRAY, Color.GREEN, Color.BLUE, Color.YELLOW, Color.DARK_GRAY, Color.MAGENTA, Color.GRAY};
		g.setColor(c[a.getSkin()]);

		//respawn timer
		if (a.isDead()) {
			g.fillArc(x-1, y-1, a.getW()+2, a.getH()+2, 90, (-360*a.getDeadTime())/a.getSpawnTime()-90);
			return;
		}

		//temporary shape
		g.fillOval(x, y, a.getW(), a.getH());
		g.fillRect((int)x+a.getW()/2, y, -a.getW()*a.getDir()/2, a.getH());
		g.fillRect(x, y, a.getW()*a.getDir()/2, a.getH());
	}

	/**
	 * Temporary method for drawing the selection process
	 */
	private void drawSel(Actor a, int x, int y, Graphics g) {
		//TODO: Make this draw a portrait instead

		//temporary colors
		Color c[] = {Color.BLACK, Color.GREEN, Color.BLUE, Color.YELLOW, Color.DARK_GRAY, Color.MAGENTA, Color.GRAY};
		g.setColor(c[a.getSkin()]);

		//temporary shape
		g.fillOval(x, y, 16, 16);
		g.fillRect(x, y, 8, 16);
		//text bits
		g.setColor(Color.WHITE);
		if (a.getSkin() > Warehouse.NOP+1) g.drawString("<", x-15, y+14);
		if (a.getSkin() < Warehouse.CHAR_NUM) g.drawString(">", x+23, y+14);
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

		//pick colors
		if (l.isHatch() && !isControl()) { //faded toggles
			g.setColor(Color.LIGHT_GRAY);
		}
		else if (l.isNHatch() && isControl()) { //faded negative toggles
			g.setColor(Color.LIGHT_GRAY);
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
		else { //normal black
			g.setColor(Color.BLACK);
		}

		//pick style
		if (l.isBounce() && l.isPlatform()) {
			g.drawRoundRect((int)l.getLeftEdge(), (int)l.getTopEdge(), l.getW(), l.getH(), 12, 12);
		}
		else if (l.isBounce()) {
			g.fillRoundRect((int)l.getLeftEdge(), (int)l.getTopEdge(), l.getW(), l.getH(), 12, 12);
		}
		else if (l.isSolid()) {
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
		g.setColor(Color.RED);
		g.fillRect((int)s.getLeftEdge(), (int)s.getTopEdge(), s.getW(), s.getH());
	}
}
