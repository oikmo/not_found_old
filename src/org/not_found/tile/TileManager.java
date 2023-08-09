package org.not_found.tile;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.not_found.main.GamePanel;
import org.not_found.main.Main;
import org.not_found.toolbox.UtilityBox;

public class TileManager  {
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][][];
	BufferedImage defaultPack = null;
	BufferedImage[] images = new BufferedImage[256];
	int squareLine = 16;
	
	List<TileImage> newAngle = new ArrayList<TileImage>();
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[16];
		mapTileNum = new int[gp.maxMaps][gp.maxWorldCol][gp.maxWorldRow];
		try {
			defaultPack = ImageIO.read(new File(Main.gameDir + "/res/defaultPack.png"));
			images = UtilityBox.fromSheet(defaultPack, 16, 16);
		} catch(IOException e) {
			System.err.println("[ERROR] \"/res/defaultPack.png\" could not be loaded!");
			//getClass()
		}
		
		getTileImage();
		loadMap(new File(Main.gameDir + "/res/maps/map_start.txt"), 0);
	}
	public void getTileImage() {
		setup(0, false, true); //ground
		setup(1, true, true);
		setup(2);
		setup(3);
		setup(4);
		setup(5);
		setup(6);
		setup(7);
		setup(8);
		setup(9);
		setup(10);
		setup(11);
	}
	
	public void setup(int index) {
		tile[index] = new Tile();
		tile[index].image = images[index];
		tile[index].image = UtilityBox.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
	}
	
	public void setup(int index, boolean collision) {
			tile[index] = new Tile();
			tile[index].image = images[index];
			tile[index].image = UtilityBox.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;
	}
	
	public void setup(int index, boolean collision, boolean rotate) {
		tile[index] = new Tile();
		tile[index].image = images[index];
		tile[index].image = UtilityBox.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
		tile[index].collision = collision;
		tile[index].canRotate = rotate;
	}
	
	

	
	public void loadMap(File mapPath, int map) {
		try {
			InputStream is = new FileInputStream(mapPath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
				String line = br.readLine();
				
				while (col < gp.maxWorldCol) {
					String numbers[] = line.split(" ");
					
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[map][col][row] = num;
					col++;
				}
				if(col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			br.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2) {
		int worldCol = 0;
		int worldRow = 0;
		
		while(worldCol<gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];
			
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			//stop moving the camera at the edge
			if(gp.player.screenX > gp.player.worldX) {
				screenX = worldX;
			}
			if(gp.player.screenY > gp.player.worldY) {
				screenY = worldY;
			}
			int rightOffset =  gp.screenWidth - gp.player.screenX;
			if(rightOffset > gp.worldWidth - gp.player.worldX) {
				screenX = gp.screenWidth - (gp.worldWidth - worldX);
			}
			int bottomOffset = gp.screenHeight - gp.player.screenY;
			if(bottomOffset > gp.worldHeight - gp.player.worldY) {
				screenY= gp.screenHeight - (gp.worldHeight - worldY);
			}
			
			
			if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
			   worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
			   worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
			   worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
				System.out.println();
				
				g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
				
			}
			else if(gp.player.screenX > gp.player.worldX ||
					gp.player.screenY > gp.player.worldY || 
					rightOffset > gp.worldWidth - gp.player.worldX ||
					bottomOffset > gp.worldHeight - gp.player.worldY) {
				g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
			}
			
			
			worldCol++;
			
			if(worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		}
		
	}
	
	public int roundToClosest360(int num) {
        return (int) num / 180 + ((double) (num % 180) / 180 < 0.5 ? 0 : 1);
	}
	
}
