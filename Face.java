import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Face {
    
    private BufferedImage faceImage;
    private int xCoord;
    private int yCoord;

    public Face(int x, int y) {
        try {
            faceImage = ImageIO.read(new File("Assets\\Face.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        
        this.xCoord = x;
        this.yCoord = y;
    }

    
    public int getXCoord() {
        return xCoord;
    }

    public int getYCoord() {
        return yCoord;
    }

    public void setXCoord(int x) {
        this.xCoord = x;
    }

    public void setYCoord(int y) {
        this.yCoord = y;
    }

}
