import java.util.ArrayList;

public abstract class Engimon {
    protected String name;
    protected String species;
    protected ArrayList<Skill> skills;
    protected ArrayList<String> elements;
    protected int life;
    protected int level;
    protected int experience;
    protected int cumulativeExperience;

    public Engimon(String name, String species, int level) {
        this.name = name;
        this.species = species;
        this.life = 3;
        this.level = level;
        this.experience = 0;
        this.cumulativeExperience = 0;
    }

    public void levelUp() {
        while (this.experience >= 100) {
            this.experience -= 100;
            this.level += 1;
        }
    }

    void increaseXP(int exp) {
        this.experience += exp;
        this.cumulativeExperience += exp;
        this.levelUp();
    }

    public abstract void displayDetail();

    public abstract String getStatus();

    public abstract void setStatus(String status);

    // Getter and setter
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return this.species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public int getLife() {
        return this.life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExperience() {
        return this.experience;
    }

    public void setExperience(int exp) {
        this.experience = exp;
    }

    public int getCumulativeExperience() {
        return this.cumulativeExperience;
    }

    public void setCumulativeExperience(int cExp) {
        this.cumulativeExperience = cExp;
    }

    public ArrayList<Skill> getSkill() {
        return this.skills;
    }

    public int getNSkill() {
        return this.skills.size();
    }

    public void addSkill(Skill skill) {
        this.skills.add(skill);
    }

    public ArrayList<String> getElements() {
        return this.elements;
    }

    public int getNElements() {
        return this.elements.size();
    }

    public void addElements(String element) {
        this.elements.add(element);
    }

    public Boolean isMemberSkill(String skillName) {
        Boolean duplicate = false;
        for (Skill x : this.skills) {
            if (x.getSkillName().equals(skillName)) {
                duplicate = true;
            }
        }
        return duplicate;
    }

    public Boolean isCorrectElement(String childElements) {
        Boolean found = false;
        for (String x : this.elements) {
            if (x.equals(childElements)) {
                found = true;
            }
        }
        return found;
    }

    public int findSkillIndex(String skillName) {
        Boolean found = false;
        int i = 0;
        while ((found == false) && (i < this.skills.size())) {
            if (this.skills.get(i).getSkillName() == skillName) {
                found = true;
            } else {
                i++;
            }
        }
        if (i < this.skills.size()) {
            return i;
        } else {
            return -1;
        }
    }

    // public Double getStrongestEl(Engimon enemy){

    // }

    void swap(Skill a, Skill b) {
        Skill t = a;
        a = b;
        b = t;
    }

    int partition(ArrayList<Skill> arr, int low, int high) {
        int pivot = arr.get(high).getMasteryLevel(); // pivot
        int i = (low - 1); // Index of smaller element

        for (int j = low; j <= high - 1; j++) {
            // If current element is smaller than or
            // equal to pivot
            if (arr.get(j).getMasteryLevel() >= pivot) {
                i++; // increment index of smaller element
                swap(arr.get(i), arr.get(j));
            }
        }
        swap(arr.get(i + 1), arr.get(high));
        return (i + 1);
    }

    void quickSort(ArrayList<Skill> arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }
}
