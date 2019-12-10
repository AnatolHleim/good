package pageobject;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MainPage {
    private ElementsCollection productSnippetInSliderOnMainPage = $$("[class='product-item-container']");
    private SelenideElement buttonOnPopUpFromRedirectToBasketPage = $(By.xpath("//span[text()='Перейти в корзину']"));
    private SelenideElement linkOpenAuthPopUp = $("[data-target='#header_auth_modal']");
    private SelenideElement authPopUpLoginField = $(By.id("auth_login_header"));
    private SelenideElement authPopUpPasswordField = $(By.id("header_auth_modal_password"));
    private SelenideElement loginButtonOnPopUp = $(By.name("Login"));
    private SelenideElement linkToProfileAuthUser = $(By.linkText("Мой профиль"));
    private SelenideElement searchProductInput = $(By.id("title-search-input"));
    private SelenideElement buttonStartSearchProduct = $(By.name("s"));
    private SelenideElement menuElementNavigateToStockPage = $("[href='/stock.html']");
    private SelenideElement menuElementNavigateToProductsForCat = $("[href='/catalog/cats.html']");
    private SelenideElement buttonChooseCity = $(By.xpath("//a[@data-target='#chooseTown']"));
    private SelenideElement fieldOnModalWindowSearchCity = $("[class='modalSearchInput selectInput_search']");
    private SelenideElement buttonSubmitChooseCity = $(("[class='form_button']"));
    private SelenideElement currentCity = $(By.className("geo_title"));
    private ElementsCollection resultSearchForCityInputed = $$("[class='selectInput_listItem']");


    @Step
    public MainPage clickOnElementIconBasketInSliderFromIndex(int index) {
        productSnippetInSliderOnMainPage.get(index).$("[data-entity='buttons-block']").click();
        return this;
    }

    @Step
    public String getNameProductOnSliderSnippetFromIndex(int index) {
        return productSnippetInSliderOnMainPage.get(index).$("[class='product-item-title']").getText();
    }

    @Step
    public BasketPage clickButtonOnPopUpFromGoToBasketPage() {
        buttonOnPopUpFromRedirectToBasketPage.click();
        return new BasketPage();
    }

    @Step
    public MainPage clickOnAuthLinkInHeader() {
        linkOpenAuthPopUp.click();
        return this;
    }

    @Step
    public MainPage enterValueInLoginFieldOnPopUp(String login) {
        authPopUpLoginField.setValue(login);
        return this;
    }

    @Step
    public MainPage enterValueInPasswordFieldOnPopUp(String password) {
        authPopUpPasswordField.setValue(password);
        return this;
    }

    @Step
    public void clickOnPopUpButtonLogin() {
        loginButtonOnPopUp.click();
    }

    @Step
    public boolean isDisplayedLinkToProfile() {
        return linkToProfileAuthUser.isDisplayed();
    }

    @Step
    public MainPage inputProductNameInSearchField(String productName) {
        searchProductInput.setValue(productName);
        return this;
    }

    @Step
    public void clickButtonStartSearch() {
        buttonStartSearchProduct.click();
    }

    @Step
    public void clickToStockLinkOnUpperMenu() {
        menuElementNavigateToStockPage.click();
    }

    @Step
    public CategoryCatPage clickToCatCategoryOnUpperMenu() {
        menuElementNavigateToProductsForCat.click();
        return new CategoryCatPage();
    }

    @Step
    public MainPage clickOnModalFormToChooseCity() {
        buttonChooseCity.click();
        return this;
    }

    @Step
    public MainPage inputCityOnSearchFieldModalWindow(String city) {
        fieldOnModalWindowSearchCity.click();
        fieldOnModalWindowSearchCity.waitUntil(Condition.visible, 3000).setValue(city);
        return this;
    }

    @Step
    public MainPage clickOnResultCitySearch() {
        resultSearchForCityInputed.get(0).click();
        return this;
    }

    @Step
    public void clickOnSubmitChoosedCity() {
        buttonSubmitChooseCity.click();
    }

    @Step
    public String getCurrentSelectedCity() {
        return currentCity.getText();
    }
}
