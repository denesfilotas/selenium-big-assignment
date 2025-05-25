import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class FindPatientPage extends BasePage {

    private final By resultRowXPath = By.xpath("//table[@id='patient-search-results-table']/tbody/tr");

    public FindPatientPage(WebDriver driver) {
        super(driver);
        this.driver.get("https://o2.openmrs.org/openmrs/coreapps/findpatient/findPatient.page?app=coreapps.findPatient");
    }

    public List<WebElement> searchForPatient(String id) {
        WebElement table = driver.findElement(By.id("patient-search-results-table"));
        String oldTableContent = table.getAttribute("innerHTML");
        
        driver.findElement(By.id("patient-search")).sendKeys(id);

        // Wait until the table's innerHTML is different
        wait.until(ExpectedConditions.not(
            driver1 -> driver1.findElement(By.id("patient-search-results-table"))
                            .getAttribute("innerHTML")
                            .equals(oldTableContent)
        ));
        return driver.findElements(resultRowXPath);
    }
}