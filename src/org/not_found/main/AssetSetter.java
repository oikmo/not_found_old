package org.not_found.main;

import java.io.IOException;

import org.not_found.entity.npc.*;
import org.not_found.entity.monster.*;
import org.not_found.object.*;
import org.not_found.object.tools.OBJ_Syringe;
import org.not_found.object.ui.*;

public class AssetSetter {
	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setAll() throws IOException {
		setObject();
		setNPC();
		setMonster();
	}
	
	public void setObject() {
		OBJ obj = new OBJ_Key(gp);
		obj.ID = "Key1";
		gp.player.inventory.add(obj);
		
		gp.obj[gp.currentMap][0] = new OBJ_Key(gp);
		gp.obj[gp.currentMap][0].worldX = 22 * gp.tileSize;
		gp.obj[gp.currentMap][0].worldY = 5 * gp.tileSize;
		gp.obj[gp.currentMap][0].ID = "Key2";
		
		gp.obj[gp.currentMap][1] = new OBJ_Syringe(gp);
		gp.obj[gp.currentMap][1].worldX = 2 * gp.tileSize;
		gp.obj[gp.currentMap][1].worldY = 6 * gp.tileSize;
		
		gp.obj[gp.currentMap][2] = new OBJ_Door(gp);
		gp.obj[gp.currentMap][2].worldX = 22 * gp.tileSize;
		gp.obj[gp.currentMap][2].worldY = 21 * gp.tileSize;
		gp.obj[gp.currentMap][2].ID = "Door1";
		
		gp.obj[gp.currentMap][3] = new OBJ_Mana(gp);
		gp.obj[gp.currentMap][3].worldX = 11 * gp.tileSize;
		gp.obj[gp.currentMap][3].worldY = 8 * gp.tileSize;
	}

	public void setNPC() throws IOException {
		gp.npc[gp.currentMap][0] = new NPC_Dupe(gp);
		gp.npc[gp.currentMap][0].worldX = gp.tileSize*8;
		gp.npc[gp.currentMap][0].worldY = gp.tileSize*12;
		gp.npc[gp.currentMap][0].npcName = "DUPE";
		
		gp.npc[gp.currentMap][1] = new NPC_TEST(gp);
		gp.npc[gp.currentMap][1].worldX = gp.tileSize*1;
		gp.npc[gp.currentMap][1].worldY = gp.tileSize*6;
		gp.npc[gp.currentMap][1].npcName = "TEST";
		
		gp.npc[gp.currentMap][2] = new NPC_BLANK(gp);
		gp.npc[gp.currentMap][2].worldX = gp.tileSize*1;
		gp.npc[gp.currentMap][2].worldY = gp.tileSize*1;
		gp.npc[gp.currentMap][2].npcName = "";
		
	}
	
	public void setMonster() {
		gp.monster[gp.currentMap][0] = new MON_EYE(gp);
		gp.monster[gp.currentMap][0].worldX = gp.tileSize*3;
		gp.monster[gp.currentMap][0].worldY = gp.tileSize*6;
		
		gp.monster[gp.currentMap][1] = new MON_EYE(gp);
		gp.monster[gp.currentMap][1].worldX = gp.tileSize*6;
		gp.monster[gp.currentMap][1].worldY = gp.tileSize*2;
	}
}
