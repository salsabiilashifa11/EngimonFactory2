package Main;

import GUI.Board;
import GUI.ConsoleInput;
import GUI.ConsoleOutput;
import GUI.HUD;
import Game.TestMainGame;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
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
                JOptionPane.showMessageDialog(null, "File tidak ditemukan, akan dimulai game baru");
                board = new Board();
            } catch (ClassNotFoundException | IOException e) {
                JOptionPane.showMessageDialog(null, "Load file gagal, akan dimulai game baru");
                board = new Board();
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Akan dimulai game baru");
            board = new Board();
        }
        board.setBounds(0,0,640,668);

        consoleOutput = new ConsoleOutput(board);
        consoleOutput.setBounds(640,0,460,450);

        consoleInput = new ConsoleInput(board, consoleOutput);
        consoleInput.setBounds(640, 450, 460, 218);

        hud = new HUD(board);
        hud.setBounds(0, 668, 640, 110);

        JFrame frame = new JFrame();
        frame.setLayout(null);
        frame.setTitle("Engimon 2");
        frame.add(board);
        frame.add(consoleInput);
        frame.add(consoleOutput);
        frame.add(hud);
        frame.setSize(1100,790); //adjust later header size = 28px
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TestMainGame game = new TestMainGame(consoleOutput, consoleInput, board);

    }

}
