package object;


import entity.Entity;
import main.GamePanel;

public class OBJ_Door extends Entity {
	public OBJ_Door(GamePanel gp) {
		super(gp);
		
		name = "door";
		down1 = setup("/objects/door", gp.tilesize, gp.tilesize);
		collision = true;
		
		solidArea.x = 0; //9
		solidArea.y = 16; //18
		solidArea.width = 48; //28
		solidArea.height = 32; //29
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}

}
