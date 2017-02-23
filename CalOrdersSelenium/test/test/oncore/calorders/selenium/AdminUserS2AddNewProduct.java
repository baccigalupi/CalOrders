package test.oncore.calorders.selenium;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class AdminUserS2AddNewProduct {
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
  public void testAdminUserS2AddNewProduct() throws Exception {
    driver.get("http://localhost:8000/");
    driver.findElement(By.id("login")).click();
    driver.findElement(By.id("userName")).clear();
    driver.findElement(By.id("userName")).sendKeys("joeadmin");
    driver.findElement(By.id("continue")).click();
    driver.findElement(By.cssSelector("#ui-id-19 > a.oj-navigationlist-focused-element.oj-navigationlist-item-content > span.oj-navigationlist-item-label")).click();
    driver.findElement(By.id("addProductsButton")).click();
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