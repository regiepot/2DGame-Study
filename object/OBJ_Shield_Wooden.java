package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Wooden extends Entity{
	
	GamePanel gp;
	public OBJ_Shield_Wooden(GamePanel gp) {
		super(gp);
		name = "Wooden Shield";
		
		down1 = setup("/objects/wooden_shield",gp.tilesize,gp.tilesize);
		defenseValue = 1;
	}
}
