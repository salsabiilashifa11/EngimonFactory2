package GUI;

import Game.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {

    //Fields
    private Timer timer; //to continuously listen for actions
    private Map map; //map
    private Player player; //player

    //Constructor
    public Board() {

        map = new Map();
        player = new Player();

        addKeyListener(new KListener());
        setFocusable(true);

        timer = new Timer(25, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public void paint(Graphics g) {
        super.paint(g);

        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 14; j++) {
                if (map.getMap(i,j).equals("g")) {
                    g.drawImage(map.getGrass(), i * 32, j * 32, null);
                } else if (map.getMap(i,j).equals("s")) {
                    g.drawImage(map.getSea(), i * 32, j * 32, null);
                } else if (map.getMap(i,j).equals("f")) {
                    g.drawImage(map.getFlower(), i * 32, j * 32, null);
                }
            }
        }

        g.drawImage(player.getPlayerActive(), player.getX(), player.getY(), null);
    }

    public class KListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();

            if (keyCode == KeyEvent.VK_UP) {
                player.move(0, -32, 0, -1);
                player.setPlayerActive(player.getPlayerBack());
            } else if (keyCode == KeyEvent.VK_DOWN) {
                player.move(0, 32, 0, 1);
                player.setPlayerActive(player.getPlayerFront());
            } else if (keyCode == KeyEvent.VK_LEFT) {
                player.move(-32, 0, -1, 0);
                player.setPlayerActive(player.getPlayerLeft());
            } else if (keyCode == KeyEvent.VK_RIGHT) {
                player.move(32, 0, 1, 0);
                player.setPlayerActive(player.getPlayerRight());
            }
        }

        public void keyReleased(KeyEvent e) {

        }

        public void keyTyped(KeyEvent e) {

        }
    }
}
