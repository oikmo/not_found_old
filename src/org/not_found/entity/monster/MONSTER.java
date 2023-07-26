package org.not_found.entity.monster;

import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

import org.not_found.entity.EntityType;
import org.not_found.entity.npc.NPC;
import org.not_found.main.GamePanel;

public class MONSTER extends NPC {

	public Rectangle patrolBox;
	
	public MONSTER(GamePanel gp) {
		super(gp);
		
		entityType = EntityType.Monster;
		
	}
	
	public void createRange(int range) {	    
	    double centerX = hitBox.getCenterX() + screenX;
	    double centerY = hitBox.getCenterY() + screenY;
	    double radius = range * 10;

	    Ellipse2D circle = new Ellipse2D.Double();
	    circle.setFrameFromCenter(centerX, centerY, centerX + radius, centerY + radius);
		
	    patrolBox = new Rectangle((int)circle.getX(),(int)circle.getY(), (int) circle.getWidth(), (int)circle.getHeight());
		
	}
}
