package game;

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
		//TODO: Clear the panel to color
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
		//TODO: Draw a circle of the Actor
	}
	
	/**
	 * Draw a land to the designated graphics object
	 * @param l
	 * 		Land to be drawn
	 * @param g
	 * 		graphics object to draw through
	 */
	public void draw(Land l, Graphics g) {
		//TODO: Draw a rectangle of the Land
	}
}
