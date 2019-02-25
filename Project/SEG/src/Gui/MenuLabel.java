package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Represents a standardized MenuLabel in order to maintain the style though out the application constant
 */
public class MenuLabel extends JLabel
{
    public MenuLabel(String title)
    {
        super(title);
        setName("");
        super.setBackground(new Color(0, 0, 0, 0));
        super.setFont(new Font("Verdana", Font.PLAIN, 16));
        super.setForeground(Color.WHITE);
        super.setBackground(new Color(0, 0, 0, 0));
        super.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        addListeners();
    }

    public MenuLabel(String title, int horizontalAlignment, int size)
    {
        this(title, horizontalAlignment);
        super.setBackground(new Color(0, 0, 0, 0));
        super.setFont(new Font("Verdana", Font.PLAIN, size));
        setName("");
        addListeners();
    }

    public MenuLabel(String title, int horizontalAlignment)
    {
        super(title);
        super.setHorizontalAlignment(horizontalAlignment);
        super.setFont(new Font("Verdana", Font.PLAIN, 16));
        super.setForeground(Color.WHITE);
        super.setBackground(new Color(0, 0, 0, 0));
        super.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        setName("");
        addListeners();
    }

    private void addListeners()
    {
        super.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseEntered(MouseEvent e)
            {
                setForeground(Color.DARK_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                setForeground(Color.WHITE);
            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                mouseClicked(e);
            }
        });
    }
}