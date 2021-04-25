package GUI;

import Game.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HUD extends JPanel implements ActionListener {
    //Atribut
    private Timer timer; //to continuously listen for actions
    private Font font1, font2;
    private Board board;
    private transient Image heart;
    private ImageIcon img;

    //Konstruktor
    public HUD(Board _board) {
        board = _board;
        this.setBackground(Color.decode("#f2f2f2"));

        timer = new Timer(25, this);
        timer.start();

        img = new ImageIcon("assets/Heart.png");
        heart = img.getImage();
    }

    //Fungsi Tambahan
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public void paint(Graphics g) {
        super.paint(g);
        for (int i = 0; i < 15; i++) {
            drawRectangle(g, (i)*32+15, 0);
        }
        for (int i = 0; i < 15; i++) {
            drawRectangle(g, (i)*32+15, 32);
        }
        if (board.getPlayer().getActiveEngimon() != null) {
            for (int i = 0; i < board.getPlayer().getActiveEngimon().getLife(); i++) {
                g.drawImage(heart, (i+16)*32+15, 0, null);
            }
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
