import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
        this.driver.get("https://o2.openmrs.org/openmrs/login.htm");
    }

    public HomePage doLogin() {
        waitAndFindElement(By.id("username")).sendKeys("admin");
        waitAndFindElement(By.id("password")).sendKeys("Admin123");

        waitAndFindElement(By.id("Inpatient Ward")).click();
        waitAndFindElement(By.id("loginButton")).click();
        return new HomePage(driver);
    }

}