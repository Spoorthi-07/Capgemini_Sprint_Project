
package objrepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class InventoryPage {

    WebDriver driver;

    @FindBy(className = "product_sort_container")
    private WebElement ddFilter;

    @FindBy(className = "inventory_item")
    private List<WebElement> inventoryItems;

    @FindBy(className = "inventory_item_name")
    private List<WebElement> productNames;

    @FindBy(className = "inventory_item_price")
    private List<WebElement> productPrices;

    @FindBy(className = "shopping_cart_link")
    private WebElement cartIcon;

    @FindBy(className = "shopping_cart_badge")
    private List<WebElement> cartBadges;

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public WebElement getDDFilter() {
        return ddFilter;
    }

    public void selectFilter(String filterText) {
        Select select = new Select(ddFilter);
        select.selectByVisibleText(filterText);
    }

    public List<String> getProductNames() {
        List<String> names = new ArrayList<>();

        for (WebElement element : productNames) {
            names.add(element.getText().trim());
        }

        return names;
    }

    public List<WebElement> getInventoryItems() {
        return inventoryItems;
    }

    public void clickProduct(String productName) {
        for (WebElement product : productNames) {
            if (product.getText().trim().equals(productName.trim())) {
                product.click();
                return;
            }
        }

        throw new IllegalArgumentException(
                "Product not found: " + productName
        );
    }

    public void addProductToCart(String productName) {
        String xpath =
                "//div[contains(@class,'inventory_item')]"
                + "[.//div[contains(@class,'inventory_item_name')"
                + " and normalize-space(.)='"
                + productName + "']]"
                + "//button[contains(@id,'add-to-cart')]";

        List<WebElement> buttons = driver.findElements(By.xpath(xpath));

        if (buttons.isEmpty()) {
            throw new IllegalArgumentException(
                    "Add to cart button not found for: " + productName
            );
        }

        buttons.get(0).click();
    }

    public void removeProductFromCart(String productName) {
        String xpath =
                "//div[contains(@class,'inventory_item')]"
                + "[.//div[contains(@class,'inventory_item_name')"
                + " and normalize-space(.)='"
                + productName + "']]"
                + "//button[contains(@id,'remove')]";

        List<WebElement> buttons = driver.findElements(By.xpath(xpath));

        if (buttons.isEmpty()) {
            throw new IllegalArgumentException(
                    "Remove button not found for: " + productName
            );
        }

        buttons.get(0).click();
    }

    public void clickCart() {
        cartIcon.click();
    }

    public int getCartCount() {
        if (cartBadges.isEmpty()) {
            return 0;
        }

        return Integer.parseInt(cartBadges.get(0).getText().trim());
    }

    public List<Double> getProductPricesAsNumbers() {
        List<Double> prices = new ArrayList<>();

        for (WebElement element : productPrices) {
            String price = element.getText()
                    .replace("$", "")
                    .trim();

            prices.add(Double.parseDouble(price));
        }

        return prices;
    }

    public String selectRandomProduct() {
        if (productNames.isEmpty()) {
            throw new IllegalStateException(
                    "No products available on inventory page"
            );
        }

        int index = new Random().nextInt(productNames.size());
        String productName = productNames.get(index).getText().trim();

        productNames.get(index).click();

        return productName;
    }
}
