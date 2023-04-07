package org.not_found.object;

import org.not_found.entity.Entity;
import org.not_found.main.GamePanel;

public class OBJ_Shield_Wood extends Entity {

	public OBJ_Shield_Wood(GamePanel gp) {
		super(gp);
		name = "Wood Shield";
		idle1 = setup("/objects/shield_wood", gp.tileSize, gp.tileSize);
		defenseValue = 1;
	}

}
