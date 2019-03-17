package JUnitTests;

import Commons.ClickEntry;
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

import static org.junit.Assert.assertEquals;

class JunitTestCases {

    DataExchange dataExchange = new DataExchange(new DatabaseManager());

    @Test
    void clickLogQueryTest() {

        String output = QueryComposer.selectByIdFrom_CLICK_LOGS(2);
        assertEquals("SELECT * FROM CLICK_LOGS WHERE CLICK_LOGS.id=2 LIMIT 1;", output);
    }

    @Test
    void clickLogUserIdQueryTest() {
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

}
