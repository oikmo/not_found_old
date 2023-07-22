package org.not_found.projectiles;

import org.not_found.main.GamePanel;

public class PROJ_Fireball extends Projectile {

	public PROJ_Fireball(GamePanel gp) {
		super(gp);
		
		name = "Fireball";
		speed = 5;
		maxLife = 80;
		life = maxLife;
		attack = 2;
		useCost = 1;
	}

}
