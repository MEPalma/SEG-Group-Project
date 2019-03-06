package DatabaseManager;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class JunitTestCases {

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

}
