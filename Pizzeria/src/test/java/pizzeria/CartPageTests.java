package pizzeria;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pizzeria.pages.CartPage;

public class CartPageTests extends TestBase {

    @DisplayName("Увеличение/уменьшение количества товара")
    @Test
    public void changingCountProduct() {
        //arrange
        var msgIncrease = "Количество товара не увеличилось";
        var msgDecrease = "Количество товара не уменьшилось";
        var page = new CartPage(driver, wait);
        page.open();
        //act
        page.add.productToCart(3);
        page.setCount(12);
        var increasedResult = page.getCount();
        var increasedValue = page.getValue();
        page.decreaseCount();
        var decreasedResult = page.getCount();
        var decreasedValue = page.getValue();
        //assert
        Assertions.assertAll(
                () -> Assertions.assertEquals(12, increasedResult, msgIncrease),
                () -> Assertions.assertEquals("12", increasedValue, msgIncrease),
                () -> Assertions.assertEquals(11, decreasedResult, msgDecrease),
                () -> Assertions.assertEquals("11", decreasedValue, msgDecrease)
        );
    }

    @DisplayName("Обновление корзины после изменений")
    @Test
    public void updatingCartAfterChanges() {
        //arrange
        var page = new CartPage(driver, wait);
        page.open();
        page.add.productToCart(2);
        page.setCount(7);
        //act
        var beforePrice = page.totalPrice.getText();
        page.buttonUpdate.click();
        var afterPrice = page.getTotalPrice();
        //assert
        Assertions.assertAll(
                () -> Assertions.assertTrue(page.messageAlert.isDisplayed(),
                        "Нет сообщения об обновлении корзины"),
                () -> Assertions.assertNotEquals(beforePrice, afterPrice, "Общая стоимость одинаковая")
        );
    }

    @DisplayName("Переход к оплате товара")
    @Test
    public void goToPay() {
        //arrange
        var page = new CartPage(driver, wait);
        page.open();
        page.authn.authorization();
        page.add.productToCart(4);
        //act
        page.checkoutBtn.click();
        //assert
        var title = page.postTitle.getText().toLowerCase();
        var breadcrumbs = page.header.breadcrumbs.getText().toLowerCase();
        Assertions.assertEquals(title, breadcrumbs, "Неверная страница для оформления заказа");
    }

    @DisplayName("Применение промокода")
    @Test
    public void couponAddSuccess() {
        //arrange
        var page = new CartPage(driver, wait);
        page.open();
        page.header.promoLink.click();
        var couponText = page.coupon.getText();
        page.authn.authorization();
        page.add.productToCart(4);
        //act
        page.addCoupon(couponText);
        //assert
        Assertions.assertAll(
                () -> Assertions.assertEquals(couponText, page.getDiscountName(),"Купон не применился"),
                () -> Assertions.assertTrue(page.messageAlert.isDisplayed(), "Нет подтверждающего сообщения")
        );
    }
}
