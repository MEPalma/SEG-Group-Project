package Gui.GuiComponents;

import Gui.GuiColors;

import javax.swing.*;
import java.awt.*;

public class CheckBox extends JCheckBox {

    public CheckBox(String text) {
        super(text);
        setBorder(BorderFactory.createEmptyBorder());
        setForeground(GuiColors.DARK_GRAY);
        setFont(new Font("Verdana", Font.PLAIN, 14));
        setBackground(new Color(0, 0, 0, 0));
    }

}
