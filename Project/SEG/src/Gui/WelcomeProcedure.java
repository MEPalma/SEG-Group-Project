package Gui;

import Commons.GraphSpecs;
import Gui.GuiComponents.MenuLabel;
import Gui.GuiComponents.TitleLabel;
import Gui.Menus.LoadCSVsMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WelcomeProcedure extends JPanel {
    private final MainController mainController;
    private final JPanel this_ = this;
    private TakeActionListener onClose;

    public WelcomeProcedure(MainController mainController) {
        super(new BorderLayout());
        this.mainController = mainController;

        setBorder(BorderFactory.createMatteBorder(20, 40, 20, 40, mainController.getGuiColors().getGuiBackgroundColor()));
        setBackground(mainController.getGuiColors().getGuiTextColor());

        add(getLoadFilesView(), BorderLayout.CENTER);
    }

    public void setOnClose(TakeActionListener onClose) {
        this.onClose = onClose;
    }

    private JPanel getLoadFilesView() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBorder(BorderFactory.createEmptyBorder());
        wrapper.setBackground(mainController.getGuiColors().getGuiTextColor());

        TitleLabel logo = new TitleLabel("", TitleLabel.CENTER, mainController.getGuiColors());
        logo.setIcon(new ImageIcon(getClass().getResource("/Icons/logo.png")));
        logo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        wrapper.add(logo, BorderLayout.NORTH);

        MenuLabel segue = new MenuLabel("Next", MenuLabel.CENTER, 16, mainController.getGuiColors());
        segue.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        segue.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                this_.removeAll();
                this_.repaint();
                this_.revalidate();
                
//                mainController.pushToGraphView(new GraphSpecs(
//
//                        GraphSpecs.METRICS.NumberImpressions,
//                        GraphSpecs.TIME_SPAN.HOUR_SPAN,
//                        GraphSpecs.BOUNCE_DEF.NPAGES,
//                        mainController.getInitFilters())
//                );
                
                onClose.takeAction();
            }
        });

        TakeActionListener onLoaded = new TakeActionListener() {
            @Override
            public void takeAction() {
                wrapper.add(segue, BorderLayout.SOUTH);
                wrapper.repaint();
                wrapper.revalidate();
            }
        };

        LoadCSVsMenu loadCSVsMenu = new LoadCSVsMenu(mainController);
        loadCSVsMenu.setBorder(BorderFactory.createEmptyBorder(40, 80, 80, 80));
        loadCSVsMenu.setOnLoaded(onLoaded);

        wrapper.add(loadCSVsMenu, BorderLayout.CENTER);

        return wrapper;
    }


}
