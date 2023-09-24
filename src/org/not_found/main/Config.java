package org.not_found.main;

import org.not_found.toolbox.StringTranslate;

public class Config {
	GamePanel gp;
	StringTranslate trans = StringTranslate.getInstance();
	
	public Config(GamePanel gp) {
		this.gp = gp;
	}

	public void saveConfig() {
		trans.setValue("sound.SFXVolume", String.valueOf(gp.se.volumeScale));	
		trans.setValue("sound.musicVolume", String.valueOf(gp.music.volumeScale));
	}
	
	public void setValue(String key, String value) {
		trans.setValue(key, value);	
	}
	
	public String getValue(String key) {
		return trans.translateKey(key);
	}
	
	public void loadConfig() {
		int yeah = Integer.parseInt(getValue("sound.musicVolume"));
		gp.music.volumeScale = yeah;
		gp.Tmusic.volumeScale = yeah;
		gp.Pmusic.volumeScale = yeah;
		gp.se.volumeScale = Integer.parseInt(getValue("sound.SFXVolume"));
	}
	
}
