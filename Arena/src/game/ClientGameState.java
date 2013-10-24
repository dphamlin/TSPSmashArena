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
		g.fillRect(0, 0, WIDTH, HEIGHT);
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
	 * Draw an actor to the designated graphics object
	 * 
	 * @param a
	 * 		Actor to be drawn
	 * @param g
	 * 		graphics object to draw through
	 */
	private void draw(Actor a, Graphics g) {
		//TODO: Make this draw an image with transparency
		if (a.isDead()) return;
		g.setColor(Color.BLACK);
		g.fillOval(a.getLeftEdge(), a.getTopEdge(), 16, 16);
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
		//TODO: Draw an image of some kind instead
		g.setColor(Color.BLACK);
		if (l.isSolid()) {
			g.fillRect(l.getLeftEdge(), l.getTopEdge(), l.getW()+1, l.getH()+1);
		}
		else {
			g.drawRect(l.getLeftEdge(), l.getTopEdge(), l.getW()+1, l.getH()+1);
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
		g.fillRect(s.getLeftEdge(), s.getTopEdge(), s.getW()+1, s.getH()+1);
	}
}
