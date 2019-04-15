package Gui;

import java.awt.*;
import java.text.ParseException;

public class GuiColors {
    public static Color DARK_GRAY = new Color(43, 43, 43);

//    public static Color TEXT_SELECTED = Color.BLACK;

    public static Color DEFAULT_BASE_PRIME = new Color(0, 134, 231);
    public static Color DEFAULT_BASE_OPTION = new Color(0, 167, 255);

    public static Color DEFAULT_TEXT = new Color(255, 255, 255);
    public static Color DEFAULT_BACKGROUND = new Color(236, 235, 235);

    public static Color RED_ERROR = new Color(242, 58, 48);

    public static Color OPTION_GREEN = new Color(59, 150, 42);
    public static Color OPTION_ORANGE = new Color(244, 112, 1);
    public static Color OPTION_GREENBLUE = new Color(0, 139, 125);
    public static Color OPTION_PURPLE = new Color(198, 4, 231);

    private Color guiPrimeColor, guiOptionColor, guiTextColor, guiBackgroundColor;

    public GuiColors() {
        this.guiPrimeColor = DEFAULT_BASE_PRIME;
        this.guiOptionColor = DEFAULT_BASE_OPTION;
        this.guiTextColor = DEFAULT_TEXT;
        this.guiBackgroundColor = DEFAULT_BACKGROUND;
    }

    public Color getGuiPrimeColor() {
        return guiPrimeColor;
    }

    public Color getGuiOptionColor() {
        return guiOptionColor;
    }

    public Color getGuiTextColor() {
        return guiTextColor;
    }

    public Color getGuiBackgroundColor() {
        return guiBackgroundColor;
    }

    public void setGuiPrimeColor(Color guiPrimeColor) {
        this.guiPrimeColor = guiPrimeColor;
    }

    public void setGuiOptionColor(Color guiOptionColor) {
        this.guiOptionColor = guiOptionColor;
    }

    public void setGuiTextColor(Color guiTextColor) {
        this.guiTextColor = guiTextColor;
    }

    public void setGuiBackgroundColor(Color guiBackgroundColor) {
        this.guiBackgroundColor = guiBackgroundColor;
    }

    public static Color parseFormattedColor(String value) throws ParseException {
        String[] rgb = value.split("/");

        try {
            return new Color(
                    Integer.parseInt(rgb[0]),
                    Integer.parseInt(rgb[1]),
                    Integer.parseInt(rgb[2])
            );
        } catch (Exception e) {
            throw new ParseException("", 0);
        }
    }

    public static String formatColor(Color color) {
        return color.getRed() + "/" + color.getGreen() + "/" + color.getBlue();
    }
}