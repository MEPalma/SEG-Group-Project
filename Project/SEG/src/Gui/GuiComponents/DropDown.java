package Gui.GuiComponents;

import Gui.GuiColors;
import Gui.TakeActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

public class DropDown extends RPanel {
    private String[] choices, descriptions;
    private int selectedIndex;
    private TakeActionListener takeActionListener;
    private MenuLabel openPopupLabel;

    public DropDown(String[] choices, String[] descriptions, int selectedIndex) {
        super(GuiColors.BASE_WHITE, new BorderLayout());
        setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, GuiColors.BASE_SMOKE));

        this.choices = choices;
        this.descriptions = descriptions;
        this.selectedIndex = selectedIndex;
        this.openPopupLabel = new MenuLabel(this.choices[this.selectedIndex], MenuLabel.CENTER, 14);

        refresh();
    }

    public void addTakeActionListener(TakeActionListener takeActionListener) {
        this.takeActionListener = takeActionListener;
    }

    @Override
    public void refresh() {
        removeAll();

        MouseAdapter openListener = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                DropDownPopUp dropDownPopUp = new DropDownPopUp();
                dropDownPopUp.init(openPopupLabel.getLocationOnScreen().x,
                        openPopupLabel.getLocationOnScreen().y + openPopupLabel.getHeight(),
                        choices,
                        descriptions,
                        new TakeActionListener() {
                            @Override
                            public void takeAction() {
                                selectedIndex = dropDownPopUp.getSelectedIndex();
                                if (takeActionListener != null) takeActionListener.takeAction();
                                refresh();
                            }
                        }
                );
                dropDownPopUp.setVisible(true);
            }
        };

        openPopupLabel.setText(this.choices[this.selectedIndex]);
        openPopupLabel.addMouseListener(openListener);
        add(openPopupLabel, BorderLayout.CENTER);

        MenuLabel arrow = new MenuLabel("");
        arrow.setIcon(new ImageIcon(getClass().getResource("/Icons/down.png")));
        arrow.addMouseListener(openListener);
        add(arrow, BorderLayout.EAST);

        repaint();
        revalidate();
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }
}

class DropDownPopUp extends JDialog {
    public static int WIDTH = 250;

    private int x, y;
    private String[] choices, descriptions;
    private TakeActionListener takeActionListener;

    private int selectedIndex;

    private DropDownPopUpHelper helperRef;

    public DropDownPopUp() {
        super();
        setUndecorated(true);
        getContentPane().setBackground(GuiColors.BASE_WHITE);
        getContentPane().setLayout(new BorderLayout());
    }

    public void init(int x, int y, String[] choices, String[] descriptions, TakeActionListener takeActionListener) {
        this.x = x;
        this.y = y;
        this.choices = choices;
        this.descriptions = descriptions;
        this.takeActionListener = takeActionListener;

        this.helperRef = new DropDownPopUpHelper(x - DropDownPopUpHelper.WIDTH - 4, y);

        getContentPane().add(getDropDownChoices(), BorderLayout.CENTER);

        addWindowFocusListener(new RecursiveLostFocus(this));

//        addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseExited(MouseEvent e) {
//                helperRef.setVisible(false);
//            }
//        });
    }

    private JPanel getDropDownChoices() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(GuiColors.BASE_WHITE);
        wrapper.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

        List<Component> cells = new LinkedList<Component>();
        for (int i = 0; i < this.choices.length; ++i) {
            final int indexChoice = i;
            JPanel cell = new JPanel(new BorderLayout());
            cell.setBackground(GuiColors.BASE_WHITE);
            cell.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
            cell.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    selectedIndex = indexChoice;
                    if (takeActionListener != null)
                        takeActionListener.takeAction();
                    setVisible(false);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    cell.setBackground(GuiColors.BASE_SMOKE);
                    if (descriptions != null) {
                        if (descriptions[indexChoice] != null) {
                            helperRef.setText(descriptions[indexChoice]);
                            helperRef.setVisible(true);
                            helperRef.refresh();
                            helperRef.setFocusable(false);
                        }
                    } else helperRef.setVisible(false);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    cell.setBackground(GuiColors.BASE_WHITE);
                }
            });

            TitleLabel contentLabel = new TitleLabel(this.choices[i], TitleLabel.LEFT, 12);
            cell.add(contentLabel, BorderLayout.CENTER);

            cells.add(cell);
        }

        wrapper.add(new ListView(wrapper.getBackground(), cells, false).getWrappedInScroll(true), BorderLayout.CENTER);

        return wrapper;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    @Override
    public void setVisible(boolean b) {
        if (this.choices.length > 5)
            setSize(new Dimension(WIDTH, 200));
        else setSize(new Dimension(WIDTH, this.choices.length * 32));
        setLocation(x, y);
        if (!b) {
            this.helperRef.setVisible(false);
        }
        super.setVisible(b);
    }
}

class DropDownPopUpHelper extends JDialog {
    public static int WIDTH = 200;
    public static int HEIGHT = 130;

    private int x, y;
    private String text;
    private TitleLabel titleLabel;

    public DropDownPopUpHelper(int x, int y) {
        super();
        setUndecorated(true);

        this.x = x;
        this.y = y;

        this.text = "";
        this.titleLabel = new TitleLabel(this.text, TitleLabel.CENTER, 10);
        this.titleLabel.setForeground(GuiColors.DARK_GRAY);

        getContentPane().setBackground(GuiColors.BASE_WHITE);
        getContentPane().add(new TitleLabel("Info", TitleLabel.CENTER, 12), BorderLayout.NORTH);
        getContentPane().add(this.titleLabel, BorderLayout.CENTER);

        refresh();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void refresh() {
        this.titleLabel.setText("<html>" + this.text + "</html>");
        this.titleLabel.repaint();
        this.titleLabel.revalidate();
        repaint();
        revalidate();
    }

    @Override
    public void setVisible(boolean b) {
        setLocation(this.x, this.y);
        setSize(WIDTH, HEIGHT);
        super.setVisible(b);
    }
}