package Gui;

import Commons.Tuple;
import Gui.GraphManager.GraphManager;
import Gui.GuiComponents.ListView;
import Gui.GuiComponents.RPanel;
import Gui.GuiComponents.TitleLabel;

import javax.swing.*;
import java.awt.*;
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

        if (this.mode == Mode.SINGLE_MODE) {
            if (this.graphsOnScreen.size() > 0) {
                GraphSpecs ref = this.graphsOnScreen.get(0);
                JPanel tmp = GraphManager.createBarChar(ref.getData(), ref.getxAxisName(), ref.getyAxisName());
                add(new GraphCardView(this, ref, tmp, true), BorderLayout.CENTER);
            } else {
                setNoGraphMode();
            }
        } else if (this.mode == Mode.CARD_MODE) {
            if (this.graphsOnScreen.size() > 0) {
                List<Component> cards = new LinkedList<Component>();

                int i = 0;
                for (GraphSpecs g : this.graphsOnScreen) {
                    JPanel tmp = GraphManager.createBarChar(g.getData(), g.getxAxisName(), g.getyAxisName());
                    cards.add(new GraphCardView(this, g, tmp, (i == (this.graphsOnScreen.size() - 1))));
                    i++;
                }

                add(new ListView(getBackground(), cards, false).getWrappedInScroll(true));
            } else {
                setNoGraphMode();
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

    private void autoSetMode() {
        if (this.graphsOnScreen.size() <= 1) this.mode = Mode.SINGLE_MODE;
        if (this.graphsOnScreen.size() > 1) this.mode = Mode.CARD_MODE;
        //TODO grid
    }

    public void pushGraphSpecs(GraphSpecs newGraphSpecs) {
        this.graphsOnScreen.add(newGraphSpecs);

        autoSetMode();

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

        autoSetMode();
        refresh();
    }

    public Mode getMode() {
        return mode;
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