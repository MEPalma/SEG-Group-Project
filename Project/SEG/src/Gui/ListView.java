package Gui;
/**
 * Created by Marco-Edoardo Palma.
 */

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public class ListView extends JPanel implements Scrollable {
    public static String SKIPSPACER = "ss";

    public ListView(Color color, Collection<Component> cells) {
        super(new GridBagLayout());
        setBackground(color);
        setBorder(BorderFactory.createEmptyBorder());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        for (Component cell : cells) {
            add(cell, gbc);

            if (cell.getName() == null || !cell.getName().equals(SKIPSPACER)) {
                JSeparator separator2 = new JSeparator(JToolBar.Separator.HORIZONTAL);
                separator2.setPreferredSize(new Dimension(80, 10));
                add(separator2, gbc);
            }
        }
    }

    public JScrollPane getWrappedInScroll(boolean visibleScrollbar) {
        JScrollPane listScroller;
        if (visibleScrollbar)
            listScroller = new JScrollPane(this, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        else
            listScroller = new JScrollPane(this, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        listScroller.setBackground(this.getBackground());
        listScroller.setOpaque(true);
        listScroller.setForeground(this.getBackground());
        listScroller.setBorder(this.getBorder());
        listScroller.getVerticalScrollBar().setUnitIncrement(16);
        listScroller.getVerticalScrollBar().setBackground(listScroller.getBackground());
        listScroller.getVerticalScrollBar().setBorder(this.getBorder());
        listScroller.getViewport().setBackground(this.getBackground());
        listScroller.getViewport().scrollRectToVisible(new Rectangle(0, 0, 0, 0));

        return listScroller;
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return null;
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 16;
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 16;
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return true;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return false;
    }
}
