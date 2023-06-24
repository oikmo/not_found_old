package org.not_found.object;

import org.not_found.main.GamePanel;

public class OBJ_Key extends OBJ {
	public OBJ_Key(GamePanel gp) {
		super(gp);
		eType = EntityType.Key;
		name = "Key";
		ID = "";
		idle1 = setup("/objects/key", gp.tileSize, gp.tileSize);
		collision = true;
	}
}
