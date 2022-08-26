package entity;

import main.GamePanel;

public class NPC_BLANK extends Entity {
	public NPC_BLANK(GamePanel gp) {
		super(gp);
		direction = "down";
		speed = 2;
		//getPlayerImage();
		//setDialogue();
	}
}
