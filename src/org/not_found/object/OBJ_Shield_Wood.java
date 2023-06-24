package org.not_found.object;

import org.not_found.main.GamePanel;

public class OBJ_Shield_Wood extends OBJ {

	public OBJ_Shield_Wood(GamePanel gp) {
		super(gp);
		ID = "NormSword";
		name = "Wood Shield";
		idle1 = setup("/objects/shield_wood", gp.tileSize, gp.tileSize);
		defenseValue = 1;
	}

}
