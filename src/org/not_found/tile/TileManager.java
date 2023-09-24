package org.not_found.tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

import org.not_found.main.*;
import org.not_found.toolbox.UtilityBox;

public class TileManager  {
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][][];
	BufferedImage[] renderedMaps;
	BufferedImage defaultPack = null;
	BufferedImage[] images = new BufferedImage[256];
	int squareLine = 16;
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[16];
		mapTileNum = new int[gp.maxMaps][gp.maxWorldCol][gp.maxWorldRow];
		renderedMaps = new BufferedImage[gp.maxMaps];
		try {
			defaultPack = ImageIO.read(new File(Main.gameDir + "/res/defaultPack.png"));
			images = UtilityBox.fromSheet(defaultPack, 16, 16);
		} catch(IOException e) {
			System.err.println("[ERROR] \"/res/defaultPack.png\" could not be loaded!");
			//getClass()
		}
		
		getTileImage();
		loadMap(new File(Main.gameDir + "/res/maps/map_cal.txt"), 0);
		loadMap(new File(Main.gameDir + "/res/maps/map_sample.txt"), 1);
	}
	public void getTileImage() {
		setup(0); //ground
		setup(1, true); //wall
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
		
		draw_to_image(map);
	}
	
	void draw_to_image(int i) {
		int worldCol = 0;
		int worldRow = 0;
		
		Graphics2D g2 = null;
		
		while(worldCol<gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];
			
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			
			
			int side = 50*gp.tileSize;
			//renderedMaps
			if(renderedMaps[i] == null) { 
				renderedMaps[i] = new BufferedImage(side, side, BufferedImage.TYPE_INT_RGB);
			} else {
				g2 = renderedMaps[i].createGraphics();
				
				g2.drawImage(tile[tileNum].image, worldX, worldY, gp.tileSize, gp.tileSize, null);
				
				
				worldCol++;
				
				if(worldCol == gp.maxWorldCol) {
					worldCol = 0;
					worldRow++;
				}
			}
			
			
			//
			
			
		}
		
		g2.dispose();
	}
	
	public void draw(Graphics2D g2) {
		int screenX = 0 - gp.player.worldX + gp.player.screenX;
		int screenY = 0 - gp.player.worldY + gp.player.screenY;
		g2.drawImage(renderedMaps[gp.currentMap], screenX, screenY, 50*gp.tileSize, 50*gp.tileSize, null);
		
	}
	
	public int roundToClosest360(int num) {
        return (int) num / 180 + ((double) (num % 180) / 180 < 0.5 ? 0 : 1);
	}
	
}
