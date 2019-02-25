package GUI;
/*
 * Created by Marco-Edoardo Palma.
 */

import javax.swing.*;
import java.awt.*;

public class TextBox extends JTextField
{
    public TextBox(Color background)
    {
        super();
        setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE));
        setFont(new Font("Verdana", Font.PLAIN, 14));
        setForeground(Color.WHITE);
        setBackground(background);
        setAlignmentX(JTextField.CENTER);
        setCaretColor(Color.WHITE);
    }
}
