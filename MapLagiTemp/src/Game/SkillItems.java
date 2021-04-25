package Game;

import java.io.Serializable;
import java.awt.Image;

public class SkillItems implements Inventoryable, Serializable {
    //Atribut
    private Skill item_skill;
    private Integer quantity;

    //Konstruktor
    public SkillItems(Skill s, Integer q) {
        this.item_skill = s;
        this.quantity = q;
    }

    //Getter
    public int getQuantity() {
        return this.quantity;
    }
    public String getName() {
        return this.item_skill.getSkillName();
    }
    public Skill getSkill() {
        return this.item_skill;
    }
    public Integer getBasePower(){
        return this.item_skill.getBasePower();
    }
    public Image getInvIcon() {
        return item_skill.getSkillImage();
    }

    //Fungsi Tambahan
    public void addQuantity(int n) {
        this.quantity += n;
    }

    public void invDisplay() {
        System.out.println(getName() + " (qty: " + getQuantity() + ")");
        System.out.println("base power: " + this.item_skill.getBasePower());
        System.out.print("Elemen: ");
        System.out.print(this.item_skill.getElement().get(0));
        for (int i = 1; i < this.item_skill.getElement().size(); i++) {
            System.out.print(", " + this.item_skill.getElement().get(i));
        }
        System.out.println();
    }
}