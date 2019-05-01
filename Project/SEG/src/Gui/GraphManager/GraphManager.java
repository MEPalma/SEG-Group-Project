package Gui.GraphManager;

import Commons.CompareGraphSpec;
import Commons.GraphSpecs;
import Commons.Tuple;
import DatabaseManager.Stringifiable;
import Gui.GuiColors;
import Gui.GuiComponents.TitleLabel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

public class GraphManager {

    public static JPanel createBarChar(String xAxis, String yAxis, List<Tuple<String, Tuple<String, Number>>> data) {

        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Tuple<String, Tuple<String, Number>> t : data)
                dataset.addValue(t.getY().getY(), t.getX(), t.getY().getX());

        JFreeChart barChart = ChartFactory.createBarChart(
                "",
                xAxis,
                yAxis,
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);
        barChart.setBorderVisible(false);
        barChart.setAntiAlias(true);
        barChart.setBackgroundPaint(Color.WHITE);
//        barChart.getCategoryPlot().getDomainAxis().setMaximumCategoryLabelLines(10);
//        barChart.getCategoryPlot().setPa

        CategoryPlot cplot = (CategoryPlot) barChart.getPlot();
        cplot.setBackgroundPaint(SystemColor.inactiveCaption);

        ((BarRenderer) cplot.getRenderer()).setBarPainter(new StandardBarPainter());

        BarRenderer r = (BarRenderer) barChart.getCategoryPlot().getRenderer();
        r.setSeriesPaint(0, GuiColors.OPTION_BLUE1);
        r.setSeriesPaint(1, GuiColors.OPTION_RED);
        r.setSeriesPaint(2, GuiColors.OPTION_GREENBLUE);
        r.setSeriesPaint(4, GuiColors.OPTION_ORANGE);
        r.setSeriesPaint(5, GuiColors.DEFAULT_BASE_PRIME);

        Plot plot = barChart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);

        barChart.getCategoryPlot().getRangeAxis().setLabelFont(new Font("Verdana", Font.PLAIN, 12));
        barChart.getCategoryPlot().getDomainAxis().setLabelFont(barChart.getCategoryPlot().getRangeAxis().getLabelFont());

        BarRenderer br = (BarRenderer) barChart.getCategoryPlot().getRenderer();
        br.setMaximumBarWidth(.05);

        barChart.getCategoryPlot().getRangeAxis().setTickLabelFont(new Font("Verdana", Font.PLAIN, 8));
        barChart.getCategoryPlot().getDomainAxis().setTickLabelFont(barChart.getCategoryPlot().getRangeAxis().getTickLabelFont());

        if (data.size() > 24) barChart.getCategoryPlot().getDomainAxis().setTickLabelsVisible(false);

        CategoryAxis axis = barChart.getCategoryPlot().getDomainAxis();
        axis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        return new ChartPanel(barChart);
    }

    public static JPanel createBarChar(GraphSpecs graphSpecs) {
        JFreeChart barChart = ChartFactory.createBarChart(
                "",
                graphSpecs.getxAxisName(),
                graphSpecs.getyAxisName(),
                getBarChartDataset("", graphSpecs.getData()),
                PlotOrientation.VERTICAL,
                true, true, false);
        barChart.setBorderVisible(false);
        barChart.setAntiAlias(true);
        barChart.removeLegend();
        barChart.setBackgroundPaint(Color.WHITE);

        CategoryPlot cplot = (CategoryPlot) barChart.getPlot();
        cplot.setBackgroundPaint(SystemColor.inactiveCaption);

        ((BarRenderer) cplot.getRenderer()).setBarPainter(new StandardBarPainter());

        BarRenderer r = (BarRenderer) barChart.getCategoryPlot().getRenderer();
        r.setSeriesPaint(0, GuiColors.DEFAULT_BASE_PRIME);

        Plot plot = barChart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);

        barChart.getCategoryPlot().getRangeAxis().setLabelFont(new Font("Verdana", Font.PLAIN, 12));
        barChart.getCategoryPlot().getDomainAxis().setLabelFont(barChart.getCategoryPlot().getRangeAxis().getLabelFont());

        BarRenderer br = (BarRenderer) barChart.getCategoryPlot().getRenderer();
        br.setMaximumBarWidth(.05);

        barChart.getCategoryPlot().getRangeAxis().setTickLabelFont(new Font("Verdana", Font.PLAIN, 8));
        barChart.getCategoryPlot().getDomainAxis().setTickLabelFont(barChart.getCategoryPlot().getRangeAxis().getTickLabelFont());

        if (graphSpecs.getData().size() > 24) barChart.getCategoryPlot().getDomainAxis().setTickLabelsVisible(false);

        CategoryAxis axis = barChart.getCategoryPlot().getDomainAxis();
        axis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        return new ChartPanel(barChart);
    }

    private static DefaultCategoryDataset getBarChartDataset(String rowKey, Collection<Tuple<String, Number>> data) {
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Tuple<String, Number> i : data) {
            dataset.addValue(i.getY(), rowKey, i.getX());
        }

        return dataset;
    }

    public static JPanel getGraphCard(GraphSpecs spec, GuiColors guiColors) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(guiColors.getGuiPrimeColor());
        card.setBorder(BorderFactory.createMatteBorder(0, 4, 4, 4, guiColors.getGuiBackgroundColor()));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(guiColors.getGuiPrimeColor());
        topPanel.setPreferredSize(new Dimension(100, 50));

        TitleLabel titleLabel = new TitleLabel(spec.getTitle(), TitleLabel.CENTER, 16, guiColors);
        titleLabel.setForeground(guiColors.getGuiTextColor());
        topPanel.add(titleLabel, BorderLayout.CENTER);

        JPanel graph = GraphManager.createBarChar(spec);

        graph.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, guiColors.getGuiTextColor()));
        card.add(topPanel, BorderLayout.NORTH);
        card.add(graph, BorderLayout.CENTER);

        return card;
    }

    public static JPanel getGraphCard(CompareGraphSpec cmpGraphSpec, GuiColors guiColors) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(guiColors.getGuiPrimeColor());
        card.setBorder(BorderFactory.createMatteBorder(0, 4, 4, 4, guiColors.getGuiBackgroundColor()));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(guiColors.getGuiPrimeColor());
        topPanel.setPreferredSize(new Dimension(100, 50));

        TitleLabel titleLabel = new TitleLabel(cmpGraphSpec.getGraphTitle(), TitleLabel.CENTER, 16, guiColors);
        titleLabel.setForeground(guiColors.getGuiTextColor());
        topPanel.add(titleLabel, BorderLayout.CENTER);

        //organize by date
        List<Tuple<String, Tuple<String, Number>>> sortedData = new LinkedList<>();

        for (GraphSpecs i : cmpGraphSpec.getGraphSpecs()) {
            for (Tuple<String, Number> j : i.getData())
                sortedData.add(new Tuple<String, Tuple<String, Number>>(i.getCampaignName(), j));
        }

        Collections.sort(sortedData, new Comparator<Tuple<String, Tuple<String, Number>>>() {
            @Override
            public int compare(Tuple<String, Tuple<String, Number>> t0, Tuple<String, Tuple<String, Number>> t1) {
                try {
                    Date d0 = Stringifiable.globalDateFormat.parse(t0.getY().getX());
                    Date d1 = Stringifiable.globalDateFormat.parse(t1.getY().getX());

                    if (d0.before(d1)) return -1;
                    else if (d0.after(d1)) return 1;

                } catch (Exception ex) {
                }
                return 0;
            }
        });

        JPanel graph = GraphManager.createBarChar(cmpGraphSpec.getxAxis(), cmpGraphSpec.getyAxis(), sortedData);

        graph.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, guiColors.getGuiTextColor()));
        card.add(topPanel, BorderLayout.NORTH);
        card.add(graph, BorderLayout.CENTER);

        return card;
    }

    public static void setGraphDescription(GraphSpecs graphSpecs) {
        String yAxis = getGraphShortTitle(graphSpecs.getMetric());
        String xAxis = getFormattedTimeSpan(graphSpecs.getTimespan());
        String title = yAxis + " / " + xAxis;

        if (graphSpecs.getMetric() == GraphSpecs.METRICS.NumberBounces)
            title += " [" + getFormattedBounceDef(graphSpecs.getBounceDef()) + "]";

        graphSpecs.setTitle(title);
        graphSpecs.setxAxisName(xAxis);
        graphSpecs.setyAxisName(yAxis);
    }

    public static String getGraphTitle(GraphSpecs.METRICS m, GraphSpecs.TIME_SPAN t, GraphSpecs.BOUNCE_DEF b) {
        String xAxis = getFormattedTimeSpan(t);
        String title = getGraphShortTitle(m) + " / " + xAxis;

        if (m == GraphSpecs.METRICS.NumberBounces)
            title += " [" + getFormattedBounceDef(b) + "]";

        return title;
    }

    public static String getGraphShortTitle(GraphSpecs.METRICS m) {
        if (m == GraphSpecs.METRICS.NumberImpressions) return "Impression";
        else if (m == GraphSpecs.METRICS.NumberClicks) return "Click";
        else if (m == GraphSpecs.METRICS.NumberUniques) return "Unique";
        else if (m == GraphSpecs.METRICS.NumberBounces) return "Bounce";
        else if (m == GraphSpecs.METRICS.NumberConversions) return "Conversion";
        else if (m == GraphSpecs.METRICS.TotalCost) return "Total Cost";
        else if (m == GraphSpecs.METRICS.BounceRate) return "Bounce Rate";
        else return m.toString();
    }

    public static String getFormattedTimeSpan(GraphSpecs.TIME_SPAN timeSpan) {
        if (timeSpan == GraphSpecs.TIME_SPAN.MONTH_SPAN) return "Month";
        if (timeSpan == GraphSpecs.TIME_SPAN.DAY_SPAN) return "Day";
        else if (timeSpan == GraphSpecs.TIME_SPAN.WEEK_SPAN) return "Week";
        else return "Hour";
    }

    public static String getFormattedBounceDef(GraphSpecs.BOUNCE_DEF bounceDef) {
        if (bounceDef == GraphSpecs.BOUNCE_DEF.NPAGES) return "Pages";
        else return "Time";
    }
}
