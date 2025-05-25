
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 15);
    }

    protected WebElement waitAndFindElement(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return driver.findElement(locator);
    }

    public String getBodyText() {
        WebElement bodyElement = waitAndFindElement(By.tagName("body"));
        return bodyElement.getText();
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public String getHeading(int level) {
        WebElement heading = waitAndFindElement(By.tagName("h" + level));
        return heading.getText();
    }

    /** Shortcut as most pages have an h2 tag as the heading */
    public String getHeading() {
        return getHeading(2);
    }

}
