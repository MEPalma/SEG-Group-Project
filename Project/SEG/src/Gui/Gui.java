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

    private JPanel filterButtonWrapper, addGraphButtonWrapper, compareButtonWrapper;

    private MenuLabel filtersMenuLabel, addGraphMenuLabel, compareMenuLabel;

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
        this.popupMessageArea.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 0));
        this.popupMessageArea.setBackground(GuiColors.BASE_SMOKE);
        this.popupMessageArea.setPreferredSize(new Dimension(0, 0));


        /*
            TABBED VIEW VIEWS INITIALIZATION
         */
        this.tabbedViewTabsHoster = new JPanel(new BorderLayout());
        tabbedViewTabsHoster.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        tabbedViewTabsHoster.setBackground(GuiColors.BASE_SMOKE);

        this.tabbedViewContentHoster = new JPanel(new BorderLayout());
        tabbedViewContentHoster.setBorder(BorderFactory.createEmptyBorder());
        tabbedViewTabsHoster.setBackground(GuiColors.BASE_SMOKE);

        /*
            STATUS DISPLAY VIEW INITIALIZATION
         */

        this.statusDisplay = new StatusDisplay();

        /*
            INIT MAIN CONTROLLER
         */
        TabbedView tabbedView = new TabbedView(tabbedViewTabsHoster, tabbedViewContentHoster);
        this.mainController = new MainController(this, this.statusDisplay, tabbedView);

        tabbedView.push("HOME", new HomeView(mainController), new Object(), null);

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
        this.northView.add(appTitleLabel, BorderLayout.WEST);
        this.northView.add(new TitleLabel(" Dashboard App", JLabel.LEFT, 26, this.northView.getBackground()), BorderLayout.EAST);//spacer to center campaign name

        this.campaignName = new TitleLabel("FIX ME ", TitleLabel.CENTER, 18, GuiColors.BASE_WHITE);
        this.campaignName.setFont(new Font("Verdana", Font.ITALIC, 18));
        this.northView.add(this.campaignName, BorderLayout.CENTER);

        getContentPane().add(this.northView, BorderLayout.NORTH);


        /*
                MAIN_VIEW
         */
        this.mainView = new JPanel(new BorderLayout());
        this.mainView.setBackground(GuiColors.BASE_SMOKE);
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
        this.mainView.add(new SideMenu(mainController), BorderLayout.WEST);

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
        tabbedViewWrapper.add(this.popupMessageArea, BorderLayout.EAST);

        this.mainView.add(tabbedViewWrapper, BorderLayout.CENTER);

//        updateCampaignName();

        repaint();
        revalidate();
    }

    private JPanel getTopRightFunctions() {
        JPanel tabbedViewTopRightFunctions = new JPanel(new GridLayout(1, 3));
        tabbedViewTopRightFunctions.setBorder(BorderFactory.createEmptyBorder());
        tabbedViewTopRightFunctions.setBackground(GuiColors.BASE_WHITE);
        tabbedViewTopRightFunctions.add(getShowFiltersMenuLabel());
        tabbedViewTopRightFunctions.add(getAddGraphMenuLabel());
        tabbedViewTopRightFunctions.add(getCompareMenuLabel());
        tabbedViewTopRightFunctions.setPreferredSize(new Dimension(380, 50));

        return tabbedViewTopRightFunctions;
    }

    private JPanel getShowFiltersMenuLabel() {
        this.filterButtonWrapper = new JPanel(new BorderLayout());
        this.filterButtonWrapper.setBackground(GuiColors.BASE_WHITE);
        this.filterButtonWrapper.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, GuiColors.BASE_SMOKE));
        this.filterButtonWrapper.setPreferredSize(new Dimension(120, 60));

        this.filtersMenuLabel = new MenuLabel("Filters", MenuLabel.CENTER, 16);
        this.filtersMenuLabel.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, GuiColors.BASE_WHITE));
        this.filtersMenuLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (currentPopup != null) {
                    if (currentPopup instanceof FiltersMenu) {
                        currentPopup = null;
                        popupMessageArea.removeAll();

                        filterButtonWrapper.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, GuiColors.BASE_SMOKE));

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
                filtersMenuLabel.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, GuiColors.BASE_WHITE));
            }
        });


        this.filterButtonWrapper.add(this.filtersMenuLabel, BorderLayout.CENTER);

        return filterButtonWrapper;
    }

    private JPanel getAddGraphMenuLabel() {

        this.addGraphButtonWrapper = new JPanel(new BorderLayout());
        this.addGraphButtonWrapper.setBackground(GuiColors.BASE_WHITE);
        this.addGraphButtonWrapper.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, GuiColors.BASE_SMOKE));
        this.addGraphButtonWrapper.setPreferredSize(new Dimension(120, 60));

        this.addGraphMenuLabel = new MenuLabel("Add Graph", MenuLabel.CENTER, 16);
        this.addGraphMenuLabel.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, GuiColors.BASE_WHITE));
        this.addGraphMenuLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (currentPopup != null) {
                    if (currentPopup instanceof ChooseNewGraphPanel) {
                        currentPopup = null;

                        addGraphButtonWrapper.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, GuiColors.BASE_SMOKE));

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
                addGraphMenuLabel.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, GuiColors.BASE_WHITE));
            }
        });


        addGraphButtonWrapper.add(addGraphMenuLabel, BorderLayout.CENTER);
        return addGraphButtonWrapper;
    }

    private JPanel getCompareMenuLabel() {
        this.compareButtonWrapper = new JPanel(new BorderLayout());
        this.compareButtonWrapper.setBackground(GuiColors.BASE_WHITE);
        this.compareButtonWrapper.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, GuiColors.BASE_SMOKE));
        this.compareButtonWrapper.setPreferredSize(new Dimension(120, 60));

        this.compareMenuLabel = new MenuLabel("Compare", MenuLabel.CENTER, 16);
        this.compareMenuLabel.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, GuiColors.BASE_WHITE));
        this.compareMenuLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                if (currentPopup != null) {
                    if (currentPopup instanceof CompareMenu) {
                        currentPopup = null;

                        compareButtonWrapper.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, GuiColors.BASE_SMOKE));

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

    //TODO FUCK
    public void updateCampaignName(int id) {
        this.campaignName.setText(mainController.getCampaignName(id));
        this.campaignName.repaint();
        this.campaignName.revalidate();
    }

    public void openAddGraph() {
        popupMessageArea.removeAll();
        currentPopup = new ChooseNewGraphPanel(mainController);
        currentPopup.setBorder(BorderFactory.createEmptyBorder());

        addGraphButtonWrapper.setBorder(BorderFactory.createMatteBorder(4, 4, 0, 4, GuiColors.BASE_SMOKE));
        filterButtonWrapper.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, GuiColors.BASE_SMOKE));
        compareButtonWrapper.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, GuiColors.BASE_SMOKE));

        popupMessageArea.add(currentPopup);
        popupMessageArea.setPreferredSize(new Dimension(380, 380));
        popupMessageArea.repaint();
        popupMessageArea.revalidate();
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

        filterButtonWrapper.setBorder(BorderFactory.createMatteBorder(4, 4, 0, 4, GuiColors.BASE_SMOKE));
        addGraphButtonWrapper.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, GuiColors.BASE_SMOKE));
        compareButtonWrapper.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, GuiColors.BASE_SMOKE));

        popupMessageArea.setPreferredSize(new Dimension(380, 380));
        popupMessageArea.repaint();
        popupMessageArea.revalidate();
    }

    public void openCompareMenu() {
        popupMessageArea.removeAll();
        currentPopup = new CompareMenu(mainController);
        currentPopup.setBorder(BorderFactory.createEmptyBorder());
        popupMessageArea.add(currentPopup);

        compareButtonWrapper.setBorder(BorderFactory.createMatteBorder(4, 4, 0, 4, GuiColors.BASE_SMOKE));
        addGraphButtonWrapper.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, GuiColors.BASE_SMOKE));
        filterButtonWrapper.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, GuiColors.BASE_SMOKE));

        popupMessageArea.setPreferredSize(new Dimension(380, 380));
        popupMessageArea.repaint();
        popupMessageArea.revalidate();
    }

    public boolean isFiltersShowing() {
        if (this.currentPopup != null) {
            return (currentPopup instanceof FiltersMenu);
        } else return false;
    }
}
