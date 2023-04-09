package pizzeria.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MyAccountPage extends Page {
    public Authorization authn;

    @FindBy(xpath = "//*[@class='woocommerce-MyAccount-navigation']//a[.='Данные аккаунта']")
    public WebElement dataAccount;

    @FindBy(id = "uploadFile")
    public WebElement fileInput;

    @FindBy(id = "uploadPreview")
    public WebElement filePreview;

    @FindBy(css = ".woocommerce-MyAccount-navigation-link--orders a")
    public WebElement ordersBtn;

    @FindBy(className = "post-title")
    public WebElement title;

    @FindBy(className = "view")
    public List<WebElement> detail;

    @FindBy(css = ".woocommerce-orders-table__cell-order-number a")
    public List<WebElement> orderNumberLink;

    @FindBy(css = ".order-number")
    public WebElement orderNumber;

    public MyAccountPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        authn = new Authorization(driver, wait);
        PageFactory.initElements(driver, this);
    }

    public String getNameBtn() {
        return ordersBtn.getText().toUpperCase();
    }

    public void clickDetailButton(int index) {
        detail.get(index).click();
    }

    public void clickOrderNumberLink(int index) {
        orderNumberLink.get(index).click();
    }

    public String getNumber(int index) {
        var text = orderNumberLink.get(index).getText();
        return text.replaceAll("\\D+","");
    }
}
