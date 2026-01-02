package main;

import entity.NPC_OldMan;
import monster.MON_PurpleSlime;
import object.OBJ_Door;

@SuppressWarnings("unused")
public class AssetSetter {
	
	GamePanel gp;
	
	public AssetSetter (GamePanel gp) {
		this.gp = gp;
	}
	public void setObject () {

	}
	
	public void setNPC() {
		gp.npc[0] = new NPC_OldMan(gp);
		gp.npc[0].worldx = gp.tilesize * 25;
		gp.npc[0].worldy = gp.tilesize * 28;

	}
	
	public void setMonster() {
		int i = 0;
		
		gp.monster[i] = new MON_PurpleSlime(gp);
		gp.monster[i].worldx = gp.tilesize * 35;
		gp.monster[i].worldy = gp.tilesize * 20;
		i++;
		gp.monster[i] = new MON_PurpleSlime(gp);
		gp.monster[i].worldx = gp.tilesize * 33;
		gp.monster[i].worldy = gp.tilesize * 21;
		i++;
		gp.monster[i] = new MON_PurpleSlime(gp);
		gp.monster[i].worldx = gp.tilesize * 33;
		gp.monster[i].worldy = gp.tilesize * 22;
		i++;
		
	}
}
