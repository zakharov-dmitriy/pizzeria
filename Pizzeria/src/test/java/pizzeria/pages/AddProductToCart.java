package pizzeria.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddProductToCart extends Page {

    public AddProductToCart(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        PageFactory.initElements(driver, this);
    }

    public void productToCart(int index) {
        header.menuLink.click();
        var el = driver.findElement(By.cssSelector(String.format(".products li:nth-of-type(%d)", index)));
        el.findElement(By.cssSelector("a.button")).click();

        var link = el.findElement(By.cssSelector(".added_to_cart"));
        wait.until(ExpectedConditions.visibilityOf(link));
        link.click();
    }
}
