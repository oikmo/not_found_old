package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity {

	public OBJ_Heart(GamePanel gp) {
		super(gp);
		name = "heart";
		image1 = setup("/objects/full", gp.tileSize, gp.tileSize);
		image2 = setup("/objects/half", gp.tileSize, gp.tileSize);
		image3 = setup("/objects/blank", gp.tileSize, gp.tileSize);
	}
}
