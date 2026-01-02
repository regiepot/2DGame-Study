package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import tile.TileManager;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable {
	
	//Screen Settings
	final int orgtilesize = 16; //32x32, 16x16 tile size
	final int scale = 3;

	public int tilesize = orgtilesize * scale; //48x48 tiles
	public int maxscreencol = 16; //column
	public int maxscreenrow = 12; //row

	public int screenWidth = tilesize * maxscreencol; //768pixel
	public int screenHeight = tilesize * maxscreenrow; //576
	
//World Settings
public final int maxWorldCol = 50;
public final int maxWorldRow = 50;
public final int worldWidth = tilesize * maxWorldCol;
public final int worldHeight = tilesize * maxWorldRow;

//FPS
int fps = 60;


//System
public CollisionChecker cChecker = new CollisionChecker(this);
public TileManager tileM = new TileManager(this);
public KeyHandler keyH = new KeyHandler(this);
Sound music = new Sound();
Sound se = new Sound();
public AssetSetter aSetter = new AssetSetter(this);
public UI ui = new UI(this);
public EventHandler eHandler = new EventHandler(this);
Thread gameThread;



//Entity and object
public Player player = new Player(this,keyH);
public Entity obj[] = new Entity[10];
public Entity npc[] = new Entity[10];
public Entity monster[] = new Entity[20];
ArrayList<Entity> entityList = new ArrayList<>();


	//GAMESTATE
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;
	public final int characterState = 4;




//Set player default position
int playerspeed = 10;
int playerX = 100;
int playerY = 100;



	public GamePanel(){
	this.setPreferredSize(new Dimension(screenWidth, screenHeight));
	this.setBackground(Color.BLACK);
	this.setDoubleBuffered(true);
	this.addKeyListener(keyH);
	this.setFocusable(true);
	}
	
	
	public void setupGame() {
		aSetter.setNPC();
		aSetter.setObject();
		aSetter.setMonster();
		//playMusic(0);
		gameState = titleState;
	}
	

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();	
		
	}

	
	public void run() {
		double drawInterval = 1000000000/fps;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		while(gameThread != null) {
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if (delta >= 1) {
				update();
				repaint();
				delta--;
				drawCount++;
			}
			if (keyH.showDebug == true) {
			if (timer >= 1000000000) {
			System.out.println("FPS:"+drawCount);
			drawCount = 0;
			timer = 0;
				}
			}
		}
	}
	
	public void update() {
		
		
		if(gameState == playState) {
			player.update();
		
		
		//NPC
		for (int i = 0; i < npc.length; i++) {
			if (npc[i] != null) {
				npc[i].update();
			}
		}
		//Monster
		for (int i = 0; i < monster.length; i++) {
			if (monster[i] != null) {
				if(monster[i].alive == true && monster[i].dying == false) {
					monster[i].update();
				}
				if(monster[i].alive == false) {
					monster[i] = null;
				}
				
			}
		}
	}	
		
		
		
		if(gameState == pauseState) {}
		
	}
	
	public void paintComponent(Graphics g) {
		
		Graphics2D g2 = (Graphics2D)g;
	
	//Debug
	long drawStart = 0;
	drawStart = System.nanoTime();
		
		if (gameState == titleState) {
			ui.draw(g2);
		}
		
		else {
			//Tile
			tileM.draw(g2);
			
			
			//ADD entity and object
			entityList.add(player);
			
			for(int i = 0; i < npc.length; i++) {
				if (npc[i] != null) {
					entityList.add(npc[i]);
				}
			}
			
			for(int i = 0; i < obj.length; i++) {
				if (obj[i] != null) {
					entityList.add(obj[i]);
				}
			}
			
			for(int i = 0; i < monster.length; i++) {
				if (monster[i] != null) {
					entityList.add(monster[i]);
				}
			}
			
			//Sort
			Collections.sort(entityList, new Comparator<Entity>(){

				@Override
				public int compare(Entity e1, Entity e2) {
					int result = Integer.compare(e1.worldy, e2.worldy);
					return result;
				}
				
			});
			
			//Draw ENtity and Object
			
			for(int i = 0; i < entityList.size(); i++) {
				entityList.get(i).draw(g2);
			}
			//Empty list
			entityList.clear();
			
			
			//UI
			ui.draw(g2);
		}
			//Debug
			if (keyH.showDebug == true) {
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;
			g2.setColor(Color.white);
			g2.drawString("Draw Time: " + passed, 10,400);
			g2.drawString("FPS: " , 10,500);
			//System.out.println("Draw Time:" + passed);
			}
			
			
			g2.dispose();
		}
		

	
	public void playMusic(int i) {
		music.setFile(i);
		music.play();                                               
		music.loop();
	}
	public void stopMusic() {
		music.stop();
	}
	public void playSE(int i) {
		se.setFile(i);
		se.play();
	}
}
