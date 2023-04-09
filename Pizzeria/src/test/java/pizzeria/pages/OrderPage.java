package pizzeria.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OrderPage extends Page {
    public AddProductToCart add;
    public Authorization authn;
    public CartPage cart;
    public JavascriptExecutor jsExecutor;
    public Actions action;

    @FindBy(css = "[name='billing_first_name']")
    public WebElement inputName;

    @FindBy(css = "[name='billing_last_name']")
    public WebElement inputSurname;

    @FindBy(css = "[name='billing_address_1']")
    public WebElement inputAddress;

    @FindBy(css = "[name='billing_city']")
    public WebElement inputCity;

    @FindBy(css = "[name='billing_state']")
    public WebElement inputRegion;

    @FindBy(css = "[name='billing_postcode']")
    public WebElement inputPostCode;

    @FindBy(css = "[name='billing_phone']")
    public WebElement inputPhone;

    @FindBy(id = "order_date")
    public WebElement inputDate;

    @FindBy(id = "terms")
    public WebElement checkboxConditions;

    @FindBy(id = "place_order")
    public WebElement submitBtn;

    @FindBy(id = "payment_method_cod")
    public WebElement payCash;

    @FindBy(css = ".post-title")
    public WebElement postTitle;

    @FindBy(css = ".woocommerce-thankyou-order-received")
    public WebElement successMsg;

    @FindBy(css = ".woocommerce-terms-and-conditions-link")
    public WebElement conditionsLink;

    @FindBy(css = ".woocommerce-terms-and-conditions")
    public WebElement conditionsBox;

    @FindBy(css = ".showcoupon")
    public WebElement showCouponLink;

    @FindBy(css = "[name=coupon_code]")
    public WebElement inputCoupon;

    @FindBy(css = "[name=apply_coupon]")
    public WebElement applyCouponBtn;

    @FindBy(css = ".woocommerce-message")
    public WebElement msgAlert;

    @FindBy(css = ".woocommerce-remove-coupon")
    public WebElement removeCoupon;

    @FindBy(css = ".shop_table tfoot tr")
    public List<WebElement> cartItems;

    public OrderPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        add = new AddProductToCart(driver, wait);
        authn = new Authorization(driver, wait);
        cart = new CartPage(driver, wait);
        PageFactory.initElements(driver, this);
        jsExecutor = (JavascriptExecutor) driver;
        action = new Actions(driver);
    }

    public void setDate(String value) {
        inputDate.sendKeys(value);
    }

    public String getDate() {
        var dateString = (String) jsExecutor.executeScript("return document.querySelector('#order_date').value");
        return LocalDate.parse(dateString, DateTimeFormatter
                .ofPattern("yyyy-MM-dd")).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public void checkField(WebElement atr, String name) {
        if (!atr.getAttribute("value").isEmpty()) {
            atr.clear();
        }
        atr.sendKeys(name);
    }

    public String getTitle() {
        wait.until(ExpectedConditions.visibilityOf(successMsg));
        return postTitle.getText().toLowerCase();
    }

    public void clickOnLink() {
        try {
            conditionsLink.click();
        } catch (StaleElementReferenceException e) {
            conditionsLink.click();
        }
    }

    public void addCoupon(String couponText) {
        if (cartItems.size() > 2) {
            try {
                removeCoupon.click();
            } catch (StaleElementReferenceException e) {
                removeCoupon.click();
            }
        }
        showCouponLink.click();
        inputCoupon.sendKeys(couponText);
        applyCouponBtn.click();
    }

    public String getMsgText() {
        wait.until(ExpectedConditions.textToBePresentInElement(msgAlert, "Coupon code applied successfully."));
        return msgAlert.getText();
    }
}
