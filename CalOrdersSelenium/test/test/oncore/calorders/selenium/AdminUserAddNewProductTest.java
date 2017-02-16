package test.oncore.calorders.selenium;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.apache.commons.lang3.RandomStringUtils;

public class AdminUserAddNewProductTest {
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
  public void testAdminUserAddNewProduct() throws Exception {
    driver.get(baseUrl + "/");
    driver.findElement(By.id("login")).click();
    driver.findElement(By.id("userName")).clear();
    driver.findElement(By.id("userName")).sendKeys("joeadmin");
    driver.findElement(By.cssSelector("#loginInnerWrapperDiv > div.oj-flex")).click();
    driver.findElement(By.id("continue")).click();
    driver.findElement(By.id("userMenu")).click();
    driver.findElement(By.id("ui-id-19")).click();
    driver.findElement(By.id("productName")).clear();
    driver.findElement(By.id("productName")).sendKeys("RandomStringUtils.randomAlphabetic(10)");
    driver.findElement(By.id("productOEMName")).clear();
    driver.findElement(By.id("productOEMName")).sendKeys("Travel Mouse");
    driver.findElement(By.id("productOEMPartNumber")).clear();
    driver.findElement(By.id("productOEMPartNumber")).sendKeys("1234");
    driver.findElement(By.id("productSKU")).clear();
    driver.findElement(By.id("productSKU")).sendKeys("abcdef");
    driver.findElement(By.id("productOEMPartNumber")).clear();
    driver.findElement(By.id("productOEMPartNumber")).sendKeys("123456");
    driver.findElement(By.id("productSKU")).clear();
    driver.findElement(By.id("productSKU")).sendKeys("abcdefg");
    driver.findElement(By.id("productPrice")).clear();
    driver.findElement(By.id("productPrice")).sendKeys("150");
    driver.findElement(By.id("productContractDiscount")).clear();
    driver.findElement(By.id("productContractDiscount")).sendKeys("25");
    driver.findElement(By.id("productDescription")).clear();
    driver.findElement(By.id("productDescription")).sendKeys("Mini Travel Mouse");
    driver.findElement(By.id("productFullDesc")).clear();
    driver.findElement(By.id("productFullDesc")).sendKeys("Mini Travel Mouse with Charger");
    driver.findElement(By.id("save")).click();
    driver.findElement(By.id("close")).click();
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