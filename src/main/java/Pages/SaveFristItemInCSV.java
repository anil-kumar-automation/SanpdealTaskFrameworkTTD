package Pages;

import com.opencsv.CSVWriter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SaveFristItemInCSV {
        WebDriver driver;

        public SaveFristItemInCSV(WebDriver driver) {
            this.driver = driver;
        }

        public void CsvWriterItems() throws IOException {
            List<WebElement> List =
                    driver.findElements(By.xpath("//div[@class='product-tuple-description ']"));


            CSVWriter csvWriter = new CSVWriter(new FileWriter("StoreItems.csv"));
            for (int i = 0; i < 5; i++) {
                String p_name = List.get(i).getText();
                String set[] = {p_name};
                //String Set1[] = {p_name};
                csvWriter.writeNext(set);
                //csvWriter.writeNext(Set1);
            }

            csvWriter.close();
        }

    }

