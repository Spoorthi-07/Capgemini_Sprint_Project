package objrepo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutInfo {

    WebDriver driver;

    @FindBy(id = "back-to-products")
    private WebElement backToHome;

    @FindBy(id = "generate-pdf-order")
    private WebElement generatePdfbtn;

    @FindBy(className = "complete-header")
    private WebElement confirmationMessage;

    public CheckoutInfo(WebDriver driver) {

        this.driver = driver;

        PageFactory.initElements(driver, this);
    }

    public WebElement getBackToHome() {

        return backToHome;
    }

    public WebElement getGeneratePdfbtn() {

        return generatePdfbtn;
    }

    public String getConfirmationMessage() {

        return confirmationMessage.getText();
    }
}