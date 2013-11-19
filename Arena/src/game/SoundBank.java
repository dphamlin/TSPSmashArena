package game;
import java.io.*;

public class SoundBank {
	
	//sounds to be usable
	public static final int JUMP = 0;
	public static final int SHOOT = 1;
	public static final int DIE = 2;
	public static final int PICKUP = 3;
	public static final int PIPE = 4;
	public static final int BOUNCE = 5;
	public static final int BOOM = 6;
	public static final int SPAWN = 7;
	public static final int N_SAMPLES = 0;
	
	// Populate with file names
	private static String[] filenameList = {};
	private static File[] fileList;
	
	// Call this before you try to use any sounds
	public static void init() {
		fileList = new File[filenameList.length];
		for (int i=0;i<filenameList.length;i++) {
			fileList[i] = new File(filenameList[i]);
		}
	}
	
	// Just play sound by index
	public static void play(int index) {
		if (index < 0 || index >= fileList.length) {
			return;
		}
		
		try {
			new Thread(new SoundThread(fileList[index])).start();
		} catch (Exception e) {
			return;
		}
	}
}
