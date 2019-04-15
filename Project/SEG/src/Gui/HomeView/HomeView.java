package Gui.HomeView;

import Commons.GraphSpecs;
import Commons.Tuple;
import Gui.GuiComponents.ListView;
import Gui.GuiComponents.TitleLabel;
import Gui.MainController;
import Gui.GuiColors;

import Gui.GuiComponents.RPanel;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class HomeView extends RPanel {

    private final MainController mainController;

    public HomeView(MainController mainController) {
        super(GuiColors.BASE_SMOKE, new BorderLayout());

        this.mainController = mainController;

        refresh();
    }

    @Override
    public void refresh() {
        removeAll();

        List<Tuple<Integer, String>> allCampaigns = mainController.getDataExchange().selectAllCampaigns();

        List<Component> cells = new LinkedList<>();

        // Impressions Row
        cells.add(getSplitView(getImpressions(allCampaigns), getImpressions(allCampaigns)));


        add(new ListView(GuiColors.BASE_SMOKE, cells).getWrappedInScroll(true), BorderLayout.CENTER);

        repaint();
        revalidate();
    }

    private JPanel wrapInCell(String title, BarChart barChart) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(GuiColors.BASE_PRIME);
        wrapper.setBorder(BorderFactory.createEmptyBorder());

        TitleLabel titleLabel = new TitleLabel(title, TitleLabel.CENTER, 18);
        titleLabel.setForeground(GuiColors.BASE_WHITE);
        wrapper.add(titleLabel, BorderLayout.NORTH);
        wrapper.add(barChart, BorderLayout.CENTER);

        return wrapper;
    }

    private JPanel getSplitView(JPanel left, JPanel right) {
        JPanel wrapper = new JPanel(new GridLayout(1, 2, 4, 4));
        wrapper.setBackground(GuiColors.BASE_SMOKE);
        wrapper.setBorder(BorderFactory.createEmptyBorder());

        wrapper.add(left);
        wrapper.add(right);

        return wrapper;
    }

    private JPanel getImpressions(List<Tuple<Integer, String>> allCampaigns) {

        List<Tuple<String, Number>> data = new LinkedList<>();

        for (Tuple<Integer, String> c : allCampaigns) {

            //TODO change to add with new queries!
            data.addAll(mainController.getGraphSpecData(
                                            new GraphSpecs(
                                                    c.getX(),
                                                    c.getY(),
                                                    GraphSpecs.METRICS.NumberImpressions,
                                                    GraphSpecs.TIME_SPAN.MONTH_SPAN, //TODO change to null
                                                    GraphSpecs.BOUNCE_DEF.NPAGES,
                                                    mainController.getInitFilters()//TODO change ot null
                                            )
                                        )
                        );
        }

        return wrapInCell(
                  "Number of Impressions",
                        new BarChart(
                                GuiColors.BASE_WHITE,
                                GuiColors.RED_ERROR,
                                data,
                                false
                        )
                );
    }
}
