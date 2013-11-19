package game;
import java.io.*;

public class SoundBank {
	
	//sounds to be usable
	public static final int JUMP = 0;
	public static final int BOOM = 1;
	public static final int SHOOT = 2;
	public static final int DIE = 3;
	public static final int PICKUP = 4;
	public static final int PIPE = 5;
	public static final int BOUNCE = 6;
	public static final int SPAWN = 7;
	public static final int N_SAMPLES = 3;

	// Populate with file names
	private static String[] filenameList = {"sound/hop.wav", "sound/launch3.wav"};
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
		try {
			new Thread(new SoundThread(fileList[index])).start();
		} catch (Exception e) {
			return;
		}
	}
}
