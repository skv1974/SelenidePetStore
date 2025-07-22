import com.codeborne.selenide.Configuration;
import helpers.ConfigReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import pages.PetStoreMainPage;

import static com.codeborne.selenide.Selenide.$;

public class TestAll {

    ConfigReader configReader = new ConfigReader();
    PetStoreMainPage petStoreMainPage = new PetStoreMainPage();

    @BeforeSuite
    public void sutUpSuit(){
        Configuration.browser = configReader.getBrowser();
        Configuration.browserSize = configReader.getResolution();
    }

    @Test (testName = "Проверка запроса GetUser")
    public void GetUserTest() throws InterruptedException {

        petStoreMainPage.open();
        Thread.sleep(3000);

        petStoreMainPage.GetUserSpanClick();
        Thread.sleep(5000);

    }
}
