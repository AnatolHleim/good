package basic;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import listeners.TestRail;
import listeners.TestRailListeners;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobject.*;
import utilites.TestData;

import static com.codeborne.selenide.Selenide.close;
import static com.codeborne.selenide.Selenide.open;

@Listeners(TestRailListeners.class)
public class HardCodeOnline extends TestRailListeners {
    private MainPage mainPage;
    private BasketPage basketPage;
    private SearchResultPage searchResultPage;
    private StockPage stockPage;
    private DryFoodPage dryFoodPage;

    @BeforeTest
    public void setUp() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(false));
        Configuration.startMaximized = true;
        Configuration.timeout = 6000;
        stockPage = new StockPage();
        mainPage = new MainPage();
        basketPage = new BasketPage();
        searchResultPage = new SearchResultPage();
        dryFoodPage = new DryFoodPage();
        createTestRun();
    }

    @BeforeMethod
    public void init() {
        open(TestData.START_PAGE.getStringProperties());
    }

    @TestRail(testCaseId = {44931})
    @Test(priority = 1, description = "Проверка работы функции авторизации")

    public void verifyAuth() {
        mainPage.clickOnAuthLinkInHeader()
                .enterValueInLoginFieldOnPopUp(TestData.ACCOUNT_NAME.getStringProperties())
                .enterValueInPasswordFieldOnPopUp(TestData.ACCOUNT_PASSWORD.getStringProperties())
                .clickOnPopUpButtonLogin();
        Assert.assertTrue(mainPage.isDisplayedLinkToProfile());
    }

    @TestRail(testCaseId = {44932})
    @Test(priority = 2, description = "Проверка отображения добавленного товара в корзине")
    public void verifyAddCart() {
        String nameElement = mainPage.getNameProductOnSliderSnippetFromIndex(0);
        mainPage.clickOnElementIconBasketInSliderFromIndex(0)
                .clickButtonOnPopUpFromGoToBasketPage();
        Assert.assertTrue(basketPage.getListProductNameInBasket().get(0).contains(nameElement));
    }

    @TestRail(testCaseId = {44933})
    @Test(priority = 3, description = "Проверка возможности удаления товаров из корзины")
    public void verifyDeleteOnCart() {
        String nameElement = mainPage.getNameProductOnSliderSnippetFromIndex(0);
        mainPage.clickOnElementIconBasketInSliderFromIndex(0)
                .clickButtonOnPopUpFromGoToBasketPage()
                .deleteElementWhenContainsSelectedName(nameElement);
        Assert.assertFalse(basketPage.getListProductNameInBasket().stream().anyMatch(o -> o.contains(nameElement)));
    }

    @TestRail(testCaseId = {44934})
    @Test(priority = 4, description = "Проверка возможности увеличения количества добавленного товара через элемент управления \"+\" корзине")
    public void verifyIncrementProductInCart() {
        String nameElement = mainPage.getNameProductOnSliderSnippetFromIndex(0);
        mainPage.clickOnElementIconBasketInSliderFromIndex(0)
                .clickButtonOnPopUpFromGoToBasketPage()
                .incrementElementWhenContainsSelectedName(nameElement);
        Assert.assertEquals(basketPage.getValueCountProduct(nameElement), "2");
    }

    @TestRail(testCaseId = {44935})
    @Test(priority = 5, description = "Проверка функции поиска на сайте")
    public void verifySearchFunctionCorrectWork() {
        String nameElement = mainPage.getNameProductOnSliderSnippetFromIndex(0);
        mainPage.inputProductNameInSearchField(nameElement)
                .clickButtonStartSearch();
        Assert.assertEquals(searchResultPage.getResultString(), TestData.RESULT_SEARCH_STRING.getStringProperties());
    }

    @TestRail(testCaseId = {44936})
    @Test(priority = 6, description = "Проверка, что каждый сниппет содержит иконку акции на странице скидки")
    public void verifyAllSnippetsContainsIconSale() {
        mainPage.clickToStockLinkOnUpperMenu();
        Assert.assertEquals(stockPage.getCountIconSaleOnPage(), stockPage.getCountSnippetOnPage());
    }

    @TestRail(testCaseId = {44937})
    @Test(priority = 7, description = "Проверка работы фильтра по производителю")
    public void verifyFilterOnCategory() {
        mainPage.clickToCatCategoryOnUpperMenu()
                .clickOnLeftMenuLinkDryFood()
                .selectManufacturerInFilterByName(TestData.MANUFACTURER_NAME_FILTER.getStringProperties());
        Assert.assertTrue(dryFoodPage.isNameProductOnSnippetContainsNameManufacturer(TestData.MANUFACTURER_NAME_FILTER.getStringProperties()));
    }

    @TestRail(testCaseId = {44939})
    @Test(priority = 8, description = "Проверка доступности смены населённого пункта")
    public void verifyChooseTown() {
        mainPage.clickOnModalFormToChooseCity()
                .inputCityOnSearchFieldModalWindow(TestData.NAME_CITY.getStringProperties())
                .clickOnResultCitySearch()
                .clickOnSubmitChoosedCity();
        Assert.assertTrue(mainPage.getCurrentSelectedCity().contains(TestData.NAME_CITY.getStringProperties()));
    }

    @AfterMethod
    public void finalized() {
        close();
    }
}
