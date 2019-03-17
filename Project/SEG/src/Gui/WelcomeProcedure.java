package Gui;

import Commons.GraphSpecs;
import Gui.GuiComponents.MenuLabel;
import Gui.GuiComponents.TitleLabel;
import Gui.Menus.LoadCSVsMenu;
import org.jfree.chart.title.Title;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WelcomeProcedure extends JPanel {
    private final MainController mainController;
    private TakeActionListener onClose;
    private final JPanel this_ = this;

    public WelcomeProcedure(MainController mainController) {
        super(new BorderLayout());
        this.mainController = mainController;

        setBorder(BorderFactory.createMatteBorder(40, 40, 40, 40, GuiColors.BASE_SMOKE));
        setBackground(GuiColors.BASE_WHITE);

        add(getWelcomeView(), BorderLayout.CENTER);
    }

    public void setOnClose(TakeActionListener onClose) {
        this.onClose = onClose;
    }

    private JPanel getWelcomeView() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBorder(BorderFactory.createEmptyBorder());
        wrapper.setBackground(GuiColors.BASE_WHITE);

        TitleLabel logo = new TitleLabel("", TitleLabel.CENTER);
        logo.setIcon(new ImageIcon(getClass().getResource("/Icons/logo.png")));
        logo.setPreferredSize(new Dimension(150, 150));
        logo.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));
        wrapper.add(logo, BorderLayout.CENTER);

        MenuLabel segue = new MenuLabel("Start", MenuLabel.CENTER, 16);
        segue.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        segue.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                this_.removeAll();
                this_.add(getLoadFilesView(), BorderLayout.CENTER);
                this_.repaint();
                this_.revalidate();
            }
        });
        wrapper.add(segue, BorderLayout.SOUTH);

        return wrapper;
    }

    private JPanel getLoadFilesView() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBorder(BorderFactory.createEmptyBorder());
        wrapper.setBackground(GuiColors.BASE_WHITE);

        MenuLabel segue = new MenuLabel("Next", MenuLabel.CENTER, 16);
        segue.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        segue.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                this_.removeAll();
                this_.add(getShowFinalMessage(), BorderLayout.CENTER);
                this_.repaint();
                this_.revalidate();
            }
        });
//        wrapper.add(segue, BorderLayout.SOUTH);

        TakeActionListener onLoaded = new TakeActionListener() {
            @Override
            public void takeAction() {
                wrapper.add(segue, BorderLayout.SOUTH);
                wrapper.repaint();
                wrapper.revalidate();
            }
        };

        LoadCSVsMenu loadCSVsMenu = new LoadCSVsMenu(mainController);
        loadCSVsMenu.setBorder(BorderFactory.createEmptyBorder());
        loadCSVsMenu.setOnLoaded(onLoaded);

        wrapper.add(loadCSVsMenu, BorderLayout.CENTER);

        return wrapper;
    }

    private JPanel getShowFinalMessage() {

        mainController.pushToGraphView(new GraphSpecs(
                GraphSpecs.METRICS.NumberImpressions,
                GraphSpecs.TIME_SPAN.WEEK_SPAN,
                GraphSpecs.BOUNCE_DEF.NPAGES,
                mainController.getFilterSpecs()));

        mainController.pushToGraphView(new GraphSpecs(
                GraphSpecs.METRICS.NumberImpressions,
                GraphSpecs.TIME_SPAN.DAY_SPAN,
                GraphSpecs.BOUNCE_DEF.NPAGES,
                mainController.getFilterSpecs()));

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBorder(BorderFactory.createEmptyBorder());
        wrapper.setBackground(GuiColors.BASE_WHITE);

        TitleLabel titleLabel = new TitleLabel("All set!", TitleLabel.LEFT);
        titleLabel.setForeground(GuiColors.BASE_PRIME);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8,8 ));
        wrapper.add(titleLabel, BorderLayout.NORTH);

        TitleLabel messageLabel = new TitleLabel("<html>Good news: you are all set!<br><br><br>I have prepared two graphs " +
                "for you, bot you can add more by clicking on the add button.<br>Last but not least don't forget to visit the " +
                "filters section if you wish to filter the data on the graphs.</html>", TitleLabel.CENTER, 16);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(8, 50, 8, 50));
        messageLabel.setForeground(GuiColors.BASE_PRIME);
        wrapper.add(messageLabel, BorderLayout.CENTER);

        MenuLabel segue = new MenuLabel("Next", MenuLabel.CENTER, 16);
        segue.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        segue.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                onClose.takeAction();
            }
        });
        wrapper.add(segue, BorderLayout.SOUTH);

        return wrapper;
    }


}
