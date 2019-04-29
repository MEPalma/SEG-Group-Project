package Gui.TabbedView;


import Gui.GuiColors;
import Gui.GuiComponents.HListView;
import Gui.GuiComponents.MenuLabel;
import Gui.GuiComponents.RPanel;
import Gui.GuiComponents.TitleLabel;
import Gui.HomeView.HomeView;
import Gui.MainController;
import Gui.TakeActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

public class TabbedView {

    private final JPanel tabsHost;
    private final JPanel contentHost;

    private JPanel homeView;

    private final LinkedList<Tab> tabs;

    private JPanel openTab;

    private int selectedIndex;

    private MainController mainController;

    public TabbedView(JPanel tabsHost, JPanel contentHost) {
        this.tabsHost = tabsHost;
        this.contentHost = contentHost;
        this.tabs = new LinkedList<>();
    }

    public void init(MainController mainController) {
        this.mainController = mainController;

        this.tabsHost.setBorder(BorderFactory.createMatteBorder(4, 4, 0, 4, mainController.getGuiColors().getGuiBackgroundColor()));

        this.homeView = null;

        this.selectedIndex = 0;

        this.tabsHost.setLayout(new BorderLayout());
        this.contentHost.setLayout(new BorderLayout());
        this.contentHost.setBorder(BorderFactory.createMatteBorder(0, 2, 2, 2, mainController.getGuiColors().getGuiBackgroundColor()));
    }

    public synchronized void refresh() {
        this.tabsHost.setBorder(BorderFactory.createMatteBorder(4, 4, 0, 4, mainController.getGuiColors().getGuiBackgroundColor()));
        this.contentHost.setBorder(BorderFactory.createMatteBorder(0, 2, 2, 2, mainController.getGuiColors().getGuiBackgroundColor()));

        this.tabsHost.removeAll();
        this.contentHost.removeAll();

        if(this.homeView != null) {
            this.tabsHost.add(createTab("Home", GuiColors.RED_ERROR, 0, null, false), BorderLayout.WEST);
        }

        if (this.selectedIndex > this.tabs.size() - 1)
            this.selectedIndex = this.tabs.size() - 1;

        List<Component> tabCells = new LinkedList<>();
        for (int i = ((this.homeView == null) ? 0 : 1); i < this.tabs.size(); ++i) {
            Tab t = this.tabs.get(i);
            tabCells.add(createTab(t.getTitle(), mainController.getGuiColors().getGuiPrimeColor(), i, t.getUpdateOnSelection(), true));
        }

        this.tabsHost.add(new HListView(mainController.getGuiColors(), tabCells).getWrappedInScroll(), BorderLayout.CENTER);
        if (this.selectedIndex >= 0)
            this.contentHost.add(this.tabs.get(this.selectedIndex).getContent(), BorderLayout.CENTER);

        this.tabsHost.repaint();
        this.tabsHost.revalidate();
        this.contentHost.repaint();
        this.contentHost.revalidate();
    }

    public void push(String title, JPanel content, Object comparable, TakeActionListener updateOnSelection) {
        synchronized (this) {
            this.tabs.add(new Tab(title, content, comparable, updateOnSelection));
            this.selectedIndex = this.tabs.size() - 1;
        }
        refresh();
    }

    public void pushNewHomeTab(String title, RPanel content) {
        Tab homeTab = new Tab(title, content, content, null);
        this.homeView = content;
        this.tabs.addFirst(homeTab);

        refresh();
    }

    public void clear() {
        synchronized (this) {
            this.tabs.clear();
            if (this.homeView != null) {
                this.tabs.addFirst(new Tab("Home", this.homeView, this.homeView, null));
            }
        }
        refresh();
    }

    public synchronized boolean containsComparable(Object comparable) {
        for (Tab tab : this.tabs) {
            if (tab.getComparable().equals(comparable)) return true;
        }
        return false;
    }

    public JPanel getHomeView() {
        return this.homeView;
    }

    public List<Object> getAllComparables() {
        List<Object> dump = new LinkedList<>();

        synchronized (this) {
            for (Tab t : this.tabs) {
                dump.add(t.getComparable());
            }
        }

        return dump;
    }

    public List<JPanel> getAllContents() {
        List<JPanel> dump = new LinkedList<>();

        synchronized (this) {
            for (Tab t : this.tabs)
                dump.add(t.getContent());
        }

        return dump;
    }

    private JPanel createTab(String title, Color color, int myIndex, TakeActionListener updateOnSelection, boolean closable) {
        JPanel tab = new JPanel(new BorderLayout());
        tab.setBackground(color);
        tab.setBorder(BorderFactory.createMatteBorder(0, 2, 4, 0, mainController.getGuiColors().getGuiTextColor()));
        if (myIndex == selectedIndex) {
            tab.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 0, mainController.getGuiColors().getGuiTextColor()));
            openTab = tab;
        }

        tab.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                selectedIndex = myIndex;
                contentHost.removeAll();
                contentHost.add(tabs.get(selectedIndex).getContent(), BorderLayout.CENTER);
                contentHost.repaint();
                contentHost.revalidate();

                if (openTab != null) {
                    openTab.setBorder(BorderFactory.createMatteBorder(0, 2, 4, 0, mainController.getGuiColors().getGuiTextColor()));
                    openTab.repaint();
                    openTab.revalidate();
                }
                openTab = tab;

                tab.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 0, mainController.getGuiColors().getGuiTextColor()));
                if (updateOnSelection != null) updateOnSelection.takeAction();
            }
        });
        String htmlTitle;
        int length;

        TitleLabel topLabel;
        TitleLabel bottomLabel;

        String[] titleHalves = title.split(":");
        if (titleHalves.length == 2) {
            topLabel = new TitleLabel("<html>" + titleHalves[0] + "</html>", TitleLabel.LEFT, 14, mainController.getGuiColors());
            bottomLabel = new TitleLabel("<html> <b>" + titleHalves[1] + "</b> </html>", TitleLabel.LEFT, 14, mainController.getGuiColors());
        } else {
            topLabel = new TitleLabel("<html><b>" + title + "</b></html>", TitleLabel.CENTER, 14, mainController.getGuiColors());
            bottomLabel = new TitleLabel("<html>" + "</html>", TitleLabel.CENTER, 14, mainController.getGuiColors());

        }

        int longest = topLabel.getFontMetrics(topLabel.getFont()).stringWidth(topLabel.getText());
        int other = bottomLabel.getFontMetrics(bottomLabel.getFont()).stringWidth(bottomLabel.getText());
        if (other > longest)
            longest = other;

        //TitleLabel titleLabel = new TitleLabel();
        //titleLabel.setForeground(mainController.getGuiColors().getGuiTextColor());
        //tab.add(titleLabel, BorderLayout.CENTER);

        topLabel.setForeground(mainController.getGuiColors().getGuiTextColor());
        tab.add(topLabel, BorderLayout.CENTER);

        bottomLabel.setForeground(mainController.getGuiColors().getGuiTextColor());
        tab.add(bottomLabel, BorderLayout.SOUTH);

        tab.setPreferredSize(new Dimension((closable ? longest -120 : 80), 50));


        if (closable) {
            MenuLabel popLabel = new MenuLabel("x", MenuLabel.CENTER, 16, mainController.getGuiColors());
            popLabel.setForeground(mainController.getGuiColors().getGuiTextColor());
            popLabel.setPreferredSize(new Dimension(20, 20));
            popLabel.dropAllListeners();
            popLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    tabs.remove(myIndex);
                    refresh();
                    updateOnSelection.takeAction();
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    popLabel.setForeground(mainController.getGuiColors().getGuiBackgroundColor());
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    popLabel.setForeground(mainController.getGuiColors().getGuiTextColor());
                }
            });
            tab.add(popLabel, BorderLayout.EAST);
        }

        return tab;
    }

    public Object getSelectedComparable() {
        if (this.tabs.size() == 0) return null;
        else if (this.homeView != null && this.selectedIndex == 0) return null;
        return this.tabs.get(this.selectedIndex).getComparable();
    }

    public void replaceOnComparable(String title, JPanel content, Object comparable) {
        for (Tab t : this.tabs) {
            if (t.getComparable() == (comparable)) {
                t.setTitle(title);
                t.setContent(content);
                t.setComparable(comparable);
            }
        }

        refresh();
    }

}

class Tab {
    private String title;
    private JPanel content;
    private Object comparable;
    private TakeActionListener updateOnSelection;

    public Tab(String title, JPanel content, Object comparable, TakeActionListener updateOnSelecetion) {
        this.title = title;
        this.content = content;
        this.comparable = comparable;
        this.updateOnSelection = updateOnSelecetion;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Tab) {
            return this.comparable.equals((Tab) obj);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.comparable.hashCode();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public JPanel getContent() {
        return content;
    }

    public TakeActionListener getUpdateOnSelection() {
        return updateOnSelection;
    }

    public void setContent(JPanel content) {
        this.content = content;
    }

    public Object getComparable() {
        return comparable;
    }

    public void setComparable(Object comparable) {
        this.comparable = comparable;
    }

    public void setUpdateOnSelection(TakeActionListener updateOnSelection) {
        this.updateOnSelection = updateOnSelection;
    }
}
