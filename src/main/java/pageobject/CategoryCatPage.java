package pageobject;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class CategoryCatPage {
    private SelenideElement subCategoryDryFoodLink = $("[alt='Сухие корма']");

    @Step
    public DryFoodPage clickOnLeftMenuLinkDryFood() {
        subCategoryDryFoodLink.click();
        return new DryFoodPage();
    }
}
