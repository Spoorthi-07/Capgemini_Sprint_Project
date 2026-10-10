package objrepo;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPage {

    WebDriver driver;

    @FindBy(id = "first-name")
    private WebElement firstName;

    @FindBy(id = "last-name")
    private WebElement lastName;

    @FindBy(id = "postal-code")
    private WebElement postalCode;

    @FindBy(id = "continue")
    private WebElement continuebtn;

    @FindBy(id = "cancel")
    private WebElement cancelbtn;

    public CheckoutPage(WebDriver driver) {

        this.driver = driver;

        PageFactory.initElements(driver, this);
    }

    public WebElement getFirstName() {

        return firstName;
    }

    public WebElement getLastName() {

        return lastName;
    }

    public WebElement getPostalCode() {

        return postalCode;
    }

    public WebElement clickContinueBtn() {

        return continuebtn;
    }

    public WebElement clickCancelBtn() {

        return cancelbtn;
    }

    public void enterCheckoutDetails(
            String firstNameValue,
            String lastNameValue,
            String zipCodeValue) {

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement firstNameElement =
                wait.until(ExpectedConditions.visibilityOf(firstName));

        firstNameElement.clear();
        firstNameElement.sendKeys(firstNameValue);

        lastName.clear();
        lastName.sendKeys(lastNameValue);

        postalCode.clear();
        postalCode.sendKeys(zipCodeValue);
    }
    
    public void clickContinue() {

        continuebtn.click();
    }
    
    @FindBy(css = ".error-message-container")
    private WebElement errorMessage;

    public String getErrorMessage() {
        return errorMessage.getText();
    }
}