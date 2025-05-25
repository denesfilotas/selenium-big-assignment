import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AuthenticationTest extends TestBase {
    @Test
    public void testAuthenticationFlow() {
        LoginPage page = new LoginPage(driver);

        assertEquals("Login", page.getTitle());

        HomePage homePage = page.doLogin();

        assertEquals("Home", homePage.getTitle());
        assertEquals("Logged in as Super User (admin) at Inpatient Ward.", homePage.getHeading());
        
        LoginPage loggedOutPage = homePage.doLogout();
        assertEquals("Login", loggedOutPage.getTitle());
    }

}
