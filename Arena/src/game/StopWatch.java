package game;

public class StopWatch {
	private int loopTime;
	private long loopEnd;
	
	/**
	 * Create a stop watch with the designated loop length
	 * 
	 * @param loopTime
	 * 		expected length of a loop in milliseconds
	 */
	public StopWatch(int loopTime) {
		this.loopTime = loopTime;
	}
	
	/**
	 * Log start time of a loop
	 */
	public void loopStart() {
		loopEnd = System.currentTimeMillis()+loopTime;
	}
	
	/**
	 * Pause for a time in the middle of a loop
	 * 
	 * @param m
	 * 		number of milliseconds to pause
	 */
	public void loopPause(long m) {
		try {
			//TODO: Find the best method to use for this
			Thread.sleep(m);
		} catch (InterruptedException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Rest at the end of a loop
	 */
	public void loopRest() {
		long tillEnd = loopEnd-System.currentTimeMillis();
		if (tillEnd > 0) {
			loopPause(tillEnd);
		}
	}
}
