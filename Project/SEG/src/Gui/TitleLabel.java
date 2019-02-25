package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

/**
 * Represents a standardized TitleLabel in order to maintain the style though out the application constant
 */
public class TitleLabel extends JLabel
{
    public TitleLabel(String title, int horizontalAlignment)
    {
        super(title);
        setName("");
        super.setHorizontalAlignment(horizontalAlignment);
        super.setForeground(Color.WHITE);
        super.setFont(new Font("Verdana", Font.PLAIN, 22));
        super.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        setBackground(new Color(0, 0, 0, 0));
    }

    public TitleLabel(String title, int horizontalAlignment, int size)
    {
        super(title);
        setName("");
        super.setHorizontalAlignment(horizontalAlignment);
        super.setForeground(Color.WHITE);
        super.setFont(new Font("Verdana", Font.PLAIN, size));
        super.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        setBackground(new Color(0, 0, 0, 0));
    }

    public TitleLabel(String title, int horizontalAlignment, int size, MouseListener listener)
    {
        super(title);
        setName("");
        super.setHorizontalAlignment(horizontalAlignment);
        super.setForeground(Color.WHITE);
        super.setFont(new Font("Verdana", Font.PLAIN, size));
        super.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        setBackground(new Color(0, 0, 0, 0));
        addMouseListener(listener);
    }
}
