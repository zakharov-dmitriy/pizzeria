package pizzeria;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pizzeria.pages.PizzaPage;

import java.util.Arrays;

public class PizzaPageTests extends TestBase {
    @DisplayName("Проверка сортировки пицц")
    @Test
    public void checkingSortingTest() {
        //arrange
        var expectedResult = "По убыванию цены";
        var page = new PizzaPage(driver, wait);
        page.open();
        var initParamSorting = page.getParamSorting();
        var beforeProduct = page.getProductName(2);
        //act
        page.setParamSorting(expectedResult);
        //assert
        var afterProduct = page.getProductName(2);
        Assertions.assertAll(
                () -> Assertions.assertEquals(expectedResult, page.getParamSorting(),
                        "Выбрана неверный параметр сортировки"),
                () -> Assertions.assertNotEquals(initParamSorting, page.getParamSorting(),
                        "С момента открытия страницы параметр сортировки не изменился"),
                () -> Assertions.assertNotEquals(beforeProduct, afterProduct,
                        "Названия пицц совпадают")
        );
    }

    @DisplayName("Проверка параметров сортировки")
    @Test
    public void checkingParamsSortingTest() {
        //arrange
        var page = new PizzaPage(driver, wait);
        page.open();
        //assert
        var expectedParams = Arrays.asList("Обычная сортировка", "По популярности",
                "Последние", "По возрастанию цены", "По убыванию цены");
        Assertions.assertEquals(expectedParams, page.getNameParams(),
                "Параметры сортировки не совпадают");
    }


    @DisplayName("Проверка фильтрации по цене")
    @Test
    public void filteringByPrice() {
        //arrange
        var page = new PizzaPage(driver, wait);
        page.open();
        //act
        var beforeFilterCount = page.products.size();
        page.changePrice();
        page.filterButton.click();
        //assert
        var afterFilterCount = page.products.size();
        Assertions.assertNotEquals(beforeFilterCount, afterFilterCount,
                "Количество продуктов до и после фильтрации совпадают");
    }

    @DisplayName("Добавление пиццы в корзину")
    @Test
    public void addingToCart() {
        //arrange
        var page = new PizzaPage(driver, wait);
        page.open();
        //act
        var nameProduct = page.getNameProduct(2);
        page.addToCart(2);
        page.clickOnLinkMore(2);
        //assert
        var nameInCart = page.getNameProductInCart();
        Assertions.assertEquals(nameProduct, nameInCart, "Название товара не совпадает");
    }
}
