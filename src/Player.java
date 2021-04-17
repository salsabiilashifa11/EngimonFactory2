import javax.swing.*;
import java.lang.annotation.ElementType;
import java.sql.SQLOutput;
import java.util.Scanner;

import static java.lang.Math.max;

public class Player {
//    Fields
    public final static int MAX_EL = 15;
    public final static int MAX_EXP = 20150;
    private String name;
    private Position playerPos;
    private OwnedEngimon active;
    private Inventory<OwnedEngimon> playerEngimons;
    private Inventory<SkillItems> playerItems;

//    For testing purpose:
//    private void makeEngimon();
//    private void initateSkill();

//    Constructor
    public Player(){
        this.name =  "";
        setPosition(0,0);
    }

    public Player(String _name){
        this.name = _name;
        setPosition(0,0);
    }

//    Commands
    public void Move(char _direction){
        Position temp = getPosition();
        if (validMove(_direction)) {
            //Engimon follow
//            active.setPosition(temp.getX(),temp.getY());

            if (_direction == 'w'){
                setPosition(temp.getX()-1, temp.getY());
            } else if (_direction == 'a') {
                setPosition(temp.getX(), temp.getY()-1);
            } else if (_direction == 's') {
                setPosition(temp.getX()+1, temp.getY());
            } else if (_direction == 'd') {
                setPosition(temp.getX(), temp.getY()+1);
            }
        } else {
            throw("Nabrak broo");
        }
    }

    public boolean validMove(char _direction){
        Position temp = getPosition();
        if (_direction == 'w'){
            return temp.getX()-1 >= 0;
        } else if (_direction == 'a') {
            return temp.getY()-1 >= 0;
        } else if (_direction == 's') {
            return temp.getX()+1 < 30;
        } else if (_direction == 'd') {
            return temp.getY()+1 < 30;
        } else {
//            is char throwable?
//            throw _direction;
        }
    }

    public void changeActiveEngimon(){
        int i;
        System.out.println("Pilih Nomor Engimon yang Akan Jadi Active: ");
        for (i = 0; i < playerEngimons.get(i).getNElements(); i++){
            System.out.print((i+1) +". "+playerEngimons.get(i).getName()+" " );
            if (playerEngimons.get(i).getName().equals(active.getName())){
                System.out.print("(active)");
            }
            System.out.println();
        }
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();
        playerEngimons.get(input-1).setPosition(active.getPosition().getX(), active.getPosition().getY());
        active.setPosition(-1,-1);
        setActiveEngimon(input-1);
    }

    public void breed(Engimon father, Engimon mother){
        if (father.getLevel() >= 4 && mother.getLevel() >= 4){
            OwnedEngimon child = new OwnedEngimon();
            int fatherLevelBefore = father.getLevel();
            int motherLevelBefore = mother.getLevel();
            // Memberi nama anak
            String name;
            System.out.print("Give your child a name: ");
            Scanner scanner = new Scanner(System.in);
            name = scanner.next();
            child.setName(name);
            // Parent Name
            child.setParentName(father.getName(), mother.getName());
//            child.setNSkill(0);

            double fatherAdvantage = ElementAdvantage.getAdv(father.getElements().get(0), mother.getElements().get(0));
            double motherAdvantage = ElementAdvantage.getAdv(mother.getElements().get(0), father.getElements().get(0));
            if (father.getElements().get(0) == mother.getElements().get(0)){
                child.addElements(father.getElements().get(0));
                child.setSpecies(father.getSpecies());
            } else if (fatherAdvantage > motherAdvantage) {
                child.addElements(father.getElements().get(0));
                child.setSpecies(father.getSpecies());
            } else if (fatherAdvantage < motherAdvantage) {
                child.addElements(mother.getElements().get(0));
                child.setSpecies(mother.getSpecies());
            } else {
                /*  1. Fire x Electric -&gt; Fire/Electric
                2. Water x Ice -&gt; Water/Ice
                3. Water x Ground -&gt; Water/Ground
            */
                if ((mother.getElements().get(0) == "fire" && father.getElements().get(0) == "electric")
                        || (father.getElements().get(0) == "fire" && mother.getElements().get(0) == "electric")){
                    child.addElements("fire");
                    child.addElements("electric");
                    child.setSpecies("siamang");
                } else if ((mother.getElements().get(0) == "water" && father.getElements().get(0) == "ice")
                        || (father.getElements().get(0) == "water" && mother.getElements().get(0) == "ice")){
                    child.addElements("water");
                    child.addElements("ice");
                    child.setSpecies("mammoth");
                } else if ((mother.getElements().get(0) == "water" && father.getElements().get(0) == "ground")
                        || (father.getElements().get(0) == "water" && mother.getElements().get(0) == "ground")) {
                    child.addElements("water");
                    child.addElements("ground");
                    child.setSpecies("kecoa");
                }
            }
            if (((child.getElements().get(0) == father.getElements().get(0) && father.getNElements() == 2)
        || (child.getElements().get(0) == father.getElements().get(0) && father.getNElements() == 2)) && child.getNElements() == 1){
                if (child.getElements().get(0) == "water"){
                    child.setSpecies("kambing");
                }
                else if (child.getElements().get(0) == "fire"){
                    child.setSpecies("ikan");
                }
                else if (child.getElements().get(0) == "electric"){
                    child.setSpecies("beruang");
                }
                else if (child.getElements().get(0) == "ground"){
                    child.setSpecies("kambing");
                }
                else if (child.getElements().get(0) == "ice"){
                    child.setSpecies("kadal");
                }
            }

            int i = 0;
            int j = 0;
            while (child.getNSkill() <= 4 && (i < father.getNSkill() || j < mother.getNSkill())){
                if (father.getSkill().get(i).getMasteryLevel() >= mother.getSkill().get(j).getMasteryLevel()){
                    for (int l = 0; l < child.getNElements() && child.getNSkill() <= 4; l++){
                        if (father.isCorrectElement(child.getElements().get(l)) && !child.isMemberSkill(father.getSkill().get(i).getSkillName()) && (i < father.getNSkill()) && (father.getSkill().get(i).isCompatibleSkill(child.getElements().get(l)))){
                            child.addSkill(father.getSkill().get(i));
                            if (mother.isMemberSkill(father.getSkill().get(i).getSkillName())){
                                int skillIdx = mother.findSkillIndex(father.getSkill().get(i).getSkillName());
                                if (skillIdx != -1){
                                    int idxAnak = child.findSkillIndex(father.getSkill().get(i).getSkillName());
                                    if (father.getSkill().get(i).getMasteryLevel() == mother.getSkill().get(skillIdx).getMasteryLevel()){   // Kalo levelnya sama
                                        if (father.getSkill().get(j).getMasteryLevel() != 3){
                                            child.getSkill().get(idxAnak).setMasteryLevel(father.getSkill().get(i).getMasteryLevel() + 1);
                                        } else {
                                            child.getSkill().get(idxAnak).setMasteryLevel(3);
                                        }
                                    } else {    // Kalo levelnya beda
                                        child.getSkill().get(idxAnak).setMasteryLevel(max(father.getSkill().get(i).getMasteryLevel(), mother.getSkill().get(skillIdx).getMasteryLevel()));
                                    }
                                }
                            }
                        }
                    }
                    i++;
                } else {
                    for (int l = 0; l < child.getNElements() && child.getNSkill() <= 4; l++){
                        if (mother.isCorrectElement(child.getElements().get(l)) && !child.isMemberSkill(mother.getSkill().get(j).getSkillName()) && (j < mother.getNSkill()) && (mother.getSkill().get(j).isCompatibleSkill(child.getElements().get(l)))){
                            child.addSkill(mother.getSkill().get(j));
                            if (father.isMemberSkill(mother.getSkill().get(j).getSkillName())){
                                int skillIdx = father.findSkillIndex(mother.getSkill().get(j).getSkillName());
                                if (skillIdx != -1){
                                    int idxAnak = child.findSkillIndex(mother.getSkill().get(j).getSkillName());
                                    if (mother.getSkill().get(j).getMasteryLevel() == father.getSkill().get(skillIdx).getMasteryLevel()){
                                        if (mother.getSkill().get(j).getMasteryLevel() != 3){
                                            child.getSkill().get(idxAnak).setMasteryLevel(father.getSkill().get(skillIdx).getMasteryLevel() + 1);
                                        } else {
                                            child.getSkill().get(idxAnak).setMasteryLevel(3);
                                        }
                                    } else {
                                        child.getSkill().get(idxAnak).setMasteryLevel((max(father.getSkill().get(skillIdx).getMasteryLevel(), mother.getSkill().get(j).getMasteryLevel())));
                                    }
                                }
                            }
                        }
                    }
                    j++;
                }
            }
            // Level, experience, and cumulative
            child.setLevel(1);
            // reduce the level of parent
            father.setLevel(fatherLevelBefore-3);
            mother.setLevel(motherLevelBefore-3);
            // child.displayDetail();
            playerEngimons.append(child);

        }
    }

    public void battle(Engimon enemy){
        System.out.println("                    Player's Engimon                   ");
        active.displayDetail();
        System.out.println("=======================================================");
        System.out.println("                          Enemy                        ");
        enemy.displayDetail();
        System.out.println("=======================================================");
        System.out.println("                 Proceed battle? (y/n)                ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        if (input.equals("y") || input.equals("Y")){
            Boolean win = active.fight(enemy);
            if (!win){
                System.out.println("Kalah battle");
                int temp = active.getLife();
                active.setLife(temp-1);
                if (active.getLife() <= 0) {
                    this.activeMati();
                }
            } else {
                System.out.println("Kamu memenangkan battle");
                System.out.println("=======================================================");
                //Set musuh jadi mati
                enemy.setStatus("dead");
                //Nambahin exp
                int activeIndex = playerEngimons.indexByName(active.getName());
                active.increaseXP(50);
                //playerEngimons[activeIndex] = active; C++
                //playerEngimons.get(activeIndex) = active; Java error
                if (active.getCumulativeExperience() >= MAX_EXP){
                    this.activeMati();
                }
                if (playerEngimons.n_elmt() + playerItems.n_elmt() >= MAX_EL) {
                    System.out.println("Inventory penuh");
                    return;
                }
                //Nambahin engimon
                OwnedEngimon baru = new OwnedEngimon();
                baru.setParentName("", "");

                String nama;
                System.out.print("Masukkan nama  : ");
                Scanner scanner1 = new Scanner(System.in);
                nama = scanner1.next();
                baru.setName(nama);

                baru.setSpecies(enemy.getSpecies());
                baru.addElements(enemy.getElements().get(0));
                for (int i = 0; i < enemy.getNSkill(); i++) {
                    baru.addSkill(enemy.getSkill().get(i));
                }
                baru.setLevel(enemy.getLevel());
                baru.setExperience(enemy.getExperience());
                baru.setCumulativeExperience(enemy.getCumulativeExperience());
                //addToInventory(baru);

                //Nambahin skill items
                if (playerEngimons.n_elmt() + playerItems.n_elmt() >= MAX_EL) {
                    System.out.println("Inventory penuh");
                    return;
                }
                if (enemy.getNSkill() > 0){
                    //this.addToInventory(generateSkillItem(enemy.getElements().get(0)));
                }
            }
        } else {
            return;
        }

    }

    public void teach(String skillName, String eName){
        Skill newSkill = playerItems.get(playerItems.indexByName(skillName)).getSkill();
        boolean kompatibel = false;

        for (int i = 0; i < playerEngimons.get(playerEngimons.indexByName(eName)).getNSkill(); i++) {
            if (playerEngimons.get(playerEngimons.indexByName(eName)).getSkill().get(i).getSkillName() == newSkill.getSkillName()) {
                System.out.println("Engimon sudah punya skill tersebut");
                return;
            }
        }

        for (int i = 0; i < playerEngimons.get(playerEngimons.indexByName(eName)).getNElements(); i++) {
            for (int j = 0; j < newSkill.getNElementSkill(); j++) {
                //harus pake equals?
                if (newSkill.getElement().get(j) == playerEngimons.get(playerEngimons.indexByName(eName)).getElements().get(i)) {
                    kompatibel = true;
                }
            }
        }

        if (!kompatibel) {
            System.out.println("Skill item tidak kompatibel");
            return;
        }

        if (playerEngimons.get(playerEngimons.indexByName(eName)).getNSkill() == 4) {
            System.out.println("Skill engimon penuh, mau tukar dengan skill item? (y/n): ");
            String choice;
            //cin string
            Scanner scanner2 = new Scanner(System.in);
            choice = scanner2.next();

            if (choice.equals("y") || choice.equals("Y") || choice.equals("yes")) {
                System.out.println("Skill engimon: ");
                for (int i = 0; i < 4; i++) {
                    System.out.println((i+1) + ". " + playerEngimons.get(playerEngimons.indexByName(eName)).getSkill().get(i).getSkillName());
                }
                System.out.print("Masukkan nomor skill yang mau ditukar: ");
                int idx;
                Scanner scanner3 = new Scanner(System.in);
                idx = scanner3.nextInt();
                //playerEngimons[playerEngimons.indexByName(eName)].getSkill()[idx] = newSkill; C++
                //playerEngimons.get(playerEngimons.indexByName(eName)).getSkill().get(idx) = newSkill;
                playerItems.deleteAt(playerItems.indexByName(skillName));
                return;
            }
            else {
                System.out.println("Gajadi learn");
                return;
            }
        }
        playerEngimons.get(playerEngimons.indexByName(eName)).addSkill(newSkill);
        playerItems.deleteAt(playerItems.indexByName(skillName));
    }

//    Display
    public void displayEngimon(String _name){
        playerEngimons.get(playerEngimons.indexByName(_name)).displayDetail();
    }

    public void executeBreed(){
        //angka 15 dan 30 nya belom diganti
        if (playerEngimons.n_elmt() + playerItems.n_elmt() < 15){
            for (int i=0; i<playerEngimons.n_elmt(); i++){
                if (playerEngimons.get(i).getLevel() >= 30){
                    System.out.println(i+1 + ". " + playerEngimons.get(i).getName() + " ");
                }
            }
            System.out.println("Pilih Abi :" );
            String Abi;
            Scanner scanner = new Scanner(System.in);
            Abi = scanner.next();

            System.out.println("Pilih Mami:" );
            String Mami;
            Scanner scanner1 = new Scanner(System.in);
            Mami = scanner1.next();

            breed(playerEngimons.get(playerEngimons.indexByName(Abi)), playerEngimons.get(playerEngimons.indexByName(Mami)));
        }
        else
        {
            System.out.println("Inventory penuh, gabisa breed");
        }
    }

//    Getter Setter
    public OwnedEngimon getActiveEngimon(){
        return active;
    }

    public String getName(){
        return name;
    }

    public Position getPosition(){
        return playerPos;
    }

    public void setActiveEngimon(int _index){

    }

    public void activeMati(){
        /*try{
            playerEngimons.n_elmt() == 1;
        } catch {
            System.out.println("Kamu ga punya engimon");
        }*/

        //bikin class baru EngimonException extends Exception

        if (playerEngimons.n_elmt() == 1) {
//            throw new EngimonException("Kamu ga punya engimon");
        }
        else{
            int activeIndex = playerEngimons.indexByName(active.getName());
            active.setStatus("dead");
            //playerEngimons[activeIndex] = active; C++
            //playerEngimons.get(activeIndex) = active;
            int posX, posY;
            posX = active.getPosition().getX();
            posY = active.getPosition().getY();
            active.setPosition(-1, -1);
            playerEngimons.deleteAt(playerEngimons.indexByName(active.getName()));
            playerEngimons.get(0).setPosition(posX, posY);
            setActiveEngimon(0);
        }
    }

    public void setPosition(int _x, int _y){
        playerPos.setX(_x);
        playerPos.setY(_y);
    }

    public void addToInventory(OwnedEngimon el){
        this.playerEngimons.append(el);
    }

    public void addToInventory(SkillItems el){
        this.playerItems.append(el);
    }

    public void showInventory(){
        System.out.println("Engimon: ");
        playerEngimons.displayAll();
        System.out.println("Skill items: ");
        playerItems.displayAll();
    }

    public void showItems(){
        System.out.println("Skill items: ");
        playerItems.displayAll();
    }

    public void showEngimons(){
        System.out.println("Engimon: ");
        playerEngimons.displayAll();
    }

    public SkillItems generateSkillItem(String tnemele){
        if (tnemele == "water"){
            Skill s = new Skill("Tenggelam di lautan cinta",100,1);
            SkillItems sk = new SkillItems(s,1);
            return sk;
        }
        else if (tnemele == "fire"){
            Skill s = new Skill("Tersundut gudang garam filter",100,1);
            SkillItems sk = new SkillItems(s,1);
            return sk;
        }
        else if (tnemele == "ice"){
            Skill s = new Skill("Bertapa di gunung es",100,1);
            SkillItems sk = new SkillItems(s,1);
            return sk;
        }
        else if (tnemele == "electric"){
            Skill s = new Skill("Berjoget di atas sutet", 100, 1);
            SkillItems sk = new SkillItems(s,1);
            return sk;
        }
        else if (tnemele == "ground"){
            Skill s = new Skill("Rajaman perselingkuh",100,1);
            SkillItems sk = new SkillItems(s,1);
            return sk;
        } else {
            Skill s = new Skill("XXX",0,-1);
            SkillItems sk = new SkillItems(s,0);
            return sk;
        }
    }

}
