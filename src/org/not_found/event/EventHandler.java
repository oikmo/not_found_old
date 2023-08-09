package org.not_found.event;

import org.not_found.entity.Entity.Direction;
import org.not_found.main.GamePanel;

public class EventHandler {
	GamePanel gp;
	EventRect eventRect[][][];
	int previousEventX, previousEventY;
	boolean canTouchEvent = true;
	
	public EventHandler(GamePanel gp) {
		this.gp = gp;
		
		eventRect= new EventRect[gp.maxMaps][gp.maxWorldCol][gp.maxWorldRow];
		
		int map = 0;
		int col = 0;
		int row = 0;
		while(map < gp.maxMaps && col < gp.maxWorldCol && row<gp.maxWorldCol) {
			eventRect[map][col][row] = new EventRect();
			eventRect[map][col][row].x = 23;
			eventRect[map][col][row].y = 23;
			eventRect[map][col][row].width = 2;
			eventRect[map][col][row].height = 2;
			eventRect[map][col][row].eRectDefaultX = eventRect[map][col][row].x;
			eventRect[map][col][row].eRectDefaultY = eventRect[map][col][row].y;
			col++;
			if(col == gp.maxWorldCol) {
				col = 0;
				row++;
				
				if(row == gp.maxWorldRow) {
					row = 0;
					map++;
				}
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
			if(hit(0,2,2, Direction.Any)) {damagePit(0,2,2, 1, true);}
			if(hit(0,1,1, Direction.Any)) {healPool(0,1,1, false);}
		}
		
		
		//if(hit(3,3, "any") == true) {tp(gp.dialogueState, 10, 10);}
	}
	
	public boolean hit(int map, int col, int row, Direction reqDir) {
		boolean hit = false;
		
		if(map == gp.currentMap) {
			gp.player.hitBox.x += gp.player.worldX;
			gp.player.hitBox.y += gp.player.worldY;
			eventRect[map][col][row].x = col*gp.tileSize + eventRect[map][col][row].x;
			eventRect[map][col][row].y = row*gp.tileSize + eventRect[map][col][row].y;
			
			if(gp.player.hitBox.intersects(eventRect[map][col][row]) && !eventRect[map][col][row].eventDone) {
				if(gp.player.direction == reqDir || reqDir.equals(Direction.Any)) {
					hit = true;
					
					previousEventX = gp.player.worldX;
					previousEventY = gp.player.worldY;
				}
			}
			
			gp.player.hitBox.x = gp.player.solidAreaDefaultX;
			gp.player.hitBox.y = gp.player.solidAreaDefaultY;
			eventRect[map][col][row].x = eventRect[map][col][row].eRectDefaultX;
			eventRect[map][col][row].y = eventRect[map][col][row].eRectDefaultY;
		}
		
		
		
		return hit;
	}
	
	public void damagePit(int map, int col, int row, int dmg, boolean isOnce) {
		gp.gameState = gp.dialogueState;
		gp.playSE(7);
		gp.ui.currentDialogue = "you fall in pit idiot";
		gp.ui.npcCounter = 2;
		gp.player.life -= dmg;
		eventRect[map][col][row].eventDone = isOnce;
	}
	
	public void healPool(int map, int col, int row, boolean isOnce) {
		if(gp.keyH.enterPressed) {
			gp.gameState = gp.dialogueState;
			gp.playSE(9);
			gp.player.stopAttacking = true;
			gp.ui.currentDialogue = "you uh drank the liquid \nand you are good";
			gp.player.life = gp.player.maxLife;
			eventRect[map][col][row].eventDone = isOnce;
		}
		gp.keyH.enterPressed = false;
	}
	
	public void tp(int map, int col, int row) {
		gp.gameState = gp.dialogueState;
		gp.ui.currentDialogue = "you got tp'd loser";
		gp.player.worldX = gp.tileSize*col;
		gp.player.worldY = gp.tileSize*row;
		previousEventX = gp.player.worldX;
		previousEventY = gp.player.worldY;
		canTouchEvent = false;
	}
}
