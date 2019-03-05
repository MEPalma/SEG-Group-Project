package Gui;

import Commons.Tuple;
import DatabaseManager.DataExchange;
import DatabaseManager.DatabaseManager;
import Gui.BreadCrumbs.BreadCrumbs;
import Gui.BreadCrumbs.BreadCrumbsHoster;
import Gui.GuiComponents.RPanel;

import javax.swing.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class MainController {
    private final DataExchange dataExchange;
    private final BreadCrumbsHoster breadCrumbsHoster;
    private final BreadCrumbs breadCrumbs;
    private final GraphView graphView;
    private final List<SwingWorker> dataLoadingTasks;

    public MainController() {
        this.dataExchange = new DataExchange(new DatabaseManager());
        this.breadCrumbsHoster = new BreadCrumbsHoster();
        this.breadCrumbs = this.breadCrumbsHoster.getBreadCrumbs();
        this.graphView = this.breadCrumbsHoster.getBreadCrumbs().getGraphView();
        this.dataLoadingTasks = new LinkedList<>();
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
        this.breadCrumbs.startProgressBar();
    }

    public void stopProgressBar() {
        this.breadCrumbs.stopProgressBar();
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

    public boolean doesGraphViewContainGraph(String graphId) {
        return this.graphView.containsGraph(graphId);
    }

    public void pushToGraphView(GraphSpecs newGraphSpecs) {
        this.graphView.pushGraphSpecs(newGraphSpecs);
    }

    public void pushToGraphView(String id, String title, String xAxisName, String yAxisName, Collection<Tuple<String, Number>> chartData, GraphSpecs.Type type) {
        this.pushToGraphView(new GraphSpecs(id, title, xAxisName, yAxisName, chartData, type));
    }

    public void popFromGraphView(String graphId) {
        this.graphView.popGraphSpecs(graphId);
    }


    /*
        Graphs
     */

    // Impressions
    public void pushNewNumberOfImpressionsPerWeek(String id) {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "Number of Impressions [Per Week]", "Week", "N. Impressions", dataExchange.getNumberOfImpressionsPerWeek(), GraphSpecs.Type.WEEK_SPAN);
                return null;
            }

            @Override
            protected void done() {
                stopProgressBar();
                pushToGraphView(tmp);
            }
        };
        addDataLoadingTask(backgroundTask);
        backgroundTask.execute();
        removeDataLoadingTask(backgroundTask);
    }

    public void pushNewNumberOfImpressionsPerDay(String id) {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "Number of Impressions [Per Day]", "Day", "N. Impressions", dataExchange.getNumberOfImpressionsPerDay(), GraphSpecs.Type.DAY_SPAN);
                return null;
            }

            @Override
            protected void done() {
                stopProgressBar();
                pushToGraphView(tmp);
            }
        };
        addDataLoadingTask(backgroundTask);
        backgroundTask.execute();
        removeDataLoadingTask(backgroundTask);
    }

    public void pushNewNumberOfImpressionsPerHour(String id) {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "Number of Impressions [Per Hour]", "Hour", "N. Impressions", dataExchange.getNumberOfImpressionsPerHour(), GraphSpecs.Type.HOUR_SPAN);
                return null;
            }

            @Override
            protected void done() {
                stopProgressBar();
                pushToGraphView(tmp);
            }
        };
        addDataLoadingTask(backgroundTask);
        backgroundTask.execute();
        removeDataLoadingTask(backgroundTask);
    }

    //Clicks
    public void pushNewNumberOfClicksPerWeek(String id) {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "Number of Impressions [Per Week]", "Week", "N. Clicks", dataExchange.getNumberOfClicksPerWeek(), GraphSpecs.Type.WEEK_SPAN);
                return null;
            }

            @Override
            protected void done() {
                stopProgressBar();
                pushToGraphView(tmp);
            }
        };
        addDataLoadingTask(backgroundTask);
        backgroundTask.execute();
        removeDataLoadingTask(backgroundTask);
    }

    public void pushNewNumberOfClicksPerDay(String id) {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "Number of Click [Per Day]", "Day", "N. Clicks", dataExchange.getNumberOfClicksPerDay(), GraphSpecs.Type.DAY_SPAN);
                return null;
            }

            @Override
            protected void done() {
                stopProgressBar();
                pushToGraphView(tmp);
            }
        };
        addDataLoadingTask(backgroundTask);
        backgroundTask.execute();
        removeDataLoadingTask(backgroundTask);
    }

    public void pushNewNumberOfClicksPerHour(String id) {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "Number of Clicks [Per Hour]", "Hour", "N. Clicks", dataExchange.getNumberOfClicksPerHour(), GraphSpecs.Type.HOUR_SPAN);
                return null;
            }

            @Override
            protected void done() {
                stopProgressBar();
                pushToGraphView(tmp);
            }
        };
        addDataLoadingTask(backgroundTask);
        backgroundTask.execute();
        removeDataLoadingTask(backgroundTask);
    }

    //Uniques
    public void pushNewNumberOfUniquesPerWeek(String id) {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "Number of Uniques [Per Week]", "Week", "N. Uniques", dataExchange.getNumberOfUniquesPerWeek(), GraphSpecs.Type.WEEK_SPAN);
                return null;
            }

            @Override
            protected void done() {
                stopProgressBar();
                pushToGraphView(tmp);
            }
        };
        addDataLoadingTask(backgroundTask);
        backgroundTask.execute();
        removeDataLoadingTask(backgroundTask);
    }

    public void pushNewNumberOfUniquesPerDay(String id) {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "Number of Uniques [Per Day]", "Day", "N. Uniques", dataExchange.getNumberOfUniquesPerDay(), GraphSpecs.Type.DAY_SPAN);
                return null;
            }

            @Override
            protected void done() {
                stopProgressBar();
                pushToGraphView(tmp);
            }
        };
        addDataLoadingTask(backgroundTask);
        backgroundTask.execute();
        removeDataLoadingTask(backgroundTask);
    }

    public void pushNewNumberOfUniquesPerHour(String id) {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "Number of Uniques [Per Hour]", "Hour", "N. Uniques", dataExchange.getNumberOfUniquesPerHour(), GraphSpecs.Type.HOUR_SPAN);
                return null;
            }

            @Override
            protected void done() {
                stopProgressBar();
                pushToGraphView(tmp);
            }
        };
        addDataLoadingTask(backgroundTask);
        backgroundTask.execute();
        removeDataLoadingTask(backgroundTask);
    }

    //Bounces
    public void pushNewNumberOfBouncesPerWeek(String id) {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "Number of Bounces [Per Week]", "Week", "N. Bounces", dataExchange.getNumberOfBouncesPerWeek(), GraphSpecs.Type.WEEK_SPAN);
                return null;
            }

            @Override
            protected void done() {
                stopProgressBar();
                pushToGraphView(tmp);
            }
        };
        addDataLoadingTask(backgroundTask);
        backgroundTask.execute();
        removeDataLoadingTask(backgroundTask);
    }

    public void pushNewNumberOfBouncesPerDay(String id) {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "Number of Bounces [Per Day]", "Day", "N. Bounces", dataExchange.getNumberOfBouncesPerDay(), GraphSpecs.Type.DAY_SPAN);
                return null;
            }

            @Override
            protected void done() {
                stopProgressBar();
                pushToGraphView(tmp);
            }
        };
        addDataLoadingTask(backgroundTask);
        backgroundTask.execute();
        removeDataLoadingTask(backgroundTask);
    }

    public void pushNewNumberOfBouncesPerHour(String id) {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "Number of Bounces [Per Hour]", "Hour", "N. Bounces", dataExchange.getNumberOfBouncesPerHour(), GraphSpecs.Type.HOUR_SPAN);
                return null;
            }

            @Override
            protected void done() {
                stopProgressBar();
                pushToGraphView(tmp);
            }
        };
        addDataLoadingTask(backgroundTask);
        backgroundTask.execute();
        removeDataLoadingTask(backgroundTask);
    }

    //Conversions
    public void pushNewNumberOfConversionsPerWeek(String id) {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "Number of Conversions [Per Week]", "Week", "N. Conversions", dataExchange.getNumberOfConversionsPerWeek(), GraphSpecs.Type.WEEK_SPAN);
                return null;
            }

            @Override
            protected void done() {
                stopProgressBar();
                pushToGraphView(tmp);
            }
        };
        addDataLoadingTask(backgroundTask);
        backgroundTask.execute();
        removeDataLoadingTask(backgroundTask);
    }

    public void pushNewNumberOfConversionsPerDay(String id) {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "Number of Conversions [Per Day]", "Day", "N. Conversions", dataExchange.getNumberOfConversionsPerDay(), GraphSpecs.Type.DAY_SPAN);
                return null;
            }

            @Override
            protected void done() {
                stopProgressBar();
                pushToGraphView(tmp);
            }
        };
        addDataLoadingTask(backgroundTask);
        backgroundTask.execute();
        removeDataLoadingTask(backgroundTask);
    }

    public void pushNewNumberOfConversionsPerHour(String id) {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "Number of Conversions [Per Hour]", "Hour", "N. Conversions", dataExchange.getNumberOfConversionsPerHour(), GraphSpecs.Type.HOUR_SPAN);
                return null;
            }

            @Override
            protected void done() {
                stopProgressBar();
                pushToGraphView(tmp);
            }
        };
        addDataLoadingTask(backgroundTask);
        backgroundTask.execute();
        removeDataLoadingTask(backgroundTask);
    }

    //Total Cost
    public void pushNewNumberOfTotalCostPerWeek(String id) {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "Total Cost [Per Week]", "Week", "Total Cost", dataExchange.getTotalCostPerWeek(), GraphSpecs.Type.WEEK_SPAN);
                return null;
            }

            @Override
            protected void done() {
                stopProgressBar();
                pushToGraphView(tmp);
            }
        };
        addDataLoadingTask(backgroundTask);
        backgroundTask.execute();
        removeDataLoadingTask(backgroundTask);
    }

    public void pushNewNumberOfTotalCostPerDay(String id) {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "Total Cost [Per Day]", "Day", "Total Cost", dataExchange.getTotalCostPerDay(), GraphSpecs.Type.DAY_SPAN);
                return null;
            }

            @Override
            protected void done() {
                stopProgressBar();
                pushToGraphView(tmp);
            }
        };
        addDataLoadingTask(backgroundTask);
        backgroundTask.execute();
        removeDataLoadingTask(backgroundTask);
    }

    public void pushNewNumberOfTotalCostPerHour(String id) {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "Total Cost [Per Hour]", "Hour", "Total Cost", dataExchange.getTotalCostPerHour(), GraphSpecs.Type.HOUR_SPAN);
                return null;
            }

            @Override
            protected void done() {
                stopProgressBar();
                pushToGraphView(tmp);
            }
        };
        addDataLoadingTask(backgroundTask);
        backgroundTask.execute();
        removeDataLoadingTask(backgroundTask);
    }


    //CTR
    public void pushNewCTRPerWeek(String id) {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "CTR [Per Week]", "Week", "CTR", dataExchange.getCTRPerWeek(), GraphSpecs.Type.WEEK_SPAN);
                return null;
            }

            @Override
            protected void done() {
                stopProgressBar();
                pushToGraphView(tmp);
            }
        };
        addDataLoadingTask(backgroundTask);
        backgroundTask.execute();
        removeDataLoadingTask(backgroundTask);
    }

    public void pushNewCTRPerDay(String id) {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "CTR [Per Day]", "Day", "CTR", dataExchange.getCTRPerDay(), GraphSpecs.Type.DAY_SPAN);
                return null;
            }

            @Override
            protected void done() {
                stopProgressBar();
                pushToGraphView(tmp);
            }
        };
        addDataLoadingTask(backgroundTask);
        backgroundTask.execute();
        removeDataLoadingTask(backgroundTask);
    }

    public void pushNewCTRPerHour(String id) {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "CTR [Per Hour]", "Hour", "CTR", dataExchange.getCTRPerHour(), GraphSpecs.Type.HOUR_SPAN);
                return null;
            }

            @Override
            protected void done() {
                stopProgressBar();
                pushToGraphView(tmp);
            }
        };
        addDataLoadingTask(backgroundTask);
        backgroundTask.execute();
        removeDataLoadingTask(backgroundTask);
    }

    //CPA
    public void pushNewCPAPerWeek(String id) {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "CPA [Per Week]", "Week", "CPA", dataExchange.getCPAPerWeek(), GraphSpecs.Type.WEEK_SPAN);
                return null;
            }

            @Override
            protected void done() {
                stopProgressBar();
                pushToGraphView(tmp);
            }
        };
        addDataLoadingTask(backgroundTask);
        backgroundTask.execute();
        removeDataLoadingTask(backgroundTask);
    }

    public void pushNewCPAPerDay(String id) {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "CPA [Per Day]", "Day", "CPA", dataExchange.getCPAPerDay(), GraphSpecs.Type.DAY_SPAN);
                return null;
            }

            @Override
            protected void done() {
                stopProgressBar();
                pushToGraphView(tmp);
            }
        };
        addDataLoadingTask(backgroundTask);
        backgroundTask.execute();
        removeDataLoadingTask(backgroundTask);
    }

    public void pushNewCPAPerHour(String id) {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "CPA [Per Hour]", "Hour", "CPA", dataExchange.getCPAPerHour(), GraphSpecs.Type.HOUR_SPAN);
                return null;
            }

            @Override
            protected void done() {
                stopProgressBar();
                pushToGraphView(tmp);
            }
        };
        addDataLoadingTask(backgroundTask);
        backgroundTask.execute();
        removeDataLoadingTask(backgroundTask);
    }

    //CPC
    public void pushNewCPCPerWeek(String id) {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "CPC [Per Week]", "Week", "CPC", dataExchange.getCPCPerWeek(), GraphSpecs.Type.WEEK_SPAN);
                return null;
            }

            @Override
            protected void done() {
                stopProgressBar();
                pushToGraphView(tmp);
            }
        };
        addDataLoadingTask(backgroundTask);
        backgroundTask.execute();
        removeDataLoadingTask(backgroundTask);
    }

    public void pushNewCPCPerDay(String id) {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "CPC [Per Day]", "Day", "CPC", dataExchange.getCPCPerDay(), GraphSpecs.Type.DAY_SPAN);
                return null;
            }

            @Override
            protected void done() {
                stopProgressBar();
                pushToGraphView(tmp);
            }
        };
        addDataLoadingTask(backgroundTask);
        backgroundTask.execute();
        removeDataLoadingTask(backgroundTask);
    }

    public void pushNewCPCPerHour(String id) {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "CPC [Per Hour]", "Hour", "CPC", dataExchange.getCPCPerHour(), GraphSpecs.Type.HOUR_SPAN);
                return null;
            }

            @Override
            protected void done() {
                stopProgressBar();
                pushToGraphView(tmp);
            }
        };
        addDataLoadingTask(backgroundTask);
        backgroundTask.execute();
        removeDataLoadingTask(backgroundTask);
    }

    //CPM
    public void pushNewCPMPerWeek(String id) {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "CPM [Per Week]", "Week", "CPM", dataExchange.getCPMPerWeek(), GraphSpecs.Type.WEEK_SPAN);
                return null;
            }

            @Override
            protected void done() {
                stopProgressBar();
                pushToGraphView(tmp);
            }
        };
        addDataLoadingTask(backgroundTask);
        backgroundTask.execute();
        removeDataLoadingTask(backgroundTask);
    }

    public void pushNewCPMPerDay(String id) {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "CPM [Per Day]", "Day", "CPM", dataExchange.getCPMPerDay(), GraphSpecs.Type.DAY_SPAN);
                return null;
            }

            @Override
            protected void done() {
                stopProgressBar();
                pushToGraphView(tmp);
            }
        };
        addDataLoadingTask(backgroundTask);
        backgroundTask.execute();
        removeDataLoadingTask(backgroundTask);
    }

    public void pushNewCPMPerHour(String id) {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "CPM [Per Hour]", "Hour", "CPM", dataExchange.getCPMPerHour(), GraphSpecs.Type.HOUR_SPAN);
                return null;
            }

            @Override
            protected void done() {
                stopProgressBar();
                pushToGraphView(tmp);
            }
        };
        addDataLoadingTask(backgroundTask);
        backgroundTask.execute();
        removeDataLoadingTask(backgroundTask);
    }

    //BounceRate
    public void pushNewBounceRatePerWeek(String id) {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "Bounce Rate [Per Week]", "Week", "Bounce Rate", dataExchange.getBounceRatePerWeek(), GraphSpecs.Type.WEEK_SPAN);
                return null;
            }

            @Override
            protected void done() {
                stopProgressBar();
                pushToGraphView(tmp);
            }
        };
        addDataLoadingTask(backgroundTask);
        backgroundTask.execute();
        removeDataLoadingTask(backgroundTask);
    }

    public void pushNewBounceRatePerDay(String id) {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "Bounce Rate [Per Day]", "Day", "Bounce Rate", dataExchange.getBounceRatePerDay(), GraphSpecs.Type.DAY_SPAN);
                return null;
            }

            @Override
            protected void done() {
                stopProgressBar();
                pushToGraphView(tmp);
            }
        };
        addDataLoadingTask(backgroundTask);
        backgroundTask.execute();
        removeDataLoadingTask(backgroundTask);
    }

    public void pushNewBounceRatePerHour(String id) {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "Bounce Rate [Per Hour]", "Hour", "Bounce Rate", dataExchange.getBounceRatePerHour(), GraphSpecs.Type.HOUR_SPAN);
                return null;
            }

            @Override
            protected void done() {
                stopProgressBar();
                pushToGraphView(tmp);
            }
        };
        addDataLoadingTask(backgroundTask);
        backgroundTask.execute();
        removeDataLoadingTask(backgroundTask);
    }
}
