package framework.Res;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Loader {

    public static void load() {
        try {
            Resource.TEXTURES.add(Resource.LAND, ImageIO.read(new File("res/textures/Border.png")));
            Resource.TEXTURES.add(Resource.GRASS, ImageIO.read(new File("res/textures/Border.png")));
            Resource.TEXTURES.add(Resource.SEA, ImageIO.read(new File("res/textures/Border.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
