package org.not_found.projectiles;

import org.not_found.main.GamePanel;

public class PROJ_Arrow extends Projectile {

	public PROJ_Arrow(GamePanel gp) {
		super(gp);
		
		sprites = setupSheet("projectiles/proj_arrow", 2, 4);
	}
	
	public void reset() {
		name = "Arrow";
		speed = 7;
		maxLife = 80;
		life = maxLife;
		attack = 4;
		useCost = 1;
	}
	
	

}
