package Gui;

import Commons.Tuple;
import DatabaseManager.DataExchange;
import DatabaseManager.DatabaseManager;
import Gui.BreadCrumbs.BreadCrumbs;
import Gui.BreadCrumbs.BreadCrumbsHoster;
import Gui.GuiComponents.RPanel;

import java.util.LinkedList;
import java.util.List;
import javax.swing.*;
import java.util.Collection;

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
        synchronized (this.dataLoadingTasks)
        {
            for (SwingWorker task : this.dataLoadingTasks)
                task.cancel(true);

            this.dataLoadingTasks.clear();
        }
    }

    private void removeDataLoadingTask(SwingWorker task)
    {
        synchronized (this.dataLoadingTasks)
        {
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

    public BreadCrumbsHoster getBreadCrumbsHoster() {
        return breadCrumbsHoster;
    }

    public DataExchange getDataExchange() {
        return this.dataExchange;
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

    public void pushToGraphView(String id, String title, String xAxisName, String yAxisName, Collection<Tuple<String, Number>> chartData) {
        this.pushToGraphView(new GraphSpecs(id, title, xAxisName, yAxisName, chartData));
    }

    public void popFromGraphView(String graphId) {
        this.graphView.popGraphSpecs(graphId);
    }


    /*
        Graphs
     */

    // Impressions
    public void pushNewNumberOfImpressionsPerWeek(String id)
    {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "Number of Impressions [Per Week]", "Week", "N. Impressions", dataExchange.getNumberOfImpressionsPerWeek());
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

    public void pushNewNumberOfImpressionsPerDay(String id)
    {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "Number of Impressions [Per Day]", "Day", "N. Impressions", dataExchange.getNumberOfImpressionsPerDay());
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

    public void pushNewNumberOfImpressionsPerHour(String id)
    {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "Number of Impressions [Per Hour]", "Hour", "N. Impressions", dataExchange.getNumberOfImpressionsPerHour());
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
    public void pushNewNumberOfClicksPerWeek(String id)
    {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "Number of Impressions [Per Week]", "Week", "N. Clicks", dataExchange.getNumberOfClicksPerWeek());
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

    public void pushNewNumberOfClicksPerDay(String id)
    {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "Number of Click [Per Day]", "Day", "N. Clicks", dataExchange.getNumberOfClicksPerDay());
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

    public void pushNewNumberOfClicksPerHour(String id)
    {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "Number of Clicks [Per Hour]", "Hour", "N. Clicks", dataExchange.getNumberOfClicksPerHour());
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
    public void pushNewNumberOfUniquesPerWeek(String id)
    {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "Number of Uniques [Per Week]", "Week", "N. Uniques", dataExchange.getNumberOfUniquesPerWeek());
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

    public void pushNewNumberOfUniquesPerDay(String id)
    {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "Number of Uniques [Per Day]", "Day", "N. Uniques", dataExchange.getNumberOfUniquesPerDay());
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

    public void pushNewNumberOfUniquesPerHour(String id)
    {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "Number of Uniques [Per Hour]", "Hour", "N. Uniques", dataExchange.getNumberOfUniquesPerHour());
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
    public void pushNewNumberOfBouncesPerWeek(String id)
    {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "Number of Bounces [Per Week]", "Week", "N. Bounces", dataExchange.getNumberOfBouncesPerWeek());
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

    public void pushNewNumberOfBouncesPerDay(String id)
    {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "Number of Bounces [Per Day]", "Day", "N. Bounces", dataExchange.getNumberOfBouncesPerDay());
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

    public void pushNewNumberOfBouncesPerHour(String id)
    {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "Number of Bounces [Per Hour]", "Hour", "N. Bounces", dataExchange.getNumberOfBouncesPerHour());
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
    public void pushNewNumberOfConversionsPerWeek(String id)
    {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "Number of Conversions [Per Week]", "Week", "N. Conversions", dataExchange.getNumberOfConversionsPerWeek());
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

    public void pushNewNumberOfConversionsPerDay(String id)
    {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "Number of Conversions [Per Day]", "Day", "N. Conversions", dataExchange.getNumberOfConversionsPerDay());
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

    public void pushNewNumberOfConversionsPerHour(String id)
    {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            GraphSpecs tmp;

            @Override
            protected Void doInBackground() {
                startProgressBar();
                tmp = new GraphSpecs(id, "Number of Conversions [Per Hour]", "Hour", "N. Conversions", dataExchange.getNumberOfConversionsPerHour());
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
