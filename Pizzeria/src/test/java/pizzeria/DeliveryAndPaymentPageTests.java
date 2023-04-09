package pizzeria;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pizzeria.pages.DeliveryAndPaymentPage;

public class DeliveryAndPaymentPageTests extends TestBase {
    @Test
    @DisplayName("Проверка текста одного из условий")
    public void checkingCondition() {
        //arrange
        var page = new DeliveryAndPaymentPage(driver, wait);
        page.open();
        //act
        page.switchToFrame();
        //assert
        var text = "Минимальная сумма заказа 800 рублей.";
        Assertions.assertEquals(text, page.article.getText(), "Такой текст не отображается на странице");
    }
}
