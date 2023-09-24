package org.not_found.entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.not_found.achievements.AchieveManager;
import org.not_found.main.*;
import org.not_found.object.*;
import org.not_found.object.tools.*;

public class Player extends Entity {
	public BufferedImage shadow;
	public int screenX;
	public int screenY;
	public ArrayList<OBJ> inventory = new ArrayList<>();
	public final int maxInventorySize = 20;
	int atkSpriteNum;
	public boolean stopAttacking = false;
	int wallCounter = 0;
	int ratio = 1;
	
	public Player(GamePanel gp) {
		super(gp);
		
		entityType = EntityType.Player;
		
		
		
		hitBox = new Rectangle(8, 2, 32, 40);
		solidAreaDefaultX = hitBox.x;
		solidAreaDefaultY = hitBox.y;
		
		setDefaultValues();
		getPlayerImage();
		getAttackImage("player/playerAttackSheet_1", "player/playerAttackSheet_2");
		
	}

	public void setDefaultValues() {
		worldX = gp.tileSize * 2;
		worldY = gp.tileSize * 4;
		speed = 4;
		//gp.currentMap = 1;
		
		// player status
		level = 1;
		maxLife = 6;
		life = maxLife;
		maxMana = 10;
		mana = maxMana;
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
	
	public void setDefaultPositions() {
		worldX = gp.tileSize * 16;
		worldY = gp.tileSize * 16;
		direction = Direction.Idle;
	}
	
	public void restoreLifeAndMana() {
		life = maxLife;
		mana = maxMana;
	}
	
	public void setItems() {
		inventory.clear();
		inventory.add(currentWeapon);
		inventory.add(currentShield);
		inventory.add(new OBJ_Bow(gp));
	}
	
	public int getAttack() {
		attackArea = currentWeapon.attackArea;
		return attack = strength * currentWeapon.attackValue;
	}
	
	public int getShootableAttack() {
		return attack = strength * projectile.attack;
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
		screenX = (gp.screenWidth / 2 - (gp.tileSize / 2));
		screenY = (gp.screenHeight / 2 - (gp.tileSize / 2));
		
		if(gp.ui.message.size() == 0) {
			if(lockCarry) {
				lockCarry = false;
			}
		}
		if(life <= 0) {
			gp.gameState = gp.gameOverState;
			gp.ui.commandNum = -1;
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
			if(gp.keyH.enterPressed && !collisionOn) {
				if(currentWeapon.objType == OBJType.Weapon)  {
					if(direction != Direction.Idle) {
						if(!stopAttacking) {
							gp.playSE(SoundEnum.swingWeapon);
							attacking = true;
							spriteCounter = 0;
						}
					}
					
				} else if(currentWeapon.objType == OBJType.Shootable) {
					OBJ_Shootable item = (OBJ_Shootable) currentWeapon;
					if(item.amount != 0) {
						if(projectile != null) {
							if(shotAvailableCounter == shotCounterLimit) {
								gp.playSE(SoundEnum.swingWeapon);
								//set projectile
								item.shot();
								projectile = item.projectile;
								projectile.set(worldX, worldY, direction, true, (Entity)this);
								shotAvailableCounter = 0;
								gp.projectiles.add(projectile);
								item.amount--;
							}

						}
					} else {
						gp.ui.addMessage("No arrows!", 40, 1);
					}
				}
			}
			
			
			
			stopAttacking = false;
			gp.keyH.enterPressed = false;
			
			spriteCounter++;
			if (spriteCounter > 7) {
				if (spriteNum > 0) {
					spriteNum++;
				}
				if (spriteNum > 5) {
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
		
		if(shotAvailableCounter < shotCounterLimit) {
			shotAvailableCounter++;
		} else {
			if(shotAvailableCounter > shotCounterLimit) {
				shotAvailableCounter = shotCounterLimit;
			}
		}
		
		if(wallCounter < 30) {
			wallCounter++;
		} else {
			if(wallCounter > 30) {
				wallCounter = 30;
			}
		}
		
		
		if (isInvince) {
			invinceCounter++;
			if (invinceCounter > 30) {
				isInvince = false;
				invinceCounter = 0;
			}
		}
		
		if(gp.keyH.shotKeyPressed && wallCounter == 30) {
			OBJ_Wall wall = new OBJ_Wall(gp);
			wall.use(this);
			wallCounter = 0;
		}
		
		if(life > maxLife) {
			life = maxLife;
		}
		
		if(mana > maxMana) {
			mana = maxMana;
		}
	}
	
	public void contactMonster(int i) {
		if (i != 999) {
			if (!isInvince && !gp.monster[gp.currentMap][i].dying) {
				if (invinceCounter <= 0) {
					gp.playSE(SoundEnum.recieveDmg);
					
					int damage = gp.monster[gp.currentMap][i].attack - defense;
					if(damage < 0) {
						damage = 0;
					}
					
					life -= damage;
					isInvince = true;
				}
			}
		}
	}
	
	public void damageMonster(int i, int attack) {
		if (i != 999) {
			if (!gp.monster[gp.currentMap][i].isInvince) {
				gp.playSE(SoundEnum.swingWeapon);
				
				int damage = attack - gp.monster[gp.currentMap][i].defense;
				if(damage < 0) {
					damage = 0;
				}
				//System.out.println(damage);
				
				gp.monster[gp.currentMap][i].life -= damage;
				gp.playSE(SoundEnum.hit);
				gp.monster[gp.currentMap][i].isInvince = true;
				gp.monster[gp.currentMap][i].damageReaction();

				if (gp.monster[gp.currentMap][i].life <= 0) {
					if(!AchieveManager.achievements.get("first-kill").completed) {
						gp.ui.addAchievement(AchieveManager.achievements.get("first-kill"));
					}
					
					gp.monster[gp.currentMap][i].dying = true;
					exp += gp.monster[gp.currentMap][i].exp;
					checkLevelUP();
				}
			}
		}
	}
	
	public void damageWall(int i, int attack) {
		if (i != 99) {
			if (!gp.walls.get(i).isInvince) {
				gp.playSE(SoundEnum.swingWeapon);
				
				int damage = attack - gp.walls.get(i).defense;
				if(damage < 0) {
					damage = 0;
				}
				//System.out.println(damage);
				
				gp.walls.get(i).life -= damage;
				gp.playSE(SoundEnum.hit);
				gp.walls.get(i).isInvince = true;

				if (gp.walls.get(i).life <= 0) {
					if(!AchieveManager.achievements.get("first-kill").completed) {
						gp.ui.addAchievement(AchieveManager.achievements.get("first-kill"));
					}
					
					gp.walls.get(i).dying = true;
					checkLevelUP();
				}
			}
		}
	}

	void checkLevelUP() {
		if(exp >= nextLevelExp) {
			gp.playSE(SoundEnum.powerUp);
			level++;
			nextLevelExp = nextLevelExp*2;
			maxLife += 2;
			strength++;
			dexterity++;
			attack = getAttack();
			defense = getDefense();
		}
		
	}

	public void attacking() {
		if(currentWeapon.objType == OBJType.Weapon) {
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
				damageMonster(monsterIndex, attack);
				
				int wall = gp.cChecker.checkWalls(this, true);
				damageWall(wall, attack);

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
		} else {
			attacking = false;
		}
		
	}
	
	void displayMessage(int i, OBJ item) {
		OBJ object = gp.obj[gp.currentMap][i];
		OBJType type = object.objType;
		switch(type) {
		case Key:
			gp.playSE(SoundEnum.key);
			inventory.add(object);
			//gp.ui.showMessage("you have key", 120, 1);
			break;
		case Door:
			gp.playSE(SoundEnum.door);
			//gp.ui.showMessage("you opened door", 120, 1);
			if(item != null) {
				inventory.remove(item);
			}
			break;
		case Eatable:
			gp.playSE(SoundEnum.key);
			inventory.add(object);
			gp.ui.addMessage("you picked up SYRINGE", 120, 1);
			break;
		default:
			return;
		}
		
		gp.obj[gp.currentMap][i] = null;
	}
	
	public void pickUpObject(int i) {
		if (i != 99) {
			if(gp.obj[gp.currentMap][i].objType != OBJType.Chest && gp.obj[gp.currentMap][i].objType != OBJType.Wall && gp.obj[gp.currentMap][i].objType != OBJType.Door) {
				if(gp.obj[gp.currentMap][i].objType == OBJType.PickupAble) {
					gp.obj[gp.currentMap][i].use(this);
					if(!gp.obj[gp.currentMap][i].didntWork) {
						gp.obj[gp.currentMap][i] = null;
					}
					
				} else {
					if(inventory.size() != maxInventorySize) {
						switch (gp.obj[gp.currentMap][i].ID) {
						case "Key1":
							gp.ui.addAchievement(AchieveManager.achievements.get("key-pickup"));
							displayMessage(i, null);
							break;
						default:
							displayMessage(i, null);
							break;
						}
					} else {
						if(!lockCarry) {
							gp.ui.addMessage("You cant carry anymo", 40, 1);
							lockCarry = true;
						}	
					}
				}
				
			} else {
				interactObject(i);
			}
		}
	}
	
	public void interactObject(int i) {
		OBJ selectedOBJ = gp.obj[gp.currentMap][i];
		
		if(selectedOBJ.ID.contains("Door"+selectedOBJ.lockID)) {
			for(int index = 0; index < inventory.size(); index++) {
				OBJ item = inventory.get(index);
				if(item.ID.contains("Key"+selectedOBJ.lockID)) {
					if(item.lockID == 1) {
						gp.ui.addAchievement(AchieveManager.achievements.get("key-pickup"));
					}
					
					displayMessage(i, item);
				}
			}
		} else {
			System.out.println(selectedOBJ.lockID);
		}
	}

	public void interactNPC(int i) {
		if (gp.keyH.enterPressed) {
			if (i != 999) {
				stopAttacking = true;
				gp.npc[gp.currentMap][i].speak();
			}
		}
	}

	public void selectItem() {
		int itemIndex = gp.ui.getItemIndexOnSlot();
		
		if(itemIndex < inventory.size()) {
			OBJ selectedItem = inventory.get(itemIndex);
			
			if(selectedItem.objType == OBJType.Weapon || selectedItem.objType == OBJType.Shootable) {
				currentWeapon = selectedItem;
				if(selectedItem.objType == OBJType.Weapon) {
					attack = getAttack();
					projectile = null;
				} else {
					OBJ_Shootable shootable = (OBJ_Shootable) selectedItem;
					projectile = shootable.projectile;
					attack = getShootableAttack();
				}
				
				
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
					} catch(ArrayIndexOutOfBoundsException e) {}
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
		
		if(gp.debug) {
			g2.setFont(gp.ui.VCR);
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
			g2.setColor(Color.red);
			g2.drawString("invince: " + invinceCounter, 10, 250);
			g2.drawString("worldX: " + (int)(worldX/gp.tileSize), 10, 300);
			g2.drawString("worldY: " + (int)(worldY/gp.tileSize), 10, 320);
			
			
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
