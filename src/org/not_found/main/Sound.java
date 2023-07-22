package org.not_found.main;

import java.net.MalformedURLException;
import java.net.URL;
import java.io.File;

import javax.sound.sampled.*;

public class Sound {
	Clip clip;
	URL soundURL[] = new URL[12];
	public int selectedTrack;
	FloatControl control;
	int volumeScale = 3;
	float volume;
	
	@SuppressWarnings("deprecation")
	public Sound() {
		try {
			soundURL[0] = new File(Main.tempDir +"/res/sound/music/Espionage.wav").toURL(); //main menu
			soundURL[1] = new File(Main.tempDir +"/res/sound/music/JEALOUS.wav").toURL(); //in game
			soundURL[2] = new File(Main.tempDir +"/res/sound/music/intense-moments.wav").toURL(); //pause menu
			soundURL[3] = new File(Main.tempDir +"/res/sound/sfx/door.wav").toURL();
			soundURL[4] = new File(Main.tempDir +"/res/sound/sfx/key.wav").toURL();
			soundURL[5] = new File(Main.tempDir +"/res/sound/sfx/fanfare.wav").toURL();
			soundURL[6] = new File(Main.tempDir +"/res/sound/sfx/hitmonster.wav").toURL();
			soundURL[7] = new File(Main.tempDir +"/res/sound/sfx/receivedamage.wav").toURL();
			soundURL[8] = new File(Main.tempDir +"/res/sound/sfx/swingweapon.wav").toURL();
			soundURL[9] = new File(Main.tempDir +"/res/sound/sfx/powerup.wav").toURL();
			soundURL[10] = new File(Main.tempDir +"/res/sound/sfx/cursor.wav").toURL();
		} catch(MalformedURLException e) {
			
		}
	}
	
	public URL[] returnArray() {
		return soundURL;
	}
	
	public void setFile(int i) {
		try {
			selectedTrack = i;
			AudioInputStream soundIn = AudioSystem.getAudioInputStream(soundURL[selectedTrack]);
			AudioFormat format = soundIn.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			clip = (Clip)AudioSystem.getLine(info);
			clip.open(soundIn);
			control = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
			checkVolume();
			
		} catch(Exception e) {
			
		}
	}
	public void play() {
		if(clip.isOpen()) {
			clip.start();
		}
		
	}
	public void loop() {
		if(clip.isOpen()) {
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
		
	}
	public void stop() {
		if(clip.isOpen()) {
			clip.stop();
		}
	}
	
	public boolean isPlaying() {
		boolean playing = false;
		if(clip != null) {
			if(clip.isOpen()) {
				playing = clip.isRunning();
			}
		}
		
		return playing;
	}
	
	public int getCurrentFile() {
		return selectedTrack;
	}
	
	public void checkVolume() {
		switch(volumeScale) {
		case 0: volume = -80f; break;
		case 1: volume = -20f; break;
		case 2: volume = -12f; break;
		case 3: volume = -5f; break;
		case 4: volume = 1f; break;
		case 5: volume = 6f; break;
		}
		control.setValue(volume);
	}
}