package org.not_found.object;

import org.not_found.entity.Entity;
import org.not_found.main.GamePanel;

public class OBJ_Door extends Entity{
	public OBJ_Door(GamePanel gp) {
		super(gp);
		name = "Door";
		idle1 = setup("/objects/door", gp.tileSize, gp.tileSize);
		collision = true;
	}
}
