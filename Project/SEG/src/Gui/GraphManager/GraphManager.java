package Gui.GraphManager;

import Commons.Tuple;
import Gui.GuiColors;
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

    private static JPanel createLineChart(Collection<Tuple<Number, Number>> data, String xAxisLabel, String yAxisLabel) {
        //copy data
        XYSeriesCollection dataset = getLineChartDataset(data);

        //Create chart
        JFreeChart chart = ChartFactory.createXYLineChart(
                "",
                xAxisLabel,
                yAxisLabel,
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );
        chart.setBorderVisible(false);
        chart.setAntiAlias(true);
        chart.setBackgroundPaint(Color.WHITE);

        //plot init
        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, GuiColors.BASE_WHITE);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(GuiColors.DARK_GRAY);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(GuiColors.LIGHT_GRAY);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(GuiColors.LIGHT_GRAY);

        plot.getDomainAxis().setLabelFont(new Font("Verdana", Font.PLAIN, 8));
        plot.getRangeAxis().setLabelFont(plot.getDomainAxis().getLabelFont());

        return new ChartPanel(chart);
    }

    private static XYSeriesCollection getLineChartDataset(Collection<Tuple<Number, Number>> data) {
        XYSeries series = new XYSeries(""); //TODO accept multiple series (next increments)
        for (Tuple<Number, Number> i : data) {
            series.add(i.getX(), i.getY());
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        return dataset;
    }

    public static JPanel createBarChar(Collection<Tuple<String, Number>> data, String xAxisLabel, String yAxisLabel) {
        JFreeChart barChart = ChartFactory.createBarChart(
                "",
                xAxisLabel,
                yAxisLabel,
                getBarChartDataset(data),
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
        r.setSeriesPaint(0, GuiColors.TEXT_ORANGE_UNSELECTED);

        Plot plot = barChart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);

        barChart.getCategoryPlot().getRangeAxis().setLabelFont(new Font("Verdana", Font.PLAIN, 12));
        barChart.getCategoryPlot().getDomainAxis().setLabelFont( barChart.getCategoryPlot().getRangeAxis().getLabelFont());

        barChart.getCategoryPlot().getRangeAxis().setTickLabelFont(new Font("Verdana", Font.PLAIN, 8));
        barChart.getCategoryPlot().getDomainAxis().setTickLabelFont( barChart.getCategoryPlot().getRangeAxis().getTickLabelFont());

        if (data.size() > 24) barChart.getCategoryPlot().getDomainAxis().setTickLabelsVisible(false);

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

}
