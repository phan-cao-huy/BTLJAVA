package entity;
import main.KeyHandle;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
  public int worldX,worldY;
  public int speed;

  public BufferedImage up1,up2,down1,down2,left1,left2,right1,right2,down0,left0,right0,up0;
  public String direction,lastDirection;
  public int spriteCounter = 0 ;
  public int spriteNumber  = 1;

  public Rectangle solidArea;
  public boolean collisionOn = false;

  public int solidAreaDefaultX;
  public int solidAreaDefaultY;
}
