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

public class EndUserViewProductDetailsWithConfirmOrderTest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    System.setProperty("webdriver.chrome.driver","/usr/local/bin/chromedriver");
    driver = new ChromeDriver();
    baseUrl = "http://calorderstest.oncorellc.com:8080/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testEndUserViewProductDetailsWithConfirmOrder() throws Exception {
    driver.get("http://calorderstest.oncorellc.com:8080/");
    driver.findElement(By.id("login")).click();
    driver.findElement(By.id("userName")).clear();
    driver.findElement(By.id("userName")).sendKeys("rickybobby");
    driver.findElement(By.id("continue")).click();
    driver.findElement(By.cssSelector("input.oj-inputtext-input.oj-component-initnode")).clear();
    driver.findElement(By.cssSelector("input.oj-inputtext-input.oj-component-initnode")).sendKeys("sff");
    driver.findElement(By.id("productImage6")).click();
    driver.findElement(By.id("addToCart")).click();
    driver.findElement(By.id("cart")).click();
    driver.findElement(By.xpath("(//button[@id='placeOrder'])[2]")).click();
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