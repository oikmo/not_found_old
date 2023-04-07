package org.not_found.object;

import org.not_found.entity.Entity;
import org.not_found.main.GamePanel;

public class OBJ_Key extends Entity {
	public OBJ_Key(GamePanel gp) {
		super(gp);
		name = "";
		idle1 = setup("/objects/key", gp.tileSize, gp.tileSize);
		collision = true;
	}
}
