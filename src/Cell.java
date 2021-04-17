public class Cell {
    private int x;
    private int y;
    private CellType type;
    private Player occupierP;
    private Engimon occupierE;

    // Constructor
    public Cell(){
        x = -1;
        y = -1;
        type = CellType.Grassland;
        occupierE = new WildEngimon();
    }
    public Cell(int _x, int _y, CellType _type){
        x = _x;
        y = _y;
        type = _type;
        occupierE = new WildEngimon();
    }

    //Getter dan Setter
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public CellType getType(){
        return type;
    }
    public Player getOccupierP(){
        return occupierP;
    }
    public Engimon getOccupierE(){
        return occupierE;
    }

    //Setter
    public void setPlayer(Player p){
        occupierP = p;
    }
    public void setEngimon(Engimon e){
        occupierE = e;
    }

    //Method
    //void drawRandom(int input);
    public void drawCell(){
        //Condition 1: player
        if (occupierP.getName() != "") {
            System.out.print("P");
        }
        //Condition 2: Alive engimons
        else if (occupierE.getStatus() != "dead" && occupierE.getName() != "XXX") {
            drawEngimon(30);
        }
        //Condition 3: dummy occupiers
        else {
            if (type == CellType.Sea) {
                System.out.print("o");
            } else {
                System.out.print("-");
            }
        }
    }
    public void drawEngimon(int level_min){
        String element = occupierE.getElements().get(0);
        int level = occupierE.getLevel();

        if (occupierE.getStatus() == "owned") {
            System.out.print("&");
        } else if (element == "fire"){
            if (level < level_min){
                System.out.print("f");
            } else {
                System.out.print("F");
            }
        } else if (element == "ground"){
            if (level < level_min) {
                System.out.print("g");
            } else {
                System.out.print("G");
            }

        } else if (element == "electric"){
            if (level < level_min){
                System.out.print("e");
            } else {
                System.out.print("E");
            }
        } else if (element == "water") {
            if (level < level_min){
                System.out.print("w");
            } else {
                System.out.print("W");
            }
        } else {
            if (level < level_min){
                System.out.print("i");
            } else {
                System.out.print("I");
            }
        }
    }

    public void printInfo(){
        System.out.println("x        : "+x);
        System.out.println("y        : "+y);
        System.out.println("type     : "+type);
    }

}
