package Gui.GuiComponents;

import Gui.GuiColors;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public class ListView extends JPanel implements Scrollable {

    private final GuiColors guiColors;

    public ListView(GuiColors guiColors, Collection<Component> cells, boolean separatorsOn) {
        super(new BorderLayout());
        this.guiColors = guiColors;

        setBackground(guiColors.getGuiTextColor());
        setBorder(BorderFactory.createEmptyBorder());

        JPanel refAddTo = this;
        for (Component cellContent : cells) {
            JPanel card = new JPanel(new BorderLayout());
            card.setBackground(guiColors.getGuiTextColor());
            card.setBorder(BorderFactory.createEmptyBorder());

            if (separatorsOn) {
                JSeparator separator2 = new JSeparator(JToolBar.Separator.HORIZONTAL);
                separator2.setBackground(guiColors.getGuiTextColor());
                separator2.setForeground(guiColors.getGuiBackgroundColor());

                JPanel wrapperPanel = new JPanel(new BorderLayout());
                wrapperPanel.setBackground(guiColors.getGuiTextColor());
                wrapperPanel.setBorder(BorderFactory.createEmptyBorder());
                wrapperPanel.add(cellContent, BorderLayout.NORTH);
                wrapperPanel.add(separator2);

                card.add(wrapperPanel, BorderLayout.NORTH);
            } else {
                card.add(cellContent, BorderLayout.NORTH);
            }

            refAddTo.add(card, BorderLayout.CENTER);
            refAddTo = card;
        }

    }

    public ListView(GuiColors guiColors, Collection<Component> cells) {
        this(guiColors, cells, true);
    }

    public ListView(Color color, Color separatorColor, Collection<Component> cells, boolean separatorsOn) {
        super(new BorderLayout());
        this.guiColors = new GuiColors();
        guiColors.setGuiTextColor(color);
        guiColors.setGuiBackgroundColor(separatorColor);

        setBackground(guiColors.getGuiTextColor());
        setBorder(BorderFactory.createEmptyBorder());

        JPanel refAddTo = this;
        for (Component cellContent : cells) {
            JPanel card = new JPanel(new BorderLayout());
            card.setBackground(guiColors.getGuiTextColor());
            card.setBorder(BorderFactory.createEmptyBorder());

            if (separatorsOn) {
                JSeparator separator2 = new JSeparator(JToolBar.Separator.HORIZONTAL);
                separator2.setBackground(guiColors.getGuiTextColor());
                separator2.setForeground(guiColors.getGuiBackgroundColor());

                JPanel wrapperPanel = new JPanel(new BorderLayout());
                wrapperPanel.setBackground(guiColors.getGuiTextColor());
                wrapperPanel.setBorder(BorderFactory.createEmptyBorder());
                wrapperPanel.add(cellContent, BorderLayout.NORTH);
                wrapperPanel.add(separator2);

                card.add(wrapperPanel, BorderLayout.NORTH);
            } else {
                card.add(cellContent, BorderLayout.NORTH);
            }

            refAddTo.add(card, BorderLayout.CENTER);
            refAddTo = card;
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
