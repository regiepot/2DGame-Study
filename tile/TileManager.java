package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
	
	GamePanel gp;
	public Tile[] tile;
	public int MapTileNum[][];
	
	public TileManager(GamePanel gp) {
		
		this.gp = gp;
		
		tile  = new Tile[99];
		MapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
		getTileImage();
		loadMap("/maps/world_map_00.txt");
	}
	
	public void getTileImage() {
		
			//Place Holder
			setup(0,"grass",false);
			setup(1,"brick",true);
			setup(2,"water",false);
			setup(3,"dirt",false);
			setup(4,"tree",true);
			setup(5,"sand",false);
			setup(6,"sand",false);
			setup(7,"sand",false);
			setup(8,"sand",false);
			setup(9,"sand",false);
			
			//Used
			//GRASS
			setup(10,"grass",false);
			setup(11,"grass1",false);
			setup(12,"grass2",false);
			setup(13,"grass3",false);
			setup(14,"grass4",false);
			setup(15,"grass5",false);
			setup(16,"grass6",false);
			setup(17,"grass7",false);
			setup(18,"grass8",false);
			setup(19,"grass9",false);
			setup(20,"grass10",false);
			setup(21,"grass11",false);
			setup(22,"grass12",false);
			setup(23,"grass13",false);
			setup(24,"grass14",false);
			
			//WATER
			setup(25,"water",true);
			setup(26,"water1",true);
			setup(27,"water2",true);
			setup(28,"water3",true);
			setup(29,"water4",true);
			setup(30,"water5",true);
			setup(31,"water6",true);
			setup(32,"water7",true);
			setup(33,"water8",true);
			setup(34,"water9",true);
			setup(35,"water10",true);
			setup(36,"water11",true);
			setup(37,"water12",true);
			setup(38,"water13",true);
			setup(39,"water14",true);
			setup(40,"water15",true);
			setup(41,"water16",true);
			setup(42,"water17",true);
			setup(43,"water18",true);
			setup(44,"water19",true);
			setup(45,"water20",true);
			setup(46,"water21",true);
			setup(47,"water22",true);
			setup(48,"water23",true);
			setup(49,"water24",true);
			setup(50,"water25",true);
			
			//MISC
			setup(51,"dirt",false);
			setup(52,"tree",true);
			setup(53,"brick",true);
			setup(54,"sand",false);


	}
	
	public void setup(int index, String imageName, boolean collision) {
		
		UtilityTool uTool = new UtilityTool();
		
		try {
			
		
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/"+ imageName +".png"));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tilesize,gp.tilesize);
			tile[index].collision = collision;
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
	}
	
	
	public void loadMap(String filePath) {
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
				String line = br.readLine();
				
				while (col < gp.maxWorldCol) {
					String numbers[] = line.split(" ");
					int num = Integer.parseInt(numbers[col]);
					
					MapTileNum[col][row] = num;
					col++;
				}
				if (col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			br.close();
		} catch (Exception e) {
			
		}
	}
	
	
	
	
	
	
	public void draw(Graphics2D g2) {
		int worldcol = 0;
		int worldrow = 0;
		

		
		while (worldcol < gp.maxWorldCol && worldrow < gp.maxWorldRow) {
			
			int tileNum = MapTileNum[worldcol][worldrow];
			
			int worldx = worldcol * gp.tilesize;
			int worldy = worldrow * gp.tilesize;
			
			int screenx = worldx - gp.player.worldx + gp.player.screenx;
			int screeny = worldy - gp.player.worldy + gp.player.screeny;
			
			//Stop moving camera at edge
			if(gp.player.screenx > gp.player.worldx) {
				screenx = worldx;
			}
			
			if(gp.player.screeny > gp.player.worldy) {
				screeny = worldy;
			}
			
			int rightOffset = gp.screenWidth - gp.player.screenx;
			if (rightOffset > gp.worldWidth - gp.player.worldx) {
				
				screenx = gp.screenWidth - (gp.worldWidth - worldx);
				
			}
			
			int bottomOffset = gp.screenHeight - gp.player.screeny;
			if(bottomOffset > gp.worldHeight - gp.player.worldy) {
				
				screeny = gp.screenHeight - (gp.worldHeight - worldy);
				
			}
			
			if (worldx + gp.tilesize > gp.player.worldx - gp.player.screenx &&
				worldx - gp.tilesize < gp.player.worldx + gp.player.screenx &&
				worldy + gp.tilesize > gp.player.worldy - gp.player.screeny &&
				worldy - gp.tilesize < gp.player.worldy + gp.player.screeny) {
				
				g2.drawImage(tile[tileNum].image, screenx, screeny, null);
			}
			
			else if (gp.player.screenx > gp.player.worldx ||
					gp.player.screeny > gp.player.worldy ||
					rightOffset > gp.worldWidth - gp.player.worldx ||
					bottomOffset > gp.worldHeight - gp.player.worldy) {
				g2.drawImage(tile[tileNum].image, screenx, screeny, null);
			}
			worldcol++;
			
			if (worldcol == gp.maxWorldCol) {
				worldcol = 0;
				worldrow++;
			}
		}
		//g2.drawImage(tile[0].image, 0, 0, gp.tilesize, gp.tilesize, null);
		
	}
}
