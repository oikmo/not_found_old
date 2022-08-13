package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import javax.imageio.ImageIO;

import main.*;

public class Player extends Entity {
	GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	ArrayList<String> inventory = new ArrayList<String>();
	//ArrayList<String> items = new ArrayList<String>();
	int hasKey = 0;

	
	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		
		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 32;
		solidArea.height = 32;
		
		setDefaultValues();
		getPlayerImage();
	}
	public void setDefaultValues() {
		worldX = 550;//gp.tileSize * 23;
		worldY = 550;//gp.tileSize * 21;
		speed = 3;
		direction = "idle";
		//loadItems("res/items.txt/");
	}
	
	public void getPlayerImage() {
		try {
			idle1 = ImageIO.read(getClass().getResourceAsStream("/player/idle_1.png"));
			idle2 = ImageIO.read(getClass().getResourceAsStream("/player/idle_2.png"));
			idle3 = ImageIO.read(getClass().getResourceAsStream("/player/idle_3.png"));
			idle4 = ImageIO.read(getClass().getResourceAsStream("/player/idle_4.png"));
			idle5 = ImageIO.read(getClass().getResourceAsStream("/player/idle_5.png"));
			idle6 = ImageIO.read(getClass().getResourceAsStream("/player/idle_6.png"));
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/up_2.png"));
			up3 = ImageIO.read(getClass().getResourceAsStream("/player/up_3.png"));
			up4 = ImageIO.read(getClass().getResourceAsStream("/player/up_4.png"));
			up5 = ImageIO.read(getClass().getResourceAsStream("/player/up_5.png"));
			up6 = ImageIO.read(getClass().getResourceAsStream("/player/up_6.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/down_2.png"));
			down3 = ImageIO.read(getClass().getResourceAsStream("/player/down_3.png"));
			down4 = ImageIO.read(getClass().getResourceAsStream("/player/down_4.png"));
			down5 = ImageIO.read(getClass().getResourceAsStream("/player/down_5.png"));
			down6 = ImageIO.read(getClass().getResourceAsStream("/player/down_6.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/left_2.png"));
			left3 = ImageIO.read(getClass().getResourceAsStream("/player/left_3.png"));
			left4 = ImageIO.read(getClass().getResourceAsStream("/player/left_4.png"));
			left5 = ImageIO.read(getClass().getResourceAsStream("/player/left_5.png"));
			left6 = ImageIO.read(getClass().getResourceAsStream("/player/left_6.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/right_2.png"));
			right3 = ImageIO.read(getClass().getResourceAsStream("/player/right_3.png"));
			right4 = ImageIO.read(getClass().getResourceAsStream("/player/right_4.png"));
			right5 = ImageIO.read(getClass().getResourceAsStream("/player/right_5.png"));
			right6 = ImageIO.read(getClass().getResourceAsStream("/player/right_6.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		System.out.println(inventory);
		if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
			if(keyH.upPressed == true) {
				direction = "up";
			}
			else if(keyH.downPressed == true) {
				direction = "down";
			}
			else if(keyH.leftPressed == true) {
				direction = "left";
			}
			else if(keyH.rightPressed == true) {
				direction = "right";
			}
			//check tile collision
			collisionOn = false;
			gp.cChecker.checkTile(this);
			
			//check obj collision
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);
			
			//if collision false player move
			if(collisionOn == false) {
				switch(direction) {
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
			if(spriteCounter > 16) {
				if(spriteNum > 0) {
					spriteNum++;
				}
				if(spriteNum > 6) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		} else {
			direction = "idle";
			spriteCounter++;
			
			if(spriteCounter > 12) {
				if(spriteNum > 0) {
					
					spriteNum++;
					
				}
				if(spriteNum > 6) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		}
		
	}
	
	public void pickUpObject(int i) {
		if (i != 99) {
			String objName = gp.obj[i].name;
			/*switch(objName) {
			case "Key1":
				hasKey++;
				gp.obj[i] = null;
				break;
			case "Door":
				if(hasKey > 0) {
					gp.obj[i] = null;
					hasKey--;
				}
				break;
			}*/
			/*switch(objName) {
			case "Key1":
				inventory.add(gp.obj[i].name);
				gp.obj[i] = null;
				break;
			case "Key2":
				inventory.add(gp.obj[i].name);
				
				gp.obj[i] = null;
				break;
			case "Door1":
				if(inventory.contains("Key1")) {
					inventory.remove("Key1");
					gp.obj[i] = null;
				}
				break;
			case "Door2":
				if(inventory.contains("Key2")) {
					inventory.remove("Key2");
					gp.obj[i] = null;
				}
				break;
			}*/
		}
	}
	/*public void loadItems(String textPath) {
		try {
			Scanner s = new Scanner(new File(textPath));
			while (s.hasNext()){
			    items.add(s.next());
			}
			s.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}*/
	public void draw(Graphics2D g2) {
//		g2.setColor(Color.white);
//		//g2.drawImage(image, playerX, playerY, tileSize, tileSize, null);
//		g2.fillRect(x, y, gp.tileSize, gp.tileSize);
//		g2.dispose();
		
		BufferedImage image = null;
		
		switch(direction) {
		case "idle":
			if(spriteNum == 1) {
				image = idle1;
			}
			if(spriteNum == 2) {
				image = idle2;
			}
			if(spriteNum == 3) {
				image = idle3;
			}
			if(spriteNum == 4) {
				image = idle4;
			}
			if(spriteNum == 5) {
				image = idle5;
			}
			if(spriteNum == 6) {
				image = idle6;
			}
			break;
		case "up":
			if(spriteNum == 1) {
				image = up1;
			}
			if(spriteNum == 2) {
				image = up2;
			}
			if(spriteNum == 3) {
				image = up3;
			}
			if(spriteNum == 4) {
				image = up4;
			}
			if(spriteNum == 5) {
				image = up5;
			}
			if(spriteNum == 6) {
				image = up6;
			}
			break;
		case "down":
			if(spriteNum == 1) {
				image = down1;
			}
			if(spriteNum == 2) {
				image = down2;
			}
			if(spriteNum == 3) {
				image = down3;
			}
			if(spriteNum == 4) {
				image = down4;
			}
			if(spriteNum == 5) {
				image = down5;
			}
			if(spriteNum == 6) {
				image = down6;
			}
			break;
			
		case "left":
			if(spriteNum == 1) {
				image = left1;
			}
			if(spriteNum == 2) {
				image = left2;
			}
			if(spriteNum == 3) {
				image = left3;
			}
			if(spriteNum == 4) {
				image = left4;
			}
			if(spriteNum == 5) {
				image = left5;
			}
			if(spriteNum == 6) {
				image = left6;
			}
				break;
		case "right":
			if(spriteNum == 1) {
				image = right1;
			}
			if(spriteNum == 2) {
				image = right2;
			}
			if(spriteNum == 3) {
				image = right3;
			}
			if(spriteNum == 4) {
				image = right4;
			}
			if(spriteNum == 5) {
				image = right5;
			}
			if(spriteNum == 6) {
				image = right6;
			}
			break;
		}

		//System.out.println(x + y);
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		g2.setColor(Color.red);
		g2.setFont(new Font("TimesRoman", Font.BOLD, 15));
		g2.drawString("FPS : " + gp.fpsCount, 0, 13);
	}
	
}
