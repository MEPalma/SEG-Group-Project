package Gui;

import DatabaseManager.DataExchange;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

public class MainMenu extends RPanel {
    private final DataExchange dataExchange;//todo change with controller
    private final BreadCrumbs breadCrumbs;

    public MainMenu(DataExchange dataExchange, BreadCrumbs breadCrumbs) {
        super(GuiColors.BASE_LIGHT, new BorderLayout());
        this.dataExchange = dataExchange;
        this.breadCrumbs = breadCrumbs;
        refresh();
    }

    @Override
    public void refresh() {

        List<Component> menus = new LinkedList<>();

        //ChooseMetrics menu
        MenuLabel chooseMetricsLabel = new MenuLabel("Choose metrics", MenuLabel.LEFT, 14);
        chooseMetricsLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                breadCrumbs.push("Choose metrics", new MetricsMenu(dataExchange, breadCrumbs));
                this.mouseExited(e);
            }
        });
        menus.add(getMenuCard(chooseMetricsLabel));

        //FILTERS
        MenuLabel filtersLabel = new MenuLabel("Filters", MenuLabel.LEFT, 14);
        filtersLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                breadCrumbs.push("Filters", new FiltersMenu(dataExchange, breadCrumbs));
                this.mouseExited(e);
            }
        });
        menus.add(getMenuCard(filtersLabel));

        //Load csvs
        MenuLabel loadCSVsLabel = new MenuLabel("Load CSVs", MenuLabel.LEFT, 14);
        loadCSVsLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                breadCrumbs.push("Load CSVs", new LoadCSVsView(dataExchange, breadCrumbs));
                this.mouseExited(e);
            }
        });
        menus.add(getMenuCard(loadCSVsLabel));

        //settings
        MenuLabel settingsLabel = new MenuLabel("Settings", MenuLabel.LEFT, 14);
        settingsLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //TODO
                this.mouseExited(e);
            }
        });
        menus.add(getMenuCard(settingsLabel));

        add(new ListView(getBackground(), menus).getWrappedInScroll(true), BorderLayout.CENTER);

        repaint();
        revalidate();
    }

    private JPanel getMenuCard(Component leftComponent) {
        JPanel tmp = new JPanel(new BorderLayout());
        tmp.setPreferredSize(new Dimension(100, 50));
        tmp.add(leftComponent, BorderLayout.WEST);
        tmp.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        tmp.setBackground(getBackground());
        tmp.add(new TitleLabel(">", TitleLabel.LEFT, 14), BorderLayout.EAST);
        return tmp;
    }
}
