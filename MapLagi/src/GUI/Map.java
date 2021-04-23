package GUI;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Map {

    //Fields
    private Scanner m;
    private String Map[] = new String[14];

    private Image grass, sea, flower;

    //Constructor
    public Map() {

        ImageIcon img = new ImageIcon("/Users/shifa/Desktop/MapLagi/assets/Grass.png");
        grass = img.getImage();
        img = new ImageIcon("/Users/shifa/Desktop/MapLagi/assets/Sea.png");
        sea = img.getImage();
        img = new ImageIcon("/Users/shifa/Desktop/MapLagi/assets/Flower.png");
        flower = img.getImage();

        openFile();
        readFile();
        closeFile();
    }

    //Methods
    public Image getSea() {
        return sea;
    }
    public Image getGrass() {
        return grass;
    }
    public Image getFlower() {
        return flower;
    }

    public String getMap(int x, int y) {
        //This basically gets a tile from map in position x,y
        String index = Map[y].substring(x, x+1);
        return index;
    }

    public void openFile() {

        try {
            m = new Scanner(new File("/Users/shifa/Desktop/MapLagi/map/map.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("Error loading map");
        }
    }

    public void readFile() {
        while (m.hasNext()) {
            for (int i = 0; i < 14; i++) {
                Map[i] = m.next();
            }
        }
    }

    public void closeFile() {
        m.close();
    }
}
