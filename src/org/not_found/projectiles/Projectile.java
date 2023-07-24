package org.not_found.projectiles;

import org.not_found.entity.Entity;
import org.not_found.entity.EntityType;
import org.not_found.main.GamePanel;

public class Projectile extends Entity {
	
	Entity user;
	public int useCost;
	GamePanel gp;
	
	public Projectile(GamePanel gp) {
		super(gp);
		this.gp = gp;
		alive = false;
		this.entityType = EntityType.Projectile;
	}
	
	public void set(int worldX, int worldY, Entity.Direction direction, boolean alive, Entity user) {
		this.worldX = worldX;
		this.worldY = worldY;
		this.direction = direction;
		this.alive = alive;
		this.user = user;
		reset();
	}
	
	public void update() {
		if(user == gp.player) {
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			if(monsterIndex != 999) {
				gp.player.damageMonster(monsterIndex, attack);
				alive = false;
			}
		} else if(user != gp.player) {
			
		}
		
		switch(direction) {
		case Up:
			worldY -= speed;
			break;
		case Down:
			worldY += speed;
			break;
		case Left:
			worldX -= speed;
			break;
		case Right:
			worldX += speed;
			break;
		case Idle:
			worldY += speed;
			break;
		default:
			break;
		}
		
		life--;
		if(life <= 0) {
			alive = false;
		}
		
		spriteCounter++;
		if(spriteCounter > 12) {
			if(spriteNum == 1) {
				spriteNum = 2;
			} else if (spriteNum == 2) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}
	}
	
	public void reset() {}

}
