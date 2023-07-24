package org.not_found.object.tools;

import org.not_found.main.GamePanel;
import org.not_found.object.*;

public class OBJ_Shootable extends OBJ {
	public int amount;
	
	public OBJ_Shootable(GamePanel gp) {
		super(gp);
		objType = OBJType.Shootable;
	}
}
