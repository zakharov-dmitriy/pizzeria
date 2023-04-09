package pizzeria.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MainPage extends Page {
    private Actions action;
    public Authorization authn;

    String url = "http://pizzeria.skillbox.cc/";

    @FindBy(tagName = "html")
    public WebElement html;

    @FindBy(css = ".slick-prev")
    public WebElement leftArrow;

    @FindBy(css = ".slick-next")
    public WebElement rightArrow;

    @FindBy(xpath = "//*[@id='product1']//*[@aria-hidden='false']")
    public List<WebElement> slideElement;

    @FindBy(xpath = "//*[@id='accesspress_store_product-7']//*[@class='item-img']")
    public WebElement itemDrink;

    @FindBy(xpath = "//*[@id='accesspress_store_product-7']//li[1]//*[@class='item-img']//*[.='В корзину']")
    public WebElement linkToCart;

    @FindBy(xpath = "//*[@id='accesspress_store_product-6']//li[2]//*[@class='item-img']")
    public WebElement dessert;

    @FindBy(xpath = "//*[@id='accesspress_store_product-6']//li[2]//h3")
    public WebElement desertName;

    @FindBy(css = ".summary .entry-title")
    public WebElement cardTitle;

    @FindBy(css = "#ak-top")
    public WebElement arrowLink;

    @FindBy(className = "entry-title")
    public WebElement titlePage;

    @FindBy(className = "product")
    public List<WebElement> productItem;


    public MainPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        action = new Actions(driver);
        authn = new Authorization(driver, wait);
        PageFactory.initElements(driver, this);
    }

    public String getPageUrl() {
        return url;
    }

    public void clickArrow(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(slideElement.get(3)));
        action.moveToElement(element).click().perform();
    }

    public String getTitlePizza(int elementIndex) {
        var element = driver.findElement(By.xpath(String
                .format("//*[@id='product1']//*[@aria-hidden='false'][%d]//h3", elementIndex)));
        wait.until(ExpectedConditions.visibilityOf(element));
        return element.getText();
    }

    public void moveToElement(WebElement elem) {
        action.moveToElement(elem).perform();
    }

    public WebElement getItemDessert(int index) {
        return driver.findElement(By.xpath(String
                .format("//*[@id='accesspress_store_product-6']//li[%d]//*[@class='item-img']", index)));
    }

    public String getTitle(WebElement elem) {
        wait.until(ExpectedConditions.visibilityOf(elem));
        return elem.getText().toLowerCase();
    }

    public void scrollDownPage() {
        html.sendKeys(Keys.END);
        wait.until(ExpectedConditions.visibilityOf(arrowLink));
    }

    public void clickToSocialLink(WebElement socialLinkElement) {
        wait.until(ExpectedConditions.visibilityOf(socialLinkElement));
        socialLinkElement.click();
    }
}
