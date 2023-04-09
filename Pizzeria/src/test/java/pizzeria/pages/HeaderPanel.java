package pizzeria.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HeaderPanel {
    private WebDriver driver;

    @FindBy(xpath = "//*[@id='menu-primary-menu']//a[.='Меню']")
    public WebElement menuLink;

    @FindBy(xpath = "//*[@id='menu-primary-menu']//a[.='Акции']")
    public WebElement promoLink;

    @FindBy(xpath = "//*[@id='menu-primary-menu']//a[.='Оформление заказа']")
    public WebElement orderLink;

    @FindBy (css = ".accesspress-breadcrumb span")
    public WebElement breadCrumbs;

    @FindBy (css = "#accesspress-breadcrumb .current")
    public WebElement breadcrumbs;

    @FindBy (css = ".login-woocommerce a")
    public WebElement loginAccount;

    @FindBy(className = "searchsubmit")
    public WebElement searchBtn;

    @FindBy(className = "search-field")
    public WebElement inputSearch;


    public HeaderPanel(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public WebElement menu(String nameMenu) {
        return driver.findElement(By.xpath(String.format("//*[@id='menu-primary-menu']//a[.='%s']", nameMenu)));
    }

    public String getBreadcrumbsText() {
        return breadcrumbs.getText().toLowerCase();
    }

    public String getBreadCrumbsText() {
        return breadCrumbs.getText().toLowerCase();
    }

    public void submenu(String nameSubmenu) {
        Actions action = new Actions(driver);
        var submenu = driver.findElement(By.xpath(String.format("//*[@id='menu-primary-menu']//a[.='%s']", nameSubmenu)));
        action.moveToElement(menuLink).moveToElement(submenu).click().perform();
    }
}
