package pizzeria.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Footer {
    private WebDriver driver;

    @FindBy (xpath = "//*[@class='text-5-value'][3]/a")
    public WebElement facebookLink;

    @FindBy (xpath = "//*[@class='text-5-value'][4]/a")
    public WebElement vkLink;

    @FindBy (xpath = "//*[@class='text-5-value'][5]/a")
    public WebElement instagramLink;

    @FindBy(css = ".text-5-value a")
    public List<WebElement> socialLinks;

    public Footer(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
