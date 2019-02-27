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

    private static String CREATE_TABLE_USERS
            = "CREATE TABLE IF NOT EXISTS USERS(\n"
            + "id VARCHAR(255) PRIMARY KEY NOT NULL,\n"
            + "gender VARCHAR(55),\n"
            + "age VARCHAR(55),\n"
            + "income VARCHAR(55)\n"
            + ");";

    private static String CREATE_TABLE_IMPRESSION_LOGS
            = "CREATE TABLE IF NOT EXISTS IMPRESSION_LOGS(\n"
            + "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n"
            + "userId VARCHAR(255),\n"
            + "date DATE,\n"
            + "context VARCHAR(255),\n"
            + "impressionCost NUMERIC,\n"
            + "FOREIGN KEY (userId) REFERENCES USERS(id)\n"
            + ");";

    private static String CREATE_TABLE_CLICK_LOGS
            = "CREATE TABLE IF NOT EXISTS CLICK_LOGS(\n"
            + "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + "userId VARCHAR(255),\n"
            + "date DATE,\n"
            + "clickCost NUMERIC,\n"
            + "FOREIGN KEY (userId) REFERENCES USERS(id)\n"
            + ");";

    private static String CREATE_TABLE_SERVER_LOGS
            = "CREATE TABLE IF NOT EXISTS SERVER_LOGS(\n"
            + "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n"
            + "userId VARCHAR(255),\n"
            + "entryDate DATE,\n"
            + "exitDate DATE,\n"
            + "pagesViewed INTEGER,\n"
            + "conversion VARCHAR(55),\n"
            + "FOREIGN KEY (userId) REFERENCES USERS(id)\n"
            + ");";

    private static String CREATE_TABLE_SETTINGS
            = "CREATE TABLE IF NOT EXISTS SETTINGS (\n"
            + "name VARCHAR(255) NOT NULL,\n"
            + "value TEXT,\n"
            + "PRIMARY KEY (name)\n"
            + ");";

    public static String[] CREATE_TABLES =
    {
        "PRAGMA foreign_keys = ON;",
        CREATE_TABLE_USERS,
        CREATE_TABLE_IMPRESSION_LOGS,
        CREATE_TABLE_CLICK_LOGS,
        CREATE_TABLE_SERVER_LOGS,
        CREATE_TABLE_SETTINGS
    };

    public static String[] CREATE_INDEXES =
    {
        "CREATE INDEX IF NOT EXISTS IMPRESSION_LOGS_DATE_INDEX ON IMPRESSION_LOGS (date);",
        "CREATE INDEX IF NOT EXISTS CLICK_LOGS_DATE_INDEX ON CLICK_LOGS (date);",
        "CREATE INDEX IF NOT EXISTS SERVER_LOGS_ENTRY_DATE_INDEX ON SERVER_LOGS (entryDate);",
        "CREATE INDEX IF NOT EXISTS SERVER_LOGS_EXIT_DATE_INDEX ON SERVER_LOGS (exitDate);"
    };

    public static String GETLASTID = "SELECT last_insert_rowid() as id";

    /*
        INSERT STATEMENTS
     */
    public static String insertUserStmt(UserEntry user)
    {
        return "INSERT INTO USERS VALUES (" + user.stringify()+ ");";
    }

    public static String insertImpressionStmt(ImpressionEntry ie)
    {
        return "INSERT INTO IMPRESSION_LOGS VALUES (" + ie.stringify() + ");";
    }

    public static String insertClickStmt(ClickEntry ce)
    {
        return "INSERT INTO CLICK_LOGS VALUES (" + ce.stringify() + ");";
    }

    public static String insertServerStmt(ServerEntry se)
    {
        return "INSERT INTO SERVER_LOGS VALUES (" + se.stringify() + ");";
    }

    public static String insertSettingStmt(String name, String value)//TODO check... looks shitty!
    {
        return "INSERT INTO SETTINGS VALUES ('" + name + "', '" + value + "');";
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
    public static String dropAllFrom_USERS = "DELETE FROM USERS;";
    public static String dropAllFrom_IMPRESSION_LOGS = "DELETE FROM IMPRESSION_LOGS;";
    public static String dropAllFrom_CLICK_LOGS = "DELETE FROM CLICK_LOGS;";
    public static String dropAllFrom_SERVER_LOGS = "DELETE FROM SERVER_LOGS;";
    public static String dropAllFrom_SETTINGS = "DELETE FROM SETTINGS;";
    public static String[] dropAll_noSettings =
    {
        dropAllFrom_USERS,
        dropAllFrom_IMPRESSION_LOGS,
        dropAllFrom_CLICK_LOGS,
        dropAllFrom_SERVER_LOGS
    };

    /*
        COUNT STATEMENTS
    */
    public static String countAllFrom_USERS = "SELECT COUNT(*) as c FROM USERS;";
    public static String countAllFrom_IMPRESSION_LOGS = "SELECT COUNT(*) as c FROM IMPRESSION_LOGS;";
    public static String countAllFrom_CLICK_LOGS = "SELECT COUNT(*) as c FROM CLICK_LOGS;";
    public static String countAllFrom_SERVER_LOGS = "SELECT COUNT(*) as c FROM SERVER_LOGS;";
    public static String countAllFrom_SETTINGS = "SELECT COUNT(*) as c FROM SETTINGS;";
    
    /*
        SELECT ALL STATEMENTS
     */
    public static String selectAllFrom_USERS = "SELECT * FROM USERS;";
    public static String selectAllFrom_IMPRESSION_LOGS = "SELECT * FROM IMPRESSION_LOGS;";
    public static String selectAllFrom_CLICK_LOGS = "SELECT * FROM CLICK_LOGS;";
    public static String selectAllFrom_SERVER_LOGS = "SELECT * FROM SERVER_LOGS;";
    public static String selectAllFrom_SETTINGS = "SELECT * FROM SETTINGS;";

    /*
        SELECT BY ID
     */
    public static String selectByIdFrom_USERS(String id)
    {
        return "SELECT * FROM USERS WHERE USERS.id='" + id + "' LIMIT 1;";
    }

    public static String selectByIdFrom_IMPRESSION_LOGS(int id)
    {
        return "SELECT * FROM IMPRESSION_LOGS WHERE IMPRESSION_LOGS.id=" + id + " LIMIT 1;";
    }

    public static String selectByIdFrom_CLICK_LOGS(int id)
    {
        return "SELECT * FROM CLICK_LOGS WHERE CLICK_LOGS.id=" + id + " LIMIT 1;";
    }

    public static String selectByIdFrom_SERVER_LOGS(int id)
    {
        return "SELECT * FROM SERVER_LOGS WHERE SERVER_LOGS.id=" + id + " LIMIT 1;";
    }

    /*
        SELECT BY userId
     */
    public static String selectByUserIdFrom_IMPRESSION_LOGS(String userId)
    {
        return "SELECT * FROM IMPRESSION_LOGS WHERE IMPRESSION_LOGS.userId='" + userId + "';";
    }

    public static String selectByUserIdFrom_CLICK_LOGS(String userId)
    {
        return "SELECT * FROM CLICK_LOGS WHERE CLICK_LOGS.userId='" + userId + "';";
    }

    public static String selectByUserIdFrom_SERVER_LOGS(String userId)
    {
        return "SELECT * FROM SERVER_LOGS WHERE SERVER_LOGS.userId='" + userId + "';";
    }

    public static String selectByNameFrom_SETTINGS(String name)
    {
        return "SELECT * FROM SETTINGS WHERE SETTINGS.name='" + name + "' LIMIT 1;";
    }
    /*
    Numer of impressions by week query.
     */

    public static String getNumberOfImpressionsPerWeek = "select count(impressionCost) as GroupedValues from impression_logs group by strftime('%W', Date);";
    /*
    Number of clicks
     */
}
