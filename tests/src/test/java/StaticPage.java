import org.openqa.selenium.WebDriver;

public class StaticPage extends BasePage {
    public StaticPage(String url, WebDriver driver) {
        super(driver);
        driver.get(url);
    }
}
