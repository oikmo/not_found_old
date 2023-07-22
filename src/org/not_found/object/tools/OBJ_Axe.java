package org.not_found.object.tools;

import org.not_found.main.GamePanel;
import org.not_found.object.*;

public class OBJ_Axe extends OBJ {

	public OBJ_Axe(GamePanel gp) {
		super(gp);
		objType = OBJType.Weapon;
		ID = "WoodAxe";
		name = "Woodcutter's Axe";
		image = getImage(0, 1);
		attackValue = 2;
		description = "[" + name + "] \nAn axe for wood cutting";
		attackArea.width = 30;
		attackArea.height = 30;
	}

}
