public class SkillItems {

    private Skill item_skill;
    private int quantity;

    public SkillItems(Skill s, int q) {
        this.item_skill = s;
        this.quantity = q;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public String getName() {
        return this.item_skill.getSkillName();
    }

    public void addQuantity(int n) {
        this.quantity += n;
    }

    public Skill getSkill() {
        return this.item_skill;
    }
}