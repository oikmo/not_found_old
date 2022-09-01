package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;

import entity.Entity;
import object.OBJ_Heart;


public class UI {
	Graphics2D g2;
	GamePanel gp;
	public Font VCR;
	BufferedImage hFull, hHalf, hBlank;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter;
	int messageCMax;
	float mX;
	float mY;
	public String currentDialogue = "";
	public int npcCounter;
	public int commandNum = 0;
	boolean ifBoys = false;
	BufferedImage image = null;
	int count = 0;
	int XX = 11;
	
	double playTime;
	DecimalFormat dForm = new DecimalFormat("#0");
	UtilityBox uTool = new UtilityBox();
	
	public UI(GamePanel gp) {
		
		
		this.gp = gp;
		try{
	        image = ImageIO.read(getClass().getResource("/theBoys.png/"));
		} catch(IOException e)	{
	    	e.printStackTrace();
	    } catch(Exception e){
	    	e.printStackTrace();
	    	}
		
		try {
			InputStream is = getClass().getResourceAsStream("/fonts/VCR.ttf");
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
		message = text;
		messageOn = true;
		this.messageCMax = messageCMax;
		this.mX = mX;
		this.mY = mY;
	}
	
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		g2.setFont(VCR);
		if(gp.gameState == gp.titleState) {
			//gp.player.setDefaultValues();
			drawTScreen();
			//drawERR("");
		}
		else if (gp.gameState == gp.playState) {
			if(gp.theBoys) {				
				if(count<2520) {
					count++;
				}
				
				if(count>=500) {
					if(!gp.STOPPLAYER) {
						drawTheBoys();
						gp.STOPPLAYER = true;
					}
				}
				
				if(count>=2520) {
					XX = 10000;
				}
				if(count>=2520) {
					count = 0;
				}
			}
			drawPLife();
			//g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
			g2.setFont(VCR);
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
			g2.setColor(Color.red);
			g2.drawString("FPS:" + gp.fpsCount, 0, gp.tileSize/3);
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
			g2.setColor(Color.white);
			playTime +=(double)1/60;
			String text = "Time : "+ dForm.format(playTime);
			int x = getXfCT(text);
			g2.drawString(text, x*1.9F, gp.tileSize);
		} else if(gp.gameState == gp.pauseState){
			drawPScreen();
		} else if(gp.gameState == gp.dialogueState) {
			drawDScreen(npcCounter);
		}

		if(messageOn == true) {
			g2.setFont(g2.getFont().deriveFont(20F));
			g2.drawString(message, gp.tileSize*mX, gp.tileSize*mY);
			
			messageCounter++;
			if (messageCounter > messageCMax) {
				messageCounter = 0;
				messageOn = false;
			}
		}
		
	}
	public void drawPLife() {
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
			if(i<gp.player.life) {
				g2.drawImage(hFull, x, y, null);
			}
			i++;
			x += gp.tileSize;
		}
	}
	
	public void drawTScreen() {
		g2.setColor(Color.black);
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		String text = "not_found";
		
		g2.setFont(VCR);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,96F));
		int x = getXfCT(text);
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
		x = getXfCT(text);
		y += gp.tileSize*4;
		g2.drawString(text, x, y);
		if(commandNum == 0) {
			g2.drawString(">", x-gp.tileSize, y);
		}
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 48F));
		text = "LOAD SAVE";
		x = getXfCT(text);
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
		x = getXfCT(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if(commandNum == 2) {
			g2.drawString(">", x-gp.tileSize, y);
			drawERR("not available");
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 48F));
		}
		
		text = "QUIT";
		x = getXfCT(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if(commandNum == 3) {
			g2.drawString(">", x-gp.tileSize, y);
		}
		
		
	}
	
	public void drawPScreen() {
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(30F));
		String text = "PAUSED";
		int x = getXfCT(text);
		int y = gp.screenHeight/12;
		g2.drawString(text, x, y);
	}
	
	public void drawTheBoys() {
		ifBoys = true;
		if(ifBoys) {
			g2.drawImage(image, gp.tileSize*XX, gp.tileSize, gp.tileSize*4, gp.tileSize*2, null);
		}
	}

	public void drawDScreen(int i) {
		//window
		//StringBuilder sbf = new StringBuilder();
		int x = gp.tileSize*2;
		int y = gp.tileSize/2;
		int width = gp.screenWidth - (gp.tileSize*4);
		int height = gp.tileSize*5;
		g2.drawString(gp.npc[i].npcName, x, y);
		
		drawSW(x, y, width, height);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
		x += gp.tileSize;
		y += gp.tileSize;
		for(String line : currentDialogue.split("\n")) {
			g2.drawString(line, x, y);
			y += 30;
			
		}
	}
	
	public void drawSW(int x, int y, int width, int height) {
		Color c = new Color(0,0,0, 220);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		c = new Color(255, 255, 255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
		
	}
	//getXforCenteredText
	public int getXfCT(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		return x;
	}
	
	public void drawERR(String text) {
		g2.setColor(Color.red);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
		int x = getXfCT(text);
		int y = gp.tileSize*1;
		g2.drawString(text, x, y); 
		
	}
}
