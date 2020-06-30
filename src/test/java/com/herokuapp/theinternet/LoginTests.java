package com.herokuapp.theinternet;

//imported classes
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LoginTests {

	@Test(priority = 1, groups = { "positivetests", "smoketest" })
	public void loginTest() {

		// Create driver
		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
		// set path and help selenium webdriver to identify web browser on which test
		// will run

		WebDriver driver = new ChromeDriver();
		// Created driver variable

		 sleep(1000);
		// sleep for 1 seconds

		// Open test page
		String url = "https://the-internet.herokuapp.com/login";
		driver.get(url);
		// get url and open the page

		// sleep(3000);
		// sleep for 3 seconds

		driver.manage().window().maximize();
		// maximising the window

		System.out.println("Starting login test");

		// enter username
		WebElement username = driver.findElement(By.id("username"));
		username.sendKeys("tomsmith");

		// enter password
		WebElement password = driver.findElement(By.name("password"));
		password.sendKeys("SuperSecretPassword!");

		// click login button
		WebElement loginbutton = driver.findElement(By.tagName("button"));
		loginbutton.click();

		// verification

		String expectedurl = "https://the-internet.herokuapp.com/secure";
		String actualurl = driver.getCurrentUrl();
		Assert.assertEquals(actualurl, expectedurl, "Actual page url is not the same as expected");
		
		// new url

		// logout button is visible
		WebElement logoutbutton = driver.findElement(By.xpath("//a[@class='button secondary radius']"));
		Assert.assertTrue(logoutbutton.isDisplayed(), "logout button is not visible");
		
		// succesful login message
		WebElement loginsuccessmessage = driver.findElement(By.cssSelector("div#flash"));
		String extecptedmessage = "You logged into a secure area!";
		String actualmessage = loginsuccessmessage.getText();
		//Assert.assertEquals(extecptedmessage, actualmessage, "Actual message is not the same as expected");
		//Assert.assertTrue(actualmessage.contains(extecptedmessage), "Actual message is not the same as expected");
		Assert.assertTrue(actualmessage.contains(extecptedmessage), "Actual message is not the same as expected");
		
		// close browser
		driver.quit();

	}
	
	@Test(priority = 2, groups = { "negetivetests"})
	@Parameters({ "username", "password", "expectedmessage" })
	public void negetiveLoginTest(String username, String password, String expectedmessage) {

		System.out.println("Starting test with username = " + username + " and password = " + password);
		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
		WebDriver driver = new ChromeDriver();

		driver.get("https://the-internet.herokuapp.com/login");
		driver.manage().window().maximize();

		WebElement usernameElement = driver.findElement(By.id("username"));
		usernameElement.sendKeys(username);

		WebElement passwordElement = driver.findElement(By.xpath("//input[@id='password']"));
		passwordElement.sendKeys(password);

		WebElement loginbutton = driver.findElement(By.xpath("//form[@id='login']/button[@class='radius']"));
		loginbutton.click();

		String expectedurl = "https://the-internet.herokuapp.com/login";
		String actualurl = driver.getCurrentUrl();
		Assert.assertEquals(actualurl, expectedurl, "Test pass");

		WebElement loginfailedmessage = driver.findElement(By.xpath("/html//div[@id='flash']"));
		String actualmsg = loginfailedmessage.getText();
		Assert.assertTrue(actualmsg.contains(expectedmessage), "true...");

		driver.quit();

	}
	
	

	private void sleep(long m) {

		try {
			Thread.sleep(m);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
