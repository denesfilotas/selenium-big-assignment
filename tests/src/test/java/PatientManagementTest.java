import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class PatientManagementTest extends AuthenticatedTestBase {

    private static final By userIconSelector = By.cssSelector("i.icon-user.small");

    @Test
    public void testUserIconHoverDisplaysMenu() {

        HomePage page = new HomePage(driver);

        WebElement userIcon = page.waitAndFindElement(userIconSelector);

        // Hover over the icon
        Actions actions = new Actions(driver);
        actions.moveToElement(userIcon).perform();

        WebElement userMenu = page.waitAndFindElement(By.id("user-account-menu"));
        assertTrue(userMenu.isDisplayed());
    }

    @Test
    public void testSearch() {
        FindPatientPage searchPage = new FindPatientPage(driver);
        assertEquals("OpenMRS Electronic Medical Record", searchPage.getTitle());
        assertEquals("Find Patient Record", searchPage.getHeading());

        List<WebElement> searchResults = searchPage.searchForPatient("10000X");
        assertEquals(1, searchResults.size());
        searchResults.get(0).click();

        assertTrue(searchPage.getBodyText().contains("Paul Walker"));

        // Go back to search page
        driver.navigate().back();
        assertEquals("OpenMRS Electronic Medical Record", searchPage.getTitle());
        assertEquals("Find Patient Record", searchPage.getHeading());
    }

    @Test
    public void testAttachmentUpload() {

        PatientPage page = new PatientPage(7, driver);
        page.ensureActiveVisit();

        String randomCaption = page.uploadAttachment("borat.png", "Borat");

        By captionXPath = By.xpath("//p[contains(@class,'ng-binding') and text()='" + randomCaption + "']");
        assertTrue(page.waitAndFindElement(captionXPath).isDisplayed());
    }

}
