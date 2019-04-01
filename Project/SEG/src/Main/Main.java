package Main;

import DatabaseManager.Stringifiable;
import Gui.Gui;

import java.text.ParseException;
import java.util.Date;
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