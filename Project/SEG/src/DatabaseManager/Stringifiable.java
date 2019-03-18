package DatabaseManager;

import java.text.SimpleDateFormat;

public interface Stringifiable {
    /**
     * This is a SimpleDateFormat able to parse and format dates based on the requirements
     * of the csv files supplied to the program.
     */
    public final SimpleDateFormat globalDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    /**
     * This function should return the content of an object's insertion instance for a db.
     * For example: INSERT IN TABLE X VALUES (stringify());
     *
     * @return
     */
    public String stringify();
}
