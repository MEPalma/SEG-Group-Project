package DatabaseManager;

import Commons.*;

import java.util.LinkedList;
import java.util.List;

/*
 * This class is a container for all of the queries that can be made to the database.
 */
public class QueryComposer {

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
                    "CREATE INDEX IF NOT EXISTS IMPRESSION_LOGS_USERID_INDEX ON IMPRESSION_LOGS (userId);",

                    "CREATE INDEX IF NOT EXISTS CLICK_LOGS_DATE_INDEX ON CLICK_LOGS (date);",
                    "CREATE INDEX IF NOT EXISTS CLICK_LOGS_USERID_INDEX ON CLICK_LOGS (userId);",

                    "CREATE INDEX IF NOT EXISTS SERVER_LOGS_USERID_INDEX ON SERVER_LOGS (userId);",
                    "CREATE INDEX IF NOT EXISTS SERVER_LOGS_ENTRY_DATE_INDEX ON SERVER_LOGS (entryDate);",
                    "CREATE INDEX IF NOT EXISTS SERVER_LOGS_EXIT_DATE_INDEX ON SERVER_LOGS (exitDate);"
            };

    public static String GETLASTID = "SELECT last_insert_rowid() as id";

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

    public static String insertSettingStmt(String name, String value)
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
        Settigns
     */
    public static String setCampaignName(String name) {
        return "INSERT OR REPLACE INTO SETTINGS VALUES('campaignName', '" + name + "');";
    }

    public static String getCampaignName = "SELECT SETTINGS.VALUE AS v FROM SETTINGS WHERE SETTINGS.NAME='campaignName';";


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
        if (timeSpan == GraphSpecs.TIME_SPAN.WEEK_SPAN) return " group by strftime('%W', d)";
        else if (timeSpan == GraphSpecs.TIME_SPAN.DAY_SPAN)
            return " group by strftime('%d', d)";
        return " group by strftime('%H:%d', d)";

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

            StringBuilder tmp = new StringBuilder("select server_logs.EntryDate as d,count(strftime('%Y-%m-%d %H:%M:%S', ExitDate) - strftime('%Y-%m-%d %H:%M:%S', EntryDate)) as c from server_logs");
            tmp.append(getFilters(graphSpecs) + " And strftime('%Y-%m-%d %H:%M:%S', ExitDate) - strftime('%Y-%m-%d %H:%M:%S', EntryDate) <= 20  ");
            tmp.append(getTimeSpanGroup(graphSpecs.getTimespan()));

            tmp.append(";");
            return tmp.toString();
        } else {
            StringBuilder tmp = new StringBuilder("select server_logs.EntryDate as d, count(id) as c from server_logs ");
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

        tmp.append(getFilters(graphSpecs) + " And Conversion='Yes' ");
        tmp.append(getTimeSpanGroup(graphSpecs.getTimespan()));

        tmp.append(";");
        return tmp.toString();
    }

    private static String getFilters(GraphSpecs graphSpecs) {
        StringBuilder tmp = new StringBuilder("");
        List<String> filters = new LinkedList<>();
        filters.add("d > '" + graphSpecs.getStartDate() + "' ");
        filters.add("d < '" + graphSpecs.getEndDate() + "' ");
        if (graphSpecs.containsFilters()) {
            if (graphSpecs.getMetric() == GraphSpecs.METRICS.NumberImpressions || graphSpecs.getMetric() == GraphSpecs.METRICS.ImpressionCost) {
                tmp.append(" inner join Users on userid=Users.id ");
                //WHERE
                tmp.append("WHERE ");
            } else if (graphSpecs.getMetric() == GraphSpecs.METRICS.BounceRate || graphSpecs.getMetric() == GraphSpecs.METRICS.NumberConversions || graphSpecs.getMetric() == GraphSpecs.METRICS.NumberBounces) {
                tmp.append(" inner join Users on server_logs.userid=Users.id");
                tmp.append(" inner join impression_logs on server_logs.userid=impression_logs.userid");
                tmp.append(" WHERE ");
            } else {
                tmp.append(" inner join Users on click_logs.userid=Users.id ");
                tmp.append(" inner join impression_logs on click_logs.userid=impression_logs.userid  ");
                //WHERE
                tmp.append("WHERE ");
            }
            //gender
            tmp.append("");

            List<String> tmpGenders = new LinkedList<>();
            for (int i = 0; i < graphSpecs.getGenders().size(); ++i)
                tmpGenders.add("Gender = '" + graphSpecs.getGenders().get(i).toString() + "' ");

            StringBuilder tmpGenderBuilder = new StringBuilder("");
            for (int i = 0; i < tmpGenders.size(); ++i) {
                if (i == tmpGenders.size() - 1) {
                    tmpGenderBuilder.append(tmpGenders.get(i));
                } else tmpGenderBuilder.append(tmpGenders.get(i) + " or ");
            }
            if (tmpGenderBuilder.length() > 0)
                filters.add(tmpGenderBuilder.toString());

            tmpGenders.clear();

            //age

            for (int i = 0; i < graphSpecs.getAges().size(); ++i)
                tmpGenders.add("Age = '" + graphSpecs.getAges().get(i).toString() + "' ");

            StringBuilder tmpAgeBuilder = new StringBuilder("");
            for (int i = 0; i < tmpGenders.size(); ++i) {
                if (i == tmpGenders.size() - 1) {
                    tmpAgeBuilder.append(tmpGenders.get(i));
                } else tmpAgeBuilder.append(tmpGenders.get(i) + " or ");
            }
            if (tmpAgeBuilder.length() > 0)
                filters.add(tmpAgeBuilder.toString());

            tmpGenders.clear();


            //context
            tmp.append("");
            for (int i = 0; i < graphSpecs.getContexts().size(); ++i)
                tmpGenders.add("Context = '" + graphSpecs.getContexts().get(i).toString() + "' ");

            StringBuilder tmpContextBuilder = new StringBuilder("");
            for (int i = 0; i < tmpGenders.size(); ++i) {
                if (i == tmpGenders.size() - 1) {
                    tmpContextBuilder.append(tmpGenders.get(i));
                } else tmpContextBuilder.append(tmpGenders.get(i) + " or ");
            }
            if (tmpContextBuilder.length() > 0)
                filters.add(tmpContextBuilder.toString());

            tmpGenders.clear();

            //income
            for (int i = 0; i < graphSpecs.getIncomes().size(); ++i)
                tmpGenders.add("Income = '" + graphSpecs.getIncomes().get(i).toString() + "' ");

            StringBuilder tmpIncomeBuilder = new StringBuilder("");
            for (int i = 0; i < tmpGenders.size(); ++i) {
                if (i == tmpGenders.size() - 1) {
                    tmpIncomeBuilder.append(tmpGenders.get(i));
                } else tmpIncomeBuilder.append(tmpGenders.get(i) + " or ");
            }
            if (tmpIncomeBuilder.length() > 0)
                filters.add(tmpIncomeBuilder.toString());

            tmpGenders.clear();


        } else tmp.append(" WHERE ");

        for (int i = 0; i < filters.size(); ++i) {

            if (i == filters.size() - 1)
                tmp.append(filters.get(i));
            else tmp.append(filters.get(i) + " AND ");
        }
        return tmp.toString();
    }

    /*
    Number of impressions by week query.
     */

    public static String getNumberOfImpressionsPerWeek = "select Date as d,count(impressionCost) as c from impression_logs group by strftime('%W', Date) order by Date;";
    public static String getNumberOfImpressionsPerHour = "select Date as d,count(impressionCost) as c from impression_logs group by strftime('%H:%d', Date) order by Date;";
    public static String getNumberOfImpressionsPerDay = "select Date as d,count(impressionCost) as c from impression_logs group by strftime('%d', Date) order by Date;";

    /*
    Number of clicks
     */
    public static String getNumberOfClicksPerWeek = "select Date as d,count(ClickCost) as c from click_logs group by strftime('%W', Date) order by Date;";
    public static String getNumberOfClicksPerHour = "select Date as d,count(ClickCost) as c from click_logs group by strftime('%H:%d', Date) order by Date;";
    public static String getNumberOfClicksPerDay = "select Date as d,count(ClickCost) as c from click_logs group by strftime('%d', Date) order by Date;";

    /*
     *Number of uniques
     */
    public static String getNumberOfUniques = "select count (distinct ID) as GroupedValues from click_logs;";
    public static String getGetNumberOfUniquesPerDay = "select Date as d,count(distinct userid) as c from click_logs group by strftime('%d', Date) order by Date;";
    public static String getGetNumberOfUniquesPerWeek = "select Date as d,count(distinct id) as c from click_logs group by strftime('%W', Date) order by Date;";
    public static String getGetNumberOfUniquesPerHours = "select Date as d,count(distinct id) as c from click_logs group by strftime('%H:%d', Date) order by Date;";


    /*
    Number of Bounces
     */
    public static String getNumberOfBounces = "select count(strftime('%M', ExitDate)-strftime('%M', EntryDate)) as GroupedValues from server_logs where  strftime('%M', ExitDate)-strftime('%M', EntryDate)=0 AND PagesViewed=1;";
    public static String getNumberOfBouncesPerDay = "select EntryDate as d,count(strftime('%M', ExitDate)-strftime('%M', EntryDate)) as c from server_logs where  strftime('%M', ExitDate)-strftime('%M', EntryDate)=0 AND PagesViewed=1 group by strftime('%d',EntryDate) order by EntryDate;";
    public static String getNumberOfBouncesPerHour = "select EntryDate as d,count(strftime('%M', ExitDate)-strftime('%M', EntryDate)) as c from server_logs where  strftime('%M', ExitDate)-strftime('%M', EntryDate)=0 AND PagesViewed=1 group by strftime('%H:%d',EntryDate) order by EntryDate;";
    public static String getNumberOfBouncesPerWeek = "select EntryDate as d,count(strftime('%M', ExitDate)-strftime('%M', EntryDate)) as c from server_logs where  strftime('%M', ExitDate)-strftime('%M', EntryDate)=0 AND PagesViewed=1 group by strftime('%W',EntryDate);";


    /*
    Number of Conversions
     */
    public static String getNumberOfConversions = "select count(Conversion) as GroupedValues from server_logs where Conversion='No';";
    public static String getNumberOfConversionsPerHour = "select EntryDate as d, count(Conversion) as c from server_logs where Conversion='No' group by strftime('%H:%d',EntryDate) order by EntryDate;";
    public static String getNumberOfConversionsPerDay = "select EntryDate as d, count(Conversion) as c from server_logs where Conversion='No' group by strftime('%d',EntryDate) order by EntryDate;";
    public static String getNumberOfConversionsPerWeek = "select EntryDate as d, count(Conversion) as c from server_logs where Conversion='No' group by strftime('%W',EntryDate) order by EntryDate;";

    /*
    Total Cost
     */
    public static String getTotalCost = "select (select sum(impressionCost) from impression_logs) +(select sum(ClickCost) from click_logs) as GroupedValues;";
    public static String getTotalCostPerHour = "select d,sum(total) as c  from (select date as d,ClickCost as total from click_logs union all select date as d,impressionCost as total from impression_logs) as u group by  strftime('%H:%d',d) order by d;";
    public static String getTotalCostPerWeek = "select d,sum(total) as c from (select date as d,ClickCost as total from click_logs union all select date as d,impressionCost as total from impression_logs) as u group by  strftime('%W',d) order by d;";
    public static String getTotalCostPerDay = "select d,sum(total) as c from (select date as d,ClickCost as total from click_logs union all select date as d,impressionCost as total from impression_logs) as u group by  strftime('%d',d) order by d";
    /*
   Gets per hour--24 hours
    */
    public static String getTotalNumberOfImpressionsPerHour = "select count(impressionCost) as GroupedValues from impression_logs group by strftime('%H',Date);";

    /*
    CTR
     */
    public static String getCTRPerWeek = "SELECT date as d ,cast(count(date) AS FLOAT)/cast((SELECT count(date) FROM impression_logs group by  strftime('%W',date) order by date) AS FLOAT) as c FROM click_logs group by  strftime('%W',date) order by date;";
    public static String getCTRPerDay = "SELECT date as d ,cast(count(date) AS FLOAT)/cast((SELECT count(date) FROM impression_logs group by  strftime('%d',date) order by date) AS FLOAT) as c FROM click_logs group by  strftime('%d',date) order by date;";
    public static String getCTRPerHour = "SELECT date as d ,cast(count(date) AS FLOAT)/cast((SELECT count(date) FROM impression_logs group by  strftime('%H:%d',date) order by date) AS FLOAT) as c FROM click_logs group by  strftime('%H:%d',date) order by date;";

    /*
    Inner Join Number of Impressions
     */
    public static String getImpressionsInnerJoinWeek = "select Date as d,count(impressionCost) as c from impression_logs group by strftime('%W', Date) order by Date;";
    public static String getImpressionsInnerJoinHour = "select Date as d,count(impressionCost) as c from impression_logs group by strftime('%H:%d', Date) order by Date;";
    public static String getImpressionsInnerJoinDay = "select Date as d,count(impressionCost) as c from impression_logs group by strftime('%d', Date) order by Date;";

    public static String getStartDate = "select min(date) as d  from (select id, date from click_logs union all select id , date from impression_logs union all select id ,EntryDate from server_logs  ) as u ;";
    public static String getEndDate = "select Max(date) as d from (select id, date from click_logs union all select id , date from impression_logs union all select id ,EntryDate from server_logs  ) as u ;";
}