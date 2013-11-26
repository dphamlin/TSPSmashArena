package game;
import javafx.scene.media.*;

public class MusicThread implements Runnable {
	
	private Media media;
	private MediaPlayer mediaPlayer;
	
	MusicThread(Media media) {
		//new JFXPanel(); // Allows the exception to be passed without incident, but a exception-free solution is still desired.
		setMedia(media);
		setMediaPlayer(new MediaPlayer(media));
		getMediaPlayer().setCycleCount(MediaPlayer.INDEFINITE);
	}
	
	public void setMedia(Media media) {
		this.media = media;
	}
	
	public Media getMedia() {
		return this.media;
	}
	
	public void setMediaPlayer(MediaPlayer mediaPlayer) {
		//new JFXPanel();
		this.mediaPlayer = mediaPlayer;
	}
	
	public MediaPlayer getMediaPlayer() {
		return this.mediaPlayer;
	}
	
	public void run() {
		getMediaPlayer().play();
	}
	
	

}
