package main;

import java.awt.*;

public class UI {
	
	GamePanel gp;
	Font roman;
	public UI(GamePanel gp) {
		this.gp = gp;
		
		roman = new Font("TimesRoman", Font.BOLD, 15);
	}
	public void draw(Graphics2D g2) {
		g2.setFont(roman);
		g2.setColor(Color.red);
		g2.drawString("FPS : " + gp.fpsCount, 0, 13);
	}
	
}
