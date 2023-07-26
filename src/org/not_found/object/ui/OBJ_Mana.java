package org.not_found.object.ui;

import java.awt.image.BufferedImage;

import org.not_found.main.GamePanel;
import org.not_found.object.OBJ;

public class OBJ_Mana extends OBJ {
	
	public BufferedImage image0, image1;
	
	public OBJ_Mana(GamePanel gp) {
		super(gp);
		name = "heart";
		
		sprites = setupSheet("player/manaSheet", 2, 1);
		
		image0 = sprites[0];
		image1 = sprites[1];
	}
}