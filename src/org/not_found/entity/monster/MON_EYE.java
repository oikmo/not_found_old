package org.not_found.entity.monster;

import java.awt.Rectangle;
import java.util.Random;

import org.not_found.main.GamePanel;

public class MON_EYE extends MONSTER {
	GamePanel gp;
	
	public MON_EYE(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "EYE";
		
		speed = 1;
		maxLife = 8;
		life = maxLife;
		attack = 5;
		defense = 0;
		exp = 4;
		
		hitBox = new Rectangle();
		hitBox.x = 8;
		hitBox.y = 1;
		solidAreaDefaultX = hitBox.x;
		solidAreaDefaultY = hitBox.y;
		hitBox.width = 32;
		hitBox.height = 46;
		getImage();
	}
	
	public void getImage() {
		idle1 = setup("/monster/eye/idle_1", gp.tileSize, gp.tileSize);
		idle2 = setup("/monster/eye/idle_2", gp.tileSize, gp.tileSize);
		idle3 = setup("/monster/eye/idle_3", gp.tileSize, gp.tileSize);
		idle4 = setup("/monster/eye/idle_4", gp.tileSize, gp.tileSize);
		idle5 = setup("/monster/eye/idle_5", gp.tileSize, gp.tileSize);
		idle6 = setup("/monster/eye/idle_6", gp.tileSize, gp.tileSize);
		up1 = setup("/monster/eye/up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/monster/eye/up_2", gp.tileSize, gp.tileSize);
		up3 = setup("/monster/eye/up_3", gp.tileSize, gp.tileSize);
		up4 = setup("/monster/eye/up_4", gp.tileSize, gp.tileSize);
		up5 = setup("/monster/eye/up_5", gp.tileSize, gp.tileSize);
		up6 = setup("/monster/eye/up_6", gp.tileSize, gp.tileSize);
		down1 = setup("/monster/eye/down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/monster/eye/down_2", gp.tileSize, gp.tileSize);
		down3 = setup("/monster/eye/down_3", gp.tileSize, gp.tileSize);
		down4 = setup("/monster/eye/down_4", gp.tileSize, gp.tileSize);
		down5 = setup("/monster/eye/down_5", gp.tileSize, gp.tileSize);
		down6 = setup("/monster/eye/down_6", gp.tileSize, gp.tileSize);
		left1 = setup("/monster/eye/left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/monster/eye/left_2", gp.tileSize, gp.tileSize);
		left3 = setup("/monster/eye/left_3", gp.tileSize, gp.tileSize);
		left4 = setup("/monster/eye/left_4", gp.tileSize, gp.tileSize);
		left5 = setup("/monster/eye/left_5", gp.tileSize, gp.tileSize);
		left6 = setup("/monster/eye/left_6", gp.tileSize, gp.tileSize);
		right1 = setup("/monster/eye/right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/monster/eye/right_2", gp.tileSize, gp.tileSize);
		right3 = setup("/monster/eye/right_3", gp.tileSize, gp.tileSize);
		right4 = setup("/monster/eye/right_4", gp.tileSize, gp.tileSize);
		right5 = setup("/monster/eye/right_5", gp.tileSize, gp.tileSize);
		right6 = setup("/monster/eye/right_6", gp.tileSize, gp.tileSize);
		
	
	}
	
	public void setAction() {
		Random random = new Random();
		actionLockCounter++;
		
		if(actionLockCounter == 120) {
			
			int i = random.nextInt(100)+1;
			direction = "idle";
			if(i <= 25) {
				direction = "up";
			}
			if(i > 25 && i <= 50) {
				direction = "down";
			}
			if(i > 50 && i <= 75) {
				direction = "left";
			}
			if(i > 75 && i <= 100) {
				direction = "right";
			}
			actionLockCounter = 0;
		}
		
		if(actionLockCounter > 120) {
			
			direction = "idle";
		}

	}
	
	public void damageReaction() {
		actionLockCounter = 0;
		direction = gp.player.direction;
	}
	
	@Override
	public void update_alt() {
		gp.cChecker.checkEntity(this, gp.npc);
		gp.cChecker.checkEntity(this, gp.monster);
	}
}
