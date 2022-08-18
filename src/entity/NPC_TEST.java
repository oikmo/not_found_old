package entity;

import java.util.Random;

import main.GamePanel;

public class NPC_TEST extends Entity{

	public NPC_TEST(GamePanel gp) {
		super(gp);
		name = "TEST";
		direction = "down";
		speed = 2;
		gp.npc[1] = this;
		getPlayerImage();
		setDialogue();
		
	}
	
	public void getPlayerImage() {
		idle1 = setup("/npc/idle_1");
		idle2 = setup("/npc/idle_2");
		idle3 = setup("/npc/idle_3");
		idle4 = setup("/npc/idle_4");
		idle5 = setup("/npc/idle_5");
		idle6 = setup("/npc/idle_6");
		up1 = setup("/npc/up_1");
		up2 = setup("/npc/up_2");
		up3 = setup("/npc/up_3");
		up4 = setup("/npc/up_4");
		up5 = setup("/npc/up_5");
		up6 = setup("/npc/up_6");
		down1 = setup("/npc/down_1");
		down2 = setup("/npc/down_2");
		down3 = setup("/npc/down_3");
		down4 = setup("/npc/down_4");
		down5 = setup("/npc/down_5");
		down6 = setup("/npc/down_6");
		left1 = setup("/npc/left_1");
		left2 = setup("/npc/left_2");
		left3 = setup("/npc/left_3");
		left4 = setup("/npc/left_4");
		left5 = setup("/npc/left_5");
		left6 = setup("/npc/left_6");
		right1 = setup("/npc/right_1");
		right2 = setup("/npc/right_2");
		right3 = setup("/npc/right_3");
		right4 = setup("/npc/right_4");
		right5 = setup("/npc/right_5");
		right6 = setup("/npc/right_6");
	}
	public void setDialogue() {
		gp.ui.npcCounter = 1;
		dialogues[0] = "test test test";;
	}
	public void setAction() {
		
		actionLockCounter++;
		
		if(actionLockCounter >= 80 && moving == false) {
			Random random = new Random();
			int i = random.nextInt(100)+2;
			
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
			switch(gp.player.direction) {
			case "up":
				direction = "up";
				break;
			case "down":
				direction = "down";
				break;
			case "left":
				direction = "left";
				break;
			case "right":
				direction = "right";
				break;
			}
			moving = true;
		}
		
		if(actionLockCounter > 80) {
			actionLockCounter = 0;
		}

	}
	
	public void speak() {
		if(dialogues[dialogueIndex] == null) {
			dialogueIndex = 0;
		}
		gp.ui.currentDialogue = dialogues[dialogueIndex];
		dialogueIndex++;
		
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
