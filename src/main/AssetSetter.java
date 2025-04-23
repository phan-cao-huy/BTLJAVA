package main;

import object.OBJ_Door;
import object.OBJ_Key;
import object.OBJ_Treasure;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        // Place the 5 keys
        gp.obj[0] = new OBJ_Key();
        gp.obj[0].worldX = 10 * gp.tileSize;
        gp.obj[0].worldY = 11 * gp.tileSize;

        gp.obj[1] = new OBJ_Key();
        gp.obj[1].worldX = 20 * gp.tileSize;
        gp.obj[1].worldY = 19 * gp.tileSize;

        gp.obj[2] = new OBJ_Key();
        gp.obj[2].worldX = 29 * gp.tileSize;
        gp.obj[2].worldY = 31 * gp.tileSize;

        gp.obj[3] = new OBJ_Key();
        gp.obj[3].worldX = 41 * gp.tileSize;
        gp.obj[3].worldY = 50 * gp.tileSize;

        gp.obj[4] = new OBJ_Key();
        gp.obj[4].worldX = 55 * gp.tileSize;
        gp.obj[4].worldY = 63 * gp.tileSize;

        // Place the 5 doors
        gp.obj[5] = new OBJ_Door();
        gp.obj[5].worldX = 15 * gp.tileSize;
        gp.obj[5].worldY = 13* gp.tileSize;

        gp.obj[6] = new OBJ_Door();
        gp.obj[6].worldX = 25 * gp.tileSize;
        gp.obj[6].worldY = 21 * gp.tileSize;

        gp.obj[7] = new OBJ_Door();
        gp.obj[7].worldX = 33 * gp.tileSize;
        gp.obj[7].worldY = 31 * gp.tileSize;

        gp.obj[8] = new OBJ_Door();
        gp.obj[8].worldX =  44 * gp.tileSize;
        gp.obj[8].worldY = 53 * gp.tileSize;

        gp.obj[9] = new OBJ_Door();
        gp.obj[9].worldX = 50 * gp.tileSize;
        gp.obj[9].worldY = 64 * gp.tileSize;

        //Place the treasure
        gp.obj[10] = new OBJ_Treasure();

        gp.obj[10].worldX = 50 * gp.tileSize;
        gp.obj[10].worldY = 67* gp.tileSize;
    }
}