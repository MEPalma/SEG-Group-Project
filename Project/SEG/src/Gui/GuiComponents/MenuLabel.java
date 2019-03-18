package Gui.GuiComponents;

import Gui.GuiColors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Represents a standardized MenuLabel in order to maintain the style though out the application constant
 */
public class MenuLabel extends JLabel {
    public MenuLabel(String title) {
        super(title);
        setName("");
        super.setBackground(new Color(0, 0, 0, 0));
        super.setFont(new Font("Verdana", Font.PLAIN, 16));
        super.setForeground(GuiColors.BASE_PRIME);
        super.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        addListeners();
    }

    public MenuLabel(String title, int horizontalAlignment, int size) {
        this(title, horizontalAlignment);
        super.setForeground(GuiColors.BASE_PRIME);
        super.setBackground(new Color(0, 0, 0, 0));
        super.setFont(new Font("Verdana", Font.PLAIN, size));
        setName("");
        addListeners();
    }

    public MenuLabel(String title, int horizontalAlignment) {
        super(title);
        super.setHorizontalAlignment(horizontalAlignment);
        super.setFont(new Font("Verdana", Font.PLAIN, 16));
        super.setForeground(GuiColors.BASE_PRIME);
        super.setBackground(new Color(0, 0, 0, 0));
        super.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        setName("");
        addListeners();
    }

    private void addListeners() {
        super.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setForeground(GuiColors.BASE_OPTION);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setForeground(GuiColors.BASE_PRIME);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                mouseClicked(e);
            }
        });
    }

    public void dropAllListeners() {
        for (MouseListener i : this.getMouseListeners())
            this.removeMouseListener(i);
    }
}
