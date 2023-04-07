package org.not_found.object;

import org.not_found.entity.Entity;
import org.not_found.main.GamePanel;

public class OBJ_Chest extends Entity {
	
	public OBJ_Chest(GamePanel gp) {
		super(gp);
		name = "Chest";
		idle1 = setup("/objects/chest", gp.tileSize, gp.tileSize);
		collision = true;
	}
}
