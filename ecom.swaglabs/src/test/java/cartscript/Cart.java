package cartscript;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import genericutility.BaseClass;
import genericutility.ExcelUtility;
import objrepo.CartPage;
import objrepo.InventoryPage;

public class Cart extends BaseClass {
	
	ExcelUtility eu = new ExcelUtility();


	@Test(groups = {"Integration", "Positive"})
	public void verifyCartToCheckoutIntegration() throws IOException {
	
	    InventoryPage inventoryPage = new InventoryPage(driver);
	
	    String productName = eu.getDataFromExcel(
	        "SwagLabsData.xlsx", "Sheet1", 2, 0
	    );
	
	    Assert.assertFalse(
	        productName.trim().isEmpty(),
	        "Product name is empty in Excel"
	    );
	
	    inventoryPage.addProductToCart(productName);
	
	    Assert.assertEquals(
	        inventoryPage.getCartCount(),
	        1,
	        "Product was not added to the cart"
	    );
	
	    inventoryPage.clickCart();
	
	    CartPage cartPage = new CartPage(driver);
	
	    Assert.assertTrue(
	        cartPage.isProductPresent(productName),
	        "Product not found in cart: " + productName
	    );
	
	    cartPage.clickCheckout();
	
	    Assert.assertTrue(
	        driver.getCurrentUrl().contains("checkout-step-one"),
	        "Checkout information page did not open"
	    );
	}

	

	@Test(groups = {"Functional", "Positive"})
	public void verifyEmptyCartContainsNoProducts() {
	
	    InventoryPage inventoryPage = new InventoryPage(driver);
	
	    Assert.assertEquals(
	        inventoryPage.getCartCount(),
	        0,
	        "Cart should initially contain zero products"
	    );
	
	    inventoryPage.clickCart();
	
	    CartPage cartPage = new CartPage(driver);
	
	    Assert.assertEquals(
	        cartPage.getCartProductCount(),
	        0,
	        "Cart should not display any product rows"
	    );
	}

	
//	   @Test(groups = {"Integration", "Positive"})
//	    public void verifyInventoryToCart() throws IOException {
//
//	        InventoryPage inventoryPage = new InventoryPage(driver);
//
//	        String productName =
//	                eu.getDataFromExcel("SwagLabsData.xlsx", "Sheet1", 2, 0);
//
//	        inventoryPage.addProductToCart(productName);
//
//	        inventoryPage.clickCart();
//
//	        CartPage cartPage = new CartPage(driver);
//
//	        Assert.assertTrue(
//	                cartPage.isProductPresent(productName)
//	        );
//	    }
	   

	   
	   @Test(groups = {"System", "Positive"})
	   public void verifyCartToCheckoutFlow() throws IOException {

	       InventoryPage inventoryPage = new InventoryPage(driver);

	       String productName =
	               eu.getDataFromExcel("SwagLabsData.xlsx", "Sheet1", 2, 0);

	       inventoryPage.addProductToCart(productName);
	       inventoryPage.clickCart();

	       CartPage cartPage = new CartPage(driver);

	       Assert.assertTrue(cartPage.isProductPresent(productName));

	       cartPage.clickCheckout();

	       Assert.assertTrue(
	               driver.getCurrentUrl().contains("checkout-step-one")
	       );
	   }
	   
	   @Test(groups = {"Smoke"})
	   public void verifyCartPageLoads() {

	       InventoryPage inventoryPage = new InventoryPage(driver);

	       inventoryPage.clickCart();

	       Assert.assertTrue(
	           driver.getCurrentUrl().contains("cart")
	       );
	   }
	   
	   @Test(groups = {"Negative"})
	   public void verifyCheckoutWithEmptyCart() throws IOException {

	       InventoryPage inventoryPage = new InventoryPage(driver);

	       inventoryPage.clickCart();

	       CartPage cartPage = new CartPage(driver);

	       wu.takeScreenshot(driver, "EmptyCartNegativeTest");

	       Assert.assertFalse(
	           cartPage.isProductPresent("Sauce Labs Bike Light")
	       );
	   }
}