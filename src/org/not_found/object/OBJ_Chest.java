package org.not_found.object;

import org.not_found.main.GamePanel;

public class OBJ_Chest extends OBJ {
	
	public OBJ_Chest(GamePanel gp) {
		super(gp);
		eType = EntityType.Chest;
		name = "Chest";
		idle1 = setup("/objects/chest", gp.tileSize, gp.tileSize);
		collision = true;
	}
}
