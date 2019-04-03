package Gui.Menus;

import Commons.Tuple;
import Gui.GuiColors;
import Gui.GuiComponents.*;
import Gui.MainController;
import Gui.TakeActionListener;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;
import java.awt.*;

public class CompareMenu extends RPanel {
    private final MainController mainController;
    private final List<Tuple<Integer,String>> selections;

    private final String[] campaignsOptions;

    public CompareMenu(MainController mainController) {
        super(GuiColors.BASE_WHITE, new BorderLayout());

        this.mainController = mainController;
        this.selections = new LinkedList<>();

        List<Tuple<Integer, String>> allCampaigns = mainController.getDataExchange().selectAllCampaigns();

        this.campaignsOptions = new String[allCampaigns.size()];

        for (int i = 0; i < allCampaigns.size(); ++i)
            campaignsOptions[i] = allCampaigns.get(i).getY();

        refresh();
    }

    @Override
    public void refresh() {
        removeAll();

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        wrapper.setBackground(getBackground());

        wrapper.add(new TitleLabel("Date range", TitleLabel.LEFT, 20), BorderLayout.NORTH);

        List<Component> cells = new LinkedList<>();

        for (int i = 0; i < this.selections.size(); ++i)
            cells.add(getDropDownSelection(i, this.selections.get(i).getX(), this.campaignsOptions));

        cells.add(getAddCampaignCell());
        cells.add(getAddButton());

        wrapper.add(new ListView(getBackground(), cells).getWrappedInScroll(true), BorderLayout.CENTER);

        add(wrapper, BorderLayout.CENTER);
        repaint();
        revalidate();
    }

    private JPanel getDropDownSelection(int indexInPool, int selectedIndex, String[] options) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(getBackground());
        wrapper.setBorder(BorderFactory.createEmptyBorder());

        DropDown dropDown = new DropDown(options, null, selectedIndex);
        dropDown.addTakeActionListener(new TakeActionListener() {
            @Override
            public void takeAction() {
                selections.set(indexInPool, new Tuple(dropDown.getSelectedIndex(), options[dropDown.getSelectedIndex()]));
            }
        });
        wrapper.add(dropDown, BorderLayout.CENTER);

        MenuLabel removeMenuLabel = new MenuLabel("x", MenuLabel.RIGHT, 20);
        removeMenuLabel.dropAllListeners();
        removeMenuLabel.setForeground(GuiColors.RED_ERROR);
        removeMenuLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                selections.remove(indexInPool);
                refresh();
            }
        });
        wrapper.add(removeMenuLabel, BorderLayout.EAST);

        return wrapper;
    }

    private JPanel getAddCampaignCell() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(getBackground());
        wrapper.setBorder(BorderFactory.createEmptyBorder());

        MenuLabel addMenuLabel = new MenuLabel("+", MenuLabel.CENTER, 20);
        addMenuLabel.setToolTipText("Compare with another campaign");
        addMenuLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                selections.add(new Tuple<>(0, campaignsOptions[0]));
                refresh();
            }
        });
        wrapper.add(addMenuLabel, BorderLayout.CENTER);

        return wrapper;
    }

    private JPanel getAddButton() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(getBackground());
        wrapper.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

        MenuLabel addMenuLabel = new MenuLabel("Compare", MenuLabel.CENTER, 16);
        addMenuLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                System.out.println(campaignsOptions);
                System.out.println(selections);

                //TODO TOMORROW!
//                mainController.pushToGraphView("", "");
            }
        });
        wrapper.add(addMenuLabel, BorderLayout.CENTER);

        return wrapper;
    }
}
