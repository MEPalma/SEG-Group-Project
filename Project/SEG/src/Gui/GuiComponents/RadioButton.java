package Gui.GuiComponents;

import Gui.GuiColors;

import javax.swing.*;
import java.awt.*;

public class RadioButton extends JRadioButton {
    public RadioButton(String text, GuiColors guiColors) {
        super(text);
        setBorder(BorderFactory.createEmptyBorder());
        setForeground(GuiColors.DARK_GRAY);
        setFont(new Font("Verdana", Font.PLAIN, 14));
        setBackground(guiColors.getGuiPrimeColor());
    }

    public RadioButton(String text, int size, GuiColors guiColors) {
        this(text, guiColors);
        setFont(new Font("Verdana", Font.PLAIN, size));
    }
}
