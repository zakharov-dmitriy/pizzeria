package pizzeria.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Authorization extends Page{

    @FindBy (xpath = "//*[@name='username']")
    public WebElement inputName;

    @FindBy (xpath = "//*[@name='password']")
    public WebElement inputPassword;

    @FindBy (xpath = "//*[@name='login']")
    public WebElement btnLogin;

    public Authorization(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        PageFactory.initElements(driver, this);
    }

    public void authorization() {
        header.loginAccount.click();
        inputName.sendKeys("test-07");
        inputPassword.sendKeys("123");
        btnLogin.click();
    }
}
