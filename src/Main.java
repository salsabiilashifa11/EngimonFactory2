import framework.GUIManager;
import framework.Res.Loader;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Loader.load();
                GUIManager.init();
                GUIManager.start();
            }
        });
    }
}
