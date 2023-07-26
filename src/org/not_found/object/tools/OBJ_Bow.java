package org.not_found.object.tools;

import org.not_found.main.GamePanel;
import org.not_found.projectile.PROJ_Arrow;

public class OBJ_Bow extends OBJ_Shootable {

	public OBJ_Bow(GamePanel gp) {
		super(gp);
		amount = Integer.MAX_VALUE;
		projectile = new PROJ_Arrow(gp);		
		ID = "NormBow";
		name = "Normal Bow";
		update();
		image = getImage(2, 0);
	}
	
	public void update() {
		description = "[" + name + "] \nJust a odd looking bow\n [ Arrows : " + amount + " ]";
	}

}
