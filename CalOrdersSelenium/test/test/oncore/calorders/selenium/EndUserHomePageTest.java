package test.oncore.calorders.selenium;

import com.thoughtworks.selenium.Selenium;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebDriver;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.regex.Pattern;
import static org.apache.commons.lang3.StringUtils.join;

public class EndUserHomePageTest {
	private Selenium selenium;

	@Before
	public void setUp() throws Exception {
                System.setProperty("webdriver.gecko.driver","/home/oncore/workspaces/CalOrders/libs/selenium-java-3.0.1/geckodriver");
		WebDriver driver = new FirefoxDriver();
		String baseUrl = "http://localhost:8000/";
		selenium = new WebDriverBackedSelenium(driver, baseUrl);
	}

	@Test
	public void testEndUserHomePage() throws Exception {
		selenium.open("/");
		selenium.click("id=login");
		selenium.type("id=userName", "rickybobby");
		selenium.click("id=continue");
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}