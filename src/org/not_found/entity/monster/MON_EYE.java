package org.not_found.entity.monster;

import java.util.Random;

import org.not_found.main.GamePanel;

public class MON_EYE extends MONSTER {
	GamePanel gp;
	
	public MON_EYE(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "EYE";
		
		speed = 1;
		maxLife = 8;
		life = maxLife;
		attack = 5;
		defense = 0;
		exp = 4;
		
		sprites = setupSheet("monster/eyeSheet", 6, 5);
	}
	
	public void setAction() {
		Random random = new Random();
		actionLockCounter++;
		
		if(actionLockCounter == 120) {
			
			int i = random.nextInt(100)+1;
			direction = Direction.Idle;
			if(i <= 25) {
				direction = Direction.Up;
			}
			if(i > 25 && i <= 50) {
				direction = Direction.Down;
			}
			if(i > 50 && i <= 75) {
				direction = Direction.Left;
			}
			if(i > 75 && i <= 100) {
				direction = Direction.Right;
			}
			actionLockCounter = 0;
		}
		
		if(actionLockCounter > 120) {
			
			direction = Direction.Idle;
		}

	}
	
	public void damageReaction() {
		actionLockCounter = 0;
		direction = gp.player.direction;
	}
	
	@Override
	public void update_alt() {
		gp.cChecker.checkEntity(this, gp.npc);
		gp.cChecker.checkEntity(this, gp.monster);
	}
}
