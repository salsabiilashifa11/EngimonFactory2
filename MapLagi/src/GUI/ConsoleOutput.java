package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConsoleOutput extends JPanel implements ActionListener {

    //Fields
    private Timer timer; //to continuously listen for actions
    private Font font1, font2;
    private String line1, line2, line3, line4, line5, line6, line7, line8, line9, line10,
                    line11, line12, line13, line14, line15;
    private Board board;

    //Constructor
    public ConsoleOutput(Board _board) {
        font1 = new Font("URW Chancery L", Font.BOLD, 21); //set font
        font2 = new Font(Font.MONOSPACED, Font.PLAIN, 13); //set font
        board = _board;
        line1 = "Test ganti dong";
        line2 = "Test ganti dong";
        line3 = "Test ganti dong";
        line4 = "Test ganti dong";
        line5 = "Test ganti dong";
        line6 = "Test ganti dong";
        line7 = "Test ganti dong";
        line8 = "Test ganti dong";
        line9 = "Test ganti dong";
        line10 = "Test ganti dong";
        line11 = "Test ganti dong";
        line12 = "Test ganti dong";
        line13 = "Test ganti dong";
        line14 = "Test ganti dong";
        line15 = "Test ganti dong";

        timer = new Timer(25, this);
        timer.start();
    }

    //Aksesor
    public String getLine1() { return line1; }

    public void setLine1(String line) { this.line1 = line; }
    public void setLine2(String line) { this.line2 = line; }
    public void setLine3(String line) { this.line3 = line; }
    public void setLine4(String line) { this.line4 = line; }
    public void setLine5(String line) { this.line5 = line; }
    public void setLine6(String line) { this.line6 = line; }
    public void setLine7(String line) { this.line7 = line; }
    public void setLine8(String line) { this.line8 = line; }
    public void setLine9(String line) { this.line9 = line; }
    public void setLine10(String line) { this.line10 = line; }
    public void setLine11(String line) { this.line10 = line; }
    public void setLine12(String line) { this.line10 = line; }
    public void setLine13(String line) { this.line10 = line; }
    public void setLine14(String line) { this.line10 = line; }
    public void setLine15(String line) { this.line10 = line; }


    //Methods
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public void paint(Graphics g) {
        super.paint(g);

        System.out.println("This gets called all the time");
        g.setFont(font1);
        g.drawString(line1, 20, 40);

        g.setFont(font2);
        g.drawString(line2, 20, 65);
        g.drawString(line3, 20, 80);
        g.drawString(line4, 20, 95);
        g.drawString(line5, 20, 110);
        g.drawString(line6, 20, 125);
        g.drawString(line7, 20, 140);
        g.drawString(line8, 20, 155);
        g.drawString(line9, 20, 170);
        g.drawString(line10, 20, 185);
        g.drawString(line11, 20, 200);
        g.drawString(line12, 20, 215);
        g.drawString(line13, 20, 230);
        g.drawString(line14, 20, 245);
        g.drawString(line15, 20, 260);

        //giveFocus();

    }

    public void giveFocus() {
        board.requestFocus();
    }

}
