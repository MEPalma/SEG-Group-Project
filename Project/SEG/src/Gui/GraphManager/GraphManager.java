package Gui.GraphManager;

import Commons.Tuple;
import Gui.GuiColors;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
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
        renderer.setSeriesPaint(0, GuiColors.BASE_LIGHT);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(GuiColors.DARK_GRAY);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(GuiColors.LIGHT_GRAY);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(GuiColors.LIGHT_GRAY);

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

    public static JPanel createBarChar(Collection<Tuple<String, Number>> data, String xAxisLabel, String yAxisLabel)
    {
        JFreeChart barChart = ChartFactory.createBarChart(
                "",
                xAxisLabel,
                yAxisLabel,
                getBarChartDataset(data),
                PlotOrientation.VERTICAL,
                true, true, false);
        barChart.setBorderVisible(false);
        barChart.setAntiAlias(true);
        barChart.setBackgroundPaint(Color.WHITE);

        XYPlot plot = barChart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, GuiColors.BASE_LIGHT);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(GuiColors.DARK_GRAY);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(GuiColors.LIGHT_GRAY);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(GuiColors.LIGHT_GRAY);

        ChartPanel chartPanel = new ChartPanel( barChart );

        return chartPanel;
    }

    private static DefaultCategoryDataset getBarChartDataset(Collection<Tuple<String, Number>> data)
    {
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );

        for (Tuple<String, Number> i : data) {
            dataset.addValue(i.getY(), i.getX(), "");
        }

        return dataset;
    }

}
