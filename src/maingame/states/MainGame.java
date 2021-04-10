package maingame.states;

import framework.Res.Resource;
import framework.states.State;
import framework.states.StateManager;

import java.awt.*;

public class MainGame extends State {

    protected MainGame(StateManager stateManager) {
        super(stateManager);
    }

    @Override
    protected void loop() {

    }

    @Override
    protected void render(Graphics graphics) {
        graphics.drawImage(Resource.TEXTURES.get(0), 0, 0, 16, 16, null);
    }

    @Override
    protected void keyPressed(int key) {

    }

    @Override
    protected void keyReleased(int key) {

    }
}
