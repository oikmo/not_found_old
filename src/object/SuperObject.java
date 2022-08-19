package object;

import java.awt.*;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.UtilityBox;

public class SuperObject {
	public BufferedImage image1, image2, image3;
	public String name;
	public boolean collision = false;
	public int worldX, worldY;
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
	public int solidAreaDefaultX = 0;
	public int solidAreaDefaultY = 0;
	UtilityBox uTool = new UtilityBox();
	// public boolean canPickUp = false;

	public void draw(Graphics2D g2, GamePanel gp) {

		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;

		// STOP MOVING CAMERA
		if (gp.player.worldX < gp.player.screenX) {
			screenX = worldX;
		}
		if (gp.player.worldY < gp.player.screenY) {
			screenY = worldY;
		}
		int rightOffset = gp.screenWidth - gp.player.screenX;
		if (rightOffset > gp.worldWidth - gp.player.worldX) {
			screenX = gp.screenWidth - (gp.worldWidth - worldX);
		}
		int bottomOffset = gp.screenHeight - gp.player.screenY;
		if (bottomOffset > gp.worldHeight - gp.player.worldY) {
			screenY = gp.screenHeight - (gp.worldHeight - worldY);
		}
		///////////////////

		if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
				&& worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
				&& worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
				&& worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

			g2.drawImage(image1, screenX, screenY, gp.tileSize, gp.tileSize, null);
		}
		// If player is around the edge, draw everything
		else if (gp.player.worldX < gp.player.screenX || gp.player.worldY < gp.player.screenY
				|| rightOffset > gp.worldWidth - gp.player.worldX || bottomOffset > gp.worldHeight - gp.player.worldY) {
			g2.drawImage(image1, screenX, screenY, gp.tileSize, gp.tileSize, null);
		}
	}
}
