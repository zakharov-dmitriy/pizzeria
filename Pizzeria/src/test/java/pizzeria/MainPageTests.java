package pizzeria;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pizzeria.pages.MainPage;

import java.util.Locale;
import java.util.stream.Stream;

public class MainPageTests extends TestBase {
    @Test
    @DisplayName("Переключение слайдера кликом левой стрелки")
    public void switchingSliderElement_clickLeftArrow() {
        //arrange
        var page = new MainPage(driver, wait);
        page.open();
        //act
        var expectedTitle = page.getTitlePizza(2);
        page.clickArrow(page.leftArrow);
        //assert
        var actualTitle = page.getTitlePizza(2);
        Assertions.assertNotEquals(expectedTitle, actualTitle,
                "При переключении слайдера заголовки у слайдов одинаковые");
    }

    @Test
    @DisplayName("Переключение слайдера кликом правой стрелки")
    public void switchingSliderElement_clickRightArrow() {
        //arrange
        var page = new MainPage(driver, wait);
        page.open();
        //act
        var expectedTitle = page.getTitlePizza(2);
        page.clickArrow(page.rightArrow);
        //assert
        var actualTitle = page.getTitlePizza(2);
        Assertions.assertNotEquals(expectedTitle, actualTitle,
                "При переключении слайдера заголовки у слайдов одинаковые");
    }

    @Test
    @DisplayName("Появление ссылки после наведения")
    public void checkingLinkOnDrink() {
        //arrange
        var page = new MainPage(driver, wait);
        page.open();
        //act
        page.moveToElement(page.itemDrink);
        //assert
        Assertions.assertTrue(page.linkToCart.isDisplayed(), "Ссылка \"В Корзину\" не отображается");
    }

    @Test
    @DisplayName("Переход на страницу десерта при клике")
    public void goToDessert() {
        //arrange
        var page = new MainPage(driver, wait);
        page.open();
        //act
        page.moveToElement(page.dessert);
        var expectedNameDesert = page.getTitle(page.desertName);
        page.dessert.click();
        //assert
        var actualNameDessert = page.getTitle(page.cardTitle);
        Assertions.assertAll(
                () -> Assertions.assertEquals(expectedNameDesert, actualNameDessert,
                        "Неверная карточка товара"),
                () -> Assertions.assertEquals(expectedNameDesert, page.getTitle(page.breadcrumbs),
                        "Неверная страница")
        );
    }

    @Test
    @DisplayName("Проверка ссылки \"Наверх\" внизу страницы")
    public void checkingLinkArrow() {
        //arrange
        var page = new MainPage(driver, wait);
        page.open();
        //act
        page.scrollDownPage();
        //assert
        Assertions.assertTrue(page.arrowLink.isDisplayed(),
                "Ссылка-стрелка \"Наверх\" не отображается");
    }

    @Test
    @DisplayName("Открытие ссылок соц.сетей")
    public void checkingOpeningLinks() {
        //arrange
        var page = new MainPage(driver, wait);
        page.open();
        //act
        page.scrollDownPage();
        page.clickToSocialLink(page.footer.vkLink);
        var expectedUrl = "https://vk.com/skillbox";
        switchToFirstNewWindow();
        //assert
        Assertions.assertAll(
                () -> Assertions.assertEquals(2, getAllWindows().size(),
                        "После клика по ссылке не открылась новая вкладка"),
                () -> Assertions.assertEquals(expectedUrl, driver.getCurrentUrl(),
                        "После клика по ссылке открылась неверная вкладка")
        );
    }

    @Test
    @DisplayName("Открытие и закрытие ссылок соц.сетей")
    public void checkingOpeningLinksAndClose() {
        //arrange
        var page = new MainPage(driver, wait);
        page.open();
        //act
        page.scrollDownPage();
        page.clickToSocialLink(page.footer.vkLink);
        switchToFirstNewWindow();
        driver.close();
        switchToWindow(initialWindow);
        //assert
        var expectedUrl = page.getPageUrl();
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, getAllWindows().size(),
                        "После закрытия вкладки количество активных окон остается неверным"),
                () -> Assertions.assertEquals(expectedUrl, driver.getCurrentUrl(),
                        "После закрытия вкладки изменился адрес основной страницы")
        );
    }

    private static Stream<Arguments> search_testData() {
        return Stream.of(
                Arguments.of("десерт"),
                Arguments.of("пицца"),
                Arguments.of("")
        );
    }

    @ParameterizedTest
    @MethodSource("search_testData")
    @DisplayName("Поиск товаров через строку поиска")
    public void searchProducts(String text) {
        //arrange
        var page = new MainPage(driver, wait);
        page.open();
        //act
        page.header.inputSearch.sendKeys(text);
        page.header.searchBtn.click();
        //assert
        Assertions.assertAll(
                () -> Assertions.assertTrue(page.titlePage.getText().toLowerCase().contains(text),
                        "Неверный заголовок страницы"),
                () -> Assertions.assertTrue(page.productItem.size() > 0,
                        "Нет запрошенных товаров")
        );
    }
}

