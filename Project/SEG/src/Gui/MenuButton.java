package Gui;

/**
 * @author Marco-Edoardo Palma
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Represents a standardized MenuButton in order to maintain the style though out the application constant
 */
public class MenuButton extends JButton {

    public MenuButton(String text, int textSize) {
        //init
        super(text.toUpperCase());

        setFont(new Font("Verdana", Font.PLAIN, textSize));

        setBackground(Color.WHITE);
        setForeground(GuiColors.TEXT_SELECTED);
        setOpaque(true);

        //set white borders
        setBorder(BorderFactory.createLineBorder(Color.WHITE, 3, true));

    }

    @Override
    public void setText(String text) {
        super.setText(text.toUpperCase());
    }
}
