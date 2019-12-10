package pageobject;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DryFoodPage {
    private SelenideElement filterFromManufacturer = $(By.xpath("//div/span[contains(text(),'Производитель')]"));
    private SelenideElement preloaderFilterWorked = $(By.xpath("//div[text()='Загрузка...']"));
    private ElementsCollection snippetProductOnPage = $$("[class='product-item-container']");

    @Step
    public void selectManufacturerInFilterByName(String name) {
        filterFromManufacturer.click();
        $("[title='" + name + " ']").click();
        preloaderFilterWorked.waitUntil(Condition.visible, 2000);
        preloaderFilterWorked.waitUntil(Condition.hidden, 5000);
    }

    @Step
    public boolean isNameProductOnSnippetContainsNameManufacturer(String name) {
        int allSnippetOnPage = snippetProductOnPage.size();
        return snippetProductOnPage.stream()
                .filter(o -> o.$("[class='product-item-title']").getText().contains(name))
                .count() == allSnippetOnPage;
    }
}
