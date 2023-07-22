package org.not_found.object;

import org.not_found.main.GamePanel;

public class OBJ_Key extends OBJ {
	public OBJ_Key(GamePanel gp) {
		super(gp);
		objType = OBJType.Key;
		name = "Key";
		ID = "";
		image = getImage(0,3);
		collision = true;
		description = "[" + name + "] \nA key. I wonder if it leads\nsomewhere?";
	}
}
