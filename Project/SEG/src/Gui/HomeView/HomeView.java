package Gui.HomeView;

import Commons.Tuple;
import Gui.GuiComponents.ListView;
import Gui.GuiComponents.TitleLabel;
import Gui.MainController;

import Gui.GuiComponents.RPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.LinkedList;
import java.util.List;

public class HomeView extends RPanel {

    private static int DEFAULT_BAR_WIDTH = 150;

    private final MainController mainController;

    private final List<BarChart> graphs;

    private int nCampaigns;

    public HomeView(MainController mainController) {
        super(mainController.getGuiColors().getGuiTextColor(), new BorderLayout());

        this.mainController = mainController;
        this.graphs = new LinkedList<BarChart>();

        this.nCampaigns = 0;

        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                format();
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });

        refresh();
    }

    private int getColumns() {
        return getWidth() / Math.max(nCampaigns * DEFAULT_BAR_WIDTH, 2 * DEFAULT_BAR_WIDTH);
    }

    private int getMaxHeightPerRow() {
        int min = 100;
        int nFields = 12;

        int columns = getColumns();
        int rows = nFields / columns;
        int height = getHeight() - 60; //60 is the title banner
        height -= (120 * rows) + 60;//the panning spaces

        int maxHeight = height / rows;

        return Math.max(min, maxHeight);
    }

    private void format() {
        if (this.graphs == null) {
            ;
        }
        else if (this.graphs.size() == 0) {
            refresh();
        } else {
            setBorder(BorderFactory.createMatteBorder(0, 4, 4, 4, mainController.getGuiColors().getGuiBackgroundColor()));

            JPanel topPanel = new JPanel(new BorderLayout());
            topPanel.setBackground(mainController.getGuiColors().getGuiPrimeColor().darker());
            topPanel.setPreferredSize(new Dimension(100, 50));

            TitleLabel titleLabel = new TitleLabel("Home", TitleLabel.CENTER, 16, mainController.getGuiColors());
            titleLabel.setForeground(mainController.getGuiColors().getGuiTextColor());
            topPanel.add(titleLabel, BorderLayout.CENTER);

            List<Component> cells = new LinkedList<>();

            int nPerRow = getColumns();
            if (nPerRow < 2) nPerRow = 1;

            int maxHeightPerRow = getMaxHeightPerRow();

            for (int i = 0; i < graphs.size(); i += nPerRow) {

                JPanel tmpWrapper = new JPanel(new GridLayout(1, nPerRow, 12, 0));
                tmpWrapper.setBackground(mainController.getGuiColors().getGuiTextColor());
                tmpWrapper.setBorder(BorderFactory.createMatteBorder(16, 16, 0, 16, mainController.getGuiColors().getGuiTextColor()));

                for (int j = i; (j < i+nPerRow) && (j < graphs.size()); ++j) {
                    graphs.get(j).updateRatioAndRepaint(DEFAULT_BAR_WIDTH, maxHeightPerRow);
                    tmpWrapper.add(graphs.get(j));
                }
                cells.add(tmpWrapper);
            }

            removeAll();
            add(topPanel, BorderLayout.NORTH);
            add(new ListView(mainController.getGuiColors(), cells, false).getWrappedInScroll(true), BorderLayout.CENTER);
        }

        repaint();
        revalidate();
    }

    @Override
    public void refresh() {
        removeAll();

        SwingWorker<Void, Void> backTask = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                graphs.clear();

                List<Tuple<Integer, String>> allCampaigns = mainController.getDataExchange().selectAllCampaigns();
                nCampaigns = allCampaigns.size();

                String[] headings = {
                    "Number of Impressions",
                    "Number of Clicks",
                    "Number of Uniques",
                    "Number of Bounces",
                    "Number of Conversions",
                    "Total Cost",
                    "CTR",
                    "CPA",
                    "CPC",
                    "CPM",
                    "Bounce Rate / Pages",
                    "Bounce Rate / Time"
                };
                Boolean[] representPricing = {
                        false,
                        false,
                        false,
                        false,
                        false,
                        true,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false
                };
                Boolean[] isFloat = {
                        false,
                        false,
                        false,
                        false,
                        false,
                        true,
                        true,
                        true,
                        true,
                        true,
                        true,
                        true
                };

                String[] popupsDescriptions = {
                        "<html>An impression occurs whenever an ad is shown to a user, regardless of whether they click on it.</html>",
                        "<html>A click occurs when a user clicks on an ad that is shown to them.</html>",
                        "<html>The number of unique users that click on an ad during the course of a campaign.</html>",
                        "<html>A user clicks on an ad, but then fails to interact with the website (typically detected when a <br>user navigates away from the website after a short time, <br>or when only a single page has been viewed).</html>",
                        "<html>A conversion, or acquisition, occurs when a user clicks and then acts on an ad. The specific <br>definition of an action depends on the campaign <br>(e.g., buying a product, registering as a new <br>customer or joining a mailing list).</html>",
                        "<html>The total of the click cost and impression cost.</html>",
                        "<html>The average number of clicks per impression.</html>",
                        "<html>The average amount of money spent on an advertising <br>campaign for each acquisition (i.e., conversion).</html>",
                        "<html>The average amount of money spent on an advertising <br>campaign for each click.</html>",
                        "<html>The average amount of money spent on an advertising <br>campaign for every one thousand impressions.</html>",
                        "<html>The average number of bounces per number of pages.</html>",
                        "<html>The average number of bounces per time spent on a page.</html>"};

                List<String> campaignNames = new LinkedList<>();
                List<Number[]> cachedValues = new LinkedList<>();

                for (Tuple<Integer, String> campaign : allCampaigns) {
                    campaignNames.add(campaign.getY());
                    cachedValues.add(mainController.getDataExchange().selectByIdFrom_HOMEVIEW_CACHE(campaign.getX()));
                }

                for (int i = 0; i < headings.length; ++i) {
                    List<Tuple<String, Number>> barChartData = new LinkedList<>();

                    for (int j = 0; j < cachedValues.size(); ++j) {
                        barChartData.add(new Tuple<>(campaignNames.get(j), cachedValues.get(j)[i]));
                    }

                    BarChart tmp = getGraph(headings[i], barChartData, representPricing[i], isFloat[i]);
                    tmp.setToolTipText(popupsDescriptions[i]);
                    graphs.add(tmp);
                }

                return null;
            }

            @Override
            protected void done() {
                format();

                mainController.removeDataLoadingTask(this);
                mainController.stopProgressBar();
            }
        };

        mainController.startProgressBar();
        backTask.execute();
        mainController.addDataLoadingTask(backTask);
    }

    private BarChart getGraph(String title,  List<Tuple<String, Number>> data, boolean representsPricing, boolean isFloat) {

        return new BarChart(
                title,
                mainController.getGuiColors(),
                DEFAULT_BAR_WIDTH,
                getMaxHeightPerRow(),
                data,
                representsPricing,
                isFloat
        );
    }
}
