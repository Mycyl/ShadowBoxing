import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
public class EnemyLeft {
    
    private String anchor = "TopRight";
    private String downPath = "Assets\\Hands\\Enemy\\Left\\LeftDown.png"; // Direction to the bottom
    private String restPath = "Assets\\Hands\\Enemy\\Left\\LeftRest.png"; // Rest position
    private String rightPath = "Assets\\Hands\\Enemy\\Left\\LeftRight.png"; // Direction to the right

    private BufferedImage restImage;
    private BufferedImage downImage;
    private BufferedImage rightImage;

    private int xCoord;
    private int yCoord;

    public EnemyLeft(int x, int y) {
        try {
            restImage = ImageIO.read(new File(restPath));
            downImage = ImageIO.read(new File(downPath));
            rightImage = ImageIO.read(new File(rightPath));
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

    public BufferedImage getRestImage() {
        return restImage;
    }

    public void setImage (BufferedImage newImage) {
        restImage = newImage;
    }
}
