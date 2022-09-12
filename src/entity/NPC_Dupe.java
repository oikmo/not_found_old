package entity;

import java.util.Random;

import main.GamePanel;

public class NPC_Dupe extends Entity{

	public NPC_Dupe(GamePanel gp) {
		super(gp);
		direction = "down";
		speed = 2;
		//gp.npc[0] = this;
		getPlayerImage();
		setDialogue();
		
	}
	
	public void getPlayerImage() {
		idle1 = setup("/player/idle_1", gp.tileSize, gp.tileSize);
		idle2 = setup("/player/idle_2", gp.tileSize, gp.tileSize);
		idle3 = setup("/player/idle_3", gp.tileSize, gp.tileSize);
		idle4 = setup("/player/idle_4", gp.tileSize, gp.tileSize);
		idle5 = setup("/player/idle_5", gp.tileSize, gp.tileSize);
		idle6 = setup("/player/idle_6", gp.tileSize, gp.tileSize);
		up1 = setup("/player/up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/player/up_2", gp.tileSize, gp.tileSize);
		up3 = setup("/player/up_3", gp.tileSize, gp.tileSize);
		up4 = setup("/player/up_4", gp.tileSize, gp.tileSize);
		up5 = setup("/player/up_5", gp.tileSize, gp.tileSize);
		up6 = setup("/player/up_6", gp.tileSize, gp.tileSize);
		down1 = setup("/player/down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/player/down_2", gp.tileSize, gp.tileSize);
		down3 = setup("/player/down_3", gp.tileSize, gp.tileSize);
		down4 = setup("/player/down_4", gp.tileSize, gp.tileSize);
		down5 = setup("/player/down_5", gp.tileSize, gp.tileSize);
		down6 = setup("/player/down_6", gp.tileSize, gp.tileSize);
		left1 = setup("/player/left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/player/left_2", gp.tileSize, gp.tileSize);
		left3 = setup("/player/left_3", gp.tileSize, gp.tileSize);
		left4 = setup("/player/left_4", gp.tileSize, gp.tileSize);
		left5 = setup("/player/left_5", gp.tileSize, gp.tileSize);
		left6 = setup("/player/left_6", gp.tileSize, gp.tileSize);
		right1 = setup("/player/right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/player/right_2", gp.tileSize, gp.tileSize);
		right3 = setup("/player/right_3", gp.tileSize, gp.tileSize);
		right4 = setup("/player/right_4", gp.tileSize, gp.tileSize);
		right5 = setup("/player/right_5", gp.tileSize, gp.tileSize);
		right6 = setup("/player/right_6", gp.tileSize, gp.tileSize);
	}
	public void setDialogue() {
		dialogues[0] = "im you";
		dialogues[1] = "what do you mean?";
		dialogues[2] = "do you not fucking get it.";
		dialogues[3] = "YOU ARE ME AND I AM YOU HOW DO YOU \nNOT FUCKING GET IT YOU SACK OF SHIT";
	}
	public void setAction() {
		
		actionLockCounter++;
		
		if(actionLockCounter >= 120) {
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
			
		}
		
		if(actionLockCounter > 120) {
			direction = "idle";
			actionLockCounter = 0;
			
		}

	}
	
	public void speak() {
		gp.ui.npcCounter = 0;
		if(dialogues[dialogueIndex] == null) {
			dialogueIndex = 0;
		}
		gp.ui.currentDialogue = dialogues[dialogueIndex];
		dialogueIndex++;
		if(dialogueIndex == 4) {
			gp.theBoys = true;
		}
		
		switch(gp.player.direction) {
		case "up":
			direction = "down";
			break;
		case "down":
			direction = "up";
			break;
		case "left":
			direction = "right";
			break;
		case "right":
			direction = "left";
			break;
		}
		
	}

	


}
