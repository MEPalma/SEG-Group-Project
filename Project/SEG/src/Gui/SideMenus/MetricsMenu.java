package Gui.SideMenus;

import Gui.GuiColors;
import Gui.GuiComponents.ListView;
import Gui.GuiComponents.RPanel;
import Gui.GuiComponents.RadioButton;
import Gui.GuiComponents.TitleLabel;
import Gui.MainController;

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
                    mainController.pushNewNumberOfImpressionsPerWeek("nImpressionsWeek");
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
                    mainController.pushNewNumberOfImpressionsPerDay("nImpressionsDay");
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
                    mainController.pushNewNumberOfImpressionsPerHour("nImpressionsHour");
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
            public void mousePressed(MouseEvent e) {
                if (nClicksWeek.isSelected()) {
                    mainController.popFromGraphView("nClicksWeek");
                } else {
                    mainController.pushNewNumberOfClicksPerWeek("nClicksWeek");
                }
            }
        });

        RadioButton nClicksDay = new RadioButton("Day");
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

        RadioButton nClicksHour = new RadioButton("Hour");
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
        RadioButton nUniquesWeek = new RadioButton("Week");
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

        RadioButton nUniquesDay = new RadioButton("Day");
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

        RadioButton nUniquesHour = new RadioButton("Hour");
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
        RadioButton nBouncesWeek = new RadioButton("Week");
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

        RadioButton nBouncesDay = new RadioButton("Day");
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

        RadioButton nBouncesHour = new RadioButton("Hour");
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
        RadioButton nConversionsWeek = new RadioButton("Week");
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

        RadioButton nConversionsDay = new RadioButton("Day");
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

        RadioButton nConversionsHour = new RadioButton("Hour");
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
        RadioButton totalCostWeek = new RadioButton("Week");
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

        RadioButton totalCostDay = new RadioButton("Day");
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

        RadioButton totalCostHour = new RadioButton("Hour");
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
                "??The total of the click cost and impression cost.??",
                new Component[]{totalCostWeek, totalCostDay, totalCostHour}));


        //CTR
        RadioButton ctrWeek = new RadioButton("Week");
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

        RadioButton ctrDay = new RadioButton("Day");
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

        RadioButton ctrHour = new RadioButton("Hour");
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
        RadioButton cpaWeek = new RadioButton("Week");
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

        RadioButton cpaDay = new RadioButton("Day");
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

        RadioButton cpaHour = new RadioButton("Hour");
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
        RadioButton cpcWeek = new RadioButton("Week");
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

        RadioButton cpcDay = new RadioButton("Day");
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

        RadioButton cpcHour = new RadioButton("Hour");
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
        RadioButton cpmWeek = new RadioButton("Week");
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

        RadioButton cpmDay = new RadioButton("Day");
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

        RadioButton cpmHour = new RadioButton("Hour");
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
        RadioButton bounceRateWeek = new RadioButton("Week");
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

        RadioButton bounceRateDay = new RadioButton("Day");
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

        RadioButton bounceRateHour = new RadioButton("Hour");
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
