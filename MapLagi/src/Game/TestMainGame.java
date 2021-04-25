package Game;

import GUI.Board;
import GUI.ConsoleInput;
import GUI.ConsoleOutput;
import Game.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;
import com.sun.corba.se.impl.orbutil.graph.Graph;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import javax.swing.*;
import java.awt.*;

public class TestMainGame implements ActionListener {

    //Fields
    ConsoleOutput consoleOutput;
    ConsoleInput consoleInput;
    Board board;
    Boolean status;
    String inputKebaca;
    String inputCommand;
    ArrayList<String> validCommands = new ArrayList<>();

    //Constructor
    public TestMainGame(ConsoleOutput _consoleOutput, ConsoleInput _consoleInput,
                        Board _board) {
        status = true;
        consoleOutput = _consoleOutput;
        consoleInput = _consoleInput;
        board = _board;
        System.setOut(consoleOutput.getPrintStream());
//        System.setErr(consoleOutput.getPrintStream());
        consoleInput.getEnterButton().addActionListener(this::actionPerformed);

        //engimonInit(15); //generate 15 random engimon di awal

        //setValidCommands
        validCommands.add("battle"); validCommands.add("help");

//        mainLoop();

    }


    public void executeCommand(String command) {
        if (command.equals("help")) {
            consoleOutput.setLine1("HELP");
            System.out.println("display      : Display player inventories");
            System.out.println("battle       : Battle with wild engimon");
            System.out.println("breed        : Breed 2 owned engimons");
            System.out.println("change name  : Change engimon's name");
            System.out.println("change active: Change active engimon");
            System.out.println("detail       : Display Engimon's detail");
            System.out.println("             : information");
            System.out.println("learn        : Teach engimon a skill");
            System.out.println("interact     : Interact with current active");
            System.out.println("             : engimon");
            System.out.println("quit         : Exit game");

            consoleInput.giveFocus();

        }
        else if (command.equals("display")){
            System.out.println("DISPLAY INVENTORY");
            board.getPlayer().showInventory();
            consoleInput.giveFocus();
        }
        else if (command.equals("battle")){
            System.out.println("BATTLE");
            if (board.getPlayer().getActiveEngimon() != null) {
                if (board.getPlayer().getEnemy() == null) {
                    System.out.println("Ga ada musuh");
                }
                else{
                    WildEngimon currentEnemy = (WildEngimon) board.getPlayer().getEnemy().getEngimon();
                    currentEnemy.stopTimer();
                    board.getPlayer().getEnemy().getEngimon().displayDetail();
                    System.out.println("Apakah anda ingin melanjutkan battle? [Y/N]");
                    String confirm = JOptionPane.showInputDialog(board, "Apakah anda ingin melanjutkan battle? [Y/N]");
                    if (confirm.equals("y") || confirm.equals("Y")){
                        Boolean menang = board.getPlayer().battle(board.getPlayer().getEnemy().getEngimon());
                        if (menang) {
                            System.out.println("Anda memenangkan battle");
                            String nama = JOptionPane.showInputDialog(board, "Masukkan nama dari engimon : ");
                            board.getPlayer().winBattle(board.getPlayer().getEnemy().getEngimon(), nama);
                        }
                        else{
                            System.out.println("Lu kalah ta**i");
                            currentEnemy.resumeTimer();

                        }
                    }
                    else {
                        System.out.println("Dasar Pengecut !!!");
                        currentEnemy.resumeTimer();
                    }

                }
            }
            else{
                System.out.println("Gaada active engimon idi**ot");
            }
            consoleInput.giveFocus();
        }

        else if (command.equals("breed")){
            System.out.println("BREED");
            Boolean breedAble = board.getPlayer().checkBreed();
            if (breedAble){
                //System.out.println("Masukkan nama Abi : ");
                String Abi = JOptionPane.showInputDialog(board, "Masukkan nama Abi : ");
                System.out.println("Nama abi : " + Abi);
                //System.out.println("Masukkan nama Mami : ");
                String Mami = JOptionPane.showInputDialog(board, "Masukkan nama Mami : ");
                System.out.println("Nama mami : " + Mami);
                //System.out.println("Masukkan nama Anak : ");
                String namaAnak = JOptionPane.showInputDialog(board, "Masukkan nama Anak : ");;
                System.out.println("Nama anak : " + namaAnak);
                board.getPlayer().breed(board.getPlayer().getPlayerEngimons().get(board.getPlayer().getPlayerEngimons().indexByName(Abi)), board.getPlayer().getPlayerEngimons().get(board.getPlayer().getPlayerEngimons().indexByName(Mami)), namaAnak);
            }
            else{
                System.out.println("Gabisa breed");
            }
            consoleInput.giveFocus();

        }
        else if (command.equals("change name")){
            System.out.println("CHANGE ENGIMON'S NAME");
            System.out.println("List engimon di inventory:");
            board.getPlayer().getPlayerEngimons().displayAll();
            String oldName = JOptionPane.showInputDialog(board, "Masukkan nama engimon yang ingin diganti namanya:");
            String newName = JOptionPane.showInputDialog(board, "Masukkan nama engimon yang baru:");
            board.getPlayer().changeName(oldName,newName);
        }
        else if (command.equals("change active")){
            System.out.println("CHANGE ACTIVE ENGIMON");
            System.out.print("Active Engimon = ");
            if (board.getPlayer().getActiveEngimon() == null){
                System.out.println("tidak ada");
            }
            else{
                System.out.println(board.getPlayer().getActiveEngimon().getName());
            }
            board.getPlayer().getPlayerEngimons().displayAll();
            String nama = JOptionPane.showInputDialog(board, "Masukkan nama engimon yang akan dijadikan active:");
            board.getPlayer().changeActiveEngimon(nama);
            consoleInput.giveFocus();
        }
        else if (command.equals("detail")){
            System.out.println("DETAIL");
            System.out.println("List engimon di inventory:");
            board.getPlayer().getPlayerEngimons().displayAll();

            String namaEngimon = JOptionPane.showInputDialog(board, "Masukkan nama engimon yang ingin dilihat detailnya");
            board.getPlayer().displayEngimon(namaEngimon);
            consoleInput.giveFocus();
        }
        else if (command.equals("learn")){
            System.out.println("USING SKILL ITEMS");
            System.out.println("List skill items di inventory:");
            board.getPlayer().getPlayerItems().displayAll();
            String skillName = JOptionPane.showInputDialog(board, "Pilih nama skill item yang ingin dipakai:");
            System.out.println("List engimon di inventory:");
            board.getPlayer().getPlayerEngimons().displayAll();
            String eName = JOptionPane.showInputDialog(board, "Pilih nama engimon yang ingin diajari:");
            Boolean kompatibel = board.getPlayer().teachAble(skillName,eName);
            if (kompatibel){
                if (board.getPlayer().getPlayerEngimons().get(board.getPlayer().getPlayerEngimons().indexByName(eName)).getNSkill() == 4) {
                    String choice = JOptionPane.showInputDialog(board, "Skill engimon penuh, mau tukar dengan skill item? (y/n): ");
                    if (choice.equals("y") || choice.equals("Y")){
                        System.out.println("Skill engimon: ");
                        for (int i = 0; i < 4; i++) {
                            System.out.println((i+1) + ". " + board.getPlayer().getPlayerEngimons().get(board.getPlayer().getPlayerEngimons().indexByName(eName)).getSkill().get(i).getSkillName());
                        }
                        int idx = Integer.parseInt(JOptionPane.showInputDialog(board, "Masukkan nomor skill yang mau ditukar:"));
                        board.getPlayer().getPlayerItems().deleteAt(board.getPlayer().getPlayerItems().indexByName(skillName));
                        board.getPlayer().teach(skillName, eName);
                    }
                    else{
                        System.out.println("Gajadi learn");
                    }
                }
                else{
                    board.getPlayer().teach(skillName, eName);
                }
            }
            consoleInput.giveFocus();
        }
        else if (command.equals("interact")) {
            board.getPlayer().interactEngimon();
            consoleInput.giveFocus();
        }
        else if (command.equals("save")) {
            String fname = JOptionPane.showInputDialog(board, "Masukkan nama file tujuan save:");
            saveBoard(fname);
        }
        else {
            consoleOutput.setLine1("Input Salah");
        }
    }

//    public void mainLoop() {
//        //Terima input dari consoleInput, jalanin kode yang berkaitan
//        System.out.println(); //masuk
//        String command;
//
//        while (status) {
//            command =  consoleInput.getLastInput(); //ga keambil
////            System.out.println(command);
//            //DEBUG
//            if (command.equals("")) {
//                //do nothing
//            } else {
//                System.out.println(command);
//            }
//
//            //System.out.println(consoleOutput.getLine1());
//            if (command.equals("help")) {
//                consoleOutput.setLine1("HELP");
//                System.out.println("display   : Display player inventorys");
//                System.out.println("battle    : Battle with wild engimon");
//                System.out.println("breed     : Breed 2 owned engimons");
//                System.out.println("change    : Change active engimon");
//                System.out.println("detail    : Display Engimon's detail");
//                System.out.println("          : information");
//                System.out.println("learn     : Teach engimon a skill");
//                System.out.println("display   : Display player inventorys");
//                System.out.println("interact  : Interact with current active");
//                System.out.println("          : engimon");
//                System.out.println("quit      : Exit game");
//
//            } else {
//                consoleOutput.setLine1("Input Salah");
//            }
//
//
//        }
//    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        if (s.equals("enter")) {
            consoleOutput.getTextArea().setText("");
            inputCommand = consoleInput.getInputField().getText();
            System.out.println(inputCommand);

            executeCommand(inputCommand);
            consoleOutput.getTextArea().select(0,0);
            consoleInput.giveFocus();
        }
    }



    private void saveBoard(String filename) {
        try {
            FileOutputStream fileOutputStream
                    = new FileOutputStream(filename);
            ObjectOutputStream objectOutputStream
                    = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this.board);
            objectOutputStream.flush();
            objectOutputStream.close();
            System.out.println("Berhasil save file");
        } catch (IOException e) {
            System.out.println("Gagal save file");
            e.printStackTrace();
        }
    }

//    private void loadBoard(String filename) {
//        FileInputStream fileInputStream;
//        try {
//            fileInputStream
//                    = new FileInputStream(filename);
//        } catch(FileNotFoundException e) {
//            System.out.println("File tidak ditemukan");
//            return;
//        }
//        try {
//            ObjectInputStream objectInputStream
//                    = new ObjectInputStream(fileInputStream);
//            this.board = (Board) objectInputStream.readObject();
//            objectInputStream.close();
//        } catch (Exception e) {
//            System.out.println("Load file gagal");
//        }
//    }
}
