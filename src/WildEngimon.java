import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WildEngimon extends Engimon{
    private String status;
    private Position position;

    private Integer element2int(String element) {
        if (element == "fire" || element == "ground" || element == "electric" ){
            return 1;
        } else {
            return 2;
        }
    }

    private static final Map<String, String> spesiesSkill;
    static {
        HashMap<String, String> temp = new HashMap<String, String>();
        temp.put("kadal","libasan ekor keadilan");
        temp.put("ikan","tembakan gelembung kebebasan");
        temp.put("kambing", "serudukan tanduk keputusasaan");
        temp.put("beruang", "cakaran cakar kematian");
        temp.put("kelelawar", "teriakan ultrasonic kemarahan");

        spesiesSkill = Collections.unmodifiableMap(temp);
    }

    public WildEngimon() {
        super();
        this.status = "wild";
    }

    public WildEngimon(String name, String species, int life, int level){
        super(name, species, life, level);
        this.status = "wild";
    }

    public WildEngimon(String _species, String _element, int _level, int _x, int _y, Map _m){
        this.name = _species;
        this.species = _species;
        String namaSkill = spesiesSkill.get(this.species);
        int basePower = 0;
        int masteryLevel = 0;

        if (species == "kadal"){
            basePower = 100;
            masteryLevel = 3;
        }
        else if (species == "ikan"){
            basePower = 110;
            masteryLevel = 2;
        }
        else if (species == "kambing"){
            basePower = 120;
            masteryLevel = 4;
        }
        else if (species == "beruang"){
            basePower = 80;
            masteryLevel = 2;
        }
        else if (species == "kelelawar"){
            basePower = 90;
            masteryLevel = 5;
        }

        Skill s = new Skill(namaSkill,basePower, masteryLevel);
        this.elements.set(0,_element);
        //this->nElements = 1; //auto ter-set 1
        s.addElementSkill(this.elements.get(0));
        //this.skills.
        //this->nSkill = 0; //butuh setter
        this.addSkill(s);
        this.level = _level;
        this.experience = 0;
        this.cumulativeExperience = this.level*100;
        this.status = "wild";
        //setPosition(x, y, m);
    }

    public String getStatus(){
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(int _x, int _y, Map m) {
        this.position.setX(_x);
        this.position.setY(_y);
    }

    public void displayDetail() {
        System.out.println("======================Info Musuh======================");
        System.out.println("Nama           : " + this.name);
        System.out.println("Species        : " + this.species);
        System.out.println("Skill          : ");

        for (int i = 0; i< this.skills.size(); i++){
            System.out.println("    " + i+1 + ". ");
            this.skills.get(i).displaySkill();
            System.out.println();
        }

        System.out.println("Element        : " + this.elements.get(0));
        System.out.println("Level          : " + this.level);
        // cout << "----------------------------------------------" << endl;
    }

//    public void assertPosition(Map m) {
//
//        bool validPosition = false;
//        int prevX = position.getX();
//        int prevY = position.getY();
//
//        if (m->getCell(position.getX(), position.getY()).getOccupierP().getName() != "" ||
//                m->getCell(position.getX(), position.getY()).getOccupierE()->getName() != "XXX") {
//            while (!validPosition) {
//                Move(m);
//                if (position.getX() != prevX || position.getY() != prevY) {
//                    validPosition = true;
//                }
//            }
//        }
//
//        m->getCell(position.getX(), position.getY()).setEngimon(this);
//    }

//    void WildEngimon::Move(Map* m){
//        int x = position.getX();
//        int y = position.getY();
//        int number;
//        number = rand() % 4 + 1;
//        Engimon* temp = new WildEngimon();
//
//        if (number == 1){
//            if (validPosition(m, x, y+1)){
//                m->getCell(x,y).setEngimon(temp);
//                setPosition(x, y+1, m);
//            }
//        } else if (number == 2){
//            if (validPosition(m, x, y-1)){
//                m->getCell(x,y).setEngimon(temp);
//                setPosition(x, y-1, m);
//            }
//        } else if (number == 3){
//            if (validPosition(m, x+1, y)){
//                m->getCell(x,y).setEngimon(temp);
//                setPosition(x+1, y, m);
//            }
//        } else {
//            if (validPosition(m, x-1, y)){
//                m->getCell(x,y).setEngimon(temp);
//                setPosition(x-1, y, m);
//            }
//        }
//
//    }
//
//    bool WildEngimon::validPosition(Map* m, int x, int y) {
//        CellType Wtype;
//        Cell cell =  m->getCell(x,y);
//        switch(element2int(getElements()[0])){
//            case 1:
//                Wtype = Grassland;
//                break;
//            case 2:
//                Wtype = Sea;
//                break;
//            default:
//                break;
//        }
//        if (x < 1  || x >= 29 ||
//                y < 1 || y >=29 ||
//                Wtype != cell.getType() ||
//                cell.getOccupierE()->getName() != "XXX" ||
//                cell.getOccupierP().getName() != "")
//        {
//            return false;
//        }
//        return true;
//    }


}
