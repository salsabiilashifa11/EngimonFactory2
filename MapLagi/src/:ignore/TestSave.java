package Main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import Game.*;

public class TestSave {
    public static void main(String[] args) throws Exception {
        Skill testskill = new Skill("skilll", 10, 3);
        SkillItems testSI = new SkillItems(testskill, 10);
        Inventory<SkillItems> testInvSI = new Inventory<>();
        testInvSI.append(testSI);

        FileOutputStream fileOutputStream
                = new FileOutputStream("test.txt");
        ObjectOutputStream objectOutputStream
                = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(testskill);
        objectOutputStream.writeObject(testSI);
        objectOutputStream.writeObject(testInvSI);
        objectOutputStream.flush();
        objectOutputStream.close();

        FileInputStream fileInputStream
                = new FileInputStream("test.txt");
        ObjectInputStream objectInputStream
                = new ObjectInputStream(fileInputStream);
        Skill bacaskill = (Skill) objectInputStream.readObject();
        SkillItems bacaSI = (Skill) objectInputStream.readObject();
        Map bacaInv = (Map) objectInputStream.readObject();
        objectInputStream.close();

    }
}
