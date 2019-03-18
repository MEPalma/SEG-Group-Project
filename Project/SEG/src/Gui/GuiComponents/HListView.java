package Gui.GuiComponents;

import Gui.GuiColors;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Collection;

public class HListView extends JPanel implements Scrollable {

    public HListView(Color color, Collection<Component> cells) {
        super(new GridBagLayout());
        setBackground(color);
        setBorder(BorderFactory.createEmptyBorder());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.VERTICAL;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        for (Component cell : cells) {
            add(cell, gbc);
        }
    }

    public JPanel getWrappedInScroll() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(GuiColors.BASE_WHITE);
        wrapper.setBorder(BorderFactory.createEmptyBorder());

        JPanel innerWrapper = new JPanel(new BorderLayout());
        innerWrapper.setBackground(getBackground());
        innerWrapper.setBorder(BorderFactory.createEmptyBorder());
        innerWrapper.add(this, BorderLayout.CENTER);

        JScrollPane listScroller = new JScrollPane(innerWrapper, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        listScroller.setBackground(this.getBackground());
        listScroller.setWheelScrollingEnabled(false);
        listScroller.setBorder(this.getBorder());
        listScroller.getVerticalScrollBar().setUnitIncrement(1);
        listScroller.getVerticalScrollBar().setBackground(listScroller.getBackground());
        listScroller.getVerticalScrollBar().setBorder(this.getBorder());
        wrapper.add(listScroller);

        MenuLabel goLeftLabel = new MenuLabel("<", MenuLabel.CENTER, 20);
        goLeftLabel.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, GuiColors.BASE_WHITE));
        goLeftLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                listScroller.getHorizontalScrollBar().setValue(listScroller.getHorizontalScrollBar().getValue() - 150);
                listScroller.repaint();
                listScroller.revalidate();

                goLeftLabel.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, GuiColors.BASE_SMOKE));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                goLeftLabel.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, GuiColors.BASE_WHITE));
            }
        });
        wrapper.add(goLeftLabel, BorderLayout.WEST);

        MenuLabel goRightLabel = new MenuLabel(">", MenuLabel.CENTER, 20);
        goRightLabel.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, GuiColors.BASE_WHITE));
        goRightLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                listScroller.getHorizontalScrollBar().setValue(listScroller.getHorizontalScrollBar().getValue() + 150);
                listScroller.repaint();
                listScroller.revalidate();

                goRightLabel.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, GuiColors.BASE_SMOKE));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                goRightLabel.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, GuiColors.BASE_WHITE));
            }
        });
        wrapper.add(goRightLabel, BorderLayout.EAST);


        listScroller.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                listScroller.getHorizontalScrollBar().setValue(listScroller.getHorizontalScrollBar().getValue() + 16 * e.getWheelRotation());
                listScroller.repaint();
                listScroller.revalidate();

//                goLeftLabel.setVisible(true);
//                goRightLabel.setVisible(true);
//
//                System.out.println(listScroller.getHorizontalScrollBar().getMaximum());
//
//                if (listScroller.getHorizontalScrollBar().getValue() == 0) goLeftLabel.setVisible(false);
//                if (listScroller.getHorizontalScrollBar().isMaximumSizeSet()) goRightLabel.setVisible(false);
            }
        });

        return wrapper;
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
