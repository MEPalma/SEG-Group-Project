package Gui;

import DatabaseManager.DataExchange;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

public class Gui extends JFrame
{

    private DataExchange dataExchange;

    private JPanel mainView;
    private JPanel northView;
    private JPanel menuButtonsPane;

    private JPanel crumbsView;

    private BreadCrumbs breadCrumbs;

    public Gui(DataExchange dataExchange)
    {
        super("Dashboard App");
//        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("")));

        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        } catch (InstantiationException e)
        {
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e)
        {
            e.printStackTrace();
        }

        this.dataExchange = dataExchange;

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter()
        {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent)
            {
                breadCrumbs.cancelBackgroundTask();
                breadCrumbs.stopProgressBar();
                dataExchange.close();
                System.exit(0);
            }
        });
        getContentPane().setLayout(new BorderLayout());

        this.northView = new JPanel(new BorderLayout());
        getContentPane().add(this.northView, BorderLayout.NORTH);
        this.northView.setBackground(GuiColors.LIGHT);
        this.northView.setLayout(new BorderLayout());
        this.northView.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, GuiColors.LIGHT_GRAY));

        List<JLabel> menuLabels = new ArrayList<JLabel>(5);

        TitleLabel titleLabel = new TitleLabel("Dashboard App", JLabel.LEFT);
        titleLabel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                if (!titleLabel.getName().equals("SELECTED"))
                {
                    for (JLabel l : menuLabels)
                    {
                        l.setName("");
                    }
                    titleLabel.setName("SELECTED");
                    openHomeView();
                }
            }
        });
        menuLabels.add(titleLabel);
        this.northView.add(titleLabel, BorderLayout.NORTH);

        this.mainView = new JPanel(new BorderLayout());
        this.mainView.setBackground(GuiColors.RED);
        getContentPane().add(this.mainView, BorderLayout.CENTER);

        this.crumbsView = new JPanel(new BorderLayout());
        this.crumbsView.setBackground(GuiColors.LIGHT);
        this.crumbsView.setBorder(BorderFactory.createEmptyBorder());

        this.breadCrumbs = new BreadCrumbs(this.mainView);
        this.mainView.add(this.crumbsView, BorderLayout.NORTH);
        this.mainView.add(this.crumbsView, BorderLayout.CENTER);

        this.menuButtonsPane = new JPanel(new GridLayout(10, 1, 4, 4));
        this.menuButtonsPane.setBackground(GuiColors.RED);
        this.menuButtonsPane.setPreferredSize(new Dimension(120, 60));
        add(this.menuButtonsPane, BorderLayout.WEST);

        MenuLabel homeButton = new MenuLabel("Home", MenuLabel.CENTER);
        homeButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if (!homeButton.getName().equals("S"))
                {
                    for (JLabel l : menuLabels)
                    {
                        l.setName("");
                    }
                    homeButton.setName("S");
                    openHomeView();
                }
            }
        });
        menuLabels.add(homeButton);

        MenuLabel compareButton = new MenuLabel("Compare", MenuLabel.CENTER);
        compareButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if (!compareButton.getName().equals("S"))
                {
                    for (JLabel l : menuLabels)
                    {
                        l.setName("");
                    }
                    compareButton.setName("S");
//                    openCompareView();TODO
                }
            }
        });
        menuLabels.add(compareButton);

        MenuLabel loadButton = new MenuLabel("Load CSVs", MenuLabel.CENTER);
        loadButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if (!compareButton.getName().equals("S"))
                {
                    for (JLabel l : menuLabels)
                    {
                        l.setName("");
                    }
                    compareButton.setName("S");
                    openLoadCSVsView();
                }
            }
        });
        menuLabels.add(loadButton);

        MenuLabel settingsButton = new MenuLabel("Settings", MenuLabel.CENTER);
        settingsButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if (!compareButton.getName().equals("S"))
                {
                    for (JLabel l : menuLabels)
                    {
                        l.setName("");
                    }
                    settingsButton.setName("S");
                    openLoadCSVsView();
                }
            }
        });
        menuLabels.add(settingsButton);

        this.menuButtonsPane.add(homeButton);
        this.menuButtonsPane.add(compareButton);
        this.menuButtonsPane.add(loadButton);
        this.menuButtonsPane.add(settingsButton);

        openHomeView();
    }

    @Override
    public void setVisible(boolean visible)
    {
        if (visible)
        {
            setSize(new Dimension(1200, 850));

            Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (screen.width / 2) - (super.getSize().width / 2);
            int y = (screen.height / 2) - (super.getSize().height / 2);
            super.setLocation(x, y);
        }
        super.setVisible(visible);
    }

    private synchronized void openHomeView()
    {
        breadCrumbs.clear();
        breadCrumbs.push("Home", new HomeView(dataExchange, breadCrumbs));
    }

    public synchronized void openLoadCSVsView()
    {
        breadCrumbs.clear();
        breadCrumbs.push("Load CSVs", new LoadCSVsView(dataExchange, breadCrumbs));
    }

}
