package Gui.Menus;

import Gui.GuiColors;
import Gui.GuiComponents.*;
import Gui.MainController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

public class Deprecated_MetricsMenu extends RPanel {

    private final MainController mainController;

    public Deprecated_MetricsMenu(MainController mainController) {
        super(GuiColors.BASE_WHITE, new BorderLayout());
        this.mainController = mainController;
        refresh();
    }

    @Override
    public void refresh() {
        removeAll();

        List<Component> menus = new LinkedList<Component>();
//TODO DO IN BACKGROUND!!!!!!!!
        //NUMBER OF IMPRESSIONS
        CheckBox nImpressionsWeek = new CheckBox("Week");
        nImpressionsWeek.setSelected(mainController.doesGraphViewContainGraph("nImpressionsWeek"));
        nImpressionsWeek.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (nImpressionsWeek.isSelected()) {
                    mainController.popFromGraphView("nImpressionsWeek");
                } else {
                    mainController.pushNewNumberOfImpressionsPerWeek("nImpressionsWeek");
                }
            }
        });

        CheckBox nImpressionsDay = new CheckBox("Day");
        nImpressionsDay.setSelected(mainController.doesGraphViewContainGraph("nImpressionsDay"));
        nImpressionsDay.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (nImpressionsDay.isSelected()) {
                    mainController.popFromGraphView("nImpressionsDay");
                } else {
                    mainController.pushNewNumberOfImpressionsPerDay("nImpressionsDay");
                }
            }
        });

        CheckBox nImpressionsHour = new CheckBox("Hour");
        nImpressionsHour.setSelected(mainController.doesGraphViewContainGraph("nImpressionsHour"));
        nImpressionsHour.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (nImpressionsHour.isSelected()) {
                    mainController.popFromGraphView("nImpressionsHour");
                } else {
                    mainController.pushNewNumberOfImpressionsPerHour("nImpressionsHour");
                }
            }
        });
        menus.add(getMenuCard(
                "Number of Impressions",
                "An impression occurs whenever an ad is shown to a user, regardless of whether they click on it.",
                new Component[]{nImpressionsWeek, nImpressionsDay, nImpressionsHour}));


        // Number of Clicks
        CheckBox nClicksWeek = new CheckBox("Week");
        nClicksWeek.setSelected(mainController.doesGraphViewContainGraph("nClicksWeek"));
        nClicksWeek.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (nClicksWeek.isSelected()) {
                    mainController.popFromGraphView("nClicksWeek");
                } else {
                    mainController.pushNewNumberOfClicksPerWeek("nClicksWeek");
                }
            }
        });

        CheckBox nClicksDay = new CheckBox("Day");
        nClicksDay.setSelected(mainController.doesGraphViewContainGraph("nClicksDay"));
        nClicksDay.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (nClicksDay.isSelected()) {
                    mainController.popFromGraphView("nClicksDay");
                } else {
                    mainController.pushNewNumberOfClicksPerDay("nClicksDay");
                }
            }
        });

        CheckBox nClicksHour = new CheckBox("Hour");
        nClicksHour.setSelected(mainController.doesGraphViewContainGraph("nClicksHour"));
        nClicksHour.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (nClicksHour.isSelected()) {
                    mainController.popFromGraphView("nClicksHour");
                } else {
                    mainController.pushNewNumberOfClicksPerHour("nClicksHour");
                }
            }
        });
        menus.add(getMenuCard(
                "Number of Clicks",
                "A click occurs when a user clicks on an ad that is shown to them.",
                new Component[]{nClicksWeek, nClicksDay, nClicksHour}));


        //Number of Uniques
        CheckBox nUniquesWeek = new CheckBox("Week");
        nUniquesWeek.setSelected(mainController.doesGraphViewContainGraph("nUniquesWeek"));
        nUniquesWeek.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (nUniquesWeek.isSelected()) {
                    mainController.popFromGraphView("nUniquesWeek");
                } else {
                    mainController.pushNewNumberOfUniquesPerWeek("nUniquesWeek");
                }
            }
        });

        CheckBox nUniquesDay = new CheckBox("Day");
        nUniquesDay.setSelected(mainController.doesGraphViewContainGraph("nUniquesDay"));
        nUniquesDay.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (nUniquesDay.isSelected()) {
                    mainController.popFromGraphView("nUniquesDay");
                } else {
                    mainController.pushNewNumberOfUniquesPerDay("nUniquesDay");
                }
            }
        });

        CheckBox nUniquesHour = new CheckBox("Hour");
        nUniquesHour.setSelected(mainController.doesGraphViewContainGraph("nUniquesHour"));
        nUniquesHour.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (nUniquesHour.isSelected()) {
                    mainController.popFromGraphView("nUniquesHour");
                } else {
                    mainController.pushNewNumberOfUniquesPerHour("nUniquesHour");
                }
            }
        });
        menus.add(getMenuCard(
                "Number of Uniques",
                "The number of unique users that click on an ad during the course of a campaign.",
                new Component[]{nUniquesWeek, nUniquesDay, nUniquesHour}));


        //Number of Bounces
        CheckBox nBouncesWeek = new CheckBox("Week");
        nBouncesWeek.setSelected(mainController.doesGraphViewContainGraph("nBouncesWeek"));
        nBouncesWeek.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (nBouncesWeek.isSelected()) {
                    mainController.popFromGraphView("nBouncesWeek");
                } else {
                    mainController.pushNewNumberOfBouncesPerWeek("nBouncesWeek");
                }
            }
        });

        CheckBox nBouncesDay = new CheckBox("Day");
        nBouncesDay.setSelected(mainController.doesGraphViewContainGraph("nBouncesDay"));
        nBouncesDay.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (nBouncesDay.isSelected()) {
                    mainController.popFromGraphView("nBouncesDay");
                } else {
                    mainController.pushNewNumberOfBouncesPerDay("nBouncesDay");
                }
            }
        });

        CheckBox nBouncesHour = new CheckBox("Hour");
        nBouncesHour.setSelected(mainController.doesGraphViewContainGraph("nBouncesHour"));
        nBouncesHour.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (nBouncesHour.isSelected()) {
                    mainController.popFromGraphView("nBouncesHour");
                } else {
                    mainController.pushNewNumberOfBouncesPerHour("nBouncesHour");
                }
            }
        });
        menus.add(getMenuCard(
                "Number of Bounces",
                "A user clicks on an ad, but then fails to interact with the website (typically detected when a user navigates away from the website after a short time, or when only a single page has been viewed).",
                new Component[]{nBouncesWeek, nBouncesDay, nBouncesHour}));


        //Number of conversions
        CheckBox nConversionsWeek = new CheckBox("Week");
        nConversionsWeek.setSelected(mainController.doesGraphViewContainGraph("nConversionsWeek"));
        nConversionsWeek.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (nConversionsWeek.isSelected()) {
                    mainController.popFromGraphView("nConversionsWeek");
                } else {
                    mainController.pushNewNumberOfConversionsPerWeek("nConversionsWeek");
                }
            }
        });

        CheckBox nConversionsDay = new CheckBox("Day");
        nConversionsDay.setSelected(mainController.doesGraphViewContainGraph("nConversionsDay"));
        nConversionsDay.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (nConversionsDay.isSelected()) {
                    mainController.popFromGraphView("nConversionsDay");
                } else {
                    mainController.pushNewNumberOfConversionsPerDay("nConversionsDay");
                }
            }
        });

        CheckBox nConversionsHour = new CheckBox("Hour");
        nConversionsHour.setSelected(mainController.doesGraphViewContainGraph("nConversionsHour"));
        nConversionsHour.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (nBouncesHour.isSelected()) {
                    mainController.popFromGraphView("nConversionsHour");
                } else {
                    mainController.pushNewNumberOfConversionsPerHour("nConversionsHour");
                }
            }
        });
        menus.add(getMenuCard(
                "Number of Conversions",
                "A conversion, or acquisition, occurs when a user clicks and then acts on an ad. The specific definition of an action depends on the campaign (e.g., buying a product, registering as a new customer or joining a mailing list).",
                new Component[]{nConversionsWeek, nConversionsDay, nConversionsHour}));


        //Total Cost
        CheckBox totalCostWeek = new CheckBox("Week");
        totalCostWeek.setSelected(mainController.doesGraphViewContainGraph("totalCostWeek"));
        totalCostWeek.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (totalCostWeek.isSelected()) {
                    mainController.popFromGraphView("totalCostWeek");
                } else {
                    mainController.pushNewNumberOfTotalCostPerWeek("totalCostWeek");
                }
            }
        });

        CheckBox totalCostDay = new CheckBox("Day");
        totalCostDay.setSelected(mainController.doesGraphViewContainGraph("totalCostDay"));
        totalCostDay.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (totalCostDay.isSelected()) {
                    mainController.popFromGraphView("totalCostDay");
                } else {
                    mainController.pushNewNumberOfTotalCostPerDay("totalCostDay");
                }
            }
        });

        CheckBox totalCostHour = new CheckBox("Hour");
        totalCostHour.setSelected(mainController.doesGraphViewContainGraph("totalCostHour"));
        totalCostHour.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (totalCostHour.isSelected()) {
                    mainController.popFromGraphView("totalCostHour");
                } else {
                    mainController.pushNewNumberOfTotalCostPerHour("totalCostHour");
                }
            }
        });
        menus.add(getMenuCard(
                "Total Cost",
                "The total of the click cost and impression cost.",
                new Component[]{totalCostWeek, totalCostDay, totalCostHour}));


        //CTR
        CheckBox ctrWeek = new CheckBox("Week");
        ctrWeek.setSelected(mainController.doesGraphViewContainGraph("ctrWeek"));
        ctrWeek.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (ctrWeek.isSelected()) {
                    mainController.popFromGraphView("ctrWeek");
                } else {
                    mainController.pushNewCTRPerWeek("ctrWeek");
                }
            }
        });

        CheckBox ctrDay = new CheckBox("Day");
        ctrDay.setSelected(mainController.doesGraphViewContainGraph("ctrDay"));
        ctrDay.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (ctrDay.isSelected()) {
                    mainController.popFromGraphView("ctrDay");
                } else {
                    mainController.pushNewCTRPerDay("ctrDay");
                }
            }
        });

        CheckBox ctrHour = new CheckBox("Hour");
        ctrHour.setSelected(mainController.doesGraphViewContainGraph("ctrHour"));
        ctrHour.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (ctrHour.isSelected()) {
                    mainController.popFromGraphView("ctrHour");
                } else {
                    mainController.pushNewCTRPerHour("ctrHour");
                }
            }
        });
        menus.add(getMenuCard(
                "CTR",
                "The average number of clicks per impression.",
                new Component[]{ctrWeek, ctrDay, ctrHour}));


        // CPA
        CheckBox cpaWeek = new CheckBox("Week");
        cpaWeek.setSelected(mainController.doesGraphViewContainGraph("cpaWeek"));
        cpaWeek.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (cpaWeek.isSelected()) {
                    mainController.popFromGraphView("cpaWeek");
                } else {
                    mainController.pushNewCPAPerWeek("cpaWeek");
                }
            }
        });

        CheckBox cpaDay = new CheckBox("Day");
        cpaDay.setSelected(mainController.doesGraphViewContainGraph("cpaDay"));
        cpaDay.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (cpaDay.isSelected()) {
                    mainController.popFromGraphView("cpaDay");
                } else {
                    mainController.pushNewCPAPerDay("cpaDay");
                }
            }
        });

        CheckBox cpaHour = new CheckBox("Hour");
        cpaHour.setSelected(mainController.doesGraphViewContainGraph("cpaHour"));
        cpaHour.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (cpaHour.isSelected()) {
                    mainController.popFromGraphView("cpaHour");
                } else {
                    mainController.pushNewCPAPerHour("cpaHour");
                }
            }
        });
        menus.add(getMenuCard(
                "CPA",
                "The average amount of money spent on an advertising campaign for each acquisition (i.e., conversion).",
                new Component[]{cpaWeek, cpaDay, cpaHour}));


        //CPC
        CheckBox cpcWeek = new CheckBox("Week");
        cpcWeek.setSelected(mainController.doesGraphViewContainGraph("cpcWeek"));
        cpcWeek.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (cpcWeek.isSelected()) {
                    mainController.popFromGraphView("cpcWeek");
                } else {
                    mainController.pushNewCPCPerWeek("cpcWeek");
                }
            }
        });

        CheckBox cpcDay = new CheckBox("Day");
        cpcDay.setSelected(mainController.doesGraphViewContainGraph("cpcDay"));
        cpcDay.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (cpcDay.isSelected()) {
                    mainController.popFromGraphView("cpcDay");
                } else {
                    mainController.pushNewCPCPerDay("cpcDay");
                }
            }
        });

        CheckBox cpcHour = new CheckBox("Hour");
        cpcHour.setSelected(mainController.doesGraphViewContainGraph("cpcHour"));
        cpcHour.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (cpcHour.isSelected()) {
                    mainController.popFromGraphView("cpcHour");
                } else {
                    mainController.pushNewCPCPerHour("cpcHour");
                }
            }
        });
        menus.add(getMenuCard(
                "CPC",
                "The average amount of money spent on an advertising campaign for each click.",
                new Component[]{cpcWeek, cpcDay, cpcHour}));


        //CPM
        CheckBox cpmWeek = new CheckBox("Week");
        cpmWeek.setSelected(mainController.doesGraphViewContainGraph("cpmWeek"));
        cpmWeek.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (cpmWeek.isSelected()) {
                    mainController.popFromGraphView("cpmWeek");
                } else {
                    mainController.pushNewCPMPerWeek("cpmWeek");
                }
            }
        });

        CheckBox cpmDay = new CheckBox("Day");
        cpmDay.setSelected(mainController.doesGraphViewContainGraph("cpmDay"));
        cpmDay.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (cpmDay.isSelected()) {
                    mainController.popFromGraphView("cpmDay");
                } else {
                    mainController.pushNewCPMPerDay("cpmDay");
                }
            }
        });

        CheckBox cpmHour = new CheckBox("Hour");
        cpmHour.setSelected(mainController.doesGraphViewContainGraph("cpmHour"));
        cpmHour.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (cpmHour.isSelected()) {
                    mainController.popFromGraphView("cpmHour");
                } else {
                    mainController.pushNewCPMPerHour("cpMHour");
                }
            }
        });
        menus.add(getMenuCard(
                "CPM",
                "The average amount of money spent on an advertising campaign for every one thousand impressions.",
                new Component[]{cpmWeek, cpmDay, cpmHour}));

        //Bounce Rate
        CheckBox bounceRateWeek = new CheckBox("Week");
        bounceRateWeek.setSelected(mainController.doesGraphViewContainGraph("bounceRateWeek"));
        bounceRateWeek.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (bounceRateWeek.isSelected()) {
                    mainController.popFromGraphView("bounceRateWeek");
                } else {
                    mainController.pushNewBounceRatePerWeek("bounceRateWeek");
                }
            }
        });

        CheckBox bounceRateDay = new CheckBox("Day");
        bounceRateDay.setSelected(mainController.doesGraphViewContainGraph("bounceRateDay"));
        bounceRateDay.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (bounceRateDay.isSelected()) {
                    mainController.popFromGraphView("bounceRateDay");
                } else {
                    mainController.pushNewBounceRatePerDay("bounceRateDay");
                }
            }
        });

        CheckBox bounceRateHour = new CheckBox("Hour");
        bounceRateHour.setSelected(mainController.doesGraphViewContainGraph("bounceRateHour"));
        bounceRateHour.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (bounceRateHour.isSelected()) {
                    mainController.popFromGraphView("bounceRateHour");
                } else {
                    mainController.pushNewBounceRatePerHour("bounceRateHour");
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

        tmp.add(titleLabel, BorderLayout.NORTH);
        tmp.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        tmp.setBackground(getBackground());

        TitleLabel descriptionLabel = new TitleLabel("<html>" + description + "</html>", TitleLabel.LEFT, 12);
        descriptionLabel.setForeground(GuiColors.DARK_GRAY);
        tmp.add(descriptionLabel, BorderLayout.CENTER);

        JPanel optionsWrapperPanel = new JPanel(new BorderLayout());
        optionsWrapperPanel.setBackground(getBackground());
        optionsWrapperPanel.setBorder(BorderFactory.createEmptyBorder());
        TitleLabel orderByLabel = new TitleLabel("Order by:", TitleLabel.LEFT, 12);
        orderByLabel.setForeground(GuiColors.DARK_GRAY);
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
