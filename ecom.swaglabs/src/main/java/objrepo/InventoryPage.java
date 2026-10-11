//
//package objrepo;
//
//import java.time.Duration;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;
//import org.openqa.selenium.support.PageFactory;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.Select;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//public class InventoryPage {
//
//    WebDriver driver;
//
//    @FindBy(className = "product_sort_container")
//    private WebElement ddFilter;
//
//    @FindBy(className = "inventory_item")
//    private List<WebElement> inventoryItems;
//
//    @FindBy(className = "inventory_item_name")
//    private List<WebElement> productNames;
//
//    @FindBy(className = "inventory_item_price")
//    private List<WebElement> productPrices;
//
//    @FindBy(className = "shopping_cart_link")
//    private WebElement cartIcon;
//
//    @FindBy(className = "shopping_cart_badge")
//    private List<WebElement> cartBadges;
//
//    public InventoryPage(WebDriver driver) {
//        this.driver = driver;
//        PageFactory.initElements(driver, this);
//    }
//
//    public WebElement getDDFilter() {
//        return ddFilter;
//    }
//
//    public void selectFilter(String filterText) {
//        Select select = new Select(ddFilter);
//        select.selectByVisibleText(filterText);
//    }
//
//    public List<String> getProductNames() {
//        List<String> names = new ArrayList<>();
//
//        for (WebElement element : productNames) {
//            names.add(element.getText().trim());
//        }
//
//        return names;
//    }
//
//    public List<WebElement> getInventoryItems() {
//        return inventoryItems;
//    }
//
//    public void clickProduct(String productName) {
//        for (WebElement product : productNames) {
//            if (product.getText().trim().equals(productName.trim())) {
//                product.click();
//                return;
//            }
//        }
//
//        throw new IllegalArgumentException(
//                "Product not found: " + productName
//        );
//    }
//
//
//    public void addProductToCart(String productName) {
//        WebDriverWait wait =
//                new WebDriverWait(driver, Duration.ofSeconds(10));
//
//        WebElement addButton = wait.until(
//            ExpectedConditions.elementToBeClickable(
//                By.xpath("//div[@class='inventory_item' and .//div[@class='inventory_item_name' and normalize-space()='" 
//                    + productName + "']]//button")
//            )
//        );
//
//        addButton.click();
//    }
//
//
//    public void removeProductFromCart(String productName) {
//        String xpath =
//                "//div[contains(@class,'inventory_item')]"
//                + "[.//div[contains(@class,'inventory_item_name')"
//                + " and normalize-space(.)='"
//                + productName + "']]"
//                + "//button[contains(@id,'remove')]";
//
//        List<WebElement> buttons = driver.findElements(By.xpath(xpath));
//
//        if (buttons.isEmpty()) {
//            throw new IllegalArgumentException(
//                    "Remove button not found for: " + productName
//            );
//        }
//
//        buttons.get(0).click();
//    }
//
//    public void clickCart() {
//        cartIcon.click();
//    }
//
//    public int getCartCount() {
//        if (cartBadges.isEmpty()) {
//            return 0;
//        }
//
//        return Integer.parseInt(cartBadges.get(0).getText().trim());
//    }
//
//    public List<Double> getProductPricesAsNumbers() {
//        List<Double> prices = new ArrayList<>();
//
//        for (WebElement element : productPrices) {
//            String price = element.getText()
//                    .replace("$", "")
//                    .trim();
//
//            prices.add(Double.parseDouble(price));
//        }
//
//        return prices;
//    }
//
//    public String selectRandomProduct() {
//        if (productNames.isEmpty()) {
//            throw new IllegalStateException(
//                    "No products available on inventory page"
//            );
//        }
//
//        int index = new Random().nextInt(productNames.size());
//        String productName = productNames.get(index).getText().trim();
//
//        productNames.get(index).click();
//
//        return productName;
//    }
//}


package objrepo;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class InventoryPage {

    WebDriver driver;
    WebDriverWait wait;

    // Sorting dropdown
    @FindBy(className = "product_sort_container")
    private WebElement ddFilter;

    // All inventory product containers
    @FindBy(className = "inventory_item")
    private List<WebElement> inventoryItems;

    // Product names
    @FindBy(className = "inventory_item_name")
    private List<WebElement> productNames;

    // Product prices
    @FindBy(className = "inventory_item_price")
    private List<WebElement> productPrices;

    // Shopping cart icon
    @FindBy(className = "shopping_cart_link")
    private WebElement cartIcon;

    // Cart badge
    @FindBy(className = "shopping_cart_badge")
    private List<WebElement> cartBadges;


    // Constructor
    public InventoryPage(WebDriver driver) {
        this.driver = driver;

        this.wait = new WebDriverWait(
                driver, Duration.ofSeconds(10));

        PageFactory.initElements(driver, this);
    }


    // Get sorting dropdown
    public WebElement getDDFilter() {
        return ddFilter;
    }


    // Select product sorting option
    public void selectFilter(String filterText) {
        wait.until(
                ExpectedConditions.visibilityOf(ddFilter));

        Select select = new Select(ddFilter);
        select.selectByVisibleText(filterText);
    }


    // Get all product names
    public List<String> getProductNames() {
        List<String> names = new ArrayList<>();

        for (WebElement element : productNames) {
            names.add(element.getText().trim());
        }

        return names;
    }


    // Get inventory product containers
    public List<WebElement> getInventoryItems() {
        return inventoryItems;
    }


    // Click a product by its name
    public void clickProduct(String productName) {

        String xpath =
                "//div[contains(@class,'inventory_item')]"
                + "//div[contains(@class,'inventory_item_name')"
                + " and normalize-space(.)="
                + xpathLiteral(productName.trim()) + "]";

        WebElement product = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath(xpath)));

        product.click();
    }


    // Add a particular product to cart
    public void addProductToCart(String productName) {

        String xpath =
                "//div[contains(@class,'inventory_item')]"
                + "[.//div[contains(@class,'inventory_item_name')"
                + " and normalize-space(.)="
                + xpathLiteral(productName.trim()) + "]]"
                + "//button[contains(@id,'add-to-cart')]";

        WebElement addButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath(xpath)));

        addButton.click();
    }


    // Remove a particular product from inventory/cart
    public void removeProductFromCart(String productName) {

        String xpath =
                "//div[contains(@class,'inventory_item')]"
                + "[.//div[contains(@class,'inventory_item_name')"
                + " and normalize-space(.)="
                + xpathLiteral(productName.trim()) + "]]"
                + "//button[contains(@id,'remove')]";

        WebElement removeButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath(xpath)));

        removeButton.click();
    }


    // Click shopping cart
    public void clickCart() {
        wait.until(
                ExpectedConditions.elementToBeClickable(cartIcon))
                .click();
    }


    // Get cart item count
    public int getCartCount() {

        List<WebElement> badges =
                driver.findElements(
                        By.className("shopping_cart_badge"));

        if (badges.isEmpty()) {
            return 0;
        }

        return Integer.parseInt(
                badges.get(0).getText().trim());
    }


    // Get product prices as double values
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


    // Select and click a random product
    public String selectRandomProduct() {

        List<WebElement> names =
                driver.findElements(
                        By.className("inventory_item_name"));

        if (names.isEmpty()) {
            throw new IllegalStateException(
                    "No products available on inventory page");
        }

        int index = new Random().nextInt(names.size());

        String productName =
                names.get(index).getText().trim();

        names.get(index).click();

        return productName;
    }


    // Safely create an XPath string literal
    private String xpathLiteral(String value) {

        if (!value.contains("'")) {
            return "'" + value + "'";
        }

        if (!value.contains("\"")) {
            return "\"" + value + "\"";
        }

        String[] parts = value.split("'", -1);

        StringBuilder result =
                new StringBuilder("concat(");

        for (int i = 0; i < parts.length; i++) {

            if (i > 0) {
                result.append(", \"'\", ");
            }

            result.append("'")
                    .append(parts[i])
                    .append("'");
        }

        result.append(")");

        return result.toString();
    }
}

