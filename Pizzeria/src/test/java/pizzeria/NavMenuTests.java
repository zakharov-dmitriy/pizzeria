package pizzeria;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pizzeria.pages.MainPage;

import java.util.stream.Stream;

public class NavMenuTests extends TestBase {

    private static Stream<Arguments> testDataForMenu() {
        return Stream.of(
                Arguments.of("Доставка и оплата"),
                Arguments.of("Акции"),
                Arguments.of("О нас"),
                Arguments.of("Корзина"),
                Arguments.of("Мой аккаунт"),
                Arguments.of("Оформление заказа"),
                Arguments.of("Бонусная программа")
        );
    }

    @ParameterizedTest
    @MethodSource("testDataForMenu")
    @DisplayName("Открытие страниц из навигационного меню")
    public void clickOnMenuLinks(String name) {
        //arrange
        var page = new MainPage(driver, wait);
        page.open();
        page.authn.authorization();
        //act
        page.header.menu(name).click();
        //assert
        Assertions.assertEquals(name.toLowerCase(), page.header.getBreadcrumbsText(),
                "Открылась неверная страница");
    }

    private static Stream<Arguments> testDataForSubmenu() {
        return Stream.of(
                Arguments.of("Пицца"),
                Arguments.of("Десерты"),
                Arguments.of("Напитки")
        );
    }

    @ParameterizedTest
    @MethodSource("testDataForSubmenu")
    @DisplayName("Открытие страниц из подменю")
    public void clickOnSubmenuLinks(String name) {
        //arrange
        var page = new MainPage(driver, wait);
        page.open();
        //act
        page.header.submenu(name);
        //assert
        Assertions.assertEquals(name.toLowerCase(), page.header.getBreadCrumbsText(),
                "Открылась неверная страница");
    }
}
