package org.not_found.object.ui;

import java.awt.image.BufferedImage;

import org.not_found.entity.Entity;
import org.not_found.main.GamePanel;
import org.not_found.main.SoundEnum;
import org.not_found.object.OBJ;
import org.not_found.object.OBJType;

public class OBJ_Mana extends OBJ {
	
	public BufferedImage image0, image1;
	GamePanel gp;
	public OBJ_Mana(GamePanel gp) {
		super(gp);
		this.gp = gp;
		name = "heart";
		
		objType = OBJType.PickupAble;
		sprites = setupSheet("player/manaSheet", 2, 1);
		value = 1;
		collision = true;
		
		image = sprites[0];
		image0 = sprites[0];
		image1 = sprites[1];
	}
	
	public void use(Entity entity) {
		if(gp.player.mana < gp.player.maxMana) {
			gp.playSE(SoundEnum.powerUp);
			gp.ui.addMessage("Mana  + " + value);
			if(gp.player.mana + value < gp.player.maxMana){
				gp.player.mana += value;
			}
			didntWork = false;
		} else {
			didntWork = true;
		}
	}
}