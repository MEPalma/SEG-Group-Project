package Gui;

import Commons.CompareGraphSpec;
import Commons.FilterSpecs;
import Commons.GraphSpecs;
import Commons.Tuple;
import DatabaseManager.DataExchange;
import DatabaseManager.DatabaseManager;
import DatabaseManager.Stringifiable;
import Gui.GraphManager.GraphManager;
import Gui.TabbedView.TabbedView;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MainController {
    private final DataExchange dataExchange;
    private final TabbedView tabbedView;
    private final List<SwingWorker> dataLoadingTasks;
    private final GuiColors guiColors;
    private final StatusDisplay statusDisplay;
    private final Gui gui;

    public MainController(Gui gui, StatusDisplay statusDisplay, TabbedView tabbedView, GuiColors guiColors) {
        this.dataExchange = new DataExchange(new DatabaseManager());
        this.gui = gui;
        this.tabbedView = tabbedView;
        this.statusDisplay = statusDisplay;
        this.dataLoadingTasks = new LinkedList<>();

        this.guiColors = guiColors;
        updateGuiColors();
    }

    public void updateGuiColors() {
        this.guiColors.setGuiPrimeColor(this.dataExchange.getPrimeColor());
        this.guiColors.setGuiOptionColor(this.dataExchange.getOptionColor());
        this.guiColors.setGuiTextColor(this.dataExchange.getTextColor());
        this.guiColors.setGuiBackgroundColor(this.dataExchange.getBackgroundColor());
    }

    public GuiColors getGuiColors() {
        return this.guiColors;
    }

    public void addDataLoadingTask(SwingWorker newTask) {
        this.dataLoadingTasks.add(newTask);
    }

    private void killDataLoadingTasks() {
        synchronized (this.dataLoadingTasks) {
            for (SwingWorker task : this.dataLoadingTasks)
                task.cancel(true);
        }
        this.dataLoadingTasks.clear();
    }

    public void removeDataLoadingTask(SwingWorker task) {
        synchronized (this.dataLoadingTasks) {
            this.dataLoadingTasks.remove(task);
        }
    }

    public void startProgressBar() {
        this.statusDisplay.newProgressBar();
    }

    public void stopProgressBar() {
        this.statusDisplay.killProgressBar();
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

    public String getCampaignName(int id) {
        return this.dataExchange.getCampaignName(id);
    }

    public void setCampaignName(int id, String name) {
        this.dataExchange.setCampaignName(id, name);
//        this.gui.updateCampaignName();
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

    public boolean isDbEmpty() {
        return this.dataExchange.isEmpty();
    }


    /*
        FILTERS
     */

    public void pushToGraphView(GraphSpecs newGraphSpecs) {

        SwingWorker task = new SwingWorker() {
            @Override
            protected Object doInBackground() {
                startProgressBar();
                newGraphSpecs.getFilterSpecs().updateFrom(getInitFilters());
                newGraphSpecs.setData(getGraphSpecData(newGraphSpecs));
                GraphManager.setGraphDescription(newGraphSpecs);

                return null;
            }

            @Override
            protected void done() {
                TakeActionListener updateOnClick = new TakeActionListener() {
                    @Override
                    public void takeAction() {
                        if (isFiltersShowing()) {
                            openFiltersMenu();
                        }
                    }
                };

                tabbedView.push(GraphManager.getGraphShortTitle(newGraphSpecs.getMetric()), GraphManager.getGraphCard(newGraphSpecs, guiColors), newGraphSpecs, updateOnClick);
                stopProgressBar();
                removeDataLoadingTask(this);
                super.done();
            }
        };

        addDataLoadingTask(task);
        task.execute();
    }

    public void pushToGraphView(CompareGraphSpec cmpGraphSpec) {
        SwingWorker task = new SwingWorker() {
            @Override
            protected Object doInBackground() {
                startProgressBar();

                for (GraphSpecs g : cmpGraphSpec.getGraphSpecs()) {
                    g.getFilterSpecs().updateFrom(getInitFilters());
                    g.setData(getGraphSpecData(g));
                }

                return null;
            }

            @Override
            protected void done() {
                TakeActionListener updateOnClick = new TakeActionListener() {
                    @Override
                    public void takeAction() {
                        if (isFiltersShowing()) {
                            openFiltersMenu();
                        }
                    }
                };

                tabbedView.push(
                        cmpGraphSpec.getCardTitle(),
                        GraphManager.getGraphCard(cmpGraphSpec, guiColors),
                        cmpGraphSpec,//TODO check
                        updateOnClick);
                stopProgressBar();
                removeDataLoadingTask(this);
                super.done();
            }
        };

        addDataLoadingTask(task);
        task.execute();
    }

    public FilterSpecs getInitFilters() {
        FilterSpecs newFilters = new FilterSpecs();
        newFilters.setStartDate(Stringifiable.globalDateFormat.format(getStartDate()));
        newFilters.setEndDate(Stringifiable.globalDateFormat.format(getEndDate()));
        return newFilters;
    }

    public void clearFilter(FilterSpecs filterSpecs) {
        filterSpecs.getAges().clear();
        filterSpecs.getContexts().clear();
        filterSpecs.getGenders().clear();
        filterSpecs.getIncomes().clear();
        filterSpecs.setStartDate(Stringifiable.globalDateFormat.format(getStartDate()));
        filterSpecs.setEndDate(Stringifiable.globalDateFormat.format(getEndDate()));
    }

    public void refreshGraph(GraphSpecs graphSpecs) {
        killDataLoadingTasks();

        SwingWorker task = new SwingWorker() {
            @Override
            protected Object doInBackground() {
                startProgressBar();
                graphSpecs.setData(getGraphSpecData(graphSpecs));
                return null;
            }

            @Override
            protected void done() {
                tabbedView.replaceOnComparable(
                        GraphManager.getGraphShortTitle(graphSpecs.getMetric()),
                        GraphManager.getGraphCard(graphSpecs, guiColors),
                        graphSpecs);

                stopProgressBar();

                super.done();
            }
        };

        addDataLoadingTask(task);
        task.execute();
    }

    public void refreshGraph(CompareGraphSpec cmpGraphSpec) {
        killDataLoadingTasks();

        SwingWorker task = new SwingWorker() {
            @Override
            protected Object doInBackground() {
                startProgressBar();

                for (GraphSpecs g : cmpGraphSpec.getGraphSpecs()) {
//                    g.getFilterSpecs().updateFrom(cmpGraphSpec.getFilterSpecs());
                    g.setData(getGraphSpecData(g));
                }

                return null;
            }

            @Override
            protected void done() {
                tabbedView.replaceOnComparable(
                        cmpGraphSpec.getGraphTitle(),
                        GraphManager.getGraphCard(cmpGraphSpec, guiColors),
                        cmpGraphSpec);

                stopProgressBar();

                super.done();
            }
        };

        addDataLoadingTask(task);
        task.execute();
    }

    public void refreshGraphs() {
        killDataLoadingTasks();

        List<Object> graphSpecs = this.tabbedView.getAllComparables();

        tabbedView.clear();

        for (Object g : graphSpecs)
            pushToGraphView((GraphSpecs) g);
    }

    public Object getSelectedGraph() {
        return this.tabbedView.getSelectedComparable();
    }

    public void openFiltersMenu() {
        this.gui.openFilters();
    }

    public void openAddGraphMenu() {
        this.gui.openAddGraph();
    }

    public boolean isFiltersShowing() {
        return this.gui.isFiltersShowing();
    }

}
