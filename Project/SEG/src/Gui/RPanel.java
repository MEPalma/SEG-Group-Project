package Gui;
/**
 *
 * @author MNarco-Edoardo Palma
 */

import javax.swing.*;
import java.awt.*;

public abstract class RPanel extends JPanel
{
    public RPanel(Color background, LayoutManager layoutManager)
    {
        super(layoutManager);
        setBackground(background);
        setBorder(BorderFactory.createEmptyBorder());
    }

    public abstract void refresh();
}
