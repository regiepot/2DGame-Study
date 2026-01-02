package main;


public class EventHandler {
	
	GamePanel gp;

	EventRect eventRect [][];
	int previousEventX, previousEventY;
	boolean canTouchEvent = true;
	
	
	public EventHandler(GamePanel gp) {
		this.gp = gp;
		eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];
		
		int col = 0;
		int row = 0;
		
		while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
			eventRect[col][row] = new EventRect();
			eventRect[col][row].x = 23;
			eventRect[col][row].y = 23;
			
			eventRect[col][row].width =2;
			eventRect[col][row].height =2;
			
			eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
			eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;
			
			col++;
			if(col == gp.maxWorldCol) {
				col = 0;
				row++;
			}
		}
		

		
	}
	
	public void checkEvent() {
		//Check if player is 1 tile away from event
		int xDistance = Math.abs(gp.player.worldx - previousEventX);
		int yDistance = Math.abs(gp.player.worldy - previousEventY);
		int distance = Math.max(xDistance, yDistance);
		
		
		if(distance > gp.tilesize) {
			canTouchEvent = true;
		}
		
		if (canTouchEvent == true) {
			if(hit(27, 27, "any") == true) {damagePit(27, 27, gp.dialogueState);}
			if(hit(25, 23, "up") == true) {healPool(25, 23, gp.dialogueState);}
			if(hit(25, 14, "any") == true) {teleport(25, 14, gp.dialogueState);}
		}
		

		
	}
	
	public boolean hit(int col, int row, String reqDirection) {
		boolean hit = false;
		gp.player.solidArea.x = gp.player.worldx + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldy + gp.player.solidArea.y;
		eventRect[col][row].x = col * gp.tilesize + eventRect[col][row].x;
		eventRect[col][row].y = row * gp.tilesize + eventRect[col][row].y;
		
		if (gp.player.solidArea.intersects(eventRect[col][row]) && eventRect[col][row].eventDone == false) {
			if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
				hit = true;
				
				previousEventX = gp.player.worldx;
				previousEventY = gp.player.worldy;
				
			}
		}
		
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
		eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;
		
		
		
		return hit;
	}
	public void damagePit(int col, int row, int gameState) {
		gp.gameState = gameState;
		gp.playSE(7);
		gp.ui.currentDialogue = "A bug bit you.";
		gp.player.life -=1;
	//	eventRect[col][row].eventDone = true;
		canTouchEvent = false;
	}
	
	public void healPool(int col, int row, int gameState) {
		if (gp.keyH.interactT == true) {
			gp.player.attackCanceled = true;
			gp.playSE(3);
			gp.gameState = gameState;
			gp.ui.currentDialogue = "You drink the water.\nYour health is restored.";
			gp.player.life = gp.player.maxLife;
			gp.aSetter.setMonster();
		}
	}
	
	public void teleport(int col, int row, int gameState) {
		gp.playSE(2);
		gp.gameState = gameState;
		gp.ui.currentDialogue = "Teleport?";
		gp.player.worldx = gp.tilesize*35;
		gp.player.worldy = gp.tilesize*28;
		
	}
	
	
	
	
	

}









