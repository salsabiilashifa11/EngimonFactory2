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
import java.util.TimerTask;

public class Board extends JPanel implements ActionListener, Serializable {
    //Atribut
    private transient Timer timer; //to continuously repaint
    private transient java.util.Timer timerLifeCycle; // to kill Engimon
    private transient KListener listener;
    private Map map; //map
    private Player player;//player
    private ArrayList<WildEngimon> wilds;

    //Konstruktor
    public Board() {
        this.setBackground(Color.decode("#f2f2f2"));
        map = new Map();
        player = new Player("Shifa", map);
        listener = new KListener();
        addKeyListener(listener);
        setFocusable(true);
        timer = new Timer(25, this);
        timer.start();
        engimonInit(15);
        TimerTask killEngimon = new TimerTask() {
            @Override
            public void run(){
                for (WildEngimon wildeng: wilds){
                    if (wildeng.getStatus().equals("dead")){
                        map.getCell(wildeng.getPosition().getX(),wildeng.getPosition().getY()).setEngimon(null);
                        wildeng = null;
                    }
                }
            }
        };
        TimerTask periodicSpawnEngimon = new TimerTask() {
            @Override
            public void run() {
                Random rand = new Random();
                int maxLvl = player.getMaxLevelEngimon();
                int selisih;
                if (maxLvl < 30) {
                    selisih = 30 - maxLvl;
                } else {
                    selisih = 1;
                }
                int n = rand.nextInt(5) + 1;
                int lvl = rand.nextInt(selisih) + maxLvl;
                for (int i = 0; i < n; i++) {
                    WildEngimon spawn = engimonSpawn();
                    spawn.setLevel(lvl);
                    wilds.add(spawn);
                }

            }
        };
        timerLifeCycle = new java.util.Timer();
        timerLifeCycle.schedule(killEngimon,0,15000);
        timerLifeCycle.schedule(periodicSpawnEngimon,0,15000);
    }

    //Getter
    public Map getMap() { return map; }
    public Player getPlayer() { return player; }

    //Fungsi Tambahan
    public void engimonInit(int N) {
        wilds = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            wilds.add(engimonSpawn());
            WildEngimon spawned = wilds.get(i);
            map.getCell(spawned.getPosition().getX(), spawned.getPosition().getY()).setEngimon(spawned);
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
            coorX = rand.nextInt(upperboundCoordiante) + 1  ;
            coorY = rand.nextInt(upperboundCoordiante) + 1;
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
                System.out.println(cellTypeTujuan.toString());
                System.out.println("posisi null" + coorX + " ," +coorY);
                System.out.println("Masuk NULL");
                wildEngimon = null;

        }
        return wildEngimon; }

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
                    if (player.getActiveEngimon() != null) {
                        if (map.getCell(i, j).getEngimon().getLevel() > player.getActiveEngimon().getLevel()) {
                            map.getCell(i, j).getEngimon().drawEngimon(g);
                        } else {
                            map.getCell(i, j).getEngimon().drawEngimonSmall(g);
                        }
                    } else {
                        map.getCell(i, j).getEngimon().drawEngimon(g);
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
        TimerTask killEngimon = new TimerTask() {
            @Override
            public void run(){
                for (WildEngimon wildeng: wilds){
                    if (wildeng.getStatus().equals("dead")){
                        map.getCell(wildeng.getPosition().getX(),wildeng.getPosition().getY()).setEngimon(null);
                        wildeng = null;
                    }
                }
            }
        };
        TimerTask periodicSpawnEngimon = new TimerTask() {
            @Override
            public void run() {
                Random rand = new Random();
                int maxLvl = player.getMaxLevelEngimon();
                int selisih;
                if (maxLvl <= 30) {
                    selisih = 30 - maxLvl;
                } else {
                    selisih = 1;
                }
                int n = rand.nextInt(5) + 1;
                int lvl = rand.nextInt(selisih) + maxLvl;
                for (int i = 0; i < n; i++) {
                    WildEngimon spawn = engimonSpawn();
                    spawn.setLevel(lvl);
                    wilds.add(spawn);
                }

            }
        };
        timerLifeCycle = new java.util.Timer();
        timerLifeCycle.schedule(killEngimon,0,15000);
        timerLifeCycle.schedule(periodicSpawnEngimon,0,15000);

        //for (WildEngimon wild : wilds) {
        //    wild.setMap(this.map);
        //    wild.resumeTimer();
        //}

        for (int i = 0; i < wilds.size(); i++) {
            wilds.get(i).setMap(this.map);
            wilds.get(i).resumeTimer();
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
