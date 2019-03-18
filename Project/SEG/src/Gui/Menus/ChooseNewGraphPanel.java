package Gui.Menus;

import Commons.GraphSpecs;
import Gui.GuiColors;
import Gui.GuiComponents.*;
import Gui.MainController;
import Gui.TakeActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

public class ChooseNewGraphPanel extends RPanel {
    public static String[] METRICS = {
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


    public static String[] BOUNCE_DEF = {"Time", "Number of Pages"};
    public static String[] TIME_SPANS = {"WEEK", "DAY", "HOUR"};

    private final MainController mainController;

    private final TitleLabel messageLabel;
    private final DropDown metricsChooser;
    private final DropDown bounceDefinitionChooser;
    private final DropDown timespanChooser;

//    private final JDialog host;

    public ChooseNewGraphPanel(MainController mainController) {
        super(GuiColors.BASE_SMOKE, new BorderLayout());

        this.mainController = mainController;
//        this.host = host;

        this.messageLabel = new TitleLabel("", TitleLabel.CENTER, 12);
        messageLabel.setForeground(GuiColors.RED_ERROR);

        TakeActionListener takeActionListener = new TakeActionListener() {
            @Override
            public void takeAction() {
                messageLabel.setText("");
                refresh();
            }
        };

        this.metricsChooser = new DropDown(METRICS, METRICS_DESCRIPTIONS, 0);
        this.metricsChooser.addTakeActionListener(takeActionListener);

        this.bounceDefinitionChooser = new DropDown(BOUNCE_DEF, null, 0);
        this.bounceDefinitionChooser.addTakeActionListener(takeActionListener);

        this.timespanChooser = new DropDown(TIME_SPANS, null, 0);
        this.timespanChooser.addTakeActionListener(takeActionListener);

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
                if (handleAdd()) ; //host.setVisible(false);
                else {
                    messageLabel.setText("You already have this graph!");
                    refresh();
                }
            }
        });

        JPanel addLabelWrapper = new JPanel(new GridLayout(1, 1));
        addLabelWrapper.setBackground(GuiColors.BASE_WHITE);
        addLabelWrapper.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, GuiColors.BASE_SMOKE));
        addLabelWrapper.add(addLabel);

        add(addLabelWrapper, BorderLayout.SOUTH);

        List<Component> items = new LinkedList<Component>();
        items.add(getMetricsChooserCell());

        if (this.metricsChooser.getSelectedIndex() == 3)//Bounce
            items.add(getBounceChooserCell());

        items.add(getTimeSpanChooserCell());

        add(new ListView(GuiColors.BASE_WHITE, items, true).getWrappedInScroll(true), BorderLayout.CENTER);

        repaint();
        revalidate();
    }

    private boolean handleAdd() {
        GraphSpecs graphSpecs = mainController.proposeNewGraph(getChosenMetric(), getChosenTimeSpan(), getChosenBounceDef());
        if (graphSpecs != null) {
            mainController.pushToGraphView(graphSpecs);
            return true;
        }
        return false;
    }

    private JPanel getMetricsChooserCell() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(GuiColors.BASE_WHITE);
        wrapper.setBorder(BorderFactory.createMatteBorder(0, 8, 0, 8, GuiColors.BASE_WHITE));

        wrapper.add(new TitleLabel("Metric", TitleLabel.LEFT, 18), BorderLayout.NORTH);
        wrapper.add(this.metricsChooser, BorderLayout.CENTER);

        return wrapper;
    }

    private JPanel getBounceChooserCell() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(GuiColors.BASE_WHITE);
        wrapper.setBorder(BorderFactory.createMatteBorder(8, 8, 0, 8, GuiColors.BASE_WHITE));

        wrapper.add(new TitleLabel("Bounce Definition", TitleLabel.LEFT, 18), BorderLayout.NORTH);
        wrapper.add(this.bounceDefinitionChooser, BorderLayout.CENTER);
        return wrapper;
    }

    private JPanel getTimeSpanChooserCell() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(GuiColors.BASE_WHITE);
        wrapper.setBorder(BorderFactory.createMatteBorder(8, 8, 0, 8, GuiColors.BASE_WHITE));

        wrapper.add(new TitleLabel("Time Grouping", TitleLabel.LEFT, 18), BorderLayout.NORTH);
        wrapper.add(this.timespanChooser, BorderLayout.CENTER);

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
