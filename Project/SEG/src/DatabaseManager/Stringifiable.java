package DatabaseManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public interface Stringifiable {
    /**
     * This is a SimpleDateFormat able to parse and format dates based on the requirements
     * of the csv files supplied to the program.
     */
    public final SimpleDateFormat globalDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static long dateToSeconds(String date) {
        try {
            return (Stringifiable.globalDateFormat.parse(date).getTime() / 1000);
        } catch (ParseException e) {
            return -1;
        }
    }
    public static long dateToSeconds(Date date) {
        return date.getTime() / 1000;
    }

    public static Date secondsToDate(long seconds) {
        return new Date(seconds * 1000);
    }

    public static String secondsToString(long seconds) {
        return globalDateFormat.format(secondsToDate(seconds));
    }



    /**
     * This function should return the content of an object's insertion instance for a db.
     * For example: INSERT IN TABLE X VALUES (stringify());
     *
     * @return
     */
    public String stringify();
}
