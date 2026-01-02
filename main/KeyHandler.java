package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
	
	public boolean upT, downT, leftT, rightT, interactT;
	public boolean showDebug = false;
	GamePanel gp;
	public KeyHandler (GamePanel gp) {
		this.gp = gp;
	}
	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		//TITLE STATE
		if (gp.gameState == gp.titleState) {
			titleState(code);	
		}
		
		//PLAYSTATE
		else if (gp.gameState == gp.playState) {
			playState(code);
		}
		//PAUSE STATE
		else if (gp.gameState == gp.pauseState) {
			pauseState(code);
		}
	
		//DIALOGUE STATE
		else if (gp.gameState == gp.dialogueState) {
			dialogueState(code);
		}
		
		else if (gp.gameState == gp.characterState) {
			characterState(code);
		}
		
	}
	
	public void titleState (int code) {
		if (gp.ui.titleScreenState == 0) {
			
			if (code == KeyEvent.VK_W) {
				gp.ui.commandNum--;
				if(gp.ui.commandNum < 0) {
					gp.ui.commandNum = 2;
				}
			}
			if (code == KeyEvent.VK_S) {
				gp.ui.commandNum++;
				if(gp.ui.commandNum > 2) {
					gp.ui.commandNum = 0;
				}
			}
			if (code == KeyEvent.VK_SPACE) {
				
				if (gp.ui.commandNum == 0) {
					gp.gameState = gp.playState;
					gp.playMusic(0);
					
				}
				if (gp.ui.commandNum == 1) {
					gp.ui.titleScreenState = 1;
					
					gp.ui.commandNum = 0;
				}
				if (gp.ui.commandNum == 2) {
					System.exit(0);
				}
				
			}
			
		}
	
		else if (gp.ui.titleScreenState == 1) {
				if (code == KeyEvent.VK_W) {
					gp.ui.commandNum--;
					if(gp.ui.commandNum < 0) {
						gp.ui.commandNum = 3;
					}
				}
				if (code == KeyEvent.VK_S) {
					gp.ui.commandNum++;
					if(gp.ui.commandNum > 3) {
						gp.ui.commandNum = 0;
					}
				}
				if (code == KeyEvent.VK_SPACE) {
					
					if (gp.ui.commandNum == 0) {
						gp.gameState = gp.playState;
						gp.playMusic(0);
					}
					if (gp.ui.commandNum == 1) {
						gp.gameState = gp.playState;
						gp.playMusic(0);
					}
					if (gp.ui.commandNum == 2) {
						gp.gameState = gp.playState;
						gp.playMusic(0);
					}
					if (gp.ui.commandNum == 3) {
						gp.ui.titleScreenState = 0;
						gp.ui.commandNum = 0;
					}
				}
			
		}
	}
	
	public void playState (int code) {
		if (code == KeyEvent.VK_W) {
			upT = true;
		}
		if (code == KeyEvent.VK_A) {
			leftT = true;
		}
		if (code == KeyEvent.VK_S) {
			downT = true;
		}
		if (code == KeyEvent.VK_D) {
			rightT = true;
		}
		if (code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.pauseState;
			gp.stopMusic();
		}
		
		if(code == KeyEvent.VK_C) {
			gp.gameState = gp.characterState;
		}
		
		if (code == KeyEvent.VK_SPACE) {
			interactT = true;
		}
		
		
		if (code == KeyEvent.VK_F2) {
			if (showDebug == false) {
				showDebug = true;
			}
			else if (showDebug == true) {
				showDebug = false;
			}
		}
	}
	
	public void pauseState (int code) {
		if (code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.playState;
					gp.music.play();
					
		}				
	}
	
	public void dialogueState(int code) {
		if (code == KeyEvent.VK_SPACE) {
			gp.gameState = gp.playState;
		}
	}
	
	public void characterState (int code) {
		if (code == KeyEvent.VK_C) {
			gp.gameState = gp.playState;
			
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		
		if (code == KeyEvent.VK_W) {
			upT = false;
		}
		if (code == KeyEvent.VK_A) {
			leftT = false;
		}
		if (code == KeyEvent.VK_S) {
			downT = false;
		}
		if (code == KeyEvent.VK_D) {
			rightT = false;
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
