package org.not_found.entity.npc;

import java.io.IOException;
import java.util.Random;

import org.not_found.entity.Entity;
import org.not_found.main.GamePanel;
import org.not_found.toolbox.UtilityBox;

public class NPC_TEST extends NPC {
	
	GamePanel gp;
	
	public NPC_TEST(GamePanel gp) throws IOException {
		super(gp);
		this.gp = gp;
		
		solidAreaDefaultX = hitBox.x;
		solidAreaDefaultY = hitBox.y;
		hitBox.width = 32;
		hitBox.height = 46;
		
		sprites = setupSheet("player/playerSheet", 6, 5);
		
		setDialogue();
		
	}
	
	public void setDialogue() throws IOException {
		dialogues[0] = UtilityBox.getDialogueFromTXT("dialogue/test");
		System.out.println(dialogues[0][0]);
	}
	
	public void setAction() {
		if(!collisionOn) { return; }
		
		Random random = new Random();
		actionLockCounter++;
		
		if(actionLockCounter == 120) {
			
			int i = random.nextInt(100)+1 + speed;
			direction = Direction.Idle;
			
			//pure random
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
		startDialogue((Entity)this, dialogueSet);
		
	}

	


}
