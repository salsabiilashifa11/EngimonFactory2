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
    private Position position;
    private Timer timer = new Timer();
    private Map map;
    //FOR GUI
    private transient Image wildImage;
//    private Integer element2int(String element) {
//        if (element.equals("fire") || element.equals("ground") || element.equals("electric") ){
//            return 1;
//        } else {
//            return 2;
//        }
//    }

    private static final HashMap<String, String> spesiesSkill;
    static {
        spesiesSkill = new HashMap<String, String>();
        spesiesSkill.put("kadal", "libasan ekor keadilan");
        spesiesSkill.put("ikan", "tembakan gelembung kebebasan");
        spesiesSkill.put("kambing", "serudukan tanduk keputusasaan");
        spesiesSkill.put("beruang", "cakaran cakar kematian");
        spesiesSkill.put("kelelawar", "teriakan ultrasonic kemarahan");

    }



//    public WildEngimon() {
//        super();
//        this.status = "wild";
//        this.position = new Position();
//    }
//
//    public WildEngimon(String name, String species, int life, int level){
//        super(name, species, life, level);
//        this.status = "wild";
//        this.position = new Position();
//    }

    public WildEngimon(String _species, String _element, int _level, int _x, int _y, Map _m){
        this.name = _species;
        this.species = _species;
        String namaSkill = spesiesSkill.get(this.species);
        int basePower = 0;
        int masteryLevel = 0;

        if (species.equals("kadal")){
            basePower = 100;
            masteryLevel = 3;
        }
        else if (species.equals("ikan")){
            basePower = 110;
            masteryLevel = 2;
        }
        else if (species.equals("kambing")){
            basePower = 120;
            masteryLevel = 4;
        }
        else if (species.equals("beruang")){
            basePower = 80;
            masteryLevel = 2;
        }
        else if (species.equals("kelelawar")){
            basePower = 90;
            masteryLevel = 5;
        }

        Skill s = new Skill(namaSkill,basePower, masteryLevel);
        //this.elements.set(0,_element);
        this.addElements(_element);
        //this->nElements = 1; //auto ter-set 1
        s.addElementSkill(this.elements.get(0));
        //this.skills.
        //this->nSkill = 0; //butuh setter
        this.addSkill(s);
        this.level = _level;
        this.experience = 0;
        this.cumulativeExperience = this.level*100;
        this.status = "wild";
        this.position = new Position();
        this.setPosition(_x,_y,_m);

        loadImage();

        TimerTask myTask = new TimerTask() {
            @Override
            public void run() {
                move(_m);
            }
        };

        timer.schedule(myTask, 0, 5000);
        map = _m;
    }

    private void loadImage() {
        //Loading active engimon sprites
        ImageIcon img = new ImageIcon("/Users/shifa/Desktop/MapLagi/assets/WildEngimonRed.png");
        wildImage = img.getImage();
    }

    public String getStatus(){
        return this.status;
    }
    public Timer getTimer() { return timer; }

    public void setStatus(String status) {
        this.status = status;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(int _x, int _y, Map m) {
        this.position.setX(_x);
        this.position.setY(_y);
        m.getCell(_x,_y).setEngimon(this);
    }

    public void displayDetail() {
        System.out.println("======================Info Musuh======================");
        System.out.println("Nama           : " + this.name);
        System.out.println("Species        : " + this.species);
        System.out.println("Skill          : ");

        for (int i = 0; i< this.skills.size(); i++){
            System.out.println("    " + i+1 + ". ");
            this.skills.get(i).displaySkill();
            System.out.println();
        }

        System.out.println("Element        : " + this.elements.get(0));
        System.out.println("Level          : " + this.level);
        // cout << "----------------------------------------------" << endl;
    }

//    public void assertPosition(Map m) {
//
//        bool validPosition = false;
//        int prevX = position.getX();
//        int prevY = position.getY();
//
//        if (m->getCell(position.getX(), position.getY()).getOccupierP().getName() != "" ||
//                m->getCell(position.getX(), position.getY()).getOccupierE()->getName() != "XXX") {
//            while (!validPosition) {
//                Move(m);
//                if (position.getX() != prevX || position.getY() != prevY) {
//                    validPosition = true;
//                }
//            }
//        }
//
//        m->getCell(position.getX(), position.getY()).setEngimon(this);
//    }

    public void move(Map m) {
        int x = this.position.getX();
        int y = this.position.getY();
        Random rand = new Random();
        int upperbound = 4;
        int number = rand.nextInt(upperbound) + 1;
        // Engimon temp = new WildEngimon();

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
                move(map);
            }
        };
        timer.schedule(myTask, 0, 5000);
    }

    //For GUI - Drawing the Engimon
    @Override
    public void drawEngimon(Graphics g) {
        g.drawImage(wildImage, position.getY()*32, position.getX()*32, null);
    }

    private void readObject(ObjectInputStream ois)
            throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        loadImage();
    }

}