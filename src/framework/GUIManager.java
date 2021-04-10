package framework;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;


import framework.GUI.WindowManager;
import framework.states.StateManager;
import maingame.states.MainMenu;

public class GUIManager {

    private static StateManager stateManager;

    private static WindowManager windowManager;
    private static Timer timer;

    public static void init() {
        stateManager = new StateManager();
        windowManager = new WindowManager();
        timer = new Timer(20, new MainGameLoop());
    }

    public static void start() {
        stateManager.pushStack(new MainMenu(stateManager));
        windowManager.addPanel(new GameScreen());
        windowManager.addKeyListener(new Keyboard());
        windowManager.createWindow();
        timer.start();
    }

    private static class MainGameLoop implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            stateManager.loop();
        }

    }

    private static class GameScreen extends JPanel {

        private static final long serialVersionUID = 1L;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            stateManager.render(g);
            repaint();
        }
    }

    private static class Keyboard implements KeyListener {

        @Override
        public void keyPressed(KeyEvent key) {
            stateManager.keyPressed(key.getKeyCode());
        }

        @Override
        public void keyReleased(KeyEvent key) {
            stateManager.keyReleased(key.getKeyCode());
        }

        @Override
        public void keyTyped(KeyEvent arg0) {}

    }
}
