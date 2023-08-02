package org.not_found.main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.not_found.achievements.Achievement;
import org.not_found.object.ui.*;

public class UI {
	Graphics2D g2;
	GamePanel gp;
	
	public Font VCR;
	
	BufferedImage heartFull, heartHalf, heartBlank;
	BufferedImage manaFull, manaBlank;
	
	public boolean messageOn = false;
	public ArrayList<String> message = new ArrayList<>();
	public ArrayList<Integer> messageCounter = new ArrayList<>();
	public ArrayList<Integer> mCounterLimit = new ArrayList<>();
	public ArrayList<Achievement> achievements = new ArrayList<>();
	public ArrayList<Integer> achieveCounter = new ArrayList<>();
	
	int messageCMax, messageX, messageY;
	
	public String currentDialogue = "";
	public int npcCounter;
	public int commandNum = 0;
	public int slotCol = 0;
	public int slotRow = 0;
	int subState = 0;

	public UI(GamePanel gp) {
		this.gp = gp;
		try {
			File is = new File(Main.gameDir + "/res/fonts/VCR.ttf");
			VCR = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//create HUD objs
		OBJ_Heart heart = new OBJ_Heart(gp);
		heartFull = heart.image0;
		heartHalf = heart.image1;
		heartBlank = heart.image2;
		
		OBJ_Mana mana = new OBJ_Mana(gp);
		manaFull = mana.image0;
		manaBlank = mana.image1;
	}

	public void draw(Graphics2D g2) {
		this.g2 = g2;
		g2.setFont(VCR);
		if(gp.gameState == gp.loadingState) {
			drawLoadingScreen();
		}else if(gp.gameState == gp.titleState) {
			drawTitleScreen();
		}
		else if (gp.gameState == gp.playState) {
			drawPlayerThings();
			drawMessages();
			drawAchievements();
			if(gp.debug || gp.fps) {
				g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
				g2.setColor(Color.red);
				g2.drawString("FPS:" + gp.fpsCount, 0, gp.tileSize/3);
			}
			
		} else if(gp.gameState == gp.pauseState){
			drawPauseScreen();
		} else if(gp.gameState == gp.dialogueState) {
			drawDialogueScreen(npcCounter);
		} else if(gp.gameState == gp.characterState) {
			drawCharacterScreen();
			drawInventory();
		} else if(gp.gameState == gp.optionsState) {
			drawOptionsScreen();
		} else if(gp.gameState == gp.gameOverState) {
			drawGAMEOVERScreen();
		}
		
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,20F));
		g2.drawString(gp.version, gp.tileSize-40f, gp.tileSize-20f);
		
	}
	
	public void addAchievement(Achievement achievement) {
		achievements.add(achievement);
		achieveCounter.add(0);
	}
	
	public void drawAchievements() {
		int messageYY = 10;
		
		for(int i = 0; i < achievements.size(); i++) {
			if(achievements.get(i) != null) { 
				if(!achievements.get(i).completed) {
					achievements.get(i).drawAchievement(gp, messageYY);
					
					
					int counter = achieveCounter.get(i) + 1;
					
					achieveCounter.set(i, counter);
					messageYY += gp.tileSize*2 + 10;
					
					if(achieveCounter.get(i) > 120) {
						achievements.get(i).completed = true;
						achievements.remove(i);
						achieveCounter.remove(i);
					}
				}
				
			}
		}
	}
	
	public void addMessage(String text, int messageCMax, int messageY) {
		message.add(text);
		messageCounter.add(0);
		mCounterLimit.add(messageCMax);
		
		messageOn = true;
		this.messageCMax = messageCMax;
		this.messageY = messageY;
	}
	
	public void addMessage(String text, int messageCMax) {
		message.add(text);
		messageCounter.add(0);
		mCounterLimit.add(messageCMax);
		
		messageOn = true;
		this.messageCMax = messageCMax;
		this.messageX = gp.tileSize;
		this.messageY = gp.tileSize * 2;
	}
	
	public void addMessage(String text) {
		message.add(text);
		messageCounter.add(0);
		mCounterLimit.add(120);
		
		messageOn = true;
		this.messageCMax = 120;
		this.messageX = getXforCenteredText(text);
		this.messageY = gp.tileSize * 2;
	}
	
	public void drawMessages() {
		g2.setFont(VCR);
		g2.setFont(g2.getFont().deriveFont(20F));
		
		float messageYY = (gp.tileSize*messageY);
		//System.out.println("yeah");
		for(int i = 0; i < message.size(); i++) {
			if(message.get(i) != null) {
				if(messageX == 0) {
					g2.setColor(Color.gray);
					g2.drawString(message.get(i), getXforCenteredText(message.get(i))+2, (messageYY)+2);
					g2.setColor(Color.white);
					g2.drawString(message.get(i), getXforCenteredText(message.get(i)), messageYY);
					
					int counter = messageCounter.get(i) + 1;
					
					messageCounter.set(i, counter);
					messageYY += 50;
					
					if(messageCounter.get(i) > mCounterLimit.get(i)) {
						message.remove(i);
						messageCounter.remove(i);
					}
				} else {
					g2.setColor(Color.gray);
					g2.drawString(message.get(i), messageX+2, (messageYY)+2);
					g2.setColor(Color.white);
					g2.drawString(message.get(i), messageX, messageYY);
					
					int counter = messageCounter.get(i) + 1;
					
					messageCounter.set(i, counter);
					messageYY += 50;
					
					if(messageCounter.get(i) > mCounterLimit.get(i)) {
						message.remove(i);
						messageCounter.remove(i);
					}
				}
				
			}
			
			
		}
	}
	
	public void drawPlayerThings() {
		drawPlayerLife();
		drawPlayerMana();
	}
	
	public void drawPlayerLife() {
		int x = gp.tileSize/2;
		int y = gp.tileSize/2;
		int i = 0;
		//draw max life
		while(i < gp.player.maxLife/2) {
			g2.drawImage(heartBlank, x, y, null);
			i++;
			x += gp.tileSize;
		}
		//reset
		x = gp.tileSize/2;
		y = gp.tileSize/2;
		i = 0;
		//draw current life
		while(i<gp.player.life) {
			g2.drawImage(heartHalf, x, y, null);
			i++;
			if(i<gp.player.life) { g2.drawImage(heartFull, x, y, null); }
			i++;
			x += gp.tileSize;
		}
	}
	
	public void drawPlayerMana() {
		if(gp.player.mana == 0) { return; }
		
		int x = gp.tileSize/2;
		int y = (int)((int)gp.tileSize*1.5f);
		int i = 0;
		//draw max life
		while(i < gp.player.maxMana) {
			g2.drawImage(manaBlank, x, y, null);
			i++;
			x += gp.tileSize;
		}
		//reset
		x = gp.tileSize/2;
		y = (int)((int)gp.tileSize*1.5f);
		i = 0;
		//draw current life
		while(i<gp.player.mana) {
			if(i<gp.player.mana) { g2.drawImage(manaFull, x, y, null); }
			i++;
			x += gp.tileSize;
		}
	}
	
	public void drawInventory() {
		//frame
		int frameX = gp.tileSize*9;
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize*6;
		int frameHeight = gp.tileSize*5;
		
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		//slot
		final int slotXstart = frameX + 20;
		final int slotYstart = frameY + 20;
		int slotX = slotXstart;
		int slotY = slotYstart;
		int slotSize = gp.tileSize+3;
		
		//cursor
		int cursorX = slotXstart + (slotSize * slotCol);
		int cursorY = slotYstart + (slotSize * slotRow);
		int cursorWidth = gp.tileSize;
		int cursorHeight = gp.tileSize;
		

		//draw cursor
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(3));
		g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
		g2.setColor(new Color(1f, 1f, 1f, 0.2f));
		g2.setStroke(new BasicStroke(0));
		g2.fillRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

		//draw players stuff
		for(int i = 0; i < gp.player.inventory.size(); i++) {
			//equip cursor (HOORAYY)
			if(gp.player.inventory.get(i) == gp.player.currentWeapon || gp.player.inventory.get(i) == gp.player.currentShield) {
				g2.setColor(new Color(240,190,90));
				g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
			}
			gp.player.inventory.get(i).update();
			g2.drawImage(gp.player.inventory.get(i).image, slotX, slotY, null);
			
			slotX += slotSize;
			if(i == 4 || i == 9 || i == 14) {
				slotX = slotXstart;
				slotY += slotSize;
			}
		}
		
		//descripton
		int dFrameX = frameX;
		int dFrameY = frameY + frameHeight + gp.tileSize;
		int dFrameWidth = frameWidth;
		int dFrameHeight = gp.tileSize*3;
		
		// draw desc text
		int textX = dFrameX + 13;
		int textY = dFrameY + (gp.tileSize / 2) + 2;
		g2.setFont(g2.getFont().deriveFont(16F));
		
		int itemIndex = getItemIndexOnSlot();
		
		
		
		if(itemIndex < gp.player.inventory.size()) {
			drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
			String name = gp.player.inventory.get(itemIndex).description.split(" \n")[0];
			g2.drawString(name, textX, textY);
			textY += 32;
			g2.setFont(g2.getFont().deriveFont(15F));
			String desc = gp.player.inventory.get(itemIndex).description.split(" \n")[1];
			for(String line : desc.split("\n")) {
				g2.drawString(line, textX, textY);
				textY += 16;
			}
		}
	}
	
	public int getItemIndexOnSlot() {
		return slotCol + (slotRow*5);
	}
	
	public void drawLoadingScreen() {
		g2.setColor(Color.black);
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		String text = "LOADING";
		
		g2.setFont(VCR);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,96F));
		int x = getXforCenteredText(text);
		int y = gp.screenHeight / 2;
		g2.setColor(Color.gray);
		g2.drawString(text, x+5, y+5);
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
		/*g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 96F/5f));
		String text2 = "(might be downloading and unzipping files rn)";
		
		int x1 = getXforCenteredText(text2);
		int y1 = (gp.screenHeight + gp.tileSize) / 2;
		g2.setColor(Color.gray);
		g2.drawString(text2, x1+2, y1+2);
		g2.setColor(Color.white);
		g2.drawString(text2, x1, y1);	*/
	}
	
	public void drawTitleScreen() {
		g2.setColor(Color.black);
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		String text = "not_found";
		
		g2.setFont(VCR);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,96F));
		int x = getXforCenteredText(text);
		int y = gp.tileSize*3;
		g2.setColor(Color.gray);
		g2.drawString(text, x+5, y+5);
		
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
		//System.out.println((gp.screenWidth/2) - (gp.tileSize + (gp.tileSize*2) + 16));
		x = (gp.getTWidth() - gp.tileSize*5)/2;
		y += gp.tileSize;
		g2.drawImage(gp.player.shadow, x,(int) (y - gp.tileSize), gp.tileSize*5, gp.tileSize*5, null);
		
		//menu
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 48F));
		text = "NEW GAME";
		x = getXforCenteredText(text);
		y += gp.tileSize*5;
		g2.drawString(text, x, y);
		if(commandNum == 0) {
			g2.drawString(">", x-gp.tileSize, y);
		}
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 48F));
		text = "LOAD SAVE";
		x = getXforCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 48F));
		if(commandNum == 1) {
			g2.drawString(">", x-gp.tileSize, y);
			drawERR("not available");
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 48F));
			
		}
		
		text = "OPTIONS";
		x = getXforCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if(commandNum == 2) {
			g2.drawString(">", x-gp.tileSize, y);
			drawERR("not available");
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 48F));
		}
		text = "QUIT";
		x = getXforCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if(commandNum == 3) {
			g2.drawString(">", x-gp.tileSize, y);
		}
	}
	JFrame frame = new JFrame();
	public void drawPauseScreen() {
		g2.setColor(new Color(0,0,0, 0.2f));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(48F));
		String text = "PAUSED";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight/12;
		g2.drawString(text, x, y);
		
		//menu
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30F));
		text = "ACHIEVEMENTS";
		x = getXforCenteredText(text);
		y += (gp.tileSize*4) + 16;
		g2.drawString(text, x, y);
		if(commandNum == 0) {
			g2.drawString(">", x-gp.tileSize, y);
			
			if(gp.keyH.enterPressed) {
				drawERR("not yet.");
				g2.setColor(Color.white);
				g2.setFont(g2.getFont().deriveFont(30F));
			}
		}
		
		text = "OPTIONS";
		x = getXforCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if(commandNum == 1) {
			g2.drawString(">", x-gp.tileSize, y);
			gp.config.loadConfig();
			if(gp.keyH.enterPressed) {
				gp.gameState = gp.optionsState;
			}
		}
		text = "QUIT";
		x = getXforCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if(commandNum == 2) {
			g2.drawString(">", x-gp.tileSize, y);
		}
	}

	public void drawDialogueScreen(int i) {
		//window
		//StringBuilder sbf = new StringBuilder();
		int x = gp.tileSize*2;
		int y = gp.tileSize/2;
		int width = gp.screenWidth - (gp.tileSize*4);
		int height = gp.tileSize*5;
		
		//FontMetrics fm = g2.getFontMetrics();
		//int totalWidth = (fm.stringWidth(gp.npc[i].npcName) * 2) + 4;
		
		
		drawSubWindow(x, y, width, height);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 15F));
		
		
		
		if(currentDialogue.contains("%")) {
			String[] dialogue = currentDialogue.split("%");
			g2.drawString(dialogue[0], x+gp.tileSize/4+1, y + gp.tileSize/ 2);
			
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
			x += gp.tileSize;
			y += gp.tileSize;
			for(String line : dialogue[1].split("\n")) {
				g2.drawString(line, x, y);
				y += 30;
				
			}
		} else {
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
			x += gp.tileSize;
			y += gp.tileSize;
			for(String line : currentDialogue.split("\n")) {
				g2.drawString(line, x, y);
				y += 30;
				
			}
		}
		
		
	}
	
	public void drawCharacterScreen() {	
		//create frame :)
		final int frameX = gp.tileSize*2;
		final int frameY = gp.tileSize;
		final int frameWidth = gp.tileSize*5;
		final int frameHeight = gp.tileSize*11;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		//text
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(24f));
		int textX = frameX + 20;
		int textY = frameY + gp.tileSize;
		final int lineHeight = 32;
		
		//names
		g2.setFont(g2.getFont().deriveFont(30f));
		g2.drawString("Stats", frameX + getXforCenteredText("Stats", frameWidth), textY);
		textY += lineHeight * 1.25f;
		
		g2.setFont(g2.getFont().deriveFont(24f));
		
		g2.drawString("Level", textX, textY);
		textY += lineHeight;
		g2.drawString("Life", textX, textY);
		textY += lineHeight;
		g2.drawString("Mana", textX, textY);
		textY += lineHeight;
		g2.drawString("Strength", textX, textY);
		textY += lineHeight;
		g2.drawString("Dexterity", textX, textY);
		textY += lineHeight;
		g2.drawString("Attack", textX, textY);
		textY += lineHeight;
		g2.drawString("Defense", textX, textY);
		textY += lineHeight;
		g2.drawString("Exp", textX, textY);
		textY += lineHeight;
		g2.drawString("Next Level", textX, textY);
		textY += lineHeight;
		g2.drawString("Coin", textX, textY);
		textY += lineHeight + 20;
		g2.drawString("Weapon", textX, textY);
		textY += lineHeight + 20;
		g2.drawString("Shield", textX, textY);
		
		//values
		int tailX = (frameX + frameWidth) - 30;
		//reset textY
		textY = (int) (frameY + (gp.tileSize * 1.5f) + 16);
		String value;
		
		value = String.valueOf(gp.player.level);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.mana + "/" + gp.player.maxMana);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.strength);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.dexterity);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.attack);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.defense);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.exp);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.nextLevelExp);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.coin);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		g2.drawImage(gp.player.currentWeapon.image, tailX - gp.tileSize, textY-14, null);
		textY += gp.tileSize;
		g2.drawImage(gp.player.currentShield.image, tailX - gp.tileSize, textY-14, null);
		
	}
	
	public void drawOptionsScreen() {
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32F));
		
		//sub window :3
		int frameWidth = gp.tileSize*8;
		int frameHeight = gp.tileSize*10;
		int frameX = (gp.getTWidth() - frameWidth) / 2;
		int frameY = gp.tileSize;
		
		
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		switch(subState) {
		case 0: optionsTop(frameX, frameY); break;
		case 1: optionsControl(frameX, frameY); break;
		}
	}
	BasicStroke stroke = new BasicStroke(3);
	public void optionsTop(int frameX, int frameY) {
		int textX;
		int textY;
		
		//title
		String text = "Options";
		textX = getXforCenteredText(text) ;
		textY = frameY + gp.tileSize;
		g2.drawString(text, textX, textY);
		
		g2.setFont(g2.getFont().deriveFont(16F));
		//full screen on/off
		textX = frameX + gp.tileSize - 16;
		textY += gp.tileSize;
		
		//music
		g2.drawString("Music", textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-15, textY);
		}
		
		//se
		textY += gp.tileSize;
		g2.drawString("SFX", textX, textY);
		if(commandNum == 1) {
			g2.drawString(">", textX-15, textY);
		}
		
		//control
		textY += gp.tileSize;
		g2.drawString("Controls", textX, textY);
		if(commandNum == 2) {
			g2.drawString(">", textX-15, textY);
			if(gp.keyH.enterPressed) {
				subState = 1;
				commandNum = 0;
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		//end game
		/*textY += gp.tileSize;
		g2.drawString("End Game", textX, textY);
		if(commandNum == 3) {
			g2.drawString(">", textX-15, textY);
		}*/
		
		//back
		textY += gp.tileSize * 3;
		g2.drawString("Back", textX, textY);
		if(commandNum == 3) {
			g2.drawString(">", textX-15, textY);
			if(gp.keyH.enterPressed) {
				commandNum = 1;
				gp.gameState = gp.pauseState;
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		//checkbox
		
		
		//g2.setStroke(stroke);
		//g2.drawRect(textX + ((120-24)/2), textY, 24, 24);
		
		//music volume
		textX = frameX + (int)(gp.tileSize*4.5);
		textY = frameY + gp.tileSize + 28;
		g2.drawRect(textX, textY, 120, 24);
		int volumeWidth = 24 * gp.music.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
		
		//se volume
		textY += gp.tileSize;
		g2.drawRect(textX, textY, 120, 24);
		volumeWidth = 24 * gp.se.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
		
		gp.config.saveConfig();
	}
	
	public void optionsControl(int frameX, int frameY) {
		int textX;
		int textY;
		
		//title :)
		g2.setFont(g2.getFont().deriveFont(32F));
		String text = "Control";
		textX = getXforCenteredText(text);
		textY = frameY + gp.tileSize;
		g2.drawString(text, textX, textY);
		
		g2.setFont(g2.getFont().deriveFont(16F));
		textX = frameX + gp.tileSize;
		textY += gp.tileSize;
		g2.drawString("Move", textX, textY); textY += gp.tileSize;
		g2.drawString("Confirm/Attack", textX, textY); textY += gp.tileSize;
		g2.drawString("Shoot/Cast", textX, textY); textY += gp.tileSize;
		g2.drawString("Character Screen", textX, textY); textY += gp.tileSize;
		g2.drawString("Pause", textX, textY); textY += gp.tileSize;
		g2.drawString("Options", textX, textY); textY += gp.tileSize;
		
		textX = frameX + gp.tileSize * 6;
		textY = frameY + gp.tileSize * 2;
		g2.drawString("WASD", textX, textY); textY += gp.tileSize;
		g2.drawString("ENTER", textX, textY); textY += gp.tileSize;
		g2.drawString("F", textX, textY); textY += gp.tileSize;
		g2.drawString("C", textX, textY); textY += gp.tileSize;
		g2.drawString("P", textX, textY); textY += gp.tileSize;
		g2.drawString("ESC", textX, textY); textY += gp.tileSize;
		
		//back
		textX = frameX + gp.tileSize;
		textY = frameY + gp.tileSize * 9;
		g2.drawString("Back", textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX - 25, textY);
			if(gp.keyH.enterPressed) {
				subState = 0;
				gp.config.loadConfig();
			}
		}
	}
	
	public void drawGAMEOVERScreen() {
		g2.setColor(new Color(0,0,0, 0.2f));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		int x, y;
		String text;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD | Font.ITALIC, 110f));
		text = "Game Over";
		
		g2.setColor(Color.gray);
		x = getXforCenteredText(text)+5;
		y = gp.tileSize*4+5;
		g2.drawString(text, x, y);
		g2.setColor(Color.white);
		g2.drawString(text, x-5, y-5);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 50f));
		text = "Retry";
		x = getXforCenteredText(text);
		y += gp.tileSize*4;
		g2.drawString(text, x, y);
		if(commandNum == 0) {
			g2.drawString(">", x-40, y);
		}
		
		text = "Quit";
		x = getXforCenteredText(text);
		y += 55;
		g2.drawString(text, x, y);
		if(commandNum == 1) {
			g2.drawString(">", x-40, y);
		}
		
	}
	public void drawSubWindow(int x, int y, int width, int height) {
		Color c = new Color(0,0,0, 210);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		c = new Color(255, 255, 255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);	
	}
	
	public void drawSubWindow(int x, int y, int width, int height, int stroke) {
		Color c = new Color(0,0,0, 210);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		c = new Color(255, 255, 255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(stroke));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);	
	}
	
	public void drawSubWindow(int x, int y, int width, int height, Color color) {
		Color c = color;
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		c = new Color(255, 255, 255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
		
	}
	/**
	 * Get X For Centered Text
	 * @param text
	 * @return centered x for text
	 */
	public int getXforCenteredText(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = (gp.screenWidth - length)/2;
		return x;
	}
	
	public int getXforCenteredText(String text, int width) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = (width - length)/2;
		return x;
	}
	
	public int getXforAlignToRightText(String text, int tailX) {   
	    int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = tailX - length;
		return x;
	}
	
	public void drawERR(String text) {
		g2.setColor(Color.red);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
		int x = getXforCenteredText(text);
		int y = gp.tileSize*1;
		g2.drawString(text, x, y); 
		
	}
}
