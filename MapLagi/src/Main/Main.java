package Main;

import GUI.Board;
import GUI.ConsoleInput;
import GUI.ConsoleOutput;
import Game.TestMainGame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        new Main();
    }

    //TextField
    public static ConsoleInput consoleInput;
    public static Board board;
    public static ConsoleOutput consoleOutput;

    public Main() {
        //Fields
        board = new Board();
        board.setBounds(0,0,640,668);

        consoleOutput = new ConsoleOutput(board);
        consoleOutput.setBounds(640,0,360,334);

        consoleInput = new ConsoleInput(board, consoleOutput);
        consoleInput.setBounds(640, 334, 360, 334);

        JFrame frame = new JFrame();
        frame.setLayout(null);
        frame.setTitle("Engimon 2");
        frame.add(board);
        frame.add(consoleInput);
        frame.add(consoleOutput);
        frame.setSize(1000,668); //adjust later header size = 28px
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TestMainGame game = new TestMainGame(consoleOutput, consoleInput, board);
    }

}
