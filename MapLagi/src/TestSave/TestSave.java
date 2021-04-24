package TestSave;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import GUI.Board;

public class TestSave {

    public static void main(String[] args) throws Exception {
        Board testboard = new Board();
        testboard.getPlayer().showInventory();

        FileOutputStream fileOutputStream
                = new FileOutputStream("test.txt");
        ObjectOutputStream objectOutputStream
                = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(testboard);
        objectOutputStream.flush();
        objectOutputStream.close();

        FileInputStream fileInputStream
                = new FileInputStream("test.txt");
        ObjectInputStream objectInputStream
                = new ObjectInputStream(fileInputStream);
        Board p2 = (Board) objectInputStream.readObject();
        objectInputStream.close();
        p2.getPlayer().showInventory();
    }
}
