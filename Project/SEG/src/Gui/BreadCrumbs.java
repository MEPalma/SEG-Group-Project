package Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Stack;

public class BreadCrumbs extends JPanel {
    private Color BACKGROUND = GuiColors.DARK_LIGHT;
    private Color SELECTED = GuiColors.LIGHT;

    private JPanel viewPanel;
    private GraphView graphView;
    private BreadCrumbsHoster breadCrumbsHoster;

    private JProgressBar progressBar;
    private boolean visibleProgressBar;

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
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, GuiColors.LIGHT_GRAY));
        setPreferredSize(new Dimension(300, 52));

        this.panesStacks = new Stack<RPanel>();
        this.crumbsStack = new Stack<JPanel>();
    }

    private JPanel getNewCrumb(String text) {
        JPanel crumb = new JPanel(new BorderLayout());
        crumb.setBackground(SELECTED);
        crumb.setBorder(BorderFactory.createEmptyBorder());
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
        this.breadCrumbsHoster.refresh();
    }

    public synchronized void push(String name, RPanel view) {
        cancelBackgroundTask();

        this.panesStacks.push(view);
        this.crumbsStack.push(getNewCrumb(name));

        navigateTo(panesStacks.size() - 1);

        //TODO check
        setPreferredSize(new Dimension(getWidth() + 300, 10));
    }

    public synchronized void pop() {
        this.crumbsStack.pop();
        this.panesStacks.pop();

        refresh();

        navigateTo(panesStacks.size() - 1);
    }

    private void refresh() {
        removeAll();

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
}

