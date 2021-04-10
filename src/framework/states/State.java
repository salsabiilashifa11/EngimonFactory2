package framework.states;
import java.awt.Graphics;

public abstract class State {

    protected StateManager stateManager;

    protected State(StateManager manager) {
        stateManager = manager;
    }

    protected abstract void loop();
    protected abstract void render(Graphics graphics);
    protected abstract void keyPressed(int key);
    protected abstract void keyReleased(int key);

}
