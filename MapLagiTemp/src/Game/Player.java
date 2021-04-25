package Game;

import GUI.ConsoleOutput;
import GUI.Map;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.IOException;
import static java.lang.Math.max;
import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;

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
        ImageIcon img = new ImageIcon("/Users/darubagus/Downloads/EngimonFactory2-main/MapLagi/assets/PlayerFront.png");
        playerFront = img.getImage();
        img = new ImageIcon("/Users/darubagus/Downloads/EngimonFactory2-main/MapLagi/assets/PlayerLeft.png");
        playerLeft = img.getImage();
        img = new ImageIcon("/Users/darubagus/Downloads/EngimonFactory2-main/MapLagi/assets/PlayerRight.png");
        playerRight = img.getImage();
        img = new ImageIcon("/Users/darubagus/Downloads/EngimonFactory2-main/MapLagi/assets/PlayerBack.png");
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
            if (active != null) {
                map.getCell(active.getPosition().getX(), active.getPosition().getY()).setEngimon(null);
                active.setPosition(temp.getX(),temp.getY());
                map.getCell(active.getPosition().getX(), active.getPosition().getY()).setEngimon(active);
            }

            if (_direction == 'w') {
                setPosition(temp.getX()-1, temp.getY());
                tujuan = new Position(temp.getX()-1, temp.getY());
                setPlayerActive(getPlayerBack());
                if (active != null) {
                    active.setActiveActive(active.getActiveBack());
                }
                ;
            } else if (_direction == 'a') {
                setPosition(temp.getX(), temp.getY()-1);
                tujuan = new Position(temp.getX(), temp.getY()-1);
                setPlayerActive(getPlayerLeft());
                if (active != null){
                    active.setActiveActive(active.getActiveLeft());
                }
            } else if (_direction == 's') {
                setPosition(temp.getX()+1, temp.getY());
                tujuan = new Position(temp.getX()+1, temp.getY());
                setPlayerActive(getPlayerFront());
                if (active != null){
                    active.setActiveActive(active.getActiveFront());
                }
            } else if (_direction == 'd') {
                setPosition(temp.getX(), temp.getY()+1);
                tujuan = new Position(temp.getX(), temp.getY()+1);
                setPlayerActive(getPlayerRight());
                if (active != null) {
                    active.setActiveActive(active.getActiveRight());
                }

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

    public void changeActiveEngimon(String nama) {
        try{
            int idxEngimon = playerEngimons.indexByName(nama);
            if (active != null) {
                playerEngimons.get(idxEngimon).setPosition(active.getPosition().getX(), active.getPosition().getY());
                active.setPosition(-1,-1);
            }

            setActiveEngimon(idxEngimon);
            System.out.println("Berhasil mengganti active engimon menjadi " + active.getName());
        }
        catch (ArrayIndexOutOfBoundsException exception){
            System.out.println("Tidak ada engimon dengan nama " + nama);
        }

    }

    public void breed(OwnedEngimon father, OwnedEngimon mother, String anak) {

        //Validasi level parents
        if (father.getLevel() < 4 && mother.getLevel() < 4) {
            System.out.println("Belom cukup umur");
            return;
        }

        //Main

//        //Inherit Elements
        ArrayList<String> childElmt = new ArrayList<>(); //container buat nyimpen, nanti di add ke anaknya
        String species;
        ArrayList<Skill> childSkill = new ArrayList<>();

        //Parent elements
        String fatherElmt = father.getElements().get(0);
        String motherElmt = mother.getElements().get(0);
        double fatherAdvantage = ElementAdvantage.getAdv(fatherElmt, motherElmt);
        double motherAdvantage = ElementAdvantage.getAdv(motherElmt, fatherElmt);

        //Kasus 1 - Elemen kedua parent sama
        if (fatherElmt.equals(motherElmt)) {
            childElmt.add(fatherElmt);
        }

        //Kasus 2 - Elemen kedua parent berbeda
        else if (fatherAdvantage > motherAdvantage) {
            childElmt.add(fatherElmt);
        } else if (fatherAdvantage < motherAdvantage) {
            childElmt.add(motherElmt);
        }

        //Kasus 3 - Elemen parent berbeda dan element advantage kedua parent sama
        else {
            childElmt.add(motherElmt);
            childElmt.add(fatherElmt);
        }

        //Select species
        species = elementMap(childElmt);

        //Inherit skills;
        Boolean done = false;
        //
        ArrayList<Skill> containerSkill = new ArrayList<>();

        for (Skill s : father.getSkill()) {
            containerSkill.add(s);
        }

        for(int i = 0; i < mother.getNSkill(); i++){
            for (int j = 0; j < containerSkill.size(); j++){
                Skill tempMother = mother.getSkill().get(i);
                Skill tempContainer = containerSkill.get(j);
                if (tempMother.getSkillName().equals(tempContainer.getSkillName())){
                    if (tempMother.getMasteryLevel() == tempContainer.getMasteryLevel()){
                        if (tempContainer.getMasteryLevel() < 3){
                            int tempMasteryLevel = tempContainer.getMasteryLevel();
                            tempContainer.setMasteryLevel(tempMasteryLevel+1);
                        } else {
                            // do nothing
                        }
                    } else {
                        if (tempContainer.getMasteryLevel() < tempMother.getMasteryLevel()) {
                            int levelS = tempMother.getMasteryLevel();
                            tempContainer.setMasteryLevel(levelS);
                        }
                    }
                } else {
                    if (!isDuplicateSkill(containerSkill, tempMother.getSkillName())) {
                        containerSkill.add(tempMother);
                    }
                }
            }
        }

        containerSkill.sort(comparing(o -> ((Skill) o).getMasteryLevel(),reverseOrder()));

        // Kurangi level parent

        int tempMotherLevel = mother.getLevel();
        int tempFatherLevel = father.getLevel();
        mother.setLevel(tempMotherLevel-3);
        father.setLevel(tempFatherLevel-3);

        // Buat Engimon
        OwnedEngimon child = new OwnedEngimon(anak,species,3,1);

        // Set parent
        child.setParentName(father.getName(), mother.getName());

        // Set Element
        for (String x: childElmt){
            child.addElements(x);
        }

        // Set Skill
        for (Skill x : containerSkill){
            if (child.getNSkill() < 4){
                child.addSkill(x);
            }
        }

        this.playerEngimons.append(child);
    }

    private Boolean isDuplicateSkill(ArrayList<Skill> containerSkill, String skillName){
        Boolean duplicate = false;
        for (Skill x : containerSkill) {
            if (x.getSkillName().equals(skillName)) {
                duplicate = true;
            }
        }
        return duplicate;
    }

    private String elementMap(ArrayList<String> elements) {

        HashMap<ArrayList<String>, String> elementToSpecies = new HashMap<>();

        //Elements initialization
        ArrayList<String> fire = new ArrayList();
        fire.add("fire");

        ArrayList<String> water = new ArrayList<>();
        water.add("water");

        ArrayList<String> ground = new ArrayList<>();
        ground.add("ground");

        ArrayList<String> electric = new ArrayList<>();
        electric.add("electric");

        ArrayList<String> ice = new ArrayList<>();
        ice.add("ice");

        ArrayList<String> fireElectric = new ArrayList<>();
        fireElectric.add("fire");
        fireElectric.add("electric");

        ArrayList<String> waterIce = new ArrayList<>();
        waterIce.add("water");
        waterIce.add("Ice");

        ArrayList<String> waterGround = new ArrayList<>();
        waterGround.add("water");
        waterGround.add("ground");

        //Map initialization
        elementToSpecies.put(fire,"ikan");
        elementToSpecies.put(water,"kambing");
        elementToSpecies.put(ground,"kelelawar");
        elementToSpecies.put(electric,"beruang");
        elementToSpecies.put(ice,"kadal");
        elementToSpecies.put(fireElectric,"siamang");
        elementToSpecies.put(waterIce,"mammoth");
        elementToSpecies.put(waterGround,"kecoa");

        String species = elementToSpecies.get(elements);
        if (species != null) {
            return species;
        }
        Collections.reverse(elements);
        return elementToSpecies.get(elements);
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

    public void changeName(String oldName, String newName){
        try{
            int idxLama = playerEngimons.indexByName(oldName);
            int idxBaru = playerEngimons.indexByName(newName);
            if (idxBaru == -1){
                playerEngimons.get(idxLama).setName(newName);
                System.out.println("Berhasil mengganti nama engimon dari " + oldName + " menjadi " + newName);
            }
            else {
                System.out.println("Gagal mengganti nama karena sudah ada engimon dengan nama yang sama");
            }
        }
        catch (ArrayIndexOutOfBoundsException exception){
            System.out.println("Input nama engimon yang ingin diganti namanya salah");
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
            SkillItems sk = new SkillItems(enemy.getSkill().get(0),1);
            this.addToInventory(sk);
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
        Skill s1 = new Skill("libasan ekor keadilan",100,3);
        s1.addElementSkill("ice");
        //Skill s2= new Skill("Tandukan kebenaran surgawi",100,2);
        //s2.addElementSkill("ice");
        Skill s3= new Skill("serudukan tanduk keputusasaan",100,3);
        s3.addElementSkill("water");
        temp.addSkill(s1);
        //temp.addSkill(s2);
        temp.addSkill(s3);
        this.playerEngimons.append(temp);

        OwnedEngimon tes1 = new OwnedEngimon("raichu","kecoa",3,20);
        tes1.addElements("water");
        tes1.addElements("ice");
        tes1.setLevel(200);
        Skill st1 = new Skill("serudukan tanduk keputusasaan",100,3);
        st1.addElementSkill("water");
        Skill st2 = new Skill("teriakan ultrasonic kemarahan",100,3);
        st2.addElementSkill("ground");
        tes1.addSkill(st1);
        tes1.addSkill(st2);
        this.playerEngimons.append(tes1);

        OwnedEngimon tes2 = new OwnedEngimon("mewtwo", "siamang",1,1);
        tes2.addElements("ground");
        tes2.addElements("electric");
        tes2.setLevel(1);
        Skill stt1 = new Skill("tembakan gelembung kebebasan",5,1);
        Skill stt2 = new Skill("cakaran cakar kematian",5,1);
        stt1.addElementSkill("fire");
        stt2.addElementSkill("electric");
        tes2.addSkill(stt1);
        tes2.addSkill(stt2);
        this.playerEngimons.append(tes2);
        //Active Engimon
    }

    public void initiateSkill() {
        //Inventory Skill Item
        Skill s1 = new Skill("libasan ekor keadilan",100,1);
        s1.addElementSkill("ice");
        //Skill s2 = new Skill("Tatapan dingin mantan",100,1);
        //s2.addElementSkill("ice");
        Skill s3 = new Skill("serudukan tanduk keputusasaan",100,1);
        s3.addElementSkill("water");
        Skill s4 = new Skill("tembakan gelembung kebebasan",100,1);
        s4.addElementSkill("fire");
        Skill s5 = new Skill("teriakan ultrasonic kemarahan",100,1);
        s5.addElementSkill("ground");
        Skill s6 = new Skill("cakaran cakar kematian",100,1);
        s6.addElementSkill("electric");
        SkillItems sk1 = new SkillItems(s1,1);
        //SkillItems sk2 = new SkillItems(s2,1);
        SkillItems sk3 = new SkillItems(s3,1);
        SkillItems sk4 = new SkillItems(s4,1);
        SkillItems sk5 = new SkillItems(s5,1);
        SkillItems sk6 = new SkillItems(s6,1);
        this.playerItems.append(sk1);
        //this.playerItems.append(sk2);
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
        ImageIcon img = new ImageIcon("/Users/darubagus/Downloads/EngimonFactory2-main/MapLagi/assets/PlayerFront.png");
        playerFront = img.getImage();
        img = new ImageIcon("/Users/darubagus/Downloads/EngimonFactory2-main/MapLagi/assets/PlayerLeft.png");
        playerLeft = img.getImage();
        img = new ImageIcon("/Users/darubagus/Downloads/EngimonFactory2-main/MapLagi/assets/PlayerRight.png");
        playerRight = img.getImage();
        img = new ImageIcon("/Users/darubagus/Downloads/EngimonFactory2-main/MapLagi/assets/PlayerBack.png");
        playerBack = img.getImage();
        playerActive = playerFront;
    }
}