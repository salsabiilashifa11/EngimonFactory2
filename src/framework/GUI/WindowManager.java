package framework.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

public class WindowManager {

    private JFrame jframe;
    private JPanel jpanel;
    public static final int HEIGHT = 600;
    public static final int WIDTH = 600;

    public WindowManager() {
        jframe = new JFrame("Engimon Factory");
        jframe.setBounds(70, 70, 0, 0);
        jframe.setResizable(false);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void addPanel(JPanel panel) {
        jpanel = panel;
        this.jpanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.jpanel.setFocusable(true);
        this.jpanel.requestFocusInWindow();
    }

    public void addKeyListener(KeyListener keyListener) {
        try {
            jpanel.addKeyListener(keyListener);
        } catch (NullPointerException e) {
            System.err.println("Exception: No panel found");
            System.exit(-1);
        }
    }

    public void createWindow() {
        jframe.setContentPane(jpanel);
        jframe.pack();
        jframe.setVisible(true);
    }
}
