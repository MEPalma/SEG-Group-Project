package Gui.GuiComponents;

import Gui.GuiColors;

import javax.swing.*;
import java.awt.*;

public class TextBox extends JTextField {
    public TextBox(GuiColors guiColors) {
        super();
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, guiColors.getGuiBackgroundColor()));
        setFont(new Font("Verdana", Font.PLAIN, 14));
        setForeground(Color.BLACK);
        setBackground(guiColors.getGuiBackgroundColor());
        setAlignmentX(JTextField.CENTER);
        setCaretColor(Color.BLACK);
    }
}
