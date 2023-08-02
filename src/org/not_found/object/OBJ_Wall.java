package org.not_found.object;

import org.not_found.entity.Entity;
import org.not_found.main.GamePanel;

public class OBJ_Wall extends OBJ {
	
	GamePanel gp;
	
	int offsetX, offsetY;
	
	public OBJ_Wall(GamePanel gp) {
		super(gp);
		this.gp = gp;
		objType = OBJType.Wall;
		sprites = setupSheet("/objects/wallSheet", 4, 1);
		collision = false;
		setHitbox(0,0,48,48);
		useCost = 2;
		ID = "Wall";
		alive = true;
		maxLife = 40;
		life = maxLife;
	}
	
	public void use(Entity entity) {
		if(haveResource(entity)) {
			
			direction = entity.direction;
			switch(entity.direction) {
			case Idle:
				offsetY = 48 + entity.speed;
				image = sprites[0];
				setHitbox(0,0,48,48);
				break;
			case Down:
				offsetY = 48 + entity.speed;
				image = sprites[0];
				setHitbox(0,0,48,48);
				break;
			case Up:
				offsetY = -(48 + entity.speed);
				image = sprites[1];
				setHitbox(0,8,48,40);
				break;
			case Left:
				offsetX = -(48 + entity.speed);
				image = sprites[2];
				setHitbox(8,0,32,48);
				break;
			case Right:
				offsetX = 48 + entity.speed;
				image = sprites[3];
				setHitbox(8,0,32,48);
				break;
			default:
				break;
			}
			
			worldX = entity.worldX + offsetX;
			worldY = entity.worldY + offsetY;
			
			entity.mana -= useCost;
		
			boolean tiled = gp.cChecker.checkWalls(this) || gp.cChecker.checkTileB(this) || gp.cChecker.checkEntityB(this, gp.monster) || gp.cChecker.checkEntityB(this, gp.obj) ||  gp.cChecker.checkEntityB(this, gp.npc);
			if(tiled) {
				entity.mana += useCost;
				
				alive = false;
			}
		}
		
		gp.walls.add(this);
		collision = true;
		
	}
	
	public boolean haveResource(Entity user) {
		boolean haveResource = false;
		if(user.mana >= useCost) {
			haveResource = true;
		}
		return haveResource;
	}
	
}
