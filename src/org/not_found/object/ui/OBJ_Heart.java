package org.not_found.object.ui;

import java.awt.image.BufferedImage;

import org.not_found.entity.Entity;
import org.not_found.main.GamePanel;
import org.not_found.main.SoundEnum;
import org.not_found.object.OBJ;
import org.not_found.object.OBJType;

public class OBJ_Heart extends OBJ {
	
	public BufferedImage image0, image1, image2;
	
	GamePanel gp;
	
	public OBJ_Heart(GamePanel gp) {
		super(gp);
		this.gp = gp;
		name = "heart";
		
		objType = OBJType.PickupAble;
		
		sprites = setupSheet("player/heartSheet", 3, 1);
		value = 2;
		collision = true;
		
		image = sprites[0];
		image0 = sprites[0];
		image1 = sprites[1];
		image2 = sprites[2];
	}
	
	public void use(Entity entity) {
		if(gp.player.life < gp.player.maxLife) {
			gp.playSE(SoundEnum.powerUp);
			gp.ui.addMessage("Heart  + " + value);
			if(gp.player.life + value < gp.player.maxLife){
				gp.player.life += value;
			}
			collision = true;
			didntWork = false;
		} else {
			collision = false;
			didntWork = true;
		}
	}
}
