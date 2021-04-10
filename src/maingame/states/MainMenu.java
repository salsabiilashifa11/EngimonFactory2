package maingame.states;

import framework.states.State;
import framework.states.StateManager;
import framework.GUI.WindowManager;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MainMenu extends State {

    //Constants
    private static final String NEW_GAME = "New Game";
    private static final String LOAD_GAME = "Load Game";
    private static final String QUIT = "Quit";

    private String[] options;
    private int selected;

    public MainMenu(StateManager manager) {
        super(manager);
        options = new String[] {NEW_GAME, LOAD_GAME, QUIT};
        selected =  0;
    }


    @Override
    protected void loop() {

    }

    @Override
    protected void render(Graphics graphics) {
        graphics.setColor(new Color(245, 179, 66));
        graphics.fillRect(0, 0, WindowManager.WIDTH, WindowManager.HEIGHT);

        for (int i = 0; i < options.length; i++) {
            if (i == selected) {
                graphics.setColor(Color.BLUE);
            } else {
                graphics.setColor(Color.WHITE);
            }

            graphics.drawString(options[i], WindowManager.WIDTH/2, 50 + i * 20);
        }
    }

    @Override
    protected void keyPressed(int key) {
        switch (key) {
            case KeyEvent.VK_UP:
                if (selected > 0) {
                    selected -= 1;
                }
                break;
            case KeyEvent.VK_DOWN:
                if (selected < options.length-1) {
                    selected += 1;
                }
                break;
            case KeyEvent.VK_ENTER:
                switch (options[selected]) {
                    case NEW_GAME:
                        super.stateManager.pushStack(new MainGame(stateManager));
                        break;
                    case LOAD_GAME:
                        System.out.println("Belom dibikin");
                        break;
                    case QUIT:
                        System.exit(0);
                        break;
                }
                break;
        }

    }

    @Override
    protected void keyReleased(int key) {

    }
}
