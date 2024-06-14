import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoginTestChrome {
    WebDriver driver;
    private String username;
    private String password;
    private boolean shouldLoginSucceed;

    public LoginTestChrome(String username, String password, boolean shouldLoginSucceed) {
        this.username = username;
        this.password = password;
        this.shouldLoginSucceed = shouldLoginSucceed;
    }

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\doruk\\Desktop\\SENG453HWL\\SENG453Sel\\chromedriver.exe");
    }

    @Test
    public void testLogin() {
        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);

        driver.get("https://my.tedu.edu.tr/home");

        WebElement userIdInput = driver.findElement(By.id("USERNAME_FIELD-inner"));
        userIdInput.sendKeys(username);

        WebElement passwordInput = driver.findElement(By.id("PASSWORD_FIELD-inner"));
        passwordInput.sendKeys(password);

        WebElement submitButton = driver.findElement(By.xpath("//span[contains(text(), 'Log On')]"));
        submitButton.click();

        WebDriverWait wait = new WebDriverWait(driver, 20); // Wait for up to 20 seconds
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
