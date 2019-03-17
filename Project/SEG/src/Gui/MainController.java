package Gui;

import Commons.FilterSpecs;
import Commons.GraphSpecs;
import Commons.Tuple;
import DatabaseManager.DataExchange;
import DatabaseManager.DatabaseManager;
import DatabaseManager.Stringifiable;
import Gui.BreadCrumbs.BreadCrumbs;
import Gui.BreadCrumbs.BreadCrumbsHoster;
import Gui.GraphManager.GraphManager;
import Gui.GuiComponents.RPanel;
import Gui.TabbedView.TabbedView;

import javax.swing.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MainController {
    private final DataExchange dataExchange;
    private final BreadCrumbsHoster breadCrumbsHoster;
    private final BreadCrumbs breadCrumbs;
    private final TabbedView tabbedView;
    private final List<SwingWorker> dataLoadingTasks;

    public MainController(TabbedView tabbedView) {
        this.dataExchange = new DataExchange(new DatabaseManager());
        this.breadCrumbsHoster = new BreadCrumbsHoster();
        this.breadCrumbs = this.breadCrumbsHoster.getBreadCrumbs();
        this.tabbedView = tabbedView;
        this.dataLoadingTasks = new LinkedList<>();
        this.filterSpecs = new FilterSpecs();
        clearFiltersSpecs();
    }

    public void setMainBackgroundTask(SwingWorker newTask) {
        this.breadCrumbs.updateBackgroundTask(newTask);
    }

    public void killMainBackgroundTask() {
        this.breadCrumbs.cancelBackgroundTask();
    }

    private void addDataLoadingTask(SwingWorker newTask) {
        this.dataLoadingTasks.add(newTask);
    }

    private void killDataLoadingTasks() {
        synchronized (this.dataLoadingTasks) {
            for (SwingWorker task : this.dataLoadingTasks)
                task.cancel(true);

            this.dataLoadingTasks.clear();
        }
    }

    private void removeDataLoadingTask(SwingWorker task) {
        synchronized (this.dataLoadingTasks) {
            this.dataLoadingTasks.remove(task);
        }
    }

    public void startProgressBar() {
        this.breadCrumbsHoster.startProgressBar();
    }

    public void stopProgressBar() {
        this.breadCrumbsHoster.stopProgressBar();
    }

    public void close() {
        killMainBackgroundTask();
        killDataLoadingTasks();
        this.dataExchange.close();
    }

    public void showErrorMessage(String title, String details) {
        this.breadCrumbs.showErrorMessage(title, details);
    }

    public BreadCrumbsHoster getBreadCrumbsHoster() {
        return breadCrumbsHoster;
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

    public void pushNewViewOnBreadCrumbs(String title, RPanel view) {
        this.breadCrumbs.push(title, view);
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

        //TODO do me in backgorund!!!!!!!!!!!
        newGraphSpecs.setData(getGraphSpecData(newGraphSpecs));
        GraphManager.setGraphDescription(newGraphSpecs);

        this.tabbedView.push(GraphManager.getGraphShortTitle(newGraphSpecs), newGraphSpecs.getTypeColor(), GraphManager.getGraphCard(newGraphSpecs), newGraphSpecs);
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

        //TODO do me in background!!!!!!!!!!
        startProgressBar();

        List<Object> graphSpecs = this.tabbedView.getAllComparables();

        this.tabbedView.clear();

        for (Object g : graphSpecs) {
            GraphSpecs tmp = (GraphSpecs) g;
            tmp.setData(this.dataExchange.getGraphData(tmp));
            this.pushToGraphView(tmp);
        }

        stopProgressBar();
    }

    public void clearFiltersSpecs() {
        updateFilterSpecs(new FilterSpecs());
    }

}
