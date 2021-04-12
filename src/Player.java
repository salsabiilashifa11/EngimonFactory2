

public class Player {
//    Fields
    private String name;
    private Position playerPos;
//    private OwnedEngimon active;
//    private Inventory<OwnedEngimon> playerEngimons;
//    private Inventory<SkillItems> playerItems;

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

    }

//    public void breed(Engimon father, Engimon mother){
//
//    }

//    public void battle(Engimon enemy){
//
//    }

    public void teach(String skillName, String eName){

    }

//    Display
    public void displayEngimon(String _name){

    }

    public void executeBreed(){

    }

//    Getter Setter
//    public OwnedEngimon getActiveEngimon(){
//
//    }

    public String getName(){
        return name;
    }

    public Position getPosition(){
        return playerPos;
    }

    public void setActiveEngimon(int _index){

    }

    public void activeMati(){

    }

    public void setPosition(int _x, int _y){
        playerPos.setX(_x);
        playerPos.setY(_y);
    }

//    public void addToInventory(OwnedEngimon el){
//
//    }

//    public void addToInventory(SkillItems el){
//
//    }

    public void showInventory(){

    }

    public void showItems(){

    }

    public void showEngimons(){

    }

    public void useSkillItems(){

    }

//    public SkillItems generateSkillItem(string _skill){
//
//    }

}
