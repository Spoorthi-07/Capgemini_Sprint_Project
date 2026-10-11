package genericutility;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import objrepo.LoginPage;

public class BaseClass {

    public WebDriver driver;

    public WebDriverUtilities wu = new WebDriverUtilities();
    public FileUtility fu = new FileUtility();

    @BeforeSuite(alwaysRun = true)
    public void dbConnection() {
        Reporter.log("Before Suite", true);
    }

    @BeforeTest(alwaysRun = true)
    public void bt() {
        Reporter.log("Before Test", true);
    }

    @Parameters("browser")
    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(
            @Optional("chrome") String browser) throws Exception {

        Reporter.log("Before Method", true);

        driver = wu.launchBrowser(browser);

        try {
            driver.get(fu.getDataFromPropertyFile("url"));

            LoginPage lp = new LoginPage(driver);

            lp.getlogin(
                fu.getDataFromPropertyFile("username"),
                fu.getDataFromPropertyFile("password")
            );

            WebDriverWait wait =
                    new WebDriverWait(driver, Duration.ofSeconds(10));

            wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.className("inventory_container")
                )
            );

            Reporter.log("Login successful", true);

        } catch (Exception e) {
            if (driver != null) {
                driver.quit();
                driver = null;
            }
            throw e;
        }
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod() {

        Reporter.log("After Method", true);

        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    @AfterTest(alwaysRun = true)
    public void at() {
        Reporter.log("After Test", true);
    }

    @AfterSuite(alwaysRun = true)
    public void closeConnection() {
        Reporter.log("After Suite", true);
    }
}
