package org.not_found.object;

import org.not_found.entity.Entity;
import org.not_found.main.GamePanel;

public class OBJ_Heart extends Entity {

	public OBJ_Heart(GamePanel gp) {
		super(gp);
		name = "heart";
		image1 = setup("/player/heart/full", gp.tileSize, gp.tileSize);
		image2 = setup("/player/heart/half", gp.tileSize, gp.tileSize);
		image3 = setup("/player/heart/blank", gp.tileSize, gp.tileSize);
	}
}
