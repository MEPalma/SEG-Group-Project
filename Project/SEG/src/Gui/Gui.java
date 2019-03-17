package Gui;

import Gui.GuiComponents.MenuLabel;
import Gui.GuiComponents.RecursiveLostFocus;
import Gui.GuiComponents.TitleLabel;
import Gui.Menus.ChooseNewGraphPanel;
import Gui.Menus.FiltersMenu;
import Gui.Menus.SideMenu;
import Gui.TabbedView.TabbedView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Gui extends JFrame {

    private JPanel mainView;
    private JPanel northView;

    private MainController mainController;

    private JPanel popupMessageArea;
    private JPanel currentPopup;

    public Gui() {
        super("Dashboard App");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Icons/logo.png")));
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                mainController.close();
                System.exit(0);
            }
        });

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }


        /*
            POPUPS
         */
        this.popupMessageArea = new JPanel(new BorderLayout());
        this.popupMessageArea.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        this.popupMessageArea.setBackground(GuiColors.BASE_SMOKE);


        /*
            TABBED VIEW VIEWS INITIALIZATION
         */
        JPanel tabbedViewTabsHoster = new JPanel(new BorderLayout());
        tabbedViewTabsHoster.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        tabbedViewTabsHoster.setBackground(GuiColors.BASE_SMOKE);

        JPanel tabbedViewContentHoster = new JPanel(new BorderLayout());
        tabbedViewContentHoster.setBorder(BorderFactory.createEmptyBorder());
        tabbedViewTabsHoster.setBackground(GuiColors.BASE_SMOKE);

        /*
            STATUS DISPLAY VIEW INITIALIZATION
         */

        StatusDisplay statusDisplay = new StatusDisplay();

        /*
            INIT MAIN CONTROLLER
         */
        this.mainController = new MainController(statusDisplay, new TabbedView(tabbedViewTabsHoster, tabbedViewContentHoster));


        /*
            ORGANIZE LAYOUT
         */
        getContentPane().setLayout(new BorderLayout());

        /*
                NORTH_VIEW
         */
        this.northView = new JPanel(new BorderLayout());
        this.northView.setBackground(GuiColors.BASE_PRIME);
        this.northView.setLayout(new BorderLayout());
        this.northView.setBorder(BorderFactory.createEmptyBorder());
        this.northView.setPreferredSize(new Dimension(300, 56));


        TitleLabel appTitleLabel = new TitleLabel(" Dashboard App", JLabel.LEFT, 26);
        appTitleLabel.setForeground(GuiColors.BASE_WHITE);
        this.northView.add(appTitleLabel, BorderLayout.CENTER);
        getContentPane().add(this.northView, BorderLayout.NORTH);


        /*
                MAIN_VIEW
         */
        this.mainView = new JPanel(new BorderLayout());
        this.mainView.setBackground(GuiColors.BASE_SMOKE);
        this.mainView.add(new SideMenu(mainController), BorderLayout.WEST);
        this.mainView.add(statusDisplay, BorderLayout.NORTH);
        this.mainView.add(this.popupMessageArea, BorderLayout.EAST);

        JPanel tabbedViewTopWrapper = new JPanel(new BorderLayout());
        tabbedViewTopWrapper.setBorder(BorderFactory.createEmptyBorder());
        tabbedViewTopWrapper.setBackground(GuiColors.BASE_SMOKE);
        tabbedViewTopWrapper.add(tabbedViewTabsHoster, BorderLayout.CENTER);
        tabbedViewTopWrapper.add(getTopRightFunctions(), BorderLayout.EAST);

        JPanel tabbedViewWrapper = new JPanel(new BorderLayout());
        tabbedViewWrapper.setBorder(BorderFactory.createEmptyBorder());
        tabbedViewWrapper.setBackground(GuiColors.BASE_SMOKE);
        tabbedViewWrapper.add(tabbedViewTopWrapper, BorderLayout.NORTH);
        tabbedViewWrapper.add(tabbedViewContentHoster, BorderLayout.CENTER);
        this.mainView.add(tabbedViewWrapper, BorderLayout.CENTER);

        getContentPane().add(this.mainView, BorderLayout.CENTER);

        repaint();
        revalidate();
    }

    private JPanel getTopRightFunctions() {
        JPanel tabbedViewTopRightFunctions = new JPanel(new GridLayout(1, 2));
        tabbedViewTopRightFunctions.setBorder(BorderFactory.createEmptyBorder());
        tabbedViewTopRightFunctions.setBackground(GuiColors.BASE_WHITE);
        tabbedViewTopRightFunctions.add(getShowFiltersMenuLabel());
        tabbedViewTopRightFunctions.add(getAddGraphMenuLabel());

        return tabbedViewTopRightFunctions;
    }

    private JPanel getShowFiltersMenuLabel() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(GuiColors.BASE_WHITE);
        wrapper.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, GuiColors.BASE_SMOKE));
        wrapper.setPreferredSize(new Dimension(120, 60));

        MenuLabel menuLabel = new MenuLabel("Filters", MenuLabel.CENTER, 16);
        menuLabel.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, GuiColors.BASE_WHITE));
        menuLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
//                JDialog dialog = new JDialog();
//                dialog.setUndecorated(true);
//                dialog.getContentPane().setLayout(new BorderLayout());
//
//                dialog.addWindowFocusListener(new RecursiveLostFocus(dialog));
//                dialog.getContentPane().add(new TitleLabel("Filters", TitleLabel.CENTER, 18), BorderLayout.NORTH);
//
//                int dfWidth = 450;
//                int dfHeight = 600;
//                dialog.setSize(new Dimension(dfWidth, dfHeight));
//
//                int centerXtmp = menuLabel.getLocationOnScreen().x + 10 - dfWidth;
//                int centerYtmp = menuLabel.getLocationOnScreen().y + 10;
//                dialog.setLocation(centerXtmp, centerYtmp);
//
//                dialog.getContentPane().add(new FiltersMenu(mainController), BorderLayout.CENTER);
//                dialog.setVisible(true);
                if (currentPopup != null) {
                    if (currentPopup instanceof FiltersMenu) {
                        currentPopup = null;
                        popupMessageArea.removeAll();
                        popupMessageArea.repaint();
                        popupMessageArea.revalidate();
                        return;
                    }
                }

                popupMessageArea.removeAll();
                currentPopup = new FiltersMenu(mainController);
                currentPopup.setPreferredSize(new Dimension(400, 40));
                popupMessageArea.add(currentPopup);
                popupMessageArea.repaint();
                popupMessageArea.revalidate();
            }
        });


        wrapper.add(menuLabel, BorderLayout.CENTER);

        return wrapper;
    }

    private JPanel getAddGraphMenuLabel() {

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(GuiColors.BASE_WHITE);
        wrapper.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, GuiColors.BASE_SMOKE));
        wrapper.setPreferredSize(new Dimension(120, 60));

        MenuLabel menuLabel = new MenuLabel("Add", MenuLabel.CENTER, 16);
        menuLabel.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, GuiColors.BASE_WHITE));
        menuLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
//                JDialog dialog = new JDialog();
//                dialog.setUndecorated(true);
//                dialog.getContentPane().setLayout(new BorderLayout());
//                dialog.addWindowFocusListener(new RecursiveLostFocus(dialog));
//                dialog.getContentPane().add(new TitleLabel("Add new graph", TitleLabel.CENTER, 18), BorderLayout.NORTH);
//
//                /*
//                    setup dialog view
//                 */
//                JPanel dialogView = new JPanel(new BorderLayout());
//                dialogView.setBackground(GuiColors.BASE_SMOKE);
//                dialogView.setBorder(BorderFactory.createLineBorder(GuiColors.BASE_SMOKE, 10, true));
//                dialogView.add(new ChooseNewGraphPanel(mainController, dialog), BorderLayout.CENTER);
//
//
//                /*
//                    display dialog
//                 */
//                int dfWidth = 300;
//                int dfHeight = 320;
//                dialog.setSize(new Dimension(dfWidth, dfHeight));
//
//                int centerXtmp = menuLabel.getLocationOnScreen().x + 90 - dfWidth;
//                int centerYtmp = menuLabel.getLocationOnScreen().y + 10;
//                dialog.setLocation(centerXtmp, centerYtmp);
//
//                dialog.getContentPane().add(dialogView, BorderLayout.CENTER);
//                dialog.setVisible(true);
                if (currentPopup != null) {
                    if (currentPopup instanceof ChooseNewGraphPanel) {
                        currentPopup = null;
                        popupMessageArea.removeAll();
                        popupMessageArea.repaint();
                        popupMessageArea.revalidate();
                        return;
                    }
                }

                popupMessageArea.removeAll();
                currentPopup = new ChooseNewGraphPanel(mainController);
                currentPopup.setPreferredSize(new Dimension(300, 40));
                popupMessageArea.add(currentPopup);
                popupMessageArea.repaint();
                popupMessageArea.revalidate();
            }
        });


        wrapper.add(menuLabel, BorderLayout.CENTER);
        return wrapper;
    }

    @Override
    public void setVisible(boolean visible) {
        if (visible) {
            setSize(new Dimension(1200, 800));

            Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (screen.width / 2) - (super.getSize().width / 2);
            int y = (screen.height / 2) - (super.getSize().height / 2);
            super.setLocation(x, y);
        }
        super.setVisible(visible);
    }

}
