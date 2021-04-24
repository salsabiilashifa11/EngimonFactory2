package GUI;

import Game.Cell;
import Game.CellType;
import Game.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.Serializable;

public class Board extends JPanel implements ActionListener, Serializable {

    //Fields
    private Timer timer; //to continuously listen for actions
    private Map map; //map
    private Player player;//player

    //Constructor
    public Board() {

        map = new Map();
        player = new Player("Shifa", map);

        addKeyListener(new KListener());
        setFocusable(true);

        timer = new Timer(25, this);
        timer.start();


    }

    //Aksesor
    public Map getMap() { return map; }
    public Player getPlayer() { return player; }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public void paint(Graphics g) {
        super.paint(g);

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                map.getCell(i, j).drawCell(g);
                if (map.getCell(i, j).getEngimon() != null) {
                    map.getCell(i, j).getEngimon().drawEngimon(g);
                } else {
                    //System.out.println("ga masuk");
                }
            }
        }

        g.drawImage(player.getPlayerActive(), player.getX(), player.getY(), null);
        player.getActiveEngimon().drawEngimon(g);

    }

    public class KListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();

            if (keyCode == KeyEvent.VK_UP) {
                player.move(0, -32, 'w');
            } else if (keyCode == KeyEvent.VK_DOWN) {
                player.move(0, 32, 's');
            } else if (keyCode == KeyEvent.VK_LEFT) {
                player.move(-32, 0, 'a');
            } else if (keyCode == KeyEvent.VK_RIGHT) {
                player.move(32, 0, 'd');
            }
        }

        public void keyReleased(KeyEvent e) {

        }

        public void keyTyped(KeyEvent e) {

        }
    }
}
