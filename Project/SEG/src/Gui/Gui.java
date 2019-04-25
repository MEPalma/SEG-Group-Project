package Gui;

import Gui.GuiComponents.MenuLabel;
import Gui.GuiComponents.TitleLabel;
import Gui.HomeView.HomeView;
import Gui.Menus.ChooseNewGraphPanel;
import Gui.Menus.CompareMenu;
import Gui.Menus.FiltersMenu;
import Gui.Menus.SideMenu;
import Gui.TabbedView.TabbedView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Gui extends JFrame {
    
    private StatusDisplay statusDisplay;
    
    private JPanel mainView;
    private JPanel northView;

    private JPanel tabbedViewTabsHoster, tabbedViewContentHoster;

    private MainController mainController;

    private JPanel popupMessageArea;
    private JPanel currentPopup;
    private TitleLabel campaignName;

    private JPanel filterButtonWrapper, compareButtonWrapper; //addGraphButtonWrapper,

    private MenuLabel filtersMenuLabel, compareMenuLabel; //addGraphMenuLabel,

    private TabbedView tabbedView;
    private SideMenu sideMenu;

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

        this.tabbedViewTabsHoster = new JPanel(new BorderLayout());
        this.tabbedViewContentHoster = new JPanel(new BorderLayout());

        /*
            INIT MAIN CONTROLLER
         */
        GuiColors guiColors = new GuiColors();
        this.statusDisplay = new StatusDisplay(guiColors);

        this.tabbedView = new TabbedView(tabbedViewTabsHoster, tabbedViewContentHoster);
        this.mainController = new MainController(this, this.statusDisplay, tabbedView, guiColors);

        tabbedView.init(this.mainController);

        TakeActionListener onHomeViewClick = () -> {
            if (tabbedView.isHomeOpen() && mainController.isFiltersShowing())
                closeFiltersMenu();
        };

        tabbedView.pushNewHomeTab("HOME", new HomeView(mainController), onHomeViewClick);

        /*
            POPUPS
         */
        this.popupMessageArea = new JPanel(new BorderLayout());
        this.popupMessageArea.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 0));
        this.popupMessageArea.setBackground(mainController.getGuiColors().getGuiBackgroundColor());
        this.popupMessageArea.setPreferredSize(new Dimension(0, 0));


        this.northView = new JPanel(new BorderLayout());
        this.mainView = new JPanel(new BorderLayout());

//        this.campaignName = new TitleLabel("", TitleLabel.CENTER, 18, mainController.getGuiColors().getGuiTextColor());

        refresh();
    }

    public void refresh() {
        /*
            ORGANIZE LAYOUT
         */
        getContentPane().setLayout(new BorderLayout());

        /*
                NORTH_VIEW
         */
        this.northView.removeAll();
        this.northView.setBackground(mainController.getGuiColors().getGuiPrimeColor());
        this.northView.setLayout(new BorderLayout());
        this.northView.setBorder(BorderFactory.createEmptyBorder());
        this.northView.setPreferredSize(new Dimension(300, 56));


        TitleLabel appTitleLabel = new TitleLabel(" Dashboard App", JLabel.LEFT, 26, mainController.getGuiColors());
        appTitleLabel.setForeground(mainController.getGuiColors().getGuiTextColor());
        this.northView.add(appTitleLabel, BorderLayout.WEST);
        this.northView.add(new TitleLabel(" Dashboard App", JLabel.LEFT, 26, this.northView.getBackground()), BorderLayout.EAST);//spacer to center campaign name


//        this.campaignName.setFont(new Font("Verdana", Font.ITALIC, 18));
//        this.northView.add(this.campaignName, BorderLayout.CENTER);

        getContentPane().add(this.northView, BorderLayout.NORTH);


        /*
                MAIN_VIEW
         */
        this.mainView.removeAll();
        this.mainView.setBackground(mainController.getGuiColors().getGuiBackgroundColor());
        this.mainView.add(statusDisplay, BorderLayout.NORTH);
        this.getContentPane().add(this.mainView, BorderLayout.CENTER);

        //Welcome view
        if (mainController.isDbEmpty()) {
            WelcomeProcedure welcomeProcedure = new WelcomeProcedure(mainController);
            welcomeProcedure.setOnClose(new TakeActionListener() {
                @Override
                public void takeAction() {
                    mainView.remove(welcomeProcedure);
                    setupMainView();
                }
            });
            this.mainView.add(welcomeProcedure, BorderLayout.CENTER);
        } else setupMainView();

        repaint();
        revalidate();
    }

    private void setupMainView() {

        this.sideMenu = new SideMenu(mainController);
        this.mainView.add(this.sideMenu, BorderLayout.WEST);

        JPanel tabbedViewTopWrapper = new JPanel(new BorderLayout());
        tabbedViewTopWrapper.setBorder(BorderFactory.createEmptyBorder());
        tabbedViewTopWrapper.setBackground(mainController.getGuiColors().getGuiBackgroundColor());
        tabbedViewTopWrapper.add(tabbedViewTabsHoster, BorderLayout.CENTER);
        tabbedViewTopWrapper.add(getTopRightFunctions(), BorderLayout.EAST);

        JPanel tabbedViewWrapper = new JPanel(new BorderLayout());
        tabbedViewWrapper.setBorder(BorderFactory.createEmptyBorder());
        tabbedViewWrapper.setBackground(mainController.getGuiColors().getGuiBackgroundColor());
        tabbedViewWrapper.add(tabbedViewTopWrapper, BorderLayout.NORTH);
        tabbedViewWrapper.add(tabbedViewContentHoster, BorderLayout.CENTER);
        tabbedViewWrapper.add(this.popupMessageArea, BorderLayout.EAST);

        this.mainView.add(tabbedViewWrapper, BorderLayout.CENTER);

//        updateCampaignName();

        repaint();
        revalidate();
    }

    private JPanel getTopRightFunctions() {
//        JPanel tabbedViewTopRightFunctions = new JPanel(new GridLayout(1, 3));
        JPanel tabbedViewTopRightFunctions = new JPanel(new GridLayout(1, 2));
        tabbedViewTopRightFunctions.setBorder(BorderFactory.createEmptyBorder());
        tabbedViewTopRightFunctions.setBackground(mainController.getGuiColors().getGuiTextColor());
        tabbedViewTopRightFunctions.add(getShowFiltersMenuLabel());
//        tabbedViewTopRightFunctions.add(getAddGraphMenuLabel());
        tabbedViewTopRightFunctions.add(getCompareMenuLabel());
        tabbedViewTopRightFunctions.setPreferredSize(new Dimension(380, 50));

        return tabbedViewTopRightFunctions;
    }

    private JPanel getShowFiltersMenuLabel() {
        this.filterButtonWrapper = new JPanel(new BorderLayout());
        this.filterButtonWrapper.setBackground(mainController.getGuiColors().getGuiTextColor());
        this.filterButtonWrapper.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, mainController.getGuiColors().getGuiBackgroundColor()));
        this.filterButtonWrapper.setPreferredSize(new Dimension(120, 60));

        this.filtersMenuLabel = new MenuLabel("Filters", MenuLabel.CENTER, 16, mainController.getGuiColors());
        this.filtersMenuLabel.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, mainController.getGuiColors().getGuiTextColor()));
        this.filtersMenuLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (currentPopup != null) {
                    if (currentPopup instanceof FiltersMenu) {
                        currentPopup = null;
                        popupMessageArea.removeAll();

                        filterButtonWrapper.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, mainController.getGuiColors().getGuiBackgroundColor()));

                        popupMessageArea.setPreferredSize(new Dimension(0, 0));
                        popupMessageArea.repaint();
                        popupMessageArea.revalidate();

                        return;
                    }
                }
                openFilters();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                filtersMenuLabel.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, mainController.getGuiColors().getGuiTextColor()));
            }
        });


        this.filterButtonWrapper.add(this.filtersMenuLabel, BorderLayout.CENTER);

        return filterButtonWrapper;
    }

    /*private JPanel getAddGraphMenuLabel() {

        this.addGraphButtonWrapper = new JPanel(new BorderLayout());
        this.addGraphButtonWrapper.setBackground(mainController.getGuiColors().getGuiTextColor());
        this.addGraphButtonWrapper.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, mainController.getGuiColors().getGuiBackgroundColor()));
        this.addGraphButtonWrapper.setPreferredSize(new Dimension(120, 60));

        this.addGraphMenuLabel = new MenuLabel("Add Graph", MenuLabel.CENTER, 16, mainController.getGuiColors());
        this.addGraphMenuLabel.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, mainController.getGuiColors().getGuiTextColor()));
        this.addGraphMenuLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (currentPopup != null) {
                    if (currentPopup instanceof ChooseNewGraphPanel) {
                        currentPopup = null;

                        addGraphButtonWrapper.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, mainController.getGuiColors().getGuiBackgroundColor()));

                        popupMessageArea.setPreferredSize(new Dimension(0, 0));
                        popupMessageArea.removeAll();
                        popupMessageArea.repaint();
                        popupMessageArea.revalidate();

                        return;
                    }
                }

                openAddGraph();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                addGraphMenuLabel.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, mainController.getGuiColors().getGuiTextColor()));
            }
        });


        addGraphButtonWrapper.add(addGraphMenuLabel, BorderLayout.CENTER);
        return addGraphButtonWrapper;
    }*/

    private JPanel getCompareMenuLabel() {
        this.compareButtonWrapper = new JPanel(new BorderLayout());
        this.compareButtonWrapper.setBackground(mainController.getGuiColors().getGuiTextColor());
        this.compareButtonWrapper.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, mainController.getGuiColors().getGuiBackgroundColor()));
        this.compareButtonWrapper.setPreferredSize(new Dimension(120, 60));

        this.compareMenuLabel = new MenuLabel("Add graph", MenuLabel.CENTER, 16, mainController.getGuiColors());
        this.compareMenuLabel.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, mainController.getGuiColors().getGuiTextColor()));
        this.compareMenuLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                if (currentPopup != null) {
                    if (currentPopup instanceof CompareMenu) {
                        currentPopup = null;

                        compareButtonWrapper.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, mainController.getGuiColors().getGuiBackgroundColor()));

                        popupMessageArea.setPreferredSize(new Dimension(0, 0));
                        popupMessageArea.removeAll();
                        popupMessageArea.repaint();
                        popupMessageArea.revalidate();

                        return;
                    }
                }

                openCompareMenu();
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }
        });

        this.compareButtonWrapper.add(this.compareMenuLabel, BorderLayout.CENTER);
        return this.compareButtonWrapper;
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

    public void openAddGraph() {
//        popupMessageArea.removeAll();
//        currentPopup = new ChooseNewGraphPanel(mainController);
//        currentPopup.setBorder(BorderFactory.createEmptyBorder());
//
//        addGraphButtonWrapper.setBorder(BorderFactory.createMatteBorder(4, 4, 0, 4, mainController.getGuiColors().getGuiBackgroundColor()));
//        filterButtonWrapper.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, mainController.getGuiColors().getGuiBackgroundColor()));
//        compareButtonWrapper.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, mainController.getGuiColors().getGuiBackgroundColor()));
//
//        popupMessageArea.add(currentPopup);
//        popupMessageArea.setPreferredSize(new Dimension(380, 380));
//        popupMessageArea.repaint();
//        popupMessageArea.revalidate();
        openCompareMenu();
    }

    public void openFilters() {
        if (mainController.getSelectedGraph() == null) {
            this.statusDisplay.showErrorMessage("No graph to filter", "Add a graph first, then click on \"Filters\" to apply some filters to it.");
            return;
        }

        popupMessageArea.removeAll();
        currentPopup = new FiltersMenu(mainController);
        currentPopup.setBorder(BorderFactory.createEmptyBorder());
        popupMessageArea.add(currentPopup);

        filterButtonWrapper.setBorder(BorderFactory.createMatteBorder(4, 4, 0, 4, mainController.getGuiColors().getGuiBackgroundColor()));
        //addGraphButtonWrapper.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, mainController.getGuiColors().getGuiBackgroundColor()));
        compareButtonWrapper.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, mainController.getGuiColors().getGuiBackgroundColor()));

        popupMessageArea.setPreferredSize(new Dimension(380, 380));
        popupMessageArea.repaint();
        popupMessageArea.revalidate();
    }

    public void openCompareMenu() {
        popupMessageArea.removeAll();
        currentPopup = new CompareMenu(mainController);
        currentPopup.setBorder(BorderFactory.createEmptyBorder());
        popupMessageArea.add(currentPopup);

        compareButtonWrapper.setBorder(BorderFactory.createMatteBorder(4, 4, 0, 4, mainController.getGuiColors().getGuiBackgroundColor()));
        //addGraphButtonWrapper.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, mainController.getGuiColors().getGuiBackgroundColor()));
        filterButtonWrapper.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, mainController.getGuiColors().getGuiBackgroundColor()));

        popupMessageArea.setPreferredSize(new Dimension(380, 380));
        popupMessageArea.repaint();
        popupMessageArea.revalidate();

    }

    public boolean isFiltersShowing() {
        if (this.currentPopup != null) {
            return (currentPopup instanceof FiltersMenu);
        } else return false;
    }

    public void closeFiltersMenu() {
        if (this.currentPopup instanceof FiltersMenu) {
            currentPopup = null;

            filterButtonWrapper.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, mainController.getGuiColors().getGuiBackgroundColor()));

            popupMessageArea.setPreferredSize(new Dimension(0, 0));
            popupMessageArea.removeAll();
            popupMessageArea.repaint();
            popupMessageArea.revalidate();
        }
    }
}
