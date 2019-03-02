package Gui;

import Commons.Tuple;
import Gui.GraphManager.GraphManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

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

        if (this.mode == Mode.SINGLE_MODE) {
            if (this.graphsOnScreen.size() > 0) {
                GraphSpecs ref = this.graphsOnScreen.get(0);
                JPanel tmp = GraphManager.createChart(this, ref.getType(), ref.getData(), ref.getxAxisName(), ref.getyAxisName());
                add(tmp, BorderLayout.CENTER);
            } else {
                setNoGraphMode();
            }
        } else if (this.mode == Mode.CARD_MODE) {
            if (this.graphsOnScreen.size() > 0) {
                List<Component> cards = new LinkedList<Component>();
                for (GraphSpecs g : this.graphsOnScreen) {
                    JPanel tmp = GraphManager.createChart(this, g.getType(), g.getData(), g.getxAxisName(), g.getyAxisName());
                    cards.add(new GraphCardView(this, g, tmp));
                }

                add(new ListView(getBackground(), cards).getWrappedInScroll(true));
            } else {
                setNoGraphMode();
            }
        }

        repaint();
        revalidate();
    }

    private void setNoGraphMode()
    {
        removeAll();
        TitleLabel message = new TitleLabel("NO GRAPH SELECTED", TitleLabel.CENTER, 22);
        message.setForeground(GuiColors.TEXT_SELECTED);
        add(message, BorderLayout.CENTER);
        repaint();
        revalidate();
    }

    private void autoSetMode()
    {
        if (this.graphsOnScreen.size() <= 1) this.mode = Mode.SINGLE_MODE;
        if (this.graphsOnScreen.size() > 1) this.mode = Mode.CARD_MODE;
        //TODO grid
    }

    public synchronized void pushGraphSpecs(GraphSpecs newGraphSpecs) {
        this.graphsOnScreen.add(newGraphSpecs);

        autoSetMode();

        refresh();
    }

    public synchronized void popGraphSpecs(String id) {
        for (GraphSpecs gs : this.graphsOnScreen)
            if (gs.getId().equals(id))
                this.graphsOnScreen.remove(gs);

        autoSetMode();
        refresh();
    }

    public Mode getMode() {
        return mode;
    }

    public List<GraphSpecs> getGraphsOnScreen() {
        return graphsOnScreen;
    }
}

class GraphCardView extends RPanel {
    private GraphView host;
    public GraphCardView(GraphView host, GraphSpecs spec, JPanel graph) {
        super(GuiColors.BASE_LIGHT, new BorderLayout());
        this.host = host;

        setBorder(BorderFactory.createMatteBorder(10, 10, 0, 10, GuiColors.TEXT_UNSELECTED));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(getBackground());
        topPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, GuiColors.BASE_DARK));

        MenuLabel closeLabel = new MenuLabel("✕ ", MenuLabel.LEFT, 20);
        closeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                host.popGraphSpecs(spec.getId());
            }
        });

        topPanel.add(new TitleLabel(spec.getTitle(), TitleLabel.CENTER, 16), BorderLayout.WEST);
        topPanel.add(closeLabel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        add(graph, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        refresh();
    }

    @Override
    public void refresh()
    {
        int width = host.getWidth();
        setPreferredSize(new Dimension(width, width / 2));
        repaint();
        revalidate();
    }
}

class GraphSpecs {
    private final String id, title, xAxisName, yAxisName;
    private final GraphManager.ChartType type;
    private final Collection<Tuple<Number, Number>> data;

    public GraphSpecs(String id, String title, String xAxisName, String yAxisName, GraphManager.ChartType type, Collection data) {
        this.id = id;
        this.title = title;
        this.xAxisName = xAxisName;
        this.yAxisName = yAxisName;
        this.type = type;
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

    public GraphManager.ChartType getType() {
        return type;
    }

    public String getxAxisName() {
        return xAxisName;
    }

    public String getyAxisName() {
        return yAxisName;
    }
}