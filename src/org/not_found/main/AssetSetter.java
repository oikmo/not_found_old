package org.not_found.main;

import java.io.IOException;

import org.not_found.entity.npc.*;
import org.not_found.entity.monster.*;
import org.not_found.object.*;
import org.not_found.object.tools.OBJ_Syringe;

public class AssetSetter {
	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setAll() throws IOException {
		setObject();
		setNPC();
	}
	
	@SuppressWarnings("static-access")
	public void setObject() {
		gp.obj[0] = new OBJ_Key(gp);
		gp.obj[0].worldX = 10 * gp.tileSize;
		gp.obj[0].worldY = 10 * gp.tileSize;
		gp.obj[0].ID = "Key1";
		gp.itemList[0] = gp.obj[0];
		
		gp.obj[1] = new OBJ_Key(gp);
		gp.obj[1].worldX = 22 * gp.tileSize;
		gp.obj[1].worldY = 5 * gp.tileSize;
		gp.obj[1].ID = "Key2";
		gp.itemList[1] = gp.obj[1];
		
		gp.obj[2] = new OBJ_Syringe(gp);
		gp.obj[2].worldX = 2 * gp.tileSize;
		gp.obj[2].worldY = 6 * gp.tileSize;
		gp.itemList[2] = gp.obj[2];
		
		gp.obj[3] = new OBJ_Door(gp);
		gp.obj[3].worldX = 24 * gp.tileSize;
		gp.obj[3].worldY = 12 * gp.tileSize;
		gp.obj[3].ID = "Door1";
		
		gp.obj[4] = new OBJ_Door(gp);
		gp.obj[4].worldX = 22 * gp.tileSize;
		gp.obj[4].worldY = 19 * gp.tileSize;
		gp.obj[4].ID = "Door2";
		
		gp.obj[5] = new OBJ_Chest(gp);
		gp.obj[5].worldX = 13 * gp.tileSize;
		gp.obj[5].worldY = 13 * gp.tileSize;
		gp.obj[5].ID = "Chest0";
		
		gp.obj[6] = new OBJ_Wall(gp);
		gp.obj[6].worldX = 10 * gp.tileSize;
		gp.obj[6].worldY = 3 * gp.tileSize;
	}

	public void setNPC() throws IOException {
		gp.npc[0] = new NPC_Dupe(gp);
		gp.npc[0].worldX = gp.tileSize*8;
		gp.npc[0].worldY = gp.tileSize*12;
		gp.npc[0].npcName = "DUPE";
		
		gp.npc[1] = new NPC_TEST(gp);
		gp.npc[1].worldX = gp.tileSize*1;
		gp.npc[1].worldY = gp.tileSize*6;
		gp.npc[1].npcName = "TEST";
		
		gp.npc[2] = new NPC_BLANK(gp);
		gp.npc[2].worldX = gp.tileSize*1;
		gp.npc[2].worldY = gp.tileSize*1;
		gp.npc[2].npcName = "";
		
	}
	
	public void setMonster() {
		gp.monster[0] = new MON_EYE(gp);
		gp.monster[0].worldX = gp.tileSize*3;
		gp.monster[0].worldY = gp.tileSize*6;
		
		gp.monster[1] = new MON_EYE(gp);
		gp.monster[1].worldX = gp.tileSize*6;
		gp.monster[1].worldY = gp.tileSize*2;
	}
}
