package Commons;

import Gui.GuiColors;

import java.awt.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class GraphSpecs {
    public enum TIME_SPAN {MONTH_SPAN, WEEK_SPAN, DAY_SPAN, HOUR_SPAN}

    public enum BOUNCE_DEF {TIME, NPAGES}

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
        BounceRate,
        ImpressionCost,
        ClickCost
    }

    private String title, xAxisName, yAxisName;
    private Collection<Tuple<String, Number>> data;

    private final METRICS metric;
    private final int campaignId;
    private final String campaignName;
    private final TIME_SPAN timespan;

    private final FilterSpecs filterSpecs;

    private final BOUNCE_DEF bounceDef;

    public GraphSpecs(int campaignId, String campaignName, METRICS metric, TIME_SPAN timespan, BOUNCE_DEF bounceDef, FilterSpecs filterSpecs) { // todo parameter for campaignId
        this.metric = metric;
        this.timespan = timespan;
        this.campaignId = campaignId;
        this.campaignName = campaignName;

        this.data = new LinkedList<>();

        this.filterSpecs = filterSpecs;

        this.bounceDef = bounceDef;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof GraphSpecs) {
            GraphSpecs other = (GraphSpecs) o;
            return (this.metric.equals(other.metric) && this.timespan.equals(other.timespan) && this.bounceDef.equals(other.bounceDef)) && filterSpecs.equals(((GraphSpecs) o).filterSpecs);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.metric) + Objects.hash(this.timespan) + Objects.hash(this.bounceDef);
    }

    public String getTitle() {
        return title;
    }

    public Collection<Tuple<String, Number>> getData() {
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
        if (getType() == TIME_SPAN.MONTH_SPAN) return GuiColors.OPTION_PURPLE;
        if (getType() == GraphSpecs.TIME_SPAN.WEEK_SPAN) return GuiColors.OPTION_GREENBLUE;
        else if (getType() == GraphSpecs.TIME_SPAN.DAY_SPAN) return GuiColors.OPTION_ORANGE;
        else return GuiColors.OPTION_GREEN;
    }

    public METRICS getMetric() {
        return metric;
    }

    public int getCampaignId() {
        return this.campaignId;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public TIME_SPAN getTimespan() {
        return timespan;
    }

    public List<Enums.Gender> getGenders() {
        return this.filterSpecs.getGenders();
    }

    public List<Enums.Age> getAges() {
        return this.filterSpecs.getAges();
    }

    public List<Enums.Income> getIncomes() {
        return this.filterSpecs.getIncomes();
    }

    public List<Enums.Context> getContexts() {
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

    public FilterSpecs getFilterSpecs() {
        return filterSpecs;
    }

    public BOUNCE_DEF getBounceDef() {
        return bounceDef;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setxAxisName(String xAxisName) {
        this.xAxisName = xAxisName;
    }

    public void setyAxisName(String yAxisName) {
        this.yAxisName = yAxisName;
    }

    public boolean containsFilters() {
        return (getGenders().size() > 0 ||
                getIncomes().size() > 0 ||
                getAges().size() > 0 ||
                getContexts().size() > 0);
    }
}
