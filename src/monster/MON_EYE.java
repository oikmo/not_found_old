package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;

public class MON_EYE extends Entity {
	public MON_EYE(GamePanel gp) {
		super(gp);
		
		monType = 0;
		name = "EYE";
		speed = 1;
		maxLife = 4;
		life = maxLife;
		
		solidArea.x = 1;
		solidArea.y = 1;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 46;
		solidArea.height = 46;
		getImage();
	}
	
	public void getImage() {
		idle1 = setup("/monster/eye/idle_1");
		idle2 = setup("/monster/eye/idle_2");
		idle3 = setup("/monster/eye/idle_3");
		idle4 = setup("/monster/eye/idle_4");
		idle5 = setup("/monster/eye/idle_5");
		idle6 = setup("/monster/eye/idle_6");
		up1 = setup("/monster/eye/up_1");
		up2 = setup("/monster/eye/up_2");
		up3 = setup("/monster/eye/up_3");
		up4 = setup("/monster/eye/up_4");
		up5 = setup("/monster/eye/up_5");
		up6 = setup("/monster/eye/up_6");
		down1 = setup("/monster/eye/down_1");
		down2 = setup("/monster/eye/down_2");
		down3 = setup("/monster/eye/down_3");
		down4 = setup("/monster/eye/down_4");
		down5 = setup("/monster/eye/down_5");
		down6 = setup("/monster/eye/down_6");
		left1 = setup("/monster/eye/left_1");
		left2 = setup("/monster/eye/left_2");
		left3 = setup("/monster/eye/left_3");
		left4 = setup("/monster/eye/left_4");
		left5 = setup("/monster/eye/left_5");
		left6 = setup("/monster/eye/left_6");
		right1 = setup("/monster/eye/right_1");
		right2 = setup("/monster/eye/right_2");
		right3 = setup("/monster/eye/right_3");
		right4 = setup("/monster/eye/right_4");
		right5 = setup("/monster/eye/right_5");
		right6 = setup("/monster/eye/right_6");
		
	
	}
	
	public void setAction() {
		
		actionLockCounter++;
		
		if(actionLockCounter >= 120 && moving == false) {
			Random random = new Random();
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
			moving = true;
			
		}
		
		if(actionLockCounter > 120) {
			direction = "idle";
			actionLockCounter = 0;
			
		}

	}
}
