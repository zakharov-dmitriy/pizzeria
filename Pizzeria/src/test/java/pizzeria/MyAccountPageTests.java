package pizzeria;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pizzeria.pages.MyAccountPage;

public class MyAccountPageTests extends TestBase {
    @Test
    @DisplayName("Загрузка файла")
    public void fileUpload() {
        //arrange
        var page = new MyAccountPage(driver, wait);
        page.open();
        page.authn.authorization();
        //act
        page.dataAccount.click();
        var filePath = System.getProperty("user.dir") + "/src/test/resources/ex.jpeg";
        page.fileInput.sendKeys(filePath);
        //assert
        Assertions.assertTrue(page.filePreview.isDisplayed(), "Не отображается превью файла");
    }

    @Test
    @DisplayName("Открытие раздела Заказы")
    public void clockToOrders() {
        //arrange
        var page = new MyAccountPage(driver, wait);
        page.open();
        page.authn.authorization();
        //act
        var expectedResult = page.getNameBtn();
        page.ordersBtn.click();
        //assert
        Assertions.assertEquals(expectedResult, page.title.getText(), "Открылся неверный раздел");
    }

    @Test
    @DisplayName("Открыть заказ, кликнув на кнопку")
    public void openOrderDetailsClickedOnButton() {
        //arrange
        var page = new MyAccountPage(driver, wait);
        page.open();
        page.authn.authorization();
        //act
        page.ordersBtn.click();
        var expectedOrderNumber = page.getNumber(1);
        page.clickDetailButton(1);
        //assert
        Assertions.assertEquals(expectedOrderNumber, page.orderNumber.getText(),
                "Открылся неверный номер заказа");
    }

    @Test
    @DisplayName("Открыть заказ, кликнув на номер")
    public void openOrderDetailsClickedOnOrderNumber() {
        //arrange
        var page = new MyAccountPage(driver, wait);
        page.open();
        page.authn.authorization();
        //act
        page.ordersBtn.click();
        var expectedOrderNumber = page.getNumber(1);
        page.clickOrderNumberLink(1);
        //assert
        Assertions.assertEquals(expectedOrderNumber, page.orderNumber.getText(),
                "Открылся неверный номер заказа");
    }
}
