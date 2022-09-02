package entity;

import java.io.IOException;
import java.util.Random;

import main.GamePanel;
import main.UtilityBox;

public class NPC_TEST extends Entity{
	
	UtilityBox uTool = new UtilityBox();
	
	public NPC_TEST(GamePanel gp) throws IOException {
		super(gp);
		setDialogue();
		
		direction = "down";
		speed = 2;
		//gp.npc[1] = this;
		//gp.npc[100] = new Entity(gp);
		getPlayerImage();
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
	public void setDialogue() throws IOException {
		for(int i=0; i < 69; i++) {
			dialogues[i] = uTool.getDialogueFromTXT("/dialogue/test")[i];
		}
		
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
			moving = true;
			
		}
		
		if(actionLockCounter > 80) {
			actionLockCounter = 0;
			direction = "idle";
		}

	}
	
	public void speak() {
		gp.ui.npcCounter = 1;
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
