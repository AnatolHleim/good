package pageobject;

import com.codeborne.selenide.ElementsCollection;
import io.qameta.allure.Step;

import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.$$;

public class BasketPage {
    private ElementsCollection listProductInBasket = $$("[data-set-number]");
    private String attributeContainsName = "data-item-name";

    @Step
    public List<String> getListProductNameInBasket() {
        return listProductInBasket.stream()
                .map(o -> o.getAttribute(attributeContainsName))
                .collect(Collectors.toList());
    }

    @Step
    public void deleteElementWhenContainsSelectedName(String nameProduct) {
        listProductInBasket.forEach(o -> {
            if (o.getAttribute(attributeContainsName).contains(nameProduct))
                o.$("[onclick='return deleteProductRow(this)']").click();
        });
    }

    @Step
    public void incrementElementWhenContainsSelectedName(String nameProduct) {
        listProductInBasket.forEach(o -> {
            if (o.getAttribute(attributeContainsName).contains(nameProduct)) o.$("[class='basket_plus']").click();
        });

    }

    @Step
    public String getValueCountProduct(String nameProduct) {
        return listProductInBasket.stream()
                .filter(o -> o.getAttribute(attributeContainsName).contains(nameProduct))
                .map(o -> o.$("[class='basket_quantity_input']").getAttribute("value"))
                .collect(Collectors.toList()).get(0);
    }
}
