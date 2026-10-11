//package objrepo;
//
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;
//import org.openqa.selenium.support.PageFactory;
//
//public class LoginPage {
//
//    WebDriver driver;
//
//    @FindBy(id = "user-name")
//    private WebElement username;
//
//    @FindBy(id = "password")
//    private WebElement password;
//
//    @FindBy(id = "login-button")
//    private WebElement loginbtn;
//
//    public LoginPage(WebDriver driver) {
//        this.driver = driver;
//        PageFactory.initElements(driver, this);
//    }
//
//    public WebElement getUsername() {
//        return username;
//    }
//
//    public WebElement getPassword() {
//        return password;
//    }
//
//    public WebElement clickLoginBtn() {
//        return loginbtn;
//    }
//
//    public void getlogin(String un, String pwd) {
//
//        username.sendKeys(un);
//        password.sendKeys(pwd);
//        loginbtn.click();
//    }
//}


package objrepo;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    WebDriver driver;

    @FindBy(id = "user-name")
    private WebElement username;

    @FindBy(id = "password")
    private WebElement password;

    @FindBy(id = "login-button")
    private WebElement loginbtn;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void getlogin(String un, String pwd) {

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(
            ExpectedConditions.visibilityOf(username)
        ).sendKeys(un);

        wait.until(
            ExpectedConditions.visibilityOf(password)
        ).sendKeys(pwd);

        wait.until(
            ExpectedConditions.elementToBeClickable(loginbtn)
        ).click();
    }
}
