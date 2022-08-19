package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Key extends SuperObject {
	
	GamePanel gp;
	
	public OBJ_Key(GamePanel gp) {
		name = "";
		
		try {
			image1 = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
			uTool.scaleImage(image1, gp.tileSize, gp.tileSize);
		} catch(IOException e) {
			e.printStackTrace();
		}
		collision = true;
	}
}
