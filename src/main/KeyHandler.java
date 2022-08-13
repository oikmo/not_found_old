package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class KeyHandler implements KeyListener, MouseListener{
	
	public boolean upPressed, downPressed, leftPressed, rightPressed;
	
	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W) {
			upPressed = true;
		}
		else if(code == KeyEvent.VK_S) {
			downPressed = true;
		}
		else if(code == KeyEvent.VK_A) {
			leftPressed = true;
		}
		else if(code == KeyEvent.VK_D) {
			rightPressed = true;
		}
		

		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W) {
			upPressed = false;
		}
		else if(code == KeyEvent.VK_S) {
			downPressed = false;
		}
		else if(code == KeyEvent.VK_A) {
			leftPressed = false;
		}
		else if(code == KeyEvent.VK_D) {
			rightPressed = false;
		}

		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int code = e.getButton();
		
		if(code == MouseEvent.BUTTON1) {
			upPressed = false;
			downPressed = false;
			leftPressed = false;
			rightPressed = false;
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stubd
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
