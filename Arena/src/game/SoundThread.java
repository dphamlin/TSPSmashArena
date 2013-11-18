package game;
import javax.sound.sampled.*;

import java.io.*;

public class SoundThread implements Runnable {
	
	
	File file;
	AudioInputStream audioInputStream;
	Clip clip;
	
	SoundThread(File file) throws Exception {
		this.file = file;
		this.audioInputStream = AudioSystem.getAudioInputStream(file);
		this.clip = AudioSystem.getClip();
		clip.open(audioInputStream);
	}
	
	public void run(){
		clip.setFramePosition(0);
		clip.start();
		while(!clip.isRunning()) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		while(clip.isRunning()) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
