package org.not_found.object;

import org.not_found.entity.Entity;
import org.not_found.main.GamePanel;

public class OBJ_Sword_Normal extends Entity {

	public OBJ_Sword_Normal(GamePanel gp) {
		super(gp);
		name = "Normal Sword";
		idle1 = setup("/objects/sword_normal", gp.tileSize, gp.tileSize);
		attackValue = 2;
	}

}
