package org.not_found.object.tools;

import org.not_found.entity.Entity;
import org.not_found.main.GamePanel;
import org.not_found.main.SoundEnum;
import org.not_found.object.OBJ;
import org.not_found.object.OBJType;

public class OBJ_Consumable extends OBJ {

	GamePanel gp;
	
	public int uses;
	
	public OBJ_Consumable(GamePanel gp) {
		super(gp);
		this.gp = gp;
		objType = OBJType.Eatable;
		collision = true;
		setDialogue();
	}
	
	public void setDialogue() {
		dialogues[0][0] = "Your health is full. There is no point.";
		dialogues[1][0] = "You used the " + name + "!\nYour life has been recovered by " + value + ".";
	}
	
	public void warn() {
		startDialogue(this, 0);
	}
	
	public void use(Entity player) {
		if(this.uses == 0) { return; }
		
		System.out.println("no way");
		
		uses--;
		startDialogue(this, 1);
		gp.playSE(SoundEnum.powerUp);
		player.life += this.value;
		
		if(gp.player.life > gp.player.maxLife) {
			gp.player.life = gp.player.maxLife;
		}
		
		updateImage();
	}
	
	public void updateImage() {}
	
}
