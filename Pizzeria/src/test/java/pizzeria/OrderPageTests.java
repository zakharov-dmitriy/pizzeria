package pizzeria;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pizzeria.pages.OrderPage;

public class OrderPageTests extends TestBase {

    @Test
    @DisplayName("Установка даты в календаре")
    public void settingDateInCalendar() {
        //arrange
        var page = new OrderPage(driver, wait);
        page.open();
        page.authn.authorization();
        page.add.productToCart(2);
        page.header.orderLink.click();
        //act
        var dateValue = "17.03.2023";
        page.setDate(dateValue);
        //assert
        Assertions.assertEquals(dateValue, page.getDate(), "Введенное значение отличается");
    }

    @Test
    @DisplayName("Оформление заказа, заполнение полей")
    public void makingOrder_Success() {
        //arrange
        var page = new OrderPage(driver, wait);
        page.open();
        page.authn.authorization();
        page.add.productToCart(2);
        page.header.orderLink.click();
        //act
        page.checkField(page.inputName, "Name");
        page.checkField(page.inputSurname, "Surname");
        page.checkField(page.inputAddress, "Address");
        page.checkField(page.inputCity, "City");
        page.checkField(page.inputRegion, "Region");
        page.checkField(page.inputPostCode, "123654");
        page.checkField(page.inputPhone, "89007602323");
        page.payCash.click();
        page.checkboxConditions.click();
        page.submitBtn.click();
        //assert
        Assertions.assertEquals("заказ получен", page.getTitle(),
                "Заказ не оформлен, нет подтверждающего заголовка");
    }

    @Test
    @DisplayName("Открытие правил и условий веб-сайта")
    public void checkingConditions() {
        //arrange
        var page = new OrderPage(driver, wait);
        page.open();
        page.authn.authorization();
        page.add.productToCart(2);
        page.header.orderLink.click();
        //act
        page.clickOnLink();
        //assert
        Assertions.assertTrue(page.conditionsBox.isDisplayed(), "text");
    }

    @Test
    @DisplayName("Добавление купона при оформлении заказа")
    public void addingCouponSuccess() {
        //arrange
        var page = new OrderPage(driver, wait);
        page.open();
        page.authn.authorization();
        page.add.productToCart(5);
        page.header.orderLink.click();
        //act
        page.addCoupon("GIVEMEHALYAVA");
        //assert
        Assertions.assertAll(
                () -> Assertions.assertTrue(page.msgAlert.isDisplayed(),
                        "Нет подтверждающего сообщения"),
                () -> Assertions.assertEquals("Coupon code applied successfully.", page.getMsgText(),
                        "Нет подтверждающего сообщения на добавление купона")
        );
    }
}
