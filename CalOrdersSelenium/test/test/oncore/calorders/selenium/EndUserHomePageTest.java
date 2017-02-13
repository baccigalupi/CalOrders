package test.oncore.calorders.selenium;

import com.thoughtworks.selenium.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.regex.Pattern;

public class EndUserHomePageTest {
	private Selenium selenium;

	@Before
	public void setUp() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, "*chrome", "http://localhost:8000/");
		selenium.start();
	}

	@Test
	public void testEndUserHomePage() throws Exception {
		selenium.open("/");
		selenium.click("id=login");
		selenium.type("id=userName", "rickybobby");
		selenium.click("css=div.oj-panel.oj-flex");
		selenium.click("id=continue");
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}