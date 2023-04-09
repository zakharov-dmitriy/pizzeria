package pizzeria.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Page {
    protected WebDriver driver;
    protected WebDriverWait wait;
    public HeaderPanel header;
    public Footer footer;

    @FindBy(xpath = "//*[contains(@class,'woocommerce-breadcrumb')]/span")
    public WebElement breadcrumbs;

    public Page(WebDriver driver, WebDriverWait wait, String subUrl) {
        this.driver = driver;
        this.wait = wait;
        this.subUrl = subUrl;
        header = new HeaderPanel(driver);
        footer = new Footer(driver);
        PageFactory.initElements(driver, this);
    }

    public Page(WebDriver driver, WebDriverWait wait) {
        this(driver, wait, "");
    }

    private String url = "http://pizzeria.skillbox.cc/";
    private final String subUrl;

    public void open() {
        driver.navigate().to(getPageUrl());
    }

    private String getPageUrl() {
        return url + subUrl;
    }
}
