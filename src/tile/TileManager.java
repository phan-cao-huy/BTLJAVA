package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;


    public TileManager(GamePanel gp){
        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int[gp.maxWorldRow][gp.maxWorldCol];
        getTileImage();
        loadMap("/map/testMap.txt");
    }
    public void getTileImage(){
        try{
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/title/earth.png"));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/title/water.png"));
            tile[2].collision = true;

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/title/tree.png"));
            tile[1].collision = true;

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/title/sand.png"));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/title/wall.png"));
            tile[4].collision = true;

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/title/sand.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void loadMap(String FileName){
        try{
            InputStream is = getClass().getResourceAsStream(FileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            for(int row = 0 ; row < gp.maxWorldRow; row++){
                String line  = br.readLine();
                for(int col = 0; col < gp.maxWorldCol; col ++){
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[row][col] = num;
                }
            }

        } catch (Exception e) {
          e.printStackTrace();
        }
    }


    public void draw(Graphics2D g2) {
        // Calculate camera bounds
        int playerWorldX = gp.player.worldX;
        int playerWorldY = gp.player.worldY;

        // Calculate where camera should be (centered on player)
        int cameraX = playerWorldX - gp.screenWidth / 2;
        int cameraY = playerWorldY - gp.screenHeight / 2;

        // Restrict camera from going outside the world bounds
        if (cameraX < 0) cameraX = 0;
        if (cameraY < 0) cameraY = 0;
        if (cameraX > gp.worldWidth - gp.screenWidth)
            cameraX = gp.worldWidth - gp.screenWidth;
        if (cameraY > gp.worldHeight - gp.screenHeight)
            cameraY = gp.worldHeight - gp.screenHeight;

        // Save camera position for reference by other components
        gp.cameraX = cameraX;
        gp.cameraY = cameraY;

        // Draw only the tiles that are visible on screen
        int startCol = cameraX / gp.tileSize;
        int endCol = (cameraX + gp.screenWidth) / gp.tileSize + 1;
        int startRow = cameraY / gp.tileSize;
        int endRow = (cameraY + gp.screenHeight) / gp.tileSize + 1;

        // Safety bounds check
        if (startCol < 0) startCol = 0;
        if (startRow < 0) startRow = 0;
        if (endCol > gp.maxWorldCol) endCol = gp.maxWorldCol;
        if (endRow > gp.maxWorldRow) endRow = gp.maxWorldRow;

        // Draw the visible tiles
        for (int row = startRow; row < endRow; row++) {
            for (int col = startCol; col < endCol; col++) {
                int tileNum = mapTileNum[row][col];
                int worldX = col * gp.tileSize;
                int worldY = row * gp.tileSize;
                int screenX = worldX - cameraX;
                int screenY = worldY - cameraY;

                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }
        }
    }
}

