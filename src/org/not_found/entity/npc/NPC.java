package org.not_found.entity.npc;

import java.awt.Rectangle;

import org.not_found.entity.Entity;
import org.not_found.entity.EntityType;
import org.not_found.main.GamePanel;

public class NPC extends Entity {

	public String npcName;
	public int actionLockCounter;
	public int dialogueIndex = 0;
	protected String dialogues[] = new String[100];
	
	public NPC(GamePanel gp) {
		super(gp);
		entityType = EntityType.NPC;
		speed = 2;
		
		hitBox = new Rectangle();
		hitBox.x = 8;
		hitBox.y = 1;
		solidAreaDefaultX = hitBox.x;
		solidAreaDefaultY = hitBox.y;
		hitBox.width = 32;
		hitBox.height = 46;
	}

}
