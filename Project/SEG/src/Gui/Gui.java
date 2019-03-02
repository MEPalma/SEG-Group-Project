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

    private MainController mainController;

    public Gui(MainController mainController) {
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
        this.mainController = mainController;

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                mainController.close();
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
        this.mainView.add(this.mainController.getBreadCrumbsHoster(), BorderLayout.CENTER);
        getContentPane().add(this.mainView, BorderLayout.CENTER);


        this.menuButtonsPane = new JPanel(new GridLayout(10, 1, 4, 4));
        this.menuButtonsPane.setBackground(GuiColors.DARK_GRAY);
        this.menuButtonsPane.setPreferredSize(new Dimension(60, 60));


        this.mainController.pushNewViewOnBreadCrumbs("Campaign Name", new MainMenu(this.mainController));
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
