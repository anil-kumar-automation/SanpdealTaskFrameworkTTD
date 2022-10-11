package TestCases;

import Pages.*;
import com.opencsv.exceptions.CsvValidationException;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

public class TestCase {
    WebDriver driver;


    @BeforeTest
    @Parameters("browser")
    //public void launchBrowserAndExecution() throws IOException {
       public void launchBrowserAndExecution(String browser ) throws IOException {
        Properties prop = new Properties();
        String propFilePath = System.getProperty("user.dir") + "/src/main/java/config/Item.properties";
        FileInputStream fis = new FileInputStream(propFilePath);
        prop.load(fis);
        String url = prop.getProperty("url");
       // String browser = prop.getProperty("browser");

        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.setHeadless(true);
            driver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("InternetExplorer")) {
            WebDriverManager.iedriver().setup();
            InternetExplorerOptions ieo = new InternetExplorerOptions();
            driver = new InternetExplorerDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();

        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions fo = new FirefoxOptions();
            fo.setHeadless(false);
            driver = new FirefoxDriver(fo);
        }
        driver.manage().window().maximize();
        driver.get(url);
        Reporter.log("successfully Launched the browser and Navigated Snapdeal", true);
    }


    @Test(priority = 1)
    public void SearchModule() throws InterruptedException {
        SearchItemData si = new SearchItemData(driver);
        si.enterItemInSearch().sendKeys("Bluetooth Headphone");
        Thread.sleep(2000);
        si.ClickSearchButton().click();
        Reporter.log("successfully  search the Bluetooth item", true);

    }

    @Test(priority = 2)
    public void VerifyingSearchDetail() {
        VerifySearchResults vsr = new VerifySearchResults(driver);
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(30));
        w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='search-result-txt-section  marT12']/span[@style='color: #212121; font-weight: normal']")));
        String actualString = vsr.VerifysearchCriteria().getText();
        System.out.println(actualString);
        Assert.assertTrue(actualString.contains("We've got"));
        Reporter.log("successfully verified search result", true);
    }

    @Test(priority = 3)
    public void SortByModule() throws InterruptedException {
        SortBy sb = new SortBy(driver);
        Thread.sleep(2000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='sorting-sec animBounce']/div[@class='sort-drop clearfix']")));
        sb.ClickonSortby().click();
        Thread.sleep(2000);
        sb.clickpopularity().click();
        Reporter.log("successfully Sorted", true);
    }

    @Test(priority = 4)
    public void RangeModule() throws InterruptedException {
        SelectPriceRange spr = new SelectPriceRange(driver);
        spr.clickFristPriceRange().click();
        spr.clickFristPriceRange().clear();
        spr.clickFristPriceRange().sendKeys("700");
        Thread.sleep(3000);
        spr.clickLastPriceRange().click();
        spr.clickLastPriceRange().clear();
        spr.clickLastPriceRange().sendKeys("3000");
        Thread.sleep(3000);
        spr.clickonGOButton();
        Reporter.log("successfully Selected Range 700 to 3000", true);
    }

    @Test(priority = 5)
    public void SavedItemNdPriceInExcel() throws IOException, InterruptedException {
        SaveFristItemNdPriceInExcel sfvt = new SaveFristItemNdPriceInExcel(driver);
        Thread.sleep(3000);
        sfvt.ExcelWriter();
        Reporter.log("successfully Written the Item Data in CSV", true);
    }


    @Test(priority = 6)
    public void FristItem() {
        SelectFristItem sfi = new SelectFristItem(driver);
        sfi.clickOnFristIteam();
        Set<String> windows = driver.getWindowHandles(); //[parentid,childid,subchildId]
        Iterator<String> it = windows.iterator();
        String parentId = it.next();
        String childId = it.next();
        driver.switchTo().window(childId);
        Reporter.log("successfully moved to another tab", true);
    }

    @Test(priority = 7)
    public void ClickOnAddtoCart() {
        AddToCart atc = new AddToCart(driver);
        atc.SelectAddtoCart().click();
        Reporter.log("successfully clicked the AddToCart", true);
    }

    @Test(priority = 8)
    public void VerifyTheAddtoCartIteam() throws InterruptedException {
        VerifyAddToCartItem vai = new VerifyAddToCartItem(driver);
        vai.clickonviewCart().click();
        Thread.sleep(3000);
        String verifiediteam = vai.ChecktheAddCartIteamRnot().getText();
        System.out.println(verifiediteam);
        Assert.assertEquals(verifiediteam, "Shopping Cart (1 Item)");
        Reporter.log("successfully verified AddtoCart item", true);
    }

    @Test(priority = 9)
    public void RemoveAddCartAndVerify() throws InterruptedException {
        RemoveCartAndVerify rcv = new RemoveCartAndVerify(driver);
        rcv.clickRemoveCart().click();
        Thread.sleep(3000);
        String verifedremovecart = rcv.verifyremoveCart().getText();
        System.out.println(verifedremovecart);
        Assert.assertEquals(verifedremovecart, "Shopping Cart is empty!");
        Reporter.log("successfully verified remove cart item", true);

    }

    @AfterTest
    public void quit()  {
        driver.quit();
    }
}
