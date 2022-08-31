package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ_Door extends Entity{
	public OBJ_Door(GamePanel gp) {
		super(gp);
		name = "Door";
		idle1 = setup("/objects/door");
		collision = true;
	}
}
