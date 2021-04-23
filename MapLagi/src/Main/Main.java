package Main;

import GUI.Board;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        JFrame frame = new JFrame();
        frame.setTitle("Engimon 2");
        frame.add(new Board());
        frame.setSize(448,476); //adjust later header size = 28px
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
