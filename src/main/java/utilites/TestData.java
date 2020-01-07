package utilites;

import java.util.Date;

public enum TestData {
    //setting
    START_PAGE("https://garfield.by/"),
    URL_TEST_RAIL("https://testrail.st.by/testrail/"),
    ACCOUNT_USER_TEST_RAIL("hleim_AA@st.by"),
    PASS_USER_TEST_RAIL(""),
    TEST_RUN_NAME("AutoTest"),
    TEST_DESCRIPTION(new Date().toString()),
    PROJECT_ID(52),
    ASSIGNED_TO(25),
    //data for test
    ACCOUNT_NAME(""),
    ACCOUNT_PASSWORD("12344321"),
    RESULT_SEARCH_STRING("Найдено: 1"),
    MANUFACTURER_NAME_FILTER("Araton"),
    NAME_CITY("Пружаны");


    TestData(final String string) {
        this.string = string;
    }

    TestData(final Integer numVal) {
        this.numVal = numVal;
    }

    private String string = null;
    private Integer numVal = 0;

    public String getStringProperties() {
        return string;
    }

    public Integer getIntegerProperties() {
        return numVal;
    }
}
