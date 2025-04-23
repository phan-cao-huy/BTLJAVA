package object;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class OBJ_Treasure extends SuperObject {
    public OBJ_Treasure(){
        name = "Treasure";
        try{
            image = ImageIO.read(getClass().getResource("/objects/chest.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        collision = true;
    }
}
