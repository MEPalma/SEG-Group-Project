package Gui;

import Commons.FilterSpecs;
import Commons.GraphSpecs;
import Commons.Tuple;
import DatabaseManager.DataExchange;
import DatabaseManager.DatabaseManager;
import Gui.BreadCrumbs.BreadCrumbs;
import Gui.BreadCrumbs.BreadCrumbsHoster;
import Gui.GraphManager.GraphManager;
import Gui.GuiComponents.MenuLabel;
import Gui.GuiComponents.RPanel;
import DatabaseManager.Stringifiable;
import Gui.GuiComponents.TitleLabel;
import Gui.TabbedView.TabbedView;
import com.sun.corba.se.impl.orbutil.graph.Graph;
import sun.awt.image.ImageWatched;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

    public void pushToGraphView(GraphSpecs newGraphSpecs) {

        //TODO do me in backgorund!!!!!!!!!!!
        newGraphSpecs.setData(getGraphSpecData(newGraphSpecs));
        String[] titles = getGraphDescription(newGraphSpecs);
        newGraphSpecs.setTitle(titles[0]);
        newGraphSpecs.setxAxisName(titles[1]);
        newGraphSpecs.setyAxisName(titles[2]);



        this.tabbedView.push(titles[0], newGraphSpecs.getTypeColor(), getGraphCard(newGraphSpecs), newGraphSpecs);
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

    private String[] getGraphDescription(GraphSpecs graphSpecs)
    {
        String yAxis = graphSpecs.getTimespan().toString();
        String xAxis = graphSpecs.getMetric().toString();
        String title = graphSpecs.getMetric().toString() + " [Per " + graphSpecs.getTimespan() + "]";

        if (graphSpecs.getMetric() == GraphSpecs.METRICS.BounceRate)
            title += " based on " + graphSpecs.getBounceDef().toString();


        return new String[] {title, xAxis, yAxis};
    }

    public GraphSpecs proposeNewGraph(GraphSpecs.METRICS metrics, GraphSpecs.TIME_SPAN time_span, GraphSpecs.BOUNCE_DEF bounce_def) {
        GraphSpecs graphSpecs = new GraphSpecs(metrics, time_span, bounce_def, getFilterSpecs());

        if (this.tabbedView.containsComparable(graphSpecs)) return null;
        else return graphSpecs;
    }

    private JPanel getGraphCard(GraphSpecs spec) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(spec.getTypeColor());
        card.setBorder(BorderFactory.createEmptyBorder(10, 10,10, 10));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(spec.getTypeColor());
        topPanel.setPreferredSize(new Dimension(100, 50));

        TitleLabel titleLabel = new TitleLabel(spec.getTitle(), TitleLabel.CENTER, 16);
        titleLabel.setForeground(GuiColors.BASE_WHITE);
        topPanel.add(titleLabel, BorderLayout.CENTER);

        JPanel graph = GraphManager.createBarChar(spec.getData(), spec.getxAxisName(), spec.getyAxisName(), spec.getTypeColor());

        graph.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, GuiColors.BASE_WHITE));
        card.add(topPanel, BorderLayout.NORTH);
        card.add(graph, BorderLayout.CENTER);

        return card;
    }

}
