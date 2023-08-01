package org.not_found.entity;

import org.not_found.main.GamePanel;

public class EntityLiving extends Entity {
	
	GamePanel gp;
	
	public EntityLiving(GamePanel gp) {
		super(gp);
		this.gp = gp;
	}

}
