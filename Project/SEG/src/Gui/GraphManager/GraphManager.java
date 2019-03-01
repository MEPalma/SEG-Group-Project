package Gui.GraphManager;

import Commons.Tuple;
import Gui.GuiColors;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public class GraphManager {

    public static enum ChartType {
        LINE_CHART
    }

    public static JPanel createChart(ChartType type, Collection<Tuple<Number, Number>> data, String xAxisLabel, String yAxisLabel) {
        return createLineChart(data, xAxisLabel, yAxisLabel);
    }

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
        chart.setBackgroundPaint(GuiColors.LIGHT);

        //plot init
        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, GuiColors.RED);
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
}
