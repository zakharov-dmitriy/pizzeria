package pizzeria.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.NoSuchElementException;

public class CartPage extends Page {
    public AddProductToCart add;
    public Authorization authn;

    @FindBy(css = "tbody .product-name")
    public WebElement productName;

    @FindBy(css = "[type=number]")
    public WebElement inputCount;

    @FindBy(css = "[name=update_cart]")
    public WebElement buttonUpdate;

    @FindBy(css = ".woocommerce-message")
    public WebElement messageAlert;

    @FindBy(css = "[data-title='Общая стоимость']:not(.product-subtotal) bdi")
    public WebElement totalPrice;

    @FindBy(css = ".checkout-button")
    public WebElement checkoutBtn;

    @FindBy(className = "post-title")
    public WebElement postTitle;

    @FindBy(xpath = "//*[@class='content-page']//strong")
    public WebElement coupon;

    @FindBy(id = "coupon_code")
    public WebElement inputCoupon;

    @FindBy(css = "[name='apply_coupon']")
    public WebElement applyCouponBtn;

    @FindBy(css = ".cart-discount th")
    public WebElement cartDiscount;

    @FindBy(css = ".cart_totals tr")
    public List<WebElement> priceItems;

    @FindBy(css = ".woocommerce-remove-coupon")
    public WebElement removeCoupon;

    public CartPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait, "");
        add = new AddProductToCart(driver, wait);
        authn = new Authorization(driver, wait);
        PageFactory.initElements(driver, this);
    }

    public int getCount() {
        return Integer.parseInt(inputCount.getAttribute("value"));
    }

    public void setCount(int count) {
        inputCount.clear();
        inputCount.sendKeys(Integer.toString(count));
    }

    public String getValue() {
        return inputCount.getAttribute("value");
    }

    public void decreaseCount() {
        inputCount.sendKeys(Keys.ARROW_DOWN);
    }

    public String getTotalPrice() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("processing")));
        return totalPrice.getText();
    }

    public String getDiscountName() {
        String[] parts = cartDiscount.getText().split(":\\s");
        return parts[1];
    }

    public void addCoupon(String couponText) {
        if (priceItems.size() > 2) {
            try {
                removeCoupon.click();
            } catch (NoSuchElementException e) {
                System.out.println("Купона нет на странице");
            }
        }
        inputCoupon.sendKeys(couponText);
        applyCouponBtn.click();
    }
}
