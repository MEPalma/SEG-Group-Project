package Gui.Menus;

import Commons.CompareGraphSpec;
import Commons.FilterSpecs;
import Commons.GraphSpecs;
import Commons.Tuple;
import Gui.GraphManager.GraphManager;
import Gui.GuiColors;
import Gui.GuiComponents.*;
import Gui.MainController;
import Gui.TakeActionListener;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;
import java.awt.*;

import static Gui.Menus.ChooseNewGraphPanel.BOUNCE_DEF;

public class CompareMenu extends RPanel {
    private final MainController mainController;
    private final List<Tuple<Integer, String>> selections;
    private final List<Tuple<Integer, String>> allCampaigns;

    private final String[] campaignsOptions;

    private final DropDown metricsChooser;
    private final DropDown bounceDefinitionChooser;
    private final DropDown timespanChooser;

    public CompareMenu(MainController mainController) {
        super(mainController.getGuiColors().getGuiTextColor(), new BorderLayout());

        this.mainController = mainController;
        this.selections = new LinkedList<>();

        TakeActionListener takeActionListener = new TakeActionListener() {
            @Override
            public void takeAction() {
                refresh();
            }
        };

        this.metricsChooser = new DropDown(ChooseNewGraphPanel.METRICS, ChooseNewGraphPanel.METRICS_DESCRIPTIONS, 0, mainController.getGuiColors());
        this.metricsChooser.addTakeActionListener(takeActionListener);

        this.bounceDefinitionChooser = new DropDown(BOUNCE_DEF, null, 0, mainController.getGuiColors());
        this.bounceDefinitionChooser.addTakeActionListener(takeActionListener);

        this.timespanChooser = new DropDown(ChooseNewGraphPanel.TIME_SPANS, null, 0, mainController.getGuiColors());
        this.timespanChooser.addTakeActionListener(takeActionListener);

        this.allCampaigns = mainController.getDataExchange().selectAllCampaigns();

        this.campaignsOptions = new String[allCampaigns.size()];

        for (int i = 0; i < allCampaigns.size(); ++i)
            campaignsOptions[i] = allCampaigns.get(i).getY();

        refresh();
    }

    @Override
    public void refresh() {
        removeAll();

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        wrapper.setBackground(getBackground());

        wrapper.add(new TitleLabel("Compare", TitleLabel.LEFT, 20, mainController.getGuiColors()), BorderLayout.NORTH);

        List<Component> cells = new LinkedList<>();

        cells.add(getMetricsChooserCell());

        if (this.metricsChooser.getSelectedIndex() == 3)//Bounce
            cells.add(getBounceChooserCell());

        cells.add(getTimeSpanChooserCell());
        cells.add(getCampaignsChooserCell());

        wrapper.add(new ListView(mainController.getGuiColors(), cells).getWrappedInScroll(true), BorderLayout.CENTER);

        add(wrapper, BorderLayout.CENTER);
        repaint();
        revalidate();
    }

    private JPanel getDropDownSelection(int indexInPool, int selectedIndex, String[] options) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(getBackground());
        wrapper.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

        DropDown dropDown = new DropDown(options, null, selectedIndex, mainController.getGuiColors());
        dropDown.addTakeActionListener(new TakeActionListener() {
            @Override
            public void takeAction() {
                selections.set(indexInPool, new Tuple(allCampaigns.get(dropDown.getSelectedIndex()).getX(), allCampaigns.get(dropDown.getSelectedIndex()).getY()));
            }
        });
        wrapper.add(dropDown, BorderLayout.CENTER);

        MenuLabel removeMenuLabel = new MenuLabel("x", MenuLabel.RIGHT, 20, mainController.getGuiColors());
        removeMenuLabel.dropAllListeners();
        removeMenuLabel.setForeground(GuiColors.RED_ERROR);
        removeMenuLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                selections.remove(indexInPool);
                refresh();
            }
        });
        wrapper.add(removeMenuLabel, BorderLayout.EAST);

        return wrapper;
    }

    private JPanel getAddCampaignCell() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(getBackground());
        wrapper.setBorder(BorderFactory.createEmptyBorder());

        MenuLabel addMenuLabel = new MenuLabel("+", MenuLabel.CENTER, 20, mainController.getGuiColors());
        addMenuLabel.setToolTipText("Compare with another campaign");
        addMenuLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                selections.add(new Tuple<>(allCampaigns.get(0).getX(), allCampaigns.get(0).getY()));
                refresh();
            }
        });
        wrapper.add(addMenuLabel, BorderLayout.CENTER);

        return wrapper;
    }

    private JPanel getCampaignsChooserCell() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(mainController.getGuiColors().getGuiTextColor());
        wrapper.setBorder(BorderFactory.createMatteBorder(12, 8, 8, 8, mainController.getGuiColors().getGuiTextColor()));

        wrapper.add(new TitleLabel("Campaigns", TitleLabel.LEFT, 18, mainController.getGuiColors()), BorderLayout.NORTH);

        List<Component> cells = new LinkedList<>();
        for (int i = 0; i < this.selections.size(); ++i)
            cells.add(getDropDownSelection(i, getOptionPositionIndex(this.selections.get(i)), this.campaignsOptions));

        cells.add(getAddCampaignCell());
        cells.add(getAddButton());

        wrapper.add(new ListView(mainController.getGuiColors(), cells), BorderLayout.CENTER);

        return wrapper;
    }

    private int getOptionPositionIndex(Tuple<Integer, String> camp) {
        int i = 0;
        for (Tuple<Integer, String> t : allCampaigns){
            if (t.getX().equals(camp.getX()) && t.getY().equals(camp.getY())) return i;
            ++i;
        }
        return i;
    }

    private JPanel getMetricsChooserCell() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(mainController.getGuiColors().getGuiTextColor());
        wrapper.setBorder(BorderFactory.createMatteBorder(12, 8, 8, 8, mainController.getGuiColors().getGuiTextColor()));

        wrapper.add(new TitleLabel("Metric", TitleLabel.LEFT, 18, mainController.getGuiColors()), BorderLayout.NORTH);
        wrapper.add(this.metricsChooser, BorderLayout.CENTER);

        return wrapper;
    }

    private JPanel getBounceChooserCell() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(mainController.getGuiColors().getGuiTextColor());
        wrapper.setBorder(BorderFactory.createMatteBorder(12, 8, 8, 8, mainController.getGuiColors().getGuiTextColor()));

        wrapper.add(new TitleLabel("Bounce Definition", TitleLabel.LEFT, 18, mainController.getGuiColors()), BorderLayout.NORTH);
        wrapper.add(this.bounceDefinitionChooser, BorderLayout.CENTER);
        return wrapper;
    }

    private JPanel getTimeSpanChooserCell() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(mainController.getGuiColors().getGuiTextColor());
        wrapper.setBorder(BorderFactory.createMatteBorder(12, 8, 8, 8, mainController.getGuiColors().getGuiTextColor()));

        wrapper.add(new TitleLabel("Time Grouping", TitleLabel.LEFT, 18, mainController.getGuiColors()), BorderLayout.NORTH);
        wrapper.add(this.timespanChooser, BorderLayout.CENTER);

        return wrapper;
    }

    private JPanel getAddButton() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(getBackground());
        wrapper.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

        MenuLabel addMenuLabel = new MenuLabel("Compare", MenuLabel.CENTER, 16, mainController.getGuiColors());
        addMenuLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                List<GraphSpecs> graphSpecs = new LinkedList<>();

                FilterSpecs commonFilters = mainController.getInitFilters();

                for (Tuple<Integer, String> s : selections) {
                    GraphSpecs tmp = new GraphSpecs(
                            s.getX(),
                            s.getY(),
                            getChosenMetric(),
                            getChosenTimeSpan(),
                            getChosenBounceDef(),
                            commonFilters
                    );
                    graphSpecs.add(tmp);
                }

                String cardTitle = "Compare " + GraphManager.getGraphShortTitle(getChosenMetric());
                String graphTitle = "Compare " + GraphManager.getGraphTitle(getChosenMetric(), getChosenTimeSpan(), getChosenBounceDef());

                mainController.pushToGraphView(new CompareGraphSpec(commonFilters, graphSpecs, cardTitle, graphTitle, GraphManager.getGraphShortTitle(getChosenMetric()), GraphManager.getFormattedTimeSpan(getChosenTimeSpan())));

            }
        });
        wrapper.add(addMenuLabel, BorderLayout.CENTER);

        return wrapper;
    }

    private GraphSpecs.METRICS getChosenMetric() {
        return GraphSpecs.METRICS.values()[this.metricsChooser.getSelectedIndex()];
    }

    private GraphSpecs.TIME_SPAN getChosenTimeSpan() {
        return GraphSpecs.TIME_SPAN.values()[this.timespanChooser.getSelectedIndex()];
    }

    private GraphSpecs.BOUNCE_DEF getChosenBounceDef() {
        return GraphSpecs.BOUNCE_DEF.values()[this.bounceDefinitionChooser.getSelectedIndex()];
    }
}
