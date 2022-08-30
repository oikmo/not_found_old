package entity;

import java.io.IOException;
import java.util.Random;

import main.GamePanel;
import main.UtilityBox;

public class NPC_TEST extends Entity{
	
	UtilityBox uTool = new UtilityBox();
	
	public NPC_TEST(GamePanel gp) throws IOException {
		super(gp);
		direction = "down";
		speed = 2;
		//gp.npc[1] = this;
		//gp.npc[100] = new Entity(gp);
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
	public void setDialogue() throws IOException {
		
		dialogues[0] = uTool.array("/dialogue/test")[0];
		dialogues[1] = uTool.array("/dialogue/test")[1];
		dialogues[2] = uTool.array("/dialogue/test")[2];
		dialogues[3] = uTool.array("/dialogue/test")[3];
		dialogues[4] = uTool.array("/dialogue/test")[4];
		dialogues[5] = uTool.array("/dialogue/test")[5];
		dialogues[6] = uTool.array("/dialogue/test")[6];
		dialogues[7] = uTool.array("/dialogue/test")[7];
		dialogues[8] = uTool.array("/dialogue/test")[8];
		dialogues[9] = uTool.array("/dialogue/test")[9];
		dialogues[10] = uTool.array("/dialogue/test")[10];
		dialogues[11] = uTool.array("/dialogue/test")[11];
		dialogues[12] = uTool.array("/dialogue/test")[12];
		dialogues[13] = uTool.array("/dialogue/test")[13];
		dialogues[14] = uTool.array("/dialogue/test")[14];
		dialogues[15] = uTool.array("/dialogue/test")[15];
		dialogues[16] = uTool.array("/dialogue/test")[16];
		dialogues[17] = uTool.array("/dialogue/test")[17];
		dialogues[18] = uTool.array("/dialogue/test")[18];
		dialogues[19] = uTool.array("/dialogue/test")[19];
		dialogues[20] = uTool.array("/dialogue/test")[20];
		dialogues[21] = uTool.array("/dialogue/test")[21];
		dialogues[22] = uTool.array("/dialogue/test")[22];
		dialogues[23] = uTool.array("/dialogue/test")[23];
		dialogues[24] = uTool.array("/dialogue/test")[24];
		dialogues[25] = uTool.array("/dialogue/test")[25];
		dialogues[26] = uTool.array("/dialogue/test")[26];
		dialogues[27] = uTool.array("/dialogue/test")[27];
		dialogues[28] = uTool.array("/dialogue/test")[28];
		dialogues[29] = uTool.array("/dialogue/test")[29];
		dialogues[30] = uTool.array("/dialogue/test")[30];
		dialogues[31] = uTool.array("/dialogue/test")[31];
		dialogues[32] = uTool.array("/dialogue/test")[32];
		dialogues[33] = uTool.array("/dialogue/test")[33];
		dialogues[34] = uTool.array("/dialogue/test")[34];
		dialogues[35] = uTool.array("/dialogue/test")[35];
		dialogues[36] = uTool.array("/dialogue/test")[36];
		dialogues[37] = uTool.array("/dialogue/test")[37];
		dialogues[38] = uTool.array("/dialogue/test")[38];
		dialogues[39] = uTool.array("/dialogue/test")[39];
		dialogues[40] = uTool.array("/dialogue/test")[40];
		
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
