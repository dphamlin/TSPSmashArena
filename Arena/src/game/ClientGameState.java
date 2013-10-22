package game;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Client implementation of the game state
 * 
 * @author Jacob Charles
 */
public class ClientGameState extends GameState {
	
	/**
	 * Draw everything
	 * 
	 * @param g
	 */
	public void draw(Graphics g) {
		//draw the background
		drawLevel(g);
		drawFighters(g);
		//draw the projectiles
	}
	
	/**
	 * Draw a background
	 * 
	 * @param g
	 * 		graphics object to draw through
	 */
	public void drawBackground(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 640, 480); //TODO: Figure out actual level width/height for painting
	}
	
	/**
	 * Draw the fighters
	 * 
	 * @param g
	 * 		graphics object to draw through
	 */
	public void drawFighters(Graphics g) {
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
	public void drawLevel(Graphics g) {
		for (Land l : getLevel()) {
			draw(l, g);
		}
	}
	
	/**
	 * Draw an actor to the designated graphics object
	 * @param a
	 * 		Actor to be drawn
	 * @param g
	 * 		graphics object to draw through
	 */
	public void draw(Actor a, Graphics g) {
		g.setColor(Color.BLACK);
		g.fillOval(a.getX(), a.getY(), 16, 16);
	}
	
	/**
	 * Draw a land to the designated graphics object
	 * @param l
	 * 		Land to be drawn
	 * @param g
	 * 		graphics object to draw through
	 */
	public void draw(Land l, Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(l.getLeftEdge(), l.getBottomEdge(), l.getW()+1, l.getH()+1);
	}
}
