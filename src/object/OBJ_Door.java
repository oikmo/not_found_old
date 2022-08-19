package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Door extends SuperObject{
	
	GamePanel gp;
	
	public OBJ_Door(GamePanel gp) {
		name = "Door";
		
		try {
			image1 = ImageIO.read(getClass().getResourceAsStream("/objects/door.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
		collision = true;
	}
}
