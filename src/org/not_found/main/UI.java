package org.not_found.main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.not_found.entity.Entity;
import org.not_found.object.OBJ_Heart;


public class UI {
	Graphics2D g2;
	GamePanel gp;
	
	public Font VCR;
	
	BufferedImage hFull, hHalf, hBlank;
	
	public boolean messageOn = false;
	public ArrayList<String> message = new ArrayList<>();
	public ArrayList<Integer> messageCounter = new ArrayList<>();
	public ArrayList<Integer> mCounterLimit = new ArrayList<>();
	
	//int messageCounter;
	int messageCMax;
	
	float mX, mY;
	
	public String currentDialogue = "";
	public int npcCounter;
	public int commandNum = 0;
	
	BufferedImage image = null;
	
	public UI(GamePanel gp) {
		
		
		this.gp = gp;
		try{
	        image = ImageIO.read(getClass().getResourceAsStream("/res/misc/theBoys.png"));
		} catch(IOException e)	{
	    	e.printStackTrace();
	    } catch(Exception e){
	    	e.printStackTrace();
	    	}
		
		try {
			InputStream is = getClass().getResourceAsStream("/res/fonts/VCR.ttf");
			VCR = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//create HUD objs
		Entity heart = new OBJ_Heart(gp);
		hFull = heart.image1;
		hHalf = heart.image2;
		hBlank = heart.image3;
	}
	public void showMessage(String text, int messageCMax, float mX, float mY) {
		message.add(text);
		messageCounter.add(0);
		mCounterLimit.add(messageCMax);
		
		messageOn = true;
		this.messageCMax = messageCMax;
		this.mX = mX;
		this.mY = mY;
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
			drawPlayerLife();
			drawMessage();
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
		}

		
		
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,20F));
		g2.drawString(gp.version, gp.tileSize-40f, gp.tileSize-20f);
		
	}
	
	public void drawMessage() {
		g2.setFont(VCR);
		g2.setFont(g2.getFont().deriveFont(20F));
		
		float messageY = (gp.tileSize*mY);
		//System.out.println("yeah");
		for(int i = 0; i < message.size(); i++) {
			if(message.get(i) != null) {
				g2.setColor(Color.gray);
				g2.drawString(message.get(i), (gp.tileSize*mX)+2, (messageY)+2);
				g2.setColor(Color.white);
				g2.drawString(message.get(i), gp.tileSize*mX, messageY);
				
				int counter = messageCounter.get(i) + 1;
				
				messageCounter.set(i, counter);
				messageY += 50;
				
				if(messageCounter.get(i) > mCounterLimit.get(i)) {
					message.remove(i);
					messageCounter.remove(i);
				}
			}
			
			
		}
	}
	
	public void drawPlayerLife() {
		//gp.player.life = 3;
		int x = gp.tileSize/2;
		int y = gp.tileSize/2;
		int i = 0;
		//draw max life
		while(i < gp.player.maxLife/2) {
			g2.drawImage(hBlank, x, y, null);
			i++;
			x += gp.tileSize;
		}
		//reset
		x = gp.tileSize/2;
		y = gp.tileSize/2;
		i = 0;
		//draw current life
		while(i<gp.player.life) {
			g2.drawImage(hHalf, x, y, null);
			i++;
			if(i<gp.player.life) { g2.drawImage(hFull, x, y, null); }
			i++;
			x += gp.tileSize;
		}
	}
	
	public void drawLoadingScreen() {
		g2.setColor(Color.black);
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		String text = "not_found";
		
		g2.setFont(VCR);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,96F));
		int x = getXforCenteredText(text);
		int y = gp.screenHeight / 2;
		g2.setColor(Color.gray);
		g2.drawString(text, x+5, y+5);
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
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
		
		x = gp.screenWidth/2 - (gp.tileSize*2)/2;
		y += gp.tileSize*2.3F;
		g2.drawImage(gp.player.shadow, x, y, gp.tileSize*2, gp.tileSize*2, null);
		
		x = gp.screenWidth/2 - (gp.tileSize*2)/2;
		y += gp.tileSize/4 - (gp.tileSize*1.12);
		g2.drawImage(gp.player.idle1, x, y, gp.tileSize*2, gp.tileSize*2, null);
		
		//menu
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 48F));
		text = "NEW GAME";
		x = getXforCenteredText(text);
		y += gp.tileSize*4;
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
	
	public void drawPauseScreen() {
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(30F));
		String text = "PAUSED";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight/12;
		g2.drawString(text, x, y);
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
		final int frameHeight = gp.tileSize*10;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		//text
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(24f));
		int textX = frameX + 20;
		int textY = frameY + gp.tileSize;
		final int lineHeight = 32;
		
		//names
		g2.drawString("Level", textX, textY);
		textY += lineHeight;
		g2.drawString("Life", textX, textY);
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
		textY = frameY + gp.tileSize;
		String value;
		
		value = String.valueOf(gp.player.level);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
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
		
		g2.drawImage(gp.player.currentWeapon.idle1, tailX - gp.tileSize, textY-14, null);
		textY += gp.tileSize;
		g2.drawImage(gp.player.currentShield.idle1, tailX - gp.tileSize, textY-14, null);
		
	}
	
	public void textXthat() {
		
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
	///**
	 //* Get X For Centered Text
	 //* @return centered x for text
	 //*/
	public int getXforCenteredText(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
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
