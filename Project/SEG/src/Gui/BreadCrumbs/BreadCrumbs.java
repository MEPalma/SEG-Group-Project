package Gui.BreadCrumbs;

import Gui.GraphView;
import Gui.GuiColors;
import Gui.GuiComponents.MenuLabel;
import Gui.GuiComponents.RPanel;
import Gui.GuiComponents.TextView;
import Gui.GuiComponents.TitleLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Stack;

public class BreadCrumbs extends JPanel {
    private Color BACKGROUND = GuiColors.BASE_SMOKE;
    private Color SELECTED = GuiColors.BASE_WHITE;

    private JPanel viewPanel;
    private GraphView graphView;
    private BreadCrumbsHoster breadCrumbsHoster;

    private JProgressBar progressBar;
    private boolean visibleProgressBar;

    private JPanel errorPanel;
    private boolean visibleErrorPanel;

    private Stack<RPanel> panesStacks;
    private Stack<JPanel> crumbsStack;

    private SwingWorker backgroundTask;

    public BreadCrumbs(BreadCrumbsHoster breadCrumbsHoster, JPanel viewPanel, GraphView graphView) {
        super();
        setBackground(SELECTED);
        setBorder(BorderFactory.createEmptyBorder());

        this.viewPanel = viewPanel;
        this.graphView = graphView;
        this.breadCrumbsHoster = breadCrumbsHoster;

        this.visibleProgressBar = false;
        this.progressBar = new JProgressBar();
        this.progressBar.setIndeterminate(true);
        this.progressBar.setBorderPainted(false);
        this.progressBar.setPreferredSize(new Dimension(30, 30));
        this.progressBar.setBorderPainted(false);

        setBackground(BACKGROUND);
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, GuiColors.BASE_WHITE));
        setPreferredSize(new Dimension(250, 50));

        this.panesStacks = new Stack<RPanel>();
        this.crumbsStack = new Stack<JPanel>();
    }

    private JPanel getNewCrumb(String text) {
        JPanel crumb = new JPanel(new BorderLayout());

        if (this.crumbsStack.size() > 0) crumb.setBackground(SELECTED);
        else crumb.setBackground(GuiColors.BASE_PRIME);
        crumb.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, GuiColors.BASE_SMOKE));
        crumb.add(new TitleLabel(text, JLabel.CENTER, 16), BorderLayout.CENTER);

        final int myIndex = crumbsStack.size();
        crumb.addMouseListener(new MouseAdapter() {
            Color previousColor;

            @Override
            public void mouseClicked(MouseEvent e) {
                navigateTo(myIndex);
                previousColor = SELECTED;
                setBackground(SELECTED);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                previousColor = crumb.getBackground();
                crumb.setBackground(SELECTED);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                crumb.setBackground(previousColor);
            }
        });

        return crumb;
    }

    private void navigateTo(int index) {
        cancelBackgroundTask();
        stopProgressBar();

        if (index < 0) {
            viewPanel.removeAll();
            crumbsStack.clear();
            panesStacks.clear();
        } else if (index >= 0 && index < this.crumbsStack.size()) {
            //unselect all
            for (JPanel c : crumbsStack)
                c.setBackground(BACKGROUND);

            //delete the followings
            while (crumbsStack.size() - 1 > index) {
                crumbsStack.pop();
                panesStacks.pop();
            }

            crumbsStack.get(index).setBackground(SELECTED);
            viewPanel.removeAll();

            panesStacks.get(index).refresh();

            viewPanel.add(this, BorderLayout.NORTH);
            viewPanel.add(panesStacks.get(index), BorderLayout.CENTER);
            if (this.visibleProgressBar) viewPanel.add(this.progressBar, BorderLayout.SOUTH);

            panesStacks.get(index).repaint();
            panesStacks.get(index).revalidate();
        }
        refresh();
        this.breadCrumbsHoster.getBreadCrumbs().refresh();//TODO remove
        this.breadCrumbsHoster.refresh();
    }

    public synchronized void push(String name, RPanel view) {
        cancelBackgroundTask();

        this.panesStacks.push(view);
        this.crumbsStack.push(getNewCrumb(name));

        navigateTo(panesStacks.size() - 1);
    }

    public synchronized void pop() {
        this.crumbsStack.pop();
        this.panesStacks.pop();

        refresh();

        navigateTo(panesStacks.size() - 1);
    }

    private void refresh() {
        setPreferredSize(new Dimension((this.crumbsStack.size() * 200), 52));

        removeAll();
        this.graphView.refresh();//todo check needed?

        setLayout(new GridLayout(1, this.crumbsStack.size()));

        for (JPanel panel : this.crumbsStack) add(panel);

        this.viewPanel.repaint();
        this.viewPanel.revalidate();
        repaint();
        revalidate();
    }

    public synchronized void clear() {
        navigateTo(-1);
    }

    public synchronized void startProgressBar() {
        if (!this.visibleProgressBar) {
            this.visibleProgressBar = true;

            viewPanel.add(this.progressBar, BorderLayout.SOUTH);
            panesStacks.get(panesStacks.size() - 1).repaint();
            panesStacks.get(panesStacks.size() - 1).revalidate();

            this.viewPanel.repaint();
            this.viewPanel.revalidate();
            repaint();
            revalidate();
        }
    }

    public synchronized void stopProgressBar() {
        if (this.visibleProgressBar) {
            this.visibleProgressBar = false;

            viewPanel.remove(this.progressBar);
            panesStacks.get(panesStacks.size() - 1).repaint();
            panesStacks.get(panesStacks.size() - 1).revalidate();

            this.viewPanel.repaint();
            this.viewPanel.revalidate();
            repaint();
            revalidate();
        }
    }

    public synchronized void updateBackgroundTask(SwingWorker backgroundTask) {
        cancelBackgroundTask();
        this.backgroundTask = backgroundTask;
    }

    public synchronized void cancelBackgroundTask() {
        if (this.backgroundTask != null) this.backgroundTask.cancel(true);
    }

    public GraphView getGraphView() {
        return graphView;
    }

    public void showErrorMessage(String title, String details) {
        hideErrorMessage();

        this.errorPanel = new JPanel(new BorderLayout());
        errorPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        errorPanel.setBackground(GuiColors.RED_ERROR);
        errorPanel.setPreferredSize(new Dimension(this.viewPanel.getWidth(), 300));

        MenuLabel closeLabel = new MenuLabel("Close", MenuLabel.CENTER, 14);
        closeLabel.setForeground(GuiColors.BASE_WHITE);
        closeLabel.dropAllListeners();
        closeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                hideErrorMessage();
            }
        });

        JPanel detailsWrapper = new JPanel(new GridLayout(1, 1));
        detailsWrapper.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        detailsWrapper.setBackground(GuiColors.RED_ERROR);

        TextView detailsLabel = new TextView(details, 10, GuiColors.RED_ERROR);
        detailsLabel.setForeground(GuiColors.BASE_WHITE);
        detailsWrapper.add(detailsLabel);

        JScrollPane scrollDetails = new JScrollPane(detailsWrapper, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollDetails.setBackground(GuiColors.RED_ERROR);
        scrollDetails.setBorder(BorderFactory.createEmptyBorder());

        errorPanel.add(new TitleLabel(title, TitleLabel.CENTER, 12, GuiColors.BASE_WHITE), BorderLayout.NORTH);
        errorPanel.add(scrollDetails, BorderLayout.CENTER);
        errorPanel.add(closeLabel, BorderLayout.SOUTH);


        this.visibleErrorPanel = true;

        viewPanel.add(this.errorPanel, BorderLayout.SOUTH);

        repaint();
        revalidate();
    }

    public void hideErrorMessage() {
        if (this.visibleErrorPanel && this.errorPanel != null) {
            this.viewPanel.remove(this.errorPanel);
            this.visibleErrorPanel = false;
            this.errorPanel = null;
            this.viewPanel.repaint();
            this.viewPanel.revalidate();
            repaint();
            revalidate();
        }
    }

}

