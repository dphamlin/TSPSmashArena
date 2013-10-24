package game;

public class StopWatch {
	private int loopTime;
	private long loopEnd;
	private long loopNumber = 0;
	
	/**
	 * Create a stop watch with the designated loop length
	 * 
	 * @param loopTime
	 * 		expected length of a loop in milliseconds
	 */
	public StopWatch(int loopTime) {
		this.loopTime = loopTime;
		loopNumber++;
	}
	
	/**
	 * Log start time of a loop
	 */
	public void loopStart() {
		loopEnd = System.currentTimeMillis()+loopTime;
	}
	
	/**
	 * Rest at the end of a loop
	 */
	public void loopRest() {
		long tillEnd = loopEnd-System.currentTimeMillis();
		if (tillEnd > 0) {
			try {
				Thread.sleep(tillEnd);
			} catch (InterruptedException e) {
				// Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return the current loopNumber
	 */
	public long getLoopNumber() {
		return loopNumber;
	}

	/**
	 * Reset the loopNumber
	 */
	public void resetLoopNumber() {
		this.loopNumber = 0;
	}
}
