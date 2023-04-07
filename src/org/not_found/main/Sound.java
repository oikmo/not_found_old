package org.not_found.main;

import java.net.URL;
import javax.sound.sampled.*;

public class Sound {
	Clip clip;
	URL soundURL[] = new URL[12];
	public int selectedTrack;
	
	public Sound() {
		soundURL[0] = getClass().getResource("/res/sound/music/Espionage.wav"); //main menu
		soundURL[1] = getClass().getResource("/res/sound/music/JEALOUS.wav"); //in game
		soundURL[2] = getClass().getResource("/res/sound/music/intense-moments.wav"); //pause menu
		soundURL[3] = getClass().getResource("/res/sound/sfx/door.wav");
		soundURL[4] = getClass().getResource("/res/sound/sfx/key.wav");
		soundURL[5] = getClass().getResource("/res/sound/sfx/fanfare.wav");
		soundURL[6] = getClass().getResource("/res/sound/sfx/hitmonster.wav");
		soundURL[7] = getClass().getResource("/res/sound/sfx/receivedamage.wav");
		soundURL[8] = getClass().getResource("/res/sound/sfx/swingweapon.wav");
		soundURL[9] = getClass().getResource("/res/sound/sfx/powerup.wav");
		//soundURL[11] = getClass().getResource("/res/sound/sfx/footsteps.wav");
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
	
	public int getCurrentFile() {
		return selectedTrack;
	}
}
