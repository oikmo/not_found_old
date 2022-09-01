package entity;

import java.awt.*;
import java.awt.image.*;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityBox;

public class Entity {
	GamePanel gp;
	// position
	public int worldX, worldY;
	public int speed;
	// animation
	public BufferedImage idle1, idle2, idle3, idle4, idle5, idle6;
	public BufferedImage up1, up2, up3, up4, up5, up6;
	public BufferedImage down1, down2, down3, down4, down5, down6;
	public BufferedImage left1, left2, left3, left4, left5, left6;
	public BufferedImage right1, right2, right3, right4, right5, right6;
	public String direction = "idle";
	public int spriteCounter = 0;
	public int spriteNum = 1;
	// collision
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collisionOn = false;
	//movement (grid based)
	public boolean moving = false;
	int pixelCounter = 0;
	public int actionLockCounter = 0;
	//dialogues
	String dialogues[] = new String[100];
	int dialogueIndex = 0;
	public String npcName;
	//objs
	public BufferedImage image1, image2, image3;
	public String name;
	public boolean collision = false;
	//damage
	public boolean isInvince = false;
	public int invinceCounter = 0;
	
	public int monType;
	
	//char status
	public int maxLife;
	public int life;

	public Entity(GamePanel gp) {
		this.gp = gp;
	}

	public void setAction() {}
	public void speak() {}
	public void update() {
		
		collisionOn = false;
		gp.cChecker.checkTile(this);
		gp.cChecker.checkObject(this, false);
		gp.cChecker.checkEntity(this, gp.npc);
		gp.cChecker.checkEntity(this, gp.monster);
		boolean contactPlayer = gp.cChecker.checkPlayer(this);
		
		if(this.monType == 0 && contactPlayer) {
			if(!gp.player.isInvince) {
				gp.player.life -= 1;
				gp.player.isInvince = true;
			}
		}
		
		
		if (moving == false) {
			setAction();
		}
		
		//System.out.println(moving);
		if(moving == true) {
			
			collisionOn = false;
			gp.cChecker.checkTile(this);
			if (collisionOn == false) {
				switch (direction) {
				case "up":
					worldY -= speed;
					break;
				case "down":
					worldY += speed;
					break;
				case "left":
					worldX -= speed;
					break;
				case "right":
					worldX += speed;
					break;
				}
			}

			spriteCounter++;
			if (spriteCounter > 8) {
				if (spriteNum > 0) {
					spriteNum++;
				}
				if (spriteNum > 6) {
					spriteNum = 1;
				}
				spriteCounter = 0;
				
			}
			pixelCounter += speed;
			// System.out.println(pixelCounter);
			if (pixelCounter == 48) {
				moving = false;
				pixelCounter = 0;
			}
		}
		
	}
	public void draw(Graphics2D g2) {

		BufferedImage image = null;
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

		switch (direction) {
		case "idle":
			if (spriteNum == 1) {
				image = idle1;
			}
			if (spriteNum == 2) {
				image = idle2;
			}
			if (spriteNum == 3) {
				image = idle3;
			}
			if (spriteNum == 4) {
				image = idle4;
			}
			if (spriteNum == 5) {
				image = idle5;
			}
			if (spriteNum == 6) {
				image = idle6;
			}
			break;
		case "up":
			if (spriteNum == 1) {
				image = up1;
			}
			if (spriteNum == 2) {
				image = up2;
			}
			if (spriteNum == 3) {
				image = up3;
			}
			if (spriteNum == 4) {
				image = up4;
			}
			if (spriteNum == 5) {
				image = up5;
			}
			if (spriteNum == 6) {
				image = up6;
			}
			break;
		case "down":
			if (spriteNum == 1) {
				image = down1;
			}
			if (spriteNum == 2) {
				image = down2;
			}
			if (spriteNum == 3) {
				image = down3;
			}
			if (spriteNum == 4) {
				image = down4;
			}
			if (spriteNum == 5) {
				image = down5;
			}
			if (spriteNum == 6) {
				image = down6;
			}
			break;
		case "left":
			if (spriteNum == 1) {
				image = left1;
			}
			if (spriteNum == 2) {
				image = left2;
			}
			if (spriteNum == 3) {
				image = left3;
			}
			if (spriteNum == 4) {
				image = left4;
			}
			if (spriteNum == 5) {
				image = left5;
			}
			if (spriteNum == 6) {
				image = left6;
			}
			break;
		case "right":
			if (spriteNum == 1) {
				image = right1;
			}
			if (spriteNum == 2) {
				image = right2;
			}
			if (spriteNum == 3) {
				image = right3;
			}
			if (spriteNum == 4) {
				image = right4;
			}
			if (spriteNum == 5) {
				image = right5;
			}
			if (spriteNum == 6) {
				image = right6;
			}
			break;
		}

		if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
				&& worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
				&& worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
				&& worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		}
		// If player is around the edge, draw everything
		else if (gp.player.worldX < gp.player.screenX || gp.player.worldY < gp.player.screenY
				|| rightOffset > gp.worldWidth - gp.player.worldX || bottomOffset > gp.worldHeight - gp.player.worldY) {
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		}
	}

	public BufferedImage setup(String imageName) {
		UtilityBox uTool = new UtilityBox();
		BufferedImage image = null;

		try {
			image = ImageIO.read(getClass().getResourceAsStream(imageName + ".png"));
			image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return image;
	}
}