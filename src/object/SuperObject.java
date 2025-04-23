package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class SuperObject {
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48); // Default solid area
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;

    public void draw(Graphics2D g2, int cameraX, int cameraY, int tileSize) {
        int screenX = worldX - cameraX;
        int screenY = worldY - cameraY;

        // Only draw if the object is visible on screen
        if (worldX + tileSize > cameraX &&
                worldX - tileSize < cameraX + tileSize * 16 &&
                worldY + tileSize > cameraY &&
                worldY - tileSize < cameraY + tileSize * 12) {

            g2.drawImage(image, screenX, screenY, tileSize, tileSize, null);
        }
    }
}