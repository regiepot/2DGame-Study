package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {
	
	GamePanel gp;
	
	
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2,
	attackLeft1, attackLeft2, attackRight1, attackRight2;
	public BufferedImage image,image2,image3;
	public Rectangle solidArea = new Rectangle(0, 0, 48,48);
	public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
	String dialogues[] = new String[20];
	
	// STATE
	public int worldx,worldy;
	public int solidAreaDefaultX, solidAreaDefaultY;
	public String direction = "down";
	public boolean collisionOn = false;
	public boolean invinsible = false; 
	public boolean collision = false;
	int dialogueIndex = 0;
	boolean attacking = false;
	public boolean alive = true;
	public boolean dying = false;
	boolean hpBarOn = false;
	
	// COUNTER
	
	public int invinsibleCounter = 0;
	public int actionLockCounter = 0;
	public int spritecounter = 0;
	public int spriteNum = 1;
	public int dyingCounter = 0;
	public int hpBarCounter = 0;
	
	// CHARACTER ATTRIBUTES
	
	public int type; // 0 = player, 1 = NPC , 2 = monster
	public String name;
	public int speed;
	public int maxLife;
	public int life;
	public int level;
	public int strength;
	public int defense;
	public int dexterity;
	public int exp;
	public int attack;
	public int nextLevelExp;
	public int coin;
	public Entity currentWeapon;
	public Entity currentShield;
	
	// ITEM ATTRIBUTES
	public int attackValue;
	public int defenseValue;
	
	public Entity (GamePanel gp) {
		this.gp = gp;
	}
	
	
	public void damageReaction() {}
	
	public void setAction() {}
	
public void speak() {
		
		if(dialogues[dialogueIndex] == null) {
			dialogueIndex = 0;
		}
		gp.ui.currentDialogue = dialogues[dialogueIndex];
		dialogueIndex++;
		
		switch(gp.player.direction){
			case "up":
				direction = "down";
				break;
			case "down":
				direction = "up";
				break;
			case "left":
				direction = "right";
				break;
			case "right":
				direction = "left";
				break;
		}
	}
	
	public void update () {
		setAction();
		
		collisionOn = false;
		gp.cChecker.checkTile(this);
		gp.cChecker.checkObject(this, false);
		gp.cChecker.checkEntity(this, gp.npc);
		gp.cChecker.checkEntity(this, gp.monster);
		boolean contactPlayer = gp.cChecker.checkPlayer(this);
		
		if(this.type == 2 && contactPlayer == true) {
			if(gp.player.invinsible == false) {
				gp.playSE(7);
				int damage = attack - gp.player.defense;
				
				if (damage < 0) {
					damage = 0;
				}
				
				gp.player.life -= damage;
				gp.player.invinsible = true;
			}
		}
		
		
		if (collisionOn == false) {
			switch(direction) {
			case "up": worldy -= speed; break;
			case "down": worldy += speed; break;
			case "left": worldx -= speed; break;
			case "right": worldx += speed; break;
			}
			
		}
		
		spritecounter++;
		if(spritecounter > 10) {
			if (spriteNum == 1) {
				spriteNum = 2;
			}
			else if (spriteNum == 2) {
				spriteNum = 1;
			}
			spritecounter = 0;
			}
		
		
		
		if (invinsible == true) {
			invinsibleCounter++;
			
			if (invinsibleCounter > 30) {
				invinsible = false;
				invinsibleCounter = 0;
			}
			
		}
	}
		
	
	
	
	public void draw (Graphics2D g2) {
		int screenx = worldx - gp.player.worldx + gp.player.screenx;
		int screeny = worldy - gp.player.worldy + gp.player.screeny;
		
		if (worldx + gp.tilesize > gp.player.worldx - gp.player.screenx &&
			worldx - gp.tilesize < gp.player.worldx + gp.player.screenx &&
			worldy + gp.tilesize > gp.player.worldy - gp.player.screeny &&
			worldy - gp.tilesize < gp.player.worldy + gp.player.screeny) {
			
			BufferedImage image = null;
			
			switch (direction) {
			case "up":
				if(spriteNum == 1) {image = up1;}
				if(spriteNum == 2) {image = up2;}
				break;
			case "down":
				if(spriteNum == 1) {image = down1;}
				if(spriteNum == 2) {image = down2;}
				break;
			case "left":
				if(spriteNum == 1) {image = left1;}
				if(spriteNum == 2) {image = left2;}
				break;
			case "right":
				if(spriteNum == 1) {image = right1;}
				if(spriteNum == 2) {image = right2;}
				break;
			}
			
			//Monster HP bar
			if(type == 2 && hpBarOn == true) {
				
				double oneScale = (double)gp.tilesize/maxLife;
				double hpBarValue = oneScale*life;
				
				g2.setColor(new Color(35,35,35));
				g2.fillRect(screenx, screeny - 15, gp.tilesize,10);
				g2.setColor(new Color(255,0,0));
				g2.fillRect(screenx, screeny - 15, (int)hpBarValue,10);
				g2.setColor(new Color(20,20,20));
				g2.drawRect(screenx, screeny - 15, gp.tilesize, 10);
				
				hpBarCounter++;
				
				if(hpBarCounter > 400){
					hpBarCounter = 0;
					hpBarOn = false;
				}
				
				
			}
			
			
			if(invinsible == true) {
				hpBarOn = true;
				hpBarCounter = 0;
				changeAlpha(g2,0.4F);
				}
			
			if(dying == true) {dyingAnimation(g2);}
			
			g2.drawImage(image, screenx, screeny, null);changeAlpha(g2,1F);}
	}
	
	public void dyingAnimation(Graphics2D g2) {
		dyingCounter++;
		
		int i = 5;
		
		if(dyingCounter <= i) {changeAlpha(g2, 0f);}
		if(dyingCounter > i && dyingCounter <= i*2) {changeAlpha(g2, 1f);}
		if(dyingCounter > i*2 && dyingCounter <= i*3) {changeAlpha(g2, 0f);}
		if(dyingCounter > i*3 && dyingCounter <= i*4) {changeAlpha(g2, 1f);}
		if(dyingCounter > i*4 && dyingCounter <= i*5) {changeAlpha(g2, 0f);}
		if(dyingCounter > i*5 && dyingCounter <= i*6) {changeAlpha(g2, 1f);}
		if(dyingCounter > i*6 && dyingCounter <= i*7) {changeAlpha(g2, 0f);}
		if(dyingCounter > i*7 && dyingCounter <= i*8) {changeAlpha(g2, 1f);}
		
		if (dyingCounter > i*8 ) {
			dying = false;
			alive = false;
		}
		
	}
	
	public void changeAlpha(Graphics2D g2, float alphaValue) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
	}

	public BufferedImage setup(String imagePath, int width, int height) {
		UtilityTool uTool = new UtilityTool();
		BufferedImage Image = null;
		
		try {
			Image = ImageIO.read(getClass().getResourceAsStream(imagePath +".png"));
			Image = uTool.scaleImage(Image, width, height);
		}
		
		
		catch (IOException e) {
			e.printStackTrace();
		}
		return Image;
	}
	
}
