package Game;

import java.util.ArrayList;

import java.io.Serializable;

public class Skill implements Serializable {
    private ArrayList<String> elements;
    private String skillName;
    private Integer basePower;
    private Integer masteryLevel;

    public Skill(String skillName, Integer basePower, Integer masteryLevel) {
        this.elements = new ArrayList<String>();
        this.skillName = skillName;
        this.basePower = basePower;
        this.masteryLevel = masteryLevel;
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
}