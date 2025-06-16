import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
public class PlayerLeft {
    
    private String anchor = "BottomLeft";
    private String downPath = "Assets\\Hands\\Player\\Left\\LeftDown.png"; // Direction to the bottom
    private String restPath = "Assets\\Hands\\Player\\Left\\LeftRest.png"; // Rest position
    private String rightPath = "Assets\\Hands\\Player\\Left\\LeftRight.png"; // Direction to the right

    private BufferedImage restImage;
    private BufferedImage downImage;
    private BufferedImage rightImage;

    private int xCoord;
    private int yCoord;

    public PlayerLeft(int x, int y) {
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