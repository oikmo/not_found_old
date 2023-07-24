package org.not_found.achievements;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import org.not_found.entity.monster.*;

import org.not_found.main.*;
import org.not_found.toolbox.UtilityBox;

public class AchieveManager {
	
	public static HashMap<String, Achievement> achievements = new HashMap<String, Achievement>();
	
	public static void setAchievements(GamePanel gp) {
		try {
			achievements.put("door-open", new Achievement(UtilityBox.scaleImage(ImageIO.read(new File(Main.gameDir + "/res/objects/door.png")), gp.tileSize, gp.tileSize), "<i><b>Opened a door!\nNow try another."));
			achievements.put("key-pickup", new Achievement(UtilityBox.scaleImage(ImageIO.read(new File(Main.gameDir + "/res/objects/door.png")), gp.tileSize, gp.tileSize), "<b> Picked up a key!"));
			achievements.put("first-kill", new Achievement(new MON_EYE(gp).sprites[0], "<b> First monster!\n<i> Do you feel better now?"));
		} catch (IOException e) {
			System.err.println("[ERROR] Achievement icons could NOT be loaded!");
		}
		
	}
	
}
