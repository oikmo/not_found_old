package org.not_found.entity.monster;

import org.not_found.entity.Entity;
import org.not_found.main.GamePanel;

public class MONSTER extends Entity {

	public MONSTER(GamePanel gp) {
		super(gp);
		
		entityType = EntityType.Monster;
	}
}
