package game;
import javafx.scene.media.*;
import java.io.*;
import javafx.embed.swing.*;

public class MusicBank {

	//tracks
	public static final int HOLODECK = 0;
	public static final int PLANET = 1;
	public static final int FACTORY = 2;
	public static final int SNOW = 3;

	//load songs
	public static String fileList[] =
		{"music/Dub Feral.mp3", "music/Cortosis.mp3",
		"music/Junkyard Tribe.mp3", "music/.mp3"};
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
