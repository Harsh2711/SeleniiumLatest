import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class GridExampleWithDockewr {

    public static RemoteWebDriver getDriver(String browser) throws MalformedURLException {
        return new RemoteWebDriver(new URL(" http://localhost:4444/wd/hub"), getBrowserCapabilities(browser));
    }

    private static DesiredCapabilities getBrowserCapabilities(String browserType) {
        switch (browserType) {
            case "firefox":
                System.out.println("Opening firefox driver");
                return DesiredCapabilities.firefox();
            case "chrome":
                System.out.println("Opening chrome driver");
                return DesiredCapabilities.chrome();
            case "IE":
                System.out.println("Opening IE driver");
                return DesiredCapabilities.internetExplorer();
            default:
                System.out.println("browser : " + browserType + " is invalid, Launching Firefox as browser of choice..");
                return DesiredCapabilities.firefox();
        }
    }

    public static RemoteWebDriver driver;
    public static String appURL = "http://www.google.com";



    @Test
    public void testGooglePageTitleInFirefox() throws MalformedURLException {
        System.out.println("*******************");
        driver = getDriver("chrome");
        driver.manage().window().maximize();
        driver.navigate().to(appURL);
        String strPageTitle = driver.getTitle();
        System.out.println("Page TIele"+strPageTitle);
        Assert.assertTrue(strPageTitle.equalsIgnoreCase("Google"), "Page title doesn't match");

    }

    @AfterClass
    public void tearDown() {
        if(driver!=null) {
            System.out.println("Closing browser");
            driver.quit();
        }
    }
}
