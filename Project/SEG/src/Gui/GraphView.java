package Gui;

import Commons.Tuple;
import Gui.GraphManager.GraphManager;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.awt.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;

public class GraphView extends RPanel {
    public enum Mode {SINGLE_MODE, CARD_MODE, GRID_MODE}

    private Mode mode;
    private final List<GraphSpecs> graphsOnScreen;

    public GraphView() {
        super(Color.YELLOW, new BorderLayout());
        this.graphsOnScreen = new LinkedList<GraphSpecs>();
    }

    @Override
    public void refresh() {
        removeAll();

        if (this.mode == Mode.SINGLE_MODE)
        {
            if (this.graphsOnScreen.size() > 0)
            {
                GraphSpecs ref = this.graphsOnScreen.get(0);
                JPanel tmp = GraphManager.createChart(ref.getType(), ref.getData(), ref.getxAxisName(), ref.getyAxisName());
                add(tmp, BorderLayout.CENTER);
            }
            else
            {
                //TODO NO GRAPH MESSAGE TEXT IN THE MIDDLE
            }
        }
        else if (this.mode == Mode.CARD_MODE) {
            if (this.graphsOnScreen.size() > 0)
            {
                List<Component> cards = new LinkedList<Component>();
                for (GraphSpecs g : this.graphsOnScreen) {
                    JPanel tmp = GraphManager.createChart(g.getType(), g.getData(), g.getxAxisName(), g.getyAxisName());
                    cards.add(new GraphCardView(this, g, tmp));
                }

                add(new ListView(getBackground(), cards).getWrappedInScroll(true));
            }
            else
            {
                //TODO NO GRAPH MESSAGE TEXT IN THE MIDDLE
            }
        }

        repaint();
        revalidate();
    }

    public void popGraphSpecs(GraphSpecs graphSpecs)
    {
        this.graphsOnScreen.remove(graphSpecs);
        refresh();
    }
}

class GraphCardView extends JPanel
{
    public GraphCardView (GraphView host, GraphSpecs spec, JPanel graph)
    {
        super(new BorderLayout());
        setBackground(GuiColors.RED);
        setBorder(BorderFactory.createMatteBorder(10, 10, 0, 10, GuiColors.LIGHT));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(getBackground());
        topPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, GuiColors.DARK_RED));

        MenuLabel closeLabel = new MenuLabel("x", MenuLabel.CENTER, 14);
        closeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                host.popGraphSpecs(spec);
            }
        });

        topPanel.add(new TitleLabel(spec.getTitle(), TitleLabel.CENTER, 16), BorderLayout.WEST);
        topPanel.add(closeLabel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        add(graph, BorderLayout.CENTER);
    }
}

class GraphSpecs {
    private final String title, xAxisName, yAxisName;
    private final GraphManager.ChartType type;
    private final Collection<Tuple<Number, Number>> data;

    public GraphSpecs(String title, String xAxisName, String yAxisName, GraphManager.ChartType type, Collection data) {
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
        return title.equals(that.title) &&
                xAxisName.equals(that.xAxisName) &&
                yAxisName.equals(that.yAxisName) &&
                type == that.type &&
                data.equals(that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, xAxisName, yAxisName, type, data);
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