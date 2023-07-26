package org.not_found.object;

import java.awt.Rectangle;

import org.not_found.main.GamePanel;

public class OBJ_Key extends OBJ {
	public OBJ_Key(GamePanel gp) {
		super(gp);
		hitBox = new Rectangle(16,4,18,38);
		solidAreaDefaultX = hitBox.x;
		solidAreaDefaultY = hitBox.y;
		
		objType = OBJType.Key;
		name = "Key";
		ID = "";
		image = getImage(0,3);
		collision = true;
		description = "[" + name + "] \nA key. I wonder if it leads\nsomewhere?";
	}
}
