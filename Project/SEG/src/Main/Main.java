package Main;

import Gui.Gui;
import Gui.MainController;

import java.util.Locale;

/**
 * @author Group 31
 */
public class Main {

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        new Gui(new MainController()).setVisible(true);
    }

}
