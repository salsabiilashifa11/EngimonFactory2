public class tesInventory {
    public static void main(String[] args) {
        Skill s1 = new Skill("JANCO",30,3);
        Skill s2 = new Skill("ASU",15,3);
        Skill s3 = new Skill("KANTE",45,3);
        SkillItems si1 = new SkillItems(s1,1);
        SkillItems si2 = new SkillItems(s2,1);
        SkillItems si3 = new SkillItems(s3,1);
        Inventory<SkillItems> inv = new Inventory<>();
        inv.append(si1);
        inv.append(si2);
        inv.append(si3);
        inv.displayAll();
    }
}
