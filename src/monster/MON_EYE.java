package monster;

import entity.Entity;
import main.GamePanel;

public class MON_EYE extends Entity {
	public MON_EYE(GamePanel gp) {
		super(gp);
		
		name = "EYE";
		speed = 1;
		maxLife = 4;
		life = maxLife;
		
		solidArea.x = 1;
		solidArea.y = 1;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 46;
		solidArea.height = 46;
	}
	
	public void getImage() {
		
	}
}
