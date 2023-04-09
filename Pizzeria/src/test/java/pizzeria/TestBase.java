package pizzeria;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Set;
import java.util.stream.Collectors;

public class TestBase {
//    private static WebDriverManager manager;
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions action;

    public String initialWindow;

    public Set<String> getAllWindows() {
        return driver.getWindowHandles();
    }

    public void switchToFirstNewWindow() {
        var newWindows =
                getAllWindows().stream().filter(w -> !w.equals(initialWindow)).collect(Collectors.toSet());
        driver.switchTo().window(newWindows.stream().findFirst().get());
    }

    public void switchToWindow(String windowId) {
        driver.switchTo().window(windowId);
    }

    @BeforeEach
    public void setUp() {
//        System.setProperty("webdriver.chrome.driver", "./src/test/resources/driver/chromedriver");
        WebDriverManager.chromedriver().setup();
        var options = new ChromeOptions();
        options.addArguments("--start-maximized", "--incognito");
        options.addArguments("--remote-allow-origins=*");
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);//с внезапным Alert Selenium не будет взаимодействовать
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
//        driver.manage().window().setSize(new Dimension(1920,1080));
        action = new Actions(driver);
        initialWindow = driver.getWindowHandle();
    }

    @AfterEach
    public void tearDown() throws IOException {
        try {
            takeScreenshot();
        } catch (UnhandledAlertException alertException) {
            Alert alert = driver.switchTo().alert();
            System.out.println("Alert text: " + alert.getText());
            alert.accept();
            takeScreenshot();
        }
        driver.quit();
    }

    private void takeScreenshot() throws IOException {
        var sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(sourceFile, new File("./src/test/resources/screens/screen.png"));
    }
}
