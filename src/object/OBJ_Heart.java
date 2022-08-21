package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Heart extends SuperObject {
	
	GamePanel gp;
	
	public OBJ_Heart(GamePanel gp) {
		name = "";
		
		try {
			image1 = ImageIO.read(getClass().getResourceAsStream("/objects/full.png"));
			image2 = ImageIO.read(getClass().getResourceAsStream("/objects/half.png"));
			image3 = ImageIO.read(getClass().getResourceAsStream("/objects/blank.png"));
			image1 = uTool.scaleImage(image1, gp.tileSize, gp.tileSize);
			image2 = uTool.scaleImage(image2, gp.tileSize, gp.tileSize);
			image3 = uTool.scaleImage(image3, gp.tileSize, gp.tileSize);
		} catch(IOException e) {
			e.printStackTrace();
		}
		collision = true;
	}
}
