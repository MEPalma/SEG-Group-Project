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
    public MenuLabel(String title, GuiColors guiColors) {
        super(title);
        setName("");
        super.setBackground(new Color(0, 0, 0, 0));
        super.setFont(new Font("Verdana", Font.PLAIN, 16));
        super.setForeground(guiColors.getGuiPrimeColor());
        super.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        addListeners(guiColors);
    }

    public MenuLabel(String title, int horizontalAlignment, int size, GuiColors guiColors) {
        this(title, horizontalAlignment, guiColors);
        super.setFont(new Font("Verdana", Font.PLAIN, size));
    }

    public MenuLabel(String title, int horizontalAlignment, GuiColors guiColors) {
        this(title, guiColors);
        super.setHorizontalAlignment(horizontalAlignment);
    }

    private void addListeners(GuiColors guiColors) {
        super.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setForeground(guiColors.getGuiOptionColor());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setForeground(guiColors.getGuiPrimeColor());
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
