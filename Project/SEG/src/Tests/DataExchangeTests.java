package Tests;

import Commons.*;
import DatabaseManager.DataExchange;
import DatabaseManager.DatabaseManager;
import Gui.MainController;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static Commons.Enums.Context.*;
import static Commons.Enums.Gender.Female;
import static Commons.Enums.Gender.Male;
import static org.junit.Assert.assertTrue;

public class DataExchangeTests {

    DataExchange de;
    List<Tuple<String, Number>>  allData;


    @Before
    public void setUp() {
        de = new DataExchange(new DatabaseManager());


        GraphSpecs blank = new GraphSpecs(200, "test graph", GraphSpecs.METRICS.NumberImpressions, GraphSpecs.TIME_SPAN.MONTH_SPAN, GraphSpecs.BOUNCE_DEF.TIME, new FilterSpecs());
        allData = de.getGraphData(blank);
    }


    public UserEntry getUserWithID(List<UserEntry> a, long id) {

        /*
        If they're in order and there are no gaps
         */
        long offset = a.get(0).getId();
        if (a.get(Math.round(id-offset)).getId() == id) {
            return a.get(Math.round(id-offset));
        }

        /*
        If they're not in order or it doesn't exist
         */

        UserEntry result = getUserWithIDBinary(a, id);

        if (result == null) {
            System.out.println("Not found :(");
            return getUserWithIDNotBinary(a, id);
        }
        /*
        If they are in order and it exists
         */
        else {
            return result;
        }
    }

    public UserEntry getUserWithIDBinary(List<UserEntry> a, long id) {
        if (a.size() == 0) {
            return null;
        }
        UserEntry middle = (a.get(Math.floorDiv(a.size(),2)));
        if (middle.getId() == id) {
            return middle;
        }
        else if (middle.getId() > id) {
            return (getUserWithIDBinary(a.subList(0,Math.round(a.size()/2)), id));
        }
        else {
            return (getUserWithIDBinary(a.subList(Math.round(a.size()/2), a.size()), id));
        }
    }


    public UserEntry getUserWithIDNotBinary(List<UserEntry> a, long id) {
        for (UserEntry i : a) {
            if (i.getId() == id) {
                return i;
            }
        }
        return null;
    }

    @Test
    public void testMale() {
        MainController m = new MainController(null, null, null,new Gui.GuiColors());
        FilterSpecs f = m.getInitFilters();
        LinkedList<Enums.Gender> genderList = new LinkedList<Enums.Gender>();
        genderList.add(Male);
        f.setGenders(genderList);

        DataExchange de2 = new DataExchange(new DatabaseManager());

        GraphSpecs males = new GraphSpecs(1, "testingcampaign", GraphSpecs.METRICS.NumberImpressions, GraphSpecs.TIME_SPAN.DAY_SPAN, GraphSpecs.BOUNCE_DEF.TIME, f);
        List<Tuple<String,Number>> actualData = de2.getGraphData(males);

        List<ImpressionEntry> allFromImpressions = de.selectAllFrom_IMPRESSION_LOGS();
        List<UserEntry> allFromUsers = de.selectAllFrom_USERS();

        double expected = 0;
        double actual = 0;
        for (ImpressionEntry i : allFromImpressions) {
            //if (getUserWithID(allFromUsers, i.getUserId()).getGender() == Male)
            if (allFromUsers.get((int) (i.getUserId())).getGender() == Male)
                expected++;
        }

        for (Tuple<String,Number> i : actualData) {
            actual+=i.getY().doubleValue();
        }

        assertTrue(expected==actual);
    }

    @Test
    public void testFemale() {
        MainController m = new MainController(null, null, null,new Gui.GuiColors());
        FilterSpecs f = m.getInitFilters();
        LinkedList<Enums.Gender> genderList = new LinkedList<Enums.Gender>();
        genderList.add(Female);
        f.setGenders(genderList);

        DataExchange de2 = new DataExchange(new DatabaseManager());

        GraphSpecs males = new GraphSpecs(1, "testingcampaign", GraphSpecs.METRICS.NumberImpressions, GraphSpecs.TIME_SPAN.DAY_SPAN, GraphSpecs.BOUNCE_DEF.TIME, f);
        List<Tuple<String,Number>> actualData = de2.getGraphData(males);

        List<ImpressionEntry> allFromImpressions = de.selectAllFrom_IMPRESSION_LOGS();
        List<UserEntry> allFromUsers = de.selectAllFrom_USERS();

        double expected = 0;
        double actual = 0;

        for (ImpressionEntry i : allFromImpressions) {
            if (getUserWithID(allFromUsers, i.getUserId()).getGender() == Female)
                expected++;
        }

        for (Tuple<String,Number> i : actualData) {
            actual+=i.getY().doubleValue();
        }

        assertTrue(expected==actual);
    }

    @Test
    public void testAllAges() {
        MainController m = new MainController(null, null, null,new Gui.GuiColors());
        FilterSpecs f = m.getInitFilters();
        LinkedList<Enums.Age> ageList = new LinkedList<Enums.Age>();
        ageList.add(Enums.Age.Age_less_than_25);
        ageList.add(Enums.Age.Age_25_34);
        ageList.add(Enums.Age.Age_35_44);
        ageList.add(Enums.Age.Age_45_54);
        ageList.add(Enums.Age.Age_more_than_54);
        f.setAges(ageList);

        DataExchange de2 = new DataExchange(new DatabaseManager());

        GraphSpecs males = new GraphSpecs(1, "testingcampaign", GraphSpecs.METRICS.NumberImpressions, GraphSpecs.TIME_SPAN.DAY_SPAN, GraphSpecs.BOUNCE_DEF.TIME, f);
        List<Tuple<String,Number>> actualData = de2.getGraphData(males);

        List<ImpressionEntry> allFromImpressions = de.selectAllFrom_IMPRESSION_LOGS();

        double expected = 0;
        double actual = 0;

        expected = allFromImpressions.size();

        for (Tuple<String,Number> i : actualData) {
            actual+=i.getY().doubleValue();
        }

        assertTrue(expected==actual);
    }

    @Test
    public void testLessThan25() {
        MainController m = new MainController(null, null, null,new Gui.GuiColors());
        FilterSpecs f = m.getInitFilters();
        LinkedList<Enums.Age> ageList = new LinkedList<Enums.Age>();
        ageList.add(Enums.Age.Age_less_than_25);
        f.setAges(ageList);

        DataExchange de2 = new DataExchange(new DatabaseManager());

        GraphSpecs males = new GraphSpecs(1, "testingcampaign", GraphSpecs.METRICS.NumberImpressions, GraphSpecs.TIME_SPAN.DAY_SPAN, GraphSpecs.BOUNCE_DEF.TIME, f);
        List<Tuple<String,Number>> actualData = de2.getGraphData(males);

        List<ImpressionEntry> allFromImpressions = de.selectAllFrom_IMPRESSION_LOGS();
        List<UserEntry> allFromUsers = de.selectAllFrom_USERS();

        double expected = 0;
        double actual = 0;

        for (ImpressionEntry i : allFromImpressions) {
            if (getUserWithID(allFromUsers, i.getUserId()).getAge() == Enums.Age.Age_25_34)
                expected++;
        }

        for (Tuple<String,Number> i : actualData) {
            actual+=i.getY().doubleValue();
        }

        assertTrue(expected==actual);
    }

    @Test
    public void test25To34() {
        MainController m = new MainController(null, null, null,new Gui.GuiColors());
        FilterSpecs f = m.getInitFilters();
        LinkedList<Enums.Age> ageList = new LinkedList<Enums.Age>();
        ageList.add(Enums.Age.Age_25_34);
        f.setAges(ageList);

        DataExchange de2 = new DataExchange(new DatabaseManager());

        GraphSpecs males = new GraphSpecs(1, "testingcampaign", GraphSpecs.METRICS.NumberImpressions, GraphSpecs.TIME_SPAN.DAY_SPAN, GraphSpecs.BOUNCE_DEF.TIME, f);
        List<Tuple<String,Number>> actualData = de2.getGraphData(males);

        List<ImpressionEntry> allFromImpressions = de.selectAllFrom_IMPRESSION_LOGS();
        List<UserEntry> allFromUsers = de.selectAllFrom_USERS();

        double expected = 0;
        double actual = 0;

        for (ImpressionEntry i : allFromImpressions) {
            if (getUserWithID(allFromUsers, i.getUserId()).getAge() == Enums.Age.Age_25_34)
                expected++;
        }

        for (Tuple<String,Number> i : actualData) {
            actual+=i.getY().doubleValue();
        }

        assertTrue(expected==actual);
    }

    @Test
    public void test35to44() {
        MainController m = new MainController(null, null, null,new Gui.GuiColors());
        FilterSpecs f = m.getInitFilters();
        LinkedList<Enums.Age> ageList = new LinkedList<Enums.Age>();
        ageList.add(Enums.Age.Age_35_44);
        f.setAges(ageList);

        DataExchange de2 = new DataExchange(new DatabaseManager());

        GraphSpecs males = new GraphSpecs(1, "testingcampaign", GraphSpecs.METRICS.NumberImpressions, GraphSpecs.TIME_SPAN.DAY_SPAN, GraphSpecs.BOUNCE_DEF.TIME, f);
        List<Tuple<String,Number>> actualData = de2.getGraphData(males);

        List<ImpressionEntry> allFromImpressions = de.selectAllFrom_IMPRESSION_LOGS();
        List<UserEntry> allFromUsers = de.selectAllFrom_USERS();

        double expected = 0;
        double actual = 0;

        for (ImpressionEntry i : allFromImpressions) {
            if (getUserWithID(allFromUsers, i.getUserId()).getAge() == Enums.Age.Age_35_44)
                expected++;
        }

        for (Tuple<String,Number> i : actualData) {
            actual+=i.getY().doubleValue();
        }

        assertTrue(expected==actual);
    }

    @Test
    public void test45To54() {
        MainController m = new MainController(null, null, null,new Gui.GuiColors());
        FilterSpecs f = m.getInitFilters();
        LinkedList<Enums.Age> ageList = new LinkedList<Enums.Age>();
        ageList.add(Enums.Age.Age_45_54);
        f.setAges(ageList);

        DataExchange de2 = new DataExchange(new DatabaseManager());

        GraphSpecs males = new GraphSpecs(1, "testingcampaign", GraphSpecs.METRICS.NumberImpressions, GraphSpecs.TIME_SPAN.DAY_SPAN, GraphSpecs.BOUNCE_DEF.TIME, f);
        List<Tuple<String,Number>> actualData = de2.getGraphData(males);

        List<ImpressionEntry> allFromImpressions = de.selectAllFrom_IMPRESSION_LOGS();
        List<UserEntry> allFromUsers = de.selectAllFrom_USERS();

        double expected = 0;
        double actual = 0;

        for (ImpressionEntry i : allFromImpressions) {
            if (getUserWithID(allFromUsers, i.getUserId()).getAge() == Enums.Age.Age_45_54)
                expected++;
        }

        for (Tuple<String,Number> i : actualData) {
            actual+=i.getY().doubleValue();
        }

        assertTrue(expected==actual);
    }

    @Test
    public void testMoreThan54() {
        MainController m = new MainController(null, null, null,new Gui.GuiColors());
        FilterSpecs f = m.getInitFilters();
        LinkedList<Enums.Age> ageList = new LinkedList<Enums.Age>();
        ageList.add(Enums.Age.Age_more_than_54);
        f.setAges(ageList);

        DataExchange de2 = new DataExchange(new DatabaseManager());

        GraphSpecs males = new GraphSpecs(1, "testingcampaign", GraphSpecs.METRICS.NumberImpressions, GraphSpecs.TIME_SPAN.DAY_SPAN, GraphSpecs.BOUNCE_DEF.TIME, f);
        List<Tuple<String,Number>> actualData = de2.getGraphData(males);

        List<ImpressionEntry> allFromImpressions = de.selectAllFrom_IMPRESSION_LOGS();
        List<UserEntry> allFromUsers = de.selectAllFrom_USERS();

        double expected = 0;
        double actual = 0;

        for (ImpressionEntry i : allFromImpressions) {
            if (getUserWithID(allFromUsers, i.getUserId()).getAge() == Enums.Age.Age_more_than_54)
                expected++;
        }

        for (Tuple<String,Number> i : actualData) {
            actual+=i.getY().doubleValue();
        }

        assertTrue(expected==actual);

    }

    @Test
    public void testAllContexts() {
        MainController m = new MainController(null, null, null,new Gui.GuiColors());
        FilterSpecs f = m.getInitFilters();
        LinkedList<Enums.Context> contextList = new LinkedList<Enums.Context>();
        contextList.add(News);
        contextList.add(SocialMedia);
        contextList.add(Shopping);
        contextList.add(Blog);
        contextList.add(Hobbies);
        contextList.add(Travel);

        f.setContexts(contextList);

        DataExchange de2 = new DataExchange(new DatabaseManager());

        GraphSpecs males = new GraphSpecs(1, "testingcampaign", GraphSpecs.METRICS.NumberImpressions, GraphSpecs.TIME_SPAN.DAY_SPAN, GraphSpecs.BOUNCE_DEF.TIME, f);
        List<Tuple<String,Number>> actualData = de2.getGraphData(males);

        List<ImpressionEntry> allFromImpressions = de.selectAllFrom_IMPRESSION_LOGS();

        double expected = 0;
        double actual = 0;

        expected = allFromImpressions.size();

        for (Tuple<String,Number> i : actualData) {
            actual+=i.getY().doubleValue();
        }

        assertTrue(expected==actual);
    }

    @Test
    public void testNews() {
        MainController m = new MainController(null, null, null,new Gui.GuiColors());
        FilterSpecs f = m.getInitFilters();
        LinkedList<Enums.Context> contextList = new LinkedList<Enums.Context>();
        contextList.add(News);
        f.setContexts(contextList);

        DataExchange de2 = new DataExchange(new DatabaseManager());

        GraphSpecs males = new GraphSpecs(1, "testingcampaign", GraphSpecs.METRICS.NumberImpressions, GraphSpecs.TIME_SPAN.DAY_SPAN, GraphSpecs.BOUNCE_DEF.TIME, f);
        List<Tuple<String,Number>> actualData = de2.getGraphData(males);

        List<ImpressionEntry> allFromImpressions = de.selectAllFrom_IMPRESSION_LOGS();

        double expected = 0;
        double actual = 0;

        for (ImpressionEntry i : allFromImpressions) {
            if (i.getContext() == News)
                expected++;
        }

        for (Tuple<String,Number> i : actualData) {
            actual+=i.getY().doubleValue();
        }

        assertTrue(expected==actual);
    }

    @Test
    public void testShopping() {
        MainController m = new MainController(null, null, null,new Gui.GuiColors());
        FilterSpecs f = m.getInitFilters();
        LinkedList<Enums.Context> contextList = new LinkedList<Enums.Context>();
        contextList.add(Shopping);
        f.setContexts(contextList);

        DataExchange de2 = new DataExchange(new DatabaseManager());

        GraphSpecs males = new GraphSpecs(1, "testingcampaign", GraphSpecs.METRICS.NumberImpressions, GraphSpecs.TIME_SPAN.DAY_SPAN, GraphSpecs.BOUNCE_DEF.TIME, f);
        List<Tuple<String,Number>> actualData = de2.getGraphData(males);

        List<ImpressionEntry> allFromImpressions = de.selectAllFrom_IMPRESSION_LOGS();

        double expected = 0;
        double actual = 0;

        for (ImpressionEntry i : allFromImpressions) {
            if (i.getContext() == Shopping)
                expected++;
        }

        for (Tuple<String,Number> i : actualData) {
            actual+=i.getY().doubleValue();
        }

        assertTrue(expected==actual);
    }

    @Test
    public void testSocialMedia() {
        MainController m = new MainController(null, null, null,new Gui.GuiColors());
        FilterSpecs f = m.getInitFilters();
        LinkedList<Enums.Context> contextList = new LinkedList<Enums.Context>();
        contextList.add(SocialMedia);
        f.setContexts(contextList);

        DataExchange de2 = new DataExchange(new DatabaseManager());

        GraphSpecs males = new GraphSpecs(1, "testingcampaign", GraphSpecs.METRICS.NumberImpressions, GraphSpecs.TIME_SPAN.DAY_SPAN, GraphSpecs.BOUNCE_DEF.TIME, f);
        List<Tuple<String,Number>> actualData = de2.getGraphData(males);

        List<ImpressionEntry> allFromImpressions = de.selectAllFrom_IMPRESSION_LOGS();

        double expected = 0;
        double actual = 0;

        for (ImpressionEntry i : allFromImpressions) {
            if (i.getContext() == SocialMedia)
                expected++;
        }

        for (Tuple<String,Number> i : actualData) {
            actual+=i.getY().doubleValue();
        }

        assertTrue(expected==actual);
    }

    @Test
    public void testTravels() {
        MainController m = new MainController(null, null, null,new Gui.GuiColors());
        FilterSpecs f = m.getInitFilters();
        LinkedList<Enums.Context> contextList = new LinkedList<Enums.Context>();
        contextList.add(Travel);
        f.setContexts(contextList);

        DataExchange de2 = new DataExchange(new DatabaseManager());

        GraphSpecs males = new GraphSpecs(1, "testingcampaign", GraphSpecs.METRICS.NumberImpressions, GraphSpecs.TIME_SPAN.DAY_SPAN, GraphSpecs.BOUNCE_DEF.TIME, f);
        List<Tuple<String,Number>> actualData = de2.getGraphData(males);

        List<ImpressionEntry> allFromImpressions = de.selectAllFrom_IMPRESSION_LOGS();

        double expected = 0;
        double actual = 0;

        for (ImpressionEntry i : allFromImpressions) {
            if (i.getContext() == Travel)
                expected++;
        }

        for (Tuple<String,Number> i : actualData) {
            actual+=i.getY().doubleValue();
        }

        assertTrue(expected==actual);
    }

    @Test
    public void testHobbies() {
        MainController m = new MainController(null, null, null,new Gui.GuiColors());
        FilterSpecs f = m.getInitFilters();
        LinkedList<Enums.Context> contextList = new LinkedList<Enums.Context>();
        contextList.add(Hobbies);
        f.setContexts(contextList);

        DataExchange de2 = new DataExchange(new DatabaseManager());

        GraphSpecs males = new GraphSpecs(1, "testingcampaign", GraphSpecs.METRICS.NumberImpressions, GraphSpecs.TIME_SPAN.DAY_SPAN, GraphSpecs.BOUNCE_DEF.TIME, f);
        List<Tuple<String,Number>> actualData = de2.getGraphData(males);

        List<ImpressionEntry> allFromImpressions = de.selectAllFrom_IMPRESSION_LOGS();

        double expected = 0;
        double actual = 0;

        for (ImpressionEntry i : allFromImpressions) {
            if (i.getContext() == Hobbies)
                expected++;
        }

        for (Tuple<String,Number> i : actualData) {
            actual+=i.getY().doubleValue();
        }

        assertTrue(expected==actual);
    }

    @Test
    public void testBlog() {
        MainController m = new MainController(null, null, null,new Gui.GuiColors());
        FilterSpecs f = m.getInitFilters();
        LinkedList<Enums.Context> contextList = new LinkedList<Enums.Context>();
        contextList.add(Blog);
        f.setContexts(contextList);

        DataExchange de2 = new DataExchange(new DatabaseManager());

        GraphSpecs males = new GraphSpecs(1, "testingcampaign", GraphSpecs.METRICS.NumberImpressions, GraphSpecs.TIME_SPAN.DAY_SPAN, GraphSpecs.BOUNCE_DEF.TIME, f);
        List<Tuple<String,Number>> actualData = de2.getGraphData(males);

        List<ImpressionEntry> allFromImpressions = de.selectAllFrom_IMPRESSION_LOGS();

        double expected = 0;
        double actual = 0;

        for (ImpressionEntry i : allFromImpressions) {
            if (i.getContext() == Blog)
                expected++;
        }

        for (Tuple<String,Number> i : actualData) {
            actual+=i.getY().doubleValue();
        }

        assertTrue(expected==actual);
    }

    @Test
    public void testLowIncome() {
        MainController m = new MainController(null, null, null,new Gui.GuiColors());
        FilterSpecs f = m.getInitFilters();
        LinkedList<Enums.Income> incomeList = new LinkedList<Enums.Income>();
        incomeList.add(Enums.Income.Low);
        f.setIncomes(incomeList);

        DataExchange de2 = new DataExchange(new DatabaseManager());

        GraphSpecs males = new GraphSpecs(1, "testingcampaign", GraphSpecs.METRICS.NumberImpressions, GraphSpecs.TIME_SPAN.DAY_SPAN, GraphSpecs.BOUNCE_DEF.TIME, f);
        List<Tuple<String,Number>> actualData = de2.getGraphData(males);

        List<ImpressionEntry> allFromImpressions = de.selectAllFrom_IMPRESSION_LOGS();
        List<UserEntry> allFromUsers = de.selectAllFrom_USERS();

        double expected = 0;
        double actual = 0;

        for (ImpressionEntry i : allFromImpressions) {
            if (getUserWithID(allFromUsers, i.getUserId()).getIncome() == Enums.Income.Low)
                expected++;
        }

        for (Tuple<String,Number> i : actualData) {
            actual+=i.getY().doubleValue();
        }

        assertTrue(expected==actual);
    }

    @Test
    public void testMediumIncome() {
        MainController m = new MainController(null, null, null,new Gui.GuiColors());
        FilterSpecs f = m.getInitFilters();
        LinkedList<Enums.Income> incomeList = new LinkedList<Enums.Income>();
        incomeList.add(Enums.Income.Medium);
        f.setIncomes(incomeList);

        DataExchange de2 = new DataExchange(new DatabaseManager());

        GraphSpecs males = new GraphSpecs(1, "testingcampaign", GraphSpecs.METRICS.NumberImpressions, GraphSpecs.TIME_SPAN.DAY_SPAN, GraphSpecs.BOUNCE_DEF.TIME, f);
        List<Tuple<String,Number>> actualData = de2.getGraphData(males);

        List<ImpressionEntry> allFromImpressions = de.selectAllFrom_IMPRESSION_LOGS();
        List<UserEntry> allFromUsers = de.selectAllFrom_USERS();

        double expected = 0;
        double actual = 0;

        for (ImpressionEntry i : allFromImpressions) {
            if (getUserWithID(allFromUsers, i.getUserId()).getIncome() == Enums.Income.Medium)
                expected++;
        }

        for (Tuple<String,Number> i : actualData) {
            actual+=i.getY().doubleValue();
        }

        assertTrue(expected==actual);
    }

    @Test
    public void testHighIncome() {
        MainController m = new MainController(null, null, null,new Gui.GuiColors());
        FilterSpecs f = m.getInitFilters();
        LinkedList<Enums.Income> incomeList = new LinkedList<Enums.Income>();
        incomeList.add(Enums.Income.High);
        f.setIncomes(incomeList);

        DataExchange de2 = new DataExchange(new DatabaseManager());

        GraphSpecs males = new GraphSpecs(1, "testingcampaign", GraphSpecs.METRICS.NumberImpressions, GraphSpecs.TIME_SPAN.DAY_SPAN, GraphSpecs.BOUNCE_DEF.TIME, f);
        List<Tuple<String,Number>> actualData = de2.getGraphData(males);

        List<ImpressionEntry> allFromImpressions = de.selectAllFrom_IMPRESSION_LOGS();
        List<UserEntry> allFromUsers = de.selectAllFrom_USERS();

        double expected = 0;
        double actual = 0;

        for (ImpressionEntry i : allFromImpressions) {
            if (getUserWithID(allFromUsers, i.getUserId()).getIncome() == Enums.Income.High)
                expected++;
        }

        for (Tuple<String,Number> i : actualData) {
            actual+=i.getY().doubleValue();
        }

        assertTrue(expected==actual);
    }



}
