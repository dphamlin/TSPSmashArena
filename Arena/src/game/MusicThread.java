package game;
import javafx.scene.media.*;

public class MusicThread implements Runnable {
	
	private Media media;
	private MediaPlayer mediaPlayer;
	
	MusicThread(Media media) {
		try {
			setMedia(media);
			setMediaPlayer(new MediaPlayer(media));
			getMediaPlayer().setCycleCount(MediaPlayer.INDEFINITE);
		}
		catch (Exception e) {
			setMedia(null);
			setMediaPlayer(null);
		}
	}
	
	public void setMedia(Media media) {
		this.media = media;
	}
	
	public Media getMedia() {
		return this.media;
	}
	
	public void setMediaPlayer(MediaPlayer mediaPlayer) {
		this.mediaPlayer = mediaPlayer;
	}
	
	public MediaPlayer getMediaPlayer() {
		return this.mediaPlayer;
	}
	
	public void run() {
		try {
			getMediaPlayer().play();
		}
		catch (Exception e) {
			
		}
	}
	
	

}
