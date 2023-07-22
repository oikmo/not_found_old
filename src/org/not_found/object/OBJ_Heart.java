package org.not_found.object;

import java.awt.image.BufferedImage;

import org.not_found.main.GamePanel;

public class OBJ_Heart extends OBJ {
	
	public BufferedImage image1, image2, image3;
	
	public OBJ_Heart(GamePanel gp) {
		super(gp);
		name = "heart";
		
		sprites = setupSheet("player/heartSheet", 3, 1);
		
		image1 = sprites[0];
		image2 = sprites[1];
		image3 = sprites[2];
	}
}
