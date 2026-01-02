package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import object.OBJ_Heart;
import entity.Entity;

public class UI {
	GamePanel gp;
	Graphics2D g2;
	Font maruMonica;
	BufferedImage heart_full, heart_half, heart_empty;
	public boolean messageOn = false;
	//public String message = "";
	ArrayList<String> message = new ArrayList<>();
	ArrayList<Integer> messageCounter = new ArrayList<>();
	//int messageCounter = 0;
	public boolean gameFinished = false; 
	public String currentDialogue;
	public int commandNum = 0;
	public int titleScreenState = 0; //0 first screen, 1 2nd screen
	
	
	public UI (GamePanel gp) {
		this.gp = gp;
		
		
		try {
			InputStream is = getClass().getResourceAsStream("/fonts/x12y16pxMaruMonica.ttf");
			maruMonica = Font.createFont(Font.TRUETYPE_FONT,is);
		} catch (FontFormatException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		//HUD OBJ
		Entity heart = new OBJ_Heart(gp);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_empty = heart.image3;
	}
	
	
	
	public void addMessage (String text) {
		message.add(text);
		messageCounter.add(0);
	}
	
	
	
	
	
	
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		
		g2.setFont(maruMonica);
		g2.setColor(Color.white);
		
		//TITLE STATE
		if (gp.gameState == gp.titleState) {
			drawTitleScreen();
		}
		
		//PLAYSTATE
		if(gp.gameState == gp.playState) {
			drawPlayerLife();
			drawMessage();
		}
		
		//PAUSESTATE
		if (gp.gameState == gp.pauseState) {
			drawPauseScreen();
		}
		
		//DIALOGUE
		if(gp.gameState == gp.dialogueState) {
			drawDialogueScreen();
		}
		if(gp.gameState == gp.characterState) {
			drawCharacterScreen();
		}
	}
	
	public void drawPlayerLife() {
		
		//gp.player.life = 1;
		
		int x = gp.tilesize/2;
		int y = gp.tilesize/2;
		int i = 0;
		
		
		//Draw blank hearts
		while (i < gp.player.maxLife/2) {
			g2.drawImage(heart_empty, x, y,null);
			i++;
			x += gp.tilesize;
		}
		
		//RESET
			x = gp.tilesize/2;
			y = gp.tilesize/2;
			i = 0;
			
		//Draw current life
			while (i < gp.player.life) {
				g2.drawImage(heart_half, x, y,null);
				i++;
				if (i < gp.player.life) {
					g2.drawImage(heart_full, x, y,null);
				}
				i++;
				x += gp.tilesize;
			
		}
	}
	
	public void drawMessage() {
		int messsageX = gp.tilesize;
		int messageY = gp.tilesize*4;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,32F));
		
		for (int i = 0; i < message.size(); i++) {
			if(message.get(i) != null) {
				
				g2.setColor(Color.black);
				g2.drawString(message.get(i), messsageX+2, messageY+2);
				
				g2.setColor(Color.white);
				g2.drawString(message.get(i), messsageX, messageY);
				
			int counter = messageCounter.get(i) + 1;
			messageCounter.set (i, counter);
			messageY += 50;
			if (messageCounter.get(i) > 160) {
				message.remove(i);
				messageCounter.remove(i);
			}
				
			}
		}
		
	}
	
	public void drawTitleScreen() {
		
		if(titleScreenState == 0) {
		
		g2.setColor(new Color(0,0,0));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		//Title Name
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
		String text = "I DON'T KNOW";
		int x = getXforCenteredText(text);
		int y = gp.tilesize*3;
		//SHADOW
		g2.setColor(Color.gray);
		g2.drawString(text, x+4, y+4);
		//Main Color
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
		//Draw Image
		x = gp.screenWidth/2 - (gp.tilesize*2)/2;
		y += gp.tilesize*2;
		g2.drawImage(gp.player.down1, x, y, gp.tilesize*2, gp.tilesize*2, null);
		
		//Menu
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
		
		text = "PLAY";
		x = getXforCenteredText(text);
		y += gp.tilesize*3.5;
		g2.drawString(text, x, y);
		if (commandNum == 0) {
			g2.drawString(">", x - gp.tilesize, y);
		}
		
		text = "OTHERS";
		x = getXforCenteredText(text);
		y += gp.tilesize;
		g2.drawString(text, x, y);
		if (commandNum == 1) {
			g2.drawString(">", x - gp.tilesize, y);
		}
		
		text = "QUIT";
		x = getXforCenteredText(text);
		y += gp.tilesize;
		g2.drawString(text, x, y);
		if (commandNum == 2) {
			g2.drawString(">", x - gp.tilesize, y);
					}
			}
		
		
		
		//EXTRA
		else if (titleScreenState == 1) {
			g2.setColor(Color.black);
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
			
			
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(42F));
			String text = "Select your class!";
			int x = getXforCenteredText(text);
			int y = gp.tilesize*3;
			g2.drawString(text, x, y);
			
			text = "Fighter";
			x = getXforCenteredText(text);
			y += gp.tilesize*3;
			g2.drawString(text, x, y);
			
			if (commandNum == 0) {
				g2.drawString(">", x - gp.tilesize, y);
			}
			
			
			text = "Thief";
			x = getXforCenteredText(text);
			y += gp.tilesize;
			g2.drawString(text, x, y);
			
			if (commandNum == 1) {
				g2.drawString(">", x - gp.tilesize, y);
			}
			
			
			text = "Sorcerer";
			x = getXforCenteredText(text);
			y += gp.tilesize;
			g2.drawString(text, x, y);
			
			if (commandNum == 2) {
				g2.drawString(">", x - gp.tilesize, y);
			}
			
			
			text = "Back";
			x = getXforCenteredText(text);
			y += gp.tilesize*2;
			g2.drawString(text, x, y);
			
			if (commandNum == 3) {
				g2.drawString(">", x - gp.tilesize, y);
			}
			
			
		}
	}
	
	public void drawPauseScreen() {
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
		String text = "-PAUSED-";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight/2;
		
		g2.drawString(text, x, y);
		
	}
	
	public void drawDialogueScreen() {
		//WINDOW
		int x = gp.tilesize*2;
		int y = gp.tilesize/2;
		int width = gp.screenWidth - (gp.tilesize*4);
		int height = gp.tilesize*4;
		
		drawSubWindow(x,y,width,height);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
		x += gp.tilesize;
		y += gp.tilesize;
		
		for (String line : currentDialogue.split("\n")) {
			g2.drawString(line, x, y);
			y += 40;
		}
		
	}
	
	public void drawCharacterScreen() {
		
		//Create frame
		final int frameX = gp.tilesize;
		final int frameY = gp.tilesize;
		final int frameWidth = gp.tilesize *5;
		final int frameHeight = gp.tilesize *10;
		
		// TEXT
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,30F));
		
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		int textX = frameX + 15;
		int textY = frameY * 2;
		final int lineHeight = 32;
		
		
		g2.drawString("Level ", textX, textY);
		textY += lineHeight;
		g2.drawString("Life ", textX, textY);
		textY += lineHeight;
		g2.drawString("Strength", textX, textY);
		textY += lineHeight;
		g2.drawString("Dexterity", textX, textY);
		textY += lineHeight;
		g2.drawString("Attack", textX, textY);
		textY += lineHeight;
		g2.drawString("Defense", textX, textY);
		textY += lineHeight;
		g2.drawString("Exp", textX, textY);
		textY += lineHeight;
		g2.drawString("Next Level", textX, textY);
		textY += lineHeight;
		g2.drawString("Coin", textX, textY);
		textY += lineHeight + 20;
		g2.drawString("Weapon", textX, textY);
		textY += lineHeight + 18;
		g2.drawString("Shield", textX, textY);
		textY += lineHeight;
		
		
		//VALUEs
		int tailX = (frameX + frameWidth) - 30;
		//reset text y
		textY = frameY + gp.tilesize;
		
		String value;
		
		value = String.valueOf(gp.player.level);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		value = String.valueOf(gp.player.life +"/"+gp.player.maxLife);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		value = String.valueOf(gp.player.strength);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		value = String.valueOf(gp.player.dexterity);
		textY += lineHeight;
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		value = String.valueOf(gp.player.attack);
		textY += lineHeight;
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		value = String.valueOf(gp.player.defense);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		value = String.valueOf(gp.player.exp);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		value = String.valueOf(gp.player.nextLevelExp);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		value = String.valueOf(gp.player.coin);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tilesize, textY - 14, null);
		textY += gp.tilesize;
		g2.drawImage(gp.player.currentShield.down1, tailX - gp.tilesize, textY - 14, null);
		
	}
	
	
	public void drawSubWindow(int x,int y, int width, int height) {
		Color c = new Color(0,0,0,210);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		c = new Color(255,255,255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
	}
	
	public int getXforCenteredText(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		return x;
	}
	
	public int getXforAlignToRightText(String text , int tailX) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = tailX - length;
		return x;
	}
}
