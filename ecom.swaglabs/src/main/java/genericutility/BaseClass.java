package genericutility;

import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
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
	@BeforeClass
	public void navigateTOApp(@Optional("chrome") String browser) throws Exception {
	    driver = wu.launchBrowser(browser);
	}

    @Parameters("browser")
    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(@Optional("chrome") String browser) throws Exception {

        Reporter.log("Before Method", true);

        driver = wu.launchBrowser(browser);

        driver.get(fu.getDataFromPropertyFile("url"));

        Thread.sleep(2000);

        LoginPage lp = new LoginPage(driver);

        lp.getlogin(
                fu.getDataFromPropertyFile("username"),
                fu.getDataFromPropertyFile("password")
        );

        Thread.sleep(1000);

        lp.clickLoginBtn();

        Thread.sleep(2000);
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod() {

        Reporter.log("After Method", true);

        if (driver != null) {
            driver.quit();
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