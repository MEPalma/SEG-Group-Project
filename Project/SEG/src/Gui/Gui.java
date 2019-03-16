package Gui;

import Gui.GuiComponents.MenuLabel;
import Gui.GuiComponents.RecursiveLostFocus;
import Gui.GuiComponents.TitleLabel;
import Gui.Menus.ChooseNewGraphPanel;
import Gui.Menus.FiltersMenu;
import Gui.Menus.MainMenu;
import Gui.TabbedView.TabbedView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Gui extends JFrame {

    private JPanel mainView;
    private JPanel northView;

    private MainController mainController;

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
            TABBED VIEW INITIALIZATION
         */
        JPanel tabbedViewTabsHoster = new JPanel(new BorderLayout());
        tabbedViewTabsHoster.setBorder(BorderFactory.createEmptyBorder());
        tabbedViewTabsHoster.setBackground(GuiColors.BASE_WHITE);

        JPanel tabbedViewContentHoster = new JPanel(new BorderLayout());
        tabbedViewContentHoster.setBorder(BorderFactory.createEmptyBorder());
        tabbedViewTabsHoster.setBackground(GuiColors.BASE_WHITE);


        /*
            INIT MAIN CONTROLLER
         */
        this.mainController = new MainController(new TabbedView(tabbedViewTabsHoster, tabbedViewContentHoster));


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
        this.northView.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, GuiColors.BASE_SMOKE));
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
        this.mainView.add(this.mainController.getBreadCrumbsHoster(), BorderLayout.WEST);

        JPanel tabbedViewTopWrapper = new JPanel(new BorderLayout());
        tabbedViewTopWrapper.setBorder(BorderFactory.createEmptyBorder());
        tabbedViewTopWrapper.setBackground(GuiColors.BASE_WHITE);
        tabbedViewTopWrapper.add(tabbedViewTabsHoster, BorderLayout.CENTER);
        tabbedViewTopWrapper.add(getTopRightFunctions(), BorderLayout.EAST);

        JPanel tabbedViewWrapper = new JPanel(new BorderLayout());
        tabbedViewWrapper.setBorder(BorderFactory.createEmptyBorder());
        tabbedViewWrapper.setBackground(GuiColors.BASE_WHITE);
        tabbedViewWrapper.add(tabbedViewTopWrapper, BorderLayout.NORTH);
        tabbedViewWrapper.add(tabbedViewContentHoster, BorderLayout.CENTER);
        this.mainView.add(tabbedViewWrapper, BorderLayout.CENTER);

        getContentPane().add(this.mainView, BorderLayout.CENTER);

        this.mainController.pushNewViewOnBreadCrumbs(mainController.getCampaignName(), new MainMenu(this.mainController));

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
        wrapper.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, GuiColors.BASE_SMOKE));
        wrapper.setPreferredSize(new Dimension(120, 60));

        MenuLabel menuLabel = new MenuLabel("Filters", MenuLabel.CENTER, 16);
        menuLabel.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, GuiColors.BASE_WHITE));
        menuLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JDialog dialog = new JDialog();
                dialog.setUndecorated(true);
                dialog.getContentPane().setLayout(new BorderLayout());

                dialog.addWindowFocusListener(new RecursiveLostFocus(dialog));
                dialog.getContentPane().add(new TitleLabel("Filters", TitleLabel.CENTER, 18), BorderLayout.NORTH);

                int dfWidth = 450;
                int dfHeight = 600;
                dialog.setSize(new Dimension(dfWidth, dfHeight));

                int centerXtmp = menuLabel.getLocationOnScreen().x + 10 -dfWidth;
                int centerYtmp = menuLabel.getLocationOnScreen().y + 10 + dfHeight;
                dialog.setLocation(centerXtmp, centerYtmp);

                dialog.getContentPane().add(new FiltersMenu(mainController), BorderLayout.CENTER);
                dialog.setVisible(true);
            }
        });


        wrapper.add(menuLabel, BorderLayout.CENTER);

        return wrapper;
    }

    private JPanel getAddGraphMenuLabel() {

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(GuiColors.BASE_WHITE);
        wrapper.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, GuiColors.BASE_SMOKE));
        wrapper.setPreferredSize(new Dimension(120, 60));

        MenuLabel menuLabel = new MenuLabel("Add", MenuLabel.CENTER, 16);
        menuLabel.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, GuiColors.BASE_WHITE));
        menuLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JDialog dialog = new JDialog();
                dialog.setUndecorated(true);
                dialog.getContentPane().setLayout(new BorderLayout());
                dialog.addWindowFocusListener(new RecursiveLostFocus(dialog));
                dialog.getContentPane().add(new TitleLabel("Add new graph", TitleLabel.CENTER, 18), BorderLayout.NORTH);

                /*
                    setup dialog view
                 */
                JPanel dialogView = new JPanel(new BorderLayout());
                dialogView.setBackground(GuiColors.BASE_SMOKE);
                dialogView.setBorder(BorderFactory.createLineBorder(GuiColors.BASE_SMOKE, 10, true));
                dialogView.add(new ChooseNewGraphPanel(mainController, dialog), BorderLayout.CENTER);


                /*
                    display dialog
                 */
                int dfWidth = 300;
                int dfHeight = 320;
                dialog.setSize(new Dimension(dfWidth, dfHeight));

                int centerXtmp = menuLabel.getLocationOnScreen().x + 90 - dfWidth;
                int centerYtmp = menuLabel.getLocationOnScreen().y + 10 + dfHeight;
                dialog.setLocation(centerXtmp, centerYtmp);

                dialog.getContentPane().add(dialogView, BorderLayout.CENTER);
                dialog.setVisible(true);
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
