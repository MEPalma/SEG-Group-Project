package Gui.GuiComponents;

import Gui.GuiColors;

import javax.swing.*;
import java.awt.*;

/**
 * Represents a standardized MenuButton in order to maintain the style though out the application constant
 */
public class MenuButton extends JButton {

    public MenuButton(String text, int textSize, GuiColors guiColors) {
        //init
        super(text.toUpperCase());

        setFont(new Font("Verdana", Font.PLAIN, textSize));

        setBackground(Color.WHITE);
        setForeground(guiColors.getGuiTextColor());
        setOpaque(true);

        //set white borders
        setBorder(BorderFactory.createLineBorder(Color.WHITE, 3, true));

    }

    @Override
    public void setText(String text) {
        super.setText(text.toUpperCase());
    }
}
