package GUI;

import Game.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HUD extends JPanel implements ActionListener {

    //Fields
    private Timer timer; //to continuously listen for actions
    private Font font1, font2;
    private Board board;

    //Constructor
    public HUD(Board _board) {
        board = _board;

        timer = new Timer(25, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    //Methods
    public void paint(Graphics g) {
        super.paint(g);

        for (int i = 0; i < 15; i++) {
            drawRectangle(g, (i+1)*32, 0);
        }

        for (int i = 0; i < 15; i++) {
            drawRectangle(g, (i+1)*32, 32);
        }

        board.getPlayer().getPlayerItems().drawInventory(   g, 0);
        board.getPlayer().getPlayerEngimons().drawInventory(   g, 32);
    }

    private void drawRectangle(Graphics g, int x, int y) {
        g.drawRect(x,y,32, 32);
//        g.setColor(Color.RED);
//        g.fillRect(x,y,32, 32);
    }


}
