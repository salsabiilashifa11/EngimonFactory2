package GUI;

import Game.Cell;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Scanner;

public class Map implements Serializable {
    //Atribut
    private transient Scanner m; //File textnya
    private String chars[] = new String[20]; //String input per line dari m
    private Cell matrix[][] = new Cell[20][20]; //Map matrix

    //Konstruktor
    public Map() {
        openFile();
        readFile();
        closeFile();
        chars2Map();
    }

    //Fungsi Tambahan
    public Cell getCell(int x, int y) {
        //This basically gets a tile from map in position x,y
        return matrix[x][y];
    }

    public void openFile() {
        try {
            m = new Scanner(new File("map/map.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("Error loading map");
        }
    }

    public void readFile() {
        while (m.hasNext()) {
            for (int i = 0; i < 20; i++) {
                chars[i] = m.next();
            }
        }
    }

    public void closeFile() {
        m.close();
    }

    public void chars2Map() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                Cell tile = new Cell(j, i, chars[i].substring(j, j+1).charAt(0));
                matrix[i][j] = tile;
            }
        }
    }
}
