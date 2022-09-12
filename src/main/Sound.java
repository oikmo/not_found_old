package main;

import java.net.URL;

import javax.sound.sampled.*;

public class Sound {
	Clip clip;
	URL soundURL[] = new URL[6];
	
	public Sound() {
		soundURL[0] = getClass().getResource("/sound/door.wav");
		soundURL[1] = getClass().getResource("/sound/key.wav");
		soundURL[2] = getClass().getResource("/sound/momentum(L).wav"); //in game
		soundURL[3] = getClass().getResource("/sound/undecided.wav"); //main menu
		soundURL[4] = getClass().getResource("/sound/intense-moments.wav"); //pause menu
		soundURL[5] = getClass().getResource("/sound/theBoys.wav");
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
