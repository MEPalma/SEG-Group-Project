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
                                                                "date DATE,\n" +
                                                                "context VARCHAR(255),\n" + 
                                                                "impressionCost NUMERIC\n" +
                                                                "FOREIGN KEY (userId) REFERENCES USERS(userId)\n" +
                                                            ");";
    
    private static String CREATE_TABLE_CLICK_LOGS = "CREATE TABLE IF NOT EXISTS CLICK_LOGS(\n" +
                                                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                        "userId INTEGER,\n" +
                                                        "date DATE,\n" +
                                                        "clickCost NUMERIC,\n" +
                                                        "FOREIGN KEY (userId) REFERENCES USERS(userId)\n" +
                                                     ");";
    
    private static String CRATE_TABLE_SERVER_LOGS = "CREATE TABLE IF NOT EXISTS SERVER_LOGS(\n" +
                                                        "id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                                                        "userId INTEGER,\n" +
                                                        "entryDate DATE,\n" +
                                                        "exitDate Date,\n" +
                                                        "pagesViewed INTEGER,\n" +
                                                        "conversion VARCHAR(55),\n" +
                                                        "FOREIGN KEY (userId) REFERENCES USERS(userId)\n" +
                                                     ");";
    
    private static String CREATE_SETTINGS = "CREATE TABLE IF NOT EXISTS Settings (\n" +
                                               "name VARCHAR(255) PRIMARY KEY,\n" +
                                               "value TEXT\n" +
                                             ");";
    
    private static String[] INDEX_IMPRESSIONS = { "CREATE INDEX IMPRESSION_LOGS_DATE_INDEX ON IMPRESSION_LOGS (date);" };
    private static String[] INDEX_CLICK_LOGS = { "CREATE INDEX CLICK_LOGS_DATE_INDEX ON CLICK_LOGS (date);" };
    private static String[] INDEX_SERVER_LOGS = { "CREATE INDEX SERVER_LOGS_ENTRY_DATE_INDEX ON SERVER_LOGS (entryDate);",
                                                  "CREATE INDEX SERVER_LOGS_EXIT_DATE_INDEX ON SERVER_LOGS (exitDate);"
                                                };
    
    
}
