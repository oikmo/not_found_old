package org.not_found.entity.monster;

import java.util.Random;

import org.not_found.main.GamePanel;
import org.not_found.projectile.PROJ_Arrow;

public class MON_EYE extends MONSTER {
	GamePanel gp;
	
	public MON_EYE(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "EYE";
		projectile = new PROJ_Arrow(gp);
		speed = 1;
		maxLife = 8;
		life = maxLife;
		attack = 5;
		defense = 0;
		exp = 4;
		sprites = setupSheet("monster/eyeSheet", 6, 5);
	}
	
	public void setAction() {
		if(!collisionOn) { return; }
		
		Random random = new Random();
		actionLockCounter++;
		
		if(actionLockCounter == 120) {
			
			int i = random.nextInt(100)+1 + speed;
			direction = Direction.Idle;
			int vlow = new Random().nextInt(25);
			int low = new Random().nextInt(50);
			if(low < vlow) { low = vlow + 25; }
			int medium = new Random().nextInt(75);
			if(medium < low) { medium = low + 25; }
			int high = new Random().nextInt(100);
			if(high < medium) { high = medium + 25; }
			
			if(i <= vlow) {
				direction = Direction.Up;
			}
			if(i > vlow && i <= low) {
				direction = Direction.Down;
			}
			if(i > low && i <= medium) {
				direction = Direction.Left;
			}
			if(i > medium && i <= high) {
				direction = Direction.Right;
			}
			actionLockCounter = 0;
		}
		
		if(actionLockCounter > 120) {
			
			direction = Direction.Idle;
		}
		
		int i = new Random().nextInt(100)+1;
		int limit = new Random().nextInt(99) + 1;
		if(i >= limit && limit > 60 && !projectile.alive && shotAvailableCounter == shotCounterLimit) {
			projectile.set(worldX, worldY, direction, true, this);
			shotAvailableCounter = 0;
			gp.projectiles.add(projectile);
			
		}
		
	}
	
	public void damageReaction() {
		actionLockCounter = 0;
		direction = gp.player.direction;
	}
	
	@Override
	public void update_alt() {

		if(shotAvailableCounter < shotCounterLimit) {
			shotAvailableCounter++;
		}
		
	}
}
