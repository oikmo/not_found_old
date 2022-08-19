package main;

import java.net.URL;

import javax.sound.sampled.*;

public class Sound {
	Clip clip;
	URL soundURL[] = new URL[5];
	
	public Sound() {
		soundURL[0] = getClass().getResource("/sound/door.wav");
		soundURL[1] = getClass().getResource("/sound/key.wav");
		soundURL[2] = getClass().getResource("/sound/JEALOUS.wav");
		soundURL[3] = getClass().getResource("/sound/3301.wav");
	}
	
	public void setFile(int i) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
		} catch(Exception e) {
			
		}
	}
	public void play() {
		clip.start();
	}
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	public void stop() {
		clip.stop();
	}
}
