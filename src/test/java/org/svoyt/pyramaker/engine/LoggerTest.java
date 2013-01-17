package org.svoyt.pyramaker.engine;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class LoggerTest {

	Logger logger;
	
	@Before
	public void setUp() throws Exception {
		logger= new Logger();
	}

	@Test
	public void test() throws InterruptedException {
		logger.write("Test message 1 for logger");
		Thread.sleep(500);
		logger.write("Test message 2 for logger");
		String logData = logger.getLog();
		//System.out.print(logData);
		assertTrue( "Log is not right", logData.contains("Test1")&logData.contains("Test2"));
	}

}
