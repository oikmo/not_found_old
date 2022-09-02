package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity {
	public OBJ_Key(GamePanel gp) {
		super(gp);
		name = "";
		idle1 = setup("/objects/key", gp.tileSize, gp.tileSize);
		collision = true;
	}
}
