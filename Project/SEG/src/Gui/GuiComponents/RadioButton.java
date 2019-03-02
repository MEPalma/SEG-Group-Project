package Gui.GuiComponents;

import Gui.GuiColors;

import javax.swing.*;
import java.awt.*;

public class RadioButton extends JRadioButton {
    public RadioButton(String text) {
        super(text);
        setBorder(BorderFactory.createEmptyBorder());
        setForeground(GuiColors.LIGHT);
        setFont(new Font("Verdana", Font.PLAIN, 14));
        setBackground(new Color(0, 0, 0, 0));
    }

    public RadioButton(String text, int size) {
        super(text);
        setBorder(BorderFactory.createEmptyBorder());
        setForeground(GuiColors.LIGHT);
        setFont(new Font("Verdana", Font.PLAIN, size));
        setBackground(new Color(0, 0, 0, 0));
    }
}
