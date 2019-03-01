package Gui;

import javax.swing.*;
import java.awt.*;

public class BreadCrumbsHoster extends RPanel
{
    private BreadCrumbs breadCrumbs;
    private GraphView graphView;

    private JPanel leftPanel, rightPanel;

    public BreadCrumbsHoster()
    {
        super(Color.WHITE, new BorderLayout());
        this.graphView = new GraphView();

        // LEFT INIT
        this.leftPanel = new JPanel(new BorderLayout());
        this.leftPanel.setBackground(GuiColors.RED);
        this.leftPanel.setBorder(BorderFactory.createEmptyBorder());
        add(this.leftPanel, BorderLayout.WEST);

        this.breadCrumbs = new BreadCrumbs(this, this.leftPanel, this.graphView);

        // RIGHT INIT
        this.rightPanel = new JPanel(new BorderLayout());
        this.rightPanel.setBackground(GuiColors.RED);
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
