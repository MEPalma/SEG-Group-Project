package Gui;

import Commons.Tuple;
import DatabaseManager.DataExchange;
import Gui.GraphManager.GraphManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class MetricsMenu extends RPanel {

    private final DataExchange dataExchange;//todo change with controller
    private final BreadCrumbs breadCrumbs;

    public MetricsMenu(DataExchange dataExchange, BreadCrumbs breadCrumbs) {
        super(GuiColors.BASE_LIGHT, new BorderLayout());
        this.dataExchange = dataExchange;
        this.breadCrumbs = breadCrumbs;
        refresh();
    }

    @Override
    public void refresh() {
        removeAll();

        List<Component> menus = new LinkedList<Component>();
//TODO DO IN BACKGROUND!!!!!!!!
        //NUMBER OF IMPRESSIONS
        RadioButton nImpressionsWeek = new RadioButton("Week");
        nImpressionsWeek.setSelected(breadCrumbs.getGraphView().containsGraph("nImpressionsWeek"));
        nImpressionsWeek.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (nImpressionsWeek.isSelected()) {
                    breadCrumbs.getGraphView().popGraphSpecs("nImpressionsWeek");
                } else {
                    Collection<Tuple<Number, Number>> chartData = new LinkedList<>();

                    for (int i = 0; i < 100; i++)
                        chartData.add(new Tuple<>(Math.random(), Math.random()));

                    GraphSpecs tmp = new GraphSpecs("nImpressionsWeek", "Sample Chart", "xAxisName", "yAxiseName", chartData);
                    breadCrumbs.getGraphView().pushGraphSpecs(tmp);
                    //todo
                }
            }
        });

        RadioButton nImpressionsDay = new RadioButton("Day");
        nImpressionsDay.setSelected(breadCrumbs.getGraphView().containsGraph("nImpressionsDay"));
        nImpressionsDay.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (nImpressionsDay.isSelected()) {
                    breadCrumbs.getGraphView().popGraphSpecs("nImpressionsDay");
                } else {
                    Collection<Tuple<Number, Number>> chartData = new LinkedList<>();
                    for (int i = 0; i < 100; i++)
                        chartData.add(new Tuple<>(Math.random(), Math.random()));

                    GraphSpecs tmp = new GraphSpecs("nImpressionsDay", "Sample Chart", "xAxisName", "yAxiseName", chartData);
                    breadCrumbs.getGraphView().pushGraphSpecs(tmp);
                    //todo
                }
            }
        });

        RadioButton nImpressionsHour = new RadioButton("Hour");
        nImpressionsHour.setSelected(breadCrumbs.getGraphView().containsGraph("nImpressionsHour"));
        nImpressionsHour.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (nImpressionsHour.isSelected()) {
                    breadCrumbs.getGraphView().popGraphSpecs("nImpressionsHour");
                } else {
                    Collection<Tuple<Number, Number>> chartData = new LinkedList<>();
                    for (int i = 0; i < 100; i++)
                        chartData.add(new Tuple<>(Math.random(), Math.random()));

                    GraphSpecs tmp = new GraphSpecs("nImpressionsHour", "Sample Chart", "xAxisName", "yAxiseName", chartData);
                    breadCrumbs.getGraphView().pushGraphSpecs(tmp);
                    //todo
                }
            }
        });
        menus.add(getMenuCard(
                "Number of Impressions",
                "An impression occurs whenever an ad is shown to a user, regardless of whether they click on it.",
                new Component[]{nImpressionsWeek, nImpressionsDay, nImpressionsHour}));


        // Number of Clicks
        RadioButton nClicksWeek = new RadioButton("Week");
        nClicksWeek.setSelected(breadCrumbs.getGraphView().containsGraph("nClicksWeek"));
        nClicksWeek.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (nClicksWeek.isSelected()) {
                    //todo remove from pool
                } else {
                    //todo
                }
            }
        });

        RadioButton nClicksDay = new RadioButton("Day");
        nClicksDay.setSelected(breadCrumbs.getGraphView().containsGraph("nClicksDay"));
        nClicksDay.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (nClicksDay.isSelected()) {
                    //todo remove from pool
                } else {
                    //todo
                }
            }
        });

        RadioButton nClicksHour = new RadioButton("Hour");
        nClicksHour.setSelected(breadCrumbs.getGraphView().containsGraph("nClicksHour"));
        nClicksHour.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (nClicksHour.isSelected()) {
                    //todo remove from pool
                } else {
                    //todo
                }
            }
        });
        menus.add(getMenuCard(
                "Number of Clicks",
                "A click occurs when a user clicks on an ad that is shown to them.",
                new Component[]{nClicksWeek, nClicksDay, nClicksHour}));


        //Number of Uniques
        RadioButton nUniquesWeek = new RadioButton("Week");
        nUniquesWeek.setSelected(breadCrumbs.getGraphView().containsGraph("nUniquesWeek"));
        nUniquesWeek.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (nUniquesWeek.isSelected()) {
                    //todo remove from pool
                } else {
                    //todo
                }
            }
        });

        RadioButton nUniquesDay = new RadioButton("Day");
        nUniquesDay.setSelected(breadCrumbs.getGraphView().containsGraph("nUniquesDay"));
        nUniquesDay.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (nUniquesDay.isSelected()) {
                    //todo remove from pool
                } else {
                    //todo
                }
            }
        });

        RadioButton nUniquesHour = new RadioButton("Hour");
        nUniquesHour.setSelected(breadCrumbs.getGraphView().containsGraph("nUniquesHour"));
        nUniquesHour.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (nUniquesHour.isSelected()) {
                    //todo remove from pool
                } else {
                    //todo
                }
            }
        });
        menus.add(getMenuCard(
                "Number of Uniques",
                "The number of unique users that click on an ad during the course of a campaign.",
                new Component[]{nUniquesWeek, nUniquesDay, nUniquesHour}));


        //Number of Bounces
        RadioButton nBouncesWeek = new RadioButton("Week");
        nBouncesWeek.setSelected(breadCrumbs.getGraphView().containsGraph("nBouncesWeek"));
        nBouncesWeek.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (nBouncesWeek.isSelected()) {
                    //todo remove from pool
                } else {
                    //todo
                }
            }
        });

        RadioButton nBouncesDay = new RadioButton("Day");
        nBouncesDay.setSelected(breadCrumbs.getGraphView().containsGraph("nBouncesDay"));
        nBouncesDay.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (nBouncesDay.isSelected()) {
                    //todo remove from pool
                } else {
                    //todo
                }
            }
        });

        RadioButton nBouncesHour = new RadioButton("Hour");
        nBouncesHour.setSelected(breadCrumbs.getGraphView().containsGraph("nBouncesHour"));
        nBouncesHour.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (nBouncesHour.isSelected()) {
                    //todo remove from pool
                } else {
                    //todo
                }
            }
        });
        menus.add(getMenuCard(
                "Number of Bounces",
                "A user clicks on an ad, but then fails to interact with the website (typically detected when a user navigates away from the website after a short time, or when only a single page has been viewed).",
                new Component[]{nBouncesWeek, nBouncesDay, nBouncesHour}));


        //Number of conversions
        RadioButton nConversionsWeek = new RadioButton("Week");
        nConversionsWeek.setSelected(breadCrumbs.getGraphView().containsGraph("nConversionsWeek"));
        nConversionsWeek.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (nConversionsWeek.isSelected()) {
                    //todo remove from pool
                } else {
                    //todo
                }
            }
        });

        RadioButton nConversionsDay = new RadioButton("Day");
        nConversionsDay.setSelected(breadCrumbs.getGraphView().containsGraph("nConversionsDay"));
        nConversionsDay.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (nConversionsDay.isSelected()) {
                    //todo remove from pool
                } else {
                    //todo
                }
            }
        });

        RadioButton nConversionsHour = new RadioButton("Hour");
        nConversionsHour.setSelected(breadCrumbs.getGraphView().containsGraph("nConversionsHour"));
        nConversionsHour.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (nBouncesHour.isSelected()) {
                    //todo remove from pool
                } else {
                    //todo
                }
            }
        });
        menus.add(getMenuCard(
                "Number of Conversions",
                "A conversion, or acquisition, occurs when a user clicks and then acts on an ad. The specific definition of an action depends on the campaign (e.g., buying a product, registering as a new customer or joining a mailing list).",
                new Component[]{nConversionsWeek, nConversionsDay, nConversionsHour}));


        //Total Cost
        RadioButton totalCostWeek = new RadioButton("Week");
        totalCostWeek.setSelected(breadCrumbs.getGraphView().containsGraph("totalCostWeek"));
        totalCostWeek.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (totalCostWeek.isSelected()) {
                    //todo remove from pool
                } else {
                    //todo
                }
            }
        });

        RadioButton totalCostDay = new RadioButton("Day");
        totalCostDay.setSelected(breadCrumbs.getGraphView().containsGraph("totalCostDay"));
        totalCostDay.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (totalCostDay.isSelected()) {
                    //todo remove from pool
                } else {
                    //todo
                }
            }
        });

        RadioButton totalCostHour = new RadioButton("Hour");
        totalCostHour.setSelected(breadCrumbs.getGraphView().containsGraph("totalCostHour"));
        nConversionsHour.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (nBouncesHour.isSelected()) {
                    //todo remove from pool
                } else {
                    //todo
                }
            }
        });
        menus.add(getMenuCard(
                "Total Cost",
                "??The total of the click cost and impression cost.??",
                new Component[]{totalCostWeek, totalCostDay, totalCostHour}));


        //CTR
        RadioButton ctrWeek = new RadioButton("Week");
        ctrWeek.setSelected(breadCrumbs.getGraphView().containsGraph("ctrWeek"));
        ctrWeek.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (ctrWeek.isSelected()) {
                    //todo remove from pool
                } else {
                    //todo
                }
            }
        });

        RadioButton ctrDay = new RadioButton("Day");
        ctrDay.setSelected(breadCrumbs.getGraphView().containsGraph("ctrDay"));
        ctrDay.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (ctrDay.isSelected()) {
                    //todo remove from pool
                } else {
                    //todo
                }
            }
        });

        RadioButton ctrHour = new RadioButton("Hour");
        ctrHour.setSelected(breadCrumbs.getGraphView().containsGraph("ctrHour"));
        ctrHour.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (ctrHour.isSelected()) {
                    //todo remove from pool
                } else {
                    //todo
                }
            }
        });
        menus.add(getMenuCard(
                "CTR",
                "The average number of clicks per impression.",
                new Component[]{ctrWeek, ctrDay, ctrHour}));


        // CPA
        RadioButton cpaWeek = new RadioButton("Week");
        cpaWeek.setSelected(breadCrumbs.getGraphView().containsGraph("cpaWeek"));
        cpaWeek.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (cpaWeek.isSelected()) {
                    //todo remove from pool
                } else {
                    //todo
                }
            }
        });

        RadioButton cpaDay = new RadioButton("Day");
        cpaDay.setSelected(breadCrumbs.getGraphView().containsGraph("cpaDay"));
        cpaDay.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (cpaDay.isSelected()) {
                    //todo remove from pool
                } else {
                    //todo
                }
            }
        });

        RadioButton cpaHour = new RadioButton("Hour");
        cpaHour.setSelected(breadCrumbs.getGraphView().containsGraph("cpaHour"));
        cpaHour.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (cpaHour.isSelected()) {
                    //todo remove from pool
                } else {
                    //todo
                }
            }
        });
        menus.add(getMenuCard(
                "CPA",
                "The average amount of money spent on an advertising campaign for each acquisition (i.e., conversion).",
                new Component[]{cpaWeek, cpaDay, cpaHour}));


        //CPC
        RadioButton cpcWeek = new RadioButton("Week");
        cpcWeek.setSelected(breadCrumbs.getGraphView().containsGraph("cpcWeek"));
        cpcWeek.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (cpcWeek.isSelected()) {
                    //todo remove from pool
                } else {
                    //todo
                }
            }
        });

        RadioButton cpcDay = new RadioButton("Day");
        cpcDay.setSelected(breadCrumbs.getGraphView().containsGraph("cpcDay"));
        cpcDay.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (cpcDay.isSelected()) {
                    //todo remove from pool
                } else {
                    //todo
                }
            }
        });

        RadioButton cpcHour = new RadioButton("Hour");
        cpcHour.setSelected(breadCrumbs.getGraphView().containsGraph("cpcHour"));
        cpcHour.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (cpcHour.isSelected()) {
                    //todo remove from pool
                } else {
                    //todo
                }
            }
        });
        menus.add(getMenuCard(
                "CPC",
                "The average amount of money spent on an advertising campaign for each click.",
                new Component[]{cpcWeek, cpcDay, cpcHour}));


        //CPM
        RadioButton cpmWeek = new RadioButton("Week");
        cpmWeek.setSelected(breadCrumbs.getGraphView().containsGraph("cpmWeek"));
        cpmWeek.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (cpmWeek.isSelected()) {
                    //todo remove from pool
                } else {
                    //todo
                }
            }
        });

        RadioButton cpmDay = new RadioButton("Day");
        cpmDay.setSelected(breadCrumbs.getGraphView().containsGraph("cpmDay"));
        cpmDay.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (cpmDay.isSelected()) {
                    //todo remove from pool
                } else {
                    //todo
                }
            }
        });

        RadioButton cpmHour = new RadioButton("Hour");
        cpmHour.setSelected(breadCrumbs.getGraphView().containsGraph("cpmHour"));
        cpmHour.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (cpmHour.isSelected()) {
                    //todo remove from pool
                } else {
                    //todo
                }
            }
        });
        menus.add(getMenuCard(
                "CPM",
                "The average amount of money spent on an advertising campaign for every one thousand impressions.",
                new Component[]{cpmWeek, cpmDay, cpmHour}));

        //Bounce Rate
        RadioButton bounceRateWeek = new RadioButton("Week");
        bounceRateWeek.setSelected(breadCrumbs.getGraphView().containsGraph("bounceRateWeek"));
        bounceRateWeek.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (bounceRateWeek.isSelected()) {
                    //todo remove from pool
                } else {
                    //todo
                }
            }
        });

        RadioButton bounceRateDay = new RadioButton("Day");
        bounceRateDay.setSelected(breadCrumbs.getGraphView().containsGraph("bounceRateDay"));
        bounceRateDay.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (bounceRateDay.isSelected()) {
                    //todo remove from pool
                } else {
                    //todo
                }
            }
        });

        RadioButton bounceRateHour = new RadioButton("Hour");
        bounceRateHour.setSelected(breadCrumbs.getGraphView().containsGraph("bounceRateHour"));
        bounceRateHour.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (bounceRateHour.isSelected()) {
                    //todo remove from pool
                } else {
                    //todo
                }
            }
        });
        menus.add(getMenuCard(
                "Bounce Rate",
                "The average number of bounces per click.",
                new Component[]{bounceRateWeek, bounceRateDay, bounceRateHour}));

        add(new ListView(getBackground(), menus).getWrappedInScroll(true), BorderLayout.CENTER);
    }

    private JPanel getMenuCard(String title, String description, Component[] options) {
        JPanel tmp = new JPanel(new BorderLayout());

        TitleLabel titleLabel = new TitleLabel(title, TitleLabel.LEFT, 20);
        titleLabel.setForeground(GuiColors.LIGHT);

        tmp.add(titleLabel, BorderLayout.NORTH);
        tmp.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        tmp.setBackground(getBackground());

        TitleLabel descriptionLabel = new TitleLabel("<html>" + description + "</html>", TitleLabel.LEFT, 12);
        descriptionLabel.setForeground(GuiColors.LIGHT);
        tmp.add(descriptionLabel, BorderLayout.CENTER);

        JPanel optionsWrapperPanel = new JPanel(new BorderLayout());
        optionsWrapperPanel.setBackground(getBackground());
        optionsWrapperPanel.setBorder(BorderFactory.createEmptyBorder());
        TitleLabel orderByLabel = new TitleLabel("Order by:", TitleLabel.LEFT, 12);
        orderByLabel.setForeground(GuiColors.LIGHT);
        optionsWrapperPanel.add(orderByLabel, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel(new GridLayout(1, options.length, 4, 4));
        optionsPanel.setBackground(tmp.getBackground());
        optionsPanel.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, tmp.getBackground()));
        for (int i = 0; i < options.length; i++)
            optionsPanel.add(options[i]);
        optionsWrapperPanel.add(optionsPanel, BorderLayout.SOUTH);

        tmp.add(optionsWrapperPanel, BorderLayout.SOUTH);

        return tmp;
    }
}
