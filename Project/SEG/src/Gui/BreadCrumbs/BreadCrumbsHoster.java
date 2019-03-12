package Gui.BreadCrumbs;

import Gui.GraphView;
import Gui.GuiColors;
import Gui.GuiComponents.RPanel;
import Gui.MainController;

import javax.swing.*;
import java.awt.*;

public class BreadCrumbsHoster extends RPanel {
    private BreadCrumbs breadCrumbs;
    private GraphView graphView;

    private JPanel leftPanel, rightPanel;

    public BreadCrumbsHoster(GraphView graphView) {
        super(Color.WHITE, new BorderLayout());
        this.graphView = graphView;

        // LEFT INIT
        this.leftPanel = new JPanel(new BorderLayout());
        this.leftPanel.setBackground(GuiColors.BASE_SMOKE);
        this.leftPanel.setBorder(BorderFactory.createEmptyBorder());
        add(this.leftPanel, BorderLayout.WEST);

        this.breadCrumbs = new BreadCrumbs(this, this.leftPanel, this.graphView);

        // RIGHT INIT
        this.rightPanel = new JPanel(new BorderLayout());
        this.rightPanel.setBackground(GuiColors.BASE_SMOKE);
        this.rightPanel.setBorder(BorderFactory.createEmptyBorder());
        this.rightPanel.add(graphView, BorderLayout.CENTER);
        add(this.rightPanel, BorderLayout.CENTER);

    }

    @Override
    public void refresh() {
        repaint();
        revalidate();
    }

    public BreadCrumbs getBreadCrumbs() {
        return breadCrumbs;
    }
}
