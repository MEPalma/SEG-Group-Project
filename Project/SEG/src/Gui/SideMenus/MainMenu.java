package Gui.SideMenus;

import Gui.GuiColors;
import Gui.GuiComponents.ListView;
import Gui.GuiComponents.MenuLabel;
import Gui.GuiComponents.RPanel;
import Gui.GuiComponents.TitleLabel;
import Gui.MainController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

public class MainMenu extends RPanel {
    private final MainController mainController;

    public MainMenu(MainController mainController) {
        super(GuiColors.BASE_WHITE, new BorderLayout());
        this.mainController = mainController;
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
                mainController.pushNewViewOnBreadCrumbs("Choose metrics", new MetricsMenu(mainController));
                this.mouseExited(e);
            }
        });
        menus.add(getMenuCard(chooseMetricsLabel));

        //FILTERS
        MenuLabel filtersLabel = new MenuLabel("Filters", MenuLabel.LEFT, 14);
        filtersLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainController.pushNewViewOnBreadCrumbs("Filters", new FiltersMenu(mainController));
                this.mouseExited(e);
            }
        });
        menus.add(getMenuCard(filtersLabel));

        //Load csvs
        MenuLabel loadCSVsLabel = new MenuLabel("Load CSVs", MenuLabel.LEFT, 14);
        loadCSVsLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainController.pushNewViewOnBreadCrumbs("Load CSVs", new LoadCSVsMenu(mainController));
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

        add(new ListView(GuiColors.BASE_WHITE, menus).getWrappedInScroll(true), BorderLayout.CENTER);

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
