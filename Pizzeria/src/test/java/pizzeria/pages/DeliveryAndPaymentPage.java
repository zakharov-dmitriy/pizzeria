package pizzeria.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DeliveryAndPaymentPage extends Page {

    @FindBy(tagName = "iframe")
    public WebElement frameElement;

    @FindBy(xpath = "//ul/li[2]")
    public WebElement article;

    public DeliveryAndPaymentPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait, "/delivery/");
        PageFactory.initElements(driver, this);
    }

    public void switchToFrame() {
        driver.switchTo().frame(frameElement);
    }

}
