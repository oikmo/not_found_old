package org.not_found.object;

import org.not_found.entity.Entity;
import org.not_found.main.GamePanel;

public class OBJ_Wall extends OBJ {
	
	public OBJ_Wall(GamePanel gp) {
		super(gp);
		objType = OBJType.Chest;
		image = setup("/objects/wall");
		collision = true;
		useCost = 2;
		ID = "Wall";
		
	}
	
	public void use(Entity entity) {
		if(haveResource(entity)) {
			
			//detect direction
			//place wall in direction of user
			//remove mana from user
			
		}
	}
	
	public boolean haveResource(Entity user) {
		boolean haveResource = false;
		if(user.mana >= useCost) {
			haveResource = true;
		}
		return haveResource;
	}
	
}
