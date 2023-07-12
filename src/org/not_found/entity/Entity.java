package org.not_found.entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.not_found.main.GamePanel;
import org.not_found.object.OBJ;
import org.not_found.toolbox.UtilityBox;

public class Entity {
	GamePanel gp;
	public BufferedImage idle1, idle2, idle3, idle4, idle5, idle6;
	public BufferedImage up1, up2, up3, up4, up5, up6;
	public BufferedImage down1, down2, down3, down4, down5, down6;
	public BufferedImage left1, left2, left3, left4, left5, left6;
	public BufferedImage right1, right2, right3, right4, right5, right6;
	public BufferedImage atkUp1, atkUp2, atkDown1, atkDown2, atkLeft1, atkLeft2, atkRight1, atkRight2;
	public BufferedImage image1, image2, image3;
	public Rectangle hitBox = new Rectangle(0, 0, 48, 48);
	public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collision = false;
	protected String dialogues[] = new String[100];
	
	public enum EntityType {
		Player,
		NPC,
		Monster,
		
		Key,
		Door,
		Chest,
		
		Weapon,
		Shield,
		Eatable
	}
	public EntityType entityType;
	
	// state
	public int worldX, worldY;
	public String direction = "idle";
	public int spriteNum = 1;
	public int dialogueIndex = 0;
	public boolean moving = false;
	public boolean collisionOn = false;
	public boolean isInvince = false;
	boolean attacking = false;
	public boolean alive = true;
	public boolean dying = false;
	boolean healthBar = false;
	
	// counters
	public int spriteCounter = 0;
	public int actionLockCounter = 0;
	public int invinceCounter = 0;
	int pixelCounter = 0;
	int dyingCounter = 0;
	int healthCounter = 0;
	
	// char attributes
	public String name;
	public String npcName;
	public int speed;
	public int maxLife;
	public int life;
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
	
	//item attributes
	public int attackValue;
	public int defenseValue;
	

	public Entity(GamePanel gp) {
		this.gp = gp;
	}

	public void setAction() {}

	public void speak() {}
	
	public void damageReaction() {}

	public void update() {
		
		setAction();
		
		collisionOn = false;
		gp.cChecker.checkTile(this);
		gp.cChecker.checkObject(this, false);
		//System.out.println(collisionOn);
		boolean contactPlayer = gp.cChecker.checkPlayer(this);

		if (this.entityType == EntityType.Monster && contactPlayer) {
			if (!gp.player.isInvince && !gp.player.attacking) {
				gp.playSE(8);
				int damage = attack - gp.player.defense;
				if(damage < 0) {
					damage = 0;
				}
				
				gp.player.life -= damage;
				gp.player.isInvince = true;
			}
		}
		
		
		//System.out.println(moving);
			
		if (!collisionOn) {
			switch (direction) {
				case "up": worldY -= speed; break;
				case "down": worldY += speed; break;
				case "left": worldX -= speed; break;
				case "right": worldX += speed; break;
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
			direction = "idle";
		}
		
		if (isInvince) { invinceCounter++; if (invinceCounter > 40) { isInvince = false; invinceCounter = 0; } }
		
		update_alt();
	}
	
	public void update_alt() {}

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
				if (spriteNum == 1) { image = idle1; }
				if (spriteNum == 2) { image = idle2; }
				if (spriteNum == 3) { image = idle3; }
				if (spriteNum == 4) { image = idle4; }
				if (spriteNum == 5) { image = idle5; }
				if (spriteNum == 6) { image = idle6; }
				break;
			case "up":
				if (spriteNum == 1) { image = up1; }
				if (spriteNum == 2) { image = up2; }
				if (spriteNum == 3) { image = up3; }
				if (spriteNum == 4) { image = up4; }
				if (spriteNum == 5) { image = up5; }
				if (spriteNum == 6) { image = up6; }
				break;
			case "down":
				if (spriteNum == 1) { image = down1; }
				if (spriteNum == 2) { image = down2; }
				if (spriteNum == 3) { image = down3; }
				if (spriteNum == 4) { image = down4; }
				if (spriteNum == 5) { image = down5; }
				if (spriteNum == 6) { image = down6; }
				break;
			case "left":
				if (spriteNum == 1) {image = left1; }
				if (spriteNum == 2) { image = left2; }
				if (spriteNum == 3) { image = left3; }
				if (spriteNum == 4) { image = left4; }
				if (spriteNum == 5) { image = left5; }
				if (spriteNum == 6) { image = left6; }
				break;
			case "right":
				if (spriteNum == 1) { image = right1; }
				if (spriteNum == 2) { image = right2; }
				if (spriteNum == 3) { image = right3; }
				if (spriteNum == 4) { image = right4; }
				if (spriteNum == 5) { image = right5; }
				if (spriteNum == 6) { image = right6; }
				break;
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
		
		if (isInvince) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
			healthBar = true;
		}
		if (dying) {
			dieAnim(g2);
		}
		if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
				&& worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
				&& worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
				&& worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
		}
		// If player is around the edge, draw everything
		else if (gp.player.worldX < gp.player.screenX || gp.player.worldY < gp.player.screenY
				|| rightOffset > gp.worldWidth - gp.player.worldX || bottomOffset > gp.worldHeight - gp.player.worldY) {
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
		}
		
		if(gp.debug) {
			Stroke oldStroke = g2.getStroke();
			g2.setStroke(new BasicStroke(2f));
			g2.setColor(Color.white);
			g2.drawRect(screenX + hitBox.x, screenY+ hitBox.y, hitBox.width, hitBox.height);
			
			g2.setStroke(oldStroke);
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
			dying = false;
			alive = false;
		}
	}
	
	public void changeAlpha(Graphics2D g2, float alphaValue) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
	}

	public BufferedImage setup(String imageName, int width, int height) {
		BufferedImage image = null;

		try {
			image = ImageIO.read(getClass().getResourceAsStream("/res" + imageName + ".png"));
			image = UtilityBox.scaleImage(image, width, height);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return image;
	}
}