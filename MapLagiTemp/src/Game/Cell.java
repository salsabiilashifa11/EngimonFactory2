package Game;

import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.IOException;
import javax.swing.*;
import java.awt.*;


public class Cell implements Serializable {
    //Atribut
    private int x;
    private int y;
    private CellType type;
    private Player occupierP;
    private Engimon occupierE;
    //For GUI
    private transient Image cellTile;
    private char cellCode;

    //Constructor
    public Cell(int _x, int _y, char _char) {
        //Initializing all fields
        x = _x;
        y = _y;
        type = mapChar(_char);
        setCellTile(_char);
        occupierP = null;
        occupierE = null;
    }

    //Getter
    public int getX() { return x; }
    public int getY() { return y; }
    public CellType getType() { return type; }
    public Player getPlayer() { return occupierP; }
    public Engimon getEngimon() { return occupierE; }

    //Setter
    public void setPlayer(Player p) { occupierP = p; }
    public void setEngimon(Engimon e) { occupierE = e; }
    public void setCellTile(char _char) {
        this.cellCode = _char;
        ImageIcon img;
        switch (_char) {
            case 't':
                img =  new ImageIcon("assets/tiles/tree.png");
                cellTile = img.getImage();
                break;
            case 'g':
                img =  new ImageIcon("assets/tiles/grass.png");
                cellTile = img.getImage();
                break;
            case 'l':
                img =  new ImageIcon("assets/tiles/iceBACK_wall.png");
                cellTile = img.getImage();
                break;
            case 'L':
                img =  new ImageIcon("assets/tiles/iceUP_wall.png");
                cellTile = img.getImage();
                break;
            case 'b':
                img =  new ImageIcon("assets/tiles/iceBRIDGE.png");
                cellTile = img.getImage();
                break;
            case 'd':
                img =  new ImageIcon("assets/tiles/iceDOWN.png");
                cellTile = img.getImage();
                break;
            case 'h':
                img =  new ImageIcon("assets/tiles/iceHOUSE.png");
                cellTile = img.getImage();
                break;
            case 'c':
                img =  new ImageIcon("assets/tiles/iceROCK.png");
                cellTile = img.getImage();
                break;

            case 'u':
                img =  new ImageIcon("assets/tiles/iceUP.png");
                cellTile = img.getImage();
                break;
            case 'k':
                img =  new ImageIcon("assets/tiles/iceBACK.png");
                cellTile = img.getImage();
                break;
            case 'o':
                img =  new ImageIcon("assets/tiles/orange.png");
                cellTile = img.getImage();
                break;
            case 'n':
                img =  new ImageIcon("assets/tiles/snowman.png");
                cellTile = img.getImage();
                break;
            case 's':
                img =  new ImageIcon("assets/tiles/sea.png");
                cellTile = img.getImage();
                break;
            case 'w':
                img =  new ImageIcon("assets/tiles/wave.png");
                cellTile = img.getImage();
                break;
            case 'a':
                img =  new ImageIcon("assets/tiles/waveROCK.png");
                cellTile = img.getImage();
                break;
            case 'y':
                img =  new ImageIcon("assets/tiles/yellow.png");
                cellTile = img.getImage();
                break;
            case 'e':
                img =  new ImageIcon("assets/tiles/yellowROCK.png");
                cellTile = img.getImage();
                break;
            case 'r':
                img =  new ImageIcon("assets/tiles/brown.png");
                cellTile = img.getImage();
                break;
            default:
                break;
        }
    }

    //Fungsi Tambahan
    private CellType mapChar(char _char) {
        //Memetakan CellType berdasarkan karakter pada map.txt
        switch (_char) {
            case 't':
            case 'l':
            case 'L':
            case 'd':
                return CellType.WALL;
            case 'g':
                return CellType.GRASSLAND;
            case 'b':
            case 'h':
            case 'c':
            case 'u':
            case 'n':
            case 'k':
                return CellType.TUNDRA;
            case 's':
            case 'w':
            case 'a':
                return CellType.SEA;
            case 'o':
            case 'y':
            case 'e':
            case 'r':
                return CellType.MOUNTAIN;
            default:
                return null;
        }
    }

    //GUI
    public void drawCell(Graphics g) {
        g.drawImage(cellTile, x * 32, y * 32, null);
    }

    private void readObject(ObjectInputStream ois)
        throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        setCellTile(this.cellCode);
    }
}
