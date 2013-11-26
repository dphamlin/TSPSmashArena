package game;
import javafx.scene.media.*;
import java.io.*;
import javafx.embed.swing.*;

public class MusicBank {

	public static String fileList[] = {""};
	public static MusicThread currentTrack = null;
	public static Media[] mediaList;
	
	// Static init(), as for the Soundbank
	public static void init() {
		new JFXPanel(); // Apparently, this is needed to prevent IllegalStateException
		mediaList = new Media[fileList.length];
		for (int i=0;i<fileList.length;i++)
			mediaList[i] = new Media(new File(fileList[i]).toURI().toString());
	}
	
	// Stops current track, if necessary
	public static void play(int index) {
		if (index < 0 || index >= mediaList.length)
			return;
		
		if (currentTrack == null) { // Nothing played yet, so start thread
			currentTrack = new MusicThread(mediaList[index]);
			new Thread(currentTrack).start();
		}
		else { // Track already running; end before starting new track
			currentTrack.getMediaPlayer().stop();
			currentTrack.setMediaPlayer(new MediaPlayer(mediaList[index]));
			currentTrack.getMediaPlayer().setCycleCount(MediaPlayer.INDEFINITE);
			currentTrack.getMediaPlayer().play();
		}
		
	}
	
	public static void stop() {
		if (currentTrack != null) {
			currentTrack.getMediaPlayer().stop();
		}
	}
}
