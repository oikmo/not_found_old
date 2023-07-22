package org.not_found.object;

import org.not_found.main.GamePanel;

public class OBJ_Door extends OBJ {
	public OBJ_Door(GamePanel gp) {
		super(gp);
		objType = OBJType.Door;
		name = "Door";
		image = setup("/objects/door", gp.tileSize, gp.tileSize);
		collision = true;
	}
}
