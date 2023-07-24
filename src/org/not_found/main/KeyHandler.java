package org.not_found.main;

import java.awt.event.*;
import javax.swing.*;

public class KeyHandler implements KeyListener {
	
	GamePanel gp;
	ImageIcon icon = new ImageIcon("question.png");
	
	public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed;
	
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		if(gp.gameState == gp.titleState) {
			titleState(code);
		}
		if(gp.gameState == gp.playState) {
			playState(code);
		}
		else if(gp.gameState == gp.pauseState) {
			pauseState(code);
		}
		else if(gp.gameState == gp.dialogueState) {
			dialogueState(code);
		}
		else if(gp.gameState == gp.characterState) {
			charState(code);
		} else if(gp.gameState == gp.optionsState) {
			optionsState(code);
		}
		
	}
	
	public void titleState(int code) {
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
				int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit le game??", "POP-UP", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, icon);
				if(option == JOptionPane.OK_OPTION) {
					System.exit(0);
				}
			}
		}
	}
	
	public void playState(int code) {
		if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
			upPressed = true;
		}
		if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
			downPressed = true;
		}
		if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
			leftPressed = true;
		}
		if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
			rightPressed = true;
		} 
		if(code == KeyEvent.VK_ENTER) {
			 enterPressed = true;
		} 
		else if(code == KeyEvent.VK_F) {
			shotKeyPressed = true;
		} 
		else if(code == KeyEvent.VK_C) {
			gp.gameState = gp.characterState;
		} 
		else if(code == KeyEvent.VK_ESCAPE)  {
			gp.gameState = gp.pauseState;
		}
	}
	
	public void pauseState(int code) {
		if(code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.playState;
		}
		
		if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
			gp.ui.commandNum--;
			gp.playSE(SoundEnum.cursor);
			if(gp.ui.commandNum < 0) {
				gp.ui.commandNum = 2;
			}
		}
		if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
			gp.ui.commandNum++;
			gp.playSE(SoundEnum.cursor);
			if(gp.ui.commandNum > 2) {
				gp.ui.commandNum = 0;
			}
		}
		
		if(code == KeyEvent.VK_ENTER) {
			enterPressed = true;
			if(gp.ui.commandNum == 2) {
				gp.gameState = gp.titleState;
			}
		}
	}
	
	public void dialogueState(int code) {
		if(code == KeyEvent.VK_ENTER) {
			gp.gameState = gp.playState;
		}
	}
	
	public void charState(int code) {
		if(code == KeyEvent.VK_C || code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.playState;
		}
		
		if(code == KeyEvent.VK_W) {
			if(gp.ui.slotRow !=0) {
				gp.ui.slotRow--;
				gp.playSE(SoundEnum.cursor);
			}
			
		} else if(code == KeyEvent.VK_S) {
			if(gp.ui.slotRow !=3) {
				gp.ui.slotRow++;
				gp.playSE(SoundEnum.cursor);
			}
		} else if(code == KeyEvent.VK_D) {
			if(gp.ui.slotCol != 4) {
				gp.ui.slotCol++;
				gp.playSE(SoundEnum.cursor);
			}
		} else if(code == KeyEvent.VK_A) {
			if(gp.ui.slotCol != 0) {
				gp.ui.slotCol--;
				gp.playSE(SoundEnum.cursor);
			}
		} else if(code == KeyEvent.VK_ENTER) {
			gp.player.selectItem();
		}
	}
	
	public void optionsState(int code) {
		if(code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.playState;
		}
		
		if(code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}
		
		if(gp.ui.subState == 0) {
			if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
				gp.ui.commandNum--;
				gp.playSE(SoundEnum.cursor);
				if(gp.ui.commandNum < 0) {
					gp.ui.commandNum = 3;
				}
			}
			if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
				gp.ui.commandNum++;
				gp.playSE(SoundEnum.cursor);
				if(gp.ui.commandNum > 3) {
					gp.ui.commandNum = 0;
				}
			}
		}
		
		
		if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
			if(gp.ui.subState == 0) {
				if(gp.ui.commandNum == 0) {
					if(gp.Tmusic.volumeScale > 0) {
						gp.Tmusic.volumeScale--;
						gp.Tmusic.checkVolume();
						gp.playSE(SoundEnum.cursor);
					}
					if(gp.Pmusic.volumeScale > 0) {
						gp.Pmusic.volumeScale--;
						gp.Pmusic.checkVolume();
						gp.playSE(SoundEnum.cursor);
					}
					if(gp.music.volumeScale > 0) {
						gp.music.volumeScale--;
						gp.music.checkVolume();
						gp.playSE(SoundEnum.cursor);
					}
				
				}
				if(gp.ui.commandNum == 1) {
					if(gp.se.volumeScale > 0) {
						gp.se.volumeScale--;
						gp.playSE(SoundEnum.cursor);
					}
				}
			}
		}
		
		if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
			if(gp.ui.subState == 0) {
				if(gp.ui.commandNum == 0) {
					
					if(gp.music.volumeScale < 5) {
						gp.music.volumeScale++;
						gp.music.checkVolume();
						gp.playSE(SoundEnum.cursor);
					}
					if(gp.Tmusic.volumeScale < 5) {
						gp.Tmusic.volumeScale++;
						gp.Tmusic.checkVolume();
						gp.playSE(SoundEnum.cursor);
					}
					if(gp.Pmusic.volumeScale < 5) {
						gp.Pmusic.volumeScale++;
						gp.Pmusic.checkVolume();
						gp.playSE(SoundEnum.cursor);
					}
				}
				if(gp.ui.commandNum == 1) {
					if(gp.se.volumeScale < 5) {
						gp.se.volumeScale++;
						gp.playSE(SoundEnum.cursor);
					}
				}
			}
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
			upPressed = false;
		}
		if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
			downPressed = false;
		}
		if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
			leftPressed = false;
		}
		if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
			rightPressed = false;
		}
		if(code == KeyEvent.VK_ENTER) {
			enterPressed = false;
		}
		if(code == KeyEvent.VK_F) {
			shotKeyPressed = false;
		} 
	}

}
