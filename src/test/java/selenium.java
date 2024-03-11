

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileInputStream;

import java.io.IOException;
import java.util.Properties;

public class selenium{

    public static WebDriver driver;
        @Test
        public void firsttc() throws IOException, InterruptedException {

        //Read browser properties from config file
        Properties properties = new Properties();
        FileInputStream stream = new FileInputStream("src/test/java/config/config.properties");
        properties.load(stream);
        String browser = properties.getProperty("browser");


        //Select browser based on config file
            if(browser.equals("chrome")) {
                driver = new ChromeDriver();
            } else if (browser.equals("edge")) {
                driver = new EdgeDriver();
            } else if (browser.equals("firefox")) {
                driver = new FirefoxDriver();
            }
            driver.get("https://shop-now.in/");
            driver.manage().window().maximize();

            //Validation for successfully opening site
            String expectedUrl = "https://shop-now.in/";
            String actualUrl = driver.getCurrentUrl();
            Assert.assertEquals(expectedUrl, actualUrl);

            //Search for item
            driver.findElement(By.xpath("//*[@id=\"shopify-section-sections--16758042919144__header\"]/sticky-header/header/div/details-modal/details/summary")).click();
            driver.findElement(By.id("Search-In-Modal")).sendKeys("bracelet");
            driver.findElement(By.id("Search-In-Modal")).submit();

            //Select item to add to cart
            driver.findElement(By.xpath("//*[@id=\"CardLink--7803972223208\"]")).click();
            String expectedSelectedItem = driver.findElement(By.xpath("//*[@id=\"ProductInfo-template--16758042591464__main\"]/div[1]")).getText();
            driver.findElement(By.name("add")).click();
            Thread.sleep(2000);
            driver.findElement(By.id("cart-notification-button")).click();
            String actualSelectedItem = driver.findElement(By.xpath("//*[@id=\"CartItem-1\"]/td[2]/a")).getText();

            //verify selected item is added to cart
            Assert.assertEquals(expectedSelectedItem,actualSelectedItem);

            //proceed to checkout
            driver.findElement(By.id("checkout")).click();

            //login
            driver.findElement(By.xpath("//*[@id=\"Form0\"]/div[1]/div/div/section/div/div/div/div/div[1]/a")).click();
            String loginId = "knch@gmail.com";
            driver.findElement(By.name("customer[email]")).sendKeys(loginId);
            driver.findElement(By.id("CustomerPassword")).sendKeys("security");
            driver.findElement(By.xpath("//*[@id=\"customer_login\"]/button")).click();

            //verify if logged in
            String loggedinAccount = driver.findElement(By.xpath("//*[@id=\"contact-collapsible\"]/div[2]/div/div/span")).getText();
            Assert.assertEquals(loginId,loggedinAccount);

            //add address
            driver.findElement(By.id("shipping-address1")).sendKeys("college street");
            driver.findElement(By.id("TextField2")).sendKeys("44");
            driver.findElement(By.id("TextField3")).sendKeys("kolkata");
            driver.findElement(By.id("TextField4")).sendKeys("711112");
            Select select = new Select(driver.findElement(By.id("Select1")));
            select.selectByVisibleText("West Bengal");

            driver.findElement(By.id("TextField5")).sendKeys("9876543210");

            //verify order
            String order =driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div/div/div[1]/div[2]/div[2]/div/aside/div/section/div/div[1]/section/div[2]/div[2]/div[2]/div")).getText();
            Assert.assertEquals(actualSelectedItem,order);
            driver.quit();
        }
        }


