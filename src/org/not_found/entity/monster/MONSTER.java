package org.not_found.entity.monster;

import org.not_found.entity.EntityType;
import org.not_found.entity.npc.NPC;
import org.not_found.main.GamePanel;

public class MONSTER extends NPC {

	public MONSTER(GamePanel gp) {
		super(gp);
		
		entityType = EntityType.Monster;
		
	}
}
