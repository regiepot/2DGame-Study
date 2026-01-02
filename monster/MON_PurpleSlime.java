package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;

public class MON_PurpleSlime extends Entity{

	GamePanel gp;
	
	public MON_PurpleSlime(GamePanel gp) {
		super(gp);
		this.gp= gp;
		
		type = 2;
		name = "purple slime";
		speed = 1;
		maxLife = 4;
		life = maxLife;
		attack = 5;
		defense = 0;
		exp = 2;
		
		
		solidArea.x = 3; //9
		solidArea.y = 18; //18
		solidArea.width = 42; //28
		solidArea.height = 30; //29
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		getImage();
	}
	public void getImage() {
		up1 = setup("/monsters/slime_1", gp.tilesize, gp.tilesize);
		up2 = setup("/monsters/slime_2", gp.tilesize, gp.tilesize);
		down1 = setup("/monsters/slime_1", gp.tilesize, gp.tilesize);
		down2 = setup("/monsters/slime_2", gp.tilesize, gp.tilesize);
		left1 = setup("/monsters/slime_1", gp.tilesize, gp.tilesize);
		left2 = setup("/monsters/slime_2", gp.tilesize, gp.tilesize);
		right1 = setup("/monsters/slime_1", gp.tilesize, gp.tilesize);
		right2 = setup("/monsters/slime_2", gp.tilesize, gp.tilesize);
	}
	
	public void setAction() {
		
		actionLockCounter++;
		
		if (actionLockCounter == 120) {

			Random random = new Random();
			int i = random.nextInt(100)+1;
			
			if (i <= 25) {
				direction = "up";
			}
			if (i > 25 && i <= 50) {
				direction = "down";
			}
			if (i > 50 && i <= 75) {
				direction = "left";
			}
			if (i > 75 && i <= 100) {
				direction = "right";
			}
			actionLockCounter = 0;
		}
		
	}

	public void damageReaction () {
		actionLockCounter = 0;
		switch(gp.player.direction) {
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

}
