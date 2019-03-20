package Gui.TabbedView;


import Gui.GuiColors;
import Gui.GuiComponents.HListView;
import Gui.GuiComponents.MenuLabel;
import Gui.GuiComponents.TitleLabel;
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

    private final List<Tab> tabs;

    private JPanel openTab;

    private int selectedIndex;

    public TabbedView(JPanel tabsHost, JPanel contentHost) {
        this.tabsHost = tabsHost;
        this.contentHost = contentHost;
        this.tabs = new LinkedList<>();
        this.selectedIndex = 0;

        this.tabsHost.setLayout(new BorderLayout());
        this.contentHost.setLayout(new BorderLayout());
    }

    public synchronized void refresh() {
        this.tabsHost.removeAll();
        this.contentHost.removeAll();

        if (this.selectedIndex > this.tabs.size() - 1)
            this.selectedIndex = this.tabs.size() - 1;

        List<Component> tabCells = new LinkedList<>();
        for (int i = 0; i < this.tabs.size(); ++i) {
            Tab t = this.tabs.get(i);
            tabCells.add(createTab(t.getTitle(), t.getColor(), i, t.getUpdateOnSelection()));
        }

        this.tabsHost.add(new HListView(GuiColors.BASE_WHITE, tabCells).getWrappedInScroll(), BorderLayout.CENTER);
        if (this.selectedIndex >= 0)
            this.contentHost.add(this.tabs.get(this.selectedIndex).getContent(), BorderLayout.CENTER);

        this.tabsHost.repaint();
        this.tabsHost.revalidate();
        this.contentHost.repaint();
        this.contentHost.revalidate();
    }

    public void push(String title, Color tabColor, JPanel content, Object comparable, TakeActionListener updateOnSelection) {
        synchronized (this) {
            this.tabs.add(new Tab(title, tabColor, content, comparable, updateOnSelection));
            this.selectedIndex = this.tabs.size() - 1;
        }
        refresh();
    }

    public void clear() {
        synchronized (this) {
            this.tabs.clear();
        }
        refresh();
    }

    public synchronized boolean containsComparable(Object comparable) {
        for (Tab tab : this.tabs) {
            if (tab.getComparable().equals(comparable)) return true;
        }
        return false;
    }

    public synchronized List<Object> getAllComparables() {
        List<Object> dump = new LinkedList<>();

        synchronized (this) {
            for (Tab t : this.tabs) {
                dump.add(t.getComparable());
            }
        }

        return dump;
    }

    private JPanel createTab(String title, Color color, int myIndex, TakeActionListener updateOnSelection) {
        JPanel tab = new JPanel(new BorderLayout());
        tab.setBackground(color);
        tab.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, GuiColors.BASE_WHITE));
        if (myIndex == selectedIndex) {
            tab.setBorder(BorderFactory.createMatteBorder(6, 6, 6, 6, color.darker()));
            openTab = tab;
        }
        tab.setPreferredSize(new Dimension(120, 50));
        tab.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                selectedIndex = myIndex;
                contentHost.removeAll();
                contentHost.add(tabs.get(selectedIndex).getContent(), BorderLayout.CENTER);
                contentHost.repaint();
                contentHost.revalidate();

                if (openTab != null) {
                    openTab.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, GuiColors.BASE_WHITE));
                    openTab.repaint();
                    openTab.revalidate();
                }
                openTab = tab;

                tab.setBorder(BorderFactory.createMatteBorder(6, 6, 6, 6, color.darker()));
                if (updateOnSelection != null) updateOnSelection.takeAction();
            }
        });

        TitleLabel titleLabel = new TitleLabel("<html>" + title + "</html>", TitleLabel.CENTER, 14);
        titleLabel.setForeground(GuiColors.BASE_WHITE);

        tab.add(titleLabel, BorderLayout.CENTER);

        MenuLabel popLabel = new MenuLabel("x", MenuLabel.CENTER, 16);
        popLabel.setForeground(GuiColors.BASE_WHITE);
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
                popLabel.setForeground(GuiColors.BASE_SMOKE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                popLabel.setForeground(GuiColors.BASE_WHITE);
            }
        });
        tab.add(popLabel, BorderLayout.EAST);

        return tab;
    }

    public Object getSelectedComparable() {
        if (this.tabs.size() == 0) return null;
        return this.tabs.get(this.selectedIndex).getComparable();
    }

    public void replaceOnComparable(String title, Color tabColor, JPanel content, Object comparable) {
        for (Tab t : this.tabs) {
            if (t.getComparable().equals(comparable)) {
                t.setTitle(title);
                t.setColor(tabColor);
                t.setContent(content);
                t.setComparable(comparable);
            }
        }

        refresh();
    }

}

class Tab {
    private String title;
    private Color color;
    private JPanel content;
    private Object comparable;
    private TakeActionListener updateOnSelection;

    public Tab(String title, Color color, JPanel content, Object comparable, TakeActionListener updateOnSelecetion) {
        this.title = title;
        this.color = color;
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
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
