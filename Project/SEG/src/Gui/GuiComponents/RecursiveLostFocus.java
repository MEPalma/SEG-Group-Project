package Gui.GuiComponents;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RecursiveLostFocus extends WindowAdapter {

    private Component toSetInvisible;

    public RecursiveLostFocus(Component toSetInvisible) {
        this.toSetInvisible = toSetInvisible;
    }

    @Override
    public void windowLostFocus(WindowEvent e) {
        try {
            int x = MouseInfo.getPointerInfo().getLocation().x;
            int y = MouseInfo.getPointerInfo().getLocation().y;

            if (x > toSetInvisible.getLocation().x + toSetInvisible.getWidth() || x < toSetInvisible.getLocation().x || y > toSetInvisible.getLocation().y + toSetInvisible.getHeight() || y < toSetInvisible.getLocation().y)
                toSetInvisible.setVisible(false);

            e.getOppositeWindow().addWindowFocusListener(this);
        } catch (Exception ex) {
        }
    }
}
