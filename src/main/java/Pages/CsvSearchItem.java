package Pages;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.FileReader;
import java.io.IOException;

public class CsvSearchItem {

    WebDriver driver;

    @FindBy(xpath = "//input[@class='col-xs-20 searchformInput keyword']")
    WebElement itemSearch;

    @FindBy(css = "span.searchTextSpan")
    WebElement SearchButton;

    public CsvSearchItem(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void enterItemInSearch() throws IOException, CsvValidationException {
        CSVReader reader;
        reader = new CSVReader(new FileReader("SearchItem.csv"));
        String[] cell = reader.readNext();


        String Keyword = null;
        while ((cell = reader.readNext()) != null) {
            for (int i = 0; i < 1; i++) {
                Keyword = cell[i];
                //driver.findElement(By.id("inputValEnter")).sendKeys(Keyword);
            }
        }
        itemSearch.sendKeys(Keyword);
    }

    public WebElement ClickSearchButton() {
        return SearchButton;
    }
}


