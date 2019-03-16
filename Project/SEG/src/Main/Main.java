package Main;

import Commons.FilterSpecs;
import Commons.GraphSpecs;
import Commons.UserEntry;
import DatabaseManager.QueryComposer;
import Gui.Gui;
import Gui.MainController;
import com.sun.corba.se.impl.orbutil.graph.Graph;

import java.util.Locale;

/**
 * @author Group 31
 */
public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        new Gui().setVisible(true);
    }
}