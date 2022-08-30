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
		if(hit(2,2,"any") == true) {damagePit(gp.dialogueState, 1);}
		if(hit(1,1, "up") == true) {healPool(gp.dialogueState);}
		if(hit(3,3, "any") == true) {tp(gp.dialogueState, 10, 10);}
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
	
	public void healPool(int gameState) {
		System.out.println("not enter");
		if(gp.keyH.enterPressed == true) {
			System.out.println("enter");
			gp.gameState = gameState;
			gp.ui.currentDialogue = "you uh drank the liquid\n and you are good";
			gp.player.life = gp.player.maxLife;
		}
		gp.keyH.enterPressed = false;
	}
	
	public void tp(int gameState, int x, int y) {
		gp.gameState = gameState;
		gp.ui.currentDialogue = "you got tp'd loser";
		gp.player.worldX = gp.tileSize*x;
		gp.player.worldY = gp.tileSize*y;
	}
}
