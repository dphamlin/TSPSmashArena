package game;
import java.io.*;

public class SoundBank {
	
	//sounds to be usable
	public static final int PICKUP = 0;
	public static final int BOOM = 1;
	public static final int DIE = 2;
	public static final int BOUNCE = 3;
	public static final int PIPE = 4;
	public static final int SPAWN = 5;
	//per-character sounds
	public static final int SHOOT = 6;
	public static final int JUMP = 6+Warehouse.CHAR_NUM;
	public static final int N_SAMPLES = 6; //total number

	// Populate with file names
	private static String[] filenameList =
		{"sound/pickup.wav", "sound/skid5.wav", "sound/skid6.wav",
		"sound/soft_hit2.wav", "sound/pipe2.wav", "sound/spawn.wav"};
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
