package test.oncore.calorders.selenium;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class EndUserCompareContinueShoppingTest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    System.setProperty("webdriver.chrome.driver","/home/oncore/workspaces/CalOrders/libs/selenium-java-3.0.1/chromedriver");
    driver = new ChromeDriver();
    baseUrl = "http://localhost:8000/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testEndUserCompareContinueShopping() throws Exception {
    driver.get("http://localhost:8000/");
    driver.findElement(By.id("login")).click();
    driver.findElement(By.id("userName")).clear();
    driver.findElement(By.id("userName")).sendKeys("rickybobby");
    driver.findElement(By.id("continue")).click();
    driver.findElement(By.cssSelector("span.oj-choice-row > #compareProduct")).click();
    driver.findElement(By.cssSelector("#ui-id-29 > div.oj-panel.oj-panel-alt1 > div.oj-flex-item > #compareProduct > div.oj-checkboxset-wrapper > span.oj-choice-row > #compareProduct")).click();
    driver.findElement(By.id("compareProductsButton")).click();
    driver.findElement(By.cssSelector("#ui-id-38 > div.oj-panel.oj-panel-alt1 > div.oj-flex-item > #addToCart")).click();
    driver.findElement(By.id("continueShopping")).click();
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}