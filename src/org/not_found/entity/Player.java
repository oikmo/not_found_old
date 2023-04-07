package org.not_found.entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.not_found.main.GamePanel;
import org.not_found.object.*;

public class Player extends Entity {
	public BufferedImage shadow;
	public final int screenX, screenY;
	ArrayList<String> inventory = new ArrayList<String>();
	int atkSpriteCounter, atkSpriteNum;
	public boolean stopAttacking = false;

	public Player(GamePanel gp) {
		super(gp);
		screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
		screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

		hitBox = new Rectangle();
		hitBox.x = 8;
		hitBox.y = 1;
		solidAreaDefaultX = hitBox.x;
		solidAreaDefaultY = hitBox.y;
		hitBox.width = 32;
		hitBox.height = 46;

		attackArea.width = 36;
		attackArea.height = 36;

		setDefaultValues();
		getPlayerImage();
		getAtkImage();

	}

	public void setDefaultValues() {
		worldX = gp.tileSize * 16;
		worldY = gp.tileSize * 16;
		speed = 4;
		direction = "idle";
		
		// player status
		level = 1;
		maxLife = 6;
		life = maxLife;
		strength = 1; // the higher the strength. the higher the damage
		dexterity = 1; // the higher the uh (what is it? oh dex- dexitrry? no? dexterity? oh ok.) dexterity the lesser the damage
		exp = 0;
		nextLevelExp = 5;
		coin = 0;
		currentWeapon = new OBJ_Sword_Normal(gp);
		currentShield = new OBJ_Shield_Wood(gp);
		attack = getAttack();
		defense = getDefense();
	}
	
	public int getAttack() {
		return attack = strength * currentWeapon.attackValue;
	}
	
	public int getDefense() {
		return defense = dexterity * currentShield.defenseValue;
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
	
	public void getAtkImage() {
		atkLeft1 = setup("/player/attack_left_1", gp.tileSize * 2, gp.tileSize);
		atkLeft2 = setup("/player/attack_left_2", gp.tileSize * 2, gp.tileSize);
		atkRight1 = setup("/player/attack_right_1", gp.tileSize * 2, gp.tileSize);
		atkRight2 = setup("/player/attack_right_2", gp.tileSize * 2, gp.tileSize);
		atkUp1 = setup("/player/attack_up_1", gp.tileSize, gp.tileSize * 2);
		atkUp2 = setup("/player/attack_up_2", gp.tileSize, gp.tileSize * 2);
		atkDown1 = setup("/player/attack_down_1", gp.tileSize, gp.tileSize * 2);
		atkDown2 = setup("/player/attack_down_2", gp.tileSize, gp.tileSize * 2);
	}

	public void update() {
		if(life <= 0) {
			gp.gameState = gp.titleState;
			setDefaultValues();
		}
		
		if (attacking) {
			attacking();
		}
		else if (gp.keyH.upPressed || gp.keyH.downPressed || gp.keyH.leftPressed || gp.keyH.rightPressed || gp.keyH.enterPressed) {
			if (gp.keyH.upPressed) {
				direction = "up";
			} else if (gp.keyH.downPressed) {
				direction = "down";
			} else if (gp.keyH.leftPressed) {
				direction = "left";
			} else if (gp.keyH.rightPressed) {
				direction = "right";
			}

			// check tile collision
			collisionOn = false;
			gp.cChecker.checkTile(this);
			// check obj collision
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);
			int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
			interactNPC(npcIndex);
			// check monster collision
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			contactMon(monsterIndex);
			// check event
			gp.eHandler.checkEvent();
			
			// if collision false player move
			if (!collisionOn) {
				//footsteps.loop();
				//footsteps.play();
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
			
			if(gp.keyH.enterPressed && !stopAttacking && direction != "idle")  {
				gp.playSE(8);
				attacking = true;
				spriteCounter = 0;
			}
			
			stopAttacking = false;
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
		}
		else {
			//footsteps.stop();
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
		
		if (isInvince) {
			invinceCounter++;
			if (invinceCounter > 30) {
				isInvince = false;
				invinceCounter = 0;
			}
		}
	}
	
	public void damageMon(int i) {
		if (i != 999) {
			if (!gp.monster[i].isInvince) {
				gp.playSE(7);
				gp.monster[i].life -= attack;
				gp.monster[i].isInvince = true;
				gp.monster[i].damageReaction();

				if (gp.monster[i].life <= 0) {
					gp.monster[i].dying = true;
				}
			}
		}
	}

	public void attacking() {
		spriteCounter++;
		if(spriteCounter <= 5) {
			spriteNum = 1;
		}
		if (spriteCounter > 5 && spriteCounter <= 20) { // SHOW SECOND ATTACK IMAGE FOR 25 FRAMES
			spriteNum = 2;

			// TEMP VARIABLES - Save the current worldX/Y, solidArea
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			int solidAreaWidth = hitBox.width;
			int solidAreaHeight = hitBox.height;

			// Adjust player's worldX/Y for the attackArea
			switch (direction) {
			case "up": worldY -= attackArea.height; break;
			case "down": worldY += attackArea.height; break;
			case "left": worldX -= attackArea.width; break;
			case "right": worldX += attackArea.width; break;
			}

			// attackArea becomes solidArea
			hitBox.width = attackArea.width;
			hitBox.height = attackArea.height;

			// Check monster collison with the updated worldX/Y, solidArea
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			damageMon(monsterIndex);

			// Restore worldX/Y, solidArea
			worldX = currentWorldX;
			worldY = currentWorldY;
			hitBox.width = solidAreaWidth;
			hitBox.height = solidAreaHeight;
		}
		
		if(spriteCounter > 20) {
			//System.out.println("FUCKCXLKVJ");
			spriteNum = 1;
			spriteCounter = 0;
			attacking = false;
		}
	}

	public void pickUpObject(int i) {
		if (i != 99) {
			String objName = gp.obj[i].name;
			switch (objName) {
			case "Key1":
				gp.ui.showMessage("you have key", 120, gp.maxScreenCol/2.35f, 13);
				gp.playSE(4);
				inventory.add(gp.obj[i].name);
				gp.obj[i] = null;
				break;
			case "Key2":
				gp.ui.showMessage("you have key", 120, gp.maxScreenCol/2.35f, 13);
				gp.playSE(4);
				inventory.add(gp.obj[i].name);
				gp.obj[i] = null;
				break;
			case "Door1":
				if (inventory.contains("Key1")) {
					gp.playSE(3);
					inventory.remove("Key1");
					gp.obj[i] = null;
				}
				break;
			case "Door2":
				if (inventory.contains("Key2")) {
					gp.playSE(3);
					inventory.remove("Key2");
					gp.obj[i] = null;
				}
				break;
			}
		}
	}

	public void interactNPC(int i) {
		if (gp.keyH.enterPressed) {
			if (i != 999) {
				stopAttacking = true;
				gp.gameState = gp.dialogueState;
				gp.npc[i].speak();
			}
		}
	}

	public void contactMon(int i) {
		if (i != 999) {
			if (!isInvince) {
				if (invinceCounter <= 0) {
					//System.out.println("call!");
					gp.playSE(7);
					life -= 1;
					isInvince = true;
				}
			}

		}
	}

	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		
		switch (direction) {
		case "idle":
			if (spriteNum == 1) { image = idle1; }
			if (spriteNum == 2) { image = idle2; }
			if (spriteNum == 3) { image = idle3; }
			if (spriteNum == 4) { image = idle4; }
			if (spriteNum == 5) { image = idle5; }
			if (spriteNum == 6) { image = idle6; }
			break;
		case "up":
			if(!attacking) {
				if (spriteNum == 1) { image = up1; }
				if (spriteNum == 2) { image = up2; }
				if (spriteNum == 3) { image = up3; }
				if (spriteNum == 4) { image = up4; }
				if (spriteNum == 5) { image = up5; }
				if (spriteNum == 6) { image = up6; }
			} else {
				tempScreenY = screenY - gp.tileSize;
				if (spriteNum == 1) { image = atkUp1; }
				if (spriteNum == 2) { image = atkUp2; }
			}
			
			break;
		case "down":
			if(!attacking) {
				if (spriteNum == 1) { image = down1; }
				if (spriteNum == 2) { image = down2; }
				if (spriteNum == 3) { image = down3; }
				if (spriteNum == 4) { image = down4; }
				if (spriteNum == 5) { image = down5; }
				if (spriteNum == 6) { image = down6; }
			} else {
				if (spriteNum == 1) { image = atkDown1; }
				if (spriteNum == 2) { image = atkDown2; }
			}
			break;
		case "left":
			if(!attacking) {
				if (spriteNum == 1) {image = left1; }
				if (spriteNum == 2) { image = left2; }
				if (spriteNum == 3) { image = left3; }
				if (spriteNum == 4) { image = left4; }
				if (spriteNum == 5) { image = left5; }
				if (spriteNum == 6) { image = left6; }
			} else {
				tempScreenX = screenX - gp.tileSize;
				if (spriteNum == 1) { image = atkLeft1; }
				if (spriteNum == 2) { image = atkLeft2; }
			}
			
			break;
		case "right":
			if(!attacking) {
				if (spriteNum == 1) { image = right1; }
				if (spriteNum == 2) { image = right2; }
				if (spriteNum == 3) { image = right3; }
				if (spriteNum == 4) { image = right4; }
				if (spriteNum == 5) { image = right5; }
				if (spriteNum == 6) { image = right6; }
			} else {
				if (spriteNum == 1) { image = atkRight1; }
				if (spriteNum == 2) { image = atkRight2; }
			}
			
			break;
		}
		
		if(screenX > worldX) tempScreenX = worldX - (screenX - tempScreenX);

	    if(screenY > worldY) tempScreenY = worldY - (screenY - tempScreenY);

	    int rightOffset = gp.screenWidth - screenX;
        if(rightOffset > gp.worldWidth - worldX) {
        	tempScreenX = (gp.screenWidth - (gp.worldWidth - worldX)) - (screenX - tempScreenX);
        }
        int bottomOffset = gp.screenHeight - screenY;
        if(bottomOffset > gp.worldHeight - worldY) {
        	tempScreenY = (gp.screenHeight - (gp.worldHeight - worldY)) - (screenY - tempScreenY);
        }

		
		if(gp.debug) {
			g2.setFont(gp.ui.VCR);
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
			g2.setColor(Color.red);
			if(gp.monster[0] != null) {
				g2.drawString("monster life: " + gp.monster[0].life, 10, 300);
			}
			
			g2.drawString("invince: " + invinceCounter, 10, 250);
			
			g2.setColor(Color.white);
			Stroke oldStroke = g2.getStroke();
			g2.setStroke(new BasicStroke(2f));
			
			//System.out.println(attackArea.x + " " + attackArea.y);
			g2.drawRect(tempScreenX + hitBox.x, tempScreenY + hitBox.y, hitBox.width, hitBox.height);
			
			g2.setStroke(oldStroke);
		}
		
		if (isInvince) {
			System.out.println("run!");
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		}
		
		//g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));

		g2.drawImage(image, tempScreenX, tempScreenY, null);
	}

}
