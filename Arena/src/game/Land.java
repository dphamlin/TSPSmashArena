package game;

/**
 * Class for terrain objects
 * 
 * @author Jacob Charles
 *
 */
public class Land extends GameObject {
	private boolean solid;
	private boolean platform;
	private boolean danger;
	private boolean water;
	private boolean slip;
	
	/*land detail getter and setters*/
	public boolean isSolid() {
		return solid;
	}
	public void setSolid(boolean solid) {
		this.solid = solid;
	}
	public boolean isPlatform() {
		return platform;
	}
	public void setPlatform(boolean platform) {
		this.platform = platform;
	}
	public boolean isDanger() {
		return danger;
	}
	public void setDanger(boolean danger) {
		this.danger = danger;
	}
	public boolean isWater() {
		return water;
	}
	public void setWater(boolean water) {
		this.water = water;
	}
	public boolean isSlip() {
		return slip;
	}
	public void setSlip(boolean slip) {
		this.slip = slip;
	}
}
