package Main;

import DatabaseManager.GraphSpecs;
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
//        new Gui(new MainController()).setVisible(true);
        GraphSpecs tmp = new GraphSpecs(
                "id",
                "title",
                "xAxis",
                "yAxis",
                new LinkedList(),
                GraphSpecs.METRICS.NumberImpressions,
                GraphSpecs.TIME_SPAN.DAY_SPAN,
                "2015-01-01 12:00:02",
                "2015-01-14 12:00:00",
                new LinkedList<>(),
                new LinkedList<>(),
                new LinkedList<>(),
                new LinkedList<>()
        );

        System.out.println(QueryComposer.composeQuery(tmp));
    }
}