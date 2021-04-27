package Main;

import GUI.Board;
import GUI.ConsoleInput;
import GUI.ConsoleOutput;
import GUI.HUD;
import Game.MainGame;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        new Main();
    }

    //TextField
    public static ConsoleInput consoleInput;
    public static Board board;
    public static ConsoleOutput consoleOutput;
    public static HUD hud;
    public static JPanel extra, extra2;

    public Main() {

        //Fields
        String load = JOptionPane.showInputDialog("Apakah anda ingin load game? (Y/N)");
        if (load.equals("Y") || load.equals("y")) {
            String fname = JOptionPane.showInputDialog("Masukkan nama savefile:");
            FileInputStream fileInputStream;
            try {
                fileInputStream
                        = new FileInputStream(fname);
                ObjectInputStream objectInputStream
                        = new ObjectInputStream(fileInputStream);
                board = (Board) objectInputStream.readObject();
                objectInputStream.close();
            } catch(FileNotFoundException e) {
                JOptionPane.showMessageDialog(null,
                        "File tidak ditemukan, akan dimulai game baru");
                board = new Board();
            } catch (ClassNotFoundException | IOException e) {
                JOptionPane.showMessageDialog(null,
                        "Load file gagal, akan dimulai game baru");
                board = new Board();
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Akan dimulai game baru");
            board = new Board();
        }
        board.setBounds(20,20,640,658);

        consoleOutput = new ConsoleOutput(board);
        consoleOutput.setBounds(660,20,460,612);

        consoleInput = new ConsoleInput(board, consoleOutput);
        consoleInput.setBounds(660, 632, 460, 218);

        hud = new HUD(board);
        hud.setBounds(20, 678, 640, 110);

        extra = new JPanel();
        extra.setBounds(0, 0, 20, 790);
        extra.setBackground(Color.decode("#f2f2f2"));

        extra2 = new JPanel();
        extra.setBounds(0, 0, 1120, 20);
        extra.setBackground(Color.decode("#f2f2f2"));

        JFrame frame = new JFrame();
        frame.setLayout(null);
        frame.setTitle("Engimon 2");
        frame.add(board);
        frame.add(consoleInput);
        frame.add(consoleOutput);
        frame.add(hud);
        frame.add(extra);
        frame.setSize(1120,790); //adjust later header size = 28px
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.decode("#f2f2f2"));

        MainGame game = new MainGame(consoleOutput, consoleInput, board);

    }

}
