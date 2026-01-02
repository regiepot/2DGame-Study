package entity;

import java.util.Random;

import main.GamePanel;

public class NPC_OldMan extends Entity {
	
	public NPC_OldMan(GamePanel gp) {


		super(gp);
		
		direction = "down";
		speed = 1;
		solidArea.x = 9; //9
		solidArea.y = 18; //18
		solidArea.width = 28; //28
		solidArea.height = 29; //29
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		getImage();
		setDialogue();
		
		
		
	}
	public void getImage() {

		up1 = setup("/npc/trostup1", gp.tilesize, gp.tilesize);
		up2 = setup("/npc/trostup2", gp.tilesize, gp.tilesize);
		down1 = setup("/npc/trostdown1", gp.tilesize, gp.tilesize);
		down2 = setup("/npc/trostdown2", gp.tilesize, gp.tilesize);
		left1 = setup("/npc/trostleft1", gp.tilesize, gp.tilesize);
		left2 = setup("/npc/trostleft2", gp.tilesize, gp.tilesize);
		right1 = setup("/npc/trostright1", gp.tilesize, gp.tilesize);
		right2 = setup("/npc/trostright2", gp.tilesize, gp.tilesize);
		
	}
	
	public void setDialogue() {
		dialogues[0] = "Uhhh... Hello?";
		dialogues[1] = "Where did you came from?";
		dialogues[2] = "It's rare to see another human \naround here.";
		dialogues[3] = "I've been here a long time living \nin isolation. Nice way to ruin it.";
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

public void speak() {
	//Custom stuff
	super.speak();
}

}
