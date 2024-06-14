import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoginTest {
    WebDriver driver;
    private String username;
    private String password;
    private boolean shouldLoginSucceed;

    public LoginTest(String username, String password, boolean shouldLoginSucceed) {
        this.username = username;
        this.password = password;
        this.shouldLoginSucceed = shouldLoginSucceed;
    }

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.opera.driver", "C:\\Users\\doruk\\Desktop\\SENG453HWL\\SENG453Sel\\operadriver.exe");
    }

    @Test
    public void testLogin() {
        OperaOptions options = new OperaOptions();
        options.setBinary("C:\\Users\\doruk\\AppData\\Local\\Programs\\Opera\\opera.exe");
        driver = new OperaDriver(options);

        driver.get("https://my.tedu.edu.tr/home");

        WebElement userIdInput = driver.findElement(By.id("USERNAME_FIELD-inner"));
        userIdInput.sendKeys(username);

        WebElement passwordInput = driver.findElement(By.id("PASSWORD_FIELD-inner"));
        passwordInput.sendKeys(password);

        WebElement submitButton = driver.findElement(By.xpath("//span[@class='sapMBtnContent sapMLabelBold sapUiSraDisplayBeforeLogin']"));
        submitButton.click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        boolean isLoggedIn;

        if (shouldLoginSucceed) {
            try {
                wait.until(ExpectedConditions.urlContains("home?sap-client=001&sap-language=EN#Shell-home"));
                isLoggedIn = true;
            } catch (Exception e) {
                isLoggedIn = false;
            }
        } else {
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("USERNAME_FIELD-inner")));
                isLoggedIn = false;
            } catch (Exception e) {
                isLoggedIn = true;
            }
        }

        Assert.assertEquals(isLoggedIn, shouldLoginSucceed, "Login sonucunun beklenen ile uyuşmadığı durumda hata.");

        driver.quit();
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
