package org.not_found.object.tools;

import org.not_found.main.GamePanel;
import org.not_found.object.*;

public class OBJ_Sword_Normal extends OBJ {

	public OBJ_Sword_Normal(GamePanel gp) {
		super(gp);
		objType = OBJType.Weapon;
		ID = "NormSword";
		name = "Normal Sword";
		image = getImage(0,0);
		attackValue = 4;
		description = "[" + name + "] \n Rusty, great for giving\n people tetnus.";
		attackArea.width = 36;
		attackArea.height = 36;
	}

}
