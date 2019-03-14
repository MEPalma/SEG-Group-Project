package Gui.Menus;

import Gui.GuiColors;
import Gui.GuiComponents.ListView;
import Gui.GuiComponents.MenuLabel;
import Gui.GuiComponents.RPanel;
import Gui.GuiComponents.TitleLabel;
import Gui.MainController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ChooseNewGraphPanel extends RPanel {
    private static String[] METRICS = {
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
            "Bounce Rate"};

    private static String[] METRICS_DESCRIPTIONS = {
            "An impression occurs whenever an ad is shown to a user, regardless of whether they click on it.",
            "A click occurs when a user clicks on an ad that is shown to them.",
            "The number of unique users that click on an ad during the course of a campaign.",
            "A user clicks on an ad, but then fails to interact with the website (typically detected when a user navigates away from the website after a short time, or when only a single page has been viewed).",
            "A conversion, or acquisition, occurs when a user clicks and then acts on an ad. The specific definition of an action depends on the campaign (e.g., buying a product, registering as a new customer or joining a mailing list).",
            "The total of the click cost and impression cost.",
            "The average number of clicks per impression.",
            "The average amount of money spent on an advertising campaign for each acquisition (i.e., conversion).",
            "The average amount of money spent on an advertising campaign for each click.",
            "The average amount of money spent on an advertising campaign for every one thousand impressions.",
            "The average number of bounces per click."};


    private static String[] BOUNCE_DEF = {"DEF 1", "DEF 2"};
    private static String[] TIME_SPANS = {"WEEK", "DAY", "HOUR"};

    private final MainController mainController;

    private final TitleLabel descriptionLabel;
    private final TitleLabel messageLabel;
    private final JComboBox metricsChooser;
    private final JComboBox bounceDefinitionChooser;
    private final JComboBox timespanChooser;

    private final JFrame host;

    public ChooseNewGraphPanel(MainController mainController, JFrame host) {
        super(GuiColors.BASE_WHITE, new BorderLayout());

        this.mainController = mainController;
        this.host = host;

        this.descriptionLabel = new TitleLabel("", TitleLabel.LEFT, 10);

        this.messageLabel = new TitleLabel("", TitleLabel.CENTER, 12);

        this.metricsChooser = new JComboBox(METRICS);
        this.metricsChooser.setSelectedIndex(0);
        this.metricsChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                descriptionLabel.setText("<html>" + METRICS_DESCRIPTIONS[metricsChooser.getSelectedIndex()] + "</html>");
                refresh();
            }
        });

        this.bounceDefinitionChooser = new JComboBox(BOUNCE_DEF);
        this.bounceDefinitionChooser.setSelectedIndex(0);

        this.timespanChooser = new JComboBox(TIME_SPANS);
        this.timespanChooser.setSelectedIndex(0);

        refresh();
    }

    @Override
    public void refresh() {
        removeAll();

        add(this.messageLabel, BorderLayout.NORTH);

        MenuLabel addLabel = new MenuLabel("ADD", MenuLabel.CENTER, 16);
        addLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (handleAdd()) host.setVisible(false);
            }
        });
        add(addLabel, BorderLayout.SOUTH);

        List<Component> items = new LinkedList<Component>();
        items.add(getMetricsChooserCell());
        items.add(getBounceChooserCell());
        items.add(getTimeSpanChooserCell());

        add(new ListView(GuiColors.BASE_WHITE, items, true).getWrappedInScroll(true), BorderLayout.CENTER);

        repaint();
        revalidate();
    }

    private boolean handleAdd() {
        mainController.pushNewNumberOfBouncesPerWeek(Integer.toString(new Random().nextInt()));
        return true;

        //else error message in message dialog and return false
    }

    private JPanel getMetricsChooserCell() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(GuiColors.BASE_WHITE);
        wrapper.setBorder(BorderFactory.createEmptyBorder());

        this.descriptionLabel.setText("<html>" + METRICS_DESCRIPTIONS[metricsChooser.getSelectedIndex()] + "</html>");

        wrapper.add(new TitleLabel("Metric", TitleLabel.LEFT, 16), BorderLayout.NORTH);
        wrapper.add(this.metricsChooser, BorderLayout.SOUTH);
        wrapper.add(this.descriptionLabel, BorderLayout.CENTER);

        return wrapper;
    }

    private JPanel getBounceChooserCell() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(GuiColors.BASE_WHITE);
        wrapper.setBorder(BorderFactory.createEmptyBorder());

        wrapper.add(new TitleLabel("Bounce Definition", TitleLabel.LEFT, 18), BorderLayout.NORTH);
        wrapper.add(this.bounceDefinitionChooser, BorderLayout.CENTER);
        return wrapper;
    }

    private JPanel getTimeSpanChooserCell() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(GuiColors.BASE_WHITE);
        wrapper.setBorder(BorderFactory.createEmptyBorder());

        wrapper.add(new TitleLabel("Time Grouping", TitleLabel.LEFT, 18), BorderLayout.NORTH);
        wrapper.add(this.timespanChooser, BorderLayout.CENTER);

        return wrapper;
    }
}
