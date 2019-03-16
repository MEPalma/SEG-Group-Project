package Gui.GuiComponents;

import Gui.GuiColors;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public class HListView extends JPanel implements Scrollable {

    public HListView(Color color, Collection<Component> cells, boolean separatorsOn) {
        super(new BorderLayout());
        setBackground(color);
        setBorder(BorderFactory.createEmptyBorder());

        JPanel refAddTo = this;
        for (Component cellContent : cells) {
            JPanel card = new JPanel(new BorderLayout());
            card.setBackground(color);
            card.setBorder(BorderFactory.createEmptyBorder());

            if (separatorsOn) {
                JSeparator separator2 = new JSeparator(JToolBar.Separator.VERTICAL);
                separator2.setBackground(color);
                separator2.setForeground(GuiColors.BASE_SMOKE);

                JPanel wrapperPanel = new JPanel(new BorderLayout());
                wrapperPanel.setBackground(color);
                wrapperPanel.setBorder(BorderFactory.createEmptyBorder());
                wrapperPanel.add(cellContent, BorderLayout.WEST);
                wrapperPanel.add(separator2);

                card.add(wrapperPanel, BorderLayout.WEST);
            } else {
                card.add(cellContent, BorderLayout.WEST);
            }

            refAddTo.add(card, BorderLayout.CENTER);
            refAddTo = card;
        }

    }

    public HListView(Color color, Collection<Component> cells) {
        this(color, cells, true);
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
