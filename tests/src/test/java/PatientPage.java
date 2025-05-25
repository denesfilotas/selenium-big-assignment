import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PatientPage extends BasePage {

    public final String url;

    public PatientPage(int patientId, WebDriver driver) {
        super(driver);
        this.url = "https://o2.openmrs.org/openmrs/coreapps/clinicianfacing/patient.page?patientId=" + patientId;
        this.driver.get(this.url);
    }

    private static final By familyHeadingXPath = By.xpath("//h3[text()='FAMILY']");
    private static final By appointmentSchedulingXPath = By.xpath("//a[@href='#appointmentschedulingui.tab']");
    private static final By captionInputXPath = By.xpath("//textarea[@placeholder='Enter a caption']");
    private static final By uploadButtonXPath = By.xpath("//button[contains(text(),'Upload file')]");

    private void ensurePageLoaded() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(familyHeadingXPath));
    }

    public void ensureActiveVisit() {
        ensurePageLoaded();
        List<WebElement> startVisitButtons = driver.findElements(By.id("org.openmrs.module.coreapps.createVisit"));
        if (!startVisitButtons.isEmpty()) {
            startVisitButtons.get(0).click();
            waitAndFindElement(By.id("start-visit-with-visittype-confirm")).click();
            // Wait until the new page loads
            wait.until(ExpectedConditions.visibilityOfElementLocated(appointmentSchedulingXPath));
            // Go back to the patient page
            driver.get(url);
        }
    }

    public String uploadAttachment(String fileName, String captionPrefix) {
        ensurePageLoaded();
        // Go to attachments page
        driver.findElements(By.id("attachments.attachments.visitActions.default"))
                .stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No visible attachments button"))
                .click();

        // Enter caption
        WebElement caption = waitAndFindElement(captionInputXPath);
        String randomCaption = captionPrefix + UUID.randomUUID();
        caption.sendKeys(randomCaption);

        // Upload file from resources
        WebElement fileInput = driver.findElement(By.className("dz-hidden-input"));
        URL fileUrl = getClass().getClassLoader().getResource(fileName);
        if (fileUrl == null) {
            throw new RuntimeException("File not found in resources");
        }
        File file = new File(fileUrl.getFile());
        if (!file.exists()) {
            throw new RuntimeException("File not found: " + file.getAbsolutePath());
        }
        fileInput.sendKeys(file.getAbsolutePath());

        // Wait until upload button becomes enabled
        WebElement uploadButton = waitAndFindElement(uploadButtonXPath);
        wait.until(ExpectedConditions.elementToBeClickable(uploadButton));

        uploadButton.click();
        return randomCaption;
    }
}
