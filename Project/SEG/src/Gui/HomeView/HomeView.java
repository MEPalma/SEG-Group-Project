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

        List<Tuple<Integer, String>> allCampaigns = mainController.getDataExchange().selectAllCampaigns();

        List<Component> cells = new LinkedList<>();

        cells.add(getSplitView(
                getGraph(allCampaigns, "Number of impressions", GraphSpecs.METRICS.NumberImpressions, GraphSpecs.BOUNCE_DEF.NPAGES, false),
                getGraph(allCampaigns, "Number of clicks", GraphSpecs.METRICS.NumberClicks, GraphSpecs.BOUNCE_DEF.NPAGES, false)
        ));

        cells.add(getSplitView(
                getGraph(allCampaigns, "Number of Uniques", GraphSpecs.METRICS.NumberUniques, GraphSpecs.BOUNCE_DEF.NPAGES, false),
                getGraph(allCampaigns, "Number of Bounces", GraphSpecs.METRICS.NumberBounces, GraphSpecs.BOUNCE_DEF.NPAGES, false)
        ));

        cells.add(getSplitView(
                getGraph(allCampaigns, "Number of Conversion", GraphSpecs.METRICS.NumberConversions, GraphSpecs.BOUNCE_DEF.NPAGES, false),
                getGraph(allCampaigns, "Total Cost", GraphSpecs.METRICS.TotalCost, GraphSpecs.BOUNCE_DEF.NPAGES, true)
        ));

        cells.add(getSplitView(
                getGraph(allCampaigns, "CTR", GraphSpecs.METRICS.CTR, GraphSpecs.BOUNCE_DEF.NPAGES, false),
                getGraph(allCampaigns, "CPA", GraphSpecs.METRICS.CPA, GraphSpecs.BOUNCE_DEF.NPAGES, false)
        ));

        cells.add(getSplitView(
                getGraph(allCampaigns, "CPC", GraphSpecs.METRICS.CPC, GraphSpecs.BOUNCE_DEF.NPAGES, false),
                getGraph(allCampaigns, "CPM", GraphSpecs.METRICS.CPM, GraphSpecs.BOUNCE_DEF.NPAGES, false)
        ));

        cells.add(getSplitView(
                getGraph(allCampaigns, "Bounce rate / pages", GraphSpecs.METRICS.BounceRate, GraphSpecs.BOUNCE_DEF.NPAGES, false),
                getGraph(allCampaigns, "Bounce rate / time", GraphSpecs.METRICS.BounceRate, GraphSpecs.BOUNCE_DEF.TIME, false)
        ));

        add(new ListView(mainController.getGuiColors(), cells).getWrappedInScroll(true), BorderLayout.CENTER);

        repaint();
        revalidate();
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
        JPanel wrapper = new JPanel(new GridLayout(1, 2, 4, 4));
        wrapper.setBackground(mainController.getGuiColors().getGuiBackgroundColor());
        wrapper.setBorder(BorderFactory.createEmptyBorder());

        wrapper.add(left);
        wrapper.add(right);

        return wrapper;
    }

    private JPanel getGraph(List<Tuple<Integer, String>> allCampaigns, String title, GraphSpecs.METRICS metric, GraphSpecs.BOUNCE_DEF bounceDef, boolean representsPricing) {

        List<Tuple<String, Number>> data = new LinkedList<>();

        for (Tuple<Integer, String> c : allCampaigns) {

            data.addAll(mainController.getGraphSpecData(
                    new GraphSpecs(
                            c.getX(),
                            c.getY(),
                            metric,
                            null,
                            bounceDef,
                            null
                    )
                    )
            );
        }

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
