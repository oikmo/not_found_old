package org.not_found.event;

import org.not_found.entity.Entity.Direction;
import org.not_found.main.GamePanel;

public class EventHandler {
	GamePanel gp;
	EventRect eventRect[][];
	int previousEventX, previousEventY;
	boolean canTouchEvent = true;
	
	public EventHandler(GamePanel gp) {
		this.gp = gp;
		
		eventRect= new EventRect[gp.maxWorldCol][gp.maxWorldRow];
		
		int col = 0;
		int row = 0;
		while(col < gp.maxWorldCol && row<gp.maxWorldCol) {
			eventRect[col][row] = new EventRect();
			eventRect[col][row].x = 23;
			eventRect[col][row].y = 23;
			eventRect[col][row].width = 2;
			eventRect[col][row].height = 2;
			eventRect[col][row].eRDX = eventRect[col][row].x;
			eventRect[col][row].eRDY = eventRect[col][row].y;
			col++;
			if(col == gp.maxWorldCol) {
				col = 0;
				row++;
			}
		}
		
		
	}
	
	public void checkEvent() {
		//if player one tile away from last event
		int xDist = Math.abs(gp.player.worldX -previousEventX);
		int yDist = Math.abs(gp.player.worldY -previousEventY);
		int dist = Math.max(xDist, yDist);
		if(dist > gp.tileSize) {
			canTouchEvent = true;
		}
		if(canTouchEvent) {
			if(hit(2,2, Direction.Any)) {damagePit(2,2, gp.dialogueState, 1, true);}
			if(hit(1,1, Direction.Any)) {healPool(1,1, gp.dialogueState, false);}
		}
		
		
		//if(hit(3,3, "any") == true) {tp(gp.dialogueState, 10, 10);}
	}
	
	public boolean hit(int col, int row, Direction reqDir) {
		boolean hit = false;
		
		gp.player.hitBox.x += gp.player.worldX;
		gp.player.hitBox.y += gp.player.worldY;
		eventRect[col][row].x = col*gp.tileSize + eventRect[col][row].x;
		eventRect[col][row].y = row*gp.tileSize + eventRect[col][row].y;
		
		if(gp.player.hitBox.intersects(eventRect[col][row]) && !eventRect[col][row].eventDone) {
			if(gp.player.direction == reqDir || reqDir.equals(Direction.Any)) {
				hit = true;
				
				previousEventX = gp.player.worldX;
				previousEventY = gp.player.worldY;
			}
		}
		
		gp.player.hitBox.x = gp.player.solidAreaDefaultX;
		gp.player.hitBox.y = gp.player.solidAreaDefaultY;
		eventRect[col][row].x = eventRect[col][row].eRDX;
		eventRect[col][row].y = eventRect[col][row].eRDY;
		
		return hit;
	}
	
	public void damagePit(int col, int row, int gameState, int dmg, boolean isOnce) {
		gp.gameState = gameState;
		gp.playSE(7);
		gp.ui.currentDialogue = "you fall in pit idiot";
		gp.ui.npcCounter = 2;
		gp.player.life -= dmg;
		eventRect[col][row].eventDone = isOnce;
	}
	
	public void healPool(int col, int row, int gameState, boolean isOnce) {
		if(gp.keyH.enterPressed) {
			gp.gameState = gameState;
			gp.playSE(9);
			gp.player.stopAttacking = true;
			gp.ui.currentDialogue = "you uh drank the liquid \nand you are good";
			gp.player.life = gp.player.maxLife;
			eventRect[col][row].eventDone = isOnce;
		}
		gp.keyH.enterPressed = false;
	}
	
	/*public void tp(int gameState, int x, int y) {
		gp.gameState = gameState;
		gp.ui.currentDialogue = "you got tp'd loser";
		gp.player.worldX = gp.tileSize*x;
		gp.player.worldY = gp.tileSize*y;
	}*/
}
