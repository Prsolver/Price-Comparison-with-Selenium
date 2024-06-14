import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PriceComparisonTest {
    WebDriver driver;
    Map<String, Double> prices = new HashMap<>();

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.opera.driver", "C:\\Users\\doruk\\Desktop\\SENG453HWL\\SENG453Sel\\operadriver.exe");

        OperaOptions options = new OperaOptions();
        options.setBinary("C:\\Users\\doruk\\AppData\\Local\\Programs\\Opera\\opera.exe");
        options.addArguments("--remote-debugging-port=9222");

        driver = new OperaDriver(options);
    }

    @Test(priority = 1)
    public void testApplePrice() {
        driver.get("https://www.apple.com/tr/shop/buy-iphone/iphone-15-pro");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        try {
            List<WebElement> dimensionSelectors = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".rc-dimension-selector-row.form-selector")));
            if (dimensionSelectors.size() > 1) {
                dimensionSelectors.get(1).click();
            } else {
                System.out.println("Dimension selector not found or not enough elements.");
                return;
            }

            List<WebElement> colorNavItems = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".colornav-item")));
            if (colorNavItems.size() > 3) {
                colorNavItems.get(3).click();
            } else {
                System.out.println("Color nav item not found or not enough elements.");
                return;
            }

            Thread.sleep(2000);

            List<WebElement> priceElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".price-point.price-point-fullPrice-short")));
            double highestPrice = 0;
            for (WebElement priceElement : priceElements) {
                priceElement = driver.findElement(By.cssSelector(".price-point.price-point-fullPrice-short"));
                String priceText = priceElement.getText().replace("₺", "").replace(".", "").replace(",", "").replace(" TL", "").trim();
                double price = Double.parseDouble(priceText) / 100;  // Kuruşları ayırmak için 100'e bölüyoruz
                if (price > highestPrice) {
                    highestPrice = price;
                }
            }
            System.out.println("Apple Price: " + highestPrice + " TL");
            prices.put("Apple", highestPrice);
        } catch (Exception e) {
            System.out.println("Error fetching Apple price: " + e.getMessage());
        }
    }

    @Test(priority = 2)
    public void testHepsiburadaPrice() {
        driver.get("https://www.hepsiburada.com/iphone-15-pro-max-1-tb-p-HBCV00004XA8GH");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        try {
            WebElement priceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".price.hepsiburada.price-new-old")));
            String priceText = priceElement.getText().replace("₺", "").replace(".", "").replace(",", "").replace(" TL", "").trim();
            double price = Double.parseDouble(priceText) / 100;  // Kuruşları ayırmak için 100'e bölüyoruz
            System.out.println("Hepsiburada Price: " + price + " TL");
            prices.put("Hepsiburada", price);
        } catch (Exception e) {
            System.out.println("Error fetching Hepsiburada price: " + e.getMessage());
        }
    }

    @Test(priority = 3)
    public void testVatanPrice() {
        driver.get("https://www.vatanbilgisayar.com/iphone-15-pro-max-1-tb-akilli-telefon-natural-titanium.html");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        try {
            WebElement priceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".product-detail-price-big")));
            String priceText = priceElement.getText().replace("TL", "").replace(".", "").replace(",", "").replace(" TL", "").replaceAll("\\s", "").trim();
            double price = Double.parseDouble(priceText);
            System.out.println("Vatan Bilgisayar Price: " + price + " TL");
            prices.put("Vatan", price);
        } catch (Exception e) {
            System.out.println("Error fetching Vatan price: " + e.getMessage());
        }
    }

    @Test(priority = 4)
    public void testPozitifTeknolojiPrice() {
        driver.get("https://www.pt.com.tr/iphone-15-pro-max-1tb-naturel-titanyum-mu7j3tua");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        try {
            WebElement priceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".col-md-12.price.pricePTpro")));
            String priceText = priceElement.getText().replace("₺", "").replace(".", "").replace(",", "").replace(" TL", "").replace(" KDV Dahil", "").trim();
            double price = Double.parseDouble(priceText) / 100;  // Kuruşları ayırmak için 100'e bölüyoruz
            System.out.println("Pozitif Teknoloji Price: " + price + " TL");
            prices.put("Pozitif Teknoloji", price);
        } catch (Exception e) {
            System.out.println("Error fetching Pozitif Teknoloji price: " + e.getMessage());
        }
    }

    @Test(priority = 5)
    public void testGurgenclerPrice() {
        driver.get("https://www.gurgencler.com.tr/iphone-15-pro-max-1-tb-naturel-titanyum-mu7j3tu-a");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        try {
            WebElement priceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".price")));
            String priceText = priceElement.getText().replace("₺", "").replace(".", "").replace(",", "").replace(" TL", "").trim();
            double price = Double.parseDouble(priceText) / 100;  // Kuruşları ayırmak için 100'e bölüyoruz
            System.out.println("Gürgençler Price: " + price + " TL");
            prices.put("Gürgençler", price);
        } catch (Exception e) {
            System.out.println("Error fetching Gürgençler price: " + e.getMessage());
        }
    }

    @Test(priority = 6)
    public void testBeymenPrice() {
        driver.get("https://www.beymen.com/tr/p_apple-iphone-15-pro-max-1tb-siyah-titanyum-akilli-cep-telefonu-mu7g3tua_1344788");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        try {
            WebElement priceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".m-price__new")));
            String priceText = priceElement.getText().replace("₺", "").replace(".", "").replace(",", "").replace(" TL", "").trim();
            double price = Double.parseDouble(priceText);
            System.out.println("Beymen Price: " + price + " TL");
            prices.put("Beymen", price);
        } catch (Exception e) {
            System.out.println("Error fetching Beymen price: " + e.getMessage());
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        comparePrices();
    }

    public void comparePrices() {
        if (prices.isEmpty()) {
            System.out.println("No prices found.");
            return;
        }

        double highestPrice = Double.MIN_VALUE;
        double lowestPrice = Double.MAX_VALUE;
        double total = 0;

        for (Map.Entry<String, Double> entry : prices.entrySet()) {
            double price = entry.getValue();
            if (price > highestPrice) {
                highestPrice = price;
            }
            if (price < lowestPrice) {
                lowestPrice = price;
            }
            total += price;
        }

        double averagePrice = total / prices.size();

        System.out.println("Highest Price: " + highestPrice + " TL");
        System.out.println("Lowest Price: " + lowestPrice + " TL");
        System.out.println("Average Price: " + averagePrice + " TL");
    }
}
