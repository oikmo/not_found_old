package org.not_found.object;

import org.not_found.main.GamePanel;
import org.not_found.main.SoundEnum;
import org.not_found.entity.*;

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
	
	public void use(Entity entity) {
		gp.playSE(SoundEnum.key);
		gp.ui.addMessage("Coin + " + value);
		gp.player.coin += value;
	}
}
