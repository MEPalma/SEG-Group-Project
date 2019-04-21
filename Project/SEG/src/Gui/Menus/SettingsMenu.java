package Gui.Menus;

import Gui.GuiColors;
import Gui.GuiComponents.ListView;
import Gui.GuiComponents.MenuLabel;
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

        setBorder(BorderFactory.createMatteBorder(4, 0, 4, 4, mainController.getGuiColors().getGuiPrimeColor()));

        refresh();
    }


    @Override
    public void refresh() {
        removeAll();

        MenuLabel titleButton = new MenuLabel("Settings", MenuLabel.LEFT, 18, mainController.getGuiColors());
        titleButton.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        add(titleButton, BorderLayout.NORTH);

        add(getChangeColorMenu(), BorderLayout.CENTER);

        repaint();
        revalidate();
    }

    private JPanel getChangeColorMenu() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBorder(BorderFactory.createEmptyBorder(12, 8, 8, 8));
        wrapper.setBackground(mainController.getGuiColors().getGuiTextColor());

        wrapper.add(new TitleLabel("Gui Colors", TitleLabel.LEFT, 18, mainController.getGuiColors()), BorderLayout.NORTH);

        TakeActionListener updateListener = new TakeActionListener() {
            @Override
            public void takeAction() {
                mainController.getDataExchange().updateGuiPrimeColor(mainController.getGuiColors().getGuiPrimeColor());
                mainController.getDataExchange().updateGuiOptionColor(mainController.getGuiColors().getGuiOptionColor());
                mainController.getDataExchange().updateGuiTextColor(mainController.getGuiColors().getGuiTextColor());
                mainController.getDataExchange().updateGuiBackgroundColor(mainController.getGuiColors().getGuiBackgroundColor());

                mainController.updateGuiColors();

                mainController.repaintAll();
            }
        };

        List<Component> cells = new LinkedList<Component>();
        cells.add(getPrimeColorChooser("Prime", mainController.getGuiColors().getGuiPrimeColor(), updateListener));
        cells.add(getOptionColorChooser("Option", mainController.getGuiColors().getGuiOptionColor(), updateListener));
        cells.add(getTextColorChooser("Text and Secondary", mainController.getGuiColors().getGuiTextColor(), updateListener));
        cells.add(getBackgroundColorChooser("Background", mainController.getGuiColors().getGuiBackgroundColor(), updateListener));

        MenuLabel resetToDefaults = new MenuLabel(" Reset to defaults", MenuLabel.LEFT, 16, mainController.getGuiColors());
        resetToDefaults.setBorder(BorderFactory.createEmptyBorder(8, 4, 4, 4));
        resetToDefaults.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                mainController.getGuiColors().setGuiPrimeColor(GuiColors.DEFAULT_BASE_PRIME);
                mainController.getGuiColors().setGuiOptionColor(GuiColors.DEFAULT_BASE_OPTION);
                mainController.getGuiColors().setGuiTextColor(GuiColors.DEFAULT_TEXT);
                mainController.getGuiColors().setGuiBackgroundColor(GuiColors.DEFAULT_BACKGROUND);

                updateListener.takeAction();
            }
        });
        cells.add(resetToDefaults);

        wrapper.add(new ListView(mainController.getGuiColors(),cells, false), BorderLayout.CENTER);

        return wrapper;
    }

    private JPanel getPrimeColorChooser(String title, Color currentColor, TakeActionListener onSelection) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(this.mainController.getGuiColors().getGuiTextColor());
        wrapper.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

        TitleLabel titleLabel = new TitleLabel(title, TitleLabel.LEFT, 14, mainController.getGuiColors());
        titleLabel.setPreferredSize(new Dimension(154, 30));
        wrapper.add(titleLabel, BorderLayout.WEST);

        JPanel colorDisplay = new JPanel(new GridLayout(1, 1));
        colorDisplay.setBackground(currentColor);
        colorDisplay.setBorder(BorderFactory.createLineBorder(colorDisplay.getBackground().darker(), 1));
        colorDisplay.setPreferredSize(new Dimension(50, 28));

        wrapper.add(colorDisplay, BorderLayout.CENTER);

        MenuLabel changeColor = new MenuLabel(" change", MenuLabel.CENTER, 14, mainController.getGuiColors());
        changeColor.addMouseListener(new MouseAdapter() {
            Color x = currentColor;
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                Color newColor = JColorChooser.showDialog(null, "Choose a Color", colorDisplay.getBackground());
                if (newColor != null) {
                    mainController.getGuiColors().setGuiPrimeColor(newColor);
                    colorDisplay.setBackground(newColor);
                    colorDisplay.setBorder(BorderFactory.createLineBorder(colorDisplay.getBackground().darker(), 1));
                    x = newColor;
                    onSelection.takeAction();
                }
            }
        });
        wrapper.add(changeColor, BorderLayout.EAST);

        return wrapper;
    }

    private JPanel getOptionColorChooser(String title, Color currentColor, TakeActionListener onSelection) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(this.mainController.getGuiColors().getGuiTextColor());
        wrapper.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

        TitleLabel titleLabel = new TitleLabel(title, TitleLabel.LEFT, 14, mainController.getGuiColors());
        titleLabel.setPreferredSize(new Dimension(154, 30));
        wrapper.add(titleLabel, BorderLayout.WEST);

        JPanel colorDisplay = new JPanel(new GridLayout(1, 1));
        colorDisplay.setBackground(currentColor);
        colorDisplay.setBorder(BorderFactory.createLineBorder(colorDisplay.getBackground().darker(), 1));
        colorDisplay.setPreferredSize(new Dimension(50, 28));

        wrapper.add(colorDisplay, BorderLayout.CENTER);

        MenuLabel changeColor = new MenuLabel(" change", MenuLabel.CENTER, 14, mainController.getGuiColors());
        changeColor.addMouseListener(new MouseAdapter() {
            Color x = currentColor;
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                Color newColor = JColorChooser.showDialog(null, "Choose a Color", colorDisplay.getBackground());
                if (newColor != null) {
                    mainController.getGuiColors().setGuiOptionColor(newColor);
                    colorDisplay.setBackground(newColor);
                    colorDisplay.setBorder(BorderFactory.createLineBorder(colorDisplay.getBackground().darker(), 1));
                    x = newColor;
                    onSelection.takeAction();
                }
            }
        });
        wrapper.add(changeColor, BorderLayout.EAST);

        return wrapper;
    }

    private JPanel getTextColorChooser(String title, Color currentColor, TakeActionListener onSelection) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(this.mainController.getGuiColors().getGuiTextColor());
        wrapper.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

        TitleLabel titleLabel = new TitleLabel(title, TitleLabel.LEFT, 14, mainController.getGuiColors());
        titleLabel.setPreferredSize(new Dimension(154, 30));
        wrapper.add(titleLabel, BorderLayout.WEST);

        JPanel colorDisplay = new JPanel(new GridLayout(1, 1));
        colorDisplay.setBackground(currentColor);
        colorDisplay.setBorder(BorderFactory.createLineBorder(colorDisplay.getBackground().darker(), 1));
        colorDisplay.setPreferredSize(new Dimension(50, 28));

        wrapper.add(colorDisplay, BorderLayout.CENTER);

        MenuLabel changeColor = new MenuLabel(" change", MenuLabel.CENTER, 14, mainController.getGuiColors());
        changeColor.addMouseListener(new MouseAdapter() {
            Color x = currentColor;
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                Color newColor = JColorChooser.showDialog(null, "Choose a Color", colorDisplay.getBackground());
                if (newColor != null) {
                    mainController.getGuiColors().setGuiTextColor(newColor);
                    colorDisplay.setBackground(newColor);
                    colorDisplay.setBorder(BorderFactory.createLineBorder(colorDisplay.getBackground().darker(), 1));
                    x = newColor;
                    onSelection.takeAction();
                }
            }
        });
        wrapper.add(changeColor, BorderLayout.EAST);

        return wrapper;
    }

    private JPanel getBackgroundColorChooser(String title, Color currentColor, TakeActionListener onSelection) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(this.mainController.getGuiColors().getGuiTextColor());
        wrapper.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

        TitleLabel titleLabel = new TitleLabel(title, TitleLabel.LEFT, 14, mainController.getGuiColors());
        titleLabel.setPreferredSize(new Dimension(154, 30));
        wrapper.add(titleLabel, BorderLayout.WEST);

        JPanel colorDisplay = new JPanel(new GridLayout(1, 1));
        colorDisplay.setBackground(currentColor);
        colorDisplay.setBorder(BorderFactory.createLineBorder(colorDisplay.getBackground().darker(), 1));
        colorDisplay.setPreferredSize(new Dimension(50, 28));

        wrapper.add(colorDisplay, BorderLayout.CENTER);

        MenuLabel changeColor = new MenuLabel(" change", MenuLabel.CENTER, 14, mainController.getGuiColors());
        changeColor.addMouseListener(new MouseAdapter() {
            Color x = currentColor;
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                Color newColor = JColorChooser.showDialog(null, "Choose a Color", colorDisplay.getBackground());
                if (newColor != null) {
                    mainController.getGuiColors().setGuiBackgroundColor(newColor);
                    colorDisplay.setBackground(newColor);
                    colorDisplay.setBorder(BorderFactory.createLineBorder(colorDisplay.getBackground().darker(), 1));
                    x = newColor;
                    onSelection.takeAction();
                }
            }
        });
        wrapper.add(changeColor, BorderLayout.EAST);

        return wrapper;
    }
}
