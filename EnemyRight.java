import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
public class EnemyRight {
    
    private String anchor = "TopLeft";
    private String leftPath = "Assets\\Hands\\Enemy\\Right\\RightLeft.png"; // Direction to the left
    private String restPath = "Assets\\Hands\\Enemy\\Right\\RightRest.png"; // Rest position
    private String upPath = "Assets\\Hands\\Enemy\\Right\\RightUp.png"; // Direction to the top

    private BufferedImage restImage;
    private BufferedImage upImage;
    private BufferedImage leftImage;

    private int xCoord;
    private int yCoord;

    public EnemyRight(int x, int y) {
        try {
            restImage = ImageIO.read(new File(restPath));
            upImage = ImageIO.read(new File(upPath));
            leftImage = ImageIO.read(new File(leftPath));
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
