package entity;

import java.awt.*;
import java.awt.image.*;

public class Entity {
	public int worldX, worldY;
	public int speed;
	
	public BufferedImage idle1, idle2, idle3, idle4, idle5, idle6;
	public BufferedImage up1, up2, up3, up4, up5, up6;
	public BufferedImage down1, down2, down3, down4, down5, down6;
	public BufferedImage left1, left2, left3, left4, left5, left6;
	public BufferedImage right1, right2, right3, right4, right5, right6;
	public String direction;
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
	public Rectangle solidArea;
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collisionOn = false;
}
