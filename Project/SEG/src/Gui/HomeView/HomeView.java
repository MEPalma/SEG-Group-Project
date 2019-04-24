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
        super(mainController.getGuiColors().getGuiBackgroundColor(), new BorderLayout());

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
                    graphs.add(getGraph(headings[i], barChartData, representPricing[i]));
                }

                return null;
            }

            @Override
            protected void done() {
                List<Component> cells = new LinkedList<>();

                for (int i = 0; i < graphs.size() - 1; i += 2)
                    cells.add(getSplitView(graphs.get(i), graphs.get(i + 1)));

                add(new ListView(mainController.getGuiColors(), cells).getWrappedInScroll(true), BorderLayout.CENTER);

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
        wrapper.setBackground(mainController.getGuiColors().getGuiPrimeColor());
        wrapper.setBorder(BorderFactory.createEmptyBorder());

        TitleLabel titleLabel = new TitleLabel(title, TitleLabel.CENTER, 18, mainController.getGuiColors());
        titleLabel.setForeground(mainController.getGuiColors().getGuiTextColor());
        wrapper.add(titleLabel, BorderLayout.NORTH);
        wrapper.add(barChart, BorderLayout.CENTER);

        return wrapper;
    }

    private JPanel getSplitView(JPanel left, JPanel right) {
        JPanel wrapper = new JPanel(new GridLayout(1, 2, 8, 4));
        wrapper.setBackground(mainController.getGuiColors().getGuiBackgroundColor());
        wrapper.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, mainController.getGuiColors().getGuiBackgroundColor()));

        wrapper.add(left);
        wrapper.add(right);

        return wrapper;
    }

    private JPanel getGraph(String title,  List<Tuple<String, Number>> data, boolean representsPricing) {
        return wrapInCell(
                title,
                new BarChart(
                        mainController.getGuiColors(),
                        data,
                        representsPricing
                )
        );
    }
}
