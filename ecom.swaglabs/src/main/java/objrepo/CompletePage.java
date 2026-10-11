package objrepo;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CompletePage {

    WebDriver driver;

    @FindBy(className = "complete-header")
    private WebElement confirmationMessage;

    @FindBy(id = "back-to-products")
    private WebElement backToProductsButton;

    public CompletePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


	public String getConfirmationMessage() {
	
	    WebDriverWait wait =
	            new WebDriverWait(driver, Duration.ofSeconds(10));
	
	    return wait.until(
	        ExpectedConditions.visibilityOf(confirmationMessage)
	    ).getText();
	}


    public WebElement getBackToProductsButton() {
        return backToProductsButton;
    }

    public void clickBackToProducts() {
        backToProductsButton.click();
    }
}