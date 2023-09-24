package org.not_found.entity.npc;

import java.awt.Rectangle;

import org.not_found.entity.Entity;
import org.not_found.entity.EntityType;
import org.not_found.entity.Entity.Direction;
import org.not_found.main.GamePanel;

public class NPC extends Entity {
	
	GamePanel gp;
	
	public String npcName;
	public int actionLockCounter;
	public int npcCounter;
	
	public NPC(GamePanel gp) {
		super(gp);
		this.gp = gp;
		entityType = EntityType.NPC;
		speed = 2;
		
		hitBox = new Rectangle(8, 1, 32, 46);
		solidAreaDefaultX = hitBox.x;
		solidAreaDefaultY = hitBox.y;
	}
	
	public void update_alt() {
		setAction();
		
		if(shotAvailableCounter < shotCounterLimit) {
			shotAvailableCounter++;
		}
	}
	
	public void setAction() {}
	
	public void speak() {}
	
	public void facePlayer() {
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
