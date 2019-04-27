package Gui.HomeView;

import Commons.GraphSpecs;
import Commons.Tuple;
import Gui.GuiComponents.ListView;
import Gui.GuiComponents.TitleLabel;
import Gui.MainController;

import Gui.GuiComponents.RPanel;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class HomeView extends RPanel {

    private final MainController mainController;

    public HomeView(MainController mainController) {
        super(mainController.getGuiColors().getGuiTextColor(), new BorderLayout());

        this.mainController = mainController;

        refresh();
    }

    @Override
    public void refresh() {
        removeAll();

        SwingWorker<Void, Void> backTask = new SwingWorker<Void, Void>() {
            List<JPanel> graphs = new LinkedList<>();

            @Override
            protected Void doInBackground() throws Exception {
                List<Tuple<Integer, String>> allCampaigns = mainController.getDataExchange().selectAllCampaigns();

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

                    JPanel tmp = getGraph(headings[i], barChartData, representPricing[i], isFloat[i]);
                    tmp.setToolTipText(popupsDescriptions[i]);
                    graphs.add(tmp);
                }

                return null;
            }

            @Override
            protected void done() {
                setBorder(BorderFactory.createMatteBorder(0, 4, 4, 4, mainController.getGuiColors().getGuiBackgroundColor()));

                JPanel topPanel = new JPanel(new BorderLayout());
                topPanel.setBackground(mainController.getGuiColors().getGuiPrimeColor().darker());
                topPanel.setPreferredSize(new Dimension(100, 50));

                TitleLabel titleLabel = new TitleLabel("Home", TitleLabel.CENTER, 16, mainController.getGuiColors());
                titleLabel.setForeground(mainController.getGuiColors().getGuiTextColor());
                topPanel.add(titleLabel, BorderLayout.CENTER);
                add(topPanel, BorderLayout.NORTH);

                List<Component> cells = new LinkedList<>();

                for (int i = 0; i < graphs.size() - 1; i += 2)
                    cells.add(getSplitView(graphs.get(i), graphs.get(i + 1)));

                add(new ListView(mainController.getGuiColors(), cells, false).getWrappedInScroll(true), BorderLayout.CENTER);

                repaint();
                revalidate();

                mainController.removeDataLoadingTask(this);
                mainController.stopProgressBar();
            }
        };

        mainController.startProgressBar();
        backTask.execute();
        mainController.addDataLoadingTask(backTask);
    }

    private JPanel wrapInCell(String title, BarChart barChart) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(mainController.getGuiColors().getGuiTextColor());
        wrapper.setBorder(BorderFactory.createEmptyBorder());

        TitleLabel titleLabel = new TitleLabel(title, TitleLabel.CENTER, 18, mainController.getGuiColors());
        titleLabel.setForeground(mainController.getGuiColors().getGuiPrimeColor().darker());
        wrapper.add(titleLabel, BorderLayout.NORTH);
        wrapper.add(barChart, BorderLayout.CENTER);

        return wrapper;
    }

    private JPanel getSplitView(JPanel left, JPanel right) {
        JPanel wrapper = new JPanel(new GridLayout(1, 2, 16, 0));
        wrapper.setBackground(mainController.getGuiColors().getGuiTextColor());
        wrapper.setBorder(BorderFactory.createMatteBorder(16, 16, 0, 16, mainController.getGuiColors().getGuiTextColor()));

        left.setBorder(BorderFactory.createMatteBorder(8, 8, 8, 8, mainController.getGuiColors().getGuiBackgroundColor()));
        right.setBorder(BorderFactory.createMatteBorder(8, 8, 8, 8, mainController.getGuiColors().getGuiBackgroundColor()));

        wrapper.add(left);
        wrapper.add(right);

        return wrapper;
    }

    private JPanel getGraph(String title,  List<Tuple<String, Number>> data, boolean representsPricing, boolean isFloat) {
        return wrapInCell(
                title,
                new BarChart(
                        mainController.getGuiColors(),
                        data,
                        representsPricing,
                        isFloat
                )
        );
    }
}
