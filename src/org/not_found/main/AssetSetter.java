package org.not_found.main;

import java.io.IOException;

import org.not_found.entity.npc.*;
import org.not_found.entity.monster.*;
import org.not_found.object.*;

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
		//MAP_00
		setupOBJ(0, 0, new OBJ_Key(gp), 2, 16, "Key1", 1);
		setupOBJ(1, 0, new OBJ_Key(gp), 47, 1, "Key2", 2);
		setupOBJ(2, 0, new OBJ_Key(gp), 18, 30, "Key3", 3);	
		setupOBJ(3, 0, new OBJ_Door(gp), 44, 2, "Door1", 1);
		setupOBJ(4, 0, new OBJ_Door(gp), 12, 30, "Door2", 2);
		//setupOBJ(5, 0, new OBJ_Door(gp), 27, 38, "Door3", 3);
		setupOBJ(6, 0, new OBJ_Door(gp), 26, 49, "DoorEXIT");
		
		//MAP_01
		setupOBJ(0, 1, new OBJ_Key(gp), 10, 10, "Key1", 1);
		setupOBJ(1, 1, new OBJ_Key(gp), 22, 5, "Key2", 2);
		setupOBJ(2, 1, new OBJ_Door(gp), 24, 12, "Door1", 1);
		setupOBJ(3, 1, new OBJ_Door(gp), 22, 19, "Door2", 2);
		setupOBJ(4, 1, new OBJ_Chest(gp), 13, 13, "Chest0");
	}

	public void setNPC() throws IOException {
		//MAP_00
		
		//MAP_01
		setupNPC(0, 1, new NPC_Dupe(gp), 8, 12, "DUPE");
		setupNPC(1, 1, new NPC_TEST(gp), 1, 6, "TEST");
	}
	
	public void setMonster() {
		//MAP_00
		setupMonster(0, 0, new MON_EYE(gp), 21, 1);
		setupMonster(1, 0, new MON_EYE(gp), 29, 6);
		
		setupMonster(2, 0, new MON_EYE(gp), 40, 9);
		setupMonster(3, 0, new MON_EYE(gp), 46, 12);
		setupMonster(4, 0, new MON_EYE_ARROW(gp), 39, 17);
		setupMonster(5, 0, new MON_EYE(gp), 18, 19);
		setupMonster(6, 0, new MON_EYE(gp), 15, 26);
		setupMonster(7, 0, new MON_EYE(gp), 25, 24);
		setupMonster(8, 0, new MON_EYE(gp), 21, 33);
		
		setupMonster(9, 0, new MON_EYE(gp), 2, 31);
		setupMonster(10, 0, new MON_EYE(gp), 2, 33);
		setupMonster(11, 0, new MON_EYE_ARROW(gp), 2, 35);
		setupMonster(12, 0, new MON_EYE(gp), 2, 37);
		
		setupMonster(13, 0, new MON_EYE(gp), 38, 23);
		setupMonster(14, 0, new MON_EYE(gp), 35, 24);
		setupMonster(15, 0, new MON_EYE_ARROW(gp), 34, 26);
		setupMonster(16, 0, new MON_EYE(gp), 35, 29);
		setupMonster(17, 0, new MON_EYE(gp), 40, 28);
		setupMonster(18, 0, new MON_EYE(gp), 40, 26);
		
		//MAP_01
		setupMonster(0, 1, new MON_EYE_ARROW(gp), 3, 6);
		setupMonster(1, 1, new MON_EYE_ARROW(gp), 6, 2);
	}
	
	
	public void setupMonster(int index, int map, MONSTER monster, int x, int y) {
		gp.monster[map][index] = monster;
		gp.monster[map][index].worldX = gp.tileSize*x;
		gp.monster[map][index].worldY = gp.tileSize*y;
	}
	
	public void setupNPC(int index, int map, NPC npc, int x, int y, String name) {
		gp.npc[map][index] = npc;
		gp.npc[map][index].worldX = gp.tileSize*x;
		gp.npc[map][index].worldY = gp.tileSize*y;
		gp.npc[map][index].npcName = name;
	}
	
	public void setupOBJ(int index, int map, OBJ obj, int x, int y) {
		gp.obj[map][index] = obj;
		gp.obj[map][index].worldX = gp.tileSize * x;
		gp.obj[map][index].worldY = gp.tileSize * y;
	}
	
	public void setupOBJ(int index, int map, OBJ obj, int x, int y, String ID) {
		gp.obj[map][index] = obj;
		gp.obj[map][index].worldX = gp.tileSize * x;
		gp.obj[map][index].worldY = gp.tileSize * y;
		gp.obj[map][index].ID = ID;
	}
	
	public void setupOBJ(int index, int map, OBJ obj, int x, int y, String ID, int lockID) {
		gp.obj[map][index] = obj;
		gp.obj[map][index].worldX = gp.tileSize * x;
		gp.obj[map][index].worldY = gp.tileSize * y;
		gp.obj[map][index].ID = ID;
		gp.obj[map][index].lockID = lockID;
	}
}
