package Gui.GuiComponents;

import Gui.GuiColors;

import javax.swing.*;
import java.awt.*;

public class RadioButton extends JRadioButton {
    public RadioButton(String text) {
        super(text);
        setBorder(BorderFactory.createEmptyBorder());
        setForeground(GuiColors.DARK_GRAY);
        setFont(new Font("Verdana", Font.PLAIN, 14));
        setBackground(GuiColors.BASE_PRIME);
    }

    public RadioButton(String text, int size) {
        super(text);
        setBorder(BorderFactory.createEmptyBorder());
        setForeground(GuiColors.DARK_GRAY);
        setFont(new Font("Verdana", Font.PLAIN, size));
        setBackground(GuiColors.BASE_PRIME);
    }
}
