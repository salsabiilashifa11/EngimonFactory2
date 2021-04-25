package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConsoleInput extends JPanel implements ActionListener {

    //Fields
    public static JTextField inputField;
    public static JButton enterButton;
    private Board board;
    private ConsoleOutput consoleOutput;
    private String lastInput;

    //Constructor
    public ConsoleInput(Board _board, ConsoleOutput _consoleOutput) {
        board = _board;
        consoleOutput = _consoleOutput;

        inputField = new JTextField();
        lastInput = "";
        inputField.setBounds(20, 20, 250, 30);

        enterButton = new JButton("enter");
        enterButton.setBounds(20, 60, 100, 30);

        //Adding components to panel
        this.setLayout(null);
        this.add(inputField);
        this.add(enterButton);
        //enterButton.addActionListener(this::actionPerformed);

    }

    //Aksesor
    public String getLastInput() { return lastInput; }
    public JTextField getInputField() { return inputField;}
    public JButton getEnterButton() { return enterButton; }
    //Methods
    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        if (s.equals("enter")) {
            lastInput = inputField.getText();
            System.out.println(inputField.getText());
            inputField.setText("");
            giveFocus();
            System.out.println("LastInput is: " + lastInput);
        }
    }

    public void giveFocus() {
        board.requestFocus();
    }


}
