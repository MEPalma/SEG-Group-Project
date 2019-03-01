package Gui;

import DatabaseManager.DataExchange;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Gui extends JFrame {

    private DataExchange dataExchange;

    private JPanel mainView;
    private JPanel northView;
    private JPanel menuButtonsPane;

    private BreadCrumbsHoster breadCrumbsHoster;

    public Gui(DataExchange dataExchange) {
        super("Dashboard App");
//        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("")));

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

        this.dataExchange = dataExchange;
        this.breadCrumbsHoster = new BreadCrumbsHoster();

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
//                breadCrumbs.cancelBackgroundTask();
//                breadCrumbs.stopProgressBar();
                dataExchange.close();
                System.exit(0);
            }
        });
        getContentPane().setLayout(new BorderLayout());

        this.northView = new JPanel(new BorderLayout());
        getContentPane().add(this.northView, BorderLayout.NORTH);
        this.northView.setBackground(GuiColors.BASE_LIGHT);
        this.northView.setLayout(new BorderLayout());
        this.northView.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, GuiColors.LIGHT_GRAY));

        List<JLabel> menuLabels = new ArrayList<JLabel>(5);

        TitleLabel titleLabel = new TitleLabel("Dashboard App", JLabel.LEFT);

        this.northView.add(titleLabel, BorderLayout.NORTH);

        this.mainView = new JPanel(new BorderLayout());
        this.mainView.setBackground(GuiColors.LIGHT);
        this.mainView.add(this.breadCrumbsHoster, BorderLayout.CENTER);
        getContentPane().add(this.mainView, BorderLayout.CENTER);


        this.menuButtonsPane = new JPanel(new GridLayout(10, 1, 4, 4));
        this.menuButtonsPane.setBackground(GuiColors.DARK_GRAY);
        this.menuButtonsPane.setPreferredSize(new Dimension(60, 60));
//        add(this.menuButtonsPane, BorderLayout.WEST);

//        MenuLabel homeButton = new MenuLabel("Home", MenuLabel.CENTER, 8);
//        homeButton.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                if (!homeButton.getName().equals("S")) {
//                    for (JLabel l : menuLabels) {
//                        l.setName("");
//                    }
//                    homeButton.setName("S");
////                    openHomeView();
//                    breadCrumbsHoster.getBreadCrumbs().push("AFSKNVA", new LoadCSVsView(dataExchange, breadCrumbsHoster.getBreadCrumbs()));
//                }
//            }
//        });
//        menuLabels.add(homeButton);
//
//        MenuLabel loadButton = new MenuLabel("Load CSVs", MenuLabel.CENTER, 8);
//        loadButton.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                if (!loadButton.getName().equals("S")) {
//                    for (JLabel l : menuLabels) {
//                        l.setName("");
//                    }
//                    loadButton.setName("S");
//                }
//            }
//        });
//        menuLabels.add(loadButton);
//
//        MenuLabel settingsButton = new MenuLabel("Settings", MenuLabel.CENTER, 8);
//        settingsButton.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                if (!settingsButton.getName().equals("S")) {
//                    for (JLabel l : menuLabels) {
//                        l.setName("");
//                    }
//                    settingsButton.setName("S");
////                    openLoadCSVsView();
//                }
//            }
//        });
//        menuLabels.add(settingsButton);
//
//        this.menuButtonsPane.add(homeButton);
//        this.menuButtonsPane.add(loadButton);
//        this.menuButtonsPane.add(settingsButton);


        //BreadCrumbs
        JPanel crumbsView = new JPanel(new BorderLayout());
        crumbsView.setBackground(GuiColors.LIGHT);
        crumbsView.setBorder(BorderFactory.createEmptyBorder());

        this.breadCrumbsHoster.getBreadCrumbs().push("Campaign Name", new MainMenu(this.dataExchange, this.breadCrumbsHoster.getBreadCrumbs()));

//        openHomeView();
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

//    private synchronized void openHomeView() {
//
//        this.breadCrumbs = new BreadCrumbs(this.mainView);
//        breadCrumbs.clear();
//        breadCrumbs.push("Home", new Deprecated_HomeView(dataExchange, breadCrumbs));
//    }
//
//    public synchronized void openLoadCSVsView() {
//        breadCrumbs.clear();
//        breadCrumbs.push("Load CSVs", new LoadCSVsView(dataExchange, breadCrumbs));
//    }

}
