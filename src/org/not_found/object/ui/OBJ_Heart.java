package org.not_found.object.ui;

import java.awt.image.BufferedImage;

import org.not_found.main.GamePanel;
import org.not_found.object.OBJ;

public class OBJ_Heart extends OBJ {
	
	public BufferedImage image0, image1, image2;
	
	public OBJ_Heart(GamePanel gp) {
		super(gp);
		name = "heart";
		
		sprites = setupSheet("player/heartSheet", 3, 1);
		
		image0 = sprites[0];
		image1 = sprites[1];
		image2 = sprites[2];
	}
}
