import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestWebDriverManager {

    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        
        driver = new ChromeDriver();
    }

    @Test
    public void TestWebDriverManager() {
        System.out.println("In test 1");
        driver.get("https://www.linkedin.com/in/harshit-trivedi-9b844163/");
        String expectedPageTitle = "Harshit Trivedi | LinkedIn";
        Assert.assertTrue(driver.getTitle().contains(expectedPageTitle), "Test Failed");
    }

    @AfterClass
    public void tearDown() {
        if(driver!=null)
            driver.quit();
    }
}
