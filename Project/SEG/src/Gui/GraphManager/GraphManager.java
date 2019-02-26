package Gui.GraphManager;

import Commons.Tuple;
import Gui.GuiColors;
import java.awt.BasicStroke;
import java.util.Collection;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class GraphManager
{

    public static enum ChartType
    {
        LINECHART, HISTOGRAM
    }

    public GraphManager()
    {

    }

    public static JPanel createChart(ChartType type, Collection<Tuple<Number, Number>> data, String xAxisLabel, String yAxisLabel)
    {
        if (type == ChartType.HISTOGRAM)
        {
            return createHistogram(data, xAxisLabel, yAxisLabel);
        } else
        {
            return createLineChart(data, xAxisLabel, yAxisLabel);
        }
    }

    private static JPanel createHistogram(Collection<Tuple<Number, Number>> data, String xAxisLabel, String yAxisLabel)
    {
        return null;
    }

    private static JPanel createLineChart(Collection<Tuple<Number, Number>> data, String xAxisLabel, String yAxisLabel)
    {
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
        chart.setBackgroundPaint(GuiColors.LIGHT_GRAY);

        //plot init
        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, GuiColors.RED);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(GuiColors.LIGHT);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(GuiColors.LIGHT_GRAY);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(GuiColors.DARK_LIGHT);

        return new ChartPanel(chart);
    }

    private static XYSeriesCollection getLineChartDataset(Collection<Tuple<Number, Number>> data)
    {
        XYSeries series = new XYSeries(""); //TODO accept multiple series (next increments)
        for (Tuple<Number, Number> i : data)
        {
            series.add(i.getX(), i.getY());
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        return dataset;
    }
}
