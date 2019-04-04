package Gui.Menus;

import Gui.GuiColors;
import Gui.GuiComponents.ListView;
import Gui.GuiComponents.MenuLabel;
import Gui.GuiComponents.RPanel;
import Gui.MainController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

public class SideMenu extends RPanel {
    private final MainController mainController;
    private JPanel openMenu;

    public SideMenu(MainController mainController) {
        super(GuiColors.BASE_PRIME, new BorderLayout());
        this.mainController = mainController;

        refresh();
    }

    public void refresh() {
        removeAll();

        List<Component> menus = new LinkedList<>();

        //Load csvs
        MenuLabel loadCSVsLabel = new MenuLabel("Load", MenuLabel.CENTER, 0);
        loadCSVsLabel.setToolTipText("Load from CSVs for a new campaign");
        loadCSVsLabel.setIcon(new ImageIcon(getClass().getResource("/Icons/upload.png")));
        loadCSVsLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                removeAll();
                if (openMenu != null) {
                    if (openMenu instanceof LoadCSVsMenu)
                        openMenu = null;
                    refresh();
                    return;
                } else {
                    openMenu = new LoadCSVsMenu(mainController);
                    refresh();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                loadCSVsLabel.setIcon(new ImageIcon(getClass().getResource("/Icons/upload_hover.png")));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loadCSVsLabel.setIcon(new ImageIcon(getClass().getResource("/Icons/upload.png")));
            }
        });
        menus.add(getMenuCard(loadCSVsLabel));

        //settings
        MenuLabel settingsLabel = new MenuLabel("Settings", MenuLabel.CENTER, 0);
        settingsLabel.setToolTipText("Settings");
        settingsLabel.setIcon(new ImageIcon(getClass().getResource("/Icons/settings.png")));
        settingsLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //TODO NEXT INCREMENT
                JOptionPane.showMessageDialog(null, "Features not supported yet", "Unsupported features", JOptionPane.WARNING_MESSAGE);
                this.mouseExited(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                settingsLabel.setIcon(new ImageIcon(getClass().getResource("/Icons/settings_hover.png")));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                settingsLabel.setIcon(new ImageIcon(getClass().getResource("/Icons/settings.png")));
            }
        });
        menus.add(getMenuCard(settingsLabel));

        JPanel menuList = new ListView(getBackground(), menus);
        if (this.openMenu != null) {
            this.openMenu.setPreferredSize(new Dimension(400, 250));
            add(menuList, BorderLayout.WEST);
            add(this.openMenu, BorderLayout.CENTER);
        } else {
            add(menuList, BorderLayout.CENTER);
        }

        repaint();
        revalidate();
    }

    private JPanel getMenuCard(Component leftComponent) {
        JPanel tmp = new JPanel(new GridLayout(1, 1));
//        tmp.setPreferredSize(new Dimension(100, 50));
        tmp.add(leftComponent, BorderLayout.WEST);
        tmp.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        tmp.setBackground(getBackground());
        return tmp;
    }
}
