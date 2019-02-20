package DatabaseManager;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Created by Marco-Edoardo Palma.
 */

import Commons.*;

public class DatabaseManager
{
    private static Connection dbCon;

    public DatabaseManager()
    {
        initDbCon();

        //init tables
//        writeQuery(QueryComposer.CREATE_TABLES);
    }

    private synchronized void initDbCon()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
            // create a connection to the database
            this.dbCon = DriverManager.getConnection("jdbc:sqlite:" + new PathsManager().getDB());
        } catch (ClassNotFoundException e)
        {
            System.err.println("[ FAIL ] --> [DB NOT CONNECTED]:: " + e.getStackTrace());
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void close()
    {
        try
        {
            this.dbCon.close();
        } catch (SQLException e)
        {

        }
    }

    public synchronized ResultSet query(String query)
    {
        try
        {
            Statement stmt = this.dbCon.createStatement();
            return stmt.executeQuery(query);
        } catch (SQLException e)
        {
            System.out.println(query);
            e.printStackTrace();
        }
        return null;
    }

    public synchronized void writeQuery(String query)
    {
        try
        {
            Statement stmt = this.dbCon.createStatement();
            stmt.execute(query);
        } catch (SQLException e)
        {
            System.out.println(query);
            e.printStackTrace();
        }
    }

    public synchronized void writeQuery(String[] queries)
    {
        for (String query : queries)
            writeQuery(query);
    }
}