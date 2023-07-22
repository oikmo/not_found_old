package org.not_found.object;

import org.not_found.main.GamePanel;

public class OBJ_Chest extends OBJ {
	
	public OBJ_Chest(GamePanel gp) {
		super(gp);
		objType = OBJType.Chest;
		name = "Chest";
		image = setup("/objects/chest");
		collision = true;
	}
}
