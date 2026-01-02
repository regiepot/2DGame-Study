package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
//import java.awt.Font;
//import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


import main.GamePanel;
import main.KeyHandler;
import object.OBJ_Shield_Wooden;
import object.OBJ_Sword_Normal;

public class Player extends Entity {
	
	KeyHandler keyH;
	
	public final int screenx;
	public final int screeny;
	public boolean attackCanceled = false;
	
	//DEBUG TOOLS
	public boolean showHitbox = false;
	
	//COUNTER
	public int standCounter = 0;
	int pixelCounter = 0;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		super(gp);
		this.keyH = keyH;
		
		screenx = gp.screenWidth / 2 - (gp.tilesize/2);
		screeny = gp.screenHeight / 2 - (gp.tilesize/2);
		
		solidArea = new Rectangle(); //x,y,w,h
		solidArea.x = 9; //9
		solidArea.y = 18; //18
		solidArea.width = 28; //28
		solidArea.height = 29; //29
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		attackArea.width = 36;
		attackArea.height = 36;
		
		setDefaultValues();
		getPlayerAttackImage();
		getPlayerImage();
		
	}
	
	public void setDefaultValues() {
		worldx = gp.tilesize *25;
		worldy = gp.tilesize *27;
		speed = 3;
		direction = "down";
		
		//Player Status
		level = 1;
		strength = 1; //More damage
		dexterity = 1; //More defense 
		exp = 0;
		nextLevelExp = 5;
		coin = 0;
		
		currentWeapon = new OBJ_Sword_Normal(gp);
		currentShield = new OBJ_Shield_Wooden(gp);
		attack = getAttack(); //Strength and weapon
		defense = getDefense(); // Dexterity and shield
		
		
		maxLife = 6;
		life = maxLife;
	}
	
	public int getAttack () {
		return attack = strength * currentWeapon.attackValue;
		
	}
	public int getDefense () {
		return defense = dexterity * currentShield.defenseValue;
	}
	
	public void getPlayerImage() {

		up1 = setup("/player/up_1",gp.tilesize, gp.tilesize);
		up2 = setup("/player/up_2", gp.tilesize, gp.tilesize);
		down1 = setup("/player/down_1", gp.tilesize, gp.tilesize);
		down2 = setup("/player/down_2", gp.tilesize, gp.tilesize);
		left1 = setup("/player/left_1", gp.tilesize, gp.tilesize);
		left2 = setup("/player/left_2", gp.tilesize, gp.tilesize);
		right1 = setup("/player/right_1", gp.tilesize, gp.tilesize);
		right2 = setup("/player/right_2", gp.tilesize, gp.tilesize);
		
	}
	
	public void getPlayerAttackImage() {
		attackUp1 = setup("/player/attack_up_1",gp.tilesize, gp.tilesize*2);
		attackUp2 = setup("/player/attack_up_2",gp.tilesize, gp.tilesize*2);
		attackDown1 = setup("/player/attack_down_1",gp.tilesize, gp.tilesize*2);
		attackDown2 = setup("/player/attack_down_2",gp.tilesize, gp.tilesize*2);
		attackLeft1 = setup("/player/attack_left_1", gp.tilesize*2, gp.tilesize);
		attackLeft2 = setup("/player/attack_left_2", gp.tilesize*2, gp.tilesize);
		attackRight1 = setup("/player/attack_right_1", gp.tilesize*2, gp.tilesize);
		attackRight2 = setup("/player/attack_right_2", gp.tilesize*2, gp.tilesize);
	}

	public void update() {	
		
	if(attacking == true) {
			
		attacking();
		
		}
		
	else {
		if (keyH.upT == true || keyH.downT == true ||
				keyH.rightT == true || keyH.leftT == true || keyH.interactT == true) {
		if (keyH.upT == true ) {
			direction = "up";
		}
		if (keyH.downT == true ) {
			direction = "down";
		}
		if (keyH.leftT == true ) {
			direction = "left";
		}
		if (keyH.rightT == true ) {
			direction = "right";
		}
		
		
	// Check tile Collision Settings
		collisionOn = false;
		gp.cChecker.checkTile(this);
	//Check Object Collision
		int objIndex = gp.cChecker.checkObject(this, true);
		pickUpObject(objIndex);
		
	//Check NPC collision
		int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
		interactNPC(npcIndex);
		
	//Check Monster collision
		int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
		contactMonster(monsterIndex);
		
	//Check Event
		gp.eHandler.checkEvent();
		
		
		
		
		
	//if collision false, player can move
		if (collisionOn == false && keyH.interactT == false) {
			switch(direction) {
			case "up": worldy -= speed; break;
			case "down": worldy += speed; break;
			case "left": worldx -= speed; break;
			case "right": worldx += speed; break;
			}
			
		}
		
		if(keyH.interactT == true && attackCanceled == false) {
			gp.playSE(6);
			attacking = true;
			spritecounter = 0;
		}
		
		attackCanceled = false;
		gp.keyH.interactT = false;
		
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
		}
		
		else {
			standCounter++;
			if (standCounter == 20){
				spriteNum = 1;
				standCounter = 0;
				}
			
			}
		}
		
		//Outside of key if statement
		if (invinsible == true) {
			invinsibleCounter++;
			
			if (invinsibleCounter > 60) {
				invinsible = false;
				invinsibleCounter = 0;
			}
			
		}
		
	}
	
	public void attacking () {
		spritecounter++;
		
		if (spritecounter <= 5) {
			spriteNum = 1;
		}
		if (spritecounter > 5 && spritecounter <= 20) {
			spriteNum = 2;
			
			//Save current x,y,solid area
			int currentWorldX = worldx;
			int currentWorldY = worldy;
			int solidAreaWidth = solidArea.width;
			int solidAreaHeight = solidArea.height;
			
			//Adjust player's world x/y for attack area
			switch(direction) {
			case "up": worldy -= attackArea.height; break;
			case "down": worldy += attackArea.height; break;
			case "left": worldx -= attackArea.width; break;
			case "right": worldx += attackArea.width; break;
			}
			//Attack area becomes solidArea
			solidArea.width = attackArea.width;
			solidArea.height = attackArea.height;
			
			//check monster collision with updated world x y solidArea
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			damageMonster(monsterIndex);
			
			//after check monster collision revert x y solidArea 
			worldx = currentWorldX;
			worldy = currentWorldY;
			solidArea.width = solidAreaWidth;
			solidArea.height = solidAreaHeight;
			
			
		}
		if (spritecounter > 20) {
			spriteNum = 1;
			spritecounter = 0;
			attacking = false;
		}
		
	}
	
	public void pickUpObject (int i) {
		if(i != 999) {
			
		}
	}
	
public void interactNPC(int i) {
	
	if(gp.keyH.interactT == true) {
		if(i != 999) {
				attackCanceled = true;
				gp.gameState = gp.dialogueState;
				gp.npc[i].speak();
			}
			
		}
	}
	
public void contactMonster(int i) {
	if (i != 999) {
		if (invinsible == false) {
			gp.playSE(7);
			int damage = gp.monster[i].attack - defense;
			
			if (damage < 0) {
				damage = 0;
			}
			
			life -= damage;
			invinsible = true;
		}
		
	}
	}

public void damageMonster(int i){
	
	if (i != 999) {
		if(gp.monster[i].invinsible == false) {
			gp.playSE(8);
			
			int damage = attack - gp.monster[i].defense;
			gp.ui.addMessage(damage + " damage.");
			
			if (damage < 0) {
				damage = 0;
			}
			
			gp.monster[i].life -= damage;
			gp.monster[i].invinsible = true;
			gp.monster[i].damageReaction();
			
			if(gp.monster[i].life <= 0) {
				gp.monster[i].dying = true;
				exp += gp.monster[i].exp;
				gp.ui.addMessage("You killed a " + gp.monster[i].name + ".");
				gp.ui.addMessage("Exp +" + gp.monster[i].exp +"!");
				checkLevelUp();
			}
			
		}
		
	}
	
}

	public void checkLevelUp() {
		if (exp >= nextLevelExp) {
			gp.playSE(9);
			level++;
			nextLevelExp *= 2;
			maxLife += 2;
			life = maxLife;
			strength++;
			dexterity++;
			attack = getAttack();
			defense = getDefense();
			
			gp.gameState = gp.dialogueState;
			gp.ui.currentDialogue = "You are now level " + level + " now!\nYou feel Stronker.";
		}
	}
	
	public void draw (Graphics2D g2) {
		
		int tempScreenX = screenx;
		int tempScreenY = screeny;
		
		//g2.setColor(Color.white);
		//g2.fillRect(x, y, gp.tilesize, gp.tilesize);
		
		BufferedImage image = null;
		
		switch (direction) {
		case "up": 
			if(attacking == false) {
				if(spriteNum == 1) { image = up1; }
				if(spriteNum == 2) { image = up2; }
			}
			if(attacking == true) {
				tempScreenY = screeny - gp.tilesize;
				if(spriteNum == 1) { image = attackUp1; }
				if(spriteNum == 2) { image = attackUp2; }
			}
			
			break;
				   
		case "down": 
			if (attacking == false) {
				if(spriteNum == 1) { image = down1; }
				if(spriteNum == 2) { image = down2; }
			}
			
			if (attacking == true) {
				if(spriteNum == 1) { image = attackDown1; }
				if(spriteNum == 2) { image = attackDown2; }
			}
			
			break;
					 
		case "left":
			if(attacking == false) {
				if(spriteNum == 1) { image = left1; }
				if(spriteNum == 2) { image = left2; }
			}
			
			if(attacking == true) {
				tempScreenX = screenx - gp.tilesize;
				if(spriteNum == 1) { image = attackLeft1; }
				if(spriteNum == 2) { image = attackLeft2; }
			}
			
			break;
					 
		case "right": 
			if(attacking == false) {
				if(spriteNum == 1) { image = right1; }
				if(spriteNum == 2) { image = right2; }
			}
			
			if(attacking == true) {
				if(spriteNum == 1) { image = attackRight1; }
				if(spriteNum == 2) { image = attackRight2; }
			}
			
			break;
		}
		//Camera Border (EXTRA)
		
		/*
		int x = screenx; //replace in draw
		int y = screeny; //replace in draw
		
		if(screenx > worldx) {
			x = worldx;
		}
		
		if(screeny > worldy) {
			y = worldy;
		}
		
		int rightOffset = gp.screenWidth - screenx;
		if (rightOffset > gp.worldWidth - worldx) {
			
			x = gp.screenWidth - (gp.worldWidth - worldx);
			
		}
		
		int bottomOffset = gp.screenHeight - screeny;
		if(bottomOffset > gp.worldHeight - worldy) {
			
			y = gp.screenHeight - (gp.worldHeight - worldy);
			
		}
		*/
		if(invinsible == true) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		}
		
		
		g2.drawImage(image, tempScreenX, tempScreenY, null);
		
		//Reset Alpha
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
		//DEBUG
		if(showHitbox == true) {
			g2.setColor(Color.red);
		g2.drawRect(screenx + solidArea.x, screeny + solidArea.y, solidArea.width, solidArea.height);
		}
		
		//SHOW INVINSIBILITY COUNTER
		//g2.setFont(new Font("Arial", Font.PLAIN,26));
		//g2.setColor(Color.white);
		//g2.drawString("Invinsibility: "+ invinsibleCounter, 10, 400);
		
		
		
		
	}

}
