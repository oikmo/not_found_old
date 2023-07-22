package org.not_found.entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import org.not_found.achievements.AchieveManager;
import org.not_found.main.GamePanel;
import org.not_found.main.SoundEnum;
import org.not_found.object.*;
import org.not_found.object.tools.OBJ_Consumable;
import org.not_found.object.tools.OBJ_Shield_Wood;
import org.not_found.object.tools.OBJ_Sword_Normal;

public class Player extends Entity {
	public BufferedImage shadow;
	public final int screenX, screenY;
	public ArrayList<OBJ> inventory = new ArrayList<>();
	public final int maxInventorySize = 20;
	int atkSpriteNum;
	public boolean stopAttacking = false;

	public Player(GamePanel gp) {
		super(gp);
		
		entityType = EntityType.Player;
		
		screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
		screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
		
		hitBox = new Rectangle(8, 1, 32, 46);
		solidAreaDefaultX = hitBox.x;
		solidAreaDefaultY = hitBox.y;
		
		setDefaultValues();
		getPlayerImage();
		getAttackImage("player/playerAttackSheet_1", "player/playerAttackSheet_2");
		
	}

	public void setDefaultValues() {
		
		
		worldX = gp.tileSize * 16;
		worldY = gp.tileSize * 16;
		speed = 4;
		
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
		shadow = setup("/player/tScreen", gp.tileSize*2, gp.tileSize*2);
		sprites = setupSheet("player/playerSheet", 6, 5);
	}
	
	public void getAttackImage(String filePath1, String filePath2) {
		atkSprites = new BufferedImage[8];
		BufferedImage[] atk1 = setupSheet(filePath1, 2, 2, gp.tileSize, gp.tileSize * 2);
		//atkLeft1 = setup("/player/attack_left_1", gp.tileSize * 2, gp.tileSize);
		BufferedImage[] atk2 = setupSheet(filePath2, 2, 2, gp.tileSize * 2, gp.tileSize);
		
		atkSprites[0] = atk1[0];
		atkSprites[1] = atk1[1];
		atkSprites[2] = atk1[2];
		atkSprites[3] = atk1[3];
		
		atkSprites[4] = atk2[0];
		atkSprites[5] = atk2[1];
		atkSprites[6] = atk2[2];
		atkSprites[7] = atk2[3];
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
				setDefaultValues();
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
				direction = Direction.Up;
			} else if (gp.keyH.downPressed) {
				direction = Direction.Down;
			} else if (gp.keyH.leftPressed) {
				direction = Direction.Left;
			} else if (gp.keyH.rightPressed) {
				direction = Direction.Right;
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
			contactMonster(monsterIndex);
			// check event
			gp.eHandler.checkEvent();
			
			// if collision false player move
			if (!collisionOn) {
				//footsteps.loop();
				//footsteps.play();
				
				switch (direction) {
				case Up:
					worldY -= speed;
					break;
				case Down:
					worldY += speed;
					break;
				case Left:
					worldX -= speed;
					break;
				case Right:
					worldX += speed;
					break;
				default:
					break;
				}
			} else {
				direction = Direction.Idle;
			}
			if(gp.keyH.enterPressed && !stopAttacking && !collisionOn && direction != Direction.Idle)  {
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
			direction = Direction.Idle;
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
	
	public void contactMonster(int i) {
		if (i != 999) {
			if (!isInvince && !gp.monster[i].dying) {
				if (invinceCounter <= 0) {
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
	
	public void damageMonster(int i) {
		if (i != 999) {
			if (!gp.monster[i].isInvince) {
				gp.playSE(SoundEnum.swingWeapon);
				
				int damage = attack - gp.monster[i].defense;
				if(damage < 0) {
					damage = 0;
				}
				//System.out.println(damage);
				
				gp.monster[i].life -= damage;
				gp.playSE(SoundEnum.hit);
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
			atkSpriteNum = 1;
		}
		if (spriteCounter > 5 && spriteCounter <= 20) { // SHOW SECOND ATTACK IMAGE FOR 25 FRAMES
			atkSpriteNum = 2;

			// TEMP VARIABLES - Save the current worldX/Y, solidArea
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			int solidAreaWidth = hitBox.width;
			int solidAreaHeight = hitBox.height;

			// Adjust player's worldX/Y for the attackArea
			switch (direction) {
			case Up: worldY -= attackArea.height; break;
			case Down: worldY += attackArea.height; break;
			case Left: worldX -= attackArea.width; break;
			case Right: worldX += attackArea.width; break;
			default:
				break;
			}

			// attackArea becomes solidArea
			hitBox.width = attackArea.width;
			hitBox.height = attackArea.height;

			// Check monster collison with the updated worldX/Y, solidArea
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			damageMonster(monsterIndex);

			// Restore worldX/Y, solidArea
			worldX = currentWorldX;
			worldY = currentWorldY;
			hitBox.width = solidAreaWidth;
			hitBox.height = solidAreaHeight;
		}
		
		if(spriteCounter > 20) {
			atkSpriteNum = 1;
			spriteCounter = 0;
			attacking = false;
		}
	}
	
	void displayMessage(int i, OBJ item) {
		OBJ object = gp.obj[i];
		OBJType type = object.objType;
		switch(type) {
		case Key:
			gp.playSE(SoundEnum.key);
			inventory.add(object);
			gp.ui.showMessage("you have key", 120, 1);
			break;
		case Door:
			gp.playSE(SoundEnum.door);
			gp.ui.showMessage("you opened door", 120, 1);
			if(item != null) {
				inventory.remove(item);
			}
			break;
		case Eatable:
			gp.playSE(SoundEnum.key);
			inventory.add(object);
			gp.ui.showMessage("you picked up SYRINGE", 120, 1);
			break;
		default:
			return;
		}
		
		gp.obj[i] = null;
	}
	
	public void pickUpObject(int i) {
		if (i != 99) {
			if(inventory.size() != maxInventorySize) {
				switch (gp.obj[i].ID) {
				case "Key1":
					gp.ui.addAchievement(AchieveManager.achievements.get("key-pickup"));
					displayMessage(i, null);
					break;
				case "Key2":
					displayMessage(i, null);
					break;
				case "Door1":
					if (inventory.contains(OBJItems.Key1)) {
						gp.ui.addAchievement(AchieveManager.achievements.get("door-open"));
						displayMessage(i, OBJItems.Key1);
					}
					break;
				case "Door2":
					if (inventory.contains(OBJItems.Key2)) {
						displayMessage(i, OBJItems.Key2);
					}
					break;
				default:
					displayMessage(i, null);
					break;
				}
			} else {
				if(!lockCarry) {
					gp.ui.showMessage("You cant carry anymo", 40, 1);
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
			
			if(selectedItem.objType == OBJType.Weapon) {
				currentWeapon = selectedItem;
				attack = getAttack();
			}
			
			if(selectedItem.objType == OBJType.Shield) {
				currentShield = selectedItem;
				defense = getDefense();
			}
			
			if(selectedItem.objType == OBJType.Eatable ) {
				OBJ_Consumable item = (OBJ_Consumable) selectedItem;
				if(life != maxLife) {
					item.use(this);
					if(item.uses == 0) {
						inventory.remove(selectedItem);
					}
				} else {
					item.warn();
				}
				
			}
			
			
		}
		
	}
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		
		if(sprites != null) {
			switch (direction) {
			case Idle:
				image = sprites[spriteNum - 1];
				break;
			case Down:
				if(!attacking) {
					image = sprites[spriteNum + 5];
				} else {
					try {
						image = atkSprites[atkSpriteNum - 1];
					} catch(ArrayIndexOutOfBoundsException e) {
						
					}
					
				}
				break;
			case Up:
				if(!attacking) {
					image = sprites[spriteNum + 11];
				} else {
					tempScreenY = screenY - gp.tileSize;
					image = atkSprites[atkSpriteNum + 1];
				}
				
				break;
			
			case Left:
				if(!attacking) {
					image = sprites[spriteNum + 17];
				} else {
					tempScreenX = screenX - gp.tileSize;
					image = atkSprites[atkSpriteNum + 3];
				}
				
				break;
			case Right:
				if(!attacking) {
					image = sprites[spriteNum + 23];
				} else {
					image = atkSprites[atkSpriteNum + 5];
				}
				
				break;
			default:
				break;
			}
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
			g2.setColor(Color.red);
			g2.setStroke(new BasicStroke(2f));
				
			g2.drawRect(tempScreenX + attackArea.x, tempScreenY + attackArea.y, attackArea.width, attackArea.height);
			
			
			g2.setStroke(oldStroke);
		}
		
		if (isInvince) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		}

		g2.drawImage(image, tempScreenX, tempScreenY, null);
	}

}
