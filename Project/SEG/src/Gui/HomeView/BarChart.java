package Gui.HomeView;

import Commons.Tuple;
import DatabaseManager.DataExchange;
import Gui.GuiComponents.TitleLabel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BarChart extends JPanel
{
    private Color background, barColor;
    private List<Tuple<String, Number>> roots;
    private boolean representsPricing;

    private JPanel graphPanel;

    public BarChart(Color background, Color barColor, List<Tuple<String, Number>> roots, boolean representsPricing)
    {
        super(new BorderLayout());
        this.background = background;
        this.barColor = barColor;
        this.roots = roots;
        this.representsPricing = representsPricing;

        setBackground(this.background);
        setBorder(BorderFactory.createEmptyBorder());

        this.graphPanel = new JPanel(new GridLayout(1, this.roots.size(), 5, 5));
        this.graphPanel.setBackground(this.background);
        this.graphPanel.setBorder(getBorder());

        add(this.graphPanel, BorderLayout.CENTER);

        refresh();
    }

    private double getMax()
    {
        double max = Double.MIN_VALUE;
        for (Tuple<String, Number> x : this.roots)
            if (x.getY().doubleValue() > max) max = x.getY().doubleValue();

        return max;
    }

    private double percOf(double n, double max)
    {
        return n * 100 / max;
    }

    public void refresh()
    {
        this.graphPanel.removeAll();
        this.graphPanel.setLayout(new GridLayout(1, roots.size(), 5, 5));
        this.graphPanel.setBorder(BorderFactory.createLineBorder(this.background, 5, false));

        double max = getMax();

        for (Tuple<String, Number> root : this.roots)
        {
            JPanel container = new JPanel(new BorderLayout());
            container.setBackground(this.background);
            container.setBorder(getBorder());

            JPanel barPanel = new JPanel(new BorderLayout());
            barPanel.setBackground(this.barColor);
            barPanel.setBorder(getBorder());
            if (!representsPricing)
                barPanel.add(new TitleLabel(Integer.toString((int) root.getY().shortValue()), TitleLabel.CENTER, 10), BorderLayout.NORTH);
            else
                barPanel.add(new TitleLabel(DataExchange.formatPrice(root.getY().doubleValue()), TitleLabel.CENTER, 10), BorderLayout.NORTH);
            barPanel.add(new TitleLabel(root.getX(), TitleLabel.CENTER, 10), BorderLayout.SOUTH);
            barPanel.setPreferredSize(new Dimension(100, (int) percOf(root.getY().doubleValue(), max) + 40));

            container.add(barPanel, BorderLayout.SOUTH);

            this.graphPanel.add(container);
        }

        this.graphPanel.repaint();
        this.graphPanel.revalidate();
        repaint();
        revalidate();
    }
}
