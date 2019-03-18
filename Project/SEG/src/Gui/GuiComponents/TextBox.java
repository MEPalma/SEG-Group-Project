package Gui.GuiComponents;

import Gui.GuiColors;

import javax.swing.*;
import java.awt.*;

public class TextBox extends JTextField {
    public TextBox(Color background) {
        super();
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, GuiColors.BASE_SMOKE));
        setFont(new Font("Verdana", Font.PLAIN, 14));
        setForeground(Color.BLACK);
        setBackground(background);
        setAlignmentX(JTextField.CENTER);
        setCaretColor(Color.BLACK);
    }
}
