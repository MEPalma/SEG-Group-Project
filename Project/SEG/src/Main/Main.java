package Main;

import Commons.FilterSpecs;
import Commons.GraphSpecs;
import Commons.UserEntry;
import DatabaseManager.QueryComposer;
import Gui.Gui;
import Gui.MainController;

import java.util.LinkedList;
import java.util.Locale;

/**
 * @author Group 31
 */
public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        //new Gui(new MainController()).setVisible(true);

        FilterSpecs filter= new FilterSpecs();
        filter.getGenders().add(UserEntry.Gender.Male);
        filter.getAges().add(UserEntry.Age.Age_25_34);
        GraphSpecs tmp = new GraphSpecs(
                "id",
                "title",
                "xAxis",
                "yAxis",
                GraphSpecs.METRICS.NumberImpressions,
                GraphSpecs.TIME_SPAN.DAY_SPAN,
                filter
        );

        System.out.println(QueryComposer.composeQuery(tmp));
    }
}