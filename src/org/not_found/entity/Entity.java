package org.not_found.entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.not_found.entity.monster.MONSTER;
import org.not_found.main.GamePanel;
import org.not_found.main.Main;
import org.not_found.main.SoundEnum;
import org.not_found.object.OBJ;
import org.not_found.object.OBJType;
import org.not_found.projectile.Projectile;
import org.not_found.toolbox.UtilityBox;

public class Entity {
	GamePanel gp;
	
	public enum Direction  {
		Any,
		Idle,
		Down,
		Up,
		Left,
		Right,
	}
	
	public int spriteNum = 1;
	public BufferedImage[] sprites = null;
	public BufferedImage[] atkSprites = null;
	public BufferedImage atkUp1, atkUp2, atkDown1, atkDown2, atkLeft1, atkLeft2, atkRight1, atkRight2;
	public Rectangle hitBox = new Rectangle(0, 0, 48, 48);
	public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
	public int solidAreaDefaultX, solidAreaDefaultY;
	
	public EntityType entityType;
	// state
	public int worldX, worldY;
	public Direction direction = Direction.Idle;
	
	public boolean collisionOn = false;
	public boolean isInvince = false;
	boolean attacking = false;
	public boolean alive = true;
	public boolean dying = false;
	boolean healthBar = false;
	
	// counters
	public int spriteCounter = 0;
	public int invinceCounter = 0;
	public int shotAvailableCounter;
	public int shotCounterLimit = 30;
	int dyingCounter = 0;
	int healthCounter = 0;
	
	// char attributes
	public String name;
	public int speed;
	public int maxLife;
	public int life;
	public int maxMana;
	public int mana;
	public int level;
	public int strength;
	public int dexterity;
	public int attack;
	public int defense;
	public int exp;
	public int nextLevelExp;
	public int coin;
	
	public OBJ currentWeapon;
	public OBJ currentShield;
	public Projectile projectile;
	
	public int screenX = 0;
	public int screenY = 0;
	
	public int useCost;
	
	//was for npcs
	public String dialogues[][] = new String[100][100];
	public int dialogueIndex = 0;
	public int dialogueSet = 0;
	public boolean dialogueSetCompleted = false;
	
	
	public Entity(GamePanel gp) {
		this.gp = gp;
		
		getSpriteSheet();
	}
	
	public void update() {
		
		update_alt();
		
		collisionOn = false;
		gp.cChecker.checkTile(this);
		gp.cChecker.checkObject(this, false);
		gp.cChecker.checkWalls(this, false);
		
		boolean contactPlayer = gp.cChecker.checkPlayer(this);
		int wall = gp.cChecker.checkWalls(this, true);
		boolean contactWall = gp.cChecker.checkWallsB(this);

		if (this.entityType == EntityType.Monster && contactPlayer && !this.dying) {
			damagePlayer(attack);
		}
		
		if (this.entityType == EntityType.Monster && contactWall && !this.dying) {
			damageWall(wall, attack);
		}
			
		if (!collisionOn) {
			switch (direction) {
				case Up: worldY -= speed; break;
				case Down: worldY += speed; break;
				case Left: worldX -= speed; break;
				case Right: worldX += speed; break;
			default:
				break;
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
			
		}
		else {
			direction = Direction.Idle;
		}
		if(this.entityType == EntityType.Monster) {
			gp.cChecker.checkEntity(this, gp.monster);
		} else if(this.entityType == EntityType.NPC ) {
			gp.cChecker.checkEntity(this, gp.npc);
		}
		
		
		if (isInvince) { invinceCounter++; if (invinceCounter > 40) { isInvince = false; invinceCounter = 0; } }
		
		
	}
	
	public void update_alt() {}	
	
	
	public void startDialogue(Entity entity, int setNum) {
		gp.gameState = gp.dialogueState;
		gp.ui.entity = entity;
		dialogueSet = setNum;
	}
	
	public void damagePlayer(int attack) {
		if (!gp.player.isInvince && !gp.player.attacking && !this.dying) {
			gp.playSE(SoundEnum.hit);
			int damage = attack - gp.player.defense;
			if(damage < 0) {
				damage = 0;
			}
			
			gp.player.life -= damage;
			gp.player.isInvince = true;
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
					gp.walls.get(i).dying = true;
				}
			}
		}
	}
	
	public void checkDrop() {}
	
	public void dropItem(OBJ droppedItem) {
		for(int i = 0; i < gp.obj.length; i++) {
			if(gp.getOBJ(i) == null) {
				gp.setOBJ(droppedItem, i);
				gp.getOBJ(i).worldX = worldX;
				gp.getOBJ(i).worldY = worldY;
				break;
			}
		}
	}
	
	public void getSpriteSheet() {}
	
	public void draw(Graphics2D g2) {

		BufferedImage image = null;
		screenX = worldX - gp.player.worldX + gp.player.screenX;
		screenY = worldY - gp.player.worldY + gp.player.screenY;
		
		
		if(isNotType()) {
			if(sprites != null)  {
				switch (direction) {
				case Idle:
					image = sprites[spriteNum - 1];
					break;
				case Down:
					image = sprites[spriteNum + 5];
					break;
				case Up:
					image = sprites[spriteNum + 11];
					break;
				case Left:
					image = sprites[spriteNum + 17];
					break;
				case Right:
					image = sprites[spriteNum + 23];
					break;
				default:
					break;
				}
			}
		} else if(this.entityType == EntityType.Object){
			OBJ obj = (OBJ) this;
			image = obj.image;
		} else if(this.entityType == EntityType.Projectile) {
			if(sprites != null) {
				switch (direction) {
				case Idle:
					image = sprites[spriteNum - 1];
					break;
				case Down:
					image = sprites[spriteNum - 1];
					break;
				case Up:
					image = sprites[spriteNum + 1];
					break;
				case Left:
					image = sprites[spriteNum + 3];
					break;
				case Right:
					image = sprites[spriteNum + 5];
					break;
				default:
					break;
				}
			}
		}
		
		//monster health bar
		if(this.entityType == EntityType.Monster) {
			double oneScale = (double)gp.tileSize/maxLife;
			double hpBarValue = oneScale * life;
			
			if(healthBar) {
				if(life < maxLife) {
					healthCounter++;
					g2.setColor(Color.DARK_GRAY);
					g2.fillRect(screenX-1, screenY-16, gp.tileSize+2, 12);
					g2.setColor(new Color(255,0,30));
					g2.fillRect(screenX, screenY-15, (int)hpBarValue, 10);
				}
			}
			if(healthCounter > 120) {
				healthCounter = 0;
				healthBar = false;
			}
		}
		
		if(this.entityType == EntityType.Object) {
			OBJ obj = (OBJ) this;
			if(obj.objType == OBJType.Wall) {
				double oneScale = (double)gp.tileSize/maxLife;
				double hpBarValue = oneScale * life;
				
				if(life < maxLife) {
					healthCounter++;
					g2.setColor(Color.DARK_GRAY);
					g2.fillRect(screenX-1, screenY-16, gp.tileSize+2, 12);
					g2.setColor(new Color(255,0,30));
					g2.fillRect(screenX, screenY-15, (int)hpBarValue, 10);
				}
				if(healthCounter > 120) {
					healthCounter = 0;
					healthBar = false;
				}
			}
		}
		
		if (isInvince) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
			healthBar = true;
		}
		if (dying) {
			dieAnim(g2);
		}
		
		g2.drawImage(image, screenX, screenY, null);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
		
		if(gp.debug) {
			Stroke oldStroke = g2.getStroke();
			g2.setStroke(new BasicStroke(2f));
			g2.setColor(Color.white);
			g2.drawRect(screenX + hitBox.x, screenY+ hitBox.y, hitBox.width, hitBox.height);
			
			g2.setStroke(oldStroke);
		}
		
		if(this.entityType == EntityType.Monster) {
			MONSTER mon = (MONSTER) this;
			if(gp.debug) {
				if(mon.patrolBox != null) {
					g2.setColor(Color.white);
					g2.draw(mon.patrolBox);
				}
				
			}
			
			
		}
	}

	public void dieAnim(Graphics2D g2) {
		dyingCounter++;
		
		int i = 5;
		
		if (dyingCounter <= i) { changeAlpha(g2, 0.1f); }
		if (dyingCounter > i && dyingCounter <= i*2) { changeAlpha(g2, 1f); }
		if (dyingCounter > i*2 && dyingCounter <= i*3) { changeAlpha(g2, 0.1f); }
		if (dyingCounter > i*3 && dyingCounter <= i*4) { changeAlpha(g2, 1f); }
		if (dyingCounter > i*4 && dyingCounter <= i*5) { changeAlpha(g2, 0.1f); }
		if (dyingCounter > i*5 && dyingCounter <= i*6) { changeAlpha(g2, 1f); }
		if (dyingCounter > i*6 && dyingCounter <= i*7) { changeAlpha(g2, 0f); }
		if (dyingCounter > i*7 && dyingCounter <= i*8) { changeAlpha(g2, 1f); }
		
		if (dyingCounter > 40) {
			alive = false;
		}
	}
	
	public void changeAlpha(Graphics2D g2, float alphaValue) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
	}
	
	public void setHitbox(int x, int y, int width, int height) {
		hitBox = new Rectangle(x, y, width, height);
		solidAreaDefaultX = hitBox.x;
		solidAreaDefaultY = hitBox.y;
	}
	
	public BufferedImage setup(String imageName, int width, int height) {
		BufferedImage image = null;

		try {
			image = ImageIO.read(new File(Main.gameDir + "/res/" + imageName + ".png"));
			image = UtilityBox.scaleImage(image, width, height);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return image;
	}
	
	public BufferedImage setup(String imageName) {
		return setup(imageName, gp.tileSize, gp.tileSize);
	}
	
	public BufferedImage[] setupSheet(String filePath, int row, int col) {
		BufferedImage[] result = null;
		try {
			BufferedImage spriteSheet = ImageIO.read(new File(Main.gameDir + "/res/" + filePath + ".png"));
			result = new BufferedImage[row * col];
			result = UtilityBox.fromSheet(spriteSheet, row, col);
			result = scaleArray(result);
		} catch (IOException e) {
			System.err.println("[ERROR] \"" + Main.gameDir + "/res/"+ filePath + ".png\" could not be loaded!");
		}
		
		return result;
	}
	
	public BufferedImage[] setupSheet(String filePath, int row, int col, int width, int height) {
		BufferedImage[] result = null;
		try {
			BufferedImage spriteSheet = ImageIO.read(new File(Main.gameDir + "/res/" + filePath + ".png"));
			result = new BufferedImage[row * col];
			result = UtilityBox.fromSheet(spriteSheet, row, col, width, height);
			result = scaleArray(result, width, height);
		} catch (IOException e) {
			System.err.println("[ERROR] \"" + Main.gameDir + "/res/"+ filePath + ".png\" could not be loaded!");
		}
		
		return result;
	}
	
	public BufferedImage[][] setupSheet2D(String filePath, int row, int col) {
		BufferedImage[][] result = null;
		try {
			BufferedImage spriteSheet = ImageIO.read(new File(Main.gameDir + "/res/" + filePath + ".png"));
			result = new BufferedImage[row * col][row * col];
			result = UtilityBox.fromSheet2D(spriteSheet, row, col);
			result = scaleArray(result, row, col);
		}catch (IOException e) {
			System.err.println("[ERROR] \" " + Main.gameDir + "/res/"+ filePath + ".png\" could not be loaded!");
		} catch (IllegalArgumentException e) {
			System.err.println("[ERROR] \" " + Main.gameDir + "/res/"+ filePath + ".png\" could not be loaded!");
		}
		
		return result;
	}
	
	public BufferedImage[] scaleArray(BufferedImage[] array, int width, int height) {
		BufferedImage[] result = new BufferedImage[array.length];
		
	    for (int i = 0; i < array.length; i++) {
	        result[i] = UtilityBox.scaleImage(array[i], width, height);
	    }
		
	    return result;
	}
	
	public BufferedImage[] scaleArray(BufferedImage[] array) {
		return scaleArray(array, gp.tileSize, gp.tileSize);
	}
	
	public BufferedImage[][] scaleArray(BufferedImage[][] array, int rows, int cols, int width, int height) {
		BufferedImage[][] result = new BufferedImage[array.length][array.length];
		
		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < cols; y++) {
				result[x][y] = UtilityBox.scaleImage(array[y][x], width, height);
			}
		}
		
		return result;
		
	}
	
	public BufferedImage[][] scaleArray(BufferedImage[][] array, int rows, int cols) {
		return scaleArray(array, rows, cols, gp.tileSize, gp.tileSize);
	}
	
	public BufferedImage setup(int index) {
		BufferedImage image = null;
		
		image = sprites[index];
		image = UtilityBox.scaleImage(image, gp.tileSize, gp.tileSize);
		
		return image;
	}
	
	boolean isNotType() {
		return this.entityType != EntityType.Object && this.entityType != EntityType.Player && this.entityType != EntityType.Projectile;
	}
}