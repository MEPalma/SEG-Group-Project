package DatabaseManager;

import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseManager {
    private Connection dbCon;

    public DatabaseManager() {
        initDbCon();

        //init tables
        writeQuery(QueryComposer.CREATE_TABLES);
        writeQuery(QueryComposer.CREATE_INDEXES);
    }

    private synchronized void initDbCon() {
        try {
            Class.forName("org.sqlite.JDBC");

            // create a connection to the database
            this.dbCon = DriverManager.getConnection("jdbc:sqlite:" + new PathsManager().getDB());
//            this.dbCon = DriverManager.getConnection("jdbc:sqlite::memory:");

            this.dbCon.setAutoCommit(true);
        } catch (ClassNotFoundException e) {
            System.err.println("[ FAIL ] --> [DB NOT CONNECTED]:: " + e.getStackTrace());
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Closes the connection with the database.
     * Perform only before quitting the program.
     */
    public synchronized void close() {
        try {
            this.dbCon.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the ResultSet given by the query.
     * Queries submitted in this method are expected to return a ResultSet.
     *
     * @param query
     * @return
     */
    public synchronized ResultSet query(String query) {
        try {
            Statement stmt = this.dbCon.createStatement();
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            System.out.println(query);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Executes a query which does not return a ResultSet.
     *
     * @param query
     */
    public synchronized void writeQuery(String query) {
        try {
            Statement stmt = this.dbCon.createStatement();
            stmt.execute(query);
            stmt.close();
        } catch (SQLException e) {
            System.out.println(query);
            e.printStackTrace();
        }
    }

    /**
     * Executes an array of queries which do not return a ResultSet
     *
     * @param query
     */
    public synchronized void writeQuery(String[] queries) {
        for (String query : queries) {
            writeQuery(query);
        }
    }

    /**
     * Executes a list of queries which do not return a ResultSet
     *
     * @param query
     */
    public void writeQuery(List<String> list) {
        try {
            Statement statement = this.dbCon.createStatement();

            for (String q : list) {
                statement.addBatch(q);
            }

            statement.executeBatch();
            statement.close();
        } catch (Exception e) {
        }
    }

    /**
     * Returns the connection instance with the database
     *
     * @return
     */
    public Connection getDbCon() {
        return dbCon;
    }

}
