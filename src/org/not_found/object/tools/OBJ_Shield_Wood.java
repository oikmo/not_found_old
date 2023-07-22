package org.not_found.object.tools;

import org.not_found.main.GamePanel;
import org.not_found.object.*;

public class OBJ_Shield_Wood extends OBJ {

	public OBJ_Shield_Wood(GamePanel gp) {
		super(gp);
		objType = OBJType.Shield;
		ID = "NormShield";
		name = "Wood Shield";
		image = getImage(0,1);
		defenseValue = 1;
		description = "[" + name + "] \n Flimsy but its worth it.";
	}

}
