package pizzeria.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.stream.Collectors;

public class PizzaPage extends Page {
    protected WebDriverWait wait;
    public CartPage cartPage;

    @FindBy(xpath = "//select[@name='orderby']")
    public WebElement sortingSelect;

    @FindBy(css = ".ui-slider-handle:last-child")
    public WebElement filterPrice;

    @FindBy(css = ".price_slider_amount button")
    public WebElement filterButton;

    @FindBy(className = "product")
    public List<WebElement> products;

    public PizzaPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait, "product-category/menu/pizza/");
        this.wait = wait;
        cartPage = new CartPage(driver, wait);
        PageFactory.initElements(driver, this);
    }

    public void setParamSorting(String value) {
        new Select(sortingSelect).selectByVisibleText(value);
    }

    public String getParamSorting() {
        return new Select(sortingSelect).getFirstSelectedOption().getText();
    }

    public List<String> getNameParams() {
        var params = new Select(sortingSelect);
        return params.getOptions().stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public String getProductName(int i) {
        return driver.findElement(By.cssSelector(String.format(".products li:nth-of-type(%d) h3", i))).getText();
    }

    public void changePrice() {
        new Actions(driver)
                .clickAndHold(filterPrice)
                .moveByOffset(-200, 0)
                .release()
                .perform();
    }

    public WebElement getProduct(int i) {
        return driver.findElement(By.cssSelector(String.format(".products li:nth-of-type(%d)", i)));
    }

    public String getPriceProduct(int i) {
        return getProduct(i).findElement(By.cssSelector(".price-cart .amount bdi")).getText();
    }

    public String getNameProduct(int i) {
        return getTitle(getProduct(i).findElement(By.cssSelector("h3")).getText());
    }

    public void addToCart(int i) {
        getProduct(i).findElement(By.cssSelector("a.button")).click();
    }

    public void clickOnLinkMore(int i) {
        var link = getProduct(i).findElement(By.cssSelector(".added_to_cart"));
        wait.until(ExpectedConditions.visibilityOf(link));
        link.click();
    }

    public String getTitle(String text) {
        return text.replaceAll("[«»\"]", "");
    }

    public String getNameProductInCart() {
        return getTitle(cartPage.productName.getText());
    }
}
