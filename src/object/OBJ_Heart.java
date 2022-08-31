package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity {

	public OBJ_Heart(GamePanel gp) {
		super(gp);
		name = "heart";
		image1 = setup("/objects/full");
		image2 = setup("/objects/half");
		image3 = setup("/objects/blank");
	}
}
