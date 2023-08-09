package org.not_found.main;

import java.awt.geom.Rectangle2D;

import org.not_found.object.*;

import org.not_found.entity.*;
import org.not_found.entity.monster.MONSTER;

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
		case Up:
			try {
				entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
				tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
				tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
				if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
					entity.collisionOn = true;
					if(entity.entityType == EntityType.Projectile) {
						entity.alive = false;
					}
				}
			} catch(ArrayIndexOutOfBoundsException e) {}
			break;
		case Down:
			try {
				entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
				tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
				tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
				if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
					entity.collisionOn = true;
					if(entity.entityType == EntityType.Projectile) {
						entity.alive = false;
					}
				}
			} catch(ArrayIndexOutOfBoundsException e) {}
			
			break;
		case Left:
			try {
				entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
				tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
				tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
				if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
					entity.collisionOn = true;
					if(entity.entityType == EntityType.Projectile) {
						entity.alive = false;
					}
				}
			} catch(ArrayIndexOutOfBoundsException e) {}
			
			break;
		case Right:
			try {
				entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
				tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
				tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
				if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
					entity.collisionOn = true;
					if(entity.entityType == EntityType.Projectile) {
						entity.alive = false;
					}
				}
			} catch(ArrayIndexOutOfBoundsException e) {}
			
			break;
		default:
			break;
		}
	}
	
	public boolean checkTileB(Entity entity) {
		int entityLeftWorldX = entity.worldX + entity.hitBox.x;
		int entityRightWorldX = entity.worldX + entity.hitBox.x + entity.hitBox.width;
		int entityTopWorldY = entity.worldY + entity.hitBox.y;
		int entityBottomWorldY = entity.worldY + entity.hitBox.y + entity.hitBox.height;
		
		int entityLeftCol = entityLeftWorldX/gp.tileSize;
		int entityRightCol = entityRightWorldX/gp.tileSize;
		int entityTopRow = entityTopWorldY/gp.tileSize;
		int entityBottomRow = entityBottomWorldY/gp.tileSize;
		
		int tileNum1, tileNum2;
		
		boolean tiled = false;
		
		switch(entity.direction) {
		case Up:
			try {
				entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
				tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
				tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
				if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
					tiled = true;
				}
			} catch(ArrayIndexOutOfBoundsException e) {}
			break;
		case Down:
			try {
				entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
				tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
				tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
				if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
					tiled = true;
				}
			} catch(ArrayIndexOutOfBoundsException e) {}
			
			break;
		case Left:
			try {
				entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
				tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
				tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
				if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
					tiled = true;
				}
			} catch(ArrayIndexOutOfBoundsException e) {}
			
			break;
		case Right:
			try {
				entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
				tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
				tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
				if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
					tiled = true;
				}
			} catch(ArrayIndexOutOfBoundsException e) {}
			
			break;
		default:
			break;
		}
		
		return tiled;
	}
	
	public int checkObject(Entity entity, boolean player) {
		int index = 99;
		
		for(int i=0;i<gp.obj.length;i++) {
			if(gp.obj[gp.currentMap][i] != null) {
				entity.hitBox.x = entity.worldX + entity.hitBox.x;
				entity.hitBox.y = entity.worldY + entity.hitBox.y;
				
				gp.obj[gp.currentMap][i].hitBox.x = gp.obj[gp.currentMap][i].worldX + gp.obj[gp.currentMap][i].hitBox.x;
				gp.obj[gp.currentMap][i].hitBox.y = gp.obj[gp.currentMap][i].worldY + gp.obj[gp.currentMap][i].hitBox.y;
				
				switch(entity.direction) {
				case Up:
					entity.hitBox.y -= entity.speed;
					break;
				case Down:
					entity.hitBox.y += entity.speed;
					break;
				case Left:
					entity.hitBox.x -= entity.speed;
					break;
				case Right:
					entity.hitBox.x += entity.speed;
					break;
				default:
					break;
				}
				if(entity.hitBox.intersects(gp.obj[gp.currentMap][i].hitBox)) {
					if(gp.obj[gp.currentMap][i].collision) {
						entity.collisionOn = true;
					}
					if(player) {
						index = i;
					}
				}
				entity.hitBox.x = entity.solidAreaDefaultX;
				entity.hitBox.y = entity.solidAreaDefaultY;
				gp.obj[gp.currentMap][i].hitBox.x = gp.obj[gp.currentMap][i].solidAreaDefaultX;
				gp.obj[gp.currentMap][i].hitBox.y = gp.obj[gp.currentMap][i].solidAreaDefaultY;
			}
			
		}
		
		return index;
	}
	//NPC or MONSTER
	public int checkEntity(Entity entity, Entity[][] target) {
		int index = 999;
		
		for(int i=0;i<target.length;i++) {
			if(target[gp.currentMap][i] != null) {
				entity.hitBox.x = entity.worldX + entity.hitBox.x;
				entity.hitBox.y = entity.worldY + entity.hitBox.y;
				
				target[gp.currentMap][i].hitBox.x = target[gp.currentMap][i].worldX + target[gp.currentMap][i].hitBox.x;
				target[gp.currentMap][i].hitBox.y = target[gp.currentMap][i].worldY + target[gp.currentMap][i].hitBox.y;
				
				switch(entity.direction) {
				case Up:
					entity.hitBox.y -= entity.speed;
					break;
				case Down:
					entity.hitBox.y += entity.speed;
					break;
				case Left:
					entity.hitBox.x -= entity.speed;
					break;
				case Right:
					entity.hitBox.x += entity.speed;
					break;
				default:
					break;
				}
				if(entity.hitBox.intersects(target[gp.currentMap][i].hitBox)) {
					entity.collisionOn = true;
					index = i;
				}
				entity.hitBox.x = entity.solidAreaDefaultX;
				entity.hitBox.y = entity.solidAreaDefaultY;
				target[gp.currentMap][i].hitBox.x = target[gp.currentMap][i].solidAreaDefaultX;
				target[gp.currentMap][i].hitBox.y = target[gp.currentMap][i].solidAreaDefaultY;
			}
			
		}
		
		return index;
	}
	
	public boolean checkEntityB(Entity entity, Entity[][] target) {
		boolean index = false;
		
		for(int i=0;i<target.length;i++) {
			if(target[gp.currentMap][i] != null) {
				entity.hitBox.x = entity.worldX + entity.hitBox.x;
				entity.hitBox.y = entity.worldY + entity.hitBox.y;
				
				target[gp.currentMap][i].hitBox.x = target[gp.currentMap][i].worldX + target[gp.currentMap][i].hitBox.x;
				target[gp.currentMap][i].hitBox.y = target[gp.currentMap][i].worldY + target[gp.currentMap][i].hitBox.y;
				
				switch(entity.direction) {
				case Up:
					entity.hitBox.y -= entity.speed;
					break;
				case Down:
					entity.hitBox.y += entity.speed;
					break;
				case Left:
					entity.hitBox.x -= entity.speed;
					break;
				case Right:
					entity.hitBox.x += entity.speed;
					break;
				default:
					break;
				}
				if(entity.hitBox.intersects(target[gp.currentMap][i].hitBox)) {
					entity.collisionOn = true;
					index = true;
				}
				entity.hitBox.x = entity.solidAreaDefaultX;
				entity.hitBox.y = entity.solidAreaDefaultY;
				target[gp.currentMap][i].hitBox.x = target[gp.currentMap][i].solidAreaDefaultX;
				target[gp.currentMap][i].hitBox.y = target[gp.currentMap][i].solidAreaDefaultY;
			}
			
		}
		
		return index;
	}
	
	public boolean checkPlayer(Entity entity) {
		boolean contactPlayer = false;
		if(gp.player != null) {
			if(entity.entityType == EntityType.Projectile) {
				entity.hitBox.x = entity.worldX + entity.hitBox.x;
				entity.hitBox.y = entity.worldY + entity.hitBox.y;
				
				gp.player.hitBox.x = gp.player.worldX + gp.player.hitBox.x;
				gp.player.hitBox.y = gp.player.worldY + gp.player.hitBox.y;
				
				switch(entity.direction) {
				case Up:
					entity.hitBox.y -= entity.speed;
					break;
				case Down:
					entity.hitBox.y += entity.speed;
					break;
				case Left:
					entity.hitBox.x -= entity.speed;
					break;
				case Right:
					entity.hitBox.x += entity.speed;
					break;
				default:
					break;
				}
				if(entity.hitBox.intersects(gp.player.hitBox)) {
					entity.collisionOn = true;
					contactPlayer = true;
				}
				
				entity.hitBox.x = entity.solidAreaDefaultX;
				entity.hitBox.y = entity.solidAreaDefaultY;
				gp.player.hitBox.x = gp.player.solidAreaDefaultX;
				gp.player.hitBox.y = gp.player.solidAreaDefaultY;
			} else {
				entity.hitBox.x = entity.worldX + entity.hitBox.x;
				entity.hitBox.y = entity.worldY + entity.hitBox.y;
				
				gp.player.hitBox.x = gp.player.worldX + gp.player.hitBox.x;
				gp.player.hitBox.y = gp.player.worldY + gp.player.hitBox.y;
				
				switch(entity.direction) {
				case Up:
					entity.hitBox.y -= entity.speed;
					break;
				case Down:
					entity.hitBox.y += entity.speed;
					break;
				case Left:
					entity.hitBox.x -= entity.speed;
					break;
				case Right:
					entity.hitBox.x += entity.speed;
					break;
				default:
					break;
				}
				if(entity.hitBox.intersects(gp.player.hitBox)) {
					entity.collisionOn = true;
					contactPlayer = true;
				}
				entity.hitBox.x = entity.solidAreaDefaultX;
				entity.hitBox.y = entity.solidAreaDefaultY;
				gp.player.hitBox.x = gp.player.solidAreaDefaultX;
				gp.player.hitBox.y = gp.player.solidAreaDefaultY;
			}
			
			
		}
		
		return contactPlayer;
	}
	
	public boolean checkPlayerPatrolBox(MONSTER entity) {
	    boolean contactPlayer = false;
	    if (gp.player != null) {
	        Rectangle2D playerHitbox = new Rectangle2D.Double(
	        		gp.player.worldX + gp.player.hitBox.getX(), 
	        		gp.player.worldY + gp.player.hitBox.getY(), 
	        		gp.player.hitBox.getWidth(), 
	        		gp.player.hitBox.getHeight()
	        );
	        
	        if (entity.patrolBox.intersects(playerHitbox)) {
	        	
	        	
	            contactPlayer = true;
	        }

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
			case Up:
				checkedOn.hitBox.y -= checkedOn.speed;
				break;
			case Down:
				checkedOn.hitBox.y += checkedOn.speed;
				break;
			case Left:
				checkedOn.hitBox.x -= checkedOn.speed;
				break;
			case Right:
				checkedOn.hitBox.x += checkedOn.speed;
				break;
			default:
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
	public int checkWalls(Entity entity, boolean player) {
		int index = 99;
		
		for(int i=0;i<gp.walls.size();i++) {
			if(gp.walls.get(i) != null) {
				
				
				entity.hitBox.x = entity.worldX + entity.hitBox.x;
				entity.hitBox.y = entity.worldY + entity.hitBox.y;
				
				gp.walls.get(i).hitBox.x = gp.walls.get(i).worldX + gp.walls.get(i).hitBox.x;
				gp.walls.get(i).hitBox.y = gp.walls.get(i).worldY + gp.walls.get(i).hitBox.y;
				
				switch(entity.direction) {
				case Up:
					entity.hitBox.y -= entity.speed;
					break;
				case Down:
					entity.hitBox.y += entity.speed;
					break;
				case Left:
					entity.hitBox.x -= entity.speed;
					break;
				case Right:
					entity.hitBox.x += entity.speed;
					break;
				default:
					break;
				}
				if(entity.hitBox.intersects(gp.walls.get(i).hitBox)) {
					if(gp.walls.get(i).collision) {
						entity.collisionOn = true;
					}
					if(player) {
						index = i;
					}
				}
				entity.hitBox.x = entity.solidAreaDefaultX;
				entity.hitBox.y = entity.solidAreaDefaultY;
				gp.walls.get(i).hitBox.x = gp.walls.get(i).solidAreaDefaultX;
				gp.walls.get(i).hitBox.y = gp.walls.get(i).solidAreaDefaultY;
			}
			
		}
		
		return index;
		
	}
	
	public boolean checkWalls(OBJ_Wall entity) {
		boolean index = false;
		
		for(int i=0;i<gp.walls.size();i++) {
			if(gp.walls.get(i) != null) {
				
				entity.hitBox.x = entity.worldX + entity.hitBox.x;
				entity.hitBox.y = entity.worldY + entity.hitBox.y;
				
				gp.walls.get(i).hitBox.x = gp.walls.get(i).worldX + gp.walls.get(i).hitBox.x;
				gp.walls.get(i).hitBox.y = gp.walls.get(i).worldY + gp.walls.get(i).hitBox.y;
				
				switch(entity.direction) {
				case Up:
					entity.hitBox.y -= entity.speed;
					break;
				case Down:
					entity.hitBox.y += entity.speed;
					break;
				case Left:
					entity.hitBox.x -= entity.speed;
					break;
				case Right:
					entity.hitBox.x += entity.speed;
					break;
				default:
					break;
				}
				if(entity.hitBox.intersects(gp.walls.get(i).hitBox)) {
					if(entity != gp.walls.get(i)) {
						index = true;
					}
					
				}
				entity.hitBox.x = entity.solidAreaDefaultX;
				entity.hitBox.y = entity.solidAreaDefaultY;
				gp.walls.get(i).hitBox.x = gp.walls.get(i).solidAreaDefaultX;
				gp.walls.get(i).hitBox.y = gp.walls.get(i).solidAreaDefaultY;
			}
			
		}
		
		return index;
		
	}
	
	public boolean checkWallsB(Entity entity) {
		boolean index = false;
		
		for(int i=0;i<gp.walls.size();i++) {
			if(gp.walls.get(i) != null) {
				
				entity.hitBox.x = entity.worldX + entity.hitBox.x;
				entity.hitBox.y = entity.worldY + entity.hitBox.y;
				
				gp.walls.get(i).hitBox.x = gp.walls.get(i).worldX + gp.walls.get(i).hitBox.x;
				gp.walls.get(i).hitBox.y = gp.walls.get(i).worldY + gp.walls.get(i).hitBox.y;
				
				switch(entity.direction) {
				case Up:
					entity.hitBox.y -= entity.speed;
					break;
				case Down:
					entity.hitBox.y += entity.speed;
					break;
				case Left:
					entity.hitBox.x -= entity.speed;
					break;
				case Right:
					entity.hitBox.x += entity.speed;
					break;
				default:
					break;
				}
				if(entity.hitBox.intersects(gp.walls.get(i).hitBox)) {
					index = true;
					
				}
				entity.hitBox.x = entity.solidAreaDefaultX;
				entity.hitBox.y = entity.solidAreaDefaultY;
				gp.walls.get(i).hitBox.x = gp.walls.get(i).solidAreaDefaultX;
				gp.walls.get(i).hitBox.y = gp.walls.get(i).solidAreaDefaultY;
			}
			
		}
		
		return index;
		
	}
}
