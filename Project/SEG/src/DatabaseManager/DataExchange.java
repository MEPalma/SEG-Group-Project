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
import java.util.logging.Level;
import java.util.logging.Logger;

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
        PARSERS
    */
    
    private List<UserEntry> parseUserEntrys(ResultSet rset)
    {
        try
        {
            UserEntry maker = new UserEntry();
            
            List<UserEntry> users = new LinkedList<UserEntry>();
            while (rset.next())
                users.add((UserEntry) maker.parseDBContent(rset));

            return users;
        } catch (Exception e)
        {
            e.printStackTrace();
            return new LinkedList<UserEntry>();
        }
    }
    
    private List<ImpressionEntry> parseImpressionEntrys(ResultSet rset)
    {
        try
        {
            ImpressionEntry maker = new ImpressionEntry();
            
            List<ImpressionEntry> impressions = new LinkedList<ImpressionEntry>();
            while (rset.next())
                impressions.add((ImpressionEntry) maker.parseDBContent(rset));

            return impressions;
        } catch (Exception e)
        {
            e.printStackTrace();
            return new LinkedList<ImpressionEntry>();
        }
    }
    
    private List<ClickEntry> parseClickEntrys(ResultSet rset)
    {
        try
        {
            ClickEntry maker = new ClickEntry();
            
            List<ClickEntry> clicks = new LinkedList<ClickEntry>();
            while (rset.next())
                clicks.add((ClickEntry) maker.parseDBContent(rset));

            return clicks;
        } catch (Exception e)
        {
            e.printStackTrace();
            return new LinkedList<ClickEntry>();
        }
    }
    
    private List<ServerEntry> parseServerEntrys(ResultSet rset)
    {
        try
        {
            ServerEntry maker = new ServerEntry();
            
            List<ServerEntry> servers = new LinkedList<ServerEntry>();
            while (rset.next())
                servers.add((ServerEntry) maker.parseDBContent(rset));

            return servers;
        } catch (Exception e)
        {
            e.printStackTrace();
            return new LinkedList<ServerEntry>();
        }
    }
    
    private Map<String, String> parseSettings(ResultSet rset)
    {
        try
        {
            Map<String, String> settings = new HashMap<String, String>();
            while (rset.next())
                settings.put(rset.getString("name"), rset.getString("value"));

            return settings;
        } catch (Exception e)
        {
            e.printStackTrace();
            return new HashMap<String, String>();
        }
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

    /*
        UPDATE STATEMENTS
    */
    // if you need them call me up (Marco)
    
    
    /*
        DELETE STATEMENTS
    */
    // if you need them call me up (Marco)
    
    
    /*
        DROP ALL STATEMENTS
    */
    public void dropAllFrom_USERS()
    {
        this.dbM.writeQuery(QueryComposer.dropAllFrom_USERS);
    }
    
    public void dropAllFrom_IMPRESSION_LOGS()
    {
        this.dbM.writeQuery(QueryComposer.dropAllFrom_IMPRESSION_LOGS);
    }
    
    public void dropAllFrom_CLICK_LOGS()
    {
        this.dbM.writeQuery(QueryComposer.dropAllFrom_CLICK_LOGS);
    }
    
    public void dropAllFrom_SERVER_LOGS()
    {
        this.dbM.writeQuery(QueryComposer.dropAllFrom_SERVER_LOGS);
    }
    
    public void dropAllFrom_SETTINGS()
    {
        this.dbM.writeQuery(QueryComposer.dropAllFrom_SETTINGS);
    }
    
    public void dropAll_noSettings()
    {
        this.dbM.writeQuery(QueryComposer.dropAll_noSettings);
    }
    
    
    /*
        SELECT ALL STATEMENTS
    */
    
    public List<UserEntry> selectAllFrom_USERS()
    {
        ResultSet rset = this.dbM.query(QueryComposer.selectAllFrom_USERS);
        List tmp = parseUserEntrys(rset);
        close(rset);
        return tmp;
    }
    
    public List<ImpressionEntry> selectAllFrom_IMPRESSION_LOGS()
    {
        ResultSet rset = this.dbM.query(QueryComposer.selectAllFrom_IMPRESSION_LOGS);
        List tmp = parseImpressionEntrys(rset);
        close(rset);
        return tmp;
    }
    
    public List<ClickEntry> selectAllFrom_CLICK_LOGS()
    {
        ResultSet rset = this.dbM.query(QueryComposer.selectAllFrom_CLICK_LOGS);
        List tmp = parseClickEntrys(rset);
        close(rset);
        return tmp;
    }
    
    public List<ServerEntry> selectAllFrom_SERVER_LOGS()
    {
        ResultSet rset = this.dbM.query(QueryComposer.selectAllFrom_SERVER_LOGS);
        List tmp = parseServerEntrys(rset);
        close(rset);
        return tmp;
    }
    
    public Map<String, String> selectAllFrom_SETTINGS()
    {
        ResultSet rset = this.dbM.query(QueryComposer.selectAllFrom_SETTINGS);
        Map tmp = parseSettings(rset);
        close(rset);
        return tmp;
    }
    
    /*
        SELECT BY ID
    */
    
    public UserEntry selectByIdFrom_USERS(int id) 
    { 
        ResultSet rset = this.dbM.query(QueryComposer.selectByIdFrom_USERS(id));
        
        UserEntry maker = new UserEntry();
        maker = (UserEntry) maker.parseDBContent(rset);
        
        close(rset);
        return maker;
    }

    public ImpressionEntry selectByIdFrom_IMPRESSION_LOGS(int id)
    { 
        ResultSet rset = this.dbM.query(QueryComposer.selectByIdFrom_IMPRESSION_LOGS(id));
        
        ImpressionEntry maker = new ImpressionEntry();
        maker = (ImpressionEntry) maker.parseDBContent(rset);
        
        close(rset);
        return maker;
    }
    
    public ClickEntry selectByIdFrom_CLICK_LOGS(int id)
    { 
        ResultSet rset = this.dbM.query(QueryComposer.selectByIdFrom_CLICK_LOGS(id));
        
        ClickEntry maker = new ClickEntry();
        maker = (ClickEntry) maker.parseDBContent(rset);
        
        close(rset);
        return maker;
    }
    
    public ServerEntry selectByIdFrom_SERVER_LOGS(int id)
    {
        ResultSet rset = this.dbM.query(QueryComposer.selectByIdFrom_SERVER_LOGS(id));
        
        ServerEntry maker = new ServerEntry();
        maker = (ServerEntry) maker.parseDBContent(rset);
        
        close(rset);
        return maker;
    }
    
    
    /*
        SELECT BY userId
    */
    public List<ImpressionEntry> selectByUserIdFrom_IMPRESSION_LOGS(int userId)
    { 
        ResultSet rset = this.dbM.query(QueryComposer.selectByUserIdFrom_IMPRESSION_LOGS(userId));
        List tmp = parseImpressionEntrys(rset);
        close(rset);
        return tmp;
    }
    
    public List<ClickEntry> selectByUserIdFrom_CLICK_LOGS(int userId)
    { 
        ResultSet rset = this.dbM.query(QueryComposer.selectByUserIdFrom_CLICK_LOGS(userId));
        List tmp = parseClickEntrys(rset);
        close(rset);
        return tmp;
    }
    
    public List<ServerEntry> selectByUserIdFrom_SERVER_LOGS(int userId)
    { 
        ResultSet rset = this.dbM.query(QueryComposer.selectByUserIdFrom_SERVER_LOGS(userId));
        List tmp = parseServerEntrys(rset);
        close(rset);
        return tmp;
    }
    
    public String selectByNameFrom_SETTINGS(String name)
    {
        ResultSet resultSet = this.dbM.query(QueryComposer.selectByNameFrom_SETTINGS(name));
        try
        {
            return resultSet.getString("value");
        } catch (SQLException ex)
        {
            return null;
        }
    }
}
