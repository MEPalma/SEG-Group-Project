package Gui;

import Commons.FilterSpecs;
import Commons.GraphSpecs;
import Commons.Tuple;
import DatabaseManager.DataExchange;
import DatabaseManager.DatabaseManager;
import DatabaseManager.Stringifiable;
import Gui.GraphManager.GraphManager;
import Gui.TabbedView.TabbedView;

import javax.swing.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MainController {
    private final DataExchange dataExchange;
    private final TabbedView tabbedView;
    private final List<SwingWorker> dataLoadingTasks;
    private final StatusDisplay statusDisplay;

    public MainController(StatusDisplay statusDisplay, TabbedView tabbedView) {
        this.dataExchange = new DataExchange(new DatabaseManager());
        this.tabbedView = tabbedView;
        this.statusDisplay = statusDisplay;
        this.dataLoadingTasks = new LinkedList<>();
        this.filterSpecs = new FilterSpecs();
        clearFiltersSpecs();
    }

    public void addDataLoadingTask(SwingWorker newTask) {
        this.dataLoadingTasks.add(newTask);
    }

    private void killDataLoadingTasks() {
        synchronized (this.dataLoadingTasks) {
            for (SwingWorker task : this.dataLoadingTasks)
                task.cancel(true);

            this.dataLoadingTasks.clear();
        }
    }

    public void removeDataLoadingTask(SwingWorker task) {
        synchronized (this.dataLoadingTasks) {
            this.dataLoadingTasks.remove(task);
        }
    }

    public void startProgressBar() {
        this.statusDisplay.startProgressBar();
    }

    public void stopProgressBar() {
        this.statusDisplay.clear();
    }

    public void close() {
        killDataLoadingTasks();
        this.dataExchange.close();
    }

    public void showErrorMessage(String title, String details) {
        this.statusDisplay.showErrorMessage(title, details);
    }

    public DataExchange getDataExchange() {
        return this.dataExchange;
    }

    public String getCampaignName() {
        return this.dataExchange.getCampaignName();
    }

    public void setCampaignName(String name) {
        this.dataExchange.setCampaignName(name);
    }

    public List<Tuple<String, Number>> getGraphSpecData(GraphSpecs graphSpecs) {
        return this.dataExchange.getGraphData(graphSpecs);
    }

    public Date getStartDate() {
        return this.dataExchange.getStartDate();
    }

    public Date getEndDate() {
        return this.dataExchange.getEndDate();
    }


    /*
        GRAPHS
    */

    public GraphSpecs proposeNewGraph(GraphSpecs.METRICS metrics, GraphSpecs.TIME_SPAN time_span, GraphSpecs.BOUNCE_DEF bounce_def) {
        GraphSpecs graphSpecs = new GraphSpecs(metrics, time_span, bounce_def, getFilterSpecs());

        if (this.tabbedView.containsComparable(graphSpecs)) return null;
        else return graphSpecs;
    }

    public void pushToGraphView(GraphSpecs newGraphSpecs) {

        SwingWorker task = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                startProgressBar();
                newGraphSpecs.setData(getGraphSpecData(newGraphSpecs));
                GraphManager.setGraphDescription(newGraphSpecs);

                return null;
            }

            @Override
            protected void done() {
                tabbedView.push(GraphManager.getGraphShortTitle(newGraphSpecs), newGraphSpecs.getTypeColor(), GraphManager.getGraphCard(newGraphSpecs), newGraphSpecs);
                stopProgressBar();
                removeDataLoadingTask(this);
                super.done();
            }
        };

        addDataLoadingTask(task);
        task.execute();
    }


    /*
        FILTERS
     */

    private final FilterSpecs filterSpecs;

    public FilterSpecs getFilterSpecs() {
        return this.filterSpecs;
    }

    public void updateFilterSpecs(FilterSpecs newFilterSpecs) {
        synchronized (this.filterSpecs) {
            this.filterSpecs.setAges(newFilterSpecs.getAges());
            this.filterSpecs.setContexts(newFilterSpecs.getContexts());
            this.filterSpecs.setStartDate(Stringifiable.globalDateFormat.format(getStartDate()));
            this.filterSpecs.setEndDate(Stringifiable.globalDateFormat.format(getEndDate()));
            this.filterSpecs.setGenders(newFilterSpecs.getGenders());
            this.filterSpecs.setIncomes(newFilterSpecs.getIncomes());
        }

        refreshGraphs();
    }

    public void refreshGraphs() {
        SwingWorker task = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                startProgressBar();

                List<Object> graphSpecs = tabbedView.getAllComparables();

                tabbedView.clear();

                for (Object g : graphSpecs) {
                    GraphSpecs tmp = (GraphSpecs) g;
                    tmp.setData(dataExchange.getGraphData(tmp));
                    pushToGraphView(tmp);
                }
                return null;
            }

            @Override
            protected void done() {
                stopProgressBar();
                removeDataLoadingTask(this);
                super.done();
            }
        };

        addDataLoadingTask(task);
        task.execute();
    }

    public void clearFiltersSpecs() {
        updateFilterSpecs(new FilterSpecs());
    }

}
