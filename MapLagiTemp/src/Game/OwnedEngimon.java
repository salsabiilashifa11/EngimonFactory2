package Game;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class OwnedEngimon extends Engimon implements Inventoryable, Serializable {
    //Atribut
    private ArrayList<String> parentName;
    private Position position;
    private static final Map<String, String> percakapan;
    //Buat GUI
    private transient Image activeActive;
    private transient Image activeFront, activeLeft, activeRight, activeBack;
    private transient Image ownedEngimonImage;
    static {
        HashMap<String, String> temp = new HashMap<String, String>();
        temp.put("ikan", "halo saya api");
        temp.put("kambing", "halo saya air");
        temp.put("beruang","halo saya listrik");
        temp.put("kelelawar","halo saya tanah");
        temp.put("kadal","halo saya es");
        temp.put("siamang","halo saya kebakaran");
        temp.put("mammoth", "halo saya LDR");
        temp.put("kecoa", "halo saya subur");
        percakapan = Collections.unmodifiableMap(temp);
    }


    //Konstruktor
    public OwnedEngimon(){
        super();
        this.status = "owned";
        this.parentName = new ArrayList<String>(2);
        this.position = new Position();
    }

    public OwnedEngimon(String name, String species, int life, int level){
        super(name, species, life, level);
        this.status = "owned";
        this.parentName = new ArrayList<String>(2);
        this.position = new Position();

        //Loading active engimon sprites
        ImageIcon img = new ImageIcon("assets/OwnedFront.png");
        activeFront = img.getImage();
        img = new ImageIcon("assets/OwnedLeft.png");
        activeLeft = img.getImage();
        img = new ImageIcon("assets/OwnedRight.png");
        activeRight = img.getImage();
        img = new ImageIcon("assets/OwnedBack.png");
        activeBack = img.getImage();
        img = new ImageIcon("assets/" + this.species + ".png");
        ownedEngimonImage = img.getImage();
        activeActive = activeFront;
    }

    //Getter
    public Image getOwnedEngimonImage(){
        return this.ownedEngimonImage;
    }
    public ArrayList<String> getParentName() {
        return parentName;
    }
    public String getStatus() {
        return this.status;
    }
    public Position getPosition() {
        return position;
    }
    public int getQuantity() {
        return 1;
    }
    public Image getActiveFront() { return activeFront; }
    public Image getActiveBack() { return activeBack; }
    public Image getActiveLeft() { return activeLeft; }
    public Image getActiveRight() { return activeRight; }
    public Image getInvIcon() {
        return ownedEngimonImage;
    }


    //Setter
    public void setOwnedEngimonImage(Image _ownedEngimonImage){
        this.ownedEngimonImage = _ownedEngimonImage;
    }
    public void setParentName(String parentName1, String parentName2) {
        this.parentName.add(parentName1);
        this.parentName.add(parentName2);
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setPosition(int _x, int _y) {
        this.position.setX(_x);
        this.position.setY(_y);
    }
    public void setActiveActive(Image activeActive) {
        this.activeActive = activeActive;
    }


    //Fungsi Tambahan
    public void interact(){
        System.out.println("[" + this.name + "]: " + percakapan.get(this.species));
    }

    public void moveActive(int _x, int _y){
        if (_x != 29){
            this.position.setX(_x+1);
            this.position.setY(_y);
        } else if (_y != 0){
            this.position.setX(_x);
            this.position.setY(_y-1);
        } else {
            this.position.setX(_x);
            this.position.setY(_y+1);
        }
    }

    public void displayDetail(){
        System.out.println("=====================Info Engimon=====================");
        System.out.println("Nama                   : " + this.name);
        System.out.println("Species                : " + this.species);
        System.out.println("Skill                  : ");

        for (int i = 0; i< this.skills.size(); i++){
            System.out.println("    " + (i+1) + ". ");
            this.skills.get(i).displaySkill();
            System.out.println();
        }
        System.out.println("Element                : ");
        for (int i = 0; i< this.elements.size(); i++){
                if (i != this.elements.size() - 1){
                System.out.print(" | ");
                }
                else{
                    System.out.println((i+1) + ". " + this.elements.get(i));
                }
        }
        System.out.println();
        System.out.println("Level                  : " + this.level);
        System.out.println("Life                   : " + this.life);
        System.out.println("Experience             : " + this.experience);
        System.out.println("Cumulative Experience  : " + this.cumulativeExperience);
        System.out.println("Abi                    : " + ((this.getParentName().size() == 0) ? "N/A" :this.getParentName().get(0)));
        System.out.println("Mami                   : " + ((this.getParentName().size() == 0) ? "N/A" :this.getParentName().get(1)));
        System.out.println("Status                 : " + this.getStatus());
        System.out.println("=======================================================");

    }

    public void calculatePower(Engimon enemy) {
        double myPower = 0;
        myPower += enemy.getStrongestEl(enemy) * this.getLevel();
        for (int i = 0; i < this.getNSkill(); i++) {
            myPower += (this.skills.get(i).getBasePower() * this.skills.get(i).getMasteryLevel());
        }

        double enemyPower = 0;
        enemyPower += enemy.getStrongestEl(this) * enemy.getLevel();
        for (int i = 0; i < enemy.getNSkill(); i++) {
            enemyPower += enemy.getSkill().get(i).getBasePower() * enemy.getSkill().get(i).getMasteryLevel();
        }
        System.out.println("======================Info Battle======================");
        System.out.println("My Power       : " + myPower);
        System.out.println("Enemy Power    : " + enemyPower);
    }

    public boolean fight(Engimon enemy){
        double myPower = 0;
        myPower += enemy.getStrongestEl(enemy) * this.getLevel();
        for (int i = 0; i < this.getNSkill(); i++) {
            myPower += (this.skills.get(i).getBasePower() * this.skills.get(i).getMasteryLevel());
        }

        double enemyPower = 0;
        enemyPower += enemy.getStrongestEl(this) * enemy.getLevel();
        for (int i = 0; i < enemy.getNSkill(); i++) {
            enemyPower += enemy.getSkill().get(i).getBasePower() * enemy.getSkill().get(i).getMasteryLevel();
        }

        return myPower >= enemyPower;
    }

    public void invDisplay() {
        System.out.print(getName() + "/");
        System.out.print(this.getElements().get(0));
        for (int i = 1; i < this.getElements().size(); i++) {
            System.out.print(", " + this.getElements().get(i));
        }
        System.out.print("/");
        System.out.println("Lv." + getLevel());
    }

    public void addQuantity(int n) {}

    @Override
    public void drawEngimon(Graphics g) {
        g.drawImage(activeActive, position.getY()*32, position.getX()*32, null);
    }

    public void drawEngimonSmall(Graphics g) {
        g.drawImage(activeActive, position.getY()*32, position.getX()*32, null);
    }

    public void move(GUI.Map map) {};


    private void readObject(ObjectInputStream ois)
            throws ClassNotFoundException, IOException {
        ois.defaultReadObject();

        //Loading active engimon sprites
        ImageIcon img = new ImageIcon("assets/OwnedFront.png");
        activeFront = img.getImage();
        img = new ImageIcon("/Users/shifa/Desktop/MapLagiTemp/assets/OwnedLeft.png");
        activeLeft = img.getImage();
        img = new ImageIcon("/Users/shifa/Desktop/MapLagiTemp/assets/OwnedRight.png");
        activeRight = img.getImage();
        img = new ImageIcon("assets/OwnedBack.png");
        activeBack = img.getImage();
        img = new ImageIcon("assets/" + this.species + ".png");
        ownedEngimonImage = img.getImage();
         activeActive = activeFront;
    }
}
