package Gui.Menus;

import Gui.GuiComponents.ListView;
import Gui.GuiComponents.RPanel;
import Gui.GuiComponents.TitleLabel;
import Gui.MainController;
import Gui.TakeActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.LinkedList;

public class SettingsMenu extends RPanel {

    private final MainController mainController;

    public SettingsMenu(MainController mainController) {
        super(mainController.getGuiColors().getGuiTextColor(), new BorderLayout());
        this.mainController = mainController;

        refresh();
    }


    @Override
    public void refresh() {
        removeAll();

        add(new JLabel("PORCO"), BorderLayout.NORTH);
        add(getChangeColorMenu(), BorderLayout.CENTER);

        repaint();
        revalidate();
    }

    private JPanel getChangeColorMenu() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBorder(BorderFactory.createEmptyBorder(12, 8, 8, 8));
        wrapper.setBackground(mainController.getGuiColors().getGuiTextColor());

        wrapper.add(new TitleLabel("Gui Colors", TitleLabel.LEFT, 18, mainController.getGuiColors()));

        List<Component> cells = new LinkedList<Component>();
        cells.add(getPrimeColorChooser("Prime", mainController.getGuiColors().getGuiPrimeColor(), new TakeActionListener() {
            @Override
            public void takeAction() {
                mainController.getDataExchange().updateGuiPrimeColor(mainController.getGuiColors().getGuiPrimeColor());
                mainController.updateGuiColors();

                mainController.repaintAll();
            }
        }));
        wrapper.add(new ListView(mainController.getGuiColors(),cells, false), BorderLayout.CENTER);

        return wrapper;
    }

    private JPanel getPrimeColorChooser(String title, Color currentColor, TakeActionListener onSelection) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(this.mainController.getGuiColors().getGuiTextColor());
        wrapper.setBorder(BorderFactory.createEmptyBorder());

        wrapper.add(new TitleLabel(title, TitleLabel.CENTER, 14, mainController.getGuiColors()), BorderLayout.WEST);

        JPanel colorDisplay = new JPanel(new GridLayout(1, 1));
        colorDisplay.setBackground(currentColor);
        colorDisplay.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        colorDisplay.setPreferredSize(new Dimension(50, 28));
        colorDisplay.addMouseListener(new MouseAdapter() {
            Color x = currentColor;
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                Color newColor = JColorChooser.showDialog(null, "Choose a Color", colorDisplay.getBackground());
                if (newColor != null) {
                    mainController.getGuiColors().setGuiPrimeColor(newColor);
                    colorDisplay.setBackground(newColor);
                    x = newColor;
                    onSelection.takeAction();
                }
            }
        });

        wrapper.add(colorDisplay, BorderLayout.CENTER);

        return wrapper;
    }
}
