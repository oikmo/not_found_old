package org.not_found.achievements;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.not_found.main.*;
import org.not_found.toolbox.UtilityBox;

public class AchieveManager {
	
	public static HashMap<String, Achievement> achievements = new HashMap<String, Achievement>();
	
	public static void setAchievements(GamePanel gp) {
		try {
			achievements.put("door-open", new Achievement(UtilityBox.scaleImage(ImageIO.read(new File(Main.tempDir + "/res/objects/door.png")), gp.tileSize, gp.tileSize), "opened a door!"));
			achievements.put("key-pickup", new Achievement(UtilityBox.scaleImage(ImageIO.read(new File(Main.tempDir + "/res/objects/door.png")), gp.tileSize, gp.tileSize), "picked up a key!"));
		} catch (IOException e) {
			System.err.println("[ERROR] Achievement icons could NOT be loaded!");
		}
		
	}
	
}
