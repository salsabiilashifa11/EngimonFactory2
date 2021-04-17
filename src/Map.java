import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Map {
    private Cell[][] map = new Cell[30][30];
    private int width;
    private int height;
    private String filename;
    private Player player;

    public Map(String _filename, Player _player) {
        File file = new File(_filename);
    }

    public Cell getCell(int x, int y){
        return map[x][y];
    }

    public Player getPlayer(){
        return player;
    }
    
    public void drawMap() {
        for (int i = 0; i < height; i++){
            for (int j = 0 ; j < width; j++){
                map[i][j].drawCell();
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public CellType getType(char c){
        CellType type = CellType.Grassland;
        switch (c) {
            case '-':
            case 'F':
            case 'f':
                type = CellType.Mountains;
                break;
            case 'G':
            case 'g':
            case 'E':
            case 'e':
                type = CellType.Grassland;
                break;
            case 'L':
            case 'l':
            case 'N':
            case 'n':
                type = CellType.Grassland;
                break;
            case 'o':
            case 'W':
            case 'w':
                type = CellType.Sea;
                break;
            case 'I':
            case 'i':
            case 'S':
            case 's':
                type = CellType.Tundra;
                break;
            default:
                break;
        }
        return type;
    }

    public void updatePlayer(){
        Player tempP = player;
        Engimon tempE = new WildEngimon();

        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                map[i][j].setPlayer(tempP);
                map[i][j].setEngimon(tempE);
            }
        }

        map[player.getPosition().getX()][player.getPosition().getY()].setPlayer(player);
        map[player.getActiveEngimon().getPosition().getX()][player.getActiveEngimon().getPosition().getY()]
                .setEngimon(player.getActiveEngimon());
    }

    public Cell getEnemy() {
        int i,j,a,b;
        Position posisiPlayer = this.getPlayer().getPosition();
        for (i=-1;i<=1;i++){
            for (j=-1;j<=1;j++){
                if (posisiPlayer.getX()+i < 30 && posisiPlayer.getX()+i > 0  && posisiPlayer.getY()+j < 30 && posisiPlayer.getY()+j > 0){
                    if (map[posisiPlayer.getX()+i][posisiPlayer.getY()+j].getOccupierE().getName() != "XXX"
                            && map[posisiPlayer.getX()+i][posisiPlayer.getY()+j].getOccupierE().getStatus() == "wild"){
                        return map[posisiPlayer.getX()+i][posisiPlayer.getY()+j];
                    }
                }
            }
        }
        return map[posisiPlayer.getX()][posisiPlayer.getY()];
    }
}
