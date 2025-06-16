import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class DisplayPanel extends JPanel implements ActionListener, KeyListener {

    private BufferedImage background;
    private BufferedImage crosshair;
    private BufferedImage faceImage;

    private PlayerRight playerRight;
    private PlayerLeft playerLeft;
    private EnemyLeft enemyLeft;
    private EnemyRight enemyRight;
    private Face face;

    private BufferedImage playerRightRestImage;
    private BufferedImage playerLeftRestImage;
    private BufferedImage playerRightLeftImage;
    private BufferedImage playerLeftRightImage;
    private BufferedImage playerRightUpImage;
    private BufferedImage playerLeftDownImage;

    private BufferedImage enemyLeftRestImage;
    private BufferedImage enemyRightRestImage;
    private BufferedImage enemyLeftDownImage;
    private BufferedImage enemyRightUpImage;
    private BufferedImage enemyLeftRightImage;
    private BufferedImage enemyRightLeftImage;

    private boolean inCooldown = false;
    private Timer cooldownTimer;

    public DisplayPanel() {
        try {
            background = ImageIO.read(new File("Background.png"));
            crosshair = ImageIO.read(new File("Assets\\Crosshair.png"));
            faceImage = ImageIO.read(new File("Assets\\Face.png"));
            playerRightRestImage = ImageIO.read(new File("Assets\\Hands\\Player\\Right\\RightRest.png"));
            playerLeftRestImage = ImageIO.read(new File("Assets\\Hands\\Player\\Left\\LeftRest.png"));
            playerRightLeftImage = ImageIO.read(new File("Assets\\Hands\\Player\\Right\\RightLeft.png"));
            playerLeftRightImage = ImageIO.read(new File("Assets\\Hands\\Player\\Left\\LeftRight.png"));
            playerRightUpImage = ImageIO.read(new File("Assets\\Hands\\Player\\Right\\RightUp.png"));
            playerLeftDownImage = ImageIO.read(new File("Assets\\Hands\\Player\\Left\\LeftDown.png"));

            enemyLeftRestImage = ImageIO.read(new File("Assets\\Hands\\Enemy\\Left\\LeftRest.png"));
            enemyRightRestImage = ImageIO.read(new File("Assets\\Hands\\Enemy\\Right\\RightRest.png"));
            enemyLeftDownImage = ImageIO.read(new File("Assets\\Hands\\Enemy\\Left\\LeftDown.png"));
            enemyRightUpImage = ImageIO.read(new File("Assets\\Hands\\Enemy\\Right\\RightUp.png"));
            enemyLeftRightImage = ImageIO.read(new File("Assets\\Hands\\Enemy\\Left\\LeftRight.png"));
            enemyRightLeftImage = ImageIO.read(new File("Assets\\Hands\\Enemy\\Right\\RightLeft.png"));

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);

        playerRight = new PlayerRight(1080, 418);
        playerLeft = new PlayerLeft(0, 533);
        playerRight.setImage(playerRightRestImage);
        playerLeft.setImage(playerLeftRestImage);

        enemyLeft = new EnemyLeft(1125, 0);
        enemyRight = new EnemyRight(525, 0);
        enemyRight.setImage(enemyRightRestImage);
        enemyLeft.setImage(enemyLeftRestImage);

        face = new Face(960 - faceImage.getWidth() / 2, 540 - faceImage.getHeight());

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
        g.drawImage(crosshair, 960 - crosshair.getWidth() / 2, 540 - crosshair.getHeight() / 2, null);
        g.drawImage(faceImage, face.getXCoord(), face.getYCoord(), null);
        g.drawImage(enemyLeft.getRestImage(), enemyLeft.getXCoord(), enemyLeft.getYCoord(), null);
        g.drawImage(enemyRight.getRestImage(), enemyRight.getXCoord(), enemyRight.getYCoord(), null);
        g.drawImage(playerRight.getRestImage(), playerRight.getXCoord(), playerRight.getYCoord(), null);
        g.drawImage(playerLeft.getRestImage(), playerLeft.getXCoord(), playerLeft.getYCoord(), null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (inCooldown) return;

        int key = e.getKeyCode();

        if (key == 65) { // A
            playerRight.setXCoord(1920 - playerRightLeftImage.getWidth());
            playerRight.setYCoord(1080 - playerRightLeftImage.getHeight());
            playerRight.setImage(playerRightLeftImage);
            startCooldown(key);
        }

        if (key == 68) { // D
            playerLeft.setYCoord(1080 - playerLeftRightImage.getHeight());
            playerLeft.setImage(playerLeftRightImage);
            startCooldown(key);
        }

        if (key == 87) { // W
            playerRight.setXCoord(1920 - playerRightUpImage.getWidth());
            playerRight.setYCoord(1080 - playerRightUpImage.getHeight());
            playerRight.setImage(playerRightUpImage);
            startCooldown(key);
        }

        if (key == 83) { // S
            playerLeft.setYCoord(1080 - playerLeftDownImage.getHeight());
            playerLeft.setImage(playerLeftDownImage);
            startCooldown(key);
        }

        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // No action needed, reset happens after cooldown
    }

    private void startCooldown(int keyCode) {
        inCooldown = true;

        cooldownTimer = new Timer(750, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (keyCode == 65 || keyCode == 87) {
                    playerRight.setXCoord(1920 - playerRightRestImage.getWidth());
                    playerRight.setYCoord(1080 - playerRightRestImage.getHeight());
                    playerRight.setImage(playerRightRestImage);
                }

                if (keyCode == 68 || keyCode == 83) {
                    playerLeft.setYCoord(1080 - playerLeftRestImage.getHeight());
                    playerLeft.setImage(playerLeftRestImage);
                }

                inCooldown = false;
                cooldownTimer.stop();
                repaint();
            }
        });

        cooldownTimer.setRepeats(false);
        cooldownTimer.start();
    }
}
