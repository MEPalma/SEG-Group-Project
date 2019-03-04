package Gui.GuiComponents;

import Gui.GuiColors;

import javax.swing.*;
import java.awt.*;

/**
 * Represents a standardized TitleLabel in order to maintain the style though out the application constant
 */
public class TitleLabel extends JLabel {
    public TitleLabel(String title, int horizontalAlignment) {
        super(title);
        setName("");
        super.setHorizontalAlignment(horizontalAlignment);
        super.setForeground(GuiColors.BASE_PRIME);
        super.setFont(new Font("Verdana", Font.PLAIN, 22));
        super.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        setBackground(new Color(0, 0, 0, 0));
    }

    public TitleLabel(String title, int horizontalAlignment, int size) {
        super(title);
        setName("");
        super.setHorizontalAlignment(horizontalAlignment);
        super.setForeground(GuiColors.BASE_PRIME);
        super.setFont(new Font("Verdana", Font.PLAIN, size));
        super.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        setBackground(new Color(0, 0, 0, 0));
    }

    public TitleLabel(String title, int horizontalAlignment, int size, Color foreground) {
        super(title);
        setName("");
        super.setHorizontalAlignment(horizontalAlignment);
        super.setForeground(foreground);
        super.setFont(new Font("Verdana", Font.PLAIN, size));
        super.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        setBackground(new Color(0, 0, 0, 0));
    }
}
