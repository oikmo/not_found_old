package org.not_found.main;

import org.not_found.entity.Entity;

public class CollisionChecker {
	GamePanel gp;
	
	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}
	public void checkTile(Entity entity) {
		int entityLeftWorldX = entity.worldX + entity.hitBox.x;
		int entityRightWorldX = entity.worldX + entity.hitBox.x + entity.hitBox.width;
		int entityTopWorldY = entity.worldY + entity.hitBox.y;
		int entityBottomWorldY = entity.worldY + entity.hitBox.y + entity.hitBox.height;
		
		int entityLeftCol = entityLeftWorldX/gp.tileSize;
		int entityRightCol = entityRightWorldX/gp.tileSize;
		int entityTopRow = entityTopWorldY/gp.tileSize;
		int entityBottomRow = entityBottomWorldY/gp.tileSize;
		
		int tileNum1, tileNum2;
		
		switch(entity.direction) {
		case "up":
			entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "down":
			entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "left":
			entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "right":
			entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		}
	}
	public int checkObject(Entity entity, boolean player) {
		int index = 99;
		
		for(int i=0;i<gp.obj.length;i++) {
			if(gp.obj[i] != null) {
				entity.hitBox.x = entity.worldX + entity.hitBox.x;
				entity.hitBox.y = entity.worldY + entity.hitBox.y;
				
				gp.obj[i].hitBox.x = gp.obj[i].worldX + gp.obj[i].hitBox.x;
				gp.obj[i].hitBox.y = gp.obj[i].worldY + gp.obj[i].hitBox.y;
				
				switch(entity.direction) {
				case "up":
					entity.hitBox.y -= entity.speed;
					break;
				case "down":
					entity.hitBox.y += entity.speed;
					break;
				case "left":
					entity.hitBox.x -= entity.speed;
					break;
				case "right":
					entity.hitBox.x += entity.speed;
					break;
				}
				if(entity.hitBox.intersects(gp.obj[i].hitBox)) {
					if(gp.obj[i].collision) {
						entity.collisionOn = true;
					}
					if(player) {
						index = i;
					}
				}
				entity.hitBox.x = entity.solidAreaDefaultX;
				entity.hitBox.y = entity.solidAreaDefaultY;
				gp.obj[i].hitBox.x = gp.obj[i].solidAreaDefaultX;
				gp.obj[i].hitBox.y = gp.obj[i].solidAreaDefaultY;
			}
			
		}
		
		return index;
	}
	//NPC or MONSTER
	public int checkEntity(Entity entity, Entity[] target) {
		int index = 999;
		
		for(int i=0;i<target.length;i++) {
			if(target[i] != null) {
				entity.hitBox.x = entity.worldX + entity.hitBox.x;
				entity.hitBox.y = entity.worldY + entity.hitBox.y;
				
				target[i].hitBox.x = target[i].worldX + target[i].hitBox.x;
				target[i].hitBox.y = target[i].worldY + target[i].hitBox.y;
				
				switch(entity.direction) {
				case "up":
					entity.hitBox.y -= entity.speed;
					break;
				case "down":
					entity.hitBox.y += entity.speed;
					break;
				case "left":
					entity.hitBox.x -= entity.speed;
					break;
				case "right":
					entity.hitBox.x += entity.speed;
					break;
				}
				if(entity.hitBox.intersects(target[i].hitBox)) {
					entity.collisionOn = true;
					index = i;
				}
				entity.hitBox.x = entity.solidAreaDefaultX;
				entity.hitBox.y = entity.solidAreaDefaultY;
				target[i].hitBox.x = target[i].solidAreaDefaultX;
				target[i].hitBox.y = target[i].solidAreaDefaultY;
			}
			
		}
		
		return index;
	}
	
	public boolean checkPlayer(Entity entity) {
		boolean contactPlayer = false;
		if(gp.player != null) {
			entity.hitBox.x = entity.worldX + entity.hitBox.x;
			entity.hitBox.y = entity.worldY + entity.hitBox.y;
			
			gp.player.hitBox.x = gp.player.worldX + gp.player.hitBox.x;
			gp.player.hitBox.y = gp.player.worldY + gp.player.hitBox.y;
			
			switch(entity.direction) {
			case "up":
				entity.hitBox.y -= entity.speed;
				if(entity.hitBox.intersects(gp.player.hitBox)) {
					entity.collisionOn = true;
				}
				break;
			case "down":
				entity.hitBox.y += entity.speed;
				if(entity.hitBox.intersects(gp.player.hitBox)) {
					entity.collisionOn = true;
				}
				break;
			case "left":
				entity.hitBox.x -= entity.speed;
				if(entity.hitBox.intersects(gp.player.hitBox)) {
					entity.collisionOn = true;
				}
				break;
			case "right":
				entity.hitBox.x += entity.speed;
				if(entity.hitBox.intersects(gp.player.hitBox)) {
					entity.collisionOn = true;
					contactPlayer = true;
				}
				break;
			}
			//if(entity.hitBox.intersects(gp.player.hitBox)) {
			//	entity.collisionOn = true;
			//}
			entity.hitBox.x = entity.solidAreaDefaultX;
			entity.hitBox.y = entity.solidAreaDefaultY;
			gp.player.hitBox.x = gp.player.solidAreaDefaultX;
			gp.player.hitBox.y = gp.player.solidAreaDefaultY;
		}
		
		return contactPlayer;
	}
	
	public boolean checkEntity(Entity checkedOn, Entity checkFor) {
		boolean contactPlayer = false;
		
		if(checkFor != null) {
			checkedOn.hitBox.x = checkedOn.worldX + checkedOn.hitBox.x;
			checkedOn.hitBox.y = checkedOn.worldY + checkedOn.hitBox.y;
			
			checkFor.hitBox.x = checkFor.worldX + checkFor.hitBox.x;
			checkFor.hitBox.y = checkFor.worldY + checkFor.hitBox.y;
				
			switch(checkedOn.direction) {
			case "up":
				checkedOn.hitBox.y -= checkedOn.speed;
				break;
			case "down":
				checkedOn.hitBox.y += checkedOn.speed;
				break;
			case "left":
				checkedOn.hitBox.x -= checkedOn.speed;
				break;
			case "right":
				checkedOn.hitBox.x += checkedOn.speed;
				break;
			}
			if(checkedOn.hitBox.intersects(gp.player.hitBox)) {
				checkedOn.collisionOn = true;
				contactPlayer = true;
			}
			checkedOn.hitBox.x = checkedOn.solidAreaDefaultX;
			checkedOn.hitBox.y = checkedOn.solidAreaDefaultY;
			checkFor.hitBox.x = checkFor.solidAreaDefaultX;
			checkFor.hitBox.y = checkFor.solidAreaDefaultY;
		}		
		return contactPlayer;
	}
}
