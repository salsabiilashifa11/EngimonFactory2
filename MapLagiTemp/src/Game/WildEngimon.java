package Game;

import java.awt.*;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import GUI.Map;

import javax.swing.*;

public class WildEngimon extends Engimon implements Serializable {
    //Atribut
    private Position position;
    private transient Timer timer = new Timer();
    private Map map;
    //FOR GUI
    private transient Image wildImageSmall;
    private transient Image wildImageBig;
    private static final HashMap<String, String> spesiesSkill;
    static {
        spesiesSkill = new HashMap<String, String>();
        spesiesSkill.put("kadal", "libasan ekor keadilan");
        spesiesSkill.put("ikan", "tembakan gelembung kebebasan");
        spesiesSkill.put("kambing", "serudukan tanduk keputusasaan");
        spesiesSkill.put("beruang", "cakaran cakar kematian");
        spesiesSkill.put("kelelawar", "teriakan ultrasonic kemarahan");

    }


    //Konstruktor
    public WildEngimon(String _species, String _element, int _level, int _x, int _y, Map _m){
        this.name = _species;
        this.species = _species;
        this.life = 1;
        String namaSkill = spesiesSkill.get(this.species);
        int basePower = 0;
        int masteryLevel = 0;

        if (species.equals("kadal")){
            basePower = 100;
            masteryLevel = 1;
        }
        else if (species.equals("ikan")){
            basePower = 110;
            masteryLevel = 1;
        }
        else if (species.equals("kambing")){
            basePower = 120;
            masteryLevel = 1;
        }
        else if (species.equals("beruang")){
            basePower = 80;
            masteryLevel = 1;
        }
        else if (species.equals("kelelawar")){
            basePower = 90;
            masteryLevel = 1;
        }

        Skill s = new Skill(namaSkill,basePower, masteryLevel);
        this.addElements(_element);
        s.addElementSkill(this.elements.get(0));
        this.addSkill(s);
        this.level = _level;
        this.experience = 0;
        this.cumulativeExperience = 0;
        this.status = "wild";
        this.position = new Position();
        this.setPosition(_x,_y,_m);
        loadImage();
        TimerTask myTask = new TimerTask() {
            @Override
            public void run() {
                if (!status.equals("dead")) {
                    move(_m);
                }
            }
        };
        TimerTask addXpTask = new TimerTask(){
            @Override
            public void run(){
                increaseXP(100);
            }
        };
        timer.schedule(myTask, 0, 5000);
        timer.schedule(addXpTask,0,30000);
        map = _m;
    }

    private void loadImage() {
        //Loading active engimon sprites
        ImageIcon img;
        switch (this.getElements().get(0)) {
            case "fire":
                img = new ImageIcon("assets/GreenBig.png");
                wildImageBig = img.getImage();
                img = new ImageIcon("assets/GreenSmall.png");
                wildImageSmall = img.getImage();
                break;
            case "water":
                img = new ImageIcon("assets/RedBig.png");
                wildImageBig = img.getImage();
                img = new ImageIcon("assets/RedSmall.png");
                wildImageSmall = img.getImage();
                break;
            case "electric":
                img = new ImageIcon("assets/YellowBig.png");
                wildImageBig = img.getImage();
                img = new ImageIcon("assets/YellowSmall.png");
                wildImageSmall = img.getImage();
                break;
            case "ground" :
                img = new ImageIcon("assets/PurpleBig.png");
                wildImageBig = img.getImage();
                img = new ImageIcon("assets/PurpleSmall.png");
                wildImageSmall = img.getImage();
                break;
            case "ice":
                img = new ImageIcon("assets/BlueBig.png");
                wildImageBig = img.getImage();
                img = new ImageIcon("assets/BlueSmall.png");
                wildImageSmall = img.getImage();
                break;
        }
    }


    //Getter
    public String getStatus(){
        return this.status;
    }
    public Timer getTimer() { return timer; }
    public Position getPosition() {
        return position;
    }


    //Setter
    public void setStatus(String status) {
        this.status = status;
    }
    public void setPosition(int _x, int _y, Map m) {
        this.position.setX(_x);
        this.position.setY(_y);
        m.getCell(_x,_y).setEngimon(this);
    }
    public void setMap(Map _m) { map = _m; }


    //Fungsi Tambahan
    public void displayDetail() {
        System.out.println("===================Info Musuh==================");
        System.out.println("Nama           : " + this.name);
        System.out.println("Species        : " + this.species);
        System.out.println("Skill          : ");

        for (int i = 0; i< this.skills.size(); i++){
            System.out.println("--->" + i+1 + ". ");
            this.skills.get(i).displaySkill();
            System.out.println();
        }

        System.out.println("Element        : " + this.elements.get(0));
        System.out.println("Level          : " + this.level);
        System.out.println("Cumulative XP  : " + this.cumulativeExperience );
    }

    public void move(Map m) {
        int x = this.position.getX();
        int y = this.position.getY();
        Random rand = new Random();
        int upperbound = 4;
        int number = rand.nextInt(upperbound) + 1;

        if (number == 1) {
            if (validPosition(m, x, y + 1)) {
                m.getCell(x, y).setEngimon(null);
                this.setPosition(x, y + 1, m);
            }
        } else if (number == 2) {
            if (validPosition(m, x, y - 1)) {
                m.getCell(x, y).setEngimon(null);
                this.setPosition(x, y - 1, m);
            }
        } else if (number == 3) {
            if (validPosition(m, x + 1, y)) {
                m.getCell(x, y).setEngimon(null);
                this.setPosition(x + 1, y, m);
            }
        } else {
            if (validPosition(m, x - 1, y)) {
                m.getCell(x, y).setEngimon(null);
                this.setPosition(x - 1, y, m);
            }
        }
    }

    public Boolean validElementPosition(Map m, int x, int y) {
        Cell cell = m.getCell(x, y);
        String element = this.getElements().get(0);
        if (element.equals("fire") && cell.getType() == CellType.MOUNTAIN) {
            return true;
        } else if (element.equals("water") && cell.getType() == CellType.SEA) {
            return true;
        } else if (element.equals("ground") && cell.getType() == CellType.GRASSLAND) {
            return true;
        } else if (element.equals("electric") && cell.getType() == CellType.GRASSLAND) {
            return true;
        } else if (element.equals("ice") && cell.getType() == CellType.TUNDRA) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean validPosition(Map m, int x, int y) {
        if (this.validElementPosition(m, x, y) && (x < 19) && ((y < 19)) && (m.getCell(x, y).getEngimon() == null) && (m.getCell(x, y).getPlayer() == null)) {
            return true;
        } else {
            return false;
        }
    }

    public void stopTimer() {
        timer.cancel();
    }

    public void resumeTimer() {
        timer = new Timer();
        TimerTask myTask = new TimerTask() {
            @Override
            public void run() {
                if (!status.equals("dead")) {
                    move(map);
                }
            }
        };
        TimerTask addXpTask = new TimerTask(){
            @Override
            public void run(){
                increaseXP(100);
            }
        };
        timer.schedule(myTask, 0, 5000);
        timer.schedule(addXpTask,0,30000);
    }

    //For GUI - Drawing the Engimon
    @Override
    public void drawEngimon(Graphics g) {
        g.drawImage(wildImageBig, position.getY()*32, position.getX()*32, null);
    }

    public void drawEngimonSmall(Graphics g)  {
        g.drawImage(wildImageSmall, position.getY()*32+4, position.getX()*32+4, null);
    }

    private void readObject(ObjectInputStream ois)
            throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        loadImage();
    }

}