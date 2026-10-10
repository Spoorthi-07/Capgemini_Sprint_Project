package objrepo;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage {

    WebDriver driver;

    @FindBy(id = "continue-shopping")
    private WebElement Continue;

    @FindBy(id = "checkout")
    private WebElement Checkout;

    @FindBy(className = "shopping_cart_badge")
    private WebElement cartCount;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public WebElement clickContinue() {
        return Continue;
    }

    public void clickCheckout() {
        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(
                ExpectedConditions.elementToBeClickable(Checkout)
        ).click();
    }

    public String getCartCount() {
        return cartCount.getText();
    }


	public boolean isProductDisplayed(String productName) {
	    return driver.findElements(
	        By.xpath(
	            "//div[contains(@class,'cart_item')]"
	            + "//div[contains(@class,'inventory_item_name')"
	            + " and normalize-space(.)='" + productName + "']"
	        )
	    ).stream().anyMatch(WebElement::isDisplayed);
	}
	
	public boolean isProductPresent(String productName) {
	    return !driver.findElements(
	        By.xpath(
	            "//div[contains(@class,'cart_item')]"
	            + "//div[contains(@class,'inventory_item_name')"
	            + " and normalize-space(.)='" + productName + "']"
	        )
	    ).isEmpty();
	}


    public void removeProduct(String productName) {

        String xpath = "//div[@class='cart_item']" +
                       "[.//div[@class='inventory_item_name' and text()='" +
                       productName + "']]//button";

        driver.findElement(By.xpath(xpath)).click();
    }
}
