package DatabaseManager;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Created by Marco-Edoardo Palma.
 */

import Commons.*;
import java.util.List;

public class DatabaseManager
{
    private Connection dbCon;

    public DatabaseManager()
    {
        initDbCon();

        //init tables
        writeQuery(QueryComposer.CREATE_TABLES);
        writeQuery(QueryComposer.CREATE_INDEXES);
    }

    private synchronized void initDbCon()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
            
            // create a connection to the database
//            this.dbCon = DriverManager.getConnection("jdbc:sqlite:" + new PathsManager().getDB());

            this.dbCon = DriverManager.getConnection("jdbc:sqlite::memory:");
            
            this.dbCon.setAutoCommit(true);
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
            e.printStackTrace();
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
            stmt.close();
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
    
    public void writeQuery(List<String> list)
    {
        try
        {
            Statement statement = this.dbCon.createStatement();

            for (String q : list) 
                    statement.addBatch(q);
        
            statement.executeBatch();
            statement.close();
        } catch (Exception e)
        {
        }
    }

    public Connection getDbCon()
    {
        return dbCon;
    }
    
}