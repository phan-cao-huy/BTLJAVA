package entity;

import main.GamePanel;
import main.KeyHandle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class player extends Entity {
    GamePanel gp;
    KeyHandle keyH;
    public int screenX;
    public int screenY;

    // Inventory
    public int hasKey = 0;
    // Announce
    public String PickupAnnounce=" ";
    public int AnnounceCounter = 0;

    public player(GamePanel gp, KeyHandle keyH) {
        this.gp = gp;
        this.keyH = keyH;
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        // Set collision area smaller than the tile size for more precise collision
        solidArea = new Rectangle(8, 16, 32, 32);
        solidAreaDefaultX = 8;
        solidAreaDefaultY = 16;

        setDefaultValue();
        getPlayerImage();
    }

    public void setDefaultValue() {
        worldX = gp.tileSize * 5;//1056 // toa do nv dung o  cot thu 22
        worldY = gp.tileSize * 5;//1152  toa do nv dung o hang 24
        speed = 4;
        direction = "down";
        lastDirection = "down0";
        spriteNumber = 1;
    }

    public void getPlayerImage() {
        try {
            down0 = ImageIO.read(getClass().getResourceAsStream("/boy/down0.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/boy/down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/boy/down3.png"));
            up0 = ImageIO.read(getClass().getResourceAsStream("/boy/up0.png"));
            up1 = ImageIO.read(getClass().getResourceAsStream("/boy/up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/boy/up3.png"));
            left0 = ImageIO.read(getClass().getResourceAsStream("/boy/left1.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/boy/left2.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/boy/left3.png"));
            right0 = ImageIO.read(getClass().getResourceAsStream("/boy/right2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/boy/right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/boy/right2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        boolean isMoving = false;

        if (keyH.upPressed || keyH.rightPressed || keyH.leftPressed || keyH.downPressed) {
            // Reset collision status
            collisionOn = false;

            // Set direction based on key press
            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed) {
                direction = "right";
            }

            // Check tile collision
            gp.Checker.checkTile(this);

            // Check object collision
            int objIndex = gp.Checker.checkObject(this, true);
            pickUpObject(objIndex);

            // If no collision, the player can move
            if(!collisionOn) {
                switch(direction) {
                    case "up":
                        if (worldY - speed >= 0) worldY -= speed;
                        break;
                    case "down":
                        if (worldY + speed < gp.worldHeight - gp.tileSize) worldY += speed;
                        break;
                    case "left":
                        if (worldX - speed >= 0) worldX -= speed;
                        break;
                    case "right":
                        if (worldX + speed < gp.worldWidth - gp.tileSize) worldX += speed;
                        break;
                }
            }

            lastDirection = direction;
            isMoving = true;

            spriteCounter++;
            if (spriteCounter > 10) {
                spriteNumber = (spriteNumber == 1) ? 2 : 1;
                spriteCounter = 0;
            }
            if(AnnounceCounter > 0){
                AnnounceCounter --;
                if(AnnounceCounter == 0){
                    PickupAnnounce="";
                }
            }
        }

        if (!isMoving) {
            spriteNumber = 0;
        }
        if(gp.gameState == gp.winState)return;
    }

    public void pickUpObject(int i) {
        if(i != 999) {
            String objectName = gp.obj[i].name;
            switch(objectName) {
                case "Key":
                    hasKey++;
                    gp.obj[i] = null;
                    PickupAnnounce = "You picked up a key! Keys: "+hasKey;
                    AnnounceCounter = 120;
                    System.out.println("Key: " + hasKey);
                    break;
                case "Door":
                    if(hasKey > 0) {
                        gp.obj[i] = null;
                        hasKey--;
                        AnnounceCounter = 120;
                        PickupAnnounce = "Door opened! Keys left: " +hasKey;
                    } else {
                        AnnounceCounter = 120;
                        PickupAnnounce ="You need a key to open this door";
                    }
                    break;
                case"Treasure":
                    gp.obj[i] = null;
                    gp.gameState = gp.winState;
                    break;
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch (direction) {
            case "up":
                if (spriteNumber == 1) {
                    image = up1;
                } else if (spriteNumber == 2) {
                    image = up2;
                }
                break;
            case "left":
                if (spriteNumber == 1) {
                    image = left1;
                } else if (spriteNumber == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (spriteNumber == 1) {
                    image = right1;
                } else if (spriteNumber == 2) {
                    image = right2;
                }
                break;
            case "down":
                if (spriteNumber == 1) {
                    image = down1;
                } else if (spriteNumber == 2) {
                    image = down2;
                }
                break;
        }

        if(image == null){
            image = down0;
        }
        if (spriteNumber == 0) {
            switch (lastDirection) {
                case "up":
                    image = up0;
                    break;
                case "down":
                    image = down0;
                    break;
                case "left":
                    image = left0;
                    break;
                case "right":
                    image = right0;
                    break;
            }
        }

        // Calculate player position on screen based on camera position
        int screenX = worldX - gp.cameraX;
        int screenY = worldY - gp.cameraY;

        // Camera edge handling
        if (worldX < gp.screenWidth / 2) {
            screenX = worldX;
        }
        if (worldY < gp.screenHeight / 2) {
            screenY = worldY;
        }
        if (worldX > gp.worldWidth - gp.screenWidth / 2) {
            screenX = gp.screenWidth - (gp.worldWidth - worldX);
        }
        if (worldY > gp.worldHeight - gp.screenHeight / 2) {
            screenY = gp.screenHeight - (gp.worldHeight - worldY);
        }

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

        int centerX = worldX + solidArea.x + solidArea.width / 2;
        int centerY = worldY + solidArea.y + solidArea.height / 2;

        int col = centerX / gp.tileSize;
        int row = centerY / gp.tileSize;

        g2.setFont(new Font("Arial", Font.BOLD, 28));
        g2.setColor(Color.WHITE);
        g2.drawString("Tile: (" + row + ", " + col + ")", 20, 80);

        // Draw key count on screen
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        g2.setColor(Color.WHITE);
        g2.drawString("Keys: " + hasKey, 20, 50);

        // Draw announce on screen
        if(!PickupAnnounce.equals("")){
            g2.setFont(new Font("Arial",Font.BOLD,24));
            g2.setColor(Color.WHITE);
            int WidthString = g2.getFontMetrics().stringWidth(PickupAnnounce);
            int width = (gp.screenWidth - WidthString)/2;
            int height = gp.screenHeight/2;
            g2.drawString(PickupAnnounce,width,height);
        }

    }
}