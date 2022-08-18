package main;

import entity.*;
import object.*;

public class AssetSetter {
	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {
		gp.obj[0] = new OBJ_Key(gp);
		gp.obj[0].worldX = 10 * gp.tileSize;
		gp.obj[0].worldY = 10 * gp.tileSize;
		gp.obj[0].name = "Key1";
		//gp.obj[0].canPickUp = true;
		
		gp.obj[1] = new OBJ_Door(gp);
		gp.obj[1].worldX = 24 * gp.tileSize;
		gp.obj[1].worldY = 12 * gp.tileSize;
		gp.obj[1].name = "Door1";
		//gp.obj[1].canPickUp = false;
		
		gp.obj[2] = new OBJ_Chest(gp);
		gp.obj[2].worldX = 13 * gp.tileSize;
		gp.obj[2].worldY = 13 * gp.tileSize;
		//gp.obj[2].canPickUp = false;
		
		gp.obj[3] = new OBJ_Key(gp);
		gp.obj[3].worldX = 5 * gp.tileSize;
		gp.obj[3].worldY = 5 * gp.tileSize;
		gp.obj[3].name = "Key2";
		
		gp.obj[4] = new OBJ_Door(gp);
		gp.obj[4].worldX = 22 * gp.tileSize;
		gp.obj[4].worldY = 19 * gp.tileSize;
		gp.obj[4].name = "Door2";
		
	}

	public void setNPC() {
		gp.npc[0] = new NPC_Dupe(gp);
		gp.npc[0].worldX = gp.tileSize*8;
		gp.npc[0].worldY = gp.tileSize*12;
		
		gp.npc[1] = new NPC_TEST(gp);
		gp.npc[1].worldX = gp.tileSize*3;
		gp.npc[1].worldY = gp.tileSize*6;
		
	}
}
