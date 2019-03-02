package Main;

import DatabaseManager.DataExchange;
import DatabaseManager.DatabaseManager;
import Gui.Gui;

import java.text.ParseException;
import java.util.Locale;

/**
 * @author Group 31
 */
public class Main {

    public static void main(String[] args) throws ParseException {
        Locale.setDefault(Locale.ENGLISH);
        new Gui(new DataExchange(new DatabaseManager())).setVisible(true);
    }

}
