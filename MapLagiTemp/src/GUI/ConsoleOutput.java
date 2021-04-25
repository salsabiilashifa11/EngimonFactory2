package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;

public class ConsoleOutput extends JPanel implements ActionListener {
    //Atribut
    private Timer timer; //to continuously listen for actions
    private Font font1, font2;
    private String line1, line2, line3, line4, line5, line6, line7, line8, line9, line10,
                    line11, line12, line13, line14, line15;
    private Board board;
    private transient Image logo;
    private ImageIcon img;
    //Custom output stream
    private JTextArea textArea;
    private PrintStream printStream;
    private JScrollPane scrollableTextArea;

    //Konstruktor
    public ConsoleOutput(Board _board) {
        font1 = new Font("URW Chancery L", Font.BOLD, 21); //set font
        font2 = new Font(Font.MONOSPACED, Font.PLAIN, 13); //set font
        board = _board;
        line1 = "OUTPUT CONSOLE";
        this.setBackground(Color.decode("#f2f2f2"));
        timer = new Timer(25, this);
        timer.start();

        //Set output stream & textArea
        textArea = new JTextArea(400, 400);
        textArea.setBounds(20,50,400,400);
        textArea.setLineWrap(true);
        textArea.setFont(font2);
        textArea.setBackground(Color.decode("#eddca4"));
        printStream = new PrintStream(new CustomOutputStream(textArea));
        System.setOut(printStream);

        //Adding components to panel
        this.setLayout(null);
        scrollableTextArea = new JScrollPane(textArea);
        scrollableTextArea.setBounds(20,202,400,400);
        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.add(scrollableTextArea);
        this.setFocusable(false);

        //Logo
        img = new ImageIcon("assets/Logo.png");
        logo = img.getImage();
    }

    //Getter
    public String getLine1() { return line1; }
    public PrintStream getPrintStream() { return printStream; }
    public JTextArea getTextArea() { return textArea; }

    //Setter
    public void setLine1(String line) { this.line1 = line; }

    //Fungsi Tambahan
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.setFont(font1);
        g.drawString(line1, 20, 192);
        g.drawImage(logo, 30, 0, null);
    }

    public void giveFocus() {
        board.requestFocus();
    }

}
