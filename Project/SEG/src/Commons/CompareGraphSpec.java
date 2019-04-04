package Commons;

import java.util.List;

public class CompareGraphSpec {
    private final FilterSpecs filterSpecs;
    private final List<GraphSpecs> graphSpecs;
    private final String cardTitle, graphTitle, xAxis, yAxis;

    public CompareGraphSpec(FilterSpecs filterSpecs, List<GraphSpecs> graphSpecs, String cardTitle, String graphTitle, String xAxis, String yAxis) {
        this.filterSpecs = filterSpecs;
        this.graphSpecs = graphSpecs;
        this.cardTitle = cardTitle;
        this.graphTitle = graphTitle;
        this.xAxis = xAxis;
        this.yAxis = yAxis;
    }

    public FilterSpecs getFilterSpecs() {
        return filterSpecs;
    }

    public List<GraphSpecs> getGraphSpecs() {
        return graphSpecs;
    }

    public String getCardTitle() {
        return cardTitle;
    }

    public String getGraphTitle() {
        return graphTitle;
    }

    public String getxAxis() {
        return xAxis;
    }

    public String getyAxis() {
        return yAxis;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (o instanceof CompareGraphSpec) {
//            CompareGraphSpec tmp = (CompareGraphSpec) o;
//            return (filterSpecs == (tmp.filterSpecs) &&
//                    graphSpecs == (tmp.filterSpecs) &&
//                    cardTitle == (tmp.cardTitle) &&
//                    graphTitle == (tmp.graphTitle) &&
//                    xAxis == (tmp.xAxis) &&
//                    yAxis == (tmp.yAxis));
//        }
//        return false;
//    }
}
