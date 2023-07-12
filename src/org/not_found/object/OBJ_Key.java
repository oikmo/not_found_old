package org.not_found.object;

import org.not_found.main.GamePanel;

public class OBJ_Key extends OBJ {
	public OBJ_Key(GamePanel gp) {
		super(gp);
		entityType = EntityType.Key;
		name = "Key";
		ID = "";
		idle1 = setup("/objects/key", gp.tileSize, gp.tileSize);
		collision = true;
		description = "[" + name + "] \nA key. I wonder if it leads\nsomewhere?";
	}
}
