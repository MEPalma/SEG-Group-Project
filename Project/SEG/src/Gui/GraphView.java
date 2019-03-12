package Gui;

import Commons.Tuple;
import Gui.GraphManager.GraphManager;
import Gui.GuiComponents.ListView;
import Gui.GuiComponents.MenuLabel;
import Gui.GuiComponents.RPanel;
import Gui.GuiComponents.TitleLabel;
import org.jfree.chart.title.Title;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.*;

public class GraphView extends RPanel {
    public enum Mode {CARD_MODE, GRID_MODE}

    private Mode mode;
    private final MainController mainController;
    private final List<GraphSpecs> graphsOnScreen;

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
            JPanel tmp = GraphManager.createBarChar(ref.getData(), ref.getxAxisName(), ref.getyAxisName(), ref.getTypeColor());
            add(new GraphCardView(this, ref, tmp, true), BorderLayout.CENTER);
        } else if (this.mode == Mode.CARD_MODE) {
            if (this.graphsOnScreen.size() > 0) {
                List<Component> cards = new LinkedList<Component>();

                int i = 0;
                for (GraphSpecs g : this.graphsOnScreen) {
                    JPanel tmp = GraphManager.createBarChar(g.getData(), g.getxAxisName(), g.getyAxisName(), g.getTypeColor());
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
                    JPanel leftGraph = GraphManager.createBarChar(leftSpec.getData(), leftSpec.getxAxisName(), leftSpec.getyAxisName(), leftSpec.getTypeColor());
                    rowWrapper.add(new GraphCardView(this, leftSpec, leftGraph, false));

                    if (i + 1 <= this.graphsOnScreen.size() - 1) {
                        GraphSpecs rightSpec = this.graphsOnScreen.get(i + 1);
                        JPanel rightGraph = GraphManager.createBarChar(rightSpec.getData(), rightSpec.getxAxisName(), rightSpec.getyAxisName(), rightSpec.getTypeColor());
                        rowWrapper.add(new GraphCardView(this, rightSpec, rightGraph, false));
                        i++;
                    }
                    rows.add(rowWrapper);
                }

                add(new ListView(getBackground(), rows, false).getWrappedInScroll(true), BorderLayout.CENTER);
            }
        }

        add(getAddGraphMenuLabel(), BorderLayout.SOUTH);

        repaint();
        revalidate();
    }

    private JPanel getAddGraphMenuLabel() {

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(GuiColors.BASE_WHITE);
        wrapper.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, GuiColors.BASE_SMOKE));

        MenuLabel menuLabel = new MenuLabel("+", MenuLabel.CENTER, 26);
        menuLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JFrame dialog = new JFrame();
                dialog.setUndecorated(true);
                dialog.getContentPane().setLayout(new BorderLayout());
                dialog.addWindowFocusListener(new WindowAdapter() {
                    @Override
                    public void windowLostFocus(WindowEvent e) {
                        dialog.setVisible(false);
                    }
                });
                dialog.getContentPane().add(new TitleLabel("Add new graph", TitleLabel.CENTER, 18));

                /*
                    setup dialog view
                 */
                JPanel dialogView = new JPanel(new BorderLayout());
                dialogView.setBackground(GuiColors.BASE_WHITE);
                dialogView.setBorder(BorderFactory.createLineBorder(GuiColors.BASE_WHITE, 10, true));

                dialogView.add(new ChooseNewGraphPanel(mainController, dialog), BorderLayout.CENTER);


                /*
                    display dialog
                 */
                int dfWidth = 450;
                int dfHeight = 300;
                dialog.setSize(new Dimension(dfWidth, dfHeight));

                int centerXtmp = menuLabel.getLocationOnScreen().x + (menuLabel.getWidth() / 2) - (dfWidth / 2);
                int centerYtmp = menuLabel.getLocationOnScreen().y + (menuLabel.getHeight() / 2) - dfHeight;
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
        TitleLabel message = new TitleLabel("NO GRAPH SELECTED", TitleLabel.CENTER, 22);
        message.setForeground(GuiColors.TEXT_SELECTED);
        add(message, BorderLayout.CENTER);
        repaint();
        revalidate();
    }

    public void pushGraphSpecs(GraphSpecs newGraphSpecs) {
        this.graphsOnScreen.add(newGraphSpecs);

        refresh();
    }

    public void popGraphSpecs(String id) {
        GraphSpecs tmp = null;

        for (GraphSpecs gs : this.graphsOnScreen) {
            if (gs.getId().equals(id)) {
                tmp = gs;
                break;
            }
        }

        if (tmp != null)
            this.graphsOnScreen.remove(tmp);

        refresh();
    }

    public boolean containsGraph(String id) {
        Iterator it = this.graphsOnScreen.iterator();
        while (it.hasNext())
            if (((GraphSpecs) it.next()).getId().equals(id)) return true;

        return false;
    }

    private int getGraphPosition(String id) {
        int index = 0;

        for (GraphSpecs gs : this.graphsOnScreen) {
            if (gs.getId().equals(id)) {
                break;
            } else ++index;
        }

        return index;
    }

    public void moveGraphUp(String id) {
        int index = getGraphPosition(id);
        if (index > 0) {
            Collections.swap(this.graphsOnScreen, index, index - 1);
            refresh();
        }
    }

    public void moveGraphDown(String id) {
        int index = getGraphPosition(id);

        if (index < this.graphsOnScreen.size() - 1) {
            Collections.swap(this.graphsOnScreen, index, index + 1);
            refresh();
        }
    }
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
        topPanel.setBackground(spec.getTypeColor());
        topPanel.setPreferredSize(new Dimension(100, 50));

        JPanel functionsPanel = new JPanel(new GridLayout(1, 3, 4, 4));
        functionsPanel.setBackground(GuiColors.BASE_WHITE);
        functionsPanel.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 0, spec.getTypeColor()));

        MenuLabel moveUpLabel = new MenuLabel("up", MenuLabel.CENTER, 10);
        moveUpLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                host.moveGraphUp(spec.getId());
            }
        });
        functionsPanel.add(moveUpLabel);

        if (!isLast) {
            MenuLabel moveDownLabel = new MenuLabel("down", MenuLabel.CENTER, 10);
            moveDownLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    host.moveGraphDown(spec.getId());
                }
            });
            functionsPanel.add(moveDownLabel);
        }

        MenuLabel deleteLabel = new MenuLabel("x", MenuLabel.CENTER, 10);
        deleteLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                host.popGraphSpecs(spec.getId());
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

class GraphSpecs {
    public enum Type { WEEK_SPAN, DAY_SPAN, HOUR_SPAN };

    private final String id, title, xAxisName, yAxisName;
    private final Collection<Tuple<Number, Number>> data;
    private final Type type;

    public GraphSpecs(String id, String title, String xAxisName, String yAxisName, Collection data, Type type) {
        this.id = id;
        this.title = title;
        this.xAxisName = xAxisName;
        this.yAxisName = yAxisName;
        this.data = data;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GraphSpecs that = (GraphSpecs) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Collection getData() {
        return data;
    }

    public String getxAxisName() {
        return xAxisName;
    }

    public String getyAxisName() {
        return yAxisName;
    }

    public Type getType() {
        return type;
    }

    public Color getTypeColor() {
        if (getType() == GraphSpecs.Type.WEEK_SPAN) return GuiColors.OPTION_GREENBLUE;
        else if (getType() == GraphSpecs.Type.DAY_SPAN) return GuiColors.OPTION_ORANGE;
        else return GuiColors.OPTION_GREEN;
    }
}