package objrepo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HamburgerPage {

    WebDriver driver;

    @FindBy(id = "react-burger-menu-btn")
    private WebElement menuButton;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutButton;
    
    @FindBy(id = "reset_sidebar_link")
    private WebElement resetbtn;
    
    public HamburgerPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickMenu() {
        menuButton.click();
    }

    public void clickLogout() {
        logoutButton.click();
    }

    public void logout() {
        clickMenu();
        clickLogout();
    }
    
    public void clickreset() {
    	resetbtn.click();
    }
}