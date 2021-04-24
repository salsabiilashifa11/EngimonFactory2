package GUI;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;

public class CustomOutputStream extends OutputStream {
    //Fields
    private JTextArea textArea;

    //Constructor
    public CustomOutputStream(JTextArea textArea) {
        this.textArea = textArea;
    }

    //Methods
    @Override
    public void write(int b) throws IOException {

    }



}
