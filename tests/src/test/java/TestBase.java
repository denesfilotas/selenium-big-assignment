import java.net.MalformedURLException;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TestBase {

    public WebDriver driver;

    @Before
    public void setup() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-extensions");
        RemoteWebDriver remoteDriver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        remoteDriver.setFileDetector(new LocalFileDetector());
        driver = remoteDriver;
    }

    @After
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}
