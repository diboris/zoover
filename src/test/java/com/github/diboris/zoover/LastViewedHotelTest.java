package com.github.diboris.zoover;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class LastViewedHotelTest {

    private static final boolean CLOSE_BROWSER_AFTER_TEST = true;

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public static void setupClass() {
        // Configure nice logging in console
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        // Auto-install chrome driver
        ChromeDriverManager.getInstance().setup();
    }

    @Before
    public void setupTest() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @After
    public void tearDown() {
        if (driver != null && CLOSE_BROWSER_AFTER_TEST) {
            driver.quit();
        }
    }

    @Test
    public void should_add_hotel_to_last_viewed() {
        driver.get("https://www.zoover.nl");
        driver.findElement(By.xpath("//input[contains(@class, 'search-input')]")).sendKeys("Hotel Barceló Castillo Beach Resort");
        driver.findElement(By.xpath("//div[@class='search-results']//a[contains(.,'Barceló Castillo Beach Resort')]")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[contains(.,'Hotel Barceló Castillo Beach Resort ****')]")));
        driver.navigate().back();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[contains(.,'Onlangs bekeken')]")));
        Assert.assertEquals("Hotel Barceló Castillo Beach Resort", driver.findElement(By.xpath("//h2[contains(.,'Onlangs bekeken')]/following-sibling::div/div[1]//h2")).getText());
    }
}
