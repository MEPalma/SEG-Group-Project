package DatabaseManager;
/*
 * Created by Marco-Edoardo Palma.
 */

//import Commons;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/*
 * This class will process queries from QueryComposer.java into DatabaseManager.
 * The 'interface' between frontend and backend
 */
public class DataExchange
{
    private DatabaseManager dbM;

    public DataExchange(DatabaseManager databaseManager)
    {
        this.dbM = databaseManager;
    }

    public String formatPrice(double price)
    {
        NumberFormat priceNumberFormat = NumberFormat.getCurrencyInstance(Locale.UK);
        return priceNumberFormat.format(price);
    }

    public static String formatPercentage(double percentage)
    {
        NumberFormat numberFormat = NumberFormat.getPercentInstance();
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);
        return numberFormat.format(percentage / 100);
    }

    public void close()
    {
        this.dbM.close();
    }
    
}
