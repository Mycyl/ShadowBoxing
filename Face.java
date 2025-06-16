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

    public void setCoords(int[] coords) {
        this.xCoord = coords[0];
        this.yCoord = coords[1];
    }

    public static String generateMoveEnemy () {
        ArrayList<String> moves = new ArrayList<>();
        moves.add("Left");
        moves.add("Right");
        moves.add("Up");
        moves.add("Down");

        int randomIndex = (int) (Math.random() * moves.size());
        return moves.get(randomIndex);
    }

}
