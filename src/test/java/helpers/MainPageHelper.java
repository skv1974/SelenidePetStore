package pages;

import com.codeborne.selenide.Clipboard;
import com.codeborne.selenide.Selenide;
import helpers.ConfigReader;
import io.qameta.allure.Step;
import lombok.Data;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$x;

@Data
public class PetStoreMainPage {

    ConfigReader configReader = new ConfigReader();


    //Локаторы элементов на странице 
    public static final String USER_SPAN = "//section[@class = 'block col-12 block-desktop col-12-desktop']//span[3]//button[@class = 'expand-operation']";

    public static final String GET_USER_BY_NAME_ACC = ".//div[@id = 'operations-user-getUserByName']"; //аккордеон GetUser
    public static final String POST_CREATE_USER = ".//div[@id = 'operations-user-createUser']";

    public static final String TRY_IT_OUT_BUTTON = ".//button[@class = 'btn try-out__btn']"; //кнопка Try it Out
    public static final String EXECUTE_BUTTON = ".//button[text() = 'Execute']"; //кнопка Execute
    public static final String USERNAME_FIELD = ".//tr[@data-param-name = 'username']//input";
    public static final String CODE_FIELD = ".//tr[@class = 'response']/td[@class = 'response-col_status']";
    public static final String COPY_RESPONSE_BODY_BUTTON = ".//tr[@class = 'response']//div[@class = 'copy-to-clipboard']";
    public static final String DOWNLOAD_RESPONSE_BUTTON = ".//tr[@class = 'response']//button[@class = 'download-contents']";

    @Step ("Открытие главной страницы petstore.swagger.io")
    public void open (){
        Selenide.open(configReader.getBaseUrl());
    }

    @Step("Раскрытие аккордеона GetUser")
    public void getUserSpanClick() throws InterruptedException {
        $x(GET_USER_BY_NAME_ACC).scrollIntoView(true).click();
    }

    @Step("Нажатие на кнопку Try it Out")
    public void tryItOutButtonClick(){
        $x(GET_USER_BY_NAME_ACC).find(By.xpath(TRY_IT_OUT_BUTTON)).scrollIntoView(true).click();
    }

    @Step("Ввод значения в поле UserName")
    public void sendTextUserNameInputField(String string){
        $x(GET_USER_BY_NAME_ACC).find(By.xpath(USERNAME_FIELD)).scrollIntoView(true).setValue(string);
    }

    @Step("Нажатие кнопки Execute")
    public void executeButtonClick(){
        $x(GET_USER_BY_NAME_ACC).find(By.xpath(EXECUTE_BUTTON)).scrollIntoView(true).click();
    }

    @Step("Получение кода ответа на запрос")
    public String getCodeResponse (){
        String code = $x(GET_USER_BY_NAME_ACC).find(By.xpath(CODE_FIELD)).scrollIntoView(true).getText();
        return code;
    }

    @Step("Нажатие кнопки CopyToClipboard")
    public void clipBoardButtonClick(){
        $x(GET_USER_BY_NAME_ACC).find(By.xpath(COPY_RESPONSE_BODY_BUTTON)).scrollIntoView(true).click();
    }

    // ///////////////////////////////////////////////
    @Step("Получение значения сохраненного в буфере")
    public String getClipboardValue(){
        Clipboard clipboard = Selenide.clipboard();
        String clipboardValue = clipboard.getText();
        return clipboardValue;
    }

    // /////////////////////////
    @Step("Нажатие на аккордеон")
    public void accordionClick (String parentElement){
        $x(parentElement).scrollIntoView(true).click();
    }

    @Step("Действие с кнопками аккордеона")
    public void accordionButtonsClick (String parentElement, String element){
        $x(parentElement).find(By.xpath(element)).scrollIntoView(true).click();
    }

    @Step("Получение кода ответа запроса")
    public String getCodeResponses (String parentElement){
        String code = $x(parentElement).find(By.xpath(CODE_FIELD)).scrollIntoView(true).getText();
        return code;
    }
}
