package Gui;

import Commons.Tuple;
import DatabaseManager.DataExchange;
import DatabaseManager.DatabaseManager;

import javax.swing.*;
import java.util.Collection;

public class MainController
{
    private final DataExchange dataExchange;
    private final BreadCrumbsHoster breadCrumbsHoster;
    private final BreadCrumbs breadCrumbs;
    private final GraphView graphView;

    public MainController()
    {
        this.dataExchange = new DataExchange(new DatabaseManager());
        this.breadCrumbsHoster = new BreadCrumbsHoster();
        this.breadCrumbs = this.breadCrumbsHoster.getBreadCrumbs();
        this.graphView = this.breadCrumbsHoster.getBreadCrumbs().getGraphView();
    }

    public void setBackgroundTask(SwingWorker newTask)
    {
        this.breadCrumbs.updateBackgroundTask(newTask);
    }

    public void killBackgroundTask()
    {
        this.breadCrumbs.cancelBackgroundTask();
    }

    public void startProgressBar()
    {
        this.breadCrumbs.startProgressBar();
    }

    public void stopProgressBar()
    {
        this.breadCrumbs.stopProgressBar();
    }

    public void close()
    {
        killBackgroundTask();
        this.dataExchange.close();
    }

    public BreadCrumbsHoster getBreadCrumbsHoster()
    {
        return breadCrumbsHoster;
    }

    public DataExchange getDataExchange()
    {
        return this.dataExchange;
    }

    public void pushNewViewOnBreadCrumbs(String title, RPanel view)
    {
        this.breadCrumbs.push(title, view);
    }

    public boolean doesGraphViewContainGraph(String graphId)
    {
        return this.graphView.containsGraph(graphId);
    }

    public void pushToGraphView(GraphSpecs newGraphSpecs)
    {
        this.graphView.pushGraphSpecs(newGraphSpecs);
    }

    public void pushToGraphView(String id, String title, String xAxisName, String yAxisName, Collection<Tuple<String, Number>> chartData)
    {
        this.pushToGraphView(new GraphSpecs(id, title, xAxisName, yAxisName, chartData));
    }

    public void popFromGraphView(String graphId)
    {
        this.graphView.popGraphSpecs(graphId);
    }
}
