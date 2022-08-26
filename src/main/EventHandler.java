package main;

import java.awt.*;

public class EventHandler {
	GamePanel gp;
	Rectangle eventRect;
	//eventRectDefaultX, eventRectDefaultY;
	int eRDX, eRDY;
	
	public EventHandler(GamePanel gp) {
		this.gp = gp;
		
		eventRect = new Rectangle();
		eventRect.x = 23;
		eventRect.y = 23;
		eventRect.width = 2;
		eventRect.height = 2;
		eRDX = eventRect.x;
		eRDY = eventRect.y;
	}
	
	public void checkEvent() {
		if(hit(1,1,"any") == true) {damagePit(gp.dialogueState, 1);}
	}
	
	public boolean hit(int eventCol, int eventRow, String reqDir) {
		boolean hit = false;
		
		gp.player.solidArea.x += gp.player.worldX;
		gp.player.solidArea.y += gp.player.worldY;
		eventRect.x = eventCol*gp.tileSize+eventRect.x;
		eventRect.y = eventCol*gp.tileSize+eventRect.y;
		
		if(gp.player.solidArea.intersects(eventRect)) {
			if(gp.player.direction.contentEquals(reqDir) || reqDir.contentEquals("any")) {
				hit = true;
			}
		}
		
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		eventRect.x = eRDX;
		eventRect.y = eRDY;
		
		return hit;
	}
	
	public void damagePit(int gameState, int dmg) {
		gp.gameState = gameState;
		gp.ui.currentDialogue = "you fall in pit idiot";
		gp.ui.npcCounter = 2;
		gp.player.life -= dmg;
		
	}
}
