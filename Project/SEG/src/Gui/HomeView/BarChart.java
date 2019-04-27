package Gui.HomeView;

import Commons.Tuple;
import DatabaseManager.DataExchange;
import Gui.GuiColors;
import Gui.GuiComponents.TitleLabel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BarChart extends JPanel
{
    private List<Tuple<String, Number>> roots;
    private boolean representsPricing, isFloat;

    private JPanel graphPanel;

    private final GuiColors guiColors;
    private Color[] barColors = {
            GuiColors.OPTION_BLUE1,
            GuiColors.OPTION_RED,
            GuiColors.OPTION_ORANGE,
            GuiColors.OPTION_GREENBLUE,
            GuiColors.RED_ERROR,
            GuiColors.DEFAULT_BASE_PRIME
    };

    public BarChart(GuiColors guiColors, List<Tuple<String, Number>> roots, boolean representsPricing, boolean isFloat)
    {
        super(new BorderLayout());
        this.guiColors = guiColors;
        this.roots = roots;
        this.representsPricing = representsPricing;
        this.isFloat = isFloat;

        setBackground(guiColors.getGuiTextColor());
        setBorder(BorderFactory.createEmptyBorder());

        this.graphPanel = new JPanel(new GridLayout(1, this.roots.size(), 5, 5));
        this.graphPanel.setBackground(guiColors.getGuiTextColor());
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
        this.graphPanel.setBorder(BorderFactory.createLineBorder(guiColors.getGuiTextColor(), 5, false));

        double max = getMax();

        int nextColorIndex = 0;

        for (Tuple<String, Number> root : this.roots)
        {
            JPanel container = new JPanel(new BorderLayout());
            container.setBackground(guiColors.getGuiTextColor());
            container.setBorder(getBorder());

            JPanel barPanel = new JPanel(new BorderLayout());
            barPanel.setBackground(barColors[nextColorIndex]);

            barPanel.setBorder(getBorder());

            String value = representsPricing ? (DataExchange.formatPrice(root.getY().doubleValue())) : (isFloat ? String.format("%.4f", (root.getY().floatValue())) : Integer.toString(root.getY().intValue()));

            TitleLabel valueLabel = new TitleLabel(value, TitleLabel.CENTER, 14, guiColors);
            valueLabel.setForeground(guiColors.getGuiTextColor());
            barPanel.add(valueLabel, BorderLayout.NORTH);

            TitleLabel titleLabel = new TitleLabel(root.getX(), TitleLabel.CENTER, 12, guiColors);
            titleLabel.setForeground(guiColors.getGuiTextColor());
            barPanel.add(titleLabel, BorderLayout.SOUTH);
            barPanel.setPreferredSize(new Dimension(100, (int) percOf(root.getY().doubleValue(), max) + 40));

            container.add(barPanel, BorderLayout.SOUTH);

            this.graphPanel.add(container);

            //update color index
            nextColorIndex++;
            if (nextColorIndex >= barColors.length) nextColorIndex = 0;
        }

        this.graphPanel.repaint();
        this.graphPanel.revalidate();
        repaint();
        revalidate();
    }
}
