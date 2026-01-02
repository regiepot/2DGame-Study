package main;

import entity.Entity;


public class CollisionChecker {
	GamePanel gp;
	
	public CollisionChecker(GamePanel gp) {
		
		this.gp = gp;
	}
	
	public void checkTile(Entity entity) {
		int entityLeftWorldX = entity.worldx + entity.solidArea.x;
		int entityRightWorldX = entity.worldx + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldy + entity.solidArea.y;
		int entityBottomWorldY = entity.worldy + entity.solidArea.y + entity.solidArea.height;
		
		int entityLeftCol = entityLeftWorldX/gp.tilesize;
		int entityRightCol = entityRightWorldX/gp.tilesize;
		int entityTopRow = entityTopWorldY/gp.tilesize;
		int entityBottomRow = entityBottomWorldY/gp.tilesize;
		
		int tileNum1, tileNum2;
		
		switch(entity.direction) {
		case "up":
			entityTopRow = (entityTopWorldY - entity.speed)/gp.tilesize;
			tileNum1 = gp.tileM.MapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.MapTileNum[entityRightCol][entityTopRow];
			
			if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "down":
			entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tilesize;
			tileNum1 = gp.tileM.MapTileNum[entityLeftCol][entityBottomRow];
			tileNum2 = gp.tileM.MapTileNum[entityRightCol][entityBottomRow];
			
			if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "left":
			entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tilesize;
			tileNum1 = gp.tileM.MapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.MapTileNum[entityLeftCol][entityBottomRow];
			
			if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "right":
			entityRightCol = (entityRightWorldX + entity.speed)/gp.tilesize;
			tileNum1 = gp.tileM.MapTileNum[entityRightCol][entityTopRow];
			tileNum2 = gp.tileM.MapTileNum[entityRightCol][entityBottomRow];
			
			if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		}
		
	}
	
	public int checkObject(Entity entity, boolean player) {
		int index = 999;
		
		for (int i = 0; i < gp.obj.length; i++) {
			
			if (gp.obj[i] != null) {
				//Get entity solid area position
				entity.solidArea.x = entity.worldx + entity.solidArea.x;
				entity.solidArea.y = entity.worldy + entity.solidArea.y;
				
				//Get object solid area position
				gp.obj[i].solidArea.x = gp.obj[i].worldx + gp.obj[i].solidArea.x;
				gp.obj[i].solidArea.y = gp.obj[i].worldy + gp.obj[i].solidArea.y;
				
				
				switch(entity.direction) {
				case "up": entity.solidArea.y -= entity.speed; break;
				case "down": entity.solidArea.y += entity.speed; break;
				case "left": entity.solidArea.x -= entity.speed; break;
				case "right": entity.solidArea.x += entity.speed; break;
				}
				
				if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
					if (gp.obj[i].collision == true) {
						entity.collisionOn = true;
					}
					if(player == true) {
						index = i;
					}
				
				}
				
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
				gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;		
				}
			}
		
		return index;
		
	}
	//NPC and Monster collision
	public int checkEntity (Entity entity, Entity target[]) {
		
	int index = 999;
		
		for (int i = 0; i < target.length; i++) {
			
			if (target[i] != null) {
				//Get entity solid area position
				entity.solidArea.x = entity.worldx + entity.solidArea.x;
				entity.solidArea.y = entity.worldy + entity.solidArea.y;
				//Get object solid area position
				target[i].solidArea.x =	target[i].worldx + target[i].solidArea.x;
				target[i].solidArea.y = target[i].worldy + target[i].solidArea.y;
				
				
				switch(entity.direction) {
				case "up": entity.solidArea.y -= entity.speed; break;
				case "down": entity.solidArea.y += entity.speed; break;
				case "left": entity.solidArea.x -= entity.speed; break;
				case "right": entity.solidArea.x += entity.speed; break;
					}
				if(entity.solidArea.intersects(target[i].solidArea)) {
					if(target[i] != entity) {
						entity.collisionOn = true;
						index = i;
						}
					}
				
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				target[i].solidArea.x = target[i].solidAreaDefaultX;
				target[i].solidArea.y = target[i].solidAreaDefaultY;		
				}
		}
		
		return index;
		
	}
	
	public boolean checkPlayer(Entity entity) {
		
		boolean contactPlayer = false;
		
		//Get entity solid position
		entity.solidArea.x = entity.worldx + entity.solidArea.x;
		entity.solidArea.y = entity.worldy + entity.solidArea.y;
		
		//Get object solid area position
		gp.player.solidArea.x =	gp.player.worldx + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldy +gp.player.solidArea.y;
		
		
		switch(entity.direction) {
		case "up": entity.solidArea.y -= entity.speed; break;
		case "down": entity.solidArea.y += entity.speed; break;
		case "left": entity.solidArea.x -= entity.speed; break;
		case "right": entity.solidArea.x += entity.speed; break;
		}
		
		if(entity.solidArea.intersects(gp.player.solidArea)) {
			entity.collisionOn=true;
			contactPlayer = true;
				}
		
		entity.solidArea.x = entity.solidAreaDefaultX;
		entity.solidArea.y = entity.solidAreaDefaultY;
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;		
		
		return contactPlayer;
		
	}
}
