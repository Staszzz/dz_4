import factory.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import tools.WaitTools;

public class HeadlessTest {
    private final Logger logger = (Logger) LogManager.getLogger(HeadlessTest.class);

    private final String duckUrl = System.getProperty("duck.url");
    private WebDriver driver;
    private WaitTools waitTools;

    @BeforeEach
    public void init() {
        driver = new DriverFactory("--headless").create();
        waitTools = new WaitTools(driver);
        driver.get(duckUrl);
        logger.info("Start driver");
    }

    @AfterEach
    public void driverStop() {
        if (driver != null) {
            driver.quit();
        }
        logger.info("Driver stop");
    }

    @Test
    public void searchResult() {
        driver.findElement(By.cssSelector("input[aria-autocomplete=\"both\"]"))
                .sendKeys("отус" + Keys.ENTER);
        WebElement firstLinkEl = driver
                .findElement(By.xpath("//span[text()='Онлайн‑курсы для профессионалов, дистанционное обучение современным ...']"));
        logger.info("Wait results search");
        waitTools.waitForCondition(ExpectedConditions.stalenessOf(firstLinkEl));
        logger.info("Get text first link");
        String factResult = firstLinkEl.getText();
        logger.info("Check text");
        Assertions.assertEquals("Онлайн‑курсы для профессионалов, дистанционное обучение современным ...", factResult);
        logger.info("the result is as expected");
    }


}