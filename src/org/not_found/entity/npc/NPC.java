package org.not_found.entity.npc;

import org.not_found.entity.Entity;
import org.not_found.main.GamePanel;

public class NPC extends Entity {

	public NPC(GamePanel gp) {
		super(gp);
		entityType = EntityType.NPC;
		direction = "down";
		speed = 2;
	}

}
