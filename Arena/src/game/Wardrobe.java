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
	public static Image e = null; //effects
	public static Image t = null; //tilesets
	public static Image b = null; //backgrounds

	/**
	 * Load the internal resources
	 */
	public static void init() {
		try {
			//TODO: Load from "image" folder, which must be packaged with the .jar
			c = ImageIO.read(new File("image/character.png"));
			p = ImageIO.read(new File("image/powerup.png"));
			e = ImageIO.read(new File("image/effect.png"));
			//TODO: Load tilesets
			//TODO: load backgrounds
		} catch (IOException e) {
			System.out.println("Failed to load images.");
		}
	}

	/**
	 * Draw a background from the set over the screen
	 * 
	 * @param g
	 * 		the graphics objects for drawing
	 * @param skin
	 * 		the background to draw
	 */
	public static void drawBackground(Graphics g, int skin) {
		g.drawImage(b, 0, 0, 0, 480*skin, null);
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
	 * @param scale
	 * 		size multiplier
	 */
	public static void drawChar(Graphics g, int x, int y, int skin, int frame, double scale) {
		g.drawImage(c, x-(int)(16*scale), y-(int)(8*scale), x+(int)(16*scale), y+(int)(8*scale),
				frame*32, skin*16, frame*32+32, skin*16+16, null);
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
	 * @param scale
	 * 		size multiplier
	 */
	public static void drawCharFlip(Graphics g, int x, int y, int skin, int frame, double scale) {
		g.drawImage(c, x+(int)(16*scale), y-(int)(8*scale), x-(int)(16*scale), y+(int)(8*scale),
				frame*32, skin*16, frame*32+32, skin*16+16, null);
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
	 * @param scale
	 * 		size multiplier
	 */
	public static void drawShot(Graphics g, int x, int y, int skin, int frame, double scale) {
		g.drawImage(c, x-(int)(16*scale), y-(int)(8*scale), x+(int)(16*scale), y+(int)(8*scale),
				(10+frame)*32, skin*16, (10+frame)*32+32, skin*16+16, null);
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
	 * @param scale
	 * 		size multiplier
	 */
	public static void drawShotFlip(Graphics g, int x, int y, int skin, int frame, double scale) {
		g.drawImage(c, x+(int)(16*scale), y-(int)(8*scale), x-(int)(16*scale), y+(int)(8*scale),
				(10+frame)*32, skin*16, (10+frame)*32+32, skin*16+16, null);
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
	public static void drawEffect(Graphics g, int x, int y, int skin, int frame) {
		g.drawImage(e, x-8, y-8, x+8, y+8, frame*16, skin*16, frame*16+16, skin*16+16, null);
	}

	/**
	 * Draw a tiled platform with a tileset
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
	public static void drawLand(Graphics g, int x1, int y1, int x2, int y2, int skin) {
		//top-left
		g.drawImage(t, x1, y1, x1, y1+8, 0, skin*24, 8, skin*24+8, null);
		for (int x = x1+8; x < x2-8; x+= 8) {
			//top
			g.drawImage(t, x, y1, x+8, y1+8, 8, skin*24, 16, skin*24+8, null);
		}
		//top-right
		g.drawImage(t, x2-8, y1, x2, y1+8, 16, skin*24, 24, skin*24+8, null);
		
		for (int y = y1+8; y < x2-8; y+= 8) {
			//left
			g.drawImage(t, x1, y, x1, y+8, 0, skin*24+8, 8, skin*24+16, null);
			for (int x = x1+8; x < x2-8; x+= 8) {
				//middle
				g.drawImage(t, x, y, x+8, y+8, 8, skin*24+8, 16, skin*24+16, null);
			}
			//right
			g.drawImage(t, x2-8, y, x2, y+8, 16, skin*24+8, 24, skin*24+16, null);
		}
		
		//bottom-left
		g.drawImage(t, x1, y2-8, x1, y2, 0, skin*24+16, 8, skin*24+24, null);
		for (int x = x1+8; x < x2-8; x+= 8) {
			//bottom
			g.drawImage(t, x, y2-8, x+8, y2, 8, skin*24+16, 16, skin*24+24, null);
		}
		//bottom-right
		g.drawImage(t, x2-8, y2-8, x2, y2, 16, skin*24+16, 24, skin*24+24, null);
	}
}
