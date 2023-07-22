package org.not_found.object;

import java.awt.image.BufferedImage;

import org.not_found.entity.*;
import org.not_found.main.GamePanel;

public class OBJ extends Entity {
	
	BufferedImage[][] images;
	public BufferedImage image;
	public OBJType objType;
	public boolean collision = false;
	
	public String ID = null;
	public String description = "";
	//item attributes
	public int attackValue;
	public int defenseValue;
	
	
	public OBJ(GamePanel gp) {
		super(gp);
		images = setupSheet2D("objects/items", 8, 8);
		entityType = EntityType.Object;
		name = "";
	}
	
	public BufferedImage getImage(int row, int col) {
		return images[row][col];
	}

}
