package Game;

import GUI.Board;
import GUI.ConsoleInput;
import GUI.ConsoleOutput;
import java.util.Random;
import com.sun.corba.se.impl.orbutil.graph.Graph;

import java.awt.*;
import java.util.Timer;

public class TestMainGame {

    //Fields
    ConsoleOutput consoleOutput;
    ConsoleInput consoleInput;
    Board board;
    Boolean status;

    //Constructor
    public TestMainGame(ConsoleOutput _consoleOutput, ConsoleInput _consoleInput,
                        Board _board) {
        status = true;
        consoleOutput = _consoleOutput;
        consoleInput = _consoleInput;
        board = _board;

        engimonInit(15); //generate 15 random engimon di awal

        mainLoop();

    }

    //Methods
    public void engimonInit(int N) {
        for (int i = 0; i < N; i++) {
            WildEngimon spawned = engimonSpawn();
            board.getMap().getCell(spawned.getPosition().getX(), spawned.getPosition().getY())
                    .setEngimon(spawned);
            System.out.println(spawned.getName());
            System.out.println(board.getMap().getCell(spawned.getPosition().getX(), spawned.getPosition().getY())
                    .getEngimon().getName());
            System.out.println(spawned.getPosition().getY());
        }
    }

    public WildEngimon engimonSpawn() {
        //Generate 1 random engimon
        Random rand = new Random();
        int upperboundCoordiante = 17;
        int upperboundLevel = 30;

        //Mencari cell kosong
        int coorX = rand.nextInt(upperboundCoordiante) + 1;
        int coorY = rand.nextInt(upperboundCoordiante) + 1;
        int level = rand.nextInt(upperboundLevel) + 1;

        while (board.getMap().getCell(coorX, coorY).getEngimon() != null
                || board.getMap().getCell(coorX, coorY).getPlayer() != null) {
            coorX = rand.nextInt(upperboundCoordiante);
            coorY = rand.nextInt(upperboundCoordiante);
        }
        WildEngimon wildEngimon;
        //Generate engimon berdasarkan cell type cell kosong
        CellType cellTypeTujuan = board.getMap().getCell(coorX, coorY).getType();
        switch (cellTypeTujuan){
            case MOUNTAIN :
                wildEngimon = new WildEngimon("ikan","fire",level,coorX,coorY,
                    this.board.getMap());
                break;
            case GRASSLAND :
                int type = rand.nextInt(2);
                if (type == 0) {
                   wildEngimon = new WildEngimon("kelelawar","ground",level,coorX,coorY,
                            this.board.getMap());
                }
                else {
                    wildEngimon = new WildEngimon("beruang","electric",level,coorX,coorY,
                                this.board.getMap());
                    }
                break;
            case SEA :
                wildEngimon = new WildEngimon("kambing","water",level,coorX,coorY,
                        this.board.getMap());
                break;

            case TUNDRA:
                wildEngimon = new WildEngimon("kadal","ice",level,coorX,coorY,
                        this.board.getMap());
                break;
            default :
                System.out.println("Masuk NULL");
                wildEngimon = null;

        }
        return wildEngimon;
    }

    public void mainLoop() {
        //Terima input dari consoleInput, jalanin kode yang berkaitan
        while (status) {
            String command = consoleInput.getLastInput();
//                System.out.println(command);
            System.out.println(consoleOutput.getLine1());
            if (command.equals("help")) {

                consoleOutput.setLine1("TESTTTTT");
//                    System.out.println("w/a/s/d     : Move character");
//                    System.out.println("display     : Display player inventory");
//                    System.out.println("battle      : Battle with wild engimon");
//                    System.out.println("breed       : Breed 2 owned engimons");
//                    System.out.println("change      : Change active engimon");
//                    System.out.println("detail      : Display Engimon's detail information");
//                    System.out.println("learn       : Teach engimon a skill");
//                    System.out.println("interact    : Interact with current active engimon");
//                    System.out.println("quit        : Exit game");
            } else {
                //do nothing, re-enter loop
            }
            System.out.println(consoleOutput.getLine1());

//                switch (command) {
//                    case ("quit"):
//                        status = false;
//                    case ("help"):
//                        System.out.println("w/a/s/d     : Move character");
//                        System.out.println("display     : Display player inventory");
//                        System.out.println("battle      : Battle with wild engimon");
//                        System.out.println("breed       : Breed 2 owned engimons");
//                        System.out.println("change      : Change active engimon");
//                        System.out.println("detail      : Display Engimon's detail information");
//                        System.out.println("learn       : Teach engimon a skill");
//                        System.out.println("interact    : Interact with current active engimon");
//                        System.out.println("quit        : Exit game");
//                    default:
//                        //do nothing, exit loop
//                }


        }
    }


}
