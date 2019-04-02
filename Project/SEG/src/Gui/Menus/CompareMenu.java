package Gui.Menus;

import Commons.Tuple;
import Gui.GuiColors;
import Gui.GuiComponents.DropDown;
import Gui.GuiComponents.ListView;
import Gui.GuiComponents.MenuLabel;
import Gui.GuiComponents.RPanel;
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

    private final List<Tuple<Integer, String>> allCampaigns;

    public CompareMenu(MainController mainController) {
        super(GuiColors.BASE_WHITE, new BorderLayout());

        this.mainController = mainController;
        this.selections = new LinkedList<>();

        this.allCampaigns = mainController.getDataExchange().selectAllCampaigns();
    }

    @Override
    public void refresh() {
        removeAll();

        List<Component> cells = new LinkedList<>();

        this.add(new ListView(getBackground(), cells).getWrappedInScroll(true), BorderLayout.CENTER);




        repaint();
        refresh();
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

        return wrapper;
    }

    private JPanel getAddCampaignCell() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(getBackground());
        wrapper.setBorder(BorderFactory.createEmptyBorder());

        MenuLabel addMenuLabel = new MenuLabel("+");
        addMenuLabel.setToolTipText("Compare with another campaign");
        addMenuLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                //TODO
            }
        });
        wrapper.add(addMenuLabel, BorderLayout.CENTER);

        return wrapper;
    }
}
