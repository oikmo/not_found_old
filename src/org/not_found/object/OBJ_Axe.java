package org.not_found.object;

import org.not_found.main.GamePanel;

public class OBJ_Axe extends OBJ {

	public OBJ_Axe(GamePanel gp) {
		super(gp);
		entityType = EntityType.Weapon;
		ID = "WoodAxe";
		name = "Woodcutter's Axe";
		idle1 = setup("/objects/axe", gp.tileSize, gp.tileSize);
		attackValue = 2;
		description = "[" + name + "] \nAn axe for wood cutting";
		attackArea.width = 30;
		attackArea.height = 30;
	}

}
