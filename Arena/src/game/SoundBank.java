package game;
import java.io.*;

public class SoundBank {
	
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
