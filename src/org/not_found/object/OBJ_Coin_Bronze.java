package org.not_found.object;

import org.not_found.main.GamePanel;

public class OBJ_Coin_Bronze extends OBJ {

	GamePanel gp;
	
	public OBJ_Coin_Bronze(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		objType = OBJType.PickupAble;
		name = "Bronze Coin";
		value = 1;
		image = getImage(0,4);
	}
	
}
