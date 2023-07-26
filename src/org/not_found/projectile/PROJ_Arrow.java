package org.not_found.projectile;

import java.awt.Rectangle;

import org.not_found.main.GamePanel;

public class PROJ_Arrow extends Projectile {

	public PROJ_Arrow(GamePanel gp) {
		super(gp);
		System.out.println("new!");
		sprites = setupSheet("projectiles/proj_arrow", 2, 4);
		reset();
	}
	
	public void reset() {
		name = "Arrow";
		speed = 7;
		maxLife = 80;
		life = maxLife;
		attack = 4;
		useCost = 1;
		
		switch(direction) {
		case Idle:
			hitBox = new Rectangle(16, 6, 16, 40);
			solidAreaDefaultX = hitBox.x;
			solidAreaDefaultY = hitBox.y;
			break;
		case Down:
			hitBox = new Rectangle(16, 6, 16, 40);
			solidAreaDefaultX = hitBox.x;
			solidAreaDefaultY = hitBox.y;
			break;
		case Up:
			hitBox = new Rectangle(16, 6, 16, 40);
			solidAreaDefaultX = hitBox.x;
			solidAreaDefaultY = hitBox.y;
			break;
		case Left:
			hitBox = new Rectangle(6, 16, 40, 16);
			solidAreaDefaultX = hitBox.x;
			solidAreaDefaultY = hitBox.y;
			break;
		case Right:
			hitBox = new Rectangle(6, 16, 40, 16);
			solidAreaDefaultX = hitBox.x;
			solidAreaDefaultY = hitBox.y;
			break;
		
		default:
			break;
		
		}
		
	}
	
	

}
