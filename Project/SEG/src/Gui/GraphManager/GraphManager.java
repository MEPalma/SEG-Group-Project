package Gui.GraphManager;

import Commons.GraphSpecs;
import Commons.Tuple;
import Gui.GuiColors;
import Gui.GuiComponents.TitleLabel;
import com.sun.corba.se.impl.orbutil.graph.Graph;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public class GraphManager {

    public static JPanel createBarChar(GraphSpecs graphSpecs) {
        JFreeChart barChart = ChartFactory.createBarChart(
                "",
                graphSpecs.getxAxisName(),
                graphSpecs.getyAxisName(),
                getBarChartDataset(graphSpecs.getData()),
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
        r.setSeriesPaint(0, graphSpecs.getTypeColor());

        Plot plot = barChart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);

        barChart.getCategoryPlot().getRangeAxis().setLabelFont(new Font("Verdana", Font.PLAIN, 12));
        barChart.getCategoryPlot().getDomainAxis().setLabelFont(barChart.getCategoryPlot().getRangeAxis().getLabelFont());

        barChart.getCategoryPlot().getRangeAxis().setTickLabelFont(new Font("Verdana", Font.PLAIN, 8));
        barChart.getCategoryPlot().getDomainAxis().setTickLabelFont(barChart.getCategoryPlot().getRangeAxis().getTickLabelFont());

        if (graphSpecs.getData().size() > 24) barChart.getCategoryPlot().getDomainAxis().setTickLabelsVisible(false);

        CategoryAxis axis = barChart.getCategoryPlot().getDomainAxis();
        axis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        return new ChartPanel(barChart);
    }

    private static DefaultCategoryDataset getBarChartDataset(Collection<Tuple<String, Number>> data) {
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Tuple<String, Number> i : data) {
            dataset.addValue(i.getY(), "", i.getX());
        }

        return dataset;
    }

    public static JPanel getGraphCard(GraphSpecs spec) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(spec.getTypeColor());
        card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(spec.getTypeColor());
        topPanel.setPreferredSize(new Dimension(100, 50));

        TitleLabel titleLabel = new TitleLabel(spec.getTitle(), TitleLabel.CENTER, 16);
        titleLabel.setForeground(GuiColors.BASE_WHITE);
        topPanel.add(titleLabel, BorderLayout.CENTER);

        JPanel graph = GraphManager.createBarChar(spec);

        graph.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, GuiColors.BASE_WHITE));
        card.add(topPanel, BorderLayout.NORTH);
        card.add(graph, BorderLayout.CENTER);

        return card;
    }

    public static void setGraphDescription(GraphSpecs graphSpecs) {
        String yAxis = getGraphShortTitle(graphSpecs);
        String xAxis = parseTimeSpan(graphSpecs.getTimespan());
        String title = getGraphShortTitle(graphSpecs) + " / " + xAxis;

        if (graphSpecs.getMetric() == GraphSpecs.METRICS.BounceRate)
            title += " [" + parseBounceDef(graphSpecs.getBounceDef()) + "]";

        graphSpecs.setTitle(title);
        graphSpecs.setxAxisName(xAxis);
        graphSpecs.setyAxisName(yAxis);
    }

    public static String getGraphShortTitle(GraphSpecs graphSpecs) {
        GraphSpecs.METRICS m = graphSpecs.getMetric();
        if (m == GraphSpecs.METRICS.NumberImpressions) return "Impressions";
        else if (m == GraphSpecs.METRICS.NumberClicks) return "Clicks";
        else if (m == GraphSpecs.METRICS.NumberUniques) return "Uniques";
        else if (m == GraphSpecs.METRICS.NumberBounces) return "Bounces";
        else if (m == GraphSpecs.METRICS.NumberConversions) return "Conversions";
        else if (m == GraphSpecs.METRICS.TotalCost) return "Total Cost";
        else if (m == GraphSpecs.METRICS.BounceRate) return "Bounce Rate";
        else return m.toString();
    }

    private static String parseTimeSpan(GraphSpecs.TIME_SPAN timeSpan) {
        if (timeSpan == GraphSpecs.TIME_SPAN.DAY_SPAN) return "Day";
        else if (timeSpan == GraphSpecs.TIME_SPAN.WEEK_SPAN) return "Week";
        else return "Hour";
    }

    private static String parseBounceDef(GraphSpecs.BOUNCE_DEF bounceDef) {
        if (bounceDef == GraphSpecs.BOUNCE_DEF.NPAGES) return "Pages";
        else return "Time";
    }
}
