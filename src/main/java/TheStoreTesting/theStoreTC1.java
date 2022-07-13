package TheStoreTesting;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import java.io.File;
import java.util.concurrent.TimeUnit;

public class theStoreTC1 {

    //Initializing WebDriver
    WebDriver driver;
    String URL = "http://automationpractice.com";
    SoftAssert softAssert = new SoftAssert();

    @BeforeTest
    //Passes browser parameter from the OpenBrowser test method
    @Parameters("browser")
    public void openBrowser(String browser){

        if(browser.equalsIgnoreCase("chrome")){

            //Create a new instance of the Chrome driver
            System.setProperty("webdriver.chrome.driver", "Resources" + File.separator + "chromedriver.exe");
            driver = new ChromeDriver();

        }else if (browser.equalsIgnoreCase("firefox")){

            //Create a new instance of the Firefox driver
            System.setProperty("webdriver.gecko.driver", "Resources" + File.separator  + "geckodriver.exe");
            driver = new FirefoxDriver();
        }

        //Wait for the page to load
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);

        //Gets the URL
        driver.get(URL);

        //Maximizes the browser
        driver.manage().window().maximize();

    }


    @Test(priority = 1)
    public void loginTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 75);

        Thread.sleep(3000);
        //Enter the signin credentials and clicks on the signin button
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//body[1]/div[1]/div[1]/header[1]/div[2]/div[1]/div[1]/nav[1]/div[1]/a[1]")));
        driver.findElement(By.xpath("//body[1]/div[1]/div[1]/header[1]/div[2]/div[1]/div[1]/nav[1]/div[1]/a[1]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//body[1]/div[1]/div[2]/div[1]/div[3]/div[1]/div[1]/div[2]/form[1]/div[1]/div[1]/input[1]")));
        driver.findElement(By.xpath("//body[1]/div[1]/div[2]/div[1]/div[3]/div[1]/div[1]/div[2]/form[1]/div[1]/div[1]/input[1]")).sendKeys("mpumelelo.mbuyazidube@gmail.com");
        driver.findElement(By.xpath("//body[1]/div[1]/div[2]/div[1]/div[3]/div[1]/div[1]/div[2]/form[1]/div[1]/div[2]/span[1]/input[1]")).sendKeys("Dialectic123");
        driver.findElement(By.xpath("//body[1]/div[1]/div[2]/div[1]/div[3]/div[1]/div[1]/div[2]/form[1]/div[1]/p[2]/button[1]/span[1]")).click();


        //Verify that the user is logged in to the correct account (By customer Name)
        String customerAccountName = driver.findElement(By.xpath("//body[1]/div[1]/div[1]/header[1]/div[2]/div[1]/div[1]/nav[1]/div[1]/a[1]/span[1]")).getText();
        softAssert.assertEquals(customerAccountName,"Mpumi Dube", "Incorrect Customer Account");

        //Verify that the store logo is displayed on the home page
        WebElement logo = driver.findElement(By.xpath("//img[@class='logo img-responsive']"));
        softAssert.assertTrue(logo.isDisplayed(), "Logo is not displayed");
        softAssert.assertAll();
    }

    @Test(priority = 2)
    public void addItemToChartTest(){

        WebDriverWait wait = new WebDriverWait(driver, 75);

        //Navigate to the Casual Dresses
        driver.findElement(By.xpath("//body[1]/div[1]/div[1]/header[1]/div[3]/div[1]/div[1]/div[6]/ul[1]/li[2]/a[1]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//body[1]/div[1]/div[2]/div[1]/div[3]/div[1]/div[1]/div[1]/ul[1]/li[1]/a[1]")));
        driver.findElement(By.xpath("//body[1]/div[1]/div[2]/div[1]/div[3]/div[1]/div[1]/div[1]/ul[1]/li[1]/a[1]")).click();

        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.scrollBy(0,600)");

        //Hover over the Casual dress and add to chart
        WebElement ele = driver.findElement(By.xpath("//body[1]/div[1]/div[2]/div[1]/div[3]/div[2]/ul[1]/li[1]/div[1]/div[1]/div[1]"));
        Actions act = new Actions(driver);
        act.moveToElement(ele).perform();
        driver.findElement(By.xpath("//body[1]/div[1]/div[2]/div[1]/div[3]/div[2]/ul[1]/li[1]/div[1]/div[2]/div[2]/a[1]/span[1]")).click();

        //Verify that a correct message is displayed for the added product
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html[1]/body[1]/div[1]/div[1]/header[1]/div[3]/div[1]/div[1]/div[4]/div[1]/div[1]/h2[1]")));
        String message = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/header[1]/div[3]/div[1]/div[1]/div[4]/div[1]/div[1]/h2[1]")).getText();
        softAssert.assertEquals(message, "Casual Printed Dress is successfully added to your shopping cart", "The message is incorrect");
        softAssert.assertAll();

    }

    @Test(priority = 3)
    public void urlTest(){
        //Storing URL in String variable
        String actualURL = driver.getCurrentUrl();
        //Verify that the current url is correct
       Assert.assertEquals(actualURL, "http://automationpractice.com/index.php?id_category=9&controller=category", "URL's don't match");

    }

    @AfterTest
    public void closeBrowser(){

        //Closes the browser
        driver.close();
    }
}
