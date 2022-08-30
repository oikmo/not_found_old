package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

import main.*;

public class Player extends Entity {
	KeyHandler keyH;
	public BufferedImage shadow;
	public final int screenX;
	public final int screenY;
	ArrayList<String> inventory = new ArrayList<String>();
	// ArrayList<String> items = new ArrayList<String>();
	boolean moving = false;
	int pixelCounter = 0;

	public Player(GamePanel gp, KeyHandler keyH) {
		super(gp);
		this.keyH = keyH;

		screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
		screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

		solidArea = new Rectangle();
		solidArea.x = 1;
		solidArea.y = 1;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 46;
		solidArea.height = 46;

		setDefaultValues();
		getPlayerImage();
	}

	public void setDefaultValues() {
		worldX = gp.tileSize * 16;
		worldY = gp.tileSize * 16;
		speed = 4;
		direction = "idle";
		
		//player status
		maxLife = 6;
		life = maxLife;
	}

	public void getPlayerImage() {
		shadow = setup("/player/shadow");
		idle1 = setup("/player/idle_1");
		idle2 = setup("/player/idle_2");
		idle3 = setup("/player/idle_3");
		idle4 = setup("/player/idle_4");
		idle5 = setup("/player/idle_5");
		idle6 = setup("/player/idle_6");
		up1 = setup("/player/up_1");
		up2 = setup("/player/up_2");
		up3 = setup("/player/up_3");
		up4 = setup("/player/up_4");
		up5 = setup("/player/up_5");
		up6 = setup("/player/up_6");
		down1 = setup("/player/down_1");
		down2 = setup("/player/down_2");
		down3 = setup("/player/down_3");
		down4 = setup("/player/down_4");
		down5 = setup("/player/down_5");
		down6 = setup("/player/down_6");
		left1 = setup("/player/left_1");
		left2 = setup("/player/left_2");
		left3 = setup("/player/left_3");
		left4 = setup("/player/left_4");
		left5 = setup("/player/left_5");
		left6 = setup("/player/left_6");
		right1 = setup("/player/right_1");
		right2 = setup("/player/right_2");
		right3 = setup("/player/right_3");
		right4 = setup("/player/right_4");
		right5 = setup("/player/right_5");
		right6 = setup("/player/right_6");
	}

	public void update() {

		if (moving == false) {
			if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true
					|| keyH.rightPressed == true) {
				if (keyH.upPressed == true) {
					direction = "up";
				} else if (keyH.downPressed == true) {
					direction = "down";
				} else if (keyH.leftPressed == true) {
					direction = "left";
				} else if (keyH.rightPressed == true) {
					direction = "right";
				}

				moving = true;

				// check tile collision
				collisionOn = false;
				gp.cChecker.checkTile(this);

				// check obj collision
				int objIndex = gp.cChecker.checkObject(this, true);
				pickUpObject(objIndex);
				
				int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
				interactNPC(npcIndex);
				//check event
				gp.eHandler.checkEvent();
				
				
			} else {
				direction = "idle";
				spriteCounter++;

				if (spriteCounter > 12) {
					if (spriteNum > 0) {

						spriteNum++;

					}
					if (spriteNum > 6) {
						spriteNum = 1;
					}
					spriteCounter = 0;
				}
			}
		}

		if (moving == true) {
			// if collision false player move
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

		// System.out.println(inventory);

	}

	public void pickUpObject(int i) {
		if (i != 99) {
			String objName = gp.obj[i].name;
			switch (objName) {
			case "Key1":
				gp.ui.showMessage("you have key", 120, 6.9F, 11);
				gp.playSE(1);
				inventory.add(gp.obj[i].name);
				gp.obj[i] = null;
				break;
			case "Key2":
				gp.ui.showMessage("you have key", 120, 6.9F, 11);
				gp.playSE(1);
				inventory.add(gp.obj[i].name);
				gp.obj[i] = null;
				break;
			case "Door1":
				if (inventory.contains("Key1")) {
					gp.playSE(0);
					inventory.remove("Key1");
					gp.obj[i] = null;
				}
				break;
			case "Door2":
				if (inventory.contains("Key2")) {
					gp.playSE(0);
					inventory.remove("Key2");
					gp.obj[i] = null;
				}
				break;
			}
		}
	}
	
	public void interactNPC(int i) {
		if(i != 999) {
			if(gp.keyH.enterPressed == true) {
				gp.gameState = gp.dialogueState;   
				gp.npc[i].speak();
			}
			
		}
		gp.keyH.enterPressed = false;
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

		g2.drawImage(image, screenX, screenY, null);

	}

}
