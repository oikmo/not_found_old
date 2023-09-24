package org.not_found.entity.npc;

import java.awt.Rectangle;

import org.not_found.main.GamePanel;

public class NPC_BLANK extends NPC {
	public NPC_BLANK(GamePanel gp) {
		super(gp);
		hitBox = new Rectangle(0,0,48,48);
	}
}
