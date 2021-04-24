package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConsoleOutput extends JPanel implements ActionListener {

    //Fields
    private Font font;
    private String line1;
    private Board board;

    //Constructor
    public ConsoleOutput(Board _board) {
        font = new Font("URW Chancery L", Font.BOLD, 21); //set font
        board = _board;
        line1 = "";
    }

    //Aksesor
    public String getLine1() { return line1; }

    public void setLine1(String line1) { this.line1 = line1; }

    //Methods
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
    public void paint(Graphics g) {
        super.paint(g);

        g.setFont(font);
        g.drawString(line1, 20, 20);
        //giveFocus();

    }

    public void giveFocus() {
        board.requestFocus();
    }

}
