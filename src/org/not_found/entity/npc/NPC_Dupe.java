package org.not_found.entity.npc;

import java.util.Random;

import org.not_found.main.GamePanel;
import org.not_found.entity.Entity;

public class NPC_Dupe extends NPC {
	GamePanel gp;
	
	public NPC_Dupe(GamePanel gp) {
		super(gp);
		this.gp = gp;
		solidAreaDefaultX = hitBox.x;
		solidAreaDefaultY = hitBox.y;
		hitBox.width = 32;
		hitBox.height = 46;
		
		setDialogue();
		sprites = setupSheet("player/playerSheet", 6, 5);
	}
	public void setDialogue() {
		dialogues[0][0] = "DUPE%im you";
		dialogues[0][1] = "YOU%what do you mean?";
		dialogues[0][2] = "DUPE%do you not fucking get it.";
		dialogues[0][3] = "DUPE%YOU ARE ME AND I AM YOU HOW DO YOU \nNOT FUCKING GET IT YOU SACK OF SHIT";
		
		dialogues[1][0] = "DUPE%leave me the fuck alone please.";
	}
	public void setAction() {
		if(!collisionOn) { return; }
		
		Random random = new Random();
		actionLockCounter++;
		
		if(actionLockCounter == 125) {
			
			int i = random.nextInt(100)+1 + speed;
			direction = Direction.Idle;
			int vlow = new Random().nextInt(25);
			int low = new Random().nextInt(50);
			if(low < vlow) { low = vlow + 25; }
			int medium = new Random().nextInt(75);
			if(medium < low) { medium = low + 25; }
			int high = new Random().nextInt(100);
			if(high < medium) { high = medium + 25; }
			
			if(i <= vlow) {
				direction = Direction.Up;
			}
			if(i > vlow && i <= low) {
				direction = Direction.Down;
			}
			if(i > low && i <= medium) {
				direction = Direction.Left;
			}
			if(i > medium && i <= high) {
				direction = Direction.Right;
			}
			actionLockCounter = 0;
		}
		
		if(actionLockCounter > 120) {
			
			direction = Direction.Idle;
		}

	}
	
	public void speak() {
		
		facePlayer();
		
		
		if(dialogueSetCompleted) {
			dialogueSet = 1;
			dialogueIndex = 0;
			dialogueSetCompleted = false;
		}
		startDialogue((Entity)this, dialogueSet);
		
		
	}

	


}
