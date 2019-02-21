package DatabaseManager;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.*;

/*
 * Created by Marco-Edoardo Palma.
 */

import Commons.*;
import java.time.chrono.ThaiBuddhistEra;

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
    
    public static void close(ResultSet resultSet)
    {
        try
        {
            resultSet.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    private int getLastID()
    {
        try
        {
            ResultSet resultSet = this.dbM.query(QueryComposer.GETLASTID);
            int c = resultSet.getInt("id");
            close(resultSet);
            return c;
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        System.exit(8);
        return -1;
    }
    
    /*
        INSERT STATEMENTS
    */
    
    public void insertUserStmt(UserEntry user)
    {
        this.dbM.writeQuery(QueryComposer.insertUserStmt(user));
        user.setId(this.getLastID());
    }
    
    public void insertImpressionStmt(ImpressionEntry ie)
    {
        this.dbM.writeQuery(QueryComposer.insertImpressionStmt(ie));
        ie.setId(this.getLastID());
    }
    
    public void insertClickStmt(ClickEntry ce)
    {
        this.dbM.writeQuery(QueryComposer.insertClickStmt(ce));
        ce.setId(this.getLastID());
    }
    
    public void insertServerStmt(ServerEntry se)
    {
        this.dbM.writeQuery(QueryComposer.insertServerStmt(se));
        se.setId(this.getLastID());
    }
    
    public void insertSettingStmt(String name, String value)
    {
        this.dbM.writeQuery(QueryComposer.insertSettingStmt(name, value));
    }
    
}
