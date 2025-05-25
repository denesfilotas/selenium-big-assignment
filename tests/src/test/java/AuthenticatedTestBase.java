import java.net.MalformedURLException;

import org.junit.Before;

public class AuthenticatedTestBase extends TestBase {

    @Before
    @Override
    public void setup() throws MalformedURLException {
        super.setup();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.doLogin();
    }
}
