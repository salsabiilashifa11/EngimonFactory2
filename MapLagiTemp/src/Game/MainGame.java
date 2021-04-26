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

public class MainGame implements ActionListener {
    // Atribut
    ConsoleOutput consoleOutput;
    ConsoleInput consoleInput;
    Board board;
    Boolean status;
    String inputKebaca;
    String inputCommand;
    ArrayList<String> validCommands = new ArrayList<>();

    // Konstruktor
    public MainGame(ConsoleOutput _consoleOutput, ConsoleInput _consoleInput, Board _board) {
        status = true;
        consoleOutput = _consoleOutput;
        consoleInput = _consoleInput;
        board = _board;
        System.setOut(consoleOutput.getPrintStream());
        // System.setErr(consoleOutput.getPrintStream());
        consoleInput.getEnterButton().addActionListener(this::actionPerformed);

        // engimonInit(15); //generate 15 random engimon di awal

        // setValidCommands
        validCommands.add("battle");
        validCommands.add("help");

        // mainLoop();

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
            System.out.println("delete       : Menghapus isi dari inventory");
            System.out.println("save         : Menyimpan game");
            System.out.println("quit         : Exit game");

            consoleInput.giveFocus();

        } else if (command.equals("display")) {
            consoleOutput.setLine1("DISPLAY INVENTORY");
            board.getPlayer().showInventory();
            consoleInput.giveFocus();
        } else if (command.equals("battle")) {
            consoleOutput.setLine1("BATTLE");
            if (board.getPlayer().getActiveEngimon() != null) {
                if (board.getPlayer().getEnemy() == null) {
                    System.out.println("Ga ada musuh");
                } else {
                    WildEngimon currentEnemy = (WildEngimon) board.getPlayer().getEnemy().getEngimon();
                    currentEnemy.stopTimer();
                    board.getPlayer().getEnemy().getEngimon().displayDetail();
                    board.getPlayer().getActiveEngimon().calculatePower(board.getPlayer().getEnemy().getEngimon());
                    System.out.print("===============================================");
                    System.out.println("  Apakah anda ingin melanjutkan battle? [Y/N] ");
                    System.out.println("===============================================");
                    String confirm = JOptionPane.showInputDialog(board, "Apakah anda ingin melanjutkan battle? [Y/N]");
                    if (confirm.equals("y") || confirm.equals("Y")) {
                        Boolean menang = board.getPlayer().battle(board.getPlayer().getEnemy().getEngimon());
                        if (menang) {
                            System.out.println("Anda memenangkan battle");
                            String nama = JOptionPane.showInputDialog(board, "Masukkan nama dari engimon : ");
                            int idxE = board.getPlayer().getPlayerEngimons().indexByName(nama);
                            while (idxE != -1) {
                                nama = JOptionPane.showInputDialog(board,
                                        "Nama sudah diambil engimon lain. Masukkan nama yang berbeda:");
                                idxE = board.getPlayer().getPlayerEngimons().indexByName(nama);
                            }
                            board.getPlayer().winBattle(board.getPlayer().getEnemy().getEngimon(), nama);
                        } else {
                            System.out.println("Lu kalah ta**i");
                            if (board.getPlayer().getPlayerEngimons().n_elmt() == 0) {
                                JOptionPane.showInputDialog(board,
                                        "Engimon yang kamu miliki sudah habis. Kamu kalah!!!\nPress Any Button to Close");
                                System.exit(0);
                            }
                            currentEnemy.resumeTimer();

                        }
                    } else {
                        System.out.println("Dasar Pengecut !!!");
                        currentEnemy.resumeTimer();
                    }

                }
            } else {
                System.out.println("Gaada active engimon idi**ot");
            }
            consoleInput.giveFocus();
        } else if (command.equals("breed")) {
            consoleOutput.setLine1("BREED");
            Boolean breedAble = board.getPlayer().checkBreed();
            if (breedAble) {
                // System.out.println("Masukkan nama Abi : ");
                String Abi = JOptionPane.showInputDialog(board, "Masukkan nama Abi : ");
                System.out.println("Nama abi : " + Abi);
                // System.out.println("Masukkan nama Mami : ");
                String Mami = JOptionPane.showInputDialog(board, "Masukkan nama Mami : ");
                while (Mami.equals(Abi)) {
                    System.out.println("Masa berkembang biak sama diri sendiri sih");
                    Mami = JOptionPane.showInputDialog(board, "Masukkan nama Mami : ");
                }
                System.out.println("Nama mami : " + Mami);
                // System.out.println("Masukkan nama Anak : ");
                String namaAnak = JOptionPane.showInputDialog(board, "Masukkan nama Anak : ");
                System.out.println("Nama anak : " + namaAnak);
                board.getPlayer()
                        .breed(board.getPlayer().getPlayerEngimons()
                                .get(board.getPlayer().getPlayerEngimons().indexByName(Abi)),
                                board.getPlayer().getPlayerEngimons()
                                        .get(board.getPlayer().getPlayerEngimons().indexByName(Mami)),
                                namaAnak);

            } else {
                System.out.println("Gabisa breed");
            }
            consoleInput.giveFocus();
        } else if (command.equals("change name")) {
            consoleOutput.setLine1("CHANGE ENGIMON'S NAME");
            System.out.println("List engimon di inventory:");
            board.getPlayer().getPlayerEngimons().displayAll();
            String oldName = JOptionPane.showInputDialog(board, "Masukkan nama engimon yang ingin diganti namanya:");
            String newName = JOptionPane.showInputDialog(board, "Masukkan nama engimon yang baru:");
            board.getPlayer().changeName(oldName, newName);
        } else if (command.equals("change active")) {
            consoleOutput.setLine1("CHANGE ACTIVE ENGIMON");
            System.out.print("Active Engimon = ");
            if (board.getPlayer().getActiveEngimon() == null) {
                System.out.println("tidak ada");
            } else {
                System.out.println(board.getPlayer().getActiveEngimon().getName());
            }
            board.getPlayer().getPlayerEngimons().displayAll();
            String nama = JOptionPane.showInputDialog(board, "Masukkan nama engimon yang akan dijadikan active:");
            board.getPlayer().changeActiveEngimon(nama);
            consoleInput.giveFocus();
        } else if (command.equals("detail")) {
            consoleOutput.setLine1("DETAIL");
            System.out.println("List engimon di inventory:");
            board.getPlayer().getPlayerEngimons().displayAll();

            String namaEngimon = JOptionPane.showInputDialog(board,
                    "Masukkan nama engimon yang ingin dilihat detailnya");
            board.getPlayer().displayEngimon(namaEngimon);
            consoleInput.giveFocus();
        } else if (command.equals("delete")) {
            consoleOutput.setLine1("DELETE");
            board.getPlayer().showInventory();
            String tipe = JOptionPane.showInputDialog(board, "Pilih tipe yang ingin dibuang: (engimon/skillitem)");
            if (tipe.equals("engimon")) {
                String eName = JOptionPane.showInputDialog(board, "Masukkan nama engimon yang ingin dibuang:");
                try {
                    board.getPlayer().getPlayerEngimons()
                            .deleteAt(board.getPlayer().getPlayerEngimons().indexByName(eName));
                } catch (ArrayIndexOutOfBoundsException exception) {
                    System.out.println("Tidak ada engimon dengan nama " + eName);
                }
            } else if (tipe.equals("skillitem")) {
                String siName = JOptionPane.showInputDialog(board, "Masukkan nama skill item yang ingin dibuang:");
                try {
                    int idxSkillItem = board.getPlayer().getPlayerItems().indexByName(siName);
                    int siQty = Integer.parseInt(
                            JOptionPane.showInputDialog(board, "Masukkan jumlah skill item yang ingin dibuang:"));
                    if (siQty > board.getPlayer().getPlayerItems().get(idxSkillItem).getQuantity()) {
                        System.out.println("Bisa ngitung ga sih mas. Jumlahnya kelebihan!!!");
                    } else {
                        for (int i = 1; i <= siQty; i++) {
                            board.getPlayer().getPlayerItems().deleteAt(idxSkillItem);
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException exception) {
                    System.out.println("Tidak ada skill item dengan nama " + siName);
                }
            } else {
                System.out.println("Tipe yang dimasukkan harus 'engimon' atau 'skillitem'");
            }
        } else if (command.equals("learn")) {
            consoleOutput.setLine1("USING SKILL ITEMS");
            System.out.println("List skill items di inventory:");
            board.getPlayer().getPlayerItems().displayAll();
            String skillName = JOptionPane.showInputDialog(board, "Pilih nama skill item yang ingin dipakai:");
            System.out.println("List engimon di inventory:");
            board.getPlayer().getPlayerEngimons().displayAll();
            String eName = JOptionPane.showInputDialog(board, "Pilih nama engimon yang ingin diajari:");
            Boolean kompatibel = board.getPlayer().teachAble(skillName, eName);
            if (kompatibel) {
                if (board.getPlayer().getPlayerEngimons().get(board.getPlayer().getPlayerEngimons().indexByName(eName))
                        .getNSkill() == 4) {
                    String choice = JOptionPane.showInputDialog(board,
                            "Skill engimon penuh, mau tukar dengan skill item? (y/n): ");
                    if (choice.equals("y") || choice.equals("Y")) {
                        System.out.println("Skill engimon: ");
                        for (int i = 0; i < 4; i++) {
                            System.out.println((i + 1) + ". "
                                    + board.getPlayer().getPlayerEngimons()
                                            .get(board.getPlayer().getPlayerEngimons().indexByName(eName)).getSkill()
                                            .get(i).getSkillName());
                        }
                        int idx = Integer
                                .parseInt(JOptionPane.showInputDialog(board, "Masukkan nomor skill yang mau ditukar:"));
                        board.getPlayer().getPlayerItems()
                                .deleteAt(board.getPlayer().getPlayerItems().indexByName(skillName));
                        board.getPlayer().teach(skillName, eName);
                    } else {
                        System.out.println("Gajadi learn");
                    }
                } else {
                    board.getPlayer().teach(skillName, eName);
                }
            }
            consoleInput.giveFocus();
        } else if (command.equals("interact")) {
            consoleOutput.setLine1("INTERACT");
            board.getPlayer().interactEngimon();
            consoleInput.giveFocus();
        } else if (command.equals("save")) {
            String fname = JOptionPane.showInputDialog(board, "Masukkan nama file tujuan save:");
            saveBoard(fname);
        } else if (command.equals("quit")) {
            String option = JOptionPane.showInputDialog(board,
                    "Apakah anda ingin melakukan save sebelum keluar? (y/n)");
            if (option.equals("y")) {
                String fname = JOptionPane.showInputDialog(board, "Masukkan nama file tujuan save:");
                saveBoard(fname);
            }
            System.exit(0);
        } else {
            consoleOutput.setLine1("Input Salah");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        if (s.equals("enter")) {
            consoleOutput.getTextArea().setText("");
            inputCommand = consoleInput.getInputField().getText();

            executeCommand(inputCommand);
            consoleOutput.getTextArea().select(0, 0);
            consoleInput.giveFocus();
        }
    }

    private void saveBoard(String filename) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filename);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this.board);
            objectOutputStream.flush();
            objectOutputStream.close();
            System.out.println("Berhasil save file");
        } catch (IOException e) {
            System.out.println("Gagal save file");
            e.printStackTrace();
        }
    }

    // private void loadBoard(String filename) {
    // FileInputStream fileInputStream;
    // try {
    // fileInputStream
    // = new FileInputStream(filename);
    // } catch(FileNotFoundException e) {
    // System.out.println("File tidak ditemukan");
    // return;
    // }
    // try {
    // ObjectInputStream objectInputStream
    // = new ObjectInputStream(fileInputStream);
    // this.board = (Board) objectInputStream.readObject();
    // objectInputStream.close();
    // } catch (Exception e) {
    // System.out.println("Load file gagal");
    // }
    // }
}
