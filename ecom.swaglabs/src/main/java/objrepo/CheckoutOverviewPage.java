package objrepo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutOverviewPage {

    WebDriver driver;

    @FindBy(id = "finish")
    private WebElement finishbtn;

    @FindBy(id = "cancel")
    private WebElement cancelbtn;

    @FindBy(className = "inventory_item_name")
    private WebElement productName;

    public CheckoutOverviewPage(WebDriver driver) {

        this.driver = driver;

        PageFactory.initElements(driver, this);
    }

    public WebElement clickFinishBtn() {

        return finishbtn;
    }

    public WebElement clickCancelBtn() {

        return cancelbtn;
    }

    public String getProductName() {

        return productName.getText();
    }

    public void clickFinish() {

        finishbtn.click();
    }
}
	

	
	
	
	
	


