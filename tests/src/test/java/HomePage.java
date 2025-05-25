import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage {

    private final By logoutXPath = By.xpath("//li[@class='nav-item logout']/a[contains(text(), 'Logout')]");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String getHeading() {
        return getHeading(4);
    }

    public LoginPage doLogout() {
        WebElement logoutButton = waitAndFindElement(logoutXPath);
        logoutButton.click();
        return new LoginPage(driver);
    }
}