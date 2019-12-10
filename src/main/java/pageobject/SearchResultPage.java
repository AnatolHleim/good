package pageobject;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class SearchResultPage {
    private SelenideElement stringContainsCountResult = $(By.className("search-result"));

    @Step
    public String getResultString() {
        return stringContainsCountResult.getText();
    }
}
