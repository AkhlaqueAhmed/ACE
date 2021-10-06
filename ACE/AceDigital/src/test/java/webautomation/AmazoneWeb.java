package webautomation;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class AmazoneWeb {

	public static void main(String[] args) throws InterruptedException {

		//initialise browser drivers
		WebDriverManager.chromedriver().setup();

		ChromeDriver driver = new ChromeDriver();

		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		//open URL
		driver.get("https://www.amazon.com/");

		JavascriptExecutor js = (JavascriptExecutor) driver;

		driver.manage().window().maximize();

		driver.findElement(By.id("nav-link-accountList")).click();

		// Enter email
		driver.findElement(By.id("ap_email")).sendKeys("akhlaque.khan4@gmail.com");

		driver.findElement(By.id("continue")).click();

		//Enter password
		driver.findElement(By.id("ap_password")).sendKeys("Test123@");

		driver.findElement(By.id("signInSubmit")).click();

		System.out.println("The page title is: "+  driver.getTitle());

		// User Verification after log in
		String name = "Akhlaque";

		String[] NameVerify = driver.findElement(By.id("nav-link-accountList-nav-line-1")).getText().split(",");

		if (!NameVerify[1].trim().equalsIgnoreCase(name)) {

			System.out.println(name + " User does not exist.");

			driver.quit();
		}

		// Search the items with name COFFEE DRIP
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys("coffee drip");

		driver.findElement(By.id("nav-search-submit-button")).click();
		
		
		// Scroll down and navigate the second page
		WebElement secPage = driver.findElement(By.cssSelector("ul.a-pagination li:nth-child(3)"));

		js.executeScript("arguments[0].scrollIntoView(true);", secPage);

		Thread.sleep(2000);

		secPage.click();

		Thread.sleep(3000);

		// Click on the product first item that containing text COFFE.
		List<WebElement> prodcut = driver.findElements(By.cssSelector(".a-section.a-spacing-medium"));

		System.out.println("The total number of prodcut is: " + prodcut.size());

		for (int i = 0; i < prodcut.size(); i++) {

			if (prodcut.get(i).getText().contains("Coffe")) {
				prodcut.get(i).click();
				break;
			
			}

		}
		System.out.println("The prodcut containing the text coffee  has been selected!!!");
		
		System.out.println(driver.getTitle());
		
		// Inside the productDetail screen increasing the quantity to 2 before adding it
		// to cart.
		String quantity = "2";

		// Click on quantity drop down.
		driver.findElement(By.id("a-autoid-0-announce")).click();

		List<WebElement> Droplist = driver.findElements(By.cssSelector("li[tabindex='0']"));

		for (int i = 0; i < Droplist.size(); i++) {

			if (Droplist.get(i).getText().equalsIgnoreCase(quantity)) {

				Droplist.get(i).click();
				break;

			}

			// Add the item the cart
			driver.findElement(By.id("add-to-cart-button")).click();

			// click on the cart icon
			driver.findElement(By.id("nav-cart-count")).click();

			// click on proceed to checkout button
			driver.findElement(By.id("sc-buy-box-ptc-button")).click();
			driver.close();
		}

	}

}
