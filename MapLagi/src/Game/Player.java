package Game;

import GUI.ConsoleOutput;
import GUI.Map;
import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.IOException;
import static java.lang.Math.max;

public class Player implements Serializable {

    //Fields
    private String name;
    private Map map;
    private OwnedEngimon active;
    private Inventory<OwnedEngimon> playerEngimons;
    private Inventory<SkillItems> playerItems;
    public final static int MAX_EL = 15;
    public final static int MAX_EXP = 20150;
    // GUI
    private Position position; //Tile positions (single increments)
    private int x, y; //for GUI drawing positions
    private transient Image playerActive;
    private transient Image playerFront, playerLeft, playerRight, playerBack;

    //Constructor
    public Player(String _name, Map _map) {
        //Attribute initialization
        name = _name;
        position = new Position(1, 1);
        map = _map;
        playerEngimons = new Inventory<>();
        playerItems = new Inventory<>();
        this.makeEngimon();
        this.initiateSkill();
        this.setActiveEngimon(0);
        active.setPosition(1,2);
        map.getCell(position.getX(), position.getY()).setPlayer(this);
        map.getCell(1, 2).setEngimon(active);

        //For GUI - loading all player sprites

        //Loading player sprites
        ImageIcon img = new ImageIcon("/Users/shifa/Desktop/MapLagi/assets/PlayerFront.png");
        playerFront = img.getImage();
        img = new ImageIcon("/Users/shifa/Desktop/MapLagi/assets/PlayerLeft.png");
        playerLeft = img.getImage();
        img = new ImageIcon("/Users/shifa/Desktop/MapLagi/assets/PlayerRight.png");
        playerRight = img.getImage();
        img = new ImageIcon("/Users/shifa/Desktop/MapLagi/assets/PlayerBack.png");
        playerBack = img.getImage();
        playerActive = playerFront;

        //Initiate player position to tile (1,1)
        x = 32;
        y = 32;

        //DEBUG

    }

    //Aksesor
    public Image getPlayerActive() { return playerActive; }
    public Image getPlayerFront() { return playerFront; }
    public Image getPlayerBack() { return playerBack; }
    public Image getPlayerLeft() { return playerLeft; }
    public Image getPlayerRight() { return playerRight; }
    public int getX() { return x; }
    public int getY() { return y; }
    public Position getPosition() { return position; }
    public OwnedEngimon getActiveEngimon() { return active; }
    public String getName() { return name; }
    public Inventory<OwnedEngimon> getPlayerEngimons() {return playerEngimons;}
    public Inventory<SkillItems> getPlayerItems() {return playerItems;}

    public void setPlayerActive(Image playerActive) { this.playerActive = playerActive; }
    public void setPosition(int x, int y) {
        map.getCell(position.getX(), position.getY()).setPlayer(null);
        position.setX(x);
        position.setY(y);
        map.getCell(x, y).setPlayer(this);
    }

    //Methods
    public void move(int dx, int dy, char _direction) {
        Position temp;
        temp = new Position(position.getX(), position.getY());
        System.out.println("TEMP: " + temp.getX() + "," + temp.getY());
        Position tujuan;
        Engimon tujuanOccupier;
        if (validMove(_direction)) {
            map.getCell(active.getPosition().getX(), active.getPosition().getY()).setEngimon(null);
            active.setPosition(temp.getX(),temp.getY());
            map.getCell(active.getPosition().getX(), active.getPosition().getY()).setEngimon(active);

            if (_direction == 'w') {
                setPosition(temp.getX()-1, temp.getY());
                tujuan = new Position(temp.getX()-1, temp.getY());
                setPlayerActive(getPlayerBack());
                active.setActiveActive(active.getActiveBack());
            } else if (_direction == 'a') {
                setPosition(temp.getX(), temp.getY()-1);
                tujuan = new Position(temp.getX(), temp.getY()-1);
                setPlayerActive(getPlayerLeft());
                active.setActiveActive(active.getActiveLeft());
            } else if (_direction == 's') {
                setPosition(temp.getX()+1, temp.getY());
                tujuan = new Position(temp.getX()+1, temp.getY());
                setPlayerActive(getPlayerFront());
                active.setActiveActive(active.getActiveFront());
            } else if (_direction == 'd') {
                setPosition(temp.getX(), temp.getY()+1);
                tujuan = new Position(temp.getX(), temp.getY()+1);
                setPlayerActive(getPlayerRight());
                active.setActiveActive(active.getActiveRight());
            } else {
                tujuan = new Position(1,1);
            }

            tujuanOccupier = map.getCell(tujuan.getX(), tujuan.getY()).getEngimon();
//            System.out.println("TEMP2: " + temp.getX() + "," + temp.getY());
//            System.out.println("TUJUAN: " + tujuan.getX() + "," + tujuan.getY());
            if (tujuanOccupier != null) {
//                System.out.println("BENER");
//                //move
                Position prevPosition = new Position(tujuan.getX(), tujuan.getY());
                Boolean hasMoved = false;
                while  (!hasMoved) {
                    tujuanOccupier.move(map);
                    hasMoved = tujuanOccupier.getPosition().getX() != prevPosition.getX()
                            || tujuanOccupier.getPosition().getY() != prevPosition.getY();
                }
//
            }

            //Update GUI draw position
            x += dx;
            y += dy;


        } else {
            System.out.println("Nabrak broo");
        }

        //DEBUG
        System.out.println(position.getX() + "," + position.getY());

    }

    public boolean validMove(char _direction){
        Position temp = new Position(position.getX(), position.getY());
        if (_direction == 'w'){
            return temp.getX()-1 >= 1;
        } else if (_direction == 'a') {
            return temp.getY()-1 >= 1;
        } else if (_direction == 's') {
            return temp.getX()+1 < 19; //Ini maks map size, manual aja hehe
        } else if (_direction == 'd') {
            return temp.getY()+1 < 19;
        } else {
            return false; //kayaknya nanti harus ganti jadi throw exception
        }
    }

    public void changeActiveEngimon(int idxEngimon) {
        playerEngimons.get(idxEngimon-1).setPosition(active.getPosition().getX(), active.getPosition().getY());
        active.setPosition(-1,-1);
        setActiveEngimon(idxEngimon-1);
    }
//
    public void breed(Engimon father, Engimon mother, String namaAnak){ // Ambil nama anak di MAIN
        if (father.getLevel() >= 4 && mother.getLevel() >= 4){
            OwnedEngimon child = new OwnedEngimon();
            int fatherLevelBefore = father.getLevel();
            int motherLevelBefore = mother.getLevel();
            // Memberi nama anak
//            String name;
//            System.out.print("Give your child a name: ");
//            // ini harusnya minta input dari text field
//            Scanner scanner = new Scanner(System.in);
//            name = scanner.next();
            child.setName(namaAnak);
            // Parent Name
            child.setParentName(father.getName(), mother.getName());
//            child.setNSkill(0);

            double fatherAdvantage = ElementAdvantage.getAdv(father.getElements().get(0), mother.getElements().get(0));
            double motherAdvantage = ElementAdvantage.getAdv(mother.getElements().get(0), father.getElements().get(0));
            if (father.getElements().get(0).equals(mother.getElements().get(0))){
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
                if ((mother.getElements().get(0).equals("fire")  && father.getElements().get(0).equals("electric"))
                        || (father.getElements().get(0).equals("fire") && mother.getElements().get(0).equals("electric"))){
                    child.addElements("fire");
                    child.addElements("electric");
                    child.setSpecies("siamang");
                } else if ((mother.getElements().get(0).equals("water") && father.getElements().get(0).equals("ice"))
                        || (father.getElements().get(0).equals("water") && mother.getElements().get(0).equals("ice"))){
                    child.addElements("water");
                    child.addElements("ice");
                    child.setSpecies("mammoth");
                } else if ((mother.getElements().get(0).equals("water") && father.getElements().get(0).equals("ground"))
                        || (father.getElements().get(0).equals("water") && mother.getElements().get(0).equals("ground"))) {
                    child.addElements("water");
                    child.addElements("ground");
                    child.setSpecies("kecoa");
                }
            }
            if (((child.getElements().get(0).equals(father.getElements().get(0)) && father.getNElements() == 2)
                    || (child.getElements().get(0).equals(father.getElements().get(0)) && father.getNElements() == 2)) && child.getNElements() == 1){
                if (child.getElements().get(0).equals("water")){
                    child.setSpecies("kambing");
                }
                else if (child.getElements().get(0).equals("fire")){
                    child.setSpecies("ikan");
                }
                else if (child.getElements().get(0).equals("electric")){
                    child.setSpecies("beruang");
                }
                else if (child.getElements().get(0).equals("ground")){
                    child.setSpecies("kambing");
                }
                else if (child.getElements().get(0).equals("ice")){
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
                                    if (father.getSkill().get(i).getMasteryLevel().equals(mother.getSkill().get(skillIdx).getMasteryLevel())){   // Kalo levelnya sama
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
                                    if (mother.getSkill().get(j).getMasteryLevel().equals(father.getSkill().get(skillIdx).getMasteryLevel())){
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

    public Boolean battle(Engimon enemy){
        Boolean win = active.fight(enemy);
        if (!win) {
            System.out.println("Kalah battle");
            int temp = active.getLife();
            active.setLife(temp - 1);
            if (active.getLife() <= 0) {
                this.activeMati();
            }
            return false;
        } else {
            return true;
        }
    }

    public void winBattle(Engimon enemy, String nama){
        //Set musuh jadi mati
        enemy.setStatus("dead");
        //Nambahin exp
        active.increaseXP(50);
        if (active.getCumulativeExperience() >= MAX_EXP) {
            this.activeMati();
        }
        if (playerEngimons.n_elmt() + playerItems.n_elmt() >= MAX_EL) {
            System.out.println("Inventory penuh. Gabisa nambahin engimon ke inventory");
            return;
        }
        //Nambahin engimon
        OwnedEngimon baru = new OwnedEngimon(nama, enemy.getSpecies(), 3, enemy.getLevel());
        baru.setParentName("", "");
        baru.addElements(enemy.getElements().get(0));
        for (int i = 0; i < enemy.getNSkill(); i++) {
            baru.addSkill(enemy.getSkill().get(i));
        }
        addToInventory(baru);
        System.out.println("Berhasil nambahin engimon ke inventory");

        //Nambahin skill items
        if (playerEngimons.n_elmt() + playerItems.n_elmt() >= MAX_EL) {
            System.out.println("Inventory penuh. Gabisa nambahin skill items ke inventory");
        }
        if (enemy.getNSkill() > 0) {
            this.addToInventory(generateSkillItem(enemy.getElements().get(0)));
        }
        System.out.println("Berhasil nambahin skill items ke inventory");
        map.getCell(enemy.getPosition().getX(), enemy.getPosition().getY()).setEngimon(null);
        enemy = null;
    }

    public Boolean teachAble(String skillName, String eName) {
        Skill newSkill = playerItems.get(playerItems.indexByName(skillName)).getSkill();
        boolean kompatibel = false;

        for (int i = 0; i < playerEngimons.get(playerEngimons.indexByName(eName)).getNSkill(); i++) {
            if (playerEngimons.get(playerEngimons.indexByName(eName)).getSkill().get(i).getSkillName().equals(newSkill.getSkillName())) {
                System.out.println("Engimon sudah punya skill tersebut");
                return false;
            }
        }

        for (int i = 0; i < playerEngimons.get(playerEngimons.indexByName(eName)).getNElements(); i++) {
            for (int j = 0; j < newSkill.getNElementSkill(); j++) {
                //harus pake equals?
                if (newSkill.getElement().get(j).equals(playerEngimons.get(playerEngimons.indexByName(eName)).getElements().get(i))) {
                    kompatibel = true;
                }
            }
        }

        if (!kompatibel) {
            System.out.println("Skill item tidak kompatibel");
            return false;
        }
        return kompatibel;
    }

    public void teach(String skillName, String eName){
        Skill newSkill = playerItems.get(playerItems.indexByName(skillName)).getSkill();
        playerEngimons.get(playerEngimons.indexByName(eName)).addSkill(newSkill);
        playerItems.deleteAt(playerItems.indexByName(skillName));
    }

    public void displayEngimon(String _name){
        playerEngimons.get(playerEngimons.indexByName(_name)).displayDetail();
    }



    public Boolean checkBreed(){
        if (playerEngimons.n_elmt() + playerItems.n_elmt() < 15){ //
            for (int i=0; i<playerEngimons.n_elmt(); i++) {
                if (playerEngimons.get(i).getLevel() >= 30) {
                    System.out.println(i + 1 + ". " + playerEngimons.get(i).getName() + " ");
                }
            }
            return true;
        }
        else{
            return false;
        }
    }
//
    public void setActiveEngimon(int _index){
        active = this.playerEngimons.get(_index);
    }

    public void interactEngimon(){
        if (active != null){
            active.interact();
        } else {
            System.out.println("Gaada active engimon be***go");
        }
    }

    public void activeMati(){
        /*try{
            playerEngimons.n_elmt() == 1;
        } catch {
            System.out.println("Kamu ga punya engimon");
        }*/

        //bikin class baru EngimonException extends Exception
        if (this.playerEngimons.n_elmt() == 1) {
//            throw new EngimonException("Kamu ga punya engimon");
        }
        else{
            active.setStatus("dead");
            int posX, posY;
            posX = active.getPosition().getX();
            posY = active.getPosition().getY();
            active.setPosition(0, 0);
            playerEngimons.deleteAt(playerEngimons.indexByName(active.getName()));
            active = null;
        }
    }

    public void addToInventory(OwnedEngimon el){
        this.playerEngimons.append(el);
    }
    public void addToInventory(SkillItems el) {
        this.playerItems.append(el);
    }

    public void showInventory(){
        System.out.println("Engimon: ");
        playerEngimons.displayAll();
        System.out.println("Skill items: ");
        playerItems.displayAll();
    }
//
//    public void showItems(){
//        System.out.println("Skill items: ");
//        playerItems.displayAll();
//    }
//
//    public void showEngimons(){
//        System.out.println("Engimon: ");
//        playerEngimons.displayAll();
//    }
//
    public SkillItems generateSkillItem(String tnemele){
        if (tnemele.equals("water") ){
            Skill s = new Skill("Tenggelam di lautan cinta",100,1);
            SkillItems sk = new SkillItems(s,1);
            return sk;
        }
        else if (tnemele.equals("fire")){
            Skill s = new Skill("Tersundut gudang garam filter",100,1);
            SkillItems sk = new SkillItems(s,1);
            return sk;
        }
        else if (tnemele.equals("ice")){
            Skill s = new Skill("Bertapa di gunung es",100,1);
            SkillItems sk = new SkillItems(s,1);
            return sk;
        }
        else if (tnemele.equals("electric")){
            Skill s = new Skill("Berjoget di atas sutet", 100, 1);
            SkillItems sk = new SkillItems(s,1);
            return sk;
        }
        else if (tnemele.equals("ground")){
            Skill s = new Skill("Rajaman perselingkuh",100,1);
            SkillItems sk = new SkillItems(s,1);
            return sk;
        } else {
            Skill s = new Skill("XXX",0,-1);
            SkillItems sk = new SkillItems(s,0);
            return sk;
        }
    }

    public void makeEngimon() {
        //Engimon
        OwnedEngimon temp =  new OwnedEngimon("pikachu","mammoth",3,20);
        temp.addElements("water");
        temp.addElements("ice");
        temp.setLevel(200);
        Skill s1 = new Skill("Libasan ekor keadilan",100,5);
        s1.addElementSkill("ice");
        Skill s2= new Skill("Tandukan kebenaran surgawi",100,2);
        s2.addElementSkill("ice");
        Skill s3= new Skill("Siraman rohani di hari yang indah",100,3);
        s3.addElementSkill("water");
        temp.addSkill(s1);
        temp.addSkill(s2);
        temp.addSkill(s3);
        this.playerEngimons.append(temp);

        OwnedEngimon tes1 = new OwnedEngimon("raichu","kecoa",3,20);
        tes1.addElements("water");
        tes1.addElements("ice");
        tes1.setLevel(200);
        Skill st1 = new Skill("serudukan tanduk keputusasaan",100,5);
        st1.addElementSkill("water");
        Skill st2 = new Skill("teriakan ultrasonic kemarahan",100,5);
        st2.addElementSkill("ground");
        tes1.addSkill(st1);
        tes1.addSkill(st2);
        this.playerEngimons.append(tes1);

        OwnedEngimon tes2 = new OwnedEngimon("mewtwo", "siamang",3,20);
        tes2.addElements("ground");
        tes2.addElements("electric");
        tes2.setLevel(200);
        Skill stt1 = new Skill("tembakan gelembung kebebasan",100,1);
        Skill stt2 = new Skill("cakaran cakar kematian",100,1);
        stt1.addElementSkill("fire");
        stt2.addElementSkill("electric");
        tes2.addSkill(stt1);
        tes2.addSkill(stt2);
        this.playerEngimons.append(tes2);
        //Active Engimon
    }

    public void initiateSkill() {
        //Inventory Skill Item
        Skill s1 = new Skill("Pesanan es teh manis",100,1);
        s1.addElementSkill("ice");
        Skill s2 = new Skill("Tatapan dingin mantan",100,1);
        s2.addElementSkill("ice");
        Skill s3 = new Skill("Kekuatan kencing di dalam celana",100,1);
        s3.addElementSkill("water");
        Skill s4 = new Skill("Seruputan rokok sampoerna",100,1);
        s4.addElementSkill("fire");
        Skill s5 = new Skill("Stone Holder",100,1);
        s5.addElementSkill("ground");
        Skill s6 = new Skill("PLN mati lampu",100,1);
        s6.addElementSkill("electric");
        SkillItems sk1 = new SkillItems(s1,1);
        SkillItems sk2 = new SkillItems(s2,1);
        SkillItems sk3 = new SkillItems(s3,1);
        SkillItems sk4 = new SkillItems(s4,1);
        SkillItems sk5 = new SkillItems(s5,1);
        SkillItems sk6 = new SkillItems(s6,1);
        this.playerItems.append(sk1);
        this.playerItems.append(sk2);
        this.playerItems.append(sk3);
        this.playerItems.append(sk4);
        this.playerItems.append(sk5);
        this.playerItems.append(sk6);
    }

    public Cell getEnemy(){
        int i,j;

        for (i=-1; i<=1; i++){
            for (j=-1; j<=1; j++){
                // 30 sama 0 nya blm diganti
                if (position.getX()+i < 30 && position.getX()+i > 0 && position.getY()+j < 30 && position.getY()+j > 0) {
                    if (map.getCell(position.getX()+i, position.getY()+j).getEngimon() != null
                            && map.getCell(position.getX()+i, position.getY()+j).getEngimon() != active) {
                        return map.getCell(position.getX()+i, position.getY()+j);
                    }
                }
            }
        }
        return null;
    }

//    public void executeBattle(String name){
//
//
//                Boolean menang = battle(getEnemy().getEngimon());
//            if (menang){
//                winBattle(getEnemy().getEngimon(), "Bambang");
//            }
//            else{
//                System.out.println("Lu kalah ta**i");
//            }
//
//    }

    private void readObject(ObjectInputStream ois)
        throws ClassNotFoundException, IOException {
        ois.defaultReadObject();

        //Loading player sprites
        ImageIcon img = new ImageIcon("/Users/shifa/Desktop/MapLagi/assets/PlayerFront.png");
        playerFront = img.getImage();
        img = new ImageIcon("/Users/shifa/Desktop/MapLagi/assets/PlayerLeft.png");
        playerLeft = img.getImage();
        img = new ImageIcon("/Users/shifa/Desktop/MapLagi/assets/PlayerRight.png");
        playerRight = img.getImage();
        img = new ImageIcon("/Users/shifa/Desktop/MapLagi/assets/PlayerBack.png");
        playerBack = img.getImage();
        playerActive = playerFront;
    }
}