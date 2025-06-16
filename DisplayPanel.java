import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

enum TurnState {
    PLAYER_CONTROLS_HANDS,
    PLAYER_CONTROLS_FACE
}

public class DisplayPanel extends JPanel implements ActionListener, KeyListener {

    private Map<String, int[]> directionMapToCoordinates = new HashMap<String, int[]>();

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
    private int playerStreak;
    private int enemyStreak;

    private boolean playerMove = true;

    

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

        face = new Face(960 - faceImage.getWidth() / 2, 540 - faceImage.getHeight() / 2);
        playerStreak = 0;
        enemyStreak = 0;

        directionMapToCoordinates.put("Left", new int[]{750 - faceImage.getWidth() / 2, 540 - faceImage.getHeight() / 2});
        directionMapToCoordinates.put("Right", new int[]{1170 - faceImage.getWidth() / 2, 540 - faceImage.getHeight() / 2});
        directionMapToCoordinates.put("Up", new int[]{960 - faceImage.getWidth() / 2, 360 - faceImage.getHeight() / 2});
        directionMapToCoordinates.put("Down", new int[]{960 - faceImage.getWidth() / 2, 720 - faceImage.getHeight() / 2});
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
        if (playerStreak != 3 && enemyStreak != 3) {
            g.drawImage(crosshair, 960 - crosshair.getWidth() / 2, 540 - crosshair.getHeight() / 2, null);
            g.drawImage(faceImage, face.getXCoord(), face.getYCoord(), null);
            g.drawImage(enemyLeft.getRestImage(), enemyLeft.getXCoord(), enemyLeft.getYCoord(), null);
            g.drawImage(enemyRight.getRestImage(), enemyRight.getXCoord(), enemyRight.getYCoord(), null);
            g.drawImage(playerRight.getRestImage(), playerRight.getXCoord(), playerRight.getYCoord(), null);
            g.drawImage(playerLeft.getRestImage(), playerLeft.getXCoord(), playerLeft.getYCoord(), null);
        }
        if (playerStreak == 3) {
            g.setColor(Color.GREEN);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("You Win!", 960 - 100, 540);
        } else if (enemyStreak == 3) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("You Lose!", 960 - 100, 540);
        }
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
            if (playerMove) {
                String enemyMove = Face.generateMoveEnemy();
                playerRight.setXCoord(1920 - playerRightLeftImage.getWidth());
                playerRight.setYCoord(1080 - playerRightLeftImage.getHeight());
                playerRight.setImage(playerRightLeftImage);
                face.setCoords(directionMapToCoordinates.get(enemyMove));
                startCooldown(key);
                if (enemyMove.equals("Left")) {
                    playerStreak++;
                    playSound();
                    enemyStreak = 0;
                    playerMove = true;
                    System.out.println("Won");
                } else {
                    playerStreak = 0;
                    playerMove = false;
                    System.out.println("Lost");
                }
            } else { // Moving Face
                face.setCoords(directionMapToCoordinates.get("Left"));
                String enemyMove = Face.generateMoveEnemy();
                processEnemyMove(enemyMove);
                startCooldown(key);
                if (enemyMove.equals("Left")) {
                    enemyStreak++;
                    playerStreak = 0;
                    playerMove = false;
                    System.out.println("Lost");
                } else {
                    enemyStreak = 0;
                    playerMove = true;
                    System.out.println("Won");
                }

                
            }
        }

        if (key == 68) { // D
            if (playerMove) {
                String enemyMove = Face.generateMoveEnemy();
                playerLeft.setYCoord(1080 - playerLeftRightImage.getHeight());
                playerLeft.setImage(playerLeftRightImage);
                face.setCoords(directionMapToCoordinates.get(enemyMove));
                startCooldown(key);
                if (enemyMove.equals("Right")) {
                    playerStreak++;
                    playSound();
                    enemyStreak = 0;
                    playerMove = true;
                    System.out.println("Won");
                } else {
                    playerStreak = 0;
                    playerMove = false;
                    System.out.println("Lost");
                }
            } else { // Moving Face
                face.setCoords(directionMapToCoordinates.get("Right"));
                String enemyMove = Face.generateMoveEnemy();
                processEnemyMove(enemyMove);
                startCooldown(key);
                if (enemyMove.equals("Right")) {
                    enemyStreak++;
                    playerStreak = 0;
                    playerMove = false;
                    System.out.println("Lost");
                } else {
                    enemyStreak = 0;
                    playerMove = true;
                    System.out.println("Won");
                }
            }

        }

        if (key == 87) { // W
            if (playerMove) {
                String enemyMove = Face.generateMoveEnemy();
                playerRight.setXCoord(1920 - playerRightUpImage.getWidth());
                playerRight.setYCoord(1080 - playerRightUpImage.getHeight());
                playerRight.setImage(playerRightUpImage);
                face.setCoords(directionMapToCoordinates.get(enemyMove));
                startCooldown(key);
                if (enemyMove.equals("Up")) {
                    playerStreak++;
                    playSound();
                    enemyStreak = 0;
                    playerMove = true;
                    System.out.println("Won");
                } else {
                    playerStreak = 0;
                    playerMove = false;
                    System.out.println("Lost");
                }
            } else { // Moving Face
                face.setCoords(directionMapToCoordinates.get("Up"));
                String enemyMove = Face.generateMoveEnemy();
                processEnemyMove(enemyMove);
                startCooldown(key);
                if (enemyMove.equals("Up")) {
                    enemyStreak++;
                    playerStreak = 0;
                    playerMove = false;
                    System.out.println("Lost");
                } else {
                    enemyStreak = 0;
                    playerMove = true;
                    System.out.println("Won");
                }
            }

        }

        if (key == 83) { // S
            if (playerMove) {
                String enemyMove = Face.generateMoveEnemy();
                playerLeft.setYCoord(1080 - playerLeftDownImage.getHeight());
                playerLeft.setImage(playerLeftDownImage);
                face.setCoords(directionMapToCoordinates.get(enemyMove));
                startCooldown(key);
                if (enemyMove.equals("Down")) {
                    playerStreak++;
                    playSound();
                    enemyStreak = 0;
                    playerMove = true;
                    System.out.println("Won");
                } else {
                    playerStreak = 0;
                    playerMove = false;
                    System.out.println("Lost");
                }
            } else { // Moving Face
                face.setCoords(directionMapToCoordinates.get("Down"));
                String enemyMove = Face.generateMoveEnemy();
                processEnemyMove(enemyMove);
                startCooldown(key);
                if (enemyMove.equals("Down")) {
                    enemyStreak++;
                    playerStreak = 0;
                    playerMove = false;
                    System.out.println("Lost");
                } else {
                    enemyStreak = 0;
                    playerMove = true;
                    System.out.println("Won");
                }
            }

        }

        repaint();
    }

    public void playSound() {
        String soundFilePath;
        if (playerStreak == 1) {
            soundFilePath = "Assets\\SoundFX\\1.wav";
        } else if (playerStreak == 2) {
            soundFilePath = "Assets\\SoundFX\\2.wav";
        } else {
            soundFilePath = "Assets\\SoundFX\\3.wav";
        }
        try {

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(soundFilePath));
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void processEnemyMove (String enemyMove) {
        if (enemyMove.equals("Right")) {
            enemyRight.setImage(enemyRightLeftImage);

        } else if (enemyMove.equals("Left")) {
            enemyLeft.setImage(enemyLeftRightImage);

        } else if (enemyMove.equals("Up")) {
            enemyRight.setImage(enemyRightUpImage);

        } else if (enemyMove.equals("Down")) {
            enemyLeft.setImage(enemyLeftDownImage);
        }
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

                enemyRight.setImage(enemyRightRestImage);
                enemyLeft.setImage(enemyLeftRestImage);
                face.setXCoord(960 - faceImage.getWidth() / 2);
                face.setYCoord(540 - faceImage.getHeight() / 2);

                inCooldown = false;
                cooldownTimer.stop();
                repaint();
            }
        });

        cooldownTimer.setRepeats(false);
        cooldownTimer.start();
    }
}
