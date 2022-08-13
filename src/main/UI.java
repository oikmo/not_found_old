package main;

import java.awt.*;

public class UI {
	
	GamePanel gp;
	Font roman;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter;
	int messageCMax;
	int mX;
	int mY;
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		roman = new Font("TimesRoman", Font.BOLD, 15);
	}
	public void showMessage(String text, int messageCMax, int mX, int mY) {
		message = text;
		messageOn = true;
		this.messageCMax = messageCMax;
		this.mX = mX;
		this.mY = mY;
	}
	
	public void draw(Graphics2D g2) {
		g2.setFont(roman);
		g2.setColor(Color.red);
		g2.drawString("FPS : " + gp.fpsCount, 0, 13);
		
		if(messageOn == true) {
			g2.setFont(g2.getFont().deriveFont(30F));
			g2.drawString(message, gp.tileSize*mX, gp.tileSize*mY);
			
			messageCounter++;
			if (messageCounter > messageCMax) {
				messageCounter = 0;
				messageOn = false;
			}
		}
	}
	
}
