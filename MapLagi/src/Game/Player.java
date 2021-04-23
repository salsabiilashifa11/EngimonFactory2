package Game;

import javax.swing.*;
import java.awt.*;

public class Player {

    //Fields
    private int x, y, tileX, tileY;
    private Image playerActive;
    private Image playerFront, playerLeft, playerRight, playerBack;

    //Constructor
    public Player() {

        //Initiating all player sprites
        ImageIcon img = new ImageIcon("/Users/shifa/Desktop/MapLagi/assets/PlayerFront.png");
        playerFront = img.getImage();
        img = new ImageIcon("/Users/shifa/Desktop/MapLagi/assets/PlayerLeft.png");
        playerLeft = img.getImage();
        img = new ImageIcon("/Users/shifa/Desktop/MapLagi/assets/PlayerRight.png");
        playerRight = img.getImage();
        img = new ImageIcon("/Users/shifa/Desktop/MapLagi/assets/PlayerBack.png");
        playerBack = img.getImage();
        playerActive = playerFront;

        //Initiate player position to tile (1,1)
        x = 32;
        y = 32;

        tileX = 1;
        tileY = 1;
    }

    //Aksesor
    public Image getPlayerActive() { return playerActive; }
    public Image getPlayerFront() { return playerFront; }
    public Image getPlayerBack() { return playerBack; }
    public Image getPlayerLeft() { return playerLeft; }
    public Image getPlayerRight() { return playerRight; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getTileX() { return tileX; }
    public int getTileY() { return tileY; }

    public void setPlayerActive(Image playerActive) { this.playerActive = playerActive; }

    //Methods
    public void move(int dx, int dy, int tx, int ty) {
        x += dx;
        y += dy;

        tileX += tx;
        tileY += ty;
    }
}
