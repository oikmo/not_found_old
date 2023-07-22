package org.not_found.entity.npc;

import java.io.IOException;
import java.util.Random;

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
		for(int i=0; i < UtilityBox.getDialogueFromTXT("dialogue/test").length; i++) {
			dialogues[i] = UtilityBox.getDialogueFromTXT("dialogue/test")[i];
		}
		
	}
	public void setAction() {
		Random random = new Random();
		actionLockCounter++;
		
		if(actionLockCounter == 120) {
			
			int i = random.nextInt(100)+1;
			direction = Direction.Idle;
			if(i <= 25) {
				direction = Direction.Up;
			}
			if(i > 25 && i <= 50) {
				direction = Direction.Down;
			}
			if(i > 50 && i <= 75) {
				direction = Direction.Left;
			}
			if(i > 75 && i <= 100) {
				direction = Direction.Right;
			}
			actionLockCounter = 0;
		}
		
		if(actionLockCounter > 120) {
			
			direction = Direction.Idle;
		}

	}
	
	@Override
	public void update_alt() {
		
		gp.cChecker.checkEntity(this, gp.npc);
		gp.cChecker.checkEntity(this, gp.monster);
	}
	
	public void speak() {
		gp.ui.npcCounter = 1;
		if(dialogues[dialogueIndex] == null) {
			dialogueIndex = 0;
		}
		gp.ui.currentDialogue = dialogues[dialogueIndex];
		dialogueIndex++;
		
		switch(gp.player.direction) {
		case Up:
			direction = Direction.Down;
			break;
		case Down:
			direction = Direction.Up;
			break;
		case Left:
			direction = Direction.Right;
			break;
		case Right:
			direction = Direction.Left;
			break;
		default:
			break;
		}
		
	}

	


}
