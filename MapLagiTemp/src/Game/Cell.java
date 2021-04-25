package Game;

import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.IOException;
import javax.swing.*;
import java.awt.*;


public class Cell implements Serializable {

    //Fields
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

    //Aksesor
    public int getX() { return x; }
    public int getY() { return y; }
    public CellType getType() { return type; }
    public Player getPlayer() { return occupierP; }
    public Engimon getEngimon() { return occupierE; }

    public void setPlayer(Player p) { occupierP = p; }
    public void setEngimon(Engimon e) { occupierE = e; }
    public void setCellTile(char _char) {
        this.cellCode = _char;
        ImageIcon img;
        switch (_char) {
            case 'f':
                img =  new ImageIcon("/Users/darubagus/Downloads/EngimonFactory2-main/MapLagi/assets/Flower.png");
                cellTile = img.getImage();
                break;
            case 'b':
                img =  new ImageIcon("/Users/darubagus/Downloads/EngimonFactory2-main/MapLagi/assets/Berg.png");
                cellTile = img.getImage();
                break;
            case 'g':
                img =  new ImageIcon("/Users/darubagus/Downloads/EngimonFactory2-main/MapLagi/assets/Grass.png");
                cellTile = img.getImage();
                break;
            case 'i':
                img =  new ImageIcon("/Users/darubagus/Downloads/EngimonFactory2-main/MapLagi/assets/Ice.png");
                cellTile = img.getImage();
                break;
            case 'n':
                img =  new ImageIcon("/Users/darubagus/Downloads/EngimonFactory2-main/MapLagi/assets/IceN.png");
                cellTile = img.getImage();
                break;
            case 't':
                img =  new ImageIcon("/Users/darubagus/Downloads/EngimonFactory2-main/MapLagi/assets/IceT.png");
                cellTile = img.getImage();
                break;
            case 'o':
                img =  new ImageIcon("/Users/darubagus/Downloads/EngimonFactory2-main/MapLagi/assets/IceO.png");
                cellTile = img.getImage();
                break;
            case 's':
                img =  new ImageIcon("/Users/darubagus/Downloads/EngimonFactory2-main/MapLagi/assets/Sea.png");
                cellTile = img.getImage();
                break;
            case 'a':
                img =  new ImageIcon("/Users/darubagus/Downloads/EngimonFactory2-main/MapLagi/assets/MountainA.png");
                cellTile = img.getImage();
                break;
            case 'e':
                img =  new ImageIcon("/Users/darubagus/Downloads/EngimonFactory2-main/MapLagi/assets/MountainB.png");
                cellTile = img.getImage();
                break;
            case 'c':
                img =  new ImageIcon("/Users/darubagus/Downloads/EngimonFactory2-main/MapLagi/assets/MountainC.png");
                cellTile = img.getImage();
                break;
            case 'd':
                img =  new ImageIcon("/Users/darubagus/Downloads/EngimonFactory2-main/MapLagi/assets/MountainD.png");
                cellTile = img.getImage();
                break;
            case 'h':
                img =  new ImageIcon("/Users/darubagus/Downloads/EngimonFactory2-main/MapLagi/assets/MountainH.png");
                cellTile = img.getImage();
                break;
            default:
                break;
        }
    }

    //Methods
    private CellType mapChar(char _char) {
        //Memetakan CellType berdasarkan karakter pada map.txt
        switch (_char) {
            case 'f':
            case 'b':
                return CellType.WALL;
            case 'g':
                return CellType.GRASSLAND;
            case 'i':
            case 'n':
            case 't':
            case 'o':
                return CellType.TUNDRA;
            case 's':
                return CellType.SEA;
            case 'm':
            case 'a':
            case 'e':
            case 'c':
            case 'd':
            case 'h':
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
