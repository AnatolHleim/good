package pageobject;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$$;

public class StockPage {
    private ElementsCollection snippetProduct = $$(By.className("product-item-container"));
    private ElementsCollection iconSaleProduct = $$(("[class=\"product-item-label-text product-item-label-stock\"]"));

    @Step
    public int getCountSnippetOnPage() {
        return snippetProduct.size();
    }

    @Step
    public int getCountIconSaleOnPage() {
        iconSaleProduct.get(0).waitUntil(Condition.visible, 3000);
        return iconSaleProduct.size();
    }

}
