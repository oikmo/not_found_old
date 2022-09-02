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
	boolean moving = false;
	int pixelCounter = 0;
	int ii = 0;
	int atkSpriteCounter;
	int atkSpriteNum;
	
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
		
		attackArea.width = 36;
		attackArea.height = 36;

		setDefaultValues();
		getPlayerImage();
		//getAtkImage();
			
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
		shadow = setup("/player/shadow", gp.tileSize, gp.tileSize);
		idle1 = setup("/player/idle_1", gp.tileSize, gp.tileSize);
		idle2 = setup("/player/idle_2", gp.tileSize, gp.tileSize);
		idle3 = setup("/player/idle_3", gp.tileSize, gp.tileSize);
		idle4 = setup("/player/idle_4", gp.tileSize, gp.tileSize);
		idle5 = setup("/player/idle_5", gp.tileSize, gp.tileSize);
		idle6 = setup("/player/idle_6", gp.tileSize, gp.tileSize);
		up1 = setup("/player/up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/player/up_2", gp.tileSize, gp.tileSize);
		up3 = setup("/player/up_3", gp.tileSize, gp.tileSize);
		up4 = setup("/player/up_4", gp.tileSize, gp.tileSize);
		up5 = setup("/player/up_5", gp.tileSize, gp.tileSize);
		up6 = setup("/player/up_6", gp.tileSize, gp.tileSize);
		down1 = setup("/player/down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/player/down_2", gp.tileSize, gp.tileSize);
		down3 = setup("/player/down_3", gp.tileSize, gp.tileSize);
		down4 = setup("/player/down_4", gp.tileSize, gp.tileSize);
		down5 = setup("/player/down_5", gp.tileSize, gp.tileSize);
		down6 = setup("/player/down_6", gp.tileSize, gp.tileSize);
		left1 = setup("/player/left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/player/left_2", gp.tileSize, gp.tileSize);
		left3 = setup("/player/left_3", gp.tileSize, gp.tileSize);
		left4 = setup("/player/left_4", gp.tileSize, gp.tileSize);
		left5 = setup("/player/left_5", gp.tileSize, gp.tileSize);
		left6 = setup("/player/left_6", gp.tileSize, gp.tileSize);
		right1 = setup("/player/right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/player/right_2", gp.tileSize, gp.tileSize);
		right3 = setup("/player/right_3", gp.tileSize, gp.tileSize);
		right4 = setup("/player/right_4", gp.tileSize, gp.tileSize);
		right5 = setup("/player/right_5", gp.tileSize, gp.tileSize);
		right6 = setup("/player/right_6", gp.tileSize, gp.tileSize);
	}
	
	/*public void getAtkImage() {
		atkLeft1 = setup("/player/attack_left_1", gp.tileSize*2, gp.tileSize);
		atkLeft2 = setup("/player/attack_left_2", gp.tileSize*2, gp.tileSize);
		atkRight1 = setup("/player/attack_right_1", gp.tileSize*2, gp.tileSize);
		atkRight2 = setup("/player/attack_right_2", gp.tileSize*2, gp.tileSize);
		atkUp1 = setup("/player/attack_up_1", gp.tileSize, gp.tileSize*2);
		atkUp2 = setup("/player/attack_up_2", gp.tileSize, gp.tileSize*2);
		atkDown1 = setup("/player/attack_down_1", gp.tileSize, gp.tileSize*2);
		atkDown2 = setup("/player/attack_down_2", gp.tileSize, gp.tileSize*2);
	}*/

	public void update() {
		
		if(attacking) {
			
			if(ii < 60) {
				ii++;
				
				int cWorldX = worldX;
				int cWorldY = worldY;
				int sAW = solidArea.width;
				int sAH = solidArea.height;
				
				switch(direction) {
				case "up": worldY -= attackArea.height; break;
				case "down": worldY += attackArea.height; break;
				case "left": worldX -= attackArea.width; break;
				case "right": worldX += attackArea.width; break;}
				
				solidArea.width = attackArea.width;
				solidArea.height = attackArea.height;
				
				int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
				damageMon(monsterIndex);
				
				worldX = cWorldX;
				worldY = cWorldY;
				solidArea.width = sAW;
				solidArea.height = sAH;
				
				if(ii >= 60) {
					ii = 0;
					attacking = false;
				}
			}
			
			
			
		}
		
		if (!moving) {
			if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed  || keyH.enterPressed) {
				if (keyH.upPressed) {
					direction = "up";
				} else if (keyH.downPressed) {
					direction = "down";
				} else if (keyH.leftPressed) {
					direction = "left";
				} else if (keyH.rightPressed) {
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
				//check momster collision
				int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
				contactMon(monsterIndex);
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

		if (moving) {
			// if collision false player move
			if (!collisionOn && !gp.keyH.enterPressed) {
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
			gp.keyH.enterPressed = false;

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
		
		if(isInvince) {
			invinceCounter++;
			if(invinceCounter > 60) {
				isInvince = false;
				invinceCounter = 0;
			}
		}

		// System.out.println(inventory);

	}
	
	public void damageMon(int i) {
		if(i != 999) {
			if(!gp.monster[i].isInvince ) {
				gp.monster[i].life -= 1;
				gp.monster[i].isInvince = true;
				
				if(gp.monster[i].life <= 0) {
					gp.monster[i] = null;
				}
			}
		}
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
		if(gp.keyH.enterPressed) {
			if(i != 999) {
				gp.gameState = gp.dialogueState;   
				gp.npc[i].speak();
			} else {
				attacking = true;
			}
		}
	}
	
	public void contactMon(int i) {
		if(i != 999) {
			if(!isInvince) {
				if (invinceCounter <= 0) { 
					life -= 1;
					isInvince = true;
				}
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

		if(isInvince) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3F));
		}

		g2.drawImage(image, screenX, screenY, null);
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
		
		g2.setFont(gp.ui.VCR);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
		g2.setColor(Color.red);
		g2.drawString("monster life: " + gp.monster[0].life, 10, 200);
		g2.drawString("invince: " + invinceCounter, 10, 400);
		
	}
	
	
}
