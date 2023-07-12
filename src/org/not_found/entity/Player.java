package org.not_found.entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import org.not_found.main.GamePanel;
import org.not_found.main.SoundEnum;
import org.not_found.object.*;

public class Player extends Entity {
	public BufferedImage shadow;
	public final int screenX, screenY;
	public ArrayList<OBJ> inventory = new ArrayList<>();
	public final int maxInventorySize = 20;
	int atkSpriteCounter, atkSpriteNum;
	public boolean stopAttacking = false;

	public Player(GamePanel gp) {
		super(gp);
		
		entityType = EntityType.Player;
		
		screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
		screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
		
		hitBox = new Rectangle();
		hitBox.x = 8;
		hitBox.y = 1;
		solidAreaDefaultX = hitBox.x;
		solidAreaDefaultY = hitBox.y;
		hitBox.width = 32;
		hitBox.height = 46;

		setDefaultValues();
		getPlayerImage();
		getAttackImage();

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
		setItems();
	}
	
	public void setItems() {
		inventory.add(currentWeapon);
		inventory.add(currentShield);
	}
	
	public int getAttack() {
		attackArea = currentWeapon.attackArea;
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
	
	public void getAttackImage() {
		atkLeft1 = setup("/player/attack_left_1", gp.tileSize * 2, gp.tileSize);
		atkLeft2 = setup("/player/attack_left_2", gp.tileSize * 2, gp.tileSize);
		atkRight1 = setup("/player/attack_right_1", gp.tileSize * 2, gp.tileSize);
		atkRight2 = setup("/player/attack_right_2", gp.tileSize * 2, gp.tileSize);
		atkUp1 = setup("/player/attack_up_1", gp.tileSize, gp.tileSize * 2);
		atkUp2 = setup("/player/attack_up_2", gp.tileSize, gp.tileSize * 2);
		atkDown1 = setup("/player/attack_down_1", gp.tileSize, gp.tileSize * 2);
		atkDown2 = setup("/player/attack_down_2", gp.tileSize, gp.tileSize * 2);
	}

	boolean lockCarry = false;
	public void update() {
		if(gp.ui.message.size() == 0) {
			if(lockCarry) {
				lockCarry = false;
			}
		}
		if(life <= 0) {
			try {
				gp.setupGame();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
			} else {
				direction = "idle";
			}
			if(gp.keyH.enterPressed && !stopAttacking && direction != "idle")  {
				gp.playSE(SoundEnum.swingWeapon);
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
	
	public void contactMon(int i) {
		if (i != 999) {
			if (!isInvince) {
				if (invinceCounter <= 0) {
					//System.out.println("call!");
					gp.playSE(SoundEnum.recieveDmg);
					
					int damage = gp.monster[i].attack - defense;
					if(damage < 0) {
						damage = 0;
					}
					
					life -= damage;
					isInvince = true;
				}
			}
		}
	}
	
	public void damageMon(int i) {
		if (i != 999) {
			if (!gp.monster[i].isInvince) {
				
				gp.playSE(SoundEnum.swingWeapon);
				
				int damage = attack - gp.monster[i].defense;
				if(damage < 0) {
					damage = 0;
				}
				//System.out.println(damage);
				
				gp.monster[i].life -= damage;
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
	
	int getXforText(String string) {
		return gp.ui.getXforCenteredText(string) / gp.tileSize;
	}
	
	void displayMessage(int i, OBJ item) {
		OBJ object = gp.obj[i];
		EntityType type = object.entityType;
		switch(type) {
		case Key:
			gp.playSE(SoundEnum.key);
			inventory.add(object);
			gp.ui.showMessage("you have key", 120, getXforText("you have key"), 1);
			break;
		case Door:
			gp.playSE(SoundEnum.door);
			gp.ui.showMessage("you opened door", 120, getXforText("you opened door"), 1);
			if(item != null) {
				inventory.remove(item);
			}
			break;
		default:
			break;
		}
		
		gp.obj[i] = null;
	}
	
	public void pickUpObject(int i) {
		if (i != 99) {
			if(inventory.size() != maxInventorySize) {
				switch (gp.obj[i].ID) {
				case "Key1":
					displayMessage(i, null);
					break;
				case "Key2":
					displayMessage(i, null);
					break;
				case "Door1":
					if (inventory.contains(OBJItems.Key1)) {
						displayMessage(i, OBJItems.Key1);
					}
					break;
				case "Door2":
					if (inventory.contains(OBJItems.Key2)) {
						displayMessage(i, OBJItems.Key2);
					}
					break;
				}
			} else {
				if(!lockCarry) {
					gp.ui.showMessage("You cant carry anymo", 40, getXforText("You cant carry anymo"), 1);
					lockCarry = true;
				}	
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

	public void selectItem() {
		int itemIndex = gp.ui.getItemIndexOnSlot();
		
		if(itemIndex < inventory.size()) {
			OBJ selectedItem = inventory.get(itemIndex);
			
			if(selectedItem.entityType == EntityType.Weapon) {
				currentWeapon = selectedItem;
				attack = getAttack();
			}
			
			if(selectedItem.entityType == EntityType.Shield) {
				currentShield = selectedItem;
				defense = getDefense();
			}
			
			if(selectedItem.entityType == EntityType.Eatable) {
				// later :3
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
			g2.drawString("invince: " + invinceCounter, 10, 250);
			
			g2.setColor(Color.white);
			Stroke oldStroke = g2.getStroke();
			g2.setStroke(new BasicStroke(2f));
			
			g2.drawRect(tempScreenX + hitBox.x, tempScreenY + hitBox.y, hitBox.width, hitBox.height);
			
			g2.setStroke(oldStroke);
		}
		
		if (isInvince) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		}

		g2.drawImage(image, tempScreenX, tempScreenY, null);
	}

}
