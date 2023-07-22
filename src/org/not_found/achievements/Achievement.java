package org.not_found.achievements;

import java.awt.*;
import java.awt.image.BufferedImage;

import org.not_found.main.GamePanel;

public class Achievement {
	
	BufferedImage image;
	public String name;
	public boolean completed = false;
	
	public Achievement(BufferedImage image, String name) {
		this.image = image;
		this.name = name;
	}
	
	public void drawAchievement(GamePanel gp, int y) {
		Graphics2D g2 = gp.g2;
		
		int frameY = y;
		int frameWidth = gp.tileSize*7;
		int frameHeight = gp.tileSize*2;
		int frameX = gp.tileSize;
		int tailX = (frameX + gp.getWidth()) - frameWidth/5;
		int x = tailX - frameWidth;
		gp.ui.drawSubWindow(x, frameY, frameWidth, frameHeight, 2);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN).deriveFont(17f));
		g2.setColor(new Color(190, 193, 0));
		g2.drawString("Achievement unlocked!",x + 17, (frameHeight/2) + y - 20);
		g2.setColor(Color.white);
		int imageY = (int)(frameHeight/2) + y - 15;
		g2.setStroke(new BasicStroke(1));
		g2.drawRect(x+17, imageY, image.getWidth(), image.getHeight());
		g2.setColor(new Color(1f,1f,1f,0.5f));
		g2.fillRect(x+17, imageY, image.getWidth(), image.getHeight());
		g2.drawImage(image, x+17, imageY, gp);
		g2.setColor(Color.white);
		
		int textY = imageY + 17;
		for(String line : name.split("\n")) {
			g2.drawString(line, x + 20 + image.getWidth() + 5, textY);
			textY += 16;
		}
	}
	
}
