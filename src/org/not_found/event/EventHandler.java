package org.not_found.event;

import org.not_found.entity.Entity;
import org.not_found.entity.Entity.Direction;
import org.not_found.main.GamePanel;

public class EventHandler {
	GamePanel gp;
	Entity eventMaster;
	EventRect eventRect[][][];
	int previousEventX, previousEventY;
	boolean canTouchEvent = true;
	
	public EventHandler(GamePanel gp) {
		this.gp = gp;
		
		eventMaster = new Entity(gp);
		
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
		
		setDialogue();
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
			if(hit(1,2,2, Direction.Any)) { damagePit(0,2,2, 1, true); }
			if(hit(1,1,1, Direction.Any)) { healPool(0,1,1, false); }
			if(hit(0,26,48, Direction.Any)) { tp(1, 13, 24); }
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
	
	public void setDialogue() {
		eventMaster.dialogues[0][0] = "you fall in pit idiot";
		eventMaster.dialogues[1][0] = "you uh drank the liquid \nand you are good";
		eventMaster.dialogues[2][0] = "you got tp'd loser";
	}
	
	public void damagePit(int map, int col, int row, int dmg, boolean isOnce) {
		if(eventRect[map][col][row].eventDone != isOnce) {
			gp.gameState = gp.dialogueState;
			gp.playSE(7);
			eventMaster.startDialogue(eventMaster, 0);
			
			gp.player.life -= dmg;
			eventRect[map][col][row].eventDone = isOnce;
		}
		
		
	}
	
	public void healPool(int map, int col, int row, boolean isOnce) {
		if(gp.keyH.enterPressed) {
			gp.gameState = gp.dialogueState;
			gp.playSE(9);
			gp.player.stopAttacking = true;
			eventMaster.startDialogue(eventMaster, 1);
			
			gp.player.life = gp.player.maxLife;
			eventRect[map][col][row].eventDone = isOnce;
		}
	}
	
	public void tp(int map, int col, int row) {
		gp.gameState = gp.dialogueState;
		gp.currentMap = map;
		eventMaster.startDialogue(eventMaster, 2);
		gp.player.worldX = gp.tileSize*col;
		gp.player.worldY = gp.tileSize*row;
		previousEventX = gp.player.worldX;
		previousEventY = gp.player.worldY;
		canTouchEvent = false;
	}
}



