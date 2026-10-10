package inventoryscript;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import genericutility.BaseClass;
import genericutility.ExcelUtility;
import junit.framework.Assert;
import objrepo.InventoryPage;
import objrepo.ProductItemPage;

public class Inventory extends BaseClass {

    ExcelUtility excel = new ExcelUtility();

    @Test(groups = {"Functional","Positive"})
    public void addProductToCartTest() throws IOException, InterruptedException {

        InventoryPage inventoryPage = new InventoryPage(driver);

        inventoryPage.selectFilter("Price (low to high)");

        String productName = excel.getDataFromExcel("SwagLabsData.xlsx","Sheet1",2,0);

        inventoryPage.addProductToCart(productName);
        Thread.sleep(1000);
    }
    
    @Test(groups = {"Functional","Positive"})
    public void verifyProductNamesAfterSorting() throws InterruptedException {

        InventoryPage inventoryPage = new InventoryPage(driver);

        inventoryPage.selectFilter("Price (low to high)");

        List<String> products = inventoryPage.getProductNames();
        Thread.sleep(1000);

        Assert.assertFalse(products.isEmpty());
    }
    
    @Test(groups = {"Integration","Positive"})
    public void productcartpage() throws IOException, InterruptedException {

        InventoryPage inventoryPage = new InventoryPage(driver);

        inventoryPage.selectFilter("Price (low to high)");

        String productName = excel.getDataFromExcel("SwagLabsData.xlsx","Sheet1",5,0);

        inventoryPage.addProductToCart(productName);
        inventoryPage.clickCart();
        Thread.sleep(1000);
        
        Assert.assertTrue(driver.getCurrentUrl().contains("cart"));
    }
    
    @Test(groups = {"Integration","Positive"})
    public void productDetailsPage() throws IOException, InterruptedException {

        InventoryPage inventoryPage = new InventoryPage(driver);

        String productName = excel.getDataFromExcel("SwagLabsData.xlsx","Sheet1",2,0);

        inventoryPage.clickProduct(productName);
        Thread.sleep(1000);

        Assert.assertTrue(driver.getCurrentUrl().contains("inventory-item"));
    }
    
    @Test(groups = {"System","Positive"})
    public void verifyCompleteAddToCartFlow() throws IOException {

        InventoryPage inventoryPage = new InventoryPage(driver);

        String productName = excel.getDataFromExcel("SwagLabsData.xlsx","Sheet1",4,0);
        
        inventoryPage.clickProduct(productName);

        ProductItemPage productItemPage = new ProductItemPage(driver);

        productItemPage.addToCart();

        productItemPage.clickCart();

        Assert.assertTrue(driver.getCurrentUrl().contains("cart"));
    }
    
    @Test(groups = {"Smoke","Positive"})
    public void verifyInventoryPageLoads() {

        InventoryPage inventoryPage = new InventoryPage(driver);

        Assert.assertFalse(inventoryPage.getInventoryItems().isEmpty());
    }
    
    
    @Test(groups = {"Negative"})
    public void verifyInvalidProductIsNotDisplayed() throws IOException {

        InventoryPage inventoryPage = new InventoryPage(driver);

        wu.takeScreenshot(driver, "InvalidProductNegativeTest");

        Assert.assertFalse(
            inventoryPage.getProductNames()
                        .contains("Invalid Product")
        );
    }

    @Test(groups = {"Negative"})
    public void verifyInventoryProductsHaveValidPrices() {

        InventoryPage inventoryPage = new InventoryPage(driver);

        List<WebElement> products = inventoryPage.getInventoryItems();

        Assert.assertTrue(products.size() > 0);

        for (WebElement product : products) {
            WebElement price = product.findElement(
                    By.className("inventory_item_price")
            );

            Assert.assertTrue(
                    price.isDisplayed() && !price.getText().trim().isEmpty()
            );
        }
    }

    
    
}