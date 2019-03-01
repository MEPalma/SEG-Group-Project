package Gui;
/**
 * @author Marco-Edoardo Palma
 */

import javax.swing.*;
import java.awt.*;

public class TextBox extends JTextField {
    public TextBox(Color background) {
        super();
        setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE));
        setFont(new Font("Verdana", Font.PLAIN, 12));
        setForeground(Color.BLACK);
        setBackground(background);
        setAlignmentX(JTextField.CENTER);
        setCaretColor(Color.WHITE);
    }
}
