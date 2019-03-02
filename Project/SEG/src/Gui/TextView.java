package Gui;

import javax.swing.*;
import java.awt.*;

public class TextView extends JTextPane {
    public TextView(String text, int fontSize, Color background) {
        super();
        setText(text);
        setFont(new Font("Verdana", Font.PLAIN, fontSize));
        setBackground(background);
        setBorder(BorderFactory.createEmptyBorder());
        setForeground(Color.WHITE);
    }
}
