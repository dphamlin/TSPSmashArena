package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Wardrobe {

	//images to load up
	public static Image c = null; //characters
	public static Image p = null; //powerups
	public static Image t = null; //tilesets
	public static Image b = null; //backgrounds
	
	/**
	 * Load the internal resources
	 */
	public static void init() {
		try {
		    c = ImageIO.read(new File("character.png"));
		    p = ImageIO.read(new File("powerup.png"));
		    //TODO: Load up the rest of the images
		} catch (IOException e) {
			System.out.println("Failed to load images.");
		}
	}
	
	/**
	 * Cut a sprite from the sheet and draw it
	 * 
	 * @param g
	 * 		the graphics object for drawing
	 * @param x
	 * 		the x position to draw at
	 * @param y
	 * 		the y position to draw at
	 * @param skin
	 * 		which character to use
	 * @param frame
	 * 		which frame to draw from
	 */
	public static void drawChar(Graphics g, int x, int y, int skin, int frame) {
		g.drawImage(c, x-16, y-8, x+16, y+8, frame*32, skin*16, frame*32+32, skin*16+16, null);
	}
	
	/**
	 * Cut a sprite from the sheet and draw it flipped
	 * 
	 * @param g
	 * 		the graphics object for drawing
	 * @param x
	 * 		the x position to draw at
	 * @param y
	 * 		the y position to draw at
	 * @param skin
	 * 		which character to use
	 * @param frame
	 * 		which frame to draw from
	 */
	public static void drawCharFlip(Graphics g, int x, int y, int skin, int frame) {
		g.drawImage(c, x+16, y-8, x-16, y+8, frame*32, skin*16, frame*32+32, skin*16+16, null);
	}
	
	/**
	 * Cut a sprite from the sheet and draw it
	 * 
	 * @param g
	 * 		the graphics object for drawing
	 * @param x
	 * 		the x position to draw at
	 * @param y
	 * 		the y position to draw at
	 * @param skin
	 * 		which character to use
	 * @param frame
	 * 		which frame to draw from
	 */
	public static void drawShot(Graphics g, int x, int y, int skin, int frame) {
		g.drawImage(c, x-16, y-8, x+16, y+8, (10+frame)*32, skin*16, (10+frame)*32+32, skin*16+16, null);
	}
	
	/**
	 * Cut a sprite from the sheet and draw it flipped
	 * 
	 * @param g
	 * 		the graphics object for drawing
	 * @param x
	 * 		the x position to draw at
	 * @param y
	 * 		the y position to draw at
	 * @param skin
	 * 		which character to use
	 * @param frame
	 * 		which frame to draw from
	 */
	public static void drawShotFlip(Graphics g, int x, int y, int skin, int frame) {
		g.drawImage(c, x+16, y-8, x-16, y+8, (10+frame)*32, skin*16, (10+frame)*32+32, skin*16+16, null);
	}
	
	/**
	 * Cut a sprite from the sheet and draw it
	 * 
	 * @param g
	 * 		the graphics object for drawing
	 * @param x
	 * 		the x position to draw at
	 * @param y
	 * 		the y position to draw at
	 * @param skin
	 * 		which powerup to use
	 * @param frame
	 * 		which frame to draw from
	 */
	public static void drawPowerup(Graphics g, int x, int y, int skin, int frame) {
		g.drawImage(p, x-8, y-8, x+8, y+8, frame*16, skin*16, frame*16+16, skin*16+16, null);
	}
}
