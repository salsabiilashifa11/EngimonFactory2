package GUI;

import Game.Cell;
import Game.CellType;
import Game.Player;
import Game.WildEngimon;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.io.Serializable;

public class Board extends JPanel implements ActionListener, Serializable {

    //Fields
    private transient Timer timer; //to continuously repaint
    private transient KListener listener;
    private Map map; //map
    private Player player;//player
    private ArrayList<WildEngimon> wilds;

    //Constructor
    public Board() {

        map = new Map();
        player = new Player("Shifa", map);

        listener = new KListener();
        addKeyListener(listener);
        setFocusable(true);

        timer = new Timer(25, this);
        timer.start();

        engimonInit(15);
    }

    //Methods
    public void engimonInit(int N) {
        wilds = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            wilds.add(engimonSpawn());
            WildEngimon spawned = wilds.get(i);
//            System.out.println(spawned == null);
            map.getCell(spawned.getPosition().getX(), spawned.getPosition().getY())
                    .setEngimon(spawned);
//            System.out.println(spawned.getName());
//            System.out.println(map.getCell(spawned.getPosition().getX(), spawned.getPosition().getY())
//                    .getEngimon().getName());
//            System.out.println(spawned.getPosition().getY());
        }
    }

    public WildEngimon engimonSpawn() {
        //Generate 1 random engimon
        Random rand = new Random();
        int upperboundCoordiante = 17;
        int upperboundLevel = 30;

        //Mencari cell kosong
        int coorX = rand.nextInt(upperboundCoordiante) + 1;
        int coorY = rand.nextInt(upperboundCoordiante) + 1;
        int level = rand.nextInt(upperboundLevel) + 1;

        while (map.getCell(coorX, coorY).getEngimon() != null
                || map.getCell(coorX, coorY).getPlayer() != null) {
            coorX = rand.nextInt(upperboundCoordiante);
            coorY = rand.nextInt(upperboundCoordiante);
        }
        WildEngimon wildEngimon;
        //Generate engimon berdasarkan cell type cell kosong
        CellType cellTypeTujuan = map.getCell(coorX, coorY).getType();
        switch (cellTypeTujuan){
            case MOUNTAIN :
                wildEngimon = new WildEngimon("ikan","fire",level,coorX,coorY,
                        this.map);
                break;
            case GRASSLAND :
                int type = rand.nextInt(2);
                if (type == 0) {
                    wildEngimon = new WildEngimon("kelelawar","ground",level,coorX,coorY,
                            this.map);
                }
                else {
                    wildEngimon = new WildEngimon("beruang","electric",level,coorX,coorY,
                            this.map);
                }
                break;
            case SEA :
                wildEngimon = new WildEngimon("kambing","water",level,coorX,coorY,
                        this.map);
                break;

            case TUNDRA:
                wildEngimon = new WildEngimon("kadal","ice",level,coorX,coorY,
                        this.map);
                break;
            default :
                System.out.println("Masuk NULL");
                wildEngimon = null;

        }
        return wildEngimon;
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
                    //Bandingkan level wild dengan level active
                    if (map.getCell(i, j).getEngimon().getLevel() > player.getActiveEngimon().getLevel()) {
                        map.getCell(i, j).getEngimon().drawEngimon(g);
                    } else {
                        map.getCell(i, j).getEngimon().drawEngimonSmall(g);
                    }

                } else {
                    //System.out.println("ga masuk");
                }
            }
        }

        g.drawImage(player.getPlayerActive(), player.getX(), player.getY(), null);
        if (player.getActiveEngimon() != null) {
            player.getActiveEngimon().drawEngimon(g);
        }


    }

    private void writeObject(ObjectOutputStream oos)
            throws IOException {
        removeKeyListener(listener);
        setFocusable(false);
        oos.defaultWriteObject();
    }

    private void readObject(ObjectInputStream ois)
            throws ClassNotFoundException, IOException {
        ois.defaultReadObject();

        listener = new KListener();
        addKeyListener(listener);
        setFocusable(true);

        timer = new Timer(25, this);
        timer.start();

        for (WildEngimon wild : wilds) {
            wild.setMap(this.map);
            wild.resumeTimer();
        }
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
