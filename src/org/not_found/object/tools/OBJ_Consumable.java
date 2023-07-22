package org.not_found.object.tools;

import org.not_found.entity.Entity;
import org.not_found.main.GamePanel;
import org.not_found.main.SoundEnum;
import org.not_found.object.OBJ;
import org.not_found.object.OBJType;

public class OBJ_Consumable extends OBJ {

	GamePanel gp ;
	int value;
	public int uses;
	
	public OBJ_Consumable(GamePanel gp) {
		super(gp);
		this.gp = gp;
		objType = OBJType.Eatable;
		collision = true;
	}
	
	public void warn() {
		gp.gameState = gp.dialogueState;
		gp.ui.currentDialogue = "Your health is full. There is no point.";
	}
	
	public void use(Entity player) {
		if(this.uses == 0) { return; }
		
		System.out.println("no way");
		
		uses--;
		gp.gameState = gp.dialogueState;
		gp.ui.currentDialogue = "You used the " + name + "!\nYour life has been recovered by " + value + ".";
		gp.playSE(SoundEnum.powerUp);
		player.life += this.value;
		
		if(gp.player.life > gp.player.maxLife) {
			gp.player.life = gp.player.maxLife;
		}
		
		updateImage();
	}
	
	public void updateImage() {}
	
}
