package DatabaseManager;
/*
 * Created by Marco-Edoardo Palma.
 */

import Commons.*;

import java.text.NumberFormat;
import java.util.Locale;

/*
 * This class is a container for all of the queries that can be made to the database.
 */
public class QueryComposer
{
    private static String CREATE_TABLE_USERS = "CREATE TABLE IF NOT EXISTS USERS(\n" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                                                "gender VARCHAR(55),\n" +
                                                "age VARCHAR(55),\n" +
                                                "income VARCHAR(55)\n" +
                                               ");";
    
    private static String CREATE_TABLE_IMPRESSION_LOGS = "CREATE TABLE IF NOT EXISTS IMPRESSION_LOGS(\n" +
                                                                "id INTEGER PRIMARY KEY AUTOINCREMENT,\n" + 
                                                                "userId INTEGER,\n" + 
                                                                "date VARCHAR(55),\n" +
                                                                "context VARCHAR(255),\n" + 
                                                                "impressionCost NUMERIC,\n" +
                                                                "FOREIGN KEY (userId) REFERENCES USERS(userId)\n" +
                                                            ");";
    
    private static String CREATE_TABLE_CLICK_LOGS = "CREATE TABLE IF NOT EXISTS CLICK_LOGS(\n" +
                                                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                        "userId INTEGER,\n" +
                                                        "date VARCHAR(55),\n" +
                                                        "clickCost NUMERIC,\n" +
                                                        "FOREIGN KEY (userId) REFERENCES USERS(userId)\n" +
                                                     ");";
    
    private static String CREATE_TABLE_SERVER_LOGS = "CREATE TABLE IF NOT EXISTS SERVER_LOGS(\n" +
                                                        "id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                                                        "userId INTEGER,\n" +
                                                        "entryDate VARCHAR(55),\n" +
                                                        "exitDate VARCHAR(55),\n" +
                                                        "pagesViewed INTEGER,\n" +
                                                        "conversion VARCHAR(55),\n" +
                                                        "FOREIGN KEY (userId) REFERENCES USERS(userId)\n" +
                                                      ");";
    
    private static String CREATE_TABLE_SETTINGS = "CREATE TABLE IF NOT EXISTS SETTINGS (\n" +
                                                    "name VARCHAR(255) PRIMARY KEY,\n" +
                                                    "value TEXT\n" +
                                                  ");";
    
    public static String[] CREATE_TABLES = {
                                            CREATE_TABLE_USERS,
                                            CREATE_TABLE_IMPRESSION_LOGS,
                                            CREATE_TABLE_CLICK_LOGS,
                                            CREATE_TABLE_SERVER_LOGS,
                                            CREATE_TABLE_SETTINGS,
                                            "PRAGMA foreign_keys = ON"
                                           };
    
    public static String[] CREATE_INDEXES= { "CREATE INDEX IF NOT EXISTS IMPRESSION_LOGS_DATE_INDEX ON IMPRESSION_LOGS (date);",
                                             "CREATE INDEX IF NOT EXISTS CLICK_LOGS_DATE_INDEX ON CLICK_LOGS (date);",
                                             "CREATE INDEX IF NOT EXISTS SERVER_LOGS_ENTRY_DATE_INDEX ON SERVER_LOGS (entryDate);",
                                             "CREATE INDEX IF NOT EXISTS SERVER_LOGS_EXIT_DATE_INDEX ON SERVER_LOGS (exitDate);"
                                           };
    
    /*
        INSERT STATEMENTS
    */
    public static String insertUserStmt(UserEntry user)
    {
        return "INSERT INTO USERS VALUES (" + user.getDBContent() + ");";
    }
    
    public static String insertImpressionStmt(ImpressionEntry ie)
    {
        return "INSERT INTO IMPRESSION_LOGS VALUES (" + ie.getDBContent() + ");";
    }
    
    public static String insertClickStmt(ClickEntry ce)
    {
        return "INSERT INTO CLICK_LOGS VALUES (" + ce.getDBContent() + ");";
    }
    
    public static String insertServerStmt(ServerEntry se)
    {
        return "INSERT INTO SERVER_LOGS VALUES (" + se.getDBContent() + ");";
    }
    
    public static String insertSettingStmt(String name, String value)//TODO check... looks shitty!
    {
        return "INSERT INTO SETTINGS VALUES ('" + name + "', '" + value + "');";
    }
}
