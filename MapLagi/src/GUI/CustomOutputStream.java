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
        // redirects data to the text area
        textArea.append(String.valueOf((char)b));
        // scrolls the text area to the end of data
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }



}
