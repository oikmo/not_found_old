package org.not_found.object.tools;

import java.awt.image.BufferedImage;

import org.not_found.main.GamePanel;

public class OBJ_Syringe extends OBJ_Consumable {
	
	public BufferedImage image0, image1, image2;
	
	public OBJ_Syringe(GamePanel gp) {
		super(gp);
		//objType = OBJType.Syringe;
		ID = "heal_Syringe";
		name = "Syringe";
		image0 = getImage(0,2);
		image1 = getImage(1,2);
		image2 = getImage(2,2);
		image = image2;
		
		value = 5;
		uses = 2;
		
		description = "[" + name + "] \n Has a thick black goo\n You should probably use it.";
	}
	
	public void updateImage() {
		if(uses == 2) {
			image = image2;
		} else if(uses == 1) {
			image = image1;
		} else if(uses == 0) {
			image = image0;
		}
	}
	
}
