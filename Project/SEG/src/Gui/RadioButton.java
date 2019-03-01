package Gui;

import javax.swing.*;
import java.awt.*;

public class RadioButton extends JRadioButton
{
    public RadioButton(String text)
    {
        super(text);
        setBorder(BorderFactory.createEmptyBorder());
        setForeground(GuiColors.LIGHT);
        setFont(new Font("Verdana", Font.PLAIN, 16));
    }

    public RadioButton(String text, int size)
    {
        super(text);
        setBorder(BorderFactory.createEmptyBorder());
        setForeground(GuiColors.LIGHT);
        setFont(new Font("Verdana", Font.PLAIN, size));
    }
}
