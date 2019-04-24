package DatabaseManager;

import Commons.*;
import Gui.GuiColors;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/*
 * This class is a container for all of the queries that can be made to the database.
 */
public class QueryComposer {

    public static String[] CREATE_INDEXES =
            {
                    "CREATE INDEX IF NOT EXISTS IMPRESSION_LOGS_DATE_INDEX ON IMPRESSION_LOGS (date);",
                    "CREATE INDEX IF NOT EXISTS IMPRESSION_LOGS_USERID_INDEX ON IMPRESSION_LOGS (userId);",

                    "CREATE INDEX IF NOT EXISTS CLICK_LOGS_DATE_INDEX ON CLICK_LOGS (date);",
                    "CREATE INDEX IF NOT EXISTS CLICK_LOGS_USERID_INDEX ON CLICK_LOGS (userId);",

                    "CREATE INDEX IF NOT EXISTS SERVER_LOGS_USERID_INDEX ON SERVER_LOGS (userId);",
                    "CREATE INDEX IF NOT EXISTS SERVER_LOGS_ENTRY_DATE_INDEX ON SERVER_LOGS (entryDate);",
                    "CREATE INDEX IF NOT EXISTS SERVER_LOGS_EXIT_DATE_INDEX ON SERVER_LOGS (exitDate);"
            };
    public static String GETLASTID = "SELECT last_insert_rowid() as id;";
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
    public static String selectAllCampaigns = "SELECT * from CAMPAIGNS;";

    public static String insertNewCampaign(String name) {
        return "INSERT INTO CAMPAIGNS VALUES (NULL, '" + name + "');";
    }

    public static String updateCampaignName(int id, String newName) {
        return "UPDATE CAMPAIGNS SET campaignName='" + newName + "' WHERE id='" + id + "';";
    }

    public static String getCampaignName(int id) {
        return "SELECT CAMPAIGNS.campaignName AS n FROM CAMPAIGNS WHERE CAMPAIGNS.id='"+ id + "';";
    }

    /*
        Totals
    */
    // Impressions
    public static String getNumberOfImpressions = "select count (impressionCost) as c from impression_logs;";
    
    // Number of clicks
    public static String getNumberOfClicks = "select count(ClickCost) as c from click_logs;";
    
    // Number of uniques
    public static String getNumberOfUniques = "select count(distinct  click_logs.userid) as c from click_logs;";

    //Number of Bounces
    public static String getNumberOfBouncesByTime = "select count(strftime('%Y-%m-%d %H:%M:%S', ExitDate) - strftime('%Y-%m-%d %H:%M:%S', EntryDate)) as c from server_logs WHERE strftime('%Y-%m-%d %H:%M:%S', ExitDate) - strftime('%Y-%m-%d %H:%M:%S', EntryDate) <= 20;";
    public static String getNumberOfBouncesByPage = "select count(server_logs.id) as c from server_logs WHERE PagesViewed <= 1;";
    
    //Number of Conversions
    public static String getNumberOfConversions = "select count(Conversion) as c from server_logs WHERE Conversion='Yes';";
    
    //Total Cost
    public static String getTotalCost = "select (select sum(impressionCost) from impression_logs) + (select sum(ClickCost) from click_logs) as GroupedValues;";//TODO?
    
    //CTR
    public static String getCTR = "select count(userId) as c from impression_logs;";//TODO
    
    //CPA
    public static String getCPA = "select count(userId) as c from impression_logs;";//TODO
    
    //CPC
    public static String getCPC = "select count(userId) as c from impression_logs;";//TODO
    
    //CPM
    public static String getCPM = "select count(userId) as c from impression_logs;";//TODO
    
    //BounceRate
    public static String getBounceRate = "select count(userId) as c from impression_logs;";//TODO
    
    public static String getStartDate = "select min(date) as d  from (select id, date from click_logs union all select id , date from impression_logs union all select id ,EntryDate from server_logs  ) as u ;";
    public static String getEndDate = "select Max(date) as d from (select id, date from click_logs union all select id , date from impression_logs union all select id ,EntryDate from server_logs  ) as u ;";

    /*
    Number of impressions by week query.
     */
    private static String CAMPAIGNS
            = "CREATE TABLE IF NOT EXISTS CAMPAIGNS(\n"
            + "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n"
            + "campaignName VARCHAR(255) NOT NULL);";
    private static String CREATE_TABLE_USERS
            = "CREATE TABLE IF NOT EXISTS USERS(\n"
            + "id INTEGER NOT NULL,\n"
            + "campaignId INTEGER NOT NULL,\n"
            + "gender INTEGER,\n"
            + "age INTEGER,\n"
            + "income INTEGER,\n"
            + "FOREIGN KEY (campaignId) REFERENCES CAMPAIGNS(id) ON DELETE CASCADE,\n"
            + "PRIMARY KEY (id, campaignId)"
            + ");";
    private static String CREATE_TABLE_IMPRESSION_LOGS
            = "CREATE TABLE IF NOT EXISTS IMPRESSION_LOGS(\n"
            + "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n"
            + "userId INTEGER NOT NULL,\n"
            + "campaignId INTEGER NOT NULL,\n"
            + "date INTEGER,\n"
            + "context INTEGER,\n"
            + "impressionCost NUMERIC,\n"
            + "FOREIGN KEY (campaignId) REFERENCES CAMPAIGNS(id) ON DELETE CASCADE,\n"
            + "FOREIGN KEY (userId, campaignId) REFERENCES USERS(id, campaignId) ON DELETE CASCADE\n"
            + ");";
    private static String CREATE_TABLE_CLICK_LOGS
            = "CREATE TABLE IF NOT EXISTS CLICK_LOGS(\n"
            + "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + "userId INTEGER NOT NULL,\n"
            + "campaignId INTEGER NOT NULL,\n"
            + "date INTEGER,\n"
            + "clickCost NUMERIC,\n"
            + "FOREIGN KEY (campaignId) REFERENCES CAMPAIGNS(id) ON DELETE CASCADE,\n"
            + "FOREIGN KEY (userId, campaignId) REFERENCES USERS(id, campaignId) ON DELETE CASCADE\n"
            + ");";
    private static String CREATE_TABLE_SERVER_LOGS
            = "CREATE TABLE IF NOT EXISTS SERVER_LOGS(\n"
            + "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n"
            + "userId INTEGER NOT NULL,\n"
            + "campaignId INTEGER NOT NULL,\n"
            + "entryDate INTEGER,\n"
            + "exitDate INTEGER,\n"
            + "pagesViewed INTEGER,\n"
            + "conversion INTEGER,\n"
            + "FOREIGN KEY (campaignId) REFERENCES CAMPAIGNS(id) ON DELETE CASCADE,\n"
            + "FOREIGN KEY (userId, campaignId) REFERENCES USERS(id, campaignId) ON DELETE CASCADE\n"
            + ");";
    private static String CREATE_TABLE_SETTINGS
            = "CREATE TABLE IF NOT EXISTS SETTINGS (\n"
            + "name VARCHAR(255) NOT NULL,\n"
            + "value TEXT,\n"
            + "PRIMARY KEY (name)\n"
            + ");";
    private static String CREATE_TABLE_HOMEVIEW_CACHE
            = "CREATE TABLE IF NOT EXISTS HOMEVIEW_CACHE (\n"
            + "campaignId INTEGER NOT NULL,\n"
            + "nImpressions NUMERIC, \n"
            + "nClicks NUMERIC, \n"
            + "nUniques NUMERIC, \n"
            + "nBounces NUMERIC, \n"
            + "nConversions NUMERIC, \n"
            + "totalCost NUMERIC, \n"
            + "CTR NUMERIC, \n"
            + "CPA NUMERIC, \n"
            + "CPC NUMERIC, \n"
            + "CPM NUMERIC, \n"
            + "bounceRate_Pages NUMERIC, \n"
            + "bounceRate_Time NUMERIC, \n"
            + "FOREIGN KEY (campaignId) REFERENCES CAMPAIGNS(id) ON DELETE CASCADE\n"
            + ");";
    public static String[] CREATE_TABLES =
            {
                "PRAGMA foreign_keys=ON;",
                "PRAGMA secure_delete=OFF",
                CAMPAIGNS,
                CREATE_TABLE_USERS,
                CREATE_TABLE_IMPRESSION_LOGS,
                CREATE_TABLE_CLICK_LOGS,
                CREATE_TABLE_SERVER_LOGS,
                CREATE_TABLE_SETTINGS,
                CREATE_TABLE_HOMEVIEW_CACHE
            };

    public static String rebuildDatabase() {
        return "VACUUM;";
    }

    public static String[] deleteCampaign(int id) {
        return new String[] {
            "DELETE FROM CAMPAIGNS WHERE id = " + id + ";",
            rebuildDatabase()
        };
    }

    /*
        INSERT STATEMENTS
     */
    public static String insertUserStmt(UserEntry user) {
        return "INSERT INTO USERS VALUES (" + user.stringify() + ");";
    }

    public static String insertImpressionStmt(ImpressionEntry ie) {
        return "INSERT INTO IMPRESSION_LOGS VALUES (" + ie.stringify() + ");";
    }

    public static String insertClickStmt(ClickEntry ce) {
        return "INSERT INTO CLICK_LOGS VALUES (" + ce.stringify() + ");";
    }

    public static String insertServerStmt(ServerEntry se) {
        return "INSERT INTO SERVER_LOGS VALUES (" + se.stringify() + ");";
    }

    public static String insertSettingStmt(String name, String value) {
        return "INSERT INTO SETTINGS VALUES ('" + name + "', '" + value + "');";
    }

    public static String insertHomeViewCache(int id,
                                             Number nImpressions,
                                             Number nClicks,
                                             Number nUniques,
                                             Number nBounces,
                                             Number nConversions,
                                             Number totalCost,
                                             Number CTR,
                                             Number CPA,
                                             Number CPC,
                                             Number CPM,
                                             Number bounceRate_Pages,
                                             Number bounceRate_Time)
    {
        String sep = "' , '";
        return "INSERT INTO HOMEVIEW_CACHE VALUES ('"
                + id
                + sep + nImpressions.doubleValue()
                + sep + nClicks.doubleValue()
                + sep + nUniques.doubleValue()
                + sep + nBounces.doubleValue()
                + sep + nConversions.doubleValue()
                + sep + totalCost.doubleValue()
                + sep + CTR.doubleValue()
                + sep + CPA.doubleValue()
                + sep + CPC.doubleValue()
                + sep + CPM.doubleValue()
                + sep + bounceRate_Pages.doubleValue()
                + sep + bounceRate_Time.doubleValue()
                + "');";
    }

    /*
        SELECT BY ID
     */
    public static String selectByIdFrom_USERS(String id) {
        return "SELECT * FROM USERS WHERE USERS.id='" + id + "' LIMIT 1;";
    }

    public static String selectByIdFrom_IMPRESSION_LOGS(int id) {
        return "SELECT * FROM IMPRESSION_LOGS WHERE IMPRESSION_LOGS.id=" + id + " LIMIT 1;";
    }

    public static String selectByIdFrom_CLICK_LOGS(int id) {
        return "SELECT * FROM CLICK_LOGS WHERE CLICK_LOGS.id=" + id + " LIMIT 1;";
    }

    public static String selectByIdFrom_SERVER_LOGS(int id) {
        return "SELECT * FROM SERVER_LOGS WHERE SERVER_LOGS.id=" + id + " LIMIT 1;";
    }

    public static String selectByIdFrom_HOMEVIEW_CACHE(int id) {
        return "SELECT * FROM HOMEVIEW_CACHE WHERE HOMEVIEW_CACHE.campaignId=" + id + " LIMIT 1;";
    }

    /*
        SELECT BY userId
     */
    public static String selectByUserIdFrom_IMPRESSION_LOGS(String userId) {
        return "SELECT * FROM IMPRESSION_LOGS WHERE IMPRESSION_LOGS.userId='" + userId + "';";
    }

    public static String selectByUserIdFrom_CLICK_LOGS(String userId) {
        return "SELECT * FROM CLICK_LOGS WHERE CLICK_LOGS.userId='" + userId + "';";
    }

    public static String selectByUserIdFrom_SERVER_LOGS(String userId) {
        return "SELECT * FROM SERVER_LOGS WHERE SERVER_LOGS.userId='" + userId + "';";
    }

    public static String selectByNameFrom_SETTINGS(String name) {
        return "SELECT * FROM SETTINGS WHERE SETTINGS.name='" + name + "' LIMIT 1;";
    }

    /*
        Graphs
    */
    public static String composeQuery(GraphSpecs graphSpecs) {
        if (graphSpecs.getMetric() == GraphSpecs.METRICS.NumberImpressions) {
            return getNumberOfImpressions(graphSpecs);
        } else if (graphSpecs.getMetric() == GraphSpecs.METRICS.NumberClicks) {
            return getNumberOfClicks(graphSpecs);
        } else if (graphSpecs.getMetric() == GraphSpecs.METRICS.NumberConversions) {
            return getNumberOfConversions(graphSpecs);
        } else if (graphSpecs.getMetric() == GraphSpecs.METRICS.NumberUniques) {
            return getNumberOfUniques(graphSpecs);
        } else if (graphSpecs.getMetric() == GraphSpecs.METRICS.NumberBounces) {
            return getNumberOfBounces(graphSpecs);
        }
        return null;
    }

    public static List<String> composeSum(GraphSpecs graphSpecs) {
        List<String> tmpList = new LinkedList<>();
        tmpList.add(getClickCost(graphSpecs));
        tmpList.add(getImpressionCost(graphSpecs));
        return tmpList;
    }

    private static String getTimeSpanGroup(GraphSpecs.TIME_SPAN timeSpan) {
        if (timeSpan == GraphSpecs.TIME_SPAN.WEEK_SPAN) return " group by strftime('%W', d, 'unixepoch') order by d";
        else if (timeSpan == GraphSpecs.TIME_SPAN.DAY_SPAN)
            return " group by strftime('%d', d, 'unixepoch') order by d";
        else if (timeSpan == GraphSpecs.TIME_SPAN.HOUR_SPAN)
            return " group by strftime('%H:%d', d, 'unixepoch') order by d";
        return " group by strftime('%m', d, 'unixepoch') order by d";

    }

    private static String getNumberOfImpressions(GraphSpecs graphSpecs) {
        StringBuilder tmp = new StringBuilder("select Date as d,count(userId) as c from impression_logs ");
        tmp.append(getFilters(graphSpecs));
        tmp.append(getTimeSpanGroup(graphSpecs.getTimespan()));


        tmp.append(";");
        return tmp.toString();
    }

    private static String getNumberOfClicks(GraphSpecs graphSpecs) {
        StringBuilder tmp = new StringBuilder("select click_logs.Date as d,count(ClickCost) as c from click_logs");
        tmp.append(getFilters(graphSpecs));
        tmp.append(getTimeSpanGroup(graphSpecs.getTimespan()));

        tmp.append(";");
        return tmp.toString();
    }

    private static String getNumberOfUniques(GraphSpecs graphSpecs) {
        StringBuilder tmp = new StringBuilder("select click_logs.Date as d,count(distinct  click_logs.userid) as c from click_logs");
        tmp.append(getFilters(graphSpecs));
        tmp.append(getTimeSpanGroup(graphSpecs.getTimespan()));

        tmp.append(";");
        return tmp.toString();
    }

    private static String getClickCost(GraphSpecs graphSpecs) {
        StringBuilder tmp = new StringBuilder("select click_logs.date as d,sum(ClickCost) as c from click_logs ");
        tmp.append(getFilters((graphSpecs)));
        tmp.append(getTimeSpanGroup(graphSpecs.getTimespan()));
        tmp.append(";");
        return tmp.toString();
    }

    private static String getImpressionCost(GraphSpecs graphSpecs) {
        StringBuilder tmp = new StringBuilder("select Date as d,sum(impressionCost) as c from impression_logs ");
        tmp.append(getFilters((graphSpecs)));
        tmp.append(getTimeSpanGroup(graphSpecs.getTimespan()));
        tmp.append(";");
        return tmp.toString();
    }

    /*
    ADD AN AND FOR THIS ONE !
     */
    public static String getNumberOfBounces(GraphSpecs graphSpecs) {

        if (graphSpecs.getBounceDef() == GraphSpecs.BOUNCE_DEF.TIME) {

            StringBuilder tmp = new StringBuilder("select server_logs.EntryDate as d,count(strftime('%Y-%m-%d %H:%M:%S', ExitDate, 'unixepoch') - strftime('%Y-%m-%d %H:%M:%S', EntryDate, 'unixepoch')) as c from server_logs");
            tmp.append(getFilters(graphSpecs) + " And strftime('%Y-%m-%d %H:%M:%S', ExitDate, 'unixepoch') - strftime('%Y-%m-%d %H:%M:%S', EntryDate, 'unixepoch') <= 20  ");
            tmp.append(getTimeSpanGroup(graphSpecs.getTimespan()));

            tmp.append(";");
            return tmp.toString();
        } else {
            StringBuilder tmp = new StringBuilder("select server_logs.EntryDate as d, count(server_logs.id) as c from server_logs ");
            tmp.append(getFilters(graphSpecs) + " And PagesViewed<=1 ");
            tmp.append(getTimeSpanGroup(graphSpecs.getTimespan()));

            tmp.append(";");
            return tmp.toString();
        }

    }

    /*
    HERE AS WELL
     */
    private static String getNumberOfConversions(GraphSpecs graphSpecs) {
        StringBuilder tmp = new StringBuilder("select server_logs.EntryDate as d, count(Conversion) as c from server_logs ");

        tmp.append(getFilters(graphSpecs) + " And Conversion='1' ");
        tmp.append(getTimeSpanGroup(graphSpecs.getTimespan()));

        tmp.append(";");
        return tmp.toString();
    }

    private static String getFilters(GraphSpecs graphSpecs) {

        if (graphSpecs.getFilterSpecs() == null) return "";

        StringBuilder tmp = new StringBuilder();
        List<String> filters = new LinkedList<>();
        if (!graphSpecs.containsFilters())
            filters.add(" campaignId = '" + graphSpecs.getCampaignId() + "')");
        filters.add("d >= '" + Stringifiable.dateToSeconds(graphSpecs.getStartDate()) + "' )");
        filters.add("d <= '" + Stringifiable.dateToSeconds(graphSpecs.getEndDate()) + "' )");

        if (graphSpecs.containsFilters()) {
            if (graphSpecs.getMetric() == GraphSpecs.METRICS.NumberImpressions || graphSpecs.getMetric() == GraphSpecs.METRICS.ImpressionCost) {
                filters.add("users.campaignId = '" + graphSpecs.getCampaignId() + "')");
                tmp.append(" inner join Users on userid=Users.id and users.campaignId=impression_logs.campaignId ");
                //WHERE
                tmp.append("WHERE (");
            } else if (graphSpecs.getMetric() == GraphSpecs.METRICS.BounceRate || graphSpecs.getMetric() == GraphSpecs.METRICS.NumberConversions || graphSpecs.getMetric() == GraphSpecs.METRICS.NumberBounces) {
                filters.add("users.campaignId = '" + graphSpecs.getCampaignId() + "')");
                tmp.append(" inner join Users on server_logs.userid=Users.id and server_logs.campaignId=users.campaignId ");
                tmp.append(" inner join impression_logs on server_logs.userid=impression_logs.userid and server_logs.campaignId=impression_logs.campaignId ");
                tmp.append(" WHERE (");
            } else {
                filters.add("users.campaignId = '" + graphSpecs.getCampaignId() + "')");
                tmp.append(" inner join Users on click_logs.userid=Users.id and click_logs.campaignId=users.campaignId ");
                tmp.append(" inner join impression_logs on click_logs.userid=impression_logs.userid and click_logs.campaignId=impression_logs.campaignId  ");
                //WHERE
                tmp.append("WHERE (");
            }

            List<String> buffer = new LinkedList<>();

            //gender
            for (int i = 0; i < graphSpecs.getGenders().size(); ++i)
                buffer.add("Gender = '" + Enums.GenderToInt(graphSpecs.getGenders().get(i)) + "' ");

            StringBuilder tmpGenderBuilder = new StringBuilder("");
            for (int i = 0; i < buffer.size(); ++i) {
                if(i == buffer.size() - 1) {
                    tmpGenderBuilder.append(buffer.get(i));
                } else tmpGenderBuilder.append(buffer.get(i) + " or ");
            }
            if (tmpGenderBuilder.length() > 0) {
                tmpGenderBuilder.append(") ");
                filters.add(tmpGenderBuilder.toString());
            }

            buffer.clear();

            //age
            for (int i = 0; i < graphSpecs.getAges().size(); ++i)
                buffer.add("Age = '" + Enums.AgeToInt(graphSpecs.getAges().get(i)) + "' ");

            StringBuilder tmpAgeBuilder = new StringBuilder("");
            for (int i = 0; i < buffer.size(); ++i) {
                if (i == buffer.size() - 1) {
                    tmpAgeBuilder.append(buffer.get(i));
                } else tmpAgeBuilder.append(buffer.get(i) + " or ");
            }
            if (tmpAgeBuilder.length() > 0) {
                tmpAgeBuilder.append(") ");
                filters.add(tmpAgeBuilder.toString());
            }
            buffer.clear();


            //context
            for (int i = 0; i < graphSpecs.getContexts().size(); ++i)
                buffer.add("Context = '" + Enums.ContextToInt(graphSpecs.getContexts().get(i)) + "' ");

            StringBuilder tmpContextBuilder = new StringBuilder("");
            for (int i = 0; i < buffer.size(); ++i) {
                if (i == buffer.size() - 1) {
                    tmpContextBuilder.append(buffer.get(i));
                } else tmpContextBuilder.append(buffer.get(i) + " or ");
            }
            if (tmpContextBuilder.length() > 0) {
                tmpContextBuilder.append(")");
                filters.add(tmpContextBuilder.toString());
            }
            buffer.clear();

            //income
            for (int i = 0; i < graphSpecs.getIncomes().size(); ++i)
                buffer.add("Income = '" + Enums.IncomeToInt(graphSpecs.getIncomes().get(i)) + "' ");

            StringBuilder tmpIncomeBuilder = new StringBuilder("");
            for (int i = 0; i < buffer.size(); ++i) {
                if (i == buffer.size() - 1) {

                    tmpIncomeBuilder.append(buffer.get(i));
                } else tmpIncomeBuilder.append(buffer.get(i) + " or ");
            }
            if (tmpIncomeBuilder.length() > 0) {
                tmpIncomeBuilder.append(" ) ");
                filters.add(tmpIncomeBuilder.toString());
            }
            buffer.clear();


        } else tmp.append(" WHERE (");

        for (int i = 0; i < filters.size(); ++i) {

            if (i == filters.size() - 1)
                tmp.append(filters.get(i));
            else tmp.append(filters.get(i) + " AND (");
        }
        return tmp.toString();
    }


    /*
       Settings
    */
    public static String setGuiPrimeColor(Color color) {
        return "INSERT OR REPLACE INTO SETTINGS VALUES('GuiPrimeColor', '" + GuiColors.formatColor(color) + "');";
    }

    public static String setGuiOptionColor(Color color) {
        return "INSERT OR REPLACE INTO SETTINGS VALUES('GuiOptionColor', '" + GuiColors.formatColor(color) + "');";
    }

    public static String setGuiTextColor(Color color) {
        return "INSERT OR REPLACE INTO SETTINGS VALUES('GuiTextColor', '" + GuiColors.formatColor(color) + "');";
    }

    public static String setGuiBackgroundColor(Color color) {
        return "INSERT OR REPLACE INTO SETTINGS VALUES('GuiBackgroundColor', '" + GuiColors.formatColor(color) + "');";
    }

    public static String getGuiPrimeColor = "SELECT value FROM SETTINGS WHERE name='GuiPrimeColor';";
    public static String getGuiOptionColor = "SELECT value FROM SETTINGS WHERE name='GuiOptionColor';";
    public static String getGuiTextColor = "SELECT value FROM SETTINGS WHERE name='GuiTextColor';";
    public static String getGuiBackgroundColor = "SELECT value FROM SETTINGS WHERE name='GuiBackgroundColor';";

    public static String deleteHomeViewCache(int campaignId) {
        return "DELETE FROM HOMEVIEW_CACHE WHERE campaignId=" + campaignId + ";";
    }
}