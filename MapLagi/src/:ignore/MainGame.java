package Game;

import GUI.Board;
import GUI.ConsoleOutput;
import GUI.ConsoleInput;
import GUI.Map;
import java.util.ArrayList;
import java.util.Scanner;

public class MainGame {
    //Fields
    ConsoleOutput consoleOutput;
    ConsoleInput consoleInput;
    Board board;

    //Constructor
    public MainGame(ConsoleOutput _consoleOutput, ConsoleInput _consoleInput,
                    Board _board) {
        consoleOutput = _consoleOutput;
        consoleInput = _consoleInput;
        board = _board;

//        engimonInit(10); //generate 10 random engimon di awal
        mainLoop();
    }

    public void mainLoop(){

        int jumlahEngimon, jumlahEngimonAir, jumlahEngimonGrassland, jumlahEngimonTundra, jumlahEngimonMountain;
        Map m = new Map();
        Player p = new Player("haji dadang", m);

        // Minta input jumlah engimon
        jumlahEngimon = 30;
        jumlahEngimonAir = jumlahEngimon/4;
        jumlahEngimonTundra = jumlahEngimon/4;
        jumlahEngimonMountain = jumlahEngimon/4;
        jumlahEngimonGrassland = jumlahEngimon - (jumlahEngimonAir + jumlahEngimonTundra + jumlahEngimonMountain);

        //Random Engimon


        // gameLoop
        Boolean jalan = true;
        while (jalan) {
            try {
                String command = consoleInput.getLastInput();
                System.out.println("Type 'help' to see available commands");
                p.getPosition().print();
                // input command
                switch (command) {
                    case ("quit"):
                        jalan = false;
                    case ("display"):
                        p.showInventory();
                    case ("battle"):

                        p.executeBattle();
                    case ("breed"):
                        if (p.getPlayerEngimons().n_elmt() + p.getPlayerItems().n_elmt() < 15) {
                            for (int i = 0; i < p.getPlayerEngimons().n_elmt(); i++) {
                                if (p.getPlayerEngimons().get(i).getLevel() >= 4) {
                                    System.out.println(i + 1 + ". " + p.getPlayerEngimons().get(i).getName() + " ");
                                }
                            }
                            // minta input nama dari text field
                            // nama abi, belum ada mukadimah
                            String abi = consoleInput.getLastInput();
                            // nama mami, belum ada mukadimah
                            String mami = consoleInput.getLastInput();
                            // nama anak, belum ada mukadimah
                            String anak = consoleInput.getLastInput();
                            p.breed(p.getPlayerEngimons().get(p.getPlayerEngimons().indexByName(abi)), p.getPlayerEngimons().get(p.getPlayerEngimons().indexByName(mami)), anak);
                        } else {
                            System.out.println("Inventory penuh, gabisa breed");
                        }
                    case ("change"):
                        int angka = 1;
                        p.changeActiveEngimon(angka);
                        break;
                    case ("detail"):
                        System.out.print("Please input engimon name : ");
                        //Minta input nama engimon dari text field
                        String _name = consoleInput.getLastInput();
                        p.displayEngimon(_name);
                        break;
                    case ("learn"):
                        // Blm nerima input jg
                        System.out.println("USING SKILL ITEMS");
                        p.showItems();
                        System.out.print("Choose skill item :");
                        // minta input nama skill item dari text field
                        String skillName = consoleInput.getLastInput();
                        System.out.print("Choose engimon :");
                        // minta input nama engimon dari text field
                        String engimonName = consoleInput.getLastInput();
                        p.teach(skillName, engimonName);
                    case ("interact"):
                        System.out.println("INTERACT WITH ACTIVE ENGIMON");
                        p.interactEngimon();
                    case ("help"):
                        System.out.println("w/a/s/d     : Move character");
                        System.out.println("display     : Display player inventory");
                        System.out.println("battle      : Battle with wild engimon");
                        System.out.println("breed       : Breed 2 owned engimons");
                        System.out.println("change      : Change active engimon");
                        System.out.println("detail      : Display Engimon's detail information");
                        System.out.println("learn       : Teach engimon a skill");
                        System.out.println("interact    : Interact with current active engimon");
                        System.out.println("quit        : Exit game");
                }
            } //blm ada catchnya
        }
    }
}
