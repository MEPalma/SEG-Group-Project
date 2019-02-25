package Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Represents a standardized MenuButton in order to maintain the style though out the application constant
 */
public class MenuButton extends JButton
{
    public final static int STANDARD = 0;
    public final static int OPEN = 1;
    public final static int DELETE = 2;

    private Color mouseEnteredColor;

    public MenuButton(String text, int textSize)
    {
        super(text.toUpperCase());

        setFont(new Font("Verdana", Font.PLAIN, textSize));

        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
        setOpaque(true);

        setBorder(BorderFactory.createEmptyBorder());
    }

    public MenuButton(String text, int textSize, int mode)
    {
        //init
        super(text.toUpperCase());

        setFont(new Font("Verdana", Font.PLAIN, textSize));

        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
        setOpaque(true);

        //set colo scheme based on button mode: STANDARD, OPEN-SAVE, DELETE
        switch (mode)
        {
            case MenuButton.STANDARD:
            {
                mouseEnteredColor = new Color(245, 245, 245);
                break;
            }

            case MenuButton.OPEN:
            {
                mouseEnteredColor = new Color(178, 223, 219);
                break;
            }

            case MenuButton.DELETE:
            {
                mouseEnteredColor = new Color(255, 138, 128);
                break;
            }

            default:
            {
                mouseEnteredColor = Color.WHITE;
                break;
            }
        }

        //set white borders
        setBorder(BorderFactory.createLineBorder(Color.WHITE, 3, true));

        //ad style listener form mouse entered and exited --> the color of the button will change
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseEntered(MouseEvent e)
            {
                setBackground(mouseEnteredColor);
                setBorder(BorderFactory.createLineBorder(mouseEnteredColor, 3, true));
            }
        });

        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseExited(MouseEvent e)
            {
                setBackground(Color.WHITE);
                setBorder(BorderFactory.createLineBorder(Color.WHITE, 3, true));
            }
        });

        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                super.mouseClicked(e);
            }
        });
    }

    @Override
    public void setText(String text)
    {
        super.setText(text.toUpperCase());
    }
}
