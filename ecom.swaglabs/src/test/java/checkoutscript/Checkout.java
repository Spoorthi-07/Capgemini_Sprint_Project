package checkoutscript;

import java.io.IOException;

import org.testng.annotations.Test;

import genericutility.BaseClass;
import genericutility.ExcelUtility;
import org.testng.Assert;
import objrepo.CartPage;
import objrepo.CheckoutOverviewPage;
import objrepo.CheckoutPage;
import objrepo.CompletePage;
import objrepo.InventoryPage;
import objrepo.ProductItemPage;

public class Checkout extends BaseClass {
	
	ExcelUtility eu = new ExcelUtility();
	
	@Test(groups = {"Functional", "Positive"})
	public void verifyCheckoutInformation() throws IOException {

	    InventoryPage inventoryPage = new InventoryPage(driver);

	    String productName =eu.getDataFromExcel("SwagLabsData.xlsx", "Sheet1", 2, 0);

	    String firstName =eu.getDataFromExcel("SwagLabsData.xlsx", "Sheet2", 1, 0);

	    String lastName =eu.getDataFromExcel("SwagLabsData.xlsx", "Sheet2", 1, 1);

	    String zipCode =eu.getDataFromExcel("SwagLabsData.xlsx", "Sheet2", 1, 2);

	    inventoryPage.addProductToCart(productName);
	    inventoryPage.clickCart();

	    CartPage cartPage = new CartPage(driver);
	    cartPage.clickCheckout();

	    CheckoutPage checkoutPage = new CheckoutPage(driver);

	    checkoutPage.enterCheckoutDetails(firstName,lastName,zipCode);

	    checkoutPage.clickContinue();

	    Assert.assertTrue(
	            driver.getCurrentUrl().contains("checkout-step-two")
	    );
	}
	

	
	@Test(groups = {"Integration", "Positive"})
	public void verifyCartToCheckoutIntegration() throws IOException {

	    InventoryPage inventoryPage = new InventoryPage(driver);

	    String productName =
	            eu.getDataFromExcel("SwagLabsData.xlsx", "Sheet1", 2, 0);

	    inventoryPage.addProductToCart(productName);
	    inventoryPage.clickCart();

	    CartPage cartPage = new CartPage(driver);

	    Assert.assertTrue(
	            cartPage.isProductPresent(productName)
	    );

	    cartPage.clickCheckout();

	    Assert.assertTrue(
	            driver.getCurrentUrl().contains("checkout-step-one")
	    );
	}
	
	@Test(groups = {"Integration", "Positive"})
	public void verifyCheckoutToOverviewIntegration() throws IOException {

	    InventoryPage inventoryPage = new InventoryPage(driver);

	    String productName =
	            eu.getDataFromExcel("SwagLabsData.xlsx", "Sheet1", 2, 0);

	    String firstName =
	            eu.getDataFromExcel("SwagLabsData.xlsx", "Sheet2", 1, 0);

	    String lastName =
	            eu.getDataFromExcel("SwagLabsData.xlsx", "Sheet2", 1, 1);

	    String zipCode =
	            eu.getDataFromExcel("SwagLabsData.xlsx", "Sheet2", 1, 2);

	    inventoryPage.addProductToCart(productName);
	    inventoryPage.clickCart();

	    CartPage cartPage = new CartPage(driver);
	    cartPage.clickCheckout();

	    CheckoutPage checkoutPage = new CheckoutPage(driver);

	    checkoutPage.enterCheckoutDetails(
	            firstName,
	            lastName,
	            zipCode
	    );

	    checkoutPage.clickContinue();

	    CheckoutOverviewPage overviewPage =
	            new CheckoutOverviewPage(driver);

	    Assert.assertEquals(
	            overviewPage.getProductName(),
	            productName
	    );
	}
	
	@Test(groups = {"System", "Positive"})
	public void verifyCompletePurchaseFlow() throws IOException {

	    InventoryPage inventoryPage = new InventoryPage(driver);

	    String productName =
	            eu.getDataFromExcel("SwagLabsData.xlsx", "Sheet1", 2, 0);

	    String firstName =
	            eu.getDataFromExcel("SwagLabsData.xlsx", "Sheet2", 1, 0);

	    String lastName =
	            eu.getDataFromExcel("SwagLabsData.xlsx", "Sheet2", 1, 1);

	    String zipCode =
	            eu.getDataFromExcel("SwagLabsData.xlsx", "Sheet2", 1, 2);

	    inventoryPage.clickProduct(productName);

	    ProductItemPage productItemPage =
	            new ProductItemPage(driver);

	    productItemPage.addToCart();
	    productItemPage.clickCart();

	    CartPage cartPage = new CartPage(driver);

	    Assert.assertTrue(cartPage.isProductPresent(productName));

	    cartPage.clickCheckout();

	    CheckoutPage checkoutPage =new CheckoutPage(driver);

	    checkoutPage.enterCheckoutDetails(firstName,lastName,zipCode);
	            
	    checkoutPage.clickContinue();

	    CheckoutOverviewPage overviewPage =
	            new CheckoutOverviewPage(driver);

	    Assert.assertEquals( overviewPage.getProductName(),productName);

	    overviewPage.clickFinish();

	    CompletePage completePage =new CompletePage(driver);

	    Assert.assertTrue(completePage.getConfirmationMessage().contains("Thank you"));
	}
	
	@Test(groups = {"System", "Positive"})
	public void verifyMultipleProductPurchaseFlow() throws IOException {

	    InventoryPage inventoryPage = new InventoryPage(driver);

	    String product1 =
	            eu.getDataFromExcel("SwagLabsData.xlsx", "Sheet1", 2, 0);

	    String product2 =
	            eu.getDataFromExcel("SwagLabsData.xlsx", "Sheet1", 3, 0);

	    String firstName =
	            eu.getDataFromExcel("SwagLabsData.xlsx", "Sheet2", 2, 0);

	    String lastName =
	            eu.getDataFromExcel("SwagLabsData.xlsx", "Sheet2", 2, 1);

	    String zipCode =
	            eu.getDataFromExcel("SwagLabsData.xlsx", "Sheet2", 2, 2);

	    inventoryPage.addProductToCart(product1);
	    inventoryPage.addProductToCart(product2);

	    inventoryPage.clickCart();

	    CartPage cartPage = new CartPage(driver);

	    Assert.assertTrue(
	            cartPage.isProductPresent(product1)
	    );

	    Assert.assertTrue(
	            cartPage.isProductPresent(product2)
	    );

	    cartPage.removeProduct(product1);

	    Assert.assertFalse(
	            cartPage.isProductPresent(product1)
	    );

	    Assert.assertTrue(
	            cartPage.isProductPresent(product2)
	    );

	    cartPage.clickCheckout();

	    CheckoutPage checkoutPage =
	            new CheckoutPage(driver);

	    checkoutPage.enterCheckoutDetails(firstName,lastName,zipCode);

	    checkoutPage.clickContinue();

	    CheckoutOverviewPage overviewPage =new CheckoutOverviewPage(driver);

	    Assert.assertEquals(overviewPage.getProductName(),product2
	    );

	    overviewPage.clickFinish();

	    CompletePage completePage =new CompletePage(driver);

	    Assert.assertTrue(completePage.getConfirmationMessage().contains("Thank you"));
	}
	@Test(groups = {"Smoke", "Positive"})
	public void verifyCheckoutCanProceed() throws IOException {

	    InventoryPage inventoryPage = new InventoryPage(driver);

	    String productName =eu.getDataFromExcel("SwagLabsData.xlsx", "Sheet1", 2, 0);

	    String firstName =eu.getDataFromExcel("SwagLabsData.xlsx", "Sheet2", 1, 0);

	    String lastName =eu.getDataFromExcel("SwagLabsData.xlsx", "Sheet2", 1, 1);

	    String zipCode =eu.getDataFromExcel("SwagLabsData.xlsx", "Sheet2", 1, 2);

	    inventoryPage.addProductToCart(productName);
	    inventoryPage.clickCart();

	    CartPage cartPage = new CartPage(driver);
	    cartPage.clickCheckout();

	    CheckoutPage checkoutPage = new CheckoutPage(driver);

	    checkoutPage.enterCheckoutDetails(firstName,lastName,zipCode);

	    checkoutPage.clickContinue();

	    Assert.assertTrue(
	            driver.getCurrentUrl().contains("checkout-step-two")
	    );
	}
	
	@Test(groups = {"Functional", "Negative"})
	public void verifyCheckoutWithMissingFirstName() throws IOException {

	    InventoryPage inventoryPage = new InventoryPage(driver);

	    String productName =
	            eu.getDataFromExcel("SwagLabsData.xlsx", "Sheet1", 2, 0);

	    String firstName =
	            eu.getDataFromExcel("SwagLabsData.xlsx", "Sheet2", 4, 0);

	    String lastName =
	            eu.getDataFromExcel("SwagLabsData.xlsx", "Sheet2", 4, 1);

	    String zipCode =
	            eu.getDataFromExcel("SwagLabsData.xlsx", "Sheet2", 4, 2);

	    inventoryPage.addProductToCart(productName);
	    inventoryPage.clickCart();

	    CartPage cartPage = new CartPage(driver);
	    cartPage.clickCheckout();

	    CheckoutPage checkoutPage = new CheckoutPage(driver);

	    checkoutPage.enterCheckoutDetails(
	            firstName,
	            lastName,
	            zipCode
	    );
		checkoutPage.clickContinue();
		
		Assert.assertTrue(
		    checkoutPage.getErrorMessage()
		        .contains("First Name is required"),
		    "Expected validation error for missing first name"
		);
		
		wu.takeScreenshot(driver, "MissingFirstName");

	}
    
}