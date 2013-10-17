package game;

/**
 * Object for storing player data
 * 
 * @author Jacob Charles
 *
 */
public class Player {
	private Actor avatar;
	private int lives;
	private int score;
	private int number;
	
	/*Getters and setters for data*/
	public Actor getAvatar() {
		return avatar;
	}
	public void setAvatar(Actor avatar) {
		this.avatar = avatar;
	}
	public int getLives() {
		return lives;
	}
	public void setLives(int lives) {
		this.lives = lives;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
}
