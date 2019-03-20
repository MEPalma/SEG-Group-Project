package JUnitTests;

import Commons.ClickEntry;
import Commons.FilterSpecs;
import Commons.GraphSpecs;
import Commons.ImpressionEntry;
import Commons.ServerEntry;
import Commons.UserEntry;
import DatabaseManager.DataExchange;
import DatabaseManager.DatabaseManager;
import DatabaseManager.QueryComposer;
import org.junit.Test;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.junit.Assert;

import static org.junit.Assert.assertEquals;

class JunitTestCases {

    DataExchange dataExchange = new DataExchange(new DatabaseManager());

    @Test
    public void clickLogQueryTest() {

        String output = QueryComposer.selectByIdFrom_CLICK_LOGS(2);
        assertEquals("SELECT * FROM CLICK_LOGS WHERE CLICK_LOGS.id=2 LIMIT 1;", output);
    }

    @Test
    public void clickLogUserIdQueryTest() {
        String output = QueryComposer.selectByUserIdFrom_CLICK_LOGS("2");
        assertEquals("SELECT * FROM CLICK_LOGS WHERE CLICK_LOGS.userId='2';", output);
    }

    @Test
    void impressionLogQueryTest() {

        String output = QueryComposer.selectByIdFrom_IMPRESSION_LOGS(2);
        assertEquals("SELECT * FROM IMPRESSION_LOGS WHERE IMPRESSION_LOGS.id=2 LIMIT 1;", output);
    }

    @Test
    void impressionLogUserIdQueryTest() {

        String output = QueryComposer.selectByUserIdFrom_IMPRESSION_LOGS("2");
        assertEquals("SELECT * FROM IMPRESSION_LOGS WHERE IMPRESSION_LOGS.userId='2';", output);
    }

    @Test
    void serverLogQueryTest() {

        String output = QueryComposer.selectByIdFrom_SERVER_LOGS(2);
        assertEquals("SELECT * FROM SERVER_LOGS WHERE SERVER_LOGS.id=2 LIMIT 1;", output);
    }

    @Test
    void serverLogUserIDTest() {
        String output = QueryComposer.selectByUserIdFrom_SERVER_LOGS("2");
        assertEquals("SELECT * FROM SERVER_LOGS WHERE SERVER_LOGS.userId='2';", output);
    }

    void addUserContains() {
        List<UserEntry> insertions = new LinkedList<UserEntry>();

        for (int i = 0; i < 100; ++i) {
            UserEntry tmp = new UserEntry(Integer.toString(i), UserEntry.Gender.Male, UserEntry.Age.Age_25_34, UserEntry.Income.High);
            insertions.add(tmp);
            this.dataExchange.insertUserStmt(tmp);
        }

        List<UserEntry> result = this.dataExchange.selectAllFrom_USERS();

        for (int i = 0; i < insertions.size(); ++i)
            assertEquals(insertions.get(i).stringify(), result.get(i).stringify());

    }

    void addClickEntryContains() {
        List<ClickEntry> insertions = new LinkedList<ClickEntry>();

        for (int i = 0; i < 100; ++i) {
            ClickEntry tmp = new ClickEntry(ClickEntry.AUTO_INDEX, Integer.toString(i), new Date(), i);
            insertions.add(tmp);
            this.dataExchange.insertClickStmt(tmp);
        }

        List<ClickEntry> result = this.dataExchange.selectAllFrom_CLICK_LOGS();

        for (int i = 0; i < insertions.size(); ++i)
            assertEquals(insertions.get(i).stringify(), result.get(i).stringify());
    }

    void addImpressionEntryContains() {
        List<ImpressionEntry> insertions = new LinkedList<ImpressionEntry>();

        for (int i = 0; i < 100; ++i) {
            ImpressionEntry tmp = new ImpressionEntry(ImpressionEntry.AUTO_INDEX, Integer.toString(i), new Date(), ImpressionEntry.Context.Unknown, new Double(i));
            insertions.add(tmp);
            this.dataExchange.insertImpressionStmt(tmp);
        }

        List<ImpressionEntry> result = this.dataExchange.selectAllFrom_IMPRESSION_LOGS();

        for (int i = 0; i < insertions.size(); ++i)
            assertEquals(insertions.get(i).stringify(), result.get(i).stringify());
    }

    void addServerEntryContains() {
        List<ServerEntry> insertions = new LinkedList<ServerEntry>();

        for (int i = 0; i < 100; ++i) {
            ServerEntry tmp = new ServerEntry(ServerEntry.AUTO_INDEX, Integer.toString(i), new Date(), new Date(), new Double(i), ServerEntry.Conversion.Yes);
            insertions.add(tmp);
            this.dataExchange.insertServerStmt(tmp);
        }

        List<ServerEntry> result = this.dataExchange.selectAllFrom_SERVER_LOGS();

        for (int i = 0; i < insertions.size(); ++i)
            assertEquals(insertions.get(i).stringify(), result.get(i).stringify());
    }
    
   
    
    
    
    //TESTS FOR NUMBER OF IMPRESSIONS
     @Test
    void numberOfImpressionsWeekQueryTest() {
         //Test for Number of Impressions per week
        FilterSpecs filterSImpWeek = new FilterSpecs();
        filterSImpWeek.getAges().add(UserEntry.Age.Age_25_34);
        filterSImpWeek.getGenders().add(UserEntry.Gender.Male);
        filterSImpWeek.getIncomes().add(UserEntry.Income.Medium);
        
        GraphSpecs graphSImpWeek = new GraphSpecs(
                GraphSpecs.METRICS.NumberImpressions, 
                GraphSpecs.TIME_SPAN.WEEK_SPAN, 
                GraphSpecs.BOUNCE_DEF.NPAGES, 
                filterSImpWeek
        );
        
        String expImpWeek = "select Date as d,count(impressionCost) as c from impression_logs group by strftime('%W', Date) order by Date;";
        String resImpWeek = QueryComposer.composeQuery(graphSImpWeek).trim();
        
        //System.out.println(resImpWeek);
        
        String[] tokensExpImpWeek = expImpWeek.split(""); 
        String[] tokensResImpWeek = resImpWeek.split(" ");
        
         Assert.assertArrayEquals(tokensExpImpWeek, tokensResImpWeek);
    }
    
    
     @Test
    void numberOfImpressionsDayQueryTest() {
         //Test for Number of Impressions per day
        FilterSpecs filterSImpDay = new FilterSpecs();
        filterSImpDay.getAges().add(UserEntry.Age.Age_25_34);
        filterSImpDay.getGenders().add(UserEntry.Gender.Male);
        filterSImpDay.getIncomes().add(UserEntry.Income.Medium);
        
        GraphSpecs graphSImpDay = new GraphSpecs(
                GraphSpecs.METRICS.NumberImpressions, 
                GraphSpecs.TIME_SPAN.DAY_SPAN, 
                GraphSpecs.BOUNCE_DEF.NPAGES, 
                filterSImpDay
        );
        
        String expImpDay = "select Date as d,count(userId) as c from impression_logs  inner join Users on userid=Users.id WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  AND Gender = 'Male'  AND Age = 'Age_25_34'  AND Income = 'Medium'  group by strftime('%d', d);";
        String resImpDay = QueryComposer.composeQuery(graphSImpDay).trim();
        
        //System.out.println(resImpDay);
        
        String[] tokensExpImpDay = expImpDay.split(""); 
        String[] tokensResImpDay = resImpDay.split(" ");
        
         Assert.assertArrayEquals(tokensExpImpDay, tokensResImpDay);
    }
    
    
     @Test
    void numberOfImpressionsHourQueryTest() {
         //Test for Number of Impressions per hour
        FilterSpecs filterSImpWeek = new FilterSpecs();
        filterSImpWeek.getAges().add(UserEntry.Age.Age_25_34);
        filterSImpWeek.getGenders().add(UserEntry.Gender.Male);
        filterSImpWeek.getIncomes().add(UserEntry.Income.Medium);
        
        GraphSpecs graphSImpWeek = new GraphSpecs(
                GraphSpecs.METRICS.NumberImpressions, 
                GraphSpecs.TIME_SPAN.HOUR_SPAN, 
                GraphSpecs.BOUNCE_DEF.NPAGES, 
                filterSImpWeek
        );
        
        String expImpWeek = "select Date as d,count(userId) as c from impression_logs  inner join Users on userid=Users.id WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  AND Gender = 'Male'  AND Age = 'Age_25_34'  AND Income = 'Medium'  group by strftime('%H:%d', d);";
        String resImpWeek = QueryComposer.composeQuery(graphSImpWeek).trim();
        
        //System.out.println(resImpHour);
        
        String[] tokensExpImpWeek = expImpWeek.split(""); 
        String[] tokensResImpWeek = resImpWeek.split(" ");
        
         Assert.assertArrayEquals(tokensExpImpWeek, tokensResImpWeek);
    }
    
    
   
    
    
    
    
    
    
    
    // TESTS FOR NUMBER OF CLICKS
     @Test
    void numberOfClicksWeekQueryTest() {
         //Test for Number of Clicks per week
        FilterSpecs filterSClickWeek = new FilterSpecs();
        filterSClickWeek.getAges().add(UserEntry.Age.Age_35_44);
        filterSClickWeek.getGenders().add(UserEntry.Gender.Female);
        filterSClickWeek.getIncomes().add(UserEntry.Income.Low);
        
        GraphSpecs graphSClickWeek = new GraphSpecs(
                GraphSpecs.METRICS.NumberClicks, 
                GraphSpecs.TIME_SPAN.WEEK_SPAN, 
                GraphSpecs.BOUNCE_DEF.NPAGES, 
                filterSClickWeek
        );
        
        String expClickWeek = "select Date as d,count(impressionCost) as c from impression_logs group by strftime('%W', Date) order by Date;";
        String resClickWeek = QueryComposer.composeQuery(graphSClickWeek).trim();
        
        //System.out.println(resClicksWeek);
        
        String[] tokensExpClickWeek = expClickWeek.split(""); 
        String[] tokensResClickWeek = resClickWeek.split(" ");
        
         Assert.assertArrayEquals(tokensExpClickWeek, tokensResClickWeek);
    }
    
     @Test
    void numberOfClicksDayQueryTest() {
         //Test for Number of Clicks per day
        FilterSpecs filterSClickDay = new FilterSpecs();
        filterSClickDay.getAges().add(UserEntry.Age.Age_25_34);
        filterSClickDay.getGenders().add(UserEntry.Gender.Male);
        filterSClickDay.getIncomes().add(UserEntry.Income.High);
        
        GraphSpecs graphSClickDay = new GraphSpecs(
                GraphSpecs.METRICS.NumberClicks, 
                GraphSpecs.TIME_SPAN.DAY_SPAN, 
                GraphSpecs.BOUNCE_DEF.NPAGES, 
                filterSClickDay
        );
        
        String expClickDay = "select click_logs.Date as d,count(ClickCost) as c from click_logs inner join Users on click_logs.userid=Users.id  inner join impression_logs on click_logs.userid=impression_logs.userid  WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  AND Gender = 'Female'  AND Age = 'Age_35_44'  AND Income = 'High'  group by strftime('%d', d);";
        String resClickDay = QueryComposer.composeQuery(graphSClickDay).trim();
        
        //System.out.println(resClickDay);
        
        String[] tokensExpClickDay = expClickDay.split(""); 
        String[] tokensResClickDay = resClickDay.split(" ");
        
         Assert.assertArrayEquals(tokensExpClickDay, tokensResClickDay);
    }
    
    void numberOfClicksHourQueryTest() {
         //Test for Number of Clicks per hour
        FilterSpecs filterSClicksHour = new FilterSpecs();
        filterSClicksHour.getAges().add(UserEntry.Age.Age_less_than_25);
        filterSClicksHour.getGenders().add(UserEntry.Gender.Female);
        filterSClicksHour.getIncomes().add(UserEntry.Income.Low);
        
        GraphSpecs graphSClicksHour = new GraphSpecs(
                GraphSpecs.METRICS.NumberClicks, 
                GraphSpecs.TIME_SPAN.HOUR_SPAN, 
                GraphSpecs.BOUNCE_DEF.NPAGES, 
                filterSClicksHour
        );
        
        String expClicksHour = "select click_logs.Date as d,count(ClickCost) as c from click_logs inner join Users on click_logs.userid=Users.id  inner join impression_logs on click_logs.userid=impression_logs.userid  WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  AND Gender = 'Female'  AND Age = 'Age_less_than_25'  AND Income = 'Low'  group by strftime('%H:%d', d);";
        String resClicksHour = QueryComposer.composeQuery(graphSClicksHour).trim();
        
        //System.out.println(resClickHour);
        
        String[] tokensExpClicksHour = expClicksHour.split(""); 
        String[] tokensResClicksHour = resClicksHour.split(" ");
        
         Assert.assertArrayEquals(tokensExpClicksHour, tokensResClicksHour);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    //TESTS FOR NUMBER OF UNIQUES
    void numberOfUniquesWeekQueryTest() {
         //Test for Number of Uniques per week
        FilterSpecs filterSUniquesWeek = new FilterSpecs();
        filterSUniquesWeek.getAges().add(UserEntry.Age.Age_less_than_25);
        filterSUniquesWeek.getGenders().add(UserEntry.Gender.Female);
        filterSUniquesWeek.getIncomes().add(UserEntry.Income.Low);
        
        GraphSpecs graphSUniquesWeek = new GraphSpecs(
                GraphSpecs.METRICS.NumberUniques, 
                GraphSpecs.TIME_SPAN.WEEK_SPAN, 
                GraphSpecs.BOUNCE_DEF.NPAGES, 
                filterSUniquesWeek
        );
        
        String expUniquesWeek = "select click_logs.Date as d,count(distinct  userid) as c from click_logs WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  group by strftime('%W', d);";
        String resUniquesWeek = QueryComposer.composeQuery(graphSUniquesWeek).trim();
        
        //System.out.println(resUniquesWeek);
        
        String[] tokensExpUniquesWeek = expUniquesWeek.split(""); 
        String[] tokensResUniquesWeek = resUniquesWeek.split(" ");
        
         Assert.assertArrayEquals(tokensExpUniquesWeek, tokensResUniquesWeek);
    }
    
     void numberOfUniquesDayQueryTest() {
         //Test for Number of Uniques per day
        FilterSpecs filterSUniquesDay = new FilterSpecs();
       
        
        GraphSpecs graphSUniquesDay = new GraphSpecs(
                GraphSpecs.METRICS.NumberUniques, 
                GraphSpecs.TIME_SPAN.DAY_SPAN, 
                GraphSpecs.BOUNCE_DEF.NPAGES, 
                filterSUniquesDay
        );
        
        String expUniquesDay = "select click_logs.Date as d,count(distinct  userid) as c from click_logs WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  group by strftime('%d', d);";
        String resUniquesDay = QueryComposer.composeQuery(graphSUniquesDay).trim();
        
        //System.out.println(resUniquesDay);
        
        String[] tokensExpUniquesDay = expUniquesDay.split(""); 
        String[] tokensResUniquesDay = resUniquesDay.split(" ");
        
         Assert.assertArrayEquals(tokensExpUniquesDay, tokensResUniquesDay);
    }
     
     void numberOfUniquesHourQueryTest() {
         //Test for Number of Uniques per hour
        FilterSpecs filterSUniquesHour = new FilterSpecs();
       
        
        GraphSpecs graphSUniquesHour = new GraphSpecs(
                GraphSpecs.METRICS.NumberUniques, 
                GraphSpecs.TIME_SPAN.HOUR_SPAN, 
                GraphSpecs.BOUNCE_DEF.NPAGES, 
                filterSUniquesHour
        );
        
        String expUniquesHour = "select click_logs.Date as d,count(distinct  userid) as c from click_logs WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  group by strftime('%H:%d', d);";
        String resUniquesHour = QueryComposer.composeQuery(graphSUniquesHour).trim();
        
        //System.out.println(resUniquesHour);
        
        String[] tokensExpUniquesHour = expUniquesHour.split(""); 
        String[] tokensResUniquesHour = resUniquesHour.split(" ");
        
         Assert.assertArrayEquals(tokensExpUniquesHour, tokensResUniquesHour);
    }
     
     
    
     
     
     
     
     
    // TESTS FOR NUMBER OF BOUNCES PAGES VIEWED
    void numberOfBouncesPagesViewedWeekQueryTest() {
       //Test for Number of Bounces (Pages Viewed) per week
       FilterSpecs filterSBouncesWeek = new FilterSpecs();
        
        GraphSpecs graphSBouncesWeek = new GraphSpecs(
                GraphSpecs.METRICS.NumberBounces, 
                GraphSpecs.TIME_SPAN.WEEK_SPAN, 
                GraphSpecs.BOUNCE_DEF.NPAGES, 
                filterSBouncesWeek
        );
        
        String expBouncesWeek = "select server_logs.EntryDate as d, count(id) as c from server_logs  WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  And PagesViewed<=1  group by strftime('%W', d);";
        String resBouncesWeek = QueryComposer.composeQuery(graphSBouncesWeek).trim();
        
        //System.out.println();
        
        String[] tokensExpBouncesWeek = expBouncesWeek.split(""); 
        String[] tokensResBouncesWeek = resBouncesWeek.split(" ");
        
         Assert.assertArrayEquals(tokensExpBouncesWeek, tokensResBouncesWeek);
    }
     void numberOfBouncesPagesViewedQueryTest() {
         //Test for Number of Bounces (Pages Viewed) per day
        FilterSpecs filterSBouncesDay = new FilterSpecs();
       
        
        GraphSpecs graphSBouncesDay = new GraphSpecs(
                GraphSpecs.METRICS.NumberBounces, 
                GraphSpecs.TIME_SPAN.DAY_SPAN, 
                GraphSpecs.BOUNCE_DEF.NPAGES, 
                filterSBouncesDay
        );
        
        //TODO
        String expBouncesDay = "select server_logs.EntryDate as d, count(id) as c from server_logs  WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  And PagesViewed<=1  group by strftime('%d', d);";
        String resBouncesDay = QueryComposer.composeQuery(graphSBouncesDay).trim();
        
        //System.out.println(resUniquesDay);
        
        String[] tokensExpBouncesDay = expBouncesDay.split(""); 
        String[] tokensResBouncesDay = resBouncesDay.split(" ");
        
         Assert.assertArrayEquals(tokensExpBouncesDay, tokensResBouncesDay);
    }
     
     
     void numberOfBouncesPagesViewedHourQueryTest() {
         //Test for Number of Bounces (Pages Viewed) per hour
        FilterSpecs filterSBouncesHour = new FilterSpecs();
       
        
        GraphSpecs graphSBouncesHour = new GraphSpecs(
                GraphSpecs.METRICS.NumberBounces, 
                GraphSpecs.TIME_SPAN.HOUR_SPAN, 
                GraphSpecs.BOUNCE_DEF.NPAGES, 
                filterSBouncesHour
        );
        
        String expBouncesHour = "select server_logs.EntryDate as d, count(id) as c from server_logs  WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  And PagesViewed<=1  group by strftime('%H:%d', d);";
        String resBouncesHour = QueryComposer.composeQuery(graphSBouncesHour).trim();
        
        //System.out.println(resUniquesHour);
        
        String[] tokensExpBouncesHour = expBouncesHour.split(""); 
        String[] tokensResBouncesHour = resBouncesHour.split(" ");
        
         Assert.assertArrayEquals(tokensExpBouncesHour, tokensResBouncesHour);
    }
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
    //TESTS FOR NUMBER OF CONVERSIONS
     @Test 
    void numberOfConversionsWeekQueryTest() {
       //Test for Number of Conversions per week
       FilterSpecs filterSConversionsWeek = new FilterSpecs();
        
        GraphSpecs graphSConversionsWeek = new GraphSpecs(
                GraphSpecs.METRICS.NumberConversions, 
                GraphSpecs.TIME_SPAN.WEEK_SPAN, 
                GraphSpecs.BOUNCE_DEF.NPAGES, 
                filterSConversionsWeek
        );
        
        String expConversionsWeek = "select server_logs.EntryDate as d, count(Conversion) as c from server_logs  WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  And Conversion='Yes'  group by strftime('%W', d);";
        String resConversionsWeek = QueryComposer.composeQuery(graphSConversionsWeek).trim();
        
        //System.out.println();
        
        String[] tokensExpConversionsWeek = expConversionsWeek.split(""); 
        String[] tokensResConversionsWeek = resConversionsWeek.split(" ");
        
         Assert.assertArrayEquals(tokensExpConversionsWeek, tokensResConversionsWeek);
    }
    
    @Test
     void numberOfConversionsDayQueryTest() {
         //Test for Number of Conversions per day
        FilterSpecs filterSConversionsDay = new FilterSpecs();
       
        
        GraphSpecs graphSConversionsDay = new GraphSpecs(
                GraphSpecs.METRICS.NumberConversions, 
                GraphSpecs.TIME_SPAN.DAY_SPAN, 
                GraphSpecs.BOUNCE_DEF.NPAGES, 
                filterSConversionsDay
        );
        
        String expConversionsDay = "select server_logs.EntryDate as d, count(Conversion) as c from server_logs  WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  And Conversion='Yes'  group by strftime('%d', d);";
        String resConversionsDay = QueryComposer.composeQuery(graphSConversionsDay).trim();
        
        //System.out.println(resUniquesDay);
        
        String[] tokensExpConversionsDay = expConversionsDay.split(""); 
        String[] tokensResConversionsDay = resConversionsDay.split(" ");
        
         Assert.assertArrayEquals(tokensExpConversionsDay, tokensResConversionsDay);
    }
    
      @Test
     void numberOfConversionsHourQueryTest() {
         //Test for Number of Conversions per hour
        FilterSpecs filterSConversionsHour = new FilterSpecs();
       
        
        GraphSpecs graphSConversionsHour = new GraphSpecs(
                GraphSpecs.METRICS.NumberConversions, 
                GraphSpecs.TIME_SPAN.HOUR_SPAN, 
                GraphSpecs.BOUNCE_DEF.NPAGES, 
                filterSConversionsHour
        );
        
        String expConversionsHour = "select server_logs.EntryDate as d, count(Conversion) as c from server_logs  WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  And Conversion='Yes'  group by strftime('%H:%d', d);";
        String resConversionsHour = QueryComposer.composeQuery(graphSConversionsHour).trim();
        
        //System.out.println(resUniquesHour);
        
        String[] tokensExpConversionsHour = expConversionsHour.split(""); 
        String[] tokensResConversionsHour = resConversionsHour.split(" ");
        
         Assert.assertArrayEquals(tokensExpConversionsHour, tokensResConversionsHour);
    }
     
     
     
     
     
     
    
     
     
    //TESTS FOR TOTAL COST 
     //TODO ASK ABOUT THESE
    void totalCostWeekQueryTest() {
       //Test for Number of Total cost per week
       FilterSpecs filterSTotalCostWeek = new FilterSpecs();
        
        GraphSpecs graphSTotalCostWeek = new GraphSpecs(
                GraphSpecs.METRICS.TotalCost, 
                GraphSpecs.TIME_SPAN.WEEK_SPAN, 
                GraphSpecs.BOUNCE_DEF.NPAGES, 
                filterSTotalCostWeek
        );
        
        String expTotalCostWeek = "select click_logs.date as d,sum(ClickCost) as c from click_logs  WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  group by strftime('%W', d);\n" +
"select Date as d,sum(impressionCost) as c from impression_logs  WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  group by strftime('%W', d);";
        String resTotalCostWeek = QueryComposer.composeQuery(graphSTotalCostWeek).trim();
        
        //System.out.println();
        
        String[] tokensExpTotalCostWeek = expTotalCostWeek.split(""); 
        String[] tokensResTotalCostWeek = resTotalCostWeek.split(" ");
        
         Assert.assertArrayEquals(tokensExpTotalCostWeek, tokensResTotalCostWeek);
    }
    
     void totalCostDayQueryTest() {
         //Test for Total Cost per day
        FilterSpecs filterSTotalCostDay = new FilterSpecs();
       
        
        GraphSpecs graphSTotalCostDay = new GraphSpecs(
                GraphSpecs.METRICS.TotalCost, 
                GraphSpecs.TIME_SPAN.DAY_SPAN, 
                GraphSpecs.BOUNCE_DEF.NPAGES, 
                filterSTotalCostDay
        );
        
        String expTotalCostDay = "select click_logs.date as d,sum(ClickCost) as c from click_logs  WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  group by strftime('%d', d);\n" +
"select Date as d,sum(impressionCost) as c from impression_logs  WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  group by strftime('%d', d);";
        String resTotalCostDay = QueryComposer.composeQuery(graphSTotalCostDay).trim();
        
        //System.out.println(resUniquesDay);
        
        String[] tokensExpTotalCostDay = expTotalCostDay.split(""); 
        String[] tokensResTotalCostDay = resTotalCostDay.split(" ");
        
         Assert.assertArrayEquals(tokensExpTotalCostDay, tokensResTotalCostDay);
    }
     
     void totalCostHourQueryTest() {
         //Test for Total Cost per hour
        FilterSpecs filterSTotalCostHour = new FilterSpecs();
       
        
        GraphSpecs graphSTotalCostHour = new GraphSpecs(
                GraphSpecs.METRICS.TotalCost, 
                GraphSpecs.TIME_SPAN.HOUR_SPAN, 
                GraphSpecs.BOUNCE_DEF.NPAGES, 
                filterSTotalCostHour
        );
        
        String expTotalCostHour = "select click_logs.date as d,sum(ClickCost) as c from click_logs  WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  group by strftime('%H:%d', d);\n" +
"select Date as d,sum(impressionCost) as c from impression_logs  WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  group by strftime('%H:%d', d);";
        String resTotalCostHour = QueryComposer.composeQuery(graphSTotalCostHour).trim();
        
        //System.out.println(resUniquesHour);
        
        String[] tokensExpTotalCostHour = expTotalCostHour.split(""); 
        String[] tokensResTotalCostHour = resTotalCostHour.split(" ");
        
         Assert.assertArrayEquals(tokensExpTotalCostHour, tokensResTotalCostHour);
    }
     
     
     
     
     
     
     
     
     
     
    //TESTS FOR CTR
     
    void ctrWeekQueryTest() {
       //Test for CTR per week
       FilterSpecs filterSCTRWeek = new FilterSpecs();
        
        GraphSpecs graphSCTRWeek = new GraphSpecs(
                GraphSpecs.METRICS.CTR, 
                GraphSpecs.TIME_SPAN.WEEK_SPAN, 
                GraphSpecs.BOUNCE_DEF.NPAGES, 
                filterSCTRWeek
        );
        
        String expCTRWeek = "select click_logs.Date as d,count(ClickCost) as c from click_logs WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  group by strftime('%W', d);\n" +
"select Date as d,count(userId) as c from impression_logs  WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  group by strftime('%W', d);";
        String resCTRWeek = QueryComposer.composeQuery(graphSCTRWeek).trim();
        
        //System.out.println();
        
        String[] tokensExpCTRWeek = expCTRWeek.split(""); 
        String[] tokensResCTRWeek = resCTRWeek.split(" ");
        
         Assert.assertArrayEquals(tokensExpCTRWeek, tokensResCTRWeek);
    }
     void ctrDayQueryTest() {
         //Test for CTR per day
        FilterSpecs filterSCTRDay = new FilterSpecs();
       
        
        GraphSpecs graphSCTRDay = new GraphSpecs(
                GraphSpecs.METRICS.CTR, 
                GraphSpecs.TIME_SPAN.DAY_SPAN, 
                GraphSpecs.BOUNCE_DEF.NPAGES, 
                filterSCTRDay
        );
        
        String expCTRDay = "select click_logs.Date as d,count(ClickCost) as c from click_logs WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  group by strftime('%d', d);\n" +
"select Date as d,count(userId) as c from impression_logs  WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  group by strftime('%d', d);";
        String resCTRDay = QueryComposer.composeQuery(graphSCTRDay).trim();
        
        //System.out.println(resUniquesDay);
        
        String[] tokensExpCTRDay = expCTRDay.split(""); 
        String[] tokensResCTRDay = resCTRDay.split(" ");
        
         Assert.assertArrayEquals(tokensExpCTRDay, tokensResCTRDay);
    }
     
     void ctrHourQueryTest() {
         //Test for CTR per hour
        FilterSpecs filterSCTRHour = new FilterSpecs();
       
        
        GraphSpecs graphSCTRHour = new GraphSpecs(
                GraphSpecs.METRICS.CTR, 
                GraphSpecs.TIME_SPAN.HOUR_SPAN, 
                GraphSpecs.BOUNCE_DEF.NPAGES, 
                filterSCTRHour
        );
        
        String expCTRHour = "select click_logs.Date as d,count(ClickCost) as c from click_logs WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  group by strftime('%H:%d', d);\n" +
"select Date as d,count(userId) as c from impression_logs  WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  group by strftime('%H:%d', d);\n" +
"";
        String resCTRHour = QueryComposer.composeQuery(graphSCTRHour).trim();
        
        //System.out.println(resUniquesHour);
        
        String[] tokensExpCTRHour = expCTRHour.split(""); 
        String[] tokensResCTRHour = resCTRHour.split(" ");
        
         Assert.assertArrayEquals(tokensExpCTRHour, tokensResCTRHour);
    }
     
     
     
     
     
     
     
     
     
    //TESTS FOR  CPA
    void cpaWeekQueryTest() {
       //Test for CPA per week
       FilterSpecs filterSCPAWeek = new FilterSpecs();
        
        GraphSpecs graphSCPAWeek = new GraphSpecs(
                GraphSpecs.METRICS.CPA, 
                GraphSpecs.TIME_SPAN.WEEK_SPAN, 
                GraphSpecs.BOUNCE_DEF.NPAGES, 
                filterSCPAWeek
        );
        
        String expCPAWeek = "select click_logs.date as d,sum(ClickCost) as c from click_logs  WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  group by strftime('%W', d);\n" +
"select Date as d,sum(impressionCost) as c from impression_logs  WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  group by strftime('%W', d);\n" +
"select server_logs.EntryDate as d, count(Conversion) as c from server_logs  WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  And Conversion='Yes'  group by strftime('%W', d);";
        String resCPAWeek = QueryComposer.composeQuery(graphSCPAWeek).trim();
        
        //System.out.println();
        
        String[] tokensExpCPAWeek = expCPAWeek.split(""); 
        String[] tokensResCPAWeek = resCPAWeek.split(" ");
        
         Assert.assertArrayEquals(tokensExpCPAWeek, tokensResCPAWeek);
    }
     void cpaDayQueryTest() {
         //Test for CPA per day
        FilterSpecs filterSCPADay = new FilterSpecs();
       
        
        GraphSpecs graphSCPADay = new GraphSpecs(
                GraphSpecs.METRICS.CPA, 
                GraphSpecs.TIME_SPAN.DAY_SPAN, 
                GraphSpecs.BOUNCE_DEF.NPAGES, 
                filterSCPADay
        );
        
        String expCPADay = "select click_logs.date as d,sum(ClickCost) as c from click_logs  WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  group by strftime('%d', d);\n" +
"select Date as d,sum(impressionCost) as c from impression_logs  WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  group by strftime('%d', d);\n" +
"select server_logs.EntryDate as d, count(Conversion) as c from server_logs  WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  And Conversion='Yes'  group by strftime('%d', d);";
        String resCPADay = QueryComposer.composeQuery(graphSCPADay).trim();
        
        //System.out.println(resUniquesDay);
        
        String[] tokensExpCPADay = expCPADay.split(""); 
        String[] tokensResCPADay = resCPADay.split(" ");
        
         Assert.assertArrayEquals(tokensExpCPADay, tokensResCPADay);
     }
     
     void cpaHourQueryTest() {
         //Test for CPA per hour
        FilterSpecs filterSCPAHour = new FilterSpecs();
       
        
        GraphSpecs graphSCPAHour = new GraphSpecs(
                GraphSpecs.METRICS.CPA, 
                GraphSpecs.TIME_SPAN.HOUR_SPAN, 
                GraphSpecs.BOUNCE_DEF.NPAGES, 
                filterSCPAHour
        );
        
        String expCPAHour = "select click_logs.date as d,sum(ClickCost) as c from click_logs  WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  group by strftime('%H:%d', d);\n" +
"select Date as d,sum(impressionCost) as c from impression_logs  WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  group by strftime('%H:%d', d);\n" +
"select server_logs.EntryDate as d, count(Conversion) as c from server_logs  WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  And Conversion='Yes'  group by strftime('%H:%d', d);";
        String resCPAHour = QueryComposer.composeQuery(graphSCPAHour).trim();
        
        //System.out.println(resUniquesHour);
        
        String[] tokensExpCPAHour = expCPAHour.split(""); 
        String[] tokensResCPAHour = resCPAHour.split(" ");
        
         Assert.assertArrayEquals(tokensExpCPAHour, tokensResCPAHour);
    }
     
     
     
     
    //TESTS FOR CPC 
    void cpcWeekQueryTest() {
       //Test for CPC per week
       FilterSpecs filterSCPCWeek = new FilterSpecs();
        
        GraphSpecs graphSCPCWeek = new GraphSpecs(
                GraphSpecs.METRICS.CPC, 
                GraphSpecs.TIME_SPAN.WEEK_SPAN, 
                GraphSpecs.BOUNCE_DEF.NPAGES, 
                filterSCPCWeek
        );
        
        String expCPCWeek = "select click_logs.date as d,sum(ClickCost) as c from click_logs  WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  group by strftime('%W', d);\n" +
"select Date as d,sum(impressionCost) as c from impression_logs  WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  group by strftime('%W', d);\n" +
"select click_logs.Date as d,count(ClickCost) as c from click_logs WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  group by strftime('%W', d);";
        String resCPCWeek = QueryComposer.composeQuery(graphSCPCWeek).trim();
        
        //System.out.println();
        
        String[] tokensExpCPCWeek = expCPCWeek.split(""); 
        String[] tokensResCPCWeek = resCPCWeek.split(" ");
        
         Assert.assertArrayEquals(tokensExpCPCWeek, tokensResCPCWeek);
    }
    
    
     void cpcDayQueryTest() {
         //Test for CPC per day
        FilterSpecs filterSCPCDay = new FilterSpecs();
       
        
        GraphSpecs graphSCPCDay = new GraphSpecs(
                GraphSpecs.METRICS.CPC, 
                GraphSpecs.TIME_SPAN.DAY_SPAN, 
                GraphSpecs.BOUNCE_DEF.NPAGES, 
                filterSCPCDay
        );
        
        String expCPCDay = "select click_logs.date as d,sum(ClickCost) as c from click_logs  WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  group by strftime('%d', d);\n" +
"select Date as d,sum(impressionCost) as c from impression_logs  WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  group by strftime('%d', d);\n" +
"select click_logs.Date as d,count(ClickCost) as c from click_logs WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  group by strftime('%d', d);\n" +
"";
        String resCPCDay = QueryComposer.composeQuery(graphSCPCDay).trim();
        
        //System.out.println(resUniquesDay);
        
        String[] tokensExpCPCDay = expCPCDay.split(""); 
        String[] tokensResCPCDay = resCPCDay.split(" ");
        
         Assert.assertArrayEquals(tokensExpCPCDay, tokensResCPCDay);
    }
     
     void cpcHourQueryTest() {
         //Test for CPC per hour
        FilterSpecs filterSCPCHour = new FilterSpecs();
       
        
        GraphSpecs graphSCPCHour = new GraphSpecs(
                GraphSpecs.METRICS.CPC, 
                GraphSpecs.TIME_SPAN.HOUR_SPAN, 
                GraphSpecs.BOUNCE_DEF.NPAGES, 
                filterSCPCHour
        );
        
        String expCPCHour = "select click_logs.date as d,sum(ClickCost) as c from click_logs  WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  group by strftime('%H:%d', d);\n" +
"select Date as d,sum(impressionCost) as c from impression_logs  WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  group by strftime('%H:%d', d);\n" +
"select click_logs.Date as d,count(ClickCost) as c from click_logs WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  group by strftime('%H:%d', d);";
        String resCPCHour = QueryComposer.composeQuery(graphSCPCHour).trim();
        
        //System.out.println(resUniquesHour);
        
        String[] tokensExpCPCHour = expCPCHour.split(""); 
        String[] tokensResCPCHour = resCPCHour.split(" ");
        
         Assert.assertArrayEquals(tokensExpCPCHour, tokensResCPCHour);
    }
     
     
     
    
     
    //TESTS FOR CPM 
    void cpmWeekQueryTest() {
       //Test for CPM per week
       FilterSpecs filterSCPMWeek = new FilterSpecs();
        
        GraphSpecs graphSCPMWeek = new GraphSpecs(
                GraphSpecs.METRICS.CPM, 
                GraphSpecs.TIME_SPAN.WEEK_SPAN, 
                GraphSpecs.BOUNCE_DEF.NPAGES, 
                filterSCPMWeek
        );
        
        String expCPMWeek = "select click_logs.date as d,sum(ClickCost) as c from click_logs  WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  group by strftime('%W', d);\n" +
"select Date as d,sum(impressionCost) as c from impression_logs  WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  group by strftime('%W', d);\n" +
"select Date as d,count(userId) as c from impression_logs  WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  group by strftime('%W', d);\n" +
"";
        String resCPMWeek = QueryComposer.composeQuery(graphSCPMWeek).trim();
        
        //System.out.println();
        
        String[] tokensExpCPMWeek = expCPMWeek.split(""); 
        String[] tokensResCPMWeek = resCPMWeek.split(" ");
        
         Assert.assertArrayEquals(tokensExpCPMWeek, tokensResCPMWeek);
    }
     void cpmDayQueryTest() {
         //Test for CPM per day
        FilterSpecs filterSCPMDay = new FilterSpecs();
       
        
        GraphSpecs graphSCPMDay = new GraphSpecs(
                GraphSpecs.METRICS.CPM, 
                GraphSpecs.TIME_SPAN.DAY_SPAN, 
                GraphSpecs.BOUNCE_DEF.NPAGES, 
                filterSCPMDay
        );
        
        String expCPMDay = "select click_logs.date as d,sum(ClickCost) as c from click_logs  WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  group by strftime('%d', d);\n" +
"select Date as d,sum(impressionCost) as c from impression_logs  WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  group by strftime('%d', d);\n" +
"select Date as d,count(userId) as c from impression_logs  WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  group by strftime('%d', d);";
        String resCPMDay = QueryComposer.composeQuery(graphSCPMDay).trim();
        
        //System.out.println(resUniquesDay);
        
        String[] tokensExpCPMDay = expCPMDay.split(""); 
        String[] tokensResCPMDay = resCPMDay.split(" ");
        
         Assert.assertArrayEquals(tokensExpCPMDay, tokensResCPMDay);
    }
     
     void cpmHourQueryTest() {
         //Test for CPM per hour
        FilterSpecs filterSCPMHour = new FilterSpecs();
       
        
        GraphSpecs graphSCPMHour = new GraphSpecs(
                GraphSpecs.METRICS.CPM, 
                GraphSpecs.TIME_SPAN.HOUR_SPAN, 
                GraphSpecs.BOUNCE_DEF.NPAGES, 
                filterSCPMHour
        );
        
        String expCPMHour = "select click_logs.date as d,sum(ClickCost) as c from click_logs  WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  group by strftime('%H:%d', d);\n" +
"select Date as d,sum(impressionCost) as c from impression_logs  WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  group by strftime('%H:%d', d);\n" +
"select Date as d,count(userId) as c from impression_logs  WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  group by strftime('%H:%d', d);";
        String resCPMHour = QueryComposer.composeQuery(graphSCPMHour).trim();
        
        //System.out.println(resUniquesHour);
        
        String[] tokensExpCPMHour = expCPMHour.split(""); 
        String[] tokensResCPMHour = resCPMHour.split(" ");
        
         Assert.assertArrayEquals(tokensExpCPMHour, tokensResCPMHour);
    }
    
     
     
    
     
     
     
    
     
    //TESTS FOR BOUNCE RATE
    void bounceRateWeekQueryTest() {
       //Test for Bounce rate per week
       FilterSpecs filterSBounceRateWeek = new FilterSpecs();
        
        GraphSpecs graphSBounceRateWeek = new GraphSpecs(
                GraphSpecs.METRICS.BounceRate, 
                GraphSpecs.TIME_SPAN.WEEK_SPAN, 
                GraphSpecs.BOUNCE_DEF.NPAGES, 
                filterSBounceRateWeek
        );
        
        String expBounceRateWeek = "select server_logs.EntryDate as d,count(strftime('%Y-%m-%d %H:%M:%S', ExitDate) - strftime('%Y-%m-%d %H:%M:%S', EntryDate)) as c from server_logs WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  And strftime('%Y-%m-%d %H:%M:%S', ExitDate) - strftime('%Y-%m-%d %H:%M:%S', EntryDate) <= 20   group by strftime('%W', d);\n" +
"select click_logs.Date as d,count(ClickCost) as c from click_logs WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  group by strftime('%W', d);";
        String resBounceRateWeek = QueryComposer.composeQuery(graphSBounceRateWeek).trim();
        
        //System.out.println();
        
        String[] tokensExpBounceRateWeek = expBounceRateWeek.split(""); 
        String[] tokensResBounceRateWeek = resBounceRateWeek.split(" ");
        
         Assert.assertArrayEquals(tokensExpBounceRateWeek, tokensResBounceRateWeek);
    }
    
    
     void bounceRateDayQueryTest() {
         //Test for Bounce rate per day
        FilterSpecs filterSBounceRateDay = new FilterSpecs();
       
        
        GraphSpecs graphSBounceRateDay = new GraphSpecs(
                GraphSpecs.METRICS.BounceRate, 
                GraphSpecs.TIME_SPAN.DAY_SPAN, 
                GraphSpecs.BOUNCE_DEF.NPAGES, 
                filterSBounceRateDay
        );
        
        String expBounceRateDay = "select server_logs.EntryDate as d,count(strftime('%Y-%m-%d %H:%M:%S', ExitDate) - strftime('%Y-%m-%d %H:%M:%S', EntryDate)) as c from server_logs WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  And strftime('%Y-%m-%d %H:%M:%S', ExitDate) - strftime('%Y-%m-%d %H:%M:%S', EntryDate) <= 20   group by strftime('%d', d);\n" +
"select click_logs.Date as d,count(ClickCost) as c from click_logs WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  group by strftime('%d', d);";
        String resBounceRateDay = QueryComposer.composeQuery(graphSBounceRateDay).trim();
        
        //System.out.println(resUniquesDay);
        
        String[] tokensExpBounceRateDay = expBounceRateDay.split(""); 
        String[] tokensResBounceRateDay = resBounceRateDay.split(" ");
        
         Assert.assertArrayEquals(tokensExpBounceRateDay, tokensResBounceRateDay);
    }
     
     void bounceRateHourQueryTest() {
         //Test for Bounce rate per hour
        FilterSpecs filterSBounceRateHour = new FilterSpecs();
       
        
        GraphSpecs graphSBounceRateHour = new GraphSpecs(
                GraphSpecs.METRICS.BounceRate, 
                GraphSpecs.TIME_SPAN.HOUR_SPAN, 
                GraphSpecs.BOUNCE_DEF.NPAGES, 
                filterSBounceRateHour
        );
        
        String expBounceRateHour = "select server_logs.EntryDate as d,count(strftime('%Y-%m-%d %H:%M:%S', ExitDate) - strftime('%Y-%m-%d %H:%M:%S', EntryDate)) as c from server_logs WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  And strftime('%Y-%m-%d %H:%M:%S', ExitDate) - strftime('%Y-%m-%d %H:%M:%S', EntryDate) <= 20   group by strftime('%H:%d', d);\n" +
"select click_logs.Date as d,count(ClickCost) as c from click_logs WHERE d > '2015-01-01 12:00:02'  AND d < '2015-01-14 12:00:00'  group by strftime('%H:%d', d);";
        String resBounceRateHour = QueryComposer.composeQuery(graphSBounceRateHour).trim();
        
        //System.out.println(resUniquesHour);
        
        String[] tokensExpBounceRateHour = expBounceRateHour.split(""); 
        String[] tokensResBounceRateHour = resBounceRateHour.split(" ");
        
         Assert.assertArrayEquals(tokensExpBounceRateHour, tokensResBounceRateHour);
    }
}
