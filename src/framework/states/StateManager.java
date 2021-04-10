package framework.states;
import java.awt.*;
import java.util.*;

public class StateManager {

    private Stack<State> stateStack;

    public StateManager() {
        stateStack = new Stack<>();
    }

    public void pushStack(State state) {
        stateStack.add(state);
    }

    public void loop() {
        try {
            stateStack.peek().loop();
        } catch (EmptyStackException e) {
            System.err.println("Exception: Empty stack");
            System.exit(-1);
        }
    }

    public void render(Graphics graphics) {
        try {
            stateStack.peek().render(graphics);
        } catch (EmptyStackException e) {
            System.err.println("Exception: Empty stack");
            System.exit(-1);
        }
    }

    public void keyPressed(int key) {
        try {
            stateStack.peek().keyPressed(key);
        } catch (EmptyStackException e) {
            System.err.println("Exception: Empty stack");
            System.exit(-1);
        }
    }

    public void keyReleased(int key) {
        try {
            stateStack.peek().keyReleased(key);
        } catch (EmptyStackException e) {
            System.err.println("Exception: Empty stack");
            System.exit(-1);
        }
    }
}
