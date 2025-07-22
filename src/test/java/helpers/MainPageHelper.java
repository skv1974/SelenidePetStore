package helpers;

import com.codeborne.selenide.Clipboard;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;
import lombok.Data;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$x;
import static pages.MainPageEl.*;

@Data
public class MainPageHelper {

    ConfigReader configReader = new ConfigReader();

    @Step ("Открытие главной страницы petstore.swagger.io")
    public void open (){
        Selenide.open(configReader.getBaseUrl());
    }

    @Step("Проверка раскрыт или нет аккордеон")
    public Boolean checkAccordionOpen(String element){
        String open = $x(element).find(By.xpath(".//div[1]/button")).scrollIntoView(true).getAttribute("aria-expanded");
        if (open.equals("false")){
            return false;
        }else return true;
    }

    @Step("Нажатие на аккордеон")
    public void accordionClick (String parentElement){
        $x(parentElement).scrollIntoView(true).click();
    }

    @Step("Действие с кнопками аккордеона")
    public void accordionButtonsClick (String parentElement, String element){
        $x(parentElement).find(By.xpath(element)).scrollIntoView(true).click();
    }

    @Step("Ввод текста в поле")
    public void sendTextToField(String parentElement, String element, String text){
        $x(parentElement).find(By.xpath(element)).scrollIntoView(true).click();
        $x(parentElement).find(By.xpath(element)).clear();
        $x(parentElement).find(By.xpath(element)).setValue(text);
    }

    @Step("Получение кода ответа запроса")
    public String getCodeResponse(String parentElement){
        String code = $x(parentElement).find(By.xpath(CODE_FIELD)).scrollIntoView(true).getText();
        return code;
    }

    @Step("Получение значения сохраненного в буфере")
    public String getClipboardValue(){
        Clipboard clipboard = Selenide.clipboard();
        String clipboardValue = clipboard.getText();
        return clipboardValue;
    }

}
