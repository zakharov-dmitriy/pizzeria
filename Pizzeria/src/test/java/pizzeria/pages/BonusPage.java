package pizzeria.pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BonusPage extends Page {

    @FindBy(css = "[name='username']")
    public WebElement inputName;

    @FindBy(css = "[name='billing_phone']")
    public WebElement inputPhone;

    @FindBy(css = "[name='bonus']")
    public WebElement buttonSubmit;

    @FindBy(css = "#bonus_main h3")
    public WebElement successMsg;

    @FindBy(xpath = "//*[@id='bonus_content']")
    public WebElement validationText;

    public BonusPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait, "/bonus/");
        PageFactory.initElements(driver, this);
    }

    public Boolean successMsgDisplayed() {
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
            return successMsg.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        } finally {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        }
    }
}
