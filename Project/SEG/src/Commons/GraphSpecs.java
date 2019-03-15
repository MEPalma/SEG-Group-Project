package Commons;

import Gui.GuiColors;

import java.awt.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class GraphSpecs {
    public enum TIME_SPAN { WEEK_SPAN, DAY_SPAN, HOUR_SPAN };

    public enum METRICS {
            NumberImpressions,
            NumberClicks,
            NumberUniques,
            NumberBounces,
            NumberConversions,
            TotalCost,
            CTR,
            CPA,
            CPC,
            CPM,
            BounceRate};

    private final String id, title, xAxisName, yAxisName;
    private Collection<Tuple<String, Number>> data;

    private final METRICS metric;
    private final TIME_SPAN timespan;

    private final FilterSpecs filterSpecs;

    public GraphSpecs(String id, String title, String xAxisName, String yAxisName, METRICS metric, TIME_SPAN timespan, FilterSpecs filterSpecs) {
        this.id = id;
        this.title = title;
        this.xAxisName = xAxisName;
        this.yAxisName = yAxisName;
        this.metric = metric;
        this.timespan = timespan;

        this.data = new LinkedList<>();

        this.filterSpecs = filterSpecs;
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

    public TIME_SPAN getType() {
        return timespan;
    }

    public Color getTypeColor() {
        if (getType() == GraphSpecs.TIME_SPAN.WEEK_SPAN) return GuiColors.OPTION_GREENBLUE;
        else if (getType() == GraphSpecs.TIME_SPAN.DAY_SPAN) return GuiColors.OPTION_ORANGE;
        else return GuiColors.OPTION_GREEN;
    }

    public METRICS getMetric() {
        return metric;
    }

    public TIME_SPAN getTimespan() {
        return timespan;
    }

    public List<UserEntry.Gender> getGenders() {
        return this.filterSpecs.getGenders();
    }

    public List<UserEntry.Age> getAges() {
        return this.filterSpecs.getAges();
    }

    public List<UserEntry.Income> getIncomes() {
        return this.filterSpecs.getIncomes();
    }

    public List<ImpressionEntry.Context> getContexts() {
        return this.filterSpecs.getContexts();
    }

    public String getStartDate() {
        return this.filterSpecs.getStartDate();
    }

    public String getEndDate() {
        return this.filterSpecs.getEndDate();
    }

    public void setData(Collection<Tuple<String, Number>> data) {
        this.data = data;
    }

}
