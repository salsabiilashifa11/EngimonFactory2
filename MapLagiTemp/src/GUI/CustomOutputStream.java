package GUI;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;

public class CustomOutputStream extends OutputStream {
    //Atribut
    private JTextArea textArea;

    //Konstruktor
    public CustomOutputStream(JTextArea textArea) {
        this.textArea = textArea;
    }

    //Fungsi Tambahan
    @Override
    public void write(int b) throws IOException {
        // redirects data to the text area
        textArea.append(String.valueOf((char)b));
        // scrolls the text area to the end of data
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }



}
