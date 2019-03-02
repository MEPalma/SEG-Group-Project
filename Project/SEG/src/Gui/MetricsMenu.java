package Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

public class MetricsMenu extends RPanel {

    private final MainController mainController;

    public MetricsMenu(MainController mainController) {
        super(GuiColors.BASE_LIGHT, new BorderLayout());
        this.mainController = mainController;
        refresh();
    }

    @Override
    public void refresh() {
        removeAll();

        List<Component> menus = new LinkedList<Component>();
//TODO DO IN BACKGROUND!!!!!!!!
        //NUMBER OF IMPRESSIONS
        RadioButton nImpressionsWeek = new RadioButton("Week");
        nImpressionsWeek.setSelected(mainController.doesGraphViewContainGraph("nImpressionsWeek"));
        nImpressionsWeek.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (nImpressionsWeek.isSelected()) {
                    mainController.popFromGraphView("nImpressionsWeek");
                } else {
                    mainController.pushToGraphView("nImpressionsWeek", "Sample Chart", "xAxisName", "yAxiseName", null);
                }
            }
        });

        RadioButton nImpressionsDay = new RadioButton("Day");
        nImpressionsDay.setSelected(mainController.doesGraphViewContainGraph("nImpressionsDay"));
        nImpressionsDay.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (nImpressionsDay.isSelected()) {
                    mainController.popFromGraphView("nImpressionsDay");
                } else {
                    mainController.pushToGraphView("nImpressionsWeek", "Sample Chart", "xAxisName", "yAxiseName", null);
                }
            }
        });

        RadioButton nImpressionsHour = new RadioButton("Hour");
        nImpressionsHour.setSelected(mainController.doesGraphViewContainGraph("nImpressionsHour"));
        nImpressionsHour.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (nImpressionsHour.isSelected()) {
                    mainController.popFromGraphView("nImpressionsHour");
                } else {
                }
            }
        });
        menus.add(getMenuCard(
                "Number of Impressions",
                "An impression occurs whenever an ad is shown to a user, regardless of whether they click on it.",
                new Component[]{nImpressionsWeek, nImpressionsDay, nImpressionsHour}));


        // Number of Clicks
        RadioButton nClicksWeek = new RadioButton("Week");
        nClicksWeek.setSelected(mainController.doesGraphViewContainGraph("nClicksWeek"));
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
        nClicksDay.setSelected(mainController.doesGraphViewContainGraph("nClicksDay"));
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
        nClicksHour.setSelected(mainController.doesGraphViewContainGraph("nClicksHour"));
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
        nUniquesWeek.setSelected(mainController.doesGraphViewContainGraph("nUniquesWeek"));
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
        nUniquesDay.setSelected(mainController.doesGraphViewContainGraph("nUniquesDay"));
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
        nUniquesHour.setSelected(mainController.doesGraphViewContainGraph("nUniquesHour"));
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
        nBouncesWeek.setSelected(mainController.doesGraphViewContainGraph("nBouncesWeek"));
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
        nBouncesDay.setSelected(mainController.doesGraphViewContainGraph("nBouncesDay"));
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
        nBouncesHour.setSelected(mainController.doesGraphViewContainGraph("nBouncesHour"));
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
        nConversionsWeek.setSelected(mainController.doesGraphViewContainGraph("nConversionsWeek"));
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
        nConversionsDay.setSelected(mainController.doesGraphViewContainGraph("nConversionsDay"));
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
        nConversionsHour.setSelected(mainController.doesGraphViewContainGraph("nConversionsHour"));
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
        totalCostWeek.setSelected(mainController.doesGraphViewContainGraph("totalCostWeek"));
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
        totalCostDay.setSelected(mainController.doesGraphViewContainGraph("totalCostDay"));
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
        totalCostHour.setSelected(mainController.doesGraphViewContainGraph("totalCostHour"));
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
        ctrWeek.setSelected(mainController.doesGraphViewContainGraph("ctrWeek"));
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
        ctrDay.setSelected(mainController.doesGraphViewContainGraph("ctrDay"));
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
        ctrHour.setSelected(mainController.doesGraphViewContainGraph("ctrHour"));
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
        cpaWeek.setSelected(mainController.doesGraphViewContainGraph("cpaWeek"));
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
        cpaDay.setSelected(mainController.doesGraphViewContainGraph("cpaDay"));
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
        cpaHour.setSelected(mainController.doesGraphViewContainGraph("cpaHour"));
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
        cpcWeek.setSelected(mainController.doesGraphViewContainGraph("cpcWeek"));
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
        cpcDay.setSelected(mainController.doesGraphViewContainGraph("cpcDay"));
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
        cpcHour.setSelected(mainController.doesGraphViewContainGraph("cpcHour"));
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
        cpmWeek.setSelected(mainController.doesGraphViewContainGraph("cpmWeek"));
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
        cpmDay.setSelected(mainController.doesGraphViewContainGraph("cpmDay"));
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
        cpmHour.setSelected(mainController.doesGraphViewContainGraph("cpmHour"));
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
        bounceRateWeek.setSelected(mainController.doesGraphViewContainGraph("bounceRateWeek"));
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
        bounceRateDay.setSelected(mainController.doesGraphViewContainGraph("bounceRateDay"));
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
        bounceRateHour.setSelected(mainController.doesGraphViewContainGraph("bounceRateHour"));
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
