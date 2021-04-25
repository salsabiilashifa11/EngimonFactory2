package Game;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import java.io.Serializable;

public class    Skill implements Serializable {
    private ArrayList<String> elements;
    private String skillName;
    private Integer basePower;
    private Integer masteryLevel;
    // Buat GUI
    private transient Image skillImageLvl1;
    private transient Image skillImageLvl2;
    private transient Image skillImageLvl3;


    public Skill(String skillName, Integer basePower, Integer masteryLevel) {
        this.elements = new ArrayList<String>();
        this.skillName = skillName;
        this.basePower = basePower;
        this.masteryLevel = masteryLevel;
        this.loadImage();
    }

    public void loadImage(){
        ImageIcon img1,img2,img3;
        String assetFolder = "/Users/darubagus/Downloads/EngimonFactory2-main/MapLagi/assets/";
        img1 = new ImageIcon(assetFolder + this.skillName + "1" + ".png");
        img2 = new ImageIcon(assetFolder + this.skillName + "2" + ".png");
        img3 = new ImageIcon(assetFolder + this.skillName + "3" + ".png");
        skillImageLvl1 = img1.getImage();
        skillImageLvl2 = img2.getImage();
        skillImageLvl3 = img3.getImage();
    }

    public Image getSkillImage() {
        switch (this.masteryLevel) {
            case 1:
                return skillImageLvl1;
            case 2:
                return skillImageLvl2;
            case 3:
                return skillImageLvl3;
            default:
                System.out.println("mastery level skill invalid");
                return null;
        }
    }



    public Image getSkillImageLvl1(){
        return this.skillImageLvl1;
    }
    public Image getSkillImageLvl2(){
        return this.skillImageLvl2;
    }
    public Image getSkillImageLvl3(){
        return this.skillImageLvl3;
    }


    public void addElementSkill(String element) {
        this.elements.add(element);
    }

    public int getNElementSkill() {
        return this.elements.size();
    }

    public ArrayList<String> getElement() {
        return this.elements;
    }

    public String getSkillName() {
        return this.skillName;
    }

    public void setSkillName(String name) {
        this.skillName = name;
    }

    public Integer getBasePower() {
        return this.basePower;
    }

    public void setBasePower(Integer basePower) {
        this.basePower = basePower;
    }

    public Integer getMasteryLevel() {
        return this.masteryLevel;
    }

    public void setMasteryLevel(Integer masteryLevel) {
        if (masteryLevel > 3) {
            System.out.println("Mastery level baru invalid");
        }

        this.masteryLevel = masteryLevel;
    }

    public Skill getMaxMasteryLevel(Skill other) {
        if (this.masteryLevel > other.masteryLevel) {
            return this;
        } else {
            return other;
        }
    }

    public void displaySkill() {
        System.out.println("NamaSKill       : " + this.skillName);
        System.out.println("Basepower       : " + this.basePower);
        System.out.println("MasteryLevel    : " + this.masteryLevel);
        System.out.println("Elements        : ");
        for (int i = 0; i < this.elements.size(); i++) {
            if (i == this.elements.size() - 1) {
                System.out.println(this.elements.get(i));
            }else{
                System.out.print(this.elements.get(i) + " | ");
            }
        }
    }

    public Boolean isCompatibleSkill(String elements) {
        Boolean status = false;
        int i = 0;
        while ((!status) && (i < this.elements.size())) {
            if (this.elements.get(i).equals(elements)) {
                status = true;
            } else {
                i++;
            }
        }
        return status;
    }

    private void readObject(ObjectInputStream ois)
            throws ClassNotFoundException, IOException {
        ois.defaultReadObject();

        loadImage();
    }
}