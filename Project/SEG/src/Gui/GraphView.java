package Gui;

import Commons.GraphSpecs;
import Gui.GraphManager.GraphManager;
import Gui.GuiComponents.*;
import Gui.Menus.ChooseNewGraphPanel;
import Gui.Menus.FiltersMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class GraphView extends RPanel {

    private final MainController mainController;
    private final List<GraphSpecs> graphsOnScreen;
    private Mode mode;

    public GraphView(MainController mainController) {
        super(GuiColors.BASE_SMOKE, new BorderLayout());
        this.mainController = mainController;
        this.graphsOnScreen = new LinkedList<GraphSpecs>();
        this.mode = Mode.CARD_MODE;
    }

    @Override
    public void refresh() {
        removeAll();

        //mode view chooser
        JPanel topMenuPanel = new JPanel(new BorderLayout());
        topMenuPanel.setPreferredSize(new Dimension(100, 50));
        topMenuPanel.setBackground(GuiColors.BASE_WHITE);
        topMenuPanel.setBorder(BorderFactory.createMatteBorder(0, 10, 0, 10, getBackground()));

        String modeText = "Cards";
        if (this.mode == Mode.GRID_MODE) modeText = "Grid";
        MenuLabel modeChooser = new MenuLabel(modeText, MenuLabel.LEFT, 16);
        modeChooser.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        modeChooser.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (mode == Mode.CARD_MODE)
                    mode = Mode.GRID_MODE;
                else mode = Mode.CARD_MODE;

                refresh();
            }
        });
        topMenuPanel.add(new TitleLabel("Graphs:", TitleLabel.LEFT, 16), BorderLayout.WEST);
        topMenuPanel.add(new TitleLabel("Organise in:", TitleLabel.RIGHT, 14), BorderLayout.CENTER);
        topMenuPanel.add(modeChooser, BorderLayout.EAST);

        add(topMenuPanel, BorderLayout.NORTH);

        //display
        if (this.graphsOnScreen.size() == 0) {
            setNoGraphMode();
        } else if (this.graphsOnScreen.size() == 1) {
            GraphSpecs ref = this.graphsOnScreen.get(0);
            JPanel tmp = GraphManager.createBarChar(ref);
            add(new GraphCardView(this, ref, tmp, true), BorderLayout.CENTER);
        } else if (this.mode == Mode.CARD_MODE) {
            if (this.graphsOnScreen.size() > 0) {
                List<Component> cards = new LinkedList<Component>();

                int i = 0;
                for (GraphSpecs g : this.graphsOnScreen) {
                    JPanel tmp = GraphManager.createBarChar(g);
                    cards.add(new GraphCardView(this, g, tmp, (i == (this.graphsOnScreen.size() - 1))));
                    i++;
                }

                add(new ListView(getBackground(), cards, false).getWrappedInScroll(true), BorderLayout.CENTER);
            }
        } else if (this.mode == Mode.GRID_MODE) {
            if (this.graphsOnScreen.size() > 0) {
                List<Component> rows = new LinkedList<Component>();

                for (int i = 0; i < this.graphsOnScreen.size(); i++) {
                    JPanel rowWrapper = new JPanel(new GridLayout(1, 2));
                    rowWrapper.setBackground(getBackground());
                    rowWrapper.setBorder(BorderFactory.createEmptyBorder());

                    GraphSpecs leftSpec = this.graphsOnScreen.get(i);
                    JPanel leftGraph = GraphManager.createBarChar(leftSpec);
                    rowWrapper.add(new GraphCardView(this, leftSpec, leftGraph, false));

                    if (i + 1 <= this.graphsOnScreen.size() - 1) {
                        GraphSpecs rightSpec = this.graphsOnScreen.get(i + 1);
                        JPanel rightGraph = GraphManager.createBarChar(rightSpec);
                        JPanel rightPanel = new GraphCardView(this, rightSpec, rightGraph, false);
                        rightPanel.setBorder(BorderFactory.createMatteBorder(24, 0, 24, 24, GuiColors.BASE_SMOKE));
                        rowWrapper.add(rightPanel);
                        i++;
                    }
                    rows.add(rowWrapper);
                }

                add(new ListView(getBackground(), rows, false).getWrappedInScroll(true), BorderLayout.CENTER);
            }
        }

        add(getBottomFunctionsPanel(), BorderLayout.SOUTH);

        repaint();
        revalidate();
    }

    private JPanel getBottomFunctionsPanel() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBorder(BorderFactory.createEmptyBorder());
        wrapper.setBackground(GuiColors.BASE_SMOKE);

        wrapper.add(getAddGraphMenuLabel(), BorderLayout.EAST);
        wrapper.add(getShowFiltersMenuLabel(), BorderLayout.WEST);

        return wrapper;
    }

    private JPanel getShowFiltersMenuLabel() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(GuiColors.BASE_WHITE);
        wrapper.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, GuiColors.BASE_SMOKE));
        wrapper.setPreferredSize(new Dimension(120, 60));

        MenuLabel menuLabel = new MenuLabel("Filters", MenuLabel.CENTER, 16);
        menuLabel.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, GuiColors.BASE_WHITE));
        menuLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JDialog dialog = new JDialog();
                dialog.setUndecorated(true);
                dialog.getContentPane().setLayout(new BorderLayout());

                dialog.addWindowFocusListener(new RecursiveLostFocus(dialog));
                dialog.getContentPane().add(new TitleLabel("Filters", TitleLabel.CENTER, 18), BorderLayout.NORTH);

                int dfWidth = 450;
                int dfHeight = 600;
                dialog.setSize(new Dimension(dfWidth, dfHeight));

                int centerXtmp = menuLabel.getLocationOnScreen().x + 10;
                int centerYtmp = menuLabel.getLocationOnScreen().y + 10 - dfHeight;
                dialog.setLocation(centerXtmp, centerYtmp);

                dialog.getContentPane().add(new FiltersMenu(mainController), BorderLayout.CENTER);
                dialog.setVisible(true);
            }
        });


        wrapper.add(menuLabel, BorderLayout.CENTER);

        return wrapper;
    }

    private JPanel getAddGraphMenuLabel() {

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(GuiColors.BASE_WHITE);
        wrapper.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, GuiColors.BASE_SMOKE));
        wrapper.setPreferredSize(new Dimension(120, 60));

        MenuLabel menuLabel = new MenuLabel("Add", MenuLabel.CENTER, 16);
        menuLabel.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, GuiColors.BASE_WHITE));
        menuLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JDialog dialog = new JDialog();
                dialog.setUndecorated(true);
                dialog.getContentPane().setLayout(new BorderLayout());
                dialog.addWindowFocusListener(new RecursiveLostFocus(dialog));
                dialog.getContentPane().add(new TitleLabel("Add new graph", TitleLabel.CENTER, 18), BorderLayout.NORTH);

                /*
                    setup dialog view
                 */
                JPanel dialogView = new JPanel(new BorderLayout());
                dialogView.setBackground(GuiColors.BASE_SMOKE);
                dialogView.setBorder(BorderFactory.createLineBorder(GuiColors.BASE_SMOKE, 10, true));
                dialogView.add(new ChooseNewGraphPanel(mainController), BorderLayout.CENTER);


                /*
                    display dialog
                 */
                int dfWidth = 300;
                int dfHeight = 320;
                dialog.setSize(new Dimension(dfWidth, dfHeight));

                int centerXtmp = menuLabel.getLocationOnScreen().x + 90 - dfWidth;
                int centerYtmp = menuLabel.getLocationOnScreen().y + 10 - dfHeight;
                dialog.setLocation(centerXtmp, centerYtmp);

                dialog.getContentPane().add(dialogView, BorderLayout.CENTER);
                dialog.setVisible(true);
            }
        });


        wrapper.add(menuLabel, BorderLayout.CENTER);
        return wrapper;
    }

    private void setNoGraphMode() {
        removeAll();
        TitleLabel message = new TitleLabel("CLICK ON ADD TO ADD A NEW GRAPH", TitleLabel.CENTER, 22);
        message.setForeground(GuiColors.TEXT_SELECTED);
        add(message, BorderLayout.CENTER);
        repaint();
        revalidate();
    }

    public void pushGraphSpecs(GraphSpecs newGraphSpecs) {
        this.graphsOnScreen.add(newGraphSpecs);

        refresh();
    }

    public void popGraphSpecs(GraphSpecs graphSpecs) {
        graphsOnScreen.remove(graphSpecs);
        refresh();
    }

    public boolean containsGraph(GraphSpecs graphSpecs) {
        return this.graphsOnScreen.contains(graphSpecs);
    }

    private int getGraphPosition(GraphSpecs graphSpecs) {
        int index = 0;

        for (GraphSpecs gs : this.graphsOnScreen) {
            if (gs.equals(graphSpecs)) {
                break;
            } else ++index;
        }

        return index;
    }

    public void moveGraphUp(GraphSpecs graphSpecs) {
        int index = getGraphPosition(graphSpecs);
        if (index > 0) {
            Collections.swap(this.graphsOnScreen, index, index - 1);
            refresh();
        }
    }

    public void moveGraphDown(GraphSpecs graphSpecs) {
        int index = getGraphPosition(graphSpecs);

        if (index < this.graphsOnScreen.size() - 1) {
            Collections.swap(this.graphsOnScreen, index, index + 1);
            refresh();
        }
    }

    public void updateGraphsData() {
        for (GraphSpecs gs : this.graphsOnScreen)
            gs.setData(mainController.getGraphSpecData(gs));
    }

    public enum Mode {CARD_MODE, GRID_MODE}
}

class GraphCardView extends RPanel {
    private GraphView host;

    public GraphCardView(GraphView host, GraphSpecs spec, JPanel graph, boolean isLast) {
        super(GuiColors.BASE_SMOKE, new BorderLayout());
        this.host = host;

        if (!isLast)
            setBorder(BorderFactory.createMatteBorder(24, 24, 0, 24, GuiColors.BASE_SMOKE));
        else setBorder(BorderFactory.createMatteBorder(24, 24, 24, 24, GuiColors.BASE_SMOKE));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(GuiColors.BASE_PRIME);
        topPanel.setPreferredSize(new Dimension(100, 50));

        JPanel functionsPanel = new JPanel(new GridLayout(1, 3, 4, 4));
        functionsPanel.setBackground(GuiColors.BASE_PRIME);
        functionsPanel.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 0, GuiColors.BASE_PRIME));

        MenuLabel moveUpLabel = new MenuLabel("up", MenuLabel.CENTER, 10);
//        moveUpLabel.setIcon(new ImageIcon(getClass().getResource("Icons/up.png")));
        moveUpLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                host.moveGraphUp(spec);
            }
        });
        functionsPanel.add(moveUpLabel);

        if (!isLast) {
            MenuLabel moveDownLabel = new MenuLabel("");
//            moveDownLabel.setIcon(new ImageIcon(getClass().getResource("/Icons/down.png")));
            moveDownLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    host.moveGraphDown(spec);
                }
            });
            functionsPanel.add(moveDownLabel);
        }

        MenuLabel deleteLabel = new MenuLabel("", MenuLabel.CENTER, 10);
//        deleteLabel.setIcon(new ImageIcon(getClass().getResource("Icons/x.png")));
        deleteLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                host.popGraphSpecs(spec);
            }
        });
        functionsPanel.add(deleteLabel);

        topPanel.add(functionsPanel, BorderLayout.EAST);

        TitleLabel titleLabel = new TitleLabel(spec.getTitle(), TitleLabel.CENTER, 16);
        titleLabel.setForeground(GuiColors.BASE_WHITE);
        topPanel.add(titleLabel, BorderLayout.CENTER);

        graph.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, GuiColors.BASE_WHITE));
        add(topPanel, BorderLayout.NORTH);
        add(graph, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        refresh();
    }

    @Override
    public void refresh() {
        int width = host.getWidth();
        setPreferredSize(new Dimension(width, width / 2));

        repaint();
        revalidate();
    }
}

