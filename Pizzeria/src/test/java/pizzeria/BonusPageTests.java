package pizzeria;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pizzeria.pages.BonusPage;

import java.util.stream.Stream;

public class BonusPageTests extends TestBase {

    @Test
    @DisplayName("Успешное добавление карты")
    public void addingCardSuccess() {
        //arrange
        var page = new BonusPage(driver, wait);
        page.open();
        //act
        page.inputName.sendKeys("Dmitrii");
        page.inputPhone.sendKeys("89003500202");
        page.buttonSubmit.click();
        var alert = driver.switchTo().alert();
        //assert
        var alertText = "Заявка отправлена, дождитесь, пожалуйста, оформления карты!";
        var actualAlertText = alert.getText();
        alert.accept();
        Assertions.assertAll(
                () -> Assertions.assertEquals(alertText, actualAlertText, "Неверное сообщение"),
                () -> Assertions.assertTrue(page.successMsgDisplayed(), "Нет сообщения")
        );
    }

    private static Stream<Arguments> testData() {
        return Stream.of(
                Arguments.of("Oleg", "+78007553292"),
                Arguments.of("Олег", "88007553292")
        );
    }

    @ParameterizedTest
    @MethodSource("testData")
    @DisplayName("Проверка формата телефона")
    public void phoneNumberFormat(String name, String phone) {
        //arrange
        var page = new BonusPage(driver, wait);
        page.open();
        //act
        page.inputName.sendKeys(name);
        page.inputPhone.sendKeys(phone);
        page.buttonSubmit.click();
        var alert = driver.switchTo().alert();
        //assert
        Assertions.assertAll(
                () -> Assertions.assertNotNull(alert, "Alert не открылся")
        );
    }

    private static Stream<Arguments> validation_testData() {
        return Stream.of(
                Arguments.of("", ""),
                Arguments.of("Олег", ""),
                Arguments.of("", "+78007553292"),
                Arguments.of("123", "number")
        );
    }

    @ParameterizedTest
    @MethodSource("validation_testData")

    @DisplayName("Проверка валидации полей")
    public void test(String name, String phone) {
        //arrange
        var page = new BonusPage(driver, wait);
        page.open();
        //act
        page.inputName.sendKeys(name);
        page.inputPhone.sendKeys(phone);
        page.buttonSubmit.click();
        var block = page.validationText.getText();
        //assert
        Assertions.assertFalse(block.isEmpty(), "Нет сообщений о валидации полей");
    }
}