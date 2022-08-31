package main;

import java.awt.event.*;
import javax.swing.*;

public class KeyHandler implements KeyListener, MouseListener{
	
	GamePanel gp;
	ImageIcon icon = new ImageIcon("question.png");

	
	public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
	
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		int code = e.getKeyCode();
		//
		
		if(gp.gameState == gp.titleState) {
			if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
				gp.ui.commandNum--;
				if(gp.ui.commandNum < 0) {
					gp.ui.commandNum = 3;
				}
			}
			if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
				gp.ui.commandNum++;
				if(gp.ui.commandNum > 3) {
					gp.ui.commandNum = 0;
				}
			}
			if(code == KeyEvent.VK_ENTER || code == KeyEvent.VK_RIGHT) {
				if(gp.ui.commandNum == 0) {
					rightPressed = false;
					gp.gameState = gp.playState;
				}
				if(gp.ui.commandNum == 1) {
					rightPressed = false;
					
				}
				if(gp.ui.commandNum == 2) {
					rightPressed = false;
				}
				if(gp.ui.commandNum == 3) {
					
					int balls = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit le game??", "POP-UP", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, icon);
					if(balls == JOptionPane.OK_OPTION) {
						System.exit(0);
					}
					
					
				}
				
			}
		}
		
		if(gp.gameState == gp.playState) {
			if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
				upPressed = true;
			}
			else if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
				downPressed = true;
			}
			else if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
				leftPressed = true;
			}
			else if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
				rightPressed = true;
			}
			else if(code == KeyEvent.VK_ESCAPE) {
				gp.gameState = gp.pauseState;
			}
			if(code == KeyEvent.VK_ENTER) {
				enterPressed = true;
			}
			/*else if(code == KeyEvent.VK_ESCAPE) {
				gp.gameState = gp.titleState;
			}*/
		}
		else if(gp.gameState == gp.pauseState) {
			if(code == KeyEvent.VK_ESCAPE) {
				gp.gameState = gp.playState;
			}
		}
		
		else if(gp.gameState == gp.dialogueState) {
			if(code == KeyEvent.VK_ENTER) {
				gp.gameState = gp.playState;
			}
		}
		
		
		

		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
			upPressed = false;
		}
		else if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
			downPressed = false;
		}
		else if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
			leftPressed = false;
		}
		else if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
			rightPressed = false;
		}
		else if(code == KeyEvent.VK_ENTER) {
			enterPressed = false;
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
