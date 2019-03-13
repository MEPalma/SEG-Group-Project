package DatabaseManager;

import Commons.ImpressionEntry;
import Commons.Tuple;
import Commons.UserEntry;
import Gui.GuiColors;

import java.awt.*;
import java.util.Collection;
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
    private final Collection<Tuple<Number, Number>> data;

    private final METRICS metric;
    private final TIME_SPAN timespan;
    private final String startDate;
    private final String endDate;
    private final List<UserEntry.Gender> genders;
    private final List<UserEntry.Age> ages;
    private final List<UserEntry.Income> incomes;
    private final List<ImpressionEntry.Context> contexts;

    public GraphSpecs(String id, String title, String xAxisName, String yAxisName, Collection<Tuple<Number, Number>> data, METRICS metric, TIME_SPAN timespan, String startDate, String endDate, List<UserEntry.Gender> genders, List<UserEntry.Age> ages, List<UserEntry.Income> incomes, List<ImpressionEntry.Context> contexts) {
        this.id = id;
        this.title = title;
        this.xAxisName = xAxisName;
        this.yAxisName = yAxisName;
        this.data = data;
        this.startDate = startDate;
        this.endDate = endDate;
        this.metric = metric;
        this.timespan = timespan;
        this.genders = genders;
        this.ages = ages;
        this.incomes = incomes;
        this.contexts = contexts;
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
        return genders;
    }

    public List<UserEntry.Age> getAges() {
        return ages;
    }

    public List<UserEntry.Income> getIncomes() {
        return incomes;
    }

    public List<ImpressionEntry.Context> getContexts() {
        return contexts;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }
}
