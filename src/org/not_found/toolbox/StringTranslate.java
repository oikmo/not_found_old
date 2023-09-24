package org.not_found.toolbox;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.not_found.main.Main;

public class StringTranslate {
	private static StringTranslate instance = new StringTranslate();
	private Properties translateTable = new Properties();

	private StringTranslate() {
		File file = new File(Main.gameDir + "config.txt");
		if(!file.exists()) {
			try {
				file.createNewFile();
				setValue("sound.SFXVolume", String.valueOf(2));	
				setValue("sound.musicVolume", String.valueOf(2));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			this.translateTable.load(new FileInputStream(Main.gameDir + "config.txt"));
			//this.translateTable.load(StringTranslate.class.getResourceAsStream("/lang/stats_US.lang"));
		} catch (IOException var2) {
			var2.printStackTrace();
		}

	}
	
	public void setValue(String name, String value) {
		this.translateTable.setProperty(name, value);
		try {
			this.translateTable.store(new FileOutputStream(Main.gameDir + "config.txt"), null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static StringTranslate getInstance() {
		return instance;
	}

	public String translateKey(String var1) {
		return this.translateTable.getProperty(var1, var1);
	}

	public String translateKeyFormat(String var1, Object... var2) {
		String var3 = this.translateTable.getProperty(var1, var1);
		return String.format(var3, var2);
	}

	public String translateNamedKey(String var1) {
		return this.translateTable.getProperty(var1 + ".name", "");
	}
}
