package Gui;

import Commons.Tuple;
import Gui.GraphManager.GraphManager;
import Gui.GuiComponents.ListView;
import Gui.GuiComponents.MenuLabel;
import Gui.GuiComponents.RPanel;
import Gui.GuiComponents.TitleLabel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.*;

public class GraphView extends RPanel {
    public enum Mode {SINGLE_MODE, CARD_MODE, GRID_MODE}

    private Mode mode;
    private final List<GraphSpecs> graphsOnScreen;

    public GraphView() {
        super(GuiColors.LIGHT, new BorderLayout());
        this.graphsOnScreen = new LinkedList<GraphSpecs>();
        this.mode = Mode.CARD_MODE;
    }

    @Override
    public void refresh() {
        removeAll();

        //mode view chooser
        JPanel topMenuPanel = new JPanel(new BorderLayout());
        topMenuPanel.setPreferredSize(new Dimension(100, 51));
        topMenuPanel.setBackground(GuiColors.BASE_LIGHT);
        topMenuPanel.setBorder(BorderFactory.createMatteBorder(0, 10, 0, 10, getBackground()));

        String modeText = "Cards";
        if (this.mode == Mode.GRID_MODE) modeText = "Grid";
        MenuLabel modeChooser = new MenuLabel(modeText, MenuLabel.LEFT, 16);
        modeChooser.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        modeChooser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (mode == Mode.CARD_MODE)
                    mode = Mode.GRID_MODE;
                else mode = Mode.CARD_MODE;

                refresh();
            }
        });
        topMenuPanel.add(new TitleLabel("Graohs:", TitleLabel.LEFT, 16), BorderLayout.WEST);
        topMenuPanel.add(new TitleLabel("Organise in:", TitleLabel.RIGHT, 14), BorderLayout.CENTER);
        topMenuPanel.add(modeChooser, BorderLayout.EAST);

        add(topMenuPanel, BorderLayout.NORTH);

        //display
        if (this.graphsOnScreen.size() == 0) {
            setNoGraphMode();
        }
        else if (this.graphsOnScreen.size() == 1) {
            GraphSpecs ref = this.graphsOnScreen.get(0);
            JPanel tmp = GraphManager.createBarChar(ref.getData(), ref.getxAxisName(), ref.getyAxisName());
            add(new GraphCardView(this, ref, tmp, true), BorderLayout.CENTER);
        }
        else if (this.mode == Mode.CARD_MODE) {
            if (this.graphsOnScreen.size() > 0) {
                List<Component> cards = new LinkedList<Component>();

                int i = 0;
                for (GraphSpecs g : this.graphsOnScreen) {
                    JPanel tmp = GraphManager.createBarChar(g.getData(), g.getxAxisName(), g.getyAxisName());
                    cards.add(new GraphCardView(this, g, tmp, (i == (this.graphsOnScreen.size() - 1))));
                    i++;
                }

                add(new ListView(getBackground(), cards, false).getWrappedInScroll(true), BorderLayout.CENTER);
            }
        }
        else if (this.mode == Mode.GRID_MODE)
        {
            if (this.graphsOnScreen.size() > 0) {
                List<Component> rows = new LinkedList<Component>();

                for (int i = 0; i < this.graphsOnScreen.size(); i++)
                {
                    JPanel rowWrapper = new JPanel(new GridLayout(1, 2));
                    rowWrapper.setBackground(getBackground());
                    rowWrapper.setBorder(BorderFactory.createEmptyBorder());

                    GraphSpecs leftSpec = this.graphsOnScreen.get(i);
                    JPanel leftGraph = GraphManager.createBarChar(leftSpec.getData(), leftSpec.getxAxisName(), leftSpec.getyAxisName());
                    rowWrapper.add(new GraphCardView(this, leftSpec, leftGraph, false));

                    if (i + 1 <= this.graphsOnScreen.size() - 1)
                    {
                        GraphSpecs rightSpec = this.graphsOnScreen.get(i + 1);
                        JPanel rightGraph = GraphManager.createBarChar(rightSpec.getData(), rightSpec.getxAxisName(), rightSpec.getyAxisName());
                        rowWrapper.add(new GraphCardView(this, rightSpec, rightGraph, false));
                        i++;
                    }
                    rows.add(rowWrapper);
                }

                add(new ListView(getBackground(), rows, false).getWrappedInScroll(true), BorderLayout.CENTER);
            }
        }

        repaint();
        revalidate();
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
}

class GraphCardView extends RPanel {
    private GraphView host;

    public GraphCardView(GraphView host, GraphSpecs spec, JPanel graph, boolean isLast) {
        super(GuiColors.BASE_LIGHT, new BorderLayout());
        this.host = host;

        if (!isLast)
            setBorder(BorderFactory.createMatteBorder(24, 24, 0, 24, GuiColors.LIGHT));
        else setBorder(BorderFactory.createMatteBorder(24, 24, 24, 24, GuiColors.LIGHT));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(getBackground());
        topPanel.setPreferredSize(new Dimension(100, 50));

        topPanel.add(new TitleLabel(spec.getTitle(), TitleLabel.CENTER, 16), BorderLayout.CENTER);

        graph.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
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
    private final String id, title, xAxisName, yAxisName;
    private final Collection<Tuple<Number, Number>> data;

    public GraphSpecs(String id, String title, String xAxisName, String yAxisName, Collection data) {
        this.id = id;
        this.title = title;
        this.xAxisName = xAxisName;
        this.yAxisName = yAxisName;
        this.data = data;
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
}