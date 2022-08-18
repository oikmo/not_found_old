package main;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import javax.swing.text.html.parser.Entity;

public class UI {
	Graphics2D g2;
	GamePanel gp;
	Font roman, VCR;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter;
	int messageCMax;
	float mX;
	float mY;
	public String currentDialogue = "";
	public int npcCounter;
	
	double playTime;
	DecimalFormat dForm = new DecimalFormat("#0");
	UtilityBox uTool = new UtilityBox();
	
	public UI(GamePanel gp) {
		this.gp = gp;
		try {
			InputStream is = getClass().getResourceAsStream("/fonts/VCR.ttf");
			VCR = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
		if (gp.gameState == gp.playState) {
			//g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
			g2.setFont(VCR);
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
			g2.setColor(Color.red);
			g2.drawString("FPS : " + gp.fpsCount, 0, 13);
			g2.setFont(VCR);
			g2.setColor(Color.white);
			playTime +=(double)1/60;
			g2.drawString("Time : "+ dForm.format(playTime), gp.tileSize*14, 39);
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
	
	public void drawPScreen() {
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(30F));
		String text = "PAUSED";
		int x;
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		x = gp.screenWidth/2 - length/2;
		int y = gp.screenHeight/12;
		g2.drawString(text, x, y);
	}
	
	public void drawDScreen(int i) {
		//window
		//StringBuilder sbf = new StringBuilder();
		int x = gp.tileSize*2;
		int y = gp.tileSize/2;
		int width = gp.screenWidth - (gp.tileSize*4);
		int height = gp.tileSize*5;
		g2.drawString(gp.npc[i].name, x, y);
		
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
	
}
